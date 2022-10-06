package blog.seckill.cc.mapper;

import blog.seckill.cc.entity.Article;
import blog.seckill.cc.entity.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * description: ArticleMapper <br>
 * date: 2022/7/7 15:10 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
public interface ArticleMapper extends BaseMapper<Article> {
    /**
     * description: selectArticlesWithCategoryAndTags <br>
     * version: 1.0 <br>
     * date: 2022/7/12 14:46 <br>
     * author: objcat <br>
     *
     * @param start
     * @param count
     * @return java.util.List<blog.seckill.cc.entity.Article>
     */
    List<Article> selectArticlesWithCategoryAndTags(@Param("start") int start,
                                                    @Param("count") int count,
                                                    @Param("queryTop") boolean queryTop);

    /**
     * description: addViewCount 添加浏览量 <br>
     * version: 1.0 <br>
     * date: 2022/8/24 22:21 <br>
     * author: objcat <br>
     *
     * @param articleId
     * @param add
     */
    void addViewCount(@Param("articleId") Long articleId, @Param("add") Long add);

    /**
     * description: searchArticleWithKeyWord 按照关键字查找文章列表<br>
     * version: 1.0 <br>
     * date: 2022/10/5 15:38 <br>
     * author: haiqiang0225@gmail.com <br>
     *
     * @param start   开始index
     * @param count   查询数量
     * @param keyWord 关键字
     * @return java.util.List<blog.seckill.cc.entity.Article>
     */
    List<Article> searchArticleWithKeyWord(@Param("start") int start,
                                           @Param("count") int count,
                                           @Param("keyWord") String keyWord);

    /**
     * description: selectArticlesByTag 根据标签tag查询文章列表 <br>
     * version: 1.0 <br>
     * date: 2022/10/6 17:27 <br>
     * author: haiqiang0225@gmail.com <br>
     *
     * @param tagId
     * @return java.util.List<blog.seckill.cc.entity.Article>
     */
    List<Article> selectArticlesByTag(@Param("start") int start,
                                      @Param("count") int count,
                                      @Param("tagId") Long tagId);

    /**
     * description: selectArticlesByCategory 根据分类查询文章列表 <br>
     * version: 1.0 <br>
     * date: 2022/10/6 17:28 <br>
     * author: haiqiang0225@gmail.com <br>
     *
     * @param categoryId
     * @return java.util.List<blog.seckill.cc.entity.Article>
     */
    List<Article> selectArticlesByCategory(@Param("start") int start,
                                           @Param("count") int count,
                                           @Param("categoryId") Long categoryId);
}
