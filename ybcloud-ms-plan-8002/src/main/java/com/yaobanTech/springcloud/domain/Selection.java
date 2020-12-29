package com.yaobanTech.springcloud.domain;

public class Selection {

    private Integer  id;
    private String planName;

    public Selection() {
    }

    public Selection(Integer id, String planName) {
        this.id = id;
        this.planName = planName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    @Override
    public String toString() {
        return "Selection{" +
                "id=" + id +
                ", planName='" + planName + '\'' +
                '}';
    }
}
