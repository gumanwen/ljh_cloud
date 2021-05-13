package com.yaobanTech.springcloud.pojos;

public class UserVo {
    private Integer id;
    private String name;
    private String loginname;

    public UserVo() {
    }

    public UserVo(Integer id, String name, String loginname) {
        this.id = id;
        this.name = name;
        this.loginname = loginname;
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
        this.name = name;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }
}
