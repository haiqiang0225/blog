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
 * description: User <br>
 * date: 2022/7/7 14:13 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_user")
@ToString
public class User {

    // 用户id
    @TableId(type = IdType.AUTO)
    Long userId;

    // 用户ip
    String userIp;

    // 用户ipv6
    String userIpv6;

    // 用户密码
    //todo: 使用加密算法
    String userPassword;

    // 用户邮箱
    String userEmail;

    // 用户头像的Path,用于从阿里云oss读取时使用
    String userProfilePhotoPath;

    // 注册时间
    Date userRegisterTime;

    // 手机号
    String userPhoneNumber;

    // 昵称
    String userNickname;

    // 用户名
    String userName;

}
