package com.yaobanTech.springcloud.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName(value = "biz_hidden_danger_record")
public class BizHiddenDangerRecord {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String hiddenDangerCode;
    private String caliber;
    private String address;
    private String reason;
    private String handleSituation;
    private String signedTime;

    public BizHiddenDangerRecord() {
    }

    public BizHiddenDangerRecord(Integer id, String hiddenDangerCode, String caliber, String address, String reason, String handleSituation, String signedTime) {
        this.id = id;
        this.hiddenDangerCode = hiddenDangerCode;
        this.caliber = caliber;
        this.address = address;
        this.reason = reason;
        this.handleSituation = handleSituation;
        this.signedTime = signedTime;
    }

    public String getSignedTime() {
        return signedTime;
    }

    public void setSignedTime(String signedTime) {
        this.signedTime = signedTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHiddenDangerCode() {
        return hiddenDangerCode;
    }

    public void setHiddenDangerCode(String hiddenDangerCode) {
        this.hiddenDangerCode = hiddenDangerCode;
    }

    public String getCaliber() {
        return caliber;
    }

    public void setCaliber(String caliber) {
        this.caliber = caliber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getHandleSituation() {
        return handleSituation;
    }

    public void setHandleSituation(String handleSituation) {
        this.handleSituation = handleSituation;
    }

    @Override
    public String toString() {
        return "BizHiddenDangerRecord{" +
                "id=" + id +
                ", hiddenDangerCode='" + hiddenDangerCode + '\'' +
                ", caliber='" + caliber + '\'' +
                ", address='" + address + '\'' +
                ", reason='" + reason + '\'' +
                ", handleSituation='" + handleSituation + '\'' +
                '}';
    }
}



