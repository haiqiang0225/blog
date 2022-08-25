package blog.seckill.cc.test.service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * description: ThCOunt <br>
 * date: 2022/8/24 21:21 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
public class ThCOunt {
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 1, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1024));
        ConcurrentHashMap<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>();

        for (int i = 0; i < 1024; i++) {
            executor.execute(() -> {
                try {
                    for (int j = 0; j < 10; j++) {
                        concurrentHashMap.compute(1, (k, v) -> {
                            if (v == null) {
                                v = 0;
                            }
                            return ++v;
                        });
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }

        AtomicInteger c = new AtomicInteger();
        Thread thread = new Thread(() -> {

            while (!Thread.currentThread().isInterrupted()) {
                concurrentHashMap.forEach((k, v) -> {
                    c.addAndGet(v);
                    concurrentHashMap.put(k, 0);
                });
            }
        });
        thread.start();

        TimeUnit.MILLISECONDS.sleep(2000);
        while (executor.getActiveCount() != 0) {

        }
        System.out.println(c.get());
        thread.interrupt();
        executor.shutdownNow();
    }
}
