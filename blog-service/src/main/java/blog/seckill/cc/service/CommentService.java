package blog.seckill.cc.service;

import blog.seckill.cc.entity.Comment;

import java.util.List;

/**
 * description: CommentService <br>
 * date: 2022/7/15 15:57 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
public interface CommentService {
    /**
     * description: queryCommentsByArticleId 根据文章的id查询评论<br>
     * version: 1.0 <br>
     * date: 2022/7/15 15:57 <br>
     * author: objcat <br>
     *
     * @param articleId 文章的id
     * @return java.util.List<blog.seckill.cc.entity.Comment>
     */
    List<Comment> queryCommentsByArticleId(Long articleId);


    /**
     * description: queryCommentsWithUserInfoByArticleId 根据文章的id查询评论,顺便把用户信息查出来<br>
     * version: 1.0 <br>
     * date: 2022/7/15 20:19 <br>
     * author: objcat <br>
     *
     * @param articleId
     * @return java.util.List<blog.seckill.cc.entity.Comment>
     */
    List<Comment> queryCommentsWithUserInfoByArticleId(Long articleId);
}
