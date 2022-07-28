package blog.seckill.cc.controller;

import blog.seckill.cc.annotation.RequestLimit;
import blog.seckill.cc.domain.Result;
import blog.seckill.cc.entity.User;
import blog.seckill.cc.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * description: LoginController <br>
 * date: 2022/7/16 20:40 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@RestController
@RequestLimit
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping("/user/login")
    public Result login(User user) {
        return loginService.login(user);
    }


    @RequestMapping("/user/logout")
    public Result logout() {
        return loginService.logout();
    }

}
