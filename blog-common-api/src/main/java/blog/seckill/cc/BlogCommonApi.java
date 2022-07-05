package blog.seckill.cc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * description: BlogAppMain <br>
 * date: 2022/6/21 22:22 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@SpringBootApplication
@SuppressWarnings("resource")
public class BlogAppMain {
    public static void main(String[] args) {
        System.setProperty("jasypt.encryptor.password", System.getenv("JASYPT_PASS"));
        SpringApplication.run(BlogAppMain.class, args);
    }
}
