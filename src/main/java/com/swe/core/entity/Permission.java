package com.swe.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author cbw
 * @since 2019-12-24
 */
@ApiModel("权限表")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Long id;
    @TableField("Title")
    @ApiModelProperty(value = "")
    private String Title;
    @TableField("Description")
    @ApiModelProperty(value = "")
    private String Description;
    @TableField("slug")
    @ApiModelProperty(value = "")
    private String slug;
    @TableField("http_path")
    @ApiModelProperty(value = "")
    private String httpPath;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getHttpPath() {
        return httpPath;
    }

    public void setHttpPath(String httpPath) {
        this.httpPath = httpPath;
    }

    @Override
    public String toString() {
        return "Permission{" +
        ", id=" + id +
        ", Title=" + Title +
        ", Description=" + Description +
        ", slug=" + slug +
        ", httpPath=" + httpPath +
        "}";
    }
}
