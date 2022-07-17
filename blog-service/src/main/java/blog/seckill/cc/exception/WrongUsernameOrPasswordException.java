package blog.seckill.cc.exception;

/**
 * description: WrongUsernameOrPasswordException <br>
 * date: 2022/7/16 18:46 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
public class WrongUsernameOrPasswordException extends RuntimeException {
    public WrongUsernameOrPasswordException() {
        super("用户名或者密码错误");
    }
}
