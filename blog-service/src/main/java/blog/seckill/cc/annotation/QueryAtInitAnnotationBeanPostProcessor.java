package blog.seckill.cc.annotation;

import blog.seckill.cc.entity.Article;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;

/**
 * description: QueryAtInitAnnotationBeanPostProcessor <br>
 * date: 2022/7/8 06:26 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@Component
public class QueryAtInitAnnotationBeanPostProcessor implements BeanPostProcessor {

    @Resource
    private ApplicationContext context;

    // 创建Bean的时候从数据库查询
    @Override
    @SuppressWarnings("rawtypes, unchecked")
    public Object postProcessAfterInitialization(@NotNull Object bean, @NotNull String beanName) throws BeansException {
        Field[] declaredFields = bean.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            QueryAtInit queryAtInit = field.getAnnotation(QueryAtInit.class);
            if (queryAtInit == null) {
                continue;
            }
            field.setAccessible(true);
            // 获取mapper类
            BaseMapper mapper = (BaseMapper) context.getBean(queryAtInit.mapperClass());
            String tableId = StringUtils.hasText(queryAtInit.value()) ? queryAtInit.value() : queryAtInit.tableId();
//            if (!StringUtils.hasText(tableId)) {
//                throw new IllegalArgumentException("@QueryAtInit must have one of the follow values : value/tableId
//                .");
//            }
            QueryWrapper queryWrapper = new QueryWrapper<>();
            // 只针对置顶进行设置
            if ("top_flag".equals(tableId))
                queryWrapper.eq(tableId, 1);
            try {
                if (Long.class.isAssignableFrom(field.getType())) {
                    Long count = mapper.selectCount(queryWrapper);
                    field.set(bean, count);
                } else if (Integer.class.isAssignableFrom(field.getType())) {
                    Long count = mapper.selectCount(queryWrapper);
                    field.set(bean, count.intValue());
                } else {
                    Object o = mapper.selectOne(new QueryWrapper());
                    System.out.println(o);
                    field.set(bean, o);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
