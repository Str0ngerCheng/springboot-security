package com.swe.core.service.impl;

import com.swe.core.entity.Role;
import com.swe.core.dao.RoleMapper;
import com.swe.core.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author cbw
 * @since 2019-12-24
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
