package blog.seckill.cc.service.impl;

import blog.seckill.cc.annotation.QueryAtInit;
import blog.seckill.cc.entity.Summary;
import blog.seckill.cc.mapper.SummaryMapper;
import blog.seckill.cc.mapper.WebInfoMapper;
import blog.seckill.cc.service.IndexService;
import blog.seckill.cc.service.async.AsyncTaskService;
import blog.seckill.cc.service.async.RecordAsyncTaskService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * description: IndexServiceImpl <br>
 * date: 2022/7/14 16:40 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@Service
@Slf4j
public class IndexServiceImpl extends ServiceImpl<SummaryMapper, Summary> implements IndexService {

    private final RecordAsyncTaskService<Summary> asyncTaskService = new RecordAsyncTaskService<Summary>() {
        @Override
        protected boolean doSync() {
            boolean b = saveBatch(recordArrayList);
            recordArrayList.clear();
            return b;
        }

    };

    @Resource
    private SummaryMapper summaryMapper;

    @Resource
    private WebInfoMapper webInfoMapper;

    @QueryAtInit(mapperClass = SummaryMapper.class)
    private volatile int totalVisitCount = 0;


    @Override
    public int getTotalVisitCount() {
        return totalVisitCount;
    }

    public void setTotalVisitCount(int totalVisitCount) {
        this.totalVisitCount = totalVisitCount;
    }

    @Override
    public int addTotalVisitCount(Summary summary) {
        asyncTaskService.exec(summary);
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
