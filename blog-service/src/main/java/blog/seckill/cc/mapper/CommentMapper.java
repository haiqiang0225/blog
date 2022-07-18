package blog.seckill.cc.mapper;

import blog.seckill.cc.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * description: CommentMapper <br>
 * date: 2022/7/7 15:41 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * description: queryCommentsWithUserInfoByArticleId 根据用户ID查询评论,包含用户信息<br>
     * version: 1.0 <br>
     * date: 2022/7/15 20:19 <br>
     * author: objcat <br>
     *
     * @param articleId
     * @return java.util.List<blog.seckill.cc.entity.Comment>
     */
    List<Comment> queryCommentsWithUserInfoByArticleId(@Param("articleId") Long articleId);
}
