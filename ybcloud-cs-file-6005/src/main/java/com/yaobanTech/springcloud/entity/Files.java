package com.yaobanTech.springcloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author lijh
 * @since 2020-12-27
 */
@EqualsAndHashCode(callSuper = false)
@TableName("biz_files")
public class Files implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private String name;

    private String pid;

    private String url;

    private String uploadDate;

    /**
     * 备注
     */
    private String remark;

    /**
     * facl:方案材料
     */
    private String type;

    /**
     * 照片/视频
     */
    private String mimeType;

    /**
     * 0：无效 1：有效
     */
    private Integer isvalid;

    public Files() {
    }

    public Files(String name, String pid, String url, String uploadDate, String remark, String type, String mimeType, Integer isvalid) {
        this.name = name;
        this.pid = pid;
        this.url = url;
        this.uploadDate = uploadDate;
        this.remark = remark;
        this.type = type;
        this.mimeType = mimeType;
        this.isvalid = isvalid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Integer getIsvalid() {
        return isvalid;
    }

    public void setIsvalid(Integer isvalid) {
        this.isvalid = isvalid;
    }
}
