package blog.seckill.cc.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * description: 统一响应 Common Result <br>
 * date: 2022/6/22 19:22 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
public class Result extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    public Result() {
        put("code", 200);
    }

    public static Result error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static Result error(String msg) {
        return error(500, msg);
    }

    public static Result error(int code, String msg) {
        Result r = new Result();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static Result error(Object msg) {
        Result r = new Result();
        r.put("code", 500);
        r.put("msg", msg);
        return r;
    }

    public static Result ok(Object msg) {
        Result r = new Result();
        r.put("msg", msg);
        return r;
    }

    public static Result data(Object data) {
        Result r = new Result();
        r.put("data", data);
        return r;
    }


    public static Result ok(Map<String, Object> map) {
        Result r = new Result();
        r.putAll(map);
        return r;
    }

    public static Result ok() {
        return new Result();
    }

    public static Result success() {
        return new Result().put("code", 200);
    }

    @Override
    public Result put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
