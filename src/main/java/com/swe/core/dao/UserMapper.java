package com.swe.core.dao;

import com.swe.core.entity.Permission;
import com.swe.core.entity.Role;
import com.swe.core.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author cbw
 * @since 2019-12-24
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
    /**
     * 通过用户ID查询角色集合
     * @Param  userId 用户ID
     * @Return List<SysRoleEntity> 角色名集合
     */
    List<Role> selectRoleByUserId(Long userId);
    /**
     * 通过用户ID查询权限集合
     * @Param  userId 用户ID
     * @Return List<SysMenuEntity> 角色名集合
     */
    List<Permission> selectPermissionByUserId(Long userId);

}
