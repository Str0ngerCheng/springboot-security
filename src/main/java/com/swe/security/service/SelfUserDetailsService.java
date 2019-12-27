package com.swe.security.service;

import com.swe.core.entity.User;
import com.swe.core.service.UserService;
import com.swe.security.entity.SelfUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * SpringSecurity用户的业务实现
 * @Author cbw
 */
@Component
public class SelfUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    /**
     * 查询用户信息
     * @Author cbw
     * @Param  username  用户名
     * @Return UserDetails SpringSecurity用户信息
     */
    @Override
    public SelfUserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户信息
        User user =userService.selectUserByName(username);
        if (user!=null){
            // 组装参数
            SelfUserEntity selfUserEntity = new SelfUserEntity();
            selfUserEntity.setUserId(user.getId());
            selfUserEntity.setUsername(user.getNickname());
            selfUserEntity.setPassword(user.getPassword());
            selfUserEntity.setStatus(user.getStatus());
            return selfUserEntity;
        }
        return null;
    }
}