package blog.seckill.cc.controller;

import blog.seckill.cc.domain.Result;
import blog.seckill.cc.entity.*;
import blog.seckill.cc.service.ArticleService;
import blog.seckill.cc.service.CommentService;
import blog.seckill.cc.service.TagService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * description: ArticleController <br>
 * date: 2022/7/7 19:17 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @Resource
    private TagService tagService;


    @Resource
    private CommentService commentService;

    /**
     * description: getArticles 获取文章列表<br>
     * version: 1.0 <br>
     * date: 2022/7/12 17:04 <br>
     * author: objcat <br>
     *
     * @param start 开始的下标
     * @param count 本次返回的总数
     * @param total 总条数
     * @return blog.seckill.cc.domain.Result
     */
    @GetMapping("/get")
    public Result getArticles(@RequestParam("start") int start,
                              @RequestParam("count") int count,
                              @RequestParam(value = "total", required = false, defaultValue = "0") Integer total) {
        List<Article> articles = articleService.queryArticleList(start, count, true);
        Result result = new Result();
        if (total == 0) {
            total = articleService.getTotalArticleCount();
            result.put("count", total);
        }
        result.put("data", articles);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @GetMapping("/getDetails")
    public Result getArticleDetails(
            @RequestParam("articleId") Long articleId,
            @RequestParam(value = "queryArticle", defaultValue = "false") boolean query) {
        // 查询文章相关信息
        Article article = articleService.getArticle(articleId);
        ArticleDetail articleDetail = articleService.queryArticleDetail(articleId);
        List<Tag> tags = tagService.queryArticleTags(articleId);
        List<Comment> comments = commentService.queryCommentsWithUserInfoByArticleId(articleId);
        article.setTags(tags);
        Result result = new Result();

        result.put("comments", comments);
        result.put("article", article);
        result.put("articleDetail", articleDetail);
        return result;
    }

}
