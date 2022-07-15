package blog.seckill.cc.aop;

import blog.seckill.cc.event.TotalCountChanged;
import blog.seckill.cc.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;

/**
 * description: ReactiveCountAspect <br>
 * date: 2022/7/7 21:39 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@Aspect
@Component
@Slf4j
public class ReactiveCountAspect {
    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    @Resource
    private ArticleService articleService;

    private volatile Field targetField;


    /**
     * description: afterInsert 插入切面,用于动态获取置顶文章的数量 <br>
     * version: 1.0 <br>
     * date: 2022/7/8 07:29 <br>
     * author: hq <br>
     */ 
    @After("execution(* blog.seckill.cc.mapper.ArticleMapper.insert(..))")
    public void afterInsert() throws NoSuchFieldException {
        if (targetField == null) {
            synchronized (this) {
                if (targetField == null) {
                    targetField = articleService.getClass().getDeclaredField("topArticleCount");
                }
            }
        }
        TotalCountChanged totalCountChanged
                = new TotalCountChanged(this,
                TotalCountChanged.TotalCountChangedType.ADD, targetField, articleService);
        log.info("event published: totalCountChanged, type: ADD");
        applicationEventPublisher.publishEvent(totalCountChanged);
    }

    @After("execution(* blog.seckill.cc.mapper.ArticleMapper.delete*(..))")
    public void afterDelete() throws NoSuchFieldException {
        if (targetField == null) {
            synchronized (this) {
                if (targetField == null) {
                    targetField = articleService.getClass().getDeclaredField("topArticleCount");
                }
            }
        }
        TotalCountChanged totalCountChanged
                = new TotalCountChanged(this,
                TotalCountChanged.TotalCountChangedType.MINUS, targetField, articleService);
        log.info("event published: totalCountChanged, type: Minus");
        applicationEventPublisher.publishEvent(totalCountChanged);
    }
}
