package blog.seckill.cc.test.mapper;

import blog.seckill.cc.test.TestBase;
import blog.seckill.cc.BlogServiceMainApp;
import blog.seckill.cc.entity.User;
import blog.seckill.cc.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * description: UserMapperTest <br>
 * date: 2022/7/7 14:25 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@SpringBootTest(classes = {BlogServiceMainApp.class})
public class UserMapperTest extends TestBase {

    @Resource
    private UserMapper userMapper;

    @Test
    public void testUserMapper() {
        User user = userMapper.selectById(0);
        assert user != null;
        System.out.println(user);
    }
}
