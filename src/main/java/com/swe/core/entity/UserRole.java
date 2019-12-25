package com.swe.core.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 用户角色表
 * </p>
 *
 * @author cbw
 * @since 2019-12-24
 */
@ApiModel("用户角色表")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("user_id")
    @ApiModelProperty(value = "")
    private Long userId;
    @TableField("role_id")
    @ApiModelProperty(value = "")
    private Long roleId;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "UserRole{" +
        ", userId=" + userId +
        ", roleId=" + roleId +
        "}";
    }
}
