package com.yaobanTech.springcloud.domain;

import java.util.Date;
import java.util.List;

public class SignPointQuery {
    private String waterUserOffice;
    private String routeName;
    private String routeType;
    private String pointInspectionType;
    private String planName;
    private String checkMan;
    private Date taskStart1;
    private Date taskEnd1;
    private Date taskStart2;
    private Date taskEnd2;
    private Date signDateStart;
    private Date signDateEnd;
    private String signPointType;
    private String pipeDiameter;
    private String signStatus;
    private String mainKey;
    private String hiddenCode;
    private String taskidList;

    public SignPointQuery() {
    }

    public SignPointQuery(String waterUserOffice, String routeName, String routeType, String pointInspectionType, String planName, String checkMan, Date taskStart1, Date taskEnd1, Date taskStart2, Date taskEnd2, Date signDateStart, Date signDateEnd, String signPointType, String pipeDiameter, String signStatus, String mainKey, String hiddenCode, String taskidList) {
        this.waterUserOffice = waterUserOffice;
        this.routeName = routeName;
        this.routeType = routeType;
        this.pointInspectionType = pointInspectionType;
        this.planName = planName;
        this.checkMan = checkMan;
        this.taskStart1 = taskStart1;
        this.taskEnd1 = taskEnd1;
        this.taskStart2 = taskStart2;
        this.taskEnd2 = taskEnd2;
        this.signDateStart = signDateStart;
        this.signDateEnd = signDateEnd;
        this.signPointType = signPointType;
        this.pipeDiameter = pipeDiameter;
        this.signStatus = signStatus;
        this.mainKey = mainKey;
        this.hiddenCode = hiddenCode;
        this.taskidList = taskidList;
    }

    public String getTaskidList() {
        return taskidList;
    }

    public void setTaskidList(String taskidList) {
        this.taskidList = taskidList;
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

    public Date getTaskStart1() {
        return taskStart1;
    }

    public void setTaskStart1(Date taskStart1) {
        this.taskStart1 = taskStart1;
    }

    public Date getTaskEnd1() {
        return taskEnd1;
    }

    public void setTaskEnd1(Date taskEnd1) {
        this.taskEnd1 = taskEnd1;
    }

    public Date getTaskStart2() {
        return taskStart2;
    }

    public void setTaskStart2(Date taskStart2) {
        this.taskStart2 = taskStart2;
    }

    public Date getTaskEnd2() {
        return taskEnd2;
    }

    public void setTaskEnd2(Date taskEnd2) {
        this.taskEnd2 = taskEnd2;
    }

    public Date getSignDateStart() {
        return signDateStart;
    }

    public void setSignDateStart(Date signDateStart) {
        this.signDateStart = signDateStart;
    }

    public Date getSignDateEnd() {
        return signDateEnd;
    }

    public void setSignDateEnd(Date signDateEnd) {
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
                ", taskStart1=" + taskStart1 +
                ", taskEnd1=" + taskEnd1 +
                ", taskStart2=" + taskStart2 +
                ", taskEnd2=" + taskEnd2 +
                ", signDateStart=" + signDateStart +
                ", signDateEnd=" + signDateEnd +
                ", signPointType='" + signPointType + '\'' +
                ", pipeDiameter='" + pipeDiameter + '\'' +
                ", signStatus='" + signStatus + '\'' +
                ", mainKey='" + mainKey + '\'' +
                ", hiddenCode='" + hiddenCode + '\'' +
                '}';
    }
}
