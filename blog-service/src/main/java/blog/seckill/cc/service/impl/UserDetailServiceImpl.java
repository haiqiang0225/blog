package blog.seckill.cc.service.impl;

import blog.seckill.cc.domain.LoginUser;
import blog.seckill.cc.entity.User;
import blog.seckill.cc.exception.WrongUsernameOrPasswordException;
import blog.seckill.cc.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * description: UserDetailServiceImpl <br>
 * date: 2022/7/16 18:33 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@Service
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, username);
        User user = userMapper.selectOne(queryWrapper);
        if (log.isDebugEnabled()) {
            log.debug("登陆 user后台数据库信息: {}", user);
        }
        // 用户不存在
        if (user == null) {
            throw new RuntimeException("用户不存在,请检查后重新输入");
        }

        //todo: 查询权限信息

        return new LoginUser(user);
    }
}
