package blog.seckill.cc.controller;

import blog.seckill.cc.util.UploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * description: FileUpLoadController <br>
 * date: 2022/7/5 18:53 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@RestController
@Slf4j
public class FileUpLoadController {

    @Resource
    private UploadUtil uploadUtil;

    @PostMapping("/upload")
    public String testUpLoad(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        String fileName = file.getName();
//        if (uploadUtil.doUpload(file, request, "uploadImg")) {
//            fileName = uploadUtil.getUploadFile();
//        } else {
//            fileName = "file";
//        }
        log.info("图片上传成功");
        uploadUtil.doUploadAliOSS();

        return fileName;
    }
}
