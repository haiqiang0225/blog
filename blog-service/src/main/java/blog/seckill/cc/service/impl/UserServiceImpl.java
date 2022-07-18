package blog.seckill.cc.service.impl;

import blog.seckill.cc.mapper.UserMapper;
import blog.seckill.cc.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * description: UserServiceImpl <br>
 * date: 2022/7/7 14:24 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;


}
