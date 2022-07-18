package blog.seckill.cc.controller;

import blog.seckill.cc.domain.Result;
import blog.seckill.cc.entity.Category;
import blog.seckill.cc.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * description: CategoryController <br>
 * date: 2022/7/18 13:45 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @GetMapping("/get/all")
    public Result getAll() {
        List<Category> categories = categoryService.queryAll();
        Result result = new Result();
        result.put("categories", categories);
        return result;
    }
}
