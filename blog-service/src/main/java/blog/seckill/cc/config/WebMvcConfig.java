package blog.seckill.cc.config;

import blog.seckill.cc.filter.RequestLimitInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * description: WebMvcConfig <br>
 * date: 2022/7/28 20:29 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private RequestLimitInterceptor requestLimitInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("添加拦截器: {}",requestLimitInterceptor.getClass().getName());
        registry.addInterceptor(requestLimitInterceptor);
    }
}
