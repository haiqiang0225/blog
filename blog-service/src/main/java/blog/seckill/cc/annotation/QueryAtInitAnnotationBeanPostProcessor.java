package blog.seckill.cc.annotation;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
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
@Slf4j
public class QueryAtInitAnnotationBeanPostProcessor implements SmartInitializingSingleton, BeanPostProcessor {

    @Resource
    private ApplicationContext context;


    // 创建Bean的时候从数据库查询
    @Override
    @SuppressWarnings("rawtypes, unchecked")
    // todo: 该方法可能需要重构一下, 优先级较低. 现在的问题是增强CGLIB类时,只能增强int的field 且必须要有setter方法.
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Field[] declaredFields = ClassUtils.getUserClass(bean.getClass()).getDeclaredFields();
        for (Field field : declaredFields) {
            QueryAtInit queryAtInit = field.getAnnotation(QueryAtInit.class);
            if (queryAtInit == null) {
                continue;
            }
            field = ReflectionUtils.findField(bean.getClass(), field.getName());
            assert field != null;
            ReflectionUtils.makeAccessible(field);
            // 获取mapper类
            BaseMapper mapper = (BaseMapper) context.getBean(queryAtInit.mapperClass());
            String tableId = StringUtils.hasText(queryAtInit.value()) ? queryAtInit.value() : queryAtInit.tableId();
            log.info("QueryAtInit 增强: {}, filed: {}", bean.getClass(), field.getName());
            QueryWrapper queryWrapper = new QueryWrapper<>();
            // 只针对置顶进行设置
            if ("top_flag".equals(tableId))
                queryWrapper.eq(tableId, 1);
            // 注入属性
            try {
                // 如果是CGLIB增强的类, 就用setter注入
                if (bean.getClass().getName().contains("CGLIB")) {
                    String methodName = getSetterName(field.getName());
                    Long count = mapper.selectCount(queryWrapper);
                    bean.getClass().getDeclaredMethod(methodName, int.class).invoke(bean, count.intValue());
                } else if (Long.class.isAssignableFrom(field.getType())) {
                    Long count = mapper.selectCount(queryWrapper);
                    ReflectionUtils.setField(field, bean, count);
                } else if (Integer.class.isAssignableFrom(field.getType())) {
                    Long count = mapper.selectCount(queryWrapper);
                    ReflectionUtils.setField(field, bean, count.intValue());
                } else {
                    Object o = mapper.selectOne(new QueryWrapper());
                    ReflectionUtils.setField(field, bean, o);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    public String getSetterName(String rowName) {
        rowName = "set" + rowName.substring(0, 1).toUpperCase() + rowName.substring(1);
        return rowName;
    }

    @Override
    public void afterSingletonsInstantiated() {
    }
}
