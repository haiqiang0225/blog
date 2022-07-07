package blog.seckill.cc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * description: Tags <br>
 * date: 2022/7/7 14:51 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_tags")
@ToString
public class Tags {

    // 标签id
    @TableId
    public Long tagId;

    // 名称
    public String name;

    // 别名
    public String alias;

    // 描述
    public String description;
}
