package blog.seckill.cc.annotation;

import java.lang.annotation.*;

/**
 * description: RequestLimit <br>
 * date: 2022/7/28 20:27 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@Inherited
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestLimit {
    // 时间窗
    int second() default 60;

    // 最大请求次数
    int maxCount() default 40;

    // 新的请求到来,是否刷新redis中key的过期时间
    boolean refreshExpireTime() default true;
}
