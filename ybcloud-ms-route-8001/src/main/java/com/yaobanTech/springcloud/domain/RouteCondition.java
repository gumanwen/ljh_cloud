package com.yaobanTech.springcloud.domain;

public class RouteCondition {

    private String waterManagementOffice;
    private String pointInspectionType;
    private Double planInspectionMileageStart;
    private Double planInspectionMileageEnd;
    private String createdTimeStart;
    private String createdTimeEnd;
    private String routeName;
    private String routeType;
    private String mainKey;

    public RouteCondition() {
    }

    public RouteCondition(String waterManagementOffice, String pointInspectionType, Double planInspectionMileageStart, Double planInspectionMileageEnd, String createdTimeStart, String createdTimeEnd, String routeName, String routeType, String mainKey) {
        this.waterManagementOffice = waterManagementOffice;
        this.pointInspectionType = pointInspectionType;
        this.planInspectionMileageStart = planInspectionMileageStart;
        this.planInspectionMileageEnd = planInspectionMileageEnd;
        this.createdTimeStart = createdTimeStart;
        this.createdTimeEnd = createdTimeEnd;
        this.routeName = routeName;
        this.routeType = routeType;
        this.mainKey = mainKey;
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

    public String getCreatedTimeStart() {
        return createdTimeStart;
    }

    public void setCreatedTimeStart(String createdTimeStart) {
        this.createdTimeStart = createdTimeStart;
    }

    public String getCreatedTimeEnd() {
        return createdTimeEnd;
    }

    public void setCreatedTimeEnd(String createdTimeEnd) {
        this.createdTimeEnd = createdTimeEnd;
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

    public String getMainKey() {
        return mainKey;
    }

    public void setMainKey(String mainKey) {
        this.mainKey = mainKey;
    }
}
