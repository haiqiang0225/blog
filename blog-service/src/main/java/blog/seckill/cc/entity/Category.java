package blog.seckill.cc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * description: Category <br>
 * date: 2022/7/7 14:48 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_category")
@ToString
public class Category {

    // 分类的id
    @TableId(type = IdType.AUTO)
    private Long categoryId;

    // 分类名
    private String name;

    //  别名
    private String alias;

    //  描述信息
    private String description;

    //  父类id
    private Long parentId;

    // 该分类下文章的总数量
    @TableField(exist = false)
    private Long count;
}
