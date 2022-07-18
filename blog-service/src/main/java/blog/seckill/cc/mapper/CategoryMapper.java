package blog.seckill.cc.mapper;

import blog.seckill.cc.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * description: CategoryMapper <br>
 * date: 2022/7/7 15:35 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
public interface CategoryMapper extends BaseMapper<Category> {

    /**
     * description: queryCategoryByArticleId 根据文章的id获取类别/获取文章的类别 <br>
     * version: 1.0 <br>
     * date: 2022/7/8 20:09 <br>
     * author: objcat <br>
     * 
     * @param articleId
     * @return blog.seckill.cc.entity.Category
     */ 
    Category queryCategoryByArticleId(@Param("articleId") Long articleId);
}
