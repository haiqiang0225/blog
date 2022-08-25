package blog.seckill.cc.mapper;

import blog.seckill.cc.entity.Article;
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
}
