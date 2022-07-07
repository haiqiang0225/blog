package blog.secikll.cc.mapper;

import blog.secikll.cc.TestBase;
import blog.seckill.cc.BlogServiceMainApp;
import blog.seckill.cc.entity.Article;
import blog.seckill.cc.entity.ArticleDetail;
import blog.seckill.cc.mapper.ArticleDetailMapper;
import blog.seckill.cc.mapper.ArticleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * description: ArticleMapperTest <br>
 * date: 2022/7/7 15:12 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@SpringBootTest(classes = {BlogServiceMainApp.class})
public class ArticleMapperTest extends TestBase {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private ArticleDetailMapper articleDetailMapper;

    @Test
    public void testArticleMapper() {
        Article article = articleMapper.selectById(0);
        assert article != null;
        System.out.println(article);
    }

    @Test
    public void testArticleDetailMapper() {
        ArticleDetail articleDetail = articleDetailMapper.selectById(0);
        assert articleDetail != null;
        System.out.println(articleDetail);
    }

}
