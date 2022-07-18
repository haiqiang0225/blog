package blog.seckill.cc.service.impl;

import blog.seckill.cc.domain.LoginUser;
import blog.seckill.cc.domain.Result;
import blog.seckill.cc.entity.User;
import blog.seckill.cc.service.LoginService;
import blog.seckill.cc.util.JwtUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * description: LoginServiceImpl <br>
 * date: 2022/7/16 20:46 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@Service
public class LoginServiceImpl implements LoginService {


    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Result login(User user) {
        // AuthenticationManager 进行认证
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getUserPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        // 认证失败
        if (authenticate == null) {
            throw new RuntimeException("登陆认证失败");
        }
        // 认证通过
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getUserId().toString();
        String jwt = JwtUtil.createJWT(userId);

        // 用户信息存入redis
        redisTemplate.opsForHash().put(REDIS_KEY, userId, loginUser.getUser());
        //todo: MQ延时消息模拟设置hash field的过期时间

        user = loginUser.getUser();
        user.erasePrivateInfo();
        Result result = new Result();
        result.put("token", jwt);
        result.put("user", user);
        return result;
    }

    @Override
    public Result logout() {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        String userId = authenticationToken.getPrincipal().toString();
        // 删除redis中的值
        redisTemplate.opsForHash().delete(REDIS_KEY, userId);

        return Result.ok("注销成功");
    }

}
