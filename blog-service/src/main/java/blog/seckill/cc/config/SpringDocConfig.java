package blog.seckill.cc.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * description: SpringDocConfig <br>
 * date: 2022/6/22 17:52 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@Configuration
public class SpringDocConfig {

    @Bean
    public GroupedOpenApi blogServiceApi() {
        return GroupedOpenApi.builder()
                .group("blog-service")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public OpenAPI blogServiceApiDesc() {
        return new OpenAPI()
                .info(new Info().title("Blog SpringBoot Service API")
                        .description("Service for Blog Application")
                        .version("v0.0.1-SNAPSHOT")
                        .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0")))
                .externalDocs(new ExternalDocumentation()
                        .description("Blog Wiki Documentation").url(""));

    }

}
