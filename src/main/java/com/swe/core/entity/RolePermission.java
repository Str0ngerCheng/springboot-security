package com.swe.core.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 角色权限表
 * </p>
 *
 * @author cbw
 * @since 2019-12-24
 */
@ApiModel("角色权限表")
public class RolePermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("role_id")
    @ApiModelProperty(value = "")
    private Long roleId;
    @TableField("permission_id")
    @ApiModelProperty(value = "")
    private Long permissionId;


    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    @Override
    public String toString() {
        return "RolePermission{" +
        ", roleId=" + roleId +
        ", premissionId=" + permissionId +
        "}";
    }
}
