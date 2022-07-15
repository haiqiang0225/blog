package blog.seckill.cc.service;

/**
 * description: WebInfoService <br>
 * date: 2022/7/14 19:59 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
public interface WebInfoService {

    /**
     * description: getArticleListVersion 获取文章列表的版本,用于判断是否需要从数据库查询所有数据 <br>
     * version: 1.0 <br>
     * date: 2022/7/14 20:00 <br>
     * author: objcat <br>
     *
     * @return java.lang.String
     */
    String getArticleListVersion();

    /**
     * description: getWebVersion 获取网站的版本 <br>
     * version: 1.0 <br>
     * date: 2022/7/14 20:00 <br>
     * author: objcat <br>
     *
     * @return java.lang.Long
     */
    Long getWebVersion();

    /**
     * description: getViewCount 获取网站浏览总数<br>
     * version: 1.0 <br>
     * date: 2022/7/14 20:00 <br>
     * author: objcat <br>
     *
     * @return java.lang.Long
     */
    Long getViewCount();

    /**
     * description: getArticleCount <br>
     * version: 1.0 <br>
     * date: 2022/7/14 20:01 <br>
     * author: objcat <br>
     *
     * @return java.lang.Long
     */
    Long getArticleCount();

}
