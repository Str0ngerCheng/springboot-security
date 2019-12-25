package com.swe.core.dao;

import com.swe.core.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author cbw
 * @since 2019-12-24
 */
@Repository
public interface PermissionMapper extends BaseMapper<Permission> {

}
