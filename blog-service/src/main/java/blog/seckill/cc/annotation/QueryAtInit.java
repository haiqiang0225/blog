package blog.seckill.cc.annotation;

import java.lang.annotation.*;

/**
 * description: QueryAtInit <br>
 * date: 2022/7/8 06:23 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QueryAtInit {

    String value() default "";

    String tableId() default "";

    Class<?> mapperClass();
}
