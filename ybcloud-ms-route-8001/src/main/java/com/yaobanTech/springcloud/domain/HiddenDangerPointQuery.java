package com.yaobanTech.springcloud.domain;

public class HiddenDangerPointQuery {
    private String waterUseOffice;
    private String mainKey;
    //隐患时长
    private String lastStart;
    private String lastEnd;

    private String hiddenDangerPointStatus;
    //施工开始时间
    private String constructionS1;
    private String constructionS2;
    //施工结束时间
    private String constructionE1;
    private String constructionE2;
    //隐患上报时间
    private String commitDate;
    private String endDate;
    private String riskLevel;
    private String projectType;
    private String constructionType;
    private String networkNotification;

    public String getWaterUseOffice() {
        return waterUseOffice;
    }

    public void setWaterUseOffice(String waterUseOffice) {
        this.waterUseOffice = waterUseOffice;
    }

    public String getMainKey() {
        return mainKey;
    }

    public void setMainKey(String mainKey) {
        this.mainKey = mainKey;
    }

    public String getLastStart() {
        return lastStart;
    }

    public void setLastStart(String lastStart) {
        this.lastStart = lastStart;
    }

    public String getLastEnd() {
        return lastEnd;
    }

    public void setLastEnd(String lastEnd) {
        this.lastEnd = lastEnd;
    }

    public String getHiddenDangerPointStatus() {
        return hiddenDangerPointStatus;
    }

    public void setHiddenDangerPointStatus(String hiddenDangerPointStatus) {
        this.hiddenDangerPointStatus = hiddenDangerPointStatus;
    }

    public String getConstructionS1() {
        return constructionS1;
    }

    public void setConstructionS1(String constructionS1) {
        this.constructionS1 = constructionS1;
    }

    public String getConstructionS2() {
        return constructionS2;
    }

    public void setConstructionS2(String constructionS2) {
        this.constructionS2 = constructionS2;
    }

    public String getConstructionE1() {
        return constructionE1;
    }

    public void setConstructionE1(String constructionE1) {
        this.constructionE1 = constructionE1;
    }

    public String getConstructionE2() {
        return constructionE2;
    }

    public void setConstructionE2(String constructionE2) {
        this.constructionE2 = constructionE2;
    }

    public String getCommitDate() {
        return commitDate;
    }

    public void setCommitDate(String commitDate) {
        this.commitDate = commitDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getConstructionType() {
        return constructionType;
    }

    public void setConstructionType(String constructionType) {
        this.constructionType = constructionType;
    }

    public String getNetworkNotification() {
        return networkNotification;
    }

    public void setNetworkNotification(String networkNotification) {
        this.networkNotification = networkNotification;
    }
}
