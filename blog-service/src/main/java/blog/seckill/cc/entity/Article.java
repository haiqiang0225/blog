package blog.seckill.cc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * description: Article <br>
 * date: 2022/7/7 14:47 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_article")
@ToString
public class Article {
    // 文章的id
    @TableId
    Long articleId;

    // 发表文章的用户的id
    Long userId;

    // 文章标题
    String title;

    // 文章摘要
    String abstractTitle;

    // 阅读数
    Long viewCount;

    // 评论数
    Long commentCount;

    // 发表时间
    Date createDate;

    // 更新时间
    Date updateDate;

    // 点赞数
    Long likeCount;

    // 是否置顶
    Boolean topFlag;

    // 文章内容id
    Long articleDetailId;

}
