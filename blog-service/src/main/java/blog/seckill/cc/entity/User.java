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
    private Long userId;

    // 用户ip
    private String userIp;

    // 用户ipv6
    private String userIpv6;

    // 用户密码
    //todo: 使用加密算法
    private String userPassword;

    // 用户邮箱
    private String userEmail;

    // 用户头像的Path,用于从阿里云oss读取时使用
    private String userProfilePhotoPath;

    // 注册时间
    private Date userRegisterTime;

    // 手机号
    private String userPhoneNumber;

    // 昵称
    private String userNickname;

    // 用户名
    private String userName;

    // 用户是否被禁用
    private Boolean userEnabled;

    // 上次登录时间
    private Date userLastLoginTime;

    // 创建该用户的用户id
    private Long userCreateUserId;

    // 更新该用户信息的用户id
    private Long userUpdateUserId;

    // 删除标志
    private Boolean userDelFlag;

    // 过期标志
    private Boolean userExpiredFlag;

    // 用户证书是否过期
    private Boolean userCredentialsExpiredFlag;

    public void erasePrivateInfo() {
        this.userPassword = null;
        this.userPhoneNumber = null;
        this.userEmail = null;
    }
}
