package blog.seckill.cc.test.mapper;

import blog.seckill.cc.test.TestBase;
import blog.seckill.cc.BlogServiceMainApp;
import blog.seckill.cc.entity.Article;
import blog.seckill.cc.entity.ArticleDetail;
import blog.seckill.cc.mapper.ArticleDetailMapper;
import blog.seckill.cc.mapper.ArticleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

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

    @Test
    public void testSelectArticlesWithCategoryAndTags() {
        List<Article> articles = articleMapper.selectArticlesWithCategoryAndTags(0, 10, true);
        System.out.println(">>>>>>>>>>>" + articles);
        System.out.println(">>>>>>>>>>>" + articles.size());
    }


    @Test
    public void testAddViewCount() {
        articleMapper.addViewCount(4L, 10L);
    }

    @Test
    public void testSearch() {
        List<Article> spring = articleMapper.searchArticleWithKeyWord(0, 1000, "Spring");
        assert spring != null;
        System.out.println(">>>>>>>>>" + spring);
    }


    //    @Test
    public void insertArticleForTest() {
        Article article = new Article();
        article.setUserId(0L);
        article.setAbstractTitle("测试文章摘要:");
        article.setViewCount(0L);
        article.setCommentCount(0L);
        article.setLikeCount(0L);
        ArticleDetail articleDetail = new ArticleDetail();
        String content =
                "这少年便是闰土。我认识他时，也不过十多岁，离现在将有三十年了；那时我的父亲还在世，家景也好，" +
                        "我正是一个少爷。那一年，我家是一件大祭祀的值年。这祭祀，说是三十多年才能轮到一回，" +
                        "所以很郑重。正（zhēng）月里供像，供品很多，祭器很讲究，拜的人也很多，祭器也很要防偷去。" +
                        "我家只有一个忙月（我们这里给人做工的分三种：整年给一定人家做工的叫长工；按日给人做工" +
                        "的叫短工；自己也种地，只在过年过节以及收租时候来给一定的人家做工的称忙月），忙不过来" +
                        "，他便对父亲说，可以叫他的儿子闰土来管祭器的。\n" +
                        "#   Vue3笔记\n" +
                        "\n" +
                        "## 脚手架文件结构\n" +
                        "\n" +
                        "\t├── node_modules \n" +
                        "\t├── public\n" +
                        "\t│   ├── favicon.ico: 页签图标\n" +
                        "\t│   └── index.html: 主页面\n" +
                        "\t├── src\n" +
                        "\t│   ├── assets: 存放静态资源\n" +
                        "\t│   │   └── logo.png\n" +
                        "\t│   │── component: 存放组件\n" +
                        "\t│   │   └── HelloWorld.vue\n" +
                        "\t│   │── App.vue: 汇总所有组件\n" +
                        "\t│   │── main.js: 入口文件\n" +
                        "\t├── .gitignore: git版本管制忽略的配置\n" +
                        "\t├── babel.config.js: babel的配置文件\n" +
                        "\t├── package.json: 应用包配置文件 \n" +
                        "\t├── README.md: 应用描述文件\n" +
                        "\t├── package-lock.json：包版本控制文件\n" +
                        "```java\n" +
                        "public class Lamborghini implements Car{\n" +
                        "    @Override\n" +
                        "    public void name() {\n" +
                        "        System.out.println(\"兰博基尼\");\n" +
                        "    }\n" +
                        "}\n" +
                        "public class Porsche implements Car{\n" +
                        "    @Override\n" +
                        "    public void name() {\n" +
                        "        System.out.println(\"保时捷\");\n" +
                        "    }\n" +
                        "}\n" +
                        "```";

        for (int i = 0; i < 20; i++) {
            long id = (long) (i + 1);
            article.setArticleId(id);
            article.setTitle("测试文章" + i);

            articleDetail.setArticleDetailId(id);
            articleDetail.setContent(content);
            articleDetail.setArticleId(article.getArticleId());
            articleDetail.setContentType("md");

            article.setArticleDetailId(articleDetail.getArticleDetailId());

            articleMapper.insert(article);
            articleDetailMapper.insert(articleDetail);
        }
    }


}
