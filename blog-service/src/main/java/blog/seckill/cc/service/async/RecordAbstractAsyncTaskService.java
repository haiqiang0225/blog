package blog.seckill.cc.service.async;

import java.util.ArrayList;

/**
 * description: RecordAbstractAsyncTaskService <br>
 * date: 2022/8/28 10:00 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
public abstract class RecordAbstractAsyncTaskService<E> extends AbstractAsyncTaskService {

    // 默认存储列表
    protected final ArrayList<E> recordArrayList = new ArrayList<>();


    /**
     * description: exec 执行提交任务 <br>
     * version: 1.0 <br>
     * date: 2022/8/28 14:43 <br>
     * author: haiqiang0225@gmail.com <br>
     *
     * @param e 存储项
     */
    public void exec(E e) {
        asyncExecute(new RecordAddTask(e));
    }

    protected class RecordAddTask extends BaseTask {

        // 存储对象
        E r;

        public RecordAddTask(E r) {
            this.r = r;
        }

        @Override
        protected void doRunTask() {
            synchronized (RecordAbstractAsyncTaskService.this) {
                recordArrayList.add(r);
            }
        }
    }


}
