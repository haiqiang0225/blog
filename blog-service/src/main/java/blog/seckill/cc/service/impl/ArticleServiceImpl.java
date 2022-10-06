package blog.seckill.cc.service.impl;

import blog.seckill.cc.annotation.QueryAtInit;
import blog.seckill.cc.entity.Article;
import blog.seckill.cc.entity.ArticleDetail;
import blog.seckill.cc.mapper.ArticleDetailMapper;
import blog.seckill.cc.mapper.ArticleMapper;
import blog.seckill.cc.service.ArticleService;
import blog.seckill.cc.service.async.ViewCountAsyncTaskService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * description: ArticleServiceImpl <br>
 * date: 2022/7/7 17:05 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@Service
@Slf4j
public class ArticleServiceImpl extends ViewCountAsyncTaskService implements ArticleService {
    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private ArticleDetailMapper articleDetailMapper;

    // 🔝置顶文章的总数
    @QueryAtInit(tableId = "top_flag", mapperClass = ArticleMapper.class)
    private final Integer topArticleCount = 0;

    @QueryAtInit(tableId = "article_id", mapperClass = ArticleMapper.class)
    private final Integer totalCount = 0;

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
        // 异步执行
        asyncExecute(new ViewCountAddTask(articleDetail.getArticleId()));
    }

    @Override
    protected boolean doSync() {
        AtomicBoolean flag = new AtomicBoolean(false);
        viewCountMap.forEach((articleId, val) -> {
            if (val != 0) {
                // 更新数据库并清除计数
                articleMapper.addViewCount(articleId, val);
                viewCountMap.put(articleId, 0L);
                // 设置标志
                flag.set(true);
            }
        });
        return flag.get();
    }

    /**
     * description: syncCountToDB 同步数据计数到数据库<br>
     * version: 1.0 <br>
     * date: 2022/8/24 21:43 <br>
     * author: objcat <br>
     */


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

    @Override
    public List<Article> searchArticleWithKeyWord(int start, int count, String keyWord) {
        return articleMapper.searchArticleWithKeyWord(start, count, keyWord);
    }

}
