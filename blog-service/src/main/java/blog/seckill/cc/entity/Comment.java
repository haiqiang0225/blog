package blog.seckill.cc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * description: Comment <br>
 * date: 2022/7/7 14:50 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_comment")
@ToString
public class Comment {

    // 评论id
    @TableId(type = IdType.AUTO)
    public Long commentId;

    // 发表评论的用户的id
    public Long userId;

    // 评论的文章的id
    public Long articleId;

    // 点赞数
    public Long likeCount;

    // 发表日期
    public Date createDate;

    // 内容
    public String content;

    // 父评论的id
    public Long parentId;

}
