package blog.seckill.cc.test.service;

import blog.seckill.cc.BlogServiceMainApp;
import blog.seckill.cc.entity.VisitRecord;
import blog.seckill.cc.mapper.VisitRecordMapper;
import blog.seckill.cc.service.IndexService;
import blog.seckill.cc.service.impl.IndexServiceImpl;
import blog.seckill.cc.test.TestBase;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * description: IndexServiceTest <br>
 * date: 2022/8/28 11:25 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
@SpringBootTest(classes = {BlogServiceMainApp.class})
public class IndexServiceTest extends TestBase {

    @Resource
    private IndexService indexService;

    @Resource
    private VisitRecordMapper visitRecordMapper;

    @Test
    public void testGetTotalViewCount() throws NoSuchFieldException, IllegalAccessException {
        System.out.println(indexService.getTotalVisitCount());
        Field totalVisitCount = IndexServiceImpl.class.getDeclaredField("totalVisitCount");
        totalVisitCount.setAccessible(true);
        System.out.println(totalVisitCount.get(indexService));
    }

    @Test
    public void testAddTotalViewCount() throws InterruptedException {
        int tvcOld = indexService.getTotalVisitCount();
        System.out.println(">>>>>>> tvcOld = " + tvcOld);


        CountDownLatch countDownLatch = new CountDownLatch(20);
        for (int i = 0; i < 20; i++) {
            int finalI = i;
            VisitRecord visitRecord = new VisitRecord();
            visitRecord.setIp("0.0.0.0");
            visitRecord.setVisitDate(new Date());
            new Thread(() -> {
                countDownLatch.countDown();
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                visitRecord.setVisitorLocation("测试用例" + finalI);
                indexService.addTotalVisitCount(visitRecord);
            }).start();
        }
        int totalVisitCount = indexService.getTotalVisitCount();
        System.out.println(">>>>>>> tvcNew = " + totalVisitCount);

        TimeUnit.SECONDS.sleep(3);
        assert tvcOld + 20 == totalVisitCount;
        Long aLong = visitRecordMapper.selectCount(new QueryWrapper<>());

        System.out.println(">>>>>>> tvcSelect = " + aLong);

        assert aLong == totalVisitCount;
    }
}
