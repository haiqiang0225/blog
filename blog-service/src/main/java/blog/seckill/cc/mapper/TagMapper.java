package blog.seckill.cc.mapper;

import blog.seckill.cc.entity.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * description: TagMapper <br>
 * date: 2022/7/7 15:47 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
public interface TagMapper extends BaseMapper<Tag> {

    /**
     * description: queryTagsByArticleId  根据文章id查询tags<br>
     * version: 1.0 <br>
     * date: 2022/7/15 15:07 <br>
     * author: objcat <br>
     *
     * @param articleId 文章id
     * @return java.util.List<blog.seckill.cc.entity.Tag>
     */
    public List<Tag> queryTagsByArticleId(@Param("articleId") Long articleId);

}
