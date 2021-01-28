package com.yaobanTech.springcloud.pojos;

import com.yaobanTech.springcloud.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class User {

    @Autowired
    private UserMapper userMapper;

    private Integer id;

    private String username;

    private String password;

    private String role;

    private String name;

    private String phone;

    private String telephone;

    private String address;

    private Boolean enabled;

    private String userface;

    private String remark;

    private String staffCode;

    private String code;

    private String department;

    public User() {
    }

    public User(UserMapper userMapper, Integer id, String username, String password, String role, String name, String phone, String telephone, String address, Boolean enabled, String userface, String remark, String staffCode, String code, String department) {
        this.userMapper = userMapper;
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
        this.phone = phone;
        this.telephone = telephone;
        this.address = address;
        this.enabled = enabled;
        this.userface = userface;
        this.remark = remark;
        this.staffCode = staffCode;
        this.code = code;
        this.department = department;
    }

    @Override
    public String toString() {
        return "User{" +
                "userMapper=" + userMapper +
                ", id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", telephone='" + telephone + '\'' +
                ", address='" + address + '\'' +
                ", enabled=" + enabled +
                ", userface='" + userface + '\'' +
                ", remark='" + remark + '\'' +
                ", staffCode='" + staffCode + '\'' +
                ", code='" + code + '\'' +
                ", department='" + department + '\'' +
                '}';
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getUserface() {
        return userface;
    }

    public void setUserface(String userface) {
        this.userface = userface;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof User)){
            return false;
        }
        User u = (User)o;
        return (u.getName().equals(name) && u.getUsername().equals(username));
    }
}
