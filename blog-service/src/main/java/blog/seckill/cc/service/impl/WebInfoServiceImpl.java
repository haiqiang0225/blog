package blog.seckill.cc.service.impl;

import blog.seckill.cc.annotation.QueryAtInit;
import blog.seckill.cc.entity.WebInfo;
import blog.seckill.cc.mapper.WebInfoMapper;
import blog.seckill.cc.service.WebInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * description: WebInfoServiceImpl <br>
 * date: 2022/7/14 20:05 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@Service
public class WebInfoServiceImpl implements WebInfoService {

    @Resource
    private WebInfoMapper webInfoMapper;

    @QueryAtInit(mapperClass = WebInfoMapper.class)
    private WebInfo webInfo;


    @Override
    public String getArticleListVersion() {
        return webInfoMapper.selectOne(new QueryWrapper<>()).getArticleVersion();
    }

    @Override
    public Long getWebVersion() {
        return webInfo.getWebVersion();
    }

    @Override
    public Long getViewCount() {
        return webInfo.getViewCount();
    }

    @Override
    public Long getArticleCount() {
        return webInfo.getWebVersion();
    }
}
