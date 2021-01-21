package com.yaobanTech.springcloud.domain;

import java.util.Date;

public class FindCondition {

    private String routeName;
    private String planPorid;
    private String planType;
    private String mainKey;
    private String waterManagementOffice;
    private Date planCreatedTime;
    private String startTime;
    private String endTime;

    public FindCondition() {
    }

    public FindCondition(String routeName, String planPorid, String planType, String mainKey, String waterManagementOffice, Date planCreatedTime, String startTime, String endTime) {
        this.routeName = routeName;
        this.planPorid = planPorid;
        this.planType = planType;
        this.mainKey = mainKey;
        this.waterManagementOffice = waterManagementOffice;
        this.planCreatedTime = planCreatedTime;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getPlanPorid() {
        return planPorid;
    }

    public void setPlanPorid(String planPorid) {
        this.planPorid = planPorid;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public String getMainKey() {
        return mainKey;
    }

    public void setMainKey(String mainKey) {
        this.mainKey = mainKey;
    }

    public String getWaterManagementOffice() {
        return waterManagementOffice;
    }

    public void setWaterManagementOffice(String waterManagementOffice) {
        this.waterManagementOffice = waterManagementOffice;
    }

    public Date getPlanCreatedTime() {
        return planCreatedTime;
    }

    public void setPlanCreatedTime(Date planCreatedTime) {
        this.planCreatedTime = planCreatedTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "FindCondition{" +
                "routeName='" + routeName + '\'' +
                ", planPorid='" + planPorid + '\'' +
                ", planType='" + planType + '\'' +
                ", mainKey='" + mainKey + '\'' +
                ", waterManagementOffice='" + waterManagementOffice + '\'' +
                ", planCreatedTime=" + planCreatedTime +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
