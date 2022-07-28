package blog.seckill.cc.controller;

import blog.seckill.cc.annotation.RequestLimit;
import blog.seckill.cc.domain.Result;
import blog.seckill.cc.entity.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * description: RegisterController <br>
 * date: 2022/7/16 20:41 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@RestController
@RequestLimit
public class RegisterController {

    @PostMapping("/user/register")
    public Result register(@RequestBody User user) {
        return Result.ok();
    }
}
