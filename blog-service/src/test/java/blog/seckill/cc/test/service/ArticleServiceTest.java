package blog.seckill.cc.test.service;

import blog.seckill.cc.entity.ArticleDetail;
import blog.seckill.cc.test.TestBase;
import blog.seckill.cc.BlogServiceMainApp;
import blog.seckill.cc.entity.Article;
import blog.seckill.cc.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * description: ArticleServiceTest <br>
 * date: 2022/7/7 18:30 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@SpringBootTest(classes = {BlogServiceMainApp.class})
public class ArticleServiceTest extends TestBase {
    @Resource
    private ArticleService articleService;

    @Test
    public void testGetArticleListFirstTime() {
        List<Article> articles = articleService.queryArticleList(0, 5, false);
        assert !articles.isEmpty();
        System.out.println(articles);
    }

    @Test
    public void testGetCount() throws NoSuchFieldException, IllegalAccessException {
        Field declaredField = articleService.getClass().getDeclaredField("topArticleCount");
        declaredField.setAccessible(true);
        Number o = (Number) declaredField.get(articleService);
        System.out.println(">>>>>>>>>>>>>>" + o);

        declaredField = articleService.getClass().getDeclaredField("totalCount");
        declaredField.setAccessible(true);
        o = (Number) declaredField.get(articleService);
        System.out.println(">>>>>>>>>>>>>>" + o);
    }


    @Test
    public void testQueryRandom() {
        int count = 4;
        List<Article> articles = articleService.queryRandomArticleList(count);
        assert !articles.isEmpty();
        assert articles.size() == count;
        System.out.println(articles);
    }

    @Test
    public void testQueryArticleDetail() throws InterruptedException {
        Article article = articleService.getArticle(4L);
        long l = article.getViewCount();
        System.out.println(">>>>>>>>>> old ViewCount: " + l);
        ArticleDetail articleDetail = articleService.queryArticleDetail(4L, true);
        TimeUnit.SECONDS.sleep(3);
        article = articleService.getArticle(4L);
        System.out.println(">>>>>>>>>> new ViewCount: " + article.getViewCount());
        assert article.getViewCount() == l + 1;

    }
}
