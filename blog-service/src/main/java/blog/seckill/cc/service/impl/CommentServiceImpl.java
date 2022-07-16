package blog.seckill.cc.service.impl;

import blog.seckill.cc.entity.Comment;
import blog.seckill.cc.mapper.CommentMapper;
import blog.seckill.cc.service.CommentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * description: CommentServiceImpl <br>
 * date: 2022/7/15 17:04 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    private CommentMapper commentMapper;


    @Override
    public List<Comment> queryCommentsByArticleId(Long articleId) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_id", articleId);
        return commentMapper.selectList(queryWrapper);
    }

    @Override
    public List<Comment> queryCommentsWithUserInfoByArticleId(Long articleId) {
        return commentMapper.queryCommentsWithUserInfoByArticleId(articleId);
    }
}
