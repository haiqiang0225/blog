package blog.seckill.cc.controller;

import blog.seckill.cc.domain.Result;
import blog.seckill.cc.util.UploadUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * description: FileUpLoadController <br>
 * date: 2022/7/17 21:59 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@RestController
@RequestMapping("/file")
public class FileUpLoadController {

    @Resource
    private UploadUtil uploadUtil;

    @RequestMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile multipartFile) {
        uploadUtil.doUploadAliOSS(multipartFile);
        return Result.ok();
    }
}
