package com.yaobanTech.springcloud.pojos;

import java.util.Date;

/**
 * @author zhoufei
 * @version 1.0
 * @description 部门实体类
 * @date 2018/10/29 17:18
 */
public class Dept {
    /**
     * null
     */
    private Integer id;

    /**
     * 集团ID（预留数据隔离）
     */
    private Integer groupId;

    /**
     * 父ID
     */
    private Integer parentId;

    /**
     * 路径
     */
    private String path;

    /**
     * 部门等级
     */
    private String level;

    /**
     * 部门类别
     */
    private String type;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 部门别名
     */
    private String aliasName;

    /**
     * 部门描述
     */
    private String desc;

    /**
     * 排序
     */
    private Integer orderNum;

    /**
     * null
     */
    private Date createTime;

    /**
     * null
     */
    private Date updateTime;

    /**
     * 创建人ID
     */
    private Integer createUserId;

    /**
     * 更新人ID
     */
    private Integer updateUserId;

    /**
     * 是否启用
     */
    private Boolean isEnabled;

    /**
     * 是否删除 ‘1‘代表删除 ’0‘代表未删除
     */
    private Boolean isDelete;

    /**
     * 组织类型（0：平台用户组织  1：管理所组织  2：外业）
     */
    private String deptType;

    public Dept() {
    }

    public Dept(Integer id, Integer groupId, Integer parentId, String path, String level, String type, String name, String aliasName, String desc, Integer orderNum, Date createTime, Date updateTime, Integer createUserId, Integer updateUserId, Boolean isEnabled, Boolean isDelete, String deptType) {
        this.id = id;
        this.groupId = groupId;
        this.parentId = parentId;
        this.path = path;
        this.level = level;
        this.type = type;
        this.name = name;
        this.aliasName = aliasName;
        this.desc = desc;
        this.orderNum = orderNum;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.createUserId = createUserId;
        this.updateUserId = updateUserId;
        this.isEnabled = isEnabled;
        this.isDelete = isDelete;
        this.deptType = deptType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public String getDeptType() {
        return deptType;
    }

    public void setDeptType(String deptType) {
        this.deptType = deptType;
    }
}