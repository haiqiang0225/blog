package blog.seckill.cc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * description: BlogServiceMainApp <br>
 * date: 2022/6/22 17:49 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@SpringBootApplication
@MapperScan("blog.seckill.cc.mapper")
public class BlogServiceMainApp {
    public static void main(String[] args) {
        System.setProperty("jasypt.encryptor.password", System.getenv("JASYPT_PASS"));
        SpringApplication.run(BlogServiceMainApp.class, args);
    }
}
