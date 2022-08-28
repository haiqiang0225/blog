package blog.seckill.cc.filter;

import blog.seckill.cc.entity.User;
import blog.seckill.cc.exception.UnLoginException;
import blog.seckill.cc.service.impl.LoginServiceImpl;
import blog.seckill.cc.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * description: JwtAuthenticationTokenFilter <br>
 * date: 2022/7/17 11:26 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 获取token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            // 放行
            filterChain.doFilter(request, response);
            return;
        }

        // 解析token
        Claims claims = JwtUtil.parseJWT(token);
        //todo: token过期处理

        // 从redis获取用户信息
        String redisKey = LoginServiceImpl.REDIS_KEY;
        String redisHashKey = claims.getSubject();

        User user = (User) redisTemplate.opsForHash().get(redisKey, redisHashKey);
        if (user == null) {
            throw new UnLoginException();
        }

        //todo: 第三个参数存放权限信息
        UsernamePasswordAuthenticationToken authenticationToke
                = new UsernamePasswordAuthenticationToken(user.getUserId(), user.getUserPassword(), null);
        // 如果认证成功,存入SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authenticationToke);
        // 放行
        filterChain.doFilter(request, response);
    }
}
