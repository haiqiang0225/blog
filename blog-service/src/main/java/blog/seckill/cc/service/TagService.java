package blog.seckill.cc.service;

import blog.seckill.cc.entity.Tag;

import java.util.List;

/**
 * description: TagService <br>
 * date: 2022/7/8 19:34 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
public interface TagService {

    /**
     * description: queryAllTags 查询所有的标签<br>
     * version: 1.0 <br>
     * date: 2022/7/14 14:21 <br>
     * author: objcat <br>
     *
     * @return java.util.List<blog.seckill.cc.entity.Tag>
     */
    List<Tag> queryAllTags();

    /**
     * description: queryArticleTags 查询文章的所有标签<br>
     * version: 1.0 <br>
     * date: 2022/7/15 15:05 <br>
     * author: objcat <br>
     *
     * @param articleId 文章的id
     * @return java.util.List<blog.seckill.cc.entity.Tag>
     */
    List<Tag> queryArticleTags(Long articleId);
}
