package blog.seckill.cc.test.mapper;

import blog.seckill.cc.test.TestBase;
import blog.seckill.cc.BlogServiceMainApp;
import blog.seckill.cc.entity.Comment;
import blog.seckill.cc.mapper.CommentMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * description: CommentMapperTest <br>
 * date: 2022/7/7 15:41 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@SpringBootTest(classes = {BlogServiceMainApp.class})
public class CommentMapperTest extends TestBase {

    @Resource
    private CommentMapper commentMapper;

    @Test
    public void testCommentMapper() {
        Comment comment = commentMapper.selectById(0);
        assert comment != null;
        System.out.println(">>>>>>>>>>>" + comment);
    }

    @Test
    public void testQueryCommentsWithUserInfoByArticleId() {
        List<Comment> comments = commentMapper.queryCommentsWithUserInfoByArticleId(20L);
        assert !comments.isEmpty();
        System.out.println(">>>>>>>>>>> " + comments);
    }
}

