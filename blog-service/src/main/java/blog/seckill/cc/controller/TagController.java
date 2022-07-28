package blog.seckill.cc.controller;

import blog.seckill.cc.annotation.RequestLimit;
import blog.seckill.cc.domain.Result;
import blog.seckill.cc.entity.Tag;
import blog.seckill.cc.service.TagService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * description: TagController <br>
 * date: 2022/7/18 14:20 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@RestController
@RequestMapping("/tag")
@RequestLimit
public class TagController {

    @Resource
    private TagService tagService;

    @GetMapping("/get/all")
    public Result getAll() {
        List<Tag> tags = tagService.queryAllTags();
        Result result = new Result();
        result.put("tags", tags);
        return result;
    }
}
