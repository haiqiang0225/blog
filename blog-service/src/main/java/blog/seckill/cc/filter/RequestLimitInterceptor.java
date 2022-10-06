package blog.seckill.cc.filter;

import blog.seckill.cc.annotation.RequestLimit;
import blog.seckill.cc.domain.Result;
import blog.seckill.cc.enums.RedisKeyPrefix;
import blog.seckill.cc.util.IpUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * description: RequestLimitInterceptor <br>
 * date: 2022/7/28 20:34 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@Component
@Slf4j
public class RequestLimitInterceptor implements HandlerInterceptor {

    private static final int DEFAULT_LIMIT = 40;

    private static final int DEFAULT_SEC = 60;

    @Resource
    private RedisTemplate<String, Integer> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            // 方法上的注解优先级高于类上的注解
            RequestLimit requestLimit = method.getAnnotation(RequestLimit.class);
            // 如果获取不到,则说明没有被限制
            if (requestLimit == null &&
                    (requestLimit = method.getDeclaringClass().getAnnotation(RequestLimit.class)) == null) {
                return true;
            }
            if (shouldLimitAccess(request, requestLimit)) {
                responsePrint(response, "访问过快,请等待一分钟再访问!");
                return false;
            }
        }
        return true;
    }

    private boolean shouldLimitAccess(HttpServletRequest request, RequestLimit requestLimit) {
        // 解析
        int maxCount = requestLimit.maxCount();
        if (maxCount == 0) {
            maxCount = DEFAULT_LIMIT;
        }
        int sec = requestLimit.second();
        boolean refreshExpireTime = requestLimit.refreshExpireTime();
        if (sec == 0) {
            sec = DEFAULT_SEC;
        }
        String key = generateKeyForRedis(request);
        // 获取当前的访问次数
        Integer curCount = redisTemplate.opsForValue().get(key);
        log.debug("cur should limit count: {}", curCount);
        if (curCount == null) {
            redisTemplate.opsForValue().set(key, 1, sec, TimeUnit.SECONDS);
        } else if (curCount < maxCount) {
            // 需要刷新时间
            if (refreshExpireTime) {
                redisTemplate.opsForValue().set(key, curCount + 1, sec, TimeUnit.SECONDS);
            }
            // 不需要刷新过期时间
            else {
                redisTemplate.opsForValue().increment(key);
            }
        } else {
            // 访问受限
            log.info("redis key : {}, 访问受限", key);
            return true;
        }

        // 判断首页是否被禁止访问,是的话全部资源禁止访问
        String indexPageKey = getGlobalIndexKey(request);
        curCount = redisTemplate.opsForValue().get(indexPageKey);
        return curCount != null && curCount >= maxCount;
    }


    public String generateKeyForRedis(HttpServletRequest request) {
        return RedisKeyPrefix.REQUEST_LIMIT + ":" + IpUtil.getClientIpAddress(request) + ":" + request.getRequestURI();
    }

    public String getGlobalIndexKey(HttpServletRequest request) {
        return RedisKeyPrefix.REQUEST_LIMIT + ":" + IpUtil.getClientIpAddress(request) + ":" + "/index/get";
    }

    private void responsePrint(HttpServletResponse response, String msg) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try (PrintWriter out = response.getWriter()) {
            out.append(JSONObject.toJSON(Result.error(403, msg)).toString());
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
