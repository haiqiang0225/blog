package blog.seckill.cc.service.async;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

/**
 * description: ViewCountAbstractAsyncTaskService <br>
 * date: 2022/8/27 20:40 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@Slf4j
public abstract class ViewCountAbstractAsyncTaskService extends AbstractAsyncTaskService {

    // 存储浏览量的map
    protected final ConcurrentHashMap<Long, Long> viewCountMap = new ConcurrentHashMap<>();


    protected class ViewCountAddTask extends BaseTask implements Runnable {

        Long key;

        public ViewCountAddTask(Long key) {
            this.key = key;
        }

        @Override
        protected void doRunTask() {
            // 增加计数
            viewCountMap.compute(key, (k, v) -> {
                if (v == null) {
                    v = 0L;
                }
                return ++v;
            });
        }
    }

}
