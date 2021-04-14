package com.yaobanTech.springcloud.pojos;

import java.util.List;

/**
 * @description   用户信息类
 * @author zhoufei
 * @date  2018/11/1 9:03
 * @version 1.0 
 */
public class UserInfo {

    private Integer id;

    private  String  loginname;

    private  String name;

    private Boolean isAnalyst;

    private List<PermissionInfo> permissionInfos;

    private Dept dept;

    private String headImg;

    private String tel;

    private String jobNumber;

    private boolean collect; // 是否采集行走轨迹

    private List<Role> roles;

    private String roleslist;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAnalyst() {
        return isAnalyst;
    }

    public void setAnalyst(Boolean analyst) {
        isAnalyst = analyst;
    }

    public List<PermissionInfo> getPermissionInfos() {
        return permissionInfos;
    }

    public void setPermissionInfos(List<PermissionInfo> permissionInfos) {
        this.permissionInfos = permissionInfos;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public boolean isCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public String getRoleslist() {
        return roleslist;
    }

    public void setRoleslist(String roleslist) {
        this.roleslist = roleslist;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public UserInfo() {
    }

    public UserInfo(Integer id, String loginname, String name, Boolean isAnalyst, List<PermissionInfo> permissionInfos, Dept dept, String headImg, String tel, String jobNumber, boolean collect, List<Role> roles, String roleslist) {
        this.id = id;
        this.loginname = loginname;
        this.name = name;
        this.isAnalyst = isAnalyst;
        this.permissionInfos = permissionInfos;
        this.dept = dept;
        this.headImg = headImg;
        this.tel = tel;
        this.jobNumber = jobNumber;
        this.collect = collect;
        this.roles = roles;
        this.roleslist = roleslist;
    }
}
