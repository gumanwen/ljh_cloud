package com.yaobanTech.springcloud.domain;

public class Selection {

    private Integer  id;
    private String routeName;

    public Selection() {
    }

    public Selection(Integer id, String routeName) {
        this.id = id;
        this.routeName = routeName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    @Override
    public String toString() {
        return "Selection{" +
                "id=" + id +
                ", routeName='" + routeName + '\'' +
                '}';
    }
}
