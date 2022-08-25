package blog.seckill.cc.service.impl;

import blog.seckill.cc.annotation.QueryAtInit;
import blog.seckill.cc.entity.Article;
import blog.seckill.cc.entity.ArticleDetail;
import blog.seckill.cc.mapper.ArticleDetailMapper;
import blog.seckill.cc.mapper.ArticleMapper;
import blog.seckill.cc.mapper.CategoryMapper;
import blog.seckill.cc.mapper.TagMapper;
import blog.seckill.cc.service.ArticleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * description: ArticleServiceImpl <br>
 * date: 2022/7/7 17:05 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {
    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private TagMapper tagMapper;

    @Resource
    private ArticleDetailMapper articleDetailMapper;

    // 🔝置顶文章的总数
    @QueryAtInit(tableId = "top_flag", mapperClass = ArticleMapper.class)
    private final Integer topArticleCount = 0;

    @QueryAtInit(tableId = "article_id", mapperClass = ArticleMapper.class)
    private final Integer totalCount = 0;


    private final ConcurrentHashMap<Long, Long> articleQueryCountMap = new ConcurrentHashMap<>();

    // CPU数量
    private static final int CPUS = Runtime.getRuntime().availableProcessors();

    // 阻塞队列长度
    private static final int BLOCKING_Q_SIZE = 64;

    // 默认拒绝策略
    private static final RejectedExecutionHandler REJECTED_EXECUTION_HANDLER =
            new ThreadPoolExecutor.CallerRunsPolicy();

    private final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(CPUS, CPUS * 2, 10, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(BLOCKING_Q_SIZE), REJECTED_EXECUTION_HANDLER);

    // 活跃的正在执行计数的线程数
    private final AtomicInteger activeAddTaskCount = new AtomicInteger();

    // 是否准备/正在更新数据
    private volatile boolean syncFlag = false;

    // 默认超时时间
    private static final int DEFAULT_SYNC_WAIT_TIMEOUT = 1500;

    // 执行刷盘任务的线程
    private Thread theSyncThread;

    public ArticleServiceImpl() {
        startScheduleTasks();
    }

    @Override
    public List<Article> queryArticleList(int start, int count, boolean queryTop) {
        return articleMapper.selectArticlesWithCategoryAndTags(start, count, queryTop);
    }

    @Override
    public List<Article> queryRandomArticleList(int count) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        // 记录已经生成过了的
        HashSet<Integer> alreadyGen = new HashSet<>();
        for (int i = 0; i < count; i++) {
            int rand = ThreadLocalRandom.current().nextInt(totalCount);
            // 如果生成过了就继续获取
            while (alreadyGen.contains(rand)) {
                rand = ThreadLocalRandom.current().nextInt(totalCount);
            }
            alreadyGen.add(rand);
        }
        queryWrapper.in("article_id", alreadyGen);
        return articleMapper.selectList(queryWrapper);
    }

    @Override
    public ArticleDetail queryArticleDetail(Long articleDetailId) {
        return queryArticleDetail(articleDetailId, true);
    }

    @Override
    public ArticleDetail queryArticleDetail(Long articleDetailId, boolean addViewCount) {
        ArticleDetail articleDetail = articleDetailMapper.selectById(articleDetailId);
        log.info("访问文章: {}, 增加访问量: {}", articleDetailId, addViewCount);
        if (addViewCount) {
            // 增加查看数,线程池异步执行
            addArticleQueryCount(articleDetail);
        }
        return articleDetail;
    }

    /**
     * description: addArticleQueryCount 增加计数<br>
     * version: 1.0 <br>
     * date: 2022/8/24 19:13 <br>
     * author: objcat <br>
     *
     * @param articleDetail 文章详情
     */
    private void addArticleQueryCount(ArticleDetail articleDetail) {
        // 交给线程池执行
        threadPoolExecutor.execute(new ArticleViewCountAddTask(articleDetail.getArticleId()));
    }

    /**
     * description: startScheduleTasks 统一调度所有定时任务<br>
     * version: 1.0 <br>
     * date: 2022/8/24 22:31 <br>
     * author: objcat <br>
     *
     * @param
     * @return void
     */
    private void startScheduleTasks() {
        // 运行30s, 如果没有真正执行任务,就挂起任务
        scheduleSyncCountToDB();
    }

    /**
     * description: syncCountToDB 同步数据计数到数据库<br>
     * version: 1.0 <br>
     * date: 2022/8/24 21:43 <br>
     * author: objcat <br>
     *
     * @param
     * @return void
     */
    private void scheduleSyncCountToDB() {
        theSyncThread = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    startSync();
                    boolean doExecuteSync = doSyncCountToDB();
                    // 如果没有执行同步任务, 那么就挂起线程
                    if (!doExecuteSync) {
                        LockSupport.park();
                    }
                    endSync();
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (Exception e) {
                log.error("同步线程异常终止!");
                e.printStackTrace();
            }
        });
        theSyncThread.start();
    }

    private void unParkSyncThread() {
        LockSupport.unpark(theSyncThread);
    }


    private boolean doSyncCountToDB() {
        // 开始同步
        startSync();
        long startTimeStamp = System.currentTimeMillis();
        // 要等待所有的计数任务执行完再同步,避免出现线程安全问题
        while (activeAddTaskCount.get() != 0) {
            if (System.currentTimeMillis() - startTimeStamp > DEFAULT_SYNC_WAIT_TIMEOUT) {
                log.error("线程同步刷盘任务超时失败, 任务开始时间戳: {}", startTimeStamp);
                throw new RuntimeException("");
            }
        }
        // 是否真正执行了刷盘任务的标志
        AtomicBoolean flag = new AtomicBoolean(false);
        articleQueryCountMap.forEach((articleId, val) -> {
            if (val != 0) {
                // 更新数据库并清除计数
                articleMapper.addViewCount(articleId, val);
                articleQueryCountMap.put(articleId, 0L);
                // 设置标志
                flag.set(true);
            }
        });

        // 结束同步
        endSync();
        return flag.get();
    }

    private void startSync() {
        syncFlag = true;
    }

    private void endSync() {
        syncFlag = false;
    }

    @Override
    public Article getArticle(Long articleId) {
        return articleMapper.selectById(articleId);
    }

    @Override
    public Integer getTopArticleCount() {
        return topArticleCount;
    }

    @Override
    public Integer getTotalArticleCount() {
        return totalCount;
    }

    /**
     * description: 用于异步增加计数 <br>
     * version: 1.0 <br>
     * date: 2022/8/24 19:29 <br>
     * author: objcat <br>
     */
    class ArticleViewCountAddTask implements Runnable {

        // 默认超时时间
        static final int DEFAULT_TIMEOUT = 1000;

        static final int DEFAULT_WAIT_TIME = 100;


        Long articleId;

        public ArticleViewCountAddTask(Long articleId) {
            this.articleId = articleId;
        }

        @Override
        public void run() {
            // 如果正在更新, 就暂时停止计数,先自旋一段时间
            long startTimeStamp = System.currentTimeMillis();
            try {
                // 如果要执行同步任务或者已经开始执行了,就不能进行
                while (syncFlag) {
                    // 如果自旋一段时间还不能得到执行,就阻塞线程
                    long currentTimeMillis = System.currentTimeMillis();
                    if (currentTimeMillis - startTimeStamp > DEFAULT_WAIT_TIME) {
                        if (currentTimeMillis - startTimeStamp > DEFAULT_TIMEOUT) {
                            throw new RuntimeException();
                        }
                        TimeUnit.MILLISECONDS.sleep(100);
                    }
                }
            } catch (Exception e) {
                log.error("计数任务执行失败,等待超时: {}", articleId);
            }
            activeAddTaskCount.getAndIncrement();
            // 增加计数
            articleQueryCountMap.compute(articleId, (k, v) -> {
                if (v == null) {
                    v = 0L;
                }
                return ++v;
            });
            activeAddTaskCount.getAndDecrement();
            unParkSyncThread();
        }
    }


}
