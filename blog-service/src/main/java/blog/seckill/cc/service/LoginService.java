package blog.seckill.cc.service;

import blog.seckill.cc.domain.Result;
import blog.seckill.cc.entity.User;

/**
 * description: LoginService <br>
 * date: 2022/7/16 20:46 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
public interface LoginService {

    // 存放用户信息的REDIS KEY
    String REDIS_KEY = "login";

    Result login(User user);

    Result logout();
}
