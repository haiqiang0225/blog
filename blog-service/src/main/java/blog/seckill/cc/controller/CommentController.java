package blog.seckill.cc.controller;

import blog.seckill.cc.annotation.RequestLimit;
import blog.seckill.cc.domain.Result;
import blog.seckill.cc.entity.Comment;
import blog.seckill.cc.service.CommentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * description: CommentController <br>
 * date: 2022/7/17 17:38 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@RestController
@RequestMapping("/comment")
@RequestLimit
public class CommentController {

    @Resource
    private CommentService commentService;

    @PostMapping("/upload")
    public Result upload(Comment comment, HttpServletRequest request) {
        return commentService.insertComment(comment);
    }
}

