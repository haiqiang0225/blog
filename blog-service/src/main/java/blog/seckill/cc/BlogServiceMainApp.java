package blog.seckill.cc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * description: BlogServiceMainApp <br>
 * date: 2022/6/22 17:49 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@SpringBootApplication
@SuppressWarnings("resource")
public class BlogServiceMainApp {
    public static void main(String[] args) {
        System.setProperty("jasypt.encryptor.password", System.getenv("JASYPT_PASS"));
        SpringApplication.run(BlogServiceMainApp.class, args);
    }
}
