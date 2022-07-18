package blog.seckill.cc.service.impl;

import blog.seckill.cc.entity.Category;
import blog.seckill.cc.mapper.ArticleMapper;
import blog.seckill.cc.mapper.CategoryMapper;
import blog.seckill.cc.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * description: CategoryServiceImpl <br>
 * date: 2022/7/18 13:47 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    private volatile boolean cached = false;

    // 缓存所有分类的count
    private final ConcurrentHashMap<Long, Long> countCache = new ConcurrentHashMap<>();

    @Override
    public List<Category> queryAll() {
        List<Category> categories = categoryMapper.selectList(new QueryWrapper<>());
        // 如果已经缓存了,直接从缓存里查
        if (cached) {
            // 设置
            categories.forEach(category -> category.setCount(countCache.get(category.getCategoryId())));
        }
        // 否则读数据库并缓存
        else {
            // 确保只有一个线程去查数据库
            synchronized (this) {
                if (!cached) {
                    categories.forEach(category -> {
                        // 查询数据库并存储
                        Long c = categoryMapper.queryArticleCountThisCategory(category.getCategoryId());
                        // 缓存
                        countCache.put(category.getCategoryId(), c);
                        category.setCount(c);
                    });
                    cached = true;
                }
                // 后续线程直接读缓存
                else {
                    categories.forEach(category -> category.setCount(countCache.get(category.getCategoryId())));
                }
            }
        }
        return categories;
    }
}
