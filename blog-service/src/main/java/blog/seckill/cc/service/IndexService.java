package blog.seckill.cc.service;

import blog.seckill.cc.entity.Summary;

/**
 * description: IndexService <br>
 * date: 2022/7/14 15:34 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
public interface IndexService {
    /**
     * description: getTotalVisitCount  获取总访问量<br>
     * version: 1.0 <br>
     * date: 2022/7/14 15:35 <br>
     * author: objcat <br>
     *
     * @return int
     */
    int getTotalVisitCount();

    /**
     * description: addTotalVisitCount 增加总访问量<br>
     * version: 1.0 <br>
     * date: 2022/7/14 15:35 <br>
     * author: objcat <br>
     *
     * @return int
     */
    int addTotalVisitCount(Summary summary);

    /**
     * description: getArticleListVersion 获取文章列表的版本<br>
     * version: 1.0 <br>
     * date: 2022/7/14 20:45 <br>
     * author: objcat <br>
     *
     * @param
     * @return java.lang.String
     */
    String getArticleListVersion();
}
