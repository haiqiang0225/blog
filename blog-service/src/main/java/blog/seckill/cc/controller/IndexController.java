package blog.seckill.cc.controller;

import blog.seckill.cc.annotation.RequestLimit;
import blog.seckill.cc.entity.Article;
import blog.seckill.cc.domain.Result;
import blog.seckill.cc.entity.Summary;
import blog.seckill.cc.entity.Tag;
import blog.seckill.cc.service.ArticleService;
import blog.seckill.cc.service.IndexService;
import blog.seckill.cc.service.TagService;
import blog.seckill.cc.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * description: IndexController <br>
 * date: 2022/7/12 20:08 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@RestController
@RequestMapping("/index")
@Slf4j
@RequestLimit
public class IndexController {

    @Resource
    private ArticleService articleService;

    @Resource
    private TagService tagService;

    @Resource
    private IndexService indexService;


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
                              @RequestParam(value = "rand", required = false, defaultValue = "4") int randCount,
                              @RequestParam(value = "total", required = false, defaultValue = "0") Integer total,
                              @RequestParam(value = "version", required = false, defaultValue = "") String version,
                              HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        // 是否6个小时内访问过
        boolean visited = false;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie != null && "visited".equals(cookie.getName())) {
                    visited = true;
                }
            }
        }
        // 说明6个小时内没有访问过
        int totalVisitCount = 0;
        if (!visited) {
            Summary summary = new Summary();
            summary.setVisitDate(new Date());
            summary.setIp(IpUtil.getClientIpAddress(request));
            totalVisitCount = indexService.addTotalVisitCount(summary);
        } else {
            totalVisitCount = indexService.getTotalVisitCount();
        }

        // 文章列表没有变则不需要从数据库取
        String articleListVersion = "";
        if (version != null && version.equals(articleListVersion = indexService.getArticleListVersion())) {
            log.info("文章列表未发生变化, 不再请求数据库,版本: {}", articleListVersion);
            Result ok = Result.ok();
            ok.put("articleListVersion", articleListVersion);
            return ok;
        }
        // 查询文章列表
        List<Article> articles = articleService.queryArticleList(start, count, true);
        Result result = new Result();
        if (total == 0) {
            total = articleService.getTotalArticleCount();
            result.put("count", total);
        }

        // 随便看看
        List<Article> lookRandoms = articleService.queryRandomArticleList(randCount);
        List<Tag> tags = tagService.queryAllTags();

        // 结果
        result.put("totalVisitCount", totalVisitCount);
        result.put("tags", tags);
        result.put("lookRandoms", lookRandoms);
        result.put("data", articles);
        result.put("articleListVersion", articleListVersion);
        return result;
    }


}
