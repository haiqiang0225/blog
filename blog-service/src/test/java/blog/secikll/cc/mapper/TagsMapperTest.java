package blog.secikll.cc.mapper;

import blog.secikll.cc.TestBase;
import blog.seckill.cc.BlogServiceMainApp;
import blog.seckill.cc.entity.Tags;
import blog.seckill.cc.mapper.TagsMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * description: TagsMapperTest <br>
 * date: 2022/7/7 15:48 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@SpringBootTest(classes = {BlogServiceMainApp.class})
public class TagsMapperTest extends TestBase {
    @Resource
    private TagsMapper tagsMapper;

    @Test
    public void testTagsMapper() {
        Tags tags = tagsMapper.selectById(0);
        assert tags != null;
        System.out.println(tags);
    }
}
