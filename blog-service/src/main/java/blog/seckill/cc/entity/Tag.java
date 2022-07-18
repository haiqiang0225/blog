package blog.seckill.cc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * description: Tag <br>
 * date: 2022/7/7 14:51 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_tags")
@ToString
public class Tag {

    // 标签id
    @TableId(type = IdType.AUTO)
    private Long tagId;

    // 名称
    private String name;

    // 别名
    private String alias;

    // 描述
    private String description;
}
