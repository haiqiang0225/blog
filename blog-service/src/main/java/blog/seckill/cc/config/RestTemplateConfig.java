package blog.seckill.cc.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * description: RestTemplateConfig <br>
 * date: 2022/10/25 16:24 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
@Configuration
public class RestTemplateConfig {

    @ConditionalOnMissingBean(RestTemplate.class)
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
