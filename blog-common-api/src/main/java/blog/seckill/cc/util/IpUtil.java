package blog.seckill.cc.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * description: IpUtil IP工具类<br>
 * date: 2022/7/28 19:40 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
public class IpUtil {

    private static final String[] HEADERS_TO_TRY = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR",
            "X-Real-IP"};


    /**
     * description: getClientIpAddress 获取请求的IP<br>
     * version: 1.0 <br>
     * date: 2022/7/28 19:46 <br>
     * author: objcat <br>
     *
     * @param request HttpServletRequest
     * @return java.lang.String ip地址
     */
    public static String getClientIpAddress(HttpServletRequest request) {
        for (String header : HEADERS_TO_TRY) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        return request.getRemoteAddr();
    }

    public static boolean containsCookie(String key, HttpServletRequest request) {
        if (key == null) {
            return false;
        }
        Cookie[] cookies = request.getCookies();
        // 是否6个小时内访问过
        boolean c = false;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie != null && key.equals(cookie.getName())) {
                    c = true;
                }
            }
        }
        return c;
    }

}
