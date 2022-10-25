package blog.seckill.cc.test.util;

import blog.seckill.cc.BlogServiceMainApp;
import blog.seckill.cc.entity.VisitRecord;
import blog.seckill.cc.mapper.VisitRecordMapper;
import blog.seckill.cc.test.TestBase;
import blog.seckill.cc.util.IpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * description: RewriteAllRecordsUtilTest <br>
 * date: 2022/10/25 19:16 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
@SpringBootTest(classes = {BlogServiceMainApp.class})
public class RewriteAllRecordsUtilTest extends TestBase {
    @Resource
    private VisitRecordMapper visitRecordMapper;

    //这个是用来重写数据库中所有已存在记录的访问地址的工具方法
    @Test
    public void doRewrite() {
        QueryWrapper<VisitRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNull("visitor_location");
        List<VisitRecord> visitRecords = visitRecordMapper.selectList(queryWrapper);
        for (VisitRecord visitRecord : visitRecords) {
            assert visitRecord.getVisitorLocation() == null;
            String ipPhysicalLocation = IpUtil.getIpPhysicalLocation(visitRecord.getIp());
            visitRecord.setVisitorLocation(ipPhysicalLocation);
            System.out.println(ipPhysicalLocation);
            visitRecordMapper.updateById(visitRecord);
        }


    }
}
