package blog.seckill.cc.controller;

import blog.seckill.cc.domain.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description: TestController <br>
 * date: 2022/6/22 17:50 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@RestController
@RequestMapping("/test")
@Tag(name = "测试接口", description = "测试")
public class TestController {

    @GetMapping("/get")
    @Operation()
    public Result get() {
        return Result.success();
    }

    @RequestMapping("/req")
    public String req() {
        return "";
    }
}
