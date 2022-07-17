package blog.seckill.cc.controller;

import blog.seckill.cc.domain.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * description: UserController <br>
 * date: 2022/7/7 14:01 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/upload")
    public Result userUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        if (file == null) {
            return Result.error(400, "未上传文件");
        }

        return null;
    }
}
