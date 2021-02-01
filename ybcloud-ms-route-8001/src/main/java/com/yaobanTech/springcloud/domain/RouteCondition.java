package com.yaobanTech.springcloud.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class RouteCondition {

    private String waterManagementOffice;
    private String pointInspectionType;
    private Double planInspectionMileageStart;
    private Double planInspectionMileageEnd;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date createdTimeStart;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date createdTimeEnd;
    private String routeName;
    private String routeType;

    public RouteCondition() {
    }

    public RouteCondition(String waterManagementOffice, String pointInspectionType, Double planInspectionMileageStart, Double planInspectionMileageEnd, Date createdTimeStart, Date createdTimeEnd, String routeName, String routeType) {
        this.waterManagementOffice = waterManagementOffice;
        this.pointInspectionType = pointInspectionType;
        this.planInspectionMileageStart = planInspectionMileageStart;
        this.planInspectionMileageEnd = planInspectionMileageEnd;
        this.createdTimeStart = createdTimeStart;
        this.createdTimeEnd = createdTimeEnd;
        this.routeName = routeName;
        this.routeType = routeType;
    }

    public String getWaterManagementOffice() {
        return waterManagementOffice;
    }

    public void setWaterManagementOffice(String waterManagementOffice) {
        this.waterManagementOffice = waterManagementOffice;
    }

    public String getPointInspectionType() {
        return pointInspectionType;
    }

    public void setPointInspectionType(String pointInspectionType) {
        this.pointInspectionType = pointInspectionType;
    }

    public Double getPlanInspectionMileageStart() {
        return planInspectionMileageStart;
    }

    public void setPlanInspectionMileageStart(Double planInspectionMileageStart) {
        this.planInspectionMileageStart = planInspectionMileageStart;
    }

    public Double getPlanInspectionMileageEnd() {
        return planInspectionMileageEnd;
    }

    public void setPlanInspectionMileageEnd(Double planInspectionMileageEnd) {
        this.planInspectionMileageEnd = planInspectionMileageEnd;
    }


    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    public Date getCreatedTimeStart() {
        return createdTimeStart;
    }

    public void setCreatedTimeStart(Date createdTimeStart) {
        this.createdTimeStart = createdTimeStart;
    }

    public Date getCreatedTimeEnd() {
        return createdTimeEnd;
    }

    public void setCreatedTimeEnd(Date createdTimeEnd) {
        this.createdTimeEnd = createdTimeEnd;
    }

    @Override
    public String toString() {
        return "RouteCondition{" +
                "waterManagementOffice='" + waterManagementOffice + '\'' +
                ", pointInspectionType='" + pointInspectionType + '\'' +
                ", planInspectionMileageStart=" + planInspectionMileageStart +
                ", planInspectionMileageEnd=" + planInspectionMileageEnd +
                ", createdTimeStart=" + createdTimeStart +
                ", createdTimeEnd=" + createdTimeEnd +
                ", routeName='" + routeName + '\'' +
                ", routeType='" + routeType + '\'' +
                '}';
    }
}
