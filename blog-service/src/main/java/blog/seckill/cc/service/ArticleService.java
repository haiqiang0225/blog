package blog.seckill.cc.service;

import blog.seckill.cc.entity.Article;
import blog.seckill.cc.entity.ArticleDetail;
import blog.seckill.cc.entity.Category;

import java.util.List;

/**
 * description: ArticleService <br>
 * date: 2022/7/7 17:05 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
public interface ArticleService {

    /**
     * description: queryArticleList 分页查询 <br>
     * version: 1.0 <br>
     * date: 2022/7/8 07:19 <br>
     * author: hq <br>
     *
     * @param start    开始位置
     * @param count    查询总数
     * @param queryTop 查询置顶
     * @return java.util.List<blog.seckill.cc.entity.Article>
     */
    List<Article> queryArticleList(int start, int count, boolean queryTop);


    /**
     * description: queryRandomArticleList 获取推荐的文章列表<br>
     * version: 1.0 <br>
     * date: 2022/7/14 14:27 <br>
     * author: objcat <br>
     *
     * @param count 返回的文章总数
     * @return java.util.List<blog.seckill.cc.entity.Article>
     */
    List<Article> queryRandomArticleList(int count);


    /**
     * description: queryArticleDetail 获取文章详情<br>
     * version: 1.0 <br>
     * date: 2022/7/8 07:23 <br>
     * author: hq <br>
     *
     * @param articleDetailId 文章详情的Id
     * @return blog.seckill.cc.entity.ArticleDetail
     */
    ArticleDetail queryArticleDetail(Long articleDetailId);


    ArticleDetail queryArticleDetail(Long articleDetailId, boolean addViewCount);

    /**
     * description: getArticle 获取文章摘要信息<br>
     * version: 1.0 <br>
     * date: 2022/7/15 14:44 <br>
     * author: objcat <br>
     *
     * @param articleId id
     * @return blog.seckill.cc.entity.Article
     */
    Article getArticle(Long articleId);


    /**
     * description: getTopArticleCount 获取置顶文章的总数<br>
     * version: 1.0 <br>
     * date: 2022/7/8 07:20 <br>
     * author: hq <br>
     *
     * @return java.lang.Integer
     */
    Integer getTopArticleCount();

    Integer getTotalArticleCount();
}
