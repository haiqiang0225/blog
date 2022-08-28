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

    // 表主键, 暂时只用于置顶项的处理
    String tableId() default "";

    // 用于查询的Mapper的类型
    Class<?> mapperClass();

    // todo:
    // 被代理类的类型
    Class<?> targetClass() default Object.class;
}
