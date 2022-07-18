package blog.seckill.cc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * description: WebSummary <br>
 * date: 2022/7/14 19:54 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_web_info")
@ToString
public class WebInfo {
    @TableId
    private Long webVersion;

    private Long articleCount;

    private Long viewCount;

    private String articleVersion;
}
