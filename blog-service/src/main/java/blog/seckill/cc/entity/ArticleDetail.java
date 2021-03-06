package blog.seckill.cc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * description: ArticleDetail <br>
 * date: 2022/7/7 14:48 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_article_detail")
@ToString
public class ArticleDetail {

    // 文章详情id
    @TableId(type = IdType.AUTO)
    private Long articleDetailId;

    // 内容
    private String content;


    // 文章id
    private Long articleId;

    // 文章内容的形式 : md, html
    private String contentType;

}
