package blog.seckill.cc.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

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
    @TableId(type = IdType.AUTO)
    private Long articleId;

    // 发表文章的用户的id
    private Long userId;

    // 文章标题
    private String title;

    // 文章摘要
    private String abstractTitle;

    // 阅读数
    private Long viewCount;

    // 评论数
    private Long commentCount;

    // 发表时间
    private Date createDate;

    // 更新时间
    private Date updateDate;

    // 点赞数
    private Long likeCount;

    // 是否置顶
    @OrderBy
    private Boolean topFlag;

    // 文章内容id
    private Long articleDetailId;

    // 文章配图
    private String articleImageUrl;

    // 该文章的分类, 不在数据库中存在
    @TableField(exist = false)
    private Category category;

    // 该文章的标签
    @TableField(exist = false)
    private List<Tag> tags;

}
