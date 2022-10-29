package blog.seckill.cc.service.async;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * description: AysncTaskService 异步任务服务<br>
 * date: 2022/8/27 18:33 <br>
 * author: hq <br>
 * version: 1.0 <br>
 * <p>
 * 需要重写 {@link AbstractAsyncTaskService#doSync()}方法实现业务逻辑.
 * 该类会自动进行调度执行{@link AbstractAsyncTaskService#doSync()}方法.
 * 此外异步任务需要重写{@link BaseTask#doRunTask()} 方法.
 *
 * @see BaseTask
 */
@Slf4j
public abstract class AbstractAsyncTaskService {

    // CPU数量
    private static final int CPUS = Runtime.getRuntime().availableProcessors();

    // 阻塞队列长度
    private static final int DEFAULT_BLOCKING_Q_SIZE = 64;

    // 默认拒绝策略
    private static final RejectedExecutionHandler DEFAULT_REJECTED_EXECUTION_HANDLER =
            new ThreadPoolExecutor.CallerRunsPolicy();

    private final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(CPUS, CPUS * 2, 10, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(DEFAULT_BLOCKING_Q_SIZE), DEFAULT_REJECTED_EXECUTION_HANDLER);

    // 活跃的正在执行计数的线程数
    protected final AtomicInteger activeAddTaskCount = new AtomicInteger();

    // 是否准备/正在更新数据
    protected volatile boolean syncFlag = false;

    // 默认超时时间
    private static final int DEFAULT_SYNC_WAIT_TIMEOUT = 1500;

    // 执行刷盘任务的线程
    private Thread theSyncThread;

    public AbstractAsyncTaskService() {
        startScheduleTasks();
    }

    /**
     * description: startScheduleTasks 启动所有任务 <br>
     * version: 1.0 <br>
     * date: 2022/8/27 18:55 <br>
     * author: hq <br>
     */
    protected void startScheduleTasks() {
        scheduleSyncTask();
    }

    /**
     * description: scheduleSyncTask <br>
     * version: 1.0 <br>
     * date: 2022/8/27 20:55 <br>
     * author: haiqiang0225@gmail.com <br>
     */
    protected void scheduleSyncTask() {
        theSyncThread = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    //boolean doExecuteSync = doSync();
                    startSync();
                    long startTimeStamp = System.currentTimeMillis();
                    // 要等待所有的计数任务执行完再同步,避免出现线程安全问题
                    while (activeAddTaskCount.get() != 0) {
                        if (System.currentTimeMillis() - startTimeStamp > DEFAULT_SYNC_WAIT_TIMEOUT) {
                            log.error("线程同步刷盘任务超时失败, 任务开始时间戳: {}", startTimeStamp);
                            throw new RuntimeException("");
                        }
                    }
                    // 执行同步任务
                    boolean doExecuteSync = doSync();
                    // 结束同步
                    endSync();
                    // 如果没有执行同步任务, 那么就挂起线程
                    if (!doExecuteSync) {
                        LockSupport.park();
                    }
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (Exception e) {
                log.error("同步线程异常终止!");
                e.printStackTrace();
            } finally {
                endSync();
            }
        });
        theSyncThread.start();
    }


    /**
     * description: doSync 执行同步任务的逻辑代码, 因为只有一个线程执行,且不会和异步任务同时执行, 不需要保证线程安全 <br>
     * version: 1.0 <br>
     * date: 2022/8/27 20:00 <br>
     * author: hq <br>
     *
     * @return boolean
     */
    protected abstract boolean doSync();

    /**
     * description: startSync 开始同步,进行准备工作 <br>
     * version: 1.0 <br>
     * date: 2022/8/27 19:01 <br>
     * author: hq <br>
     */
    protected void startSync() {
        syncFlag = true;
    }

    /**
     * description: endSync 结束同步,执行清理工作<br>
     * version: 1.0 <br>
     * date: 2022/8/27 19:01 <br>
     * author: hq <br>
     */
    protected void endSync() {
        syncFlag = false;
    }

    /**
     * description: unParkSyncThread 唤醒同步线程<br>
     * version: 1.0 <br>
     * date: 2022/8/27 19:01 <br>
     * author: hq <br>
     */
    protected void unParkSyncThread() {
        LockSupport.unpark(theSyncThread);
    }

    /**
     * description: execute 提交异步任务<br>
     * version: 1.0 <br>
     * date: 2022/8/27 19:35 <br>
     * author: hq <br>
     *
     * @param runnable task
     */
    public void asyncExecute(Runnable runnable) {
        threadPoolExecutor.execute(runnable);
    }

    /**
     * description:  <br>
     * version: 1.0 <br>
     * date: 2022/8/27 21:09 <br>
     * author: haiqiang0225@gmail.com <br>
     * <p>
     * 所有子类都应该继承该类实现异步任务
     *
     * @see AbstractAsyncTaskService
     */
    protected abstract class BaseTask implements Runnable {
        // 默认超时时间
        static final int DEFAULT_TIMEOUT = 1000;

        // 默认自旋时间
        static final int DEFAULT_WAIT_TIME = 100;

        static final int DEFAULT_SLEEP_TIME = 100;

        /**
         * description: doRunTask 执行异步提交任务,该方法需要保证是线程安全的 <br>
         * version: 1.0 <br>
         * date: 2022/8/28 10:06 <br>
         * author: haiqiang0225@gmail.com <br>
         */
        protected abstract void doRunTask();

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
                        TimeUnit.MILLISECONDS.sleep(DEFAULT_SLEEP_TIME);
                    }
                }
            } catch (Exception e) {
                log.error("计数任务执行失败,等待超时: {}", e.getMessage());
            }
            // 统计活跃任务计数增加
            activeAddTaskCount.getAndIncrement();
            // 执行业务逻辑
            doRunTask();
            // 统计活跃任务计数减少
            activeAddTaskCount.getAndDecrement();
            // 唤醒执行同步任务的线程
            unParkSyncThread();
        }
    }
}
