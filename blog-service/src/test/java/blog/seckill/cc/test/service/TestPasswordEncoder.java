package blog.seckill.cc.test.service;

import blog.seckill.cc.BlogServiceMainApp;
import blog.seckill.cc.test.TestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * description: TestPasswordEncoder <br>
 * date: 2022/7/16 19:16 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@SpringBootTest(classes = {BlogServiceMainApp.class})
public class TestPasswordEncoder extends TestBase {
    @Resource
    @Qualifier("passwordEncoder")
    private PasswordEncoder passwordEncoder;


    @Test
    public void testEnc() {
        for (int i = 0; i < 2; i++) {
            String encode = passwordEncoder.encode("123");
            System.out.println("enc >>>>>>>> " + encode);
            System.out.println("match >>>>>> " + passwordEncoder.matches("123", encode));
        }
    }


}
