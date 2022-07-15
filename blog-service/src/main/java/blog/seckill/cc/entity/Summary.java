package blog.seckill.cc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * description: Summary 统计信息<br>
 * date: 2022/7/14 18:39 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_blog_summary")
@ToString
public class Summary {
    @TableId
    Long id;

    // 访问时间
    Date visitDate;

    // 访问源所在实际物理地址
    String visitorLocation;

    // 访问来源
    String visitRoute;

    // 访问设备
    String visitDevice;

    // ipv4地址
    String ip;

    // ipv6地址
    String ipv6;
}
