package blog.seckill.cc.enums;

/**
 * description: RedisKeyPrefix <br>
 * date: 2022/7/28 22:06 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
public enum RedisKeyPrefix {
    LOGIN("login"),
    REQUEST_LIMIT("request-limit"),
    ;

    private final String name;

    RedisKeyPrefix(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
