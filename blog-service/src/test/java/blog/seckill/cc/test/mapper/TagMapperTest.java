package blog.seckill.cc.test.mapper;

import blog.seckill.cc.test.TestBase;
import blog.seckill.cc.BlogServiceMainApp;
import blog.seckill.cc.entity.Tag;
import blog.seckill.cc.mapper.TagMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * description: TagMapperTest <br>
 * date: 2022/7/7 15:48 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@SpringBootTest(classes = {BlogServiceMainApp.class})
public class TagMapperTest extends TestBase {
    @Resource
    private TagMapper tagMapper;

    @Test
    public void testTagsMapper() {
        Tag tag = tagMapper.selectById(0);
        assert tag != null;
        System.out.println(tag);
    }

    @Test
    public void testGetTagsByArticleId() {
        List<Tag> tags = tagMapper.queryTagsByArticleId(0L);
        assert tags != null;
        System.out.println(tags);
    }
}
