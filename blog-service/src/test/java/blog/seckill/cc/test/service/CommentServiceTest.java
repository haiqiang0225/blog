package blog.seckill.cc.test.service;

import blog.seckill.cc.BlogServiceMainApp;
import blog.seckill.cc.entity.Comment;
import blog.seckill.cc.mapper.CommentMapper;
import blog.seckill.cc.service.CommentService;
import blog.seckill.cc.test.TestBase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * description: CommentServiceTest <br>
 * date: 2022/7/15 17:06 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@SpringBootTest(classes = {BlogServiceMainApp.class})

public class CommentServiceTest extends TestBase {

    @Resource
    private CommentService commentService;

    @Resource
    private CommentMapper commentMapper;

    @Test
    public void testQueryCommentsByArticleId() {
        List<Comment> comments = commentService.queryCommentsByArticleId(20L);
        assert !comments.isEmpty();
        System.out.println(">>>>>>>" + comments);
    }


//    @Test
    public void insertForTest() {
        for (int i = 0; i < 10; i++) {
            Comment comment = new Comment();
            comment.setArticleId(20L);
            comment.setParentId((long) i - 1);
            comment.setContent("测试评论 " + i);
            comment.setLikeCount(0L);
            comment.setUserId(0L);
            commentMapper.insert(comment);
        }
    }
}
