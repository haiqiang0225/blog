package blog.seckill.cc.test.service;

import blog.seckill.cc.BlogServiceMainApp;
import blog.seckill.cc.entity.Tag;
import blog.seckill.cc.service.TagService;
import blog.seckill.cc.test.TestBase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * description: TagServiceTest <br>
 * date: 2022/7/14 14:22 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@SpringBootTest(classes = {BlogServiceMainApp.class})
public class TagServiceTest extends TestBase {


    @Resource
    private TagService tagService;

    @Test
    public void testQueryAllTags() {
        List<Tag> tags = tagService.queryAllTags();
        assert !tags.isEmpty();
        System.out.println(tags);
    }
}
