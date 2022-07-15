package blog.seckill.cc.service.impl;

import blog.seckill.cc.annotation.QueryAtInit;
import blog.seckill.cc.entity.Summary;
import blog.seckill.cc.mapper.SummaryMapper;
import blog.seckill.cc.mapper.WebInfoMapper;
import blog.seckill.cc.service.IndexService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * description: IndexServiceImpl <br>
 * date: 2022/7/14 16:40 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@Service
@Slf4j
public class IndexServiceImpl implements IndexService {

    @Resource
    private SummaryMapper summaryMapper;

    @Resource
    private WebInfoMapper webInfoMapper;

    @QueryAtInit(mapperClass = SummaryMapper.class)
    private volatile Integer totalVisitCount = 0;

    @Override
    public int getTotalVisitCount() {
        return totalVisitCount;
    }

    @Override
    public int addTotalVisitCount(Summary summary) {
        int insert = summaryMapper.insert(summary);
        synchronized (this) {
            totalVisitCount += insert;
        }
        return totalVisitCount;
    }

    @Override
    public String getArticleListVersion() {
        return webInfoMapper.selectOne(new QueryWrapper<>()).getArticleVersion();
    }
}
