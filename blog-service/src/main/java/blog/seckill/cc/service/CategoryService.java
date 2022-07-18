package blog.seckill.cc.service;

import blog.seckill.cc.entity.Category;

import java.util.List;

/**
 * description: CategoryService <br>
 * date: 2022/7/8 19:34 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
public interface CategoryService {
    /**
     * description: queryAll 查询所有的分类<br>
     * version: 1.0 <br>
     * date: 2022/7/18 13:48 <br>
     * author: objcat <br>
     *
     * @param
     * @return java.util.List<blog.seckill.cc.entity.Category>
     */
    List<Category> queryAll();
}
