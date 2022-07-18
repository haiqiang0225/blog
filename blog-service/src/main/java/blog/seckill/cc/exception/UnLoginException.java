package blog.seckill.cc.exception;

/**
 * description: UnLoginException <br>
 * date: 2022/7/17 12:33 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
public class UnLoginException extends RuntimeException {

    public UnLoginException() {
        super("用户未登录");
    }
}
