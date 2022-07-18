package blog.seckill.cc.mapper;

import blog.seckill.cc.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
}
