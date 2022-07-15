package blog.seckill.cc.service.impl;

import blog.seckill.cc.entity.Tag;
import blog.seckill.cc.mapper.TagMapper;
import blog.seckill.cc.service.TagService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * description: TagServiceImpl <br>
 * date: 2022/7/14 14:21 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@Service
public class TagServiceImpl implements TagService {

    @Resource
    private TagMapper tagMapper;

    @Override
    public List<Tag> queryAllTags() {
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        return tagMapper.selectList(queryWrapper);
    }

    @Override
    public List<Tag> queryArticleTags(Long articleId) {
        return tagMapper.queryTagsByArticleId(articleId);
    }
}
