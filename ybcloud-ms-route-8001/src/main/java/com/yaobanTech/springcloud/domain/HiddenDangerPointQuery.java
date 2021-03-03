package com.yaobanTech.springcloud.domain;

import java.util.Date;

public class HiddenDangerPointQuery {
    private String assetType;
    private String equipmentSize;
    private String waterUseOffice;
    private String mainKey;
    private Date commitDate;
    private Date endDate;
    private String hiddenDangerPointStatus;
    private String riskLevel;
    private String projectType;
    private String constructionType;
    private String networkNotification;

    public HiddenDangerPointQuery() {
    }

    public HiddenDangerPointQuery(String assetType, String equipmentSize, String waterUseOffice, String mainKey, Date commitDate, Date endDate, String hiddenDangerPointStatus, String riskLevel, String projectType, String constructionType, String networkNotification) {
        this.assetType = assetType;
        this.equipmentSize = equipmentSize;
        this.waterUseOffice = waterUseOffice;
        this.mainKey = mainKey;
        this.commitDate = commitDate;
        this.endDate = endDate;
        this.hiddenDangerPointStatus = hiddenDangerPointStatus;
        this.riskLevel = riskLevel;
        this.projectType = projectType;
        this.constructionType = constructionType;
        this.networkNotification = networkNotification;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getEquipmentSize() {
        return equipmentSize;
    }

    public void setEquipmentSize(String equipmentSize) {
        this.equipmentSize = equipmentSize;
    }

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

    public Date getCommitDate() {
        return commitDate;
    }

    public void setCommitDate(Date commitDate) {
        this.commitDate = commitDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getHiddenDangerPointStatus() {
        return hiddenDangerPointStatus;
    }

    public void setHiddenDangerPointStatus(String hiddenDangerPointStatus) {
        this.hiddenDangerPointStatus = hiddenDangerPointStatus;
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

    @Override
    public String toString() {
        return "HiddenDangerPointQuery{" +
                "assetType='" + assetType + '\'' +
                ", equipmentSize='" + equipmentSize + '\'' +
                ", waterUseOffice='" + waterUseOffice + '\'' +
                ", mainKey='" + mainKey + '\'' +
                ", commitDate=" + commitDate +
                ", endDate=" + endDate +
                ", hiddenDangerPointStatus='" + hiddenDangerPointStatus + '\'' +
                ", riskLevel='" + riskLevel + '\'' +
                ", projectType='" + projectType + '\'' +
                ", constructionType='" + constructionType + '\'' +
                ", networkNotification='" + networkNotification + '\'' +
                '}';
    }
}
