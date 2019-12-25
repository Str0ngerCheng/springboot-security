package com.swe.core.service.impl;

import com.swe.core.entity.UserRole;
import com.swe.core.dao.UserRoleMapper;
import com.swe.core.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author cbw
 * @since 2019-12-24
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
