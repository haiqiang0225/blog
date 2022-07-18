package blog.seckill.cc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * description: BlogCommonApi <br>
 * date: 2022/6/21 22:22 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@SpringBootApplication
public class BlogCommonApi {
    public static void main(String[] args) {
        System.setProperty("jasypt.encryptor.password", System.getenv("JASYPT_PASS"));
        SpringApplication.run(BlogCommonApi.class, args);
    }
}
