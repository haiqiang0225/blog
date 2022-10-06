package blog.seckill.cc.service.impl;

import blog.seckill.cc.annotation.QueryAtInit;
import blog.seckill.cc.entity.VisitRecord;
import blog.seckill.cc.mapper.VisitRecordMapper;
import blog.seckill.cc.mapper.WebInfoMapper;
import blog.seckill.cc.service.IndexService;
import blog.seckill.cc.service.async.RecordAsyncTaskService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * description: IndexServiceImpl <br>
 * date: 2022/7/14 16:40 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@Service
@Slf4j
public class IndexServiceImpl extends ServiceImpl<VisitRecordMapper, VisitRecord> implements IndexService {

    private final RecordAsyncTaskService<VisitRecord> asyncTaskService = new RecordAsyncTaskService<VisitRecord>() {
        @Override
        protected boolean doSync() {
            boolean b = saveBatch(recordArrayList);
            recordArrayList.clear();
            return b;
        }

    };

    @Resource
    private VisitRecordMapper visitRecordMapper;

    @Resource
    private WebInfoMapper webInfoMapper;

    @QueryAtInit(mapperClass = VisitRecordMapper.class)
    private volatile int totalVisitCount = 0;


    @Override
    public int getTotalVisitCount() {
        return totalVisitCount;
    }

    public void setTotalVisitCount(int totalVisitCount) {
        this.totalVisitCount = totalVisitCount;
    }

    @Override
    public int addTotalVisitCount(VisitRecord visitRecord) {
        asyncTaskService.exec(visitRecord);
        synchronized (this) {
            totalVisitCount += 1;
        }
        return totalVisitCount;
    }

    @Override
    public String getArticleListVersion() {
        return webInfoMapper.selectOne(new QueryWrapper<>()).getArticleVersion();
    }


}
