package com.swe.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author cbw
 * @since 2019-12-24
 */
@ApiModel("用户表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Long id;
    @TableField("nickname")
    @ApiModelProperty(value = "")
    private String nickname;
    @TableField("sno")
    @ApiModelProperty(value = "")
    private String sno;
    @TableField("head")
    @ApiModelProperty(value = "")
    private String head;
    @TableField("password")
    @ApiModelProperty(value = "")
    private String password;
    @TableField("sex")
    @ApiModelProperty(value = "")
    private Boolean sex;
    @TableField("campus")
    @ApiModelProperty(value = "")
    private Integer campus;
    @TableField("phone")
    @ApiModelProperty(value = "")
    private String phone;
    @TableField("status")
    @ApiModelProperty(value = "")
    private String status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public Integer getCampus() {
        return campus;
    }

    public void setCampus(Integer campus) {
        this.campus = campus;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
        ", id=" + id +
        ", nickname=" + nickname +
        ", sno=" + sno +
        ", head=" + head +
        ", password=" + password +
        ", sex=" + sex +
        ", campus=" + campus +
        ", phone=" + phone +
        ", status=" + status +
        "}";
    }
}
