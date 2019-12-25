package com.swe.core.service.impl;

import com.swe.core.entity.Permission;
import com.swe.core.dao.PermissionMapper;
import com.swe.core.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author cbw
 * @since 2019-12-24
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

}
