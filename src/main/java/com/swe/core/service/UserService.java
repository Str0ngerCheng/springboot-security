package com.swe.core.service;

import com.swe.core.entity.Permission;
import com.swe.core.entity.Role;
import com.swe.core.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author cbw
 * @since 2019-12-24
 */
public interface UserService extends IService<User> {
    /**
     * 根据用户名查询实体
     * @Param  username 用户名
     * @Return SysUserEntity 用户实体
     */
    User selectUserByName(String username);
    /**
     * 根据用户ID查询角色集合
     * @Param  userId 用户ID
     * @Return List<SysRoleEntity> 角色名集合
     */
    List<Role> selectRoleByUserId(Long userId);
    /**
     * 根据用户ID查询权限集合
     * @Param  userId 用户ID
     * @Return List<SysMenuEntity> 角色名集合
     */
    List<Permission> selectPermissionByUserId(Long userId);

}
