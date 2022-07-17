package blog.seckill.cc.test.redis;

import blog.seckill.cc.BlogServiceMainApp;
import blog.seckill.cc.entity.User;
import blog.seckill.cc.test.TestBase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

/**
 * description: RedisTest <br>
 * date: 2022/7/16 17:33 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@SpringBootTest(classes = {BlogServiceMainApp.class})
public class RedisTest extends TestBase {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    @Test
    public void testString() {
        stringRedisTemplate.opsForValue().set("blog0", "wdt");
        String blog0 = stringRedisTemplate.opsForValue().get("blog0");
        System.out.println(blog0);
    }

    @Test
    public void testRedisTemplate() {
        User login = (User) redisTemplate.opsForHash().get("login", "0");
        System.out.println(login);
    }

    @Test
    public void testStringTemplatePut() {
        stringRedisTemplate.opsForHash().put("test", "0", "xxx");

    }
}
