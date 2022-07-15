package blog.seckill.cc.service.impl;

import blog.seckill.cc.annotation.QueryAtInit;
import blog.seckill.cc.entity.Article;
import blog.seckill.cc.entity.ArticleDetail;
import blog.seckill.cc.mapper.ArticleDetailMapper;
import blog.seckill.cc.mapper.ArticleMapper;
import blog.seckill.cc.mapper.CategoryMapper;
import blog.seckill.cc.mapper.TagMapper;
import blog.seckill.cc.service.ArticleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * description: ArticleServiceImpl <br>
 * date: 2022/7/7 17:05 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private TagMapper tagMapper;

    @Resource
    private ArticleDetailMapper articleDetailMapper;

    // 🔝置顶文章的总数
    @QueryAtInit(tableId = "top_flag", mapperClass = ArticleMapper.class)
    private final Integer topArticleCount = 0;

    @QueryAtInit(tableId = "article_id", mapperClass = ArticleMapper.class)
    private final Integer totalCount = 0;


    @Override
    public List<Article> queryArticleList(int start, int count, boolean queryTop) {
        return articleMapper.selectArticlesWithCategoryAndTags(start, count, queryTop);
    }

    @Override
    public List<Article> queryRandomArticleList(int count) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        // 记录已经生成过了的
        HashSet<Integer> alreadyGen = new HashSet<>();
        for (int i = 0; i < count; i++) {
            int rand = ThreadLocalRandom.current().nextInt(totalCount);
            // 如果生成过了就继续获取
            while (alreadyGen.contains(rand)) {
                rand = ThreadLocalRandom.current().nextInt(totalCount);
            }
            alreadyGen.add(rand);
        }
        queryWrapper.in("article_id", alreadyGen);
        return articleMapper.selectList(queryWrapper);
    }

    @Override
    public ArticleDetail queryArticleDetail(Long articleDetailId) {
        return articleDetailMapper.selectById(articleDetailId);
    }

    @Override
    public Article getArticle(Long articleId) {
        return articleMapper.selectById(articleId);
    }

    @Override
    public Integer getTopArticleCount() {
        return topArticleCount;
    }

    @Override
    public Integer getTotalArticleCount() {
        return totalCount;
    }
}
