package blog.secikll.cc.mapper;

import blog.secikll.cc.TestBase;
import blog.seckill.cc.BlogServiceMainApp;
import blog.seckill.cc.entity.Category;
import blog.seckill.cc.mapper.CategoryMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * description: CategoryMapperTest <br>
 * date: 2022/7/7 15:34 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@SpringBootTest(classes = {BlogServiceMainApp.class})
public class CategoryMapperTest extends TestBase {

    @Resource
    private CategoryMapper categoryMapper;

    @Test
    public void testCategoryMapper() {
        Category category = categoryMapper.selectById(0);
        assert category != null;
        System.out.println(">>>>>>>>>>>>> " + category);
    }
}
