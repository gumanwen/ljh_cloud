package com.yaobanTech.springcloud.domain;

public class LeakPointQuery {
    private String waterUseOffice;
    private String equipmentSize;
    private String assetType;
    private String leakPointStatus;

    public LeakPointQuery() {
    }

    public LeakPointQuery(String waterUseOffice, String equipmentSize, String assetType, String leakPointStatus) {
        this.waterUseOffice = waterUseOffice;
        this.equipmentSize = equipmentSize;
        this.assetType = assetType;
        this.leakPointStatus = leakPointStatus;
    }

    public String getWaterUseOffice() {
        return waterUseOffice;
    }

    public void setWaterUseOffice(String waterUseOffice) {
        this.waterUseOffice = waterUseOffice;
    }

    public String getEquipmentSize() {
        return equipmentSize;
    }

    public void setEquipmentSize(String equipmentSize) {
        this.equipmentSize = equipmentSize;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getLeakPointStatus() {
        return leakPointStatus;
    }

    public void setLeakPointStatus(String leakPointStatus) {
        this.leakPointStatus = leakPointStatus;
    }
}
