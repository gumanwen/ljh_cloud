package com.yaobanTech.springcloud.domain;

public class SignPointQuery {
    private String waterUserOffice;
    private String routeName;
    private String routeType;
    private String pointInspectionType;
    private String planName;
    private String checkMan;
    private String taskStart;
    private String taskEnd;
    private String signDateStart;
    private String signDateEnd;
    private String signPointType;
    private String pipeDiameter;
    private String signStatus;
    private String mainKey;
    private String hiddenCode;

    public SignPointQuery() {
    }

    public SignPointQuery(String waterUserOffice, String routeName, String routeType, String pointInspectionType, String planName, String checkMan, String taskStart, String taskEnd, String signDateStart, String signDateEnd, String signPointType, String pipeDiameter, String signStatus, String mainKey, String hiddenCode) {
        this.waterUserOffice = waterUserOffice;
        this.routeName = routeName;
        this.routeType = routeType;
        this.pointInspectionType = pointInspectionType;
        this.planName = planName;
        this.checkMan = checkMan;
        this.taskStart = taskStart;
        this.taskEnd = taskEnd;
        this.signDateStart = signDateStart;
        this.signDateEnd = signDateEnd;
        this.signPointType = signPointType;
        this.pipeDiameter = pipeDiameter;
        this.signStatus = signStatus;
        this.mainKey = mainKey;
        this.hiddenCode = hiddenCode;
    }

    public String getWaterUserOffice() {
        return waterUserOffice;
    }

    public void setWaterUserOffice(String waterUserOffice) {
        this.waterUserOffice = waterUserOffice;
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

    public String getPointInspectionType() {
        return pointInspectionType;
    }

    public void setPointInspectionType(String pointInspectionType) {
        this.pointInspectionType = pointInspectionType;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getCheckMan() {
        return checkMan;
    }

    public void setCheckMan(String checkMan) {
        this.checkMan = checkMan;
    }

    public String getTaskStart() {
        return taskStart;
    }

    public void setTaskStart(String taskStart) {
        this.taskStart = taskStart;
    }

    public String getTaskEnd() {
        return taskEnd;
    }

    public void setTaskEnd(String taskEnd) {
        this.taskEnd = taskEnd;
    }

    public String getSignDateStart() {
        return signDateStart;
    }

    public void setSignDateStart(String signDateStart) {
        this.signDateStart = signDateStart;
    }

    public String getSignDateEnd() {
        return signDateEnd;
    }

    public void setSignDateEnd(String signDateEnd) {
        this.signDateEnd = signDateEnd;
    }

    public String getSignPointType() {
        return signPointType;
    }

    public void setSignPointType(String signPointType) {
        this.signPointType = signPointType;
    }

    public String getPipeDiameter() {
        return pipeDiameter;
    }

    public void setPipeDiameter(String pipeDiameter) {
        this.pipeDiameter = pipeDiameter;
    }

    public String getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(String signStatus) {
        this.signStatus = signStatus;
    }

    public String getMainKey() {
        return mainKey;
    }

    public void setMainKey(String mainKey) {
        this.mainKey = mainKey;
    }

    public String getHiddenCode() {
        return hiddenCode;
    }

    public void setHiddenCode(String hiddenCode) {
        this.hiddenCode = hiddenCode;
    }

    @Override
    public String toString() {
        return "SignPointQuery{" +
                "waterUserOffice='" + waterUserOffice + '\'' +
                ", routeName='" + routeName + '\'' +
                ", routeType='" + routeType + '\'' +
                ", pointInspectionType='" + pointInspectionType + '\'' +
                ", planName='" + planName + '\'' +
                ", checkMan='" + checkMan + '\'' +
                ", taskStart='" + taskStart + '\'' +
                ", taskEnd='" + taskEnd + '\'' +
                ", signDateStart='" + signDateStart + '\'' +
                ", signDateEnd='" + signDateEnd + '\'' +
                ", signPointType='" + signPointType + '\'' +
                ", pipeDiameter='" + pipeDiameter + '\'' +
                ", signStatus='" + signStatus + '\'' +
                ", mainKey='" + mainKey + '\'' +
                ", hiddenCode='" + hiddenCode + '\'' +
                '}';
    }
}
