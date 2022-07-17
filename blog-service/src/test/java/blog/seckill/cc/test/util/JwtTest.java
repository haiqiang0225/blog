package blog.seckill.cc.test.util;

import blog.seckill.cc.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultJwtBuilder;
import org.junit.jupiter.api.Test;

/**
 * description: JwtTest <br>
 * date: 2022/7/16 19:57 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
public class JwtTest {


    @Test
    public void testJwt() {
        String jwt = JwtUtil.createJWT("123", "enen");
        System.out.println(jwt);
        Claims claims = JwtUtil.parseJWT(jwt);
        String subject = claims.getSubject();
        System.out.println(subject);
    }
}
