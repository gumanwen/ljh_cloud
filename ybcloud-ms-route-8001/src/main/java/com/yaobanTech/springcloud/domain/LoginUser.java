package com.yaobanTech.springcloud.domain;

import java.util.Date;
import java.util.List;

public class LoginUser {
    public LoginUser() {
    }

    public LoginUser(Integer id, String name, String loginname, String password, String sex, Integer deptid, String remark, Boolean isAnalyst, Boolean isEnable, Date createTime, Date updateTime) {
        this(id, name, loginname, password, sex, deptid, remark, isAnalyst, isEnable, createTime, updateTime, ClientTypeEnum.WEB);
    }

    public LoginUser(Integer id, String name, String loginname, String password, String sex, Integer deptid, String remark, Boolean isAnalyst, Boolean isEnable, Date createTime, Date updateTime, ClientTypeEnum clientType) {
        this.id = id;
        this.name = name;
        this.loginname = loginname;
        this.password = password;
        this.sex = sex;
        this.deptid = deptid;
        this.remark = remark;
        this.isAnalyst = isAnalyst;
        this.isEnable = isEnable;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.clientType = clientType;
    }

    /** */
    private Integer id;

    /**
     * 账户名称
     */
    private String name;

    /**
     * 登录名称
     */
    private String loginname;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 性别
     */
    private String sex;

    /**
     * 部门ID
     */
    private Integer deptid;

    /**
     * 用水管理所
     */
    private String deptName;;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否分析员
     */
    private Boolean isAnalyst;

    /**
     * 是否启用
     */
    private Boolean isEnable;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 对应角色列表
     */
    private List<Integer> roleList;
    /**
     * 对应角色列表
     */
    private String roleLists;
    /**
     * 登录类型  android  ios  web
     */
    private ClientTypeEnum clientType;

    /**
     * 是否超级管理员
     */
    private  Boolean isSuperAdmin;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname == null ? null : loginname.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Boolean getIsAnalyst() {
        return isAnalyst;
    }

    public void setIsAnalyst(Boolean isAnalyst) {
        this.isAnalyst = isAnalyst;
    }

    public Boolean getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
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

    public List<Integer> getRoleList() {

        return roleList;
    }

    public void setRoleList(List<Integer> roleList) {
        this.roleList = roleList;
    }

    public ClientTypeEnum getClientType() {
        return clientType;
    }

    public void setClientType(ClientTypeEnum clientType) {
        this.clientType = clientType;
    }

    public Boolean getIsSuperAdmin() {
        return isSuperAdmin;
    }

    public void setIsSuperAdmin(Boolean isSuperAdmin) {
        this.isSuperAdmin = isSuperAdmin;
    }

    public String getRoleLists() {
        return roleLists;
    }

    public void setRoleLists(String roleLists) {
        this.roleLists = roleLists;
    }
}