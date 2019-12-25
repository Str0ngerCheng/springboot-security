package com.swe.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.swe.core.entity.Permission;
import com.swe.core.entity.Role;
import com.swe.core.entity.User;
import com.swe.core.dao.UserMapper;
import com.swe.core.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author cbw
 * @since 2019-12-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User selectUserByName(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getNickname,username);
        return this.baseMapper.selectOne(queryWrapper);
    }

    @Override
    public List<Role> selectRoleByUserId(Long userId) {
        return this.baseMapper.selectRoleByUserId(userId);
    }

    @Override
    public List<Permission> selectPermissionByUserId(Long userId) {

        return this.baseMapper.selectPermissionByUserId(userId);
    }
}
