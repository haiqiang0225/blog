package blog.seckill.cc.entity;

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
    @TableId
    public Long categoryId;

    // 分类名
    public String name;

    //  别名
    public String alias;

    //  描述信息
    public String description;

    //  父类id
    public Long parentId;
}
