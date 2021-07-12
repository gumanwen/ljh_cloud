package com.yaobanTech.springcloud.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author lijh
 * @since 2020-12-18
 */
//特行用水巡检
@EqualsAndHashCode(callSuper = false)
@TableName("BIZ_SPECIAL_INSPECTION")
public class SpeSupervise implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 编号
     */
    @TableId(value = "record_id")
    private String recordId;
    /**
    * 特行用水单位名称
    */
    private String waterUnit;
    /**
     *用水地点
     */
    private String waterAdd;
    /**
     * 负责人
     */
    private String principal;

    /**
     * 电话
     */
    private String telephone;

    /**
     * 工商执照
     */
    private String businessLicense;

    /**
     * 洗车许可证
     */
    private String washPermit;

    /**
     * 用水水源
     */
    private String waterSource;

    /**
     * 水表水位户号
     */
    private String watermeterAccountNo;

    /**
     * 用水性质
     */
    private String waterNature;

    /**
     * 违规用水情况
     */
    private String isIllegal;

    /**
     * 被调查人签字
     */
    private String inspectPersonSign;

    /**
     * 检查人员
     */
    private String inspectPerson;

    /**
     * 时间
     */
    private String inspectDate;

    /**
     * 备注
     */
    private String memo;

    /**
     * 是否删除
     */
    private String isDelete;

    public SpeSupervise() {
    }

    public SpeSupervise(String recordId, String waterUnit, String waterAdd, String principal, String telephone, String businessLicense, String washPermit, String waterSource, String watermeterAccountNo, String waterNature, String isIllegal, String inspectPersonSign, String inspectPerson, String inspectDate, String memo, String isDelete) {
        this.recordId = recordId;
        this.waterUnit = waterUnit;
        this.waterAdd = waterAdd;
        this.principal = principal;
        this.telephone = telephone;
        this.businessLicense = businessLicense;
        this.washPermit = washPermit;
        this.waterSource = waterSource;
        this.watermeterAccountNo = watermeterAccountNo;
        this.waterNature = waterNature;
        this.isIllegal = isIllegal;
        this.inspectPersonSign = inspectPersonSign;
        this.inspectPerson = inspectPerson;
        this.inspectDate = inspectDate;
        this.memo = memo;
        this.isDelete = isDelete;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getWaterUnit() {
        return waterUnit;
    }

    public void setWaterUnit(String waterUnit) {
        this.waterUnit = waterUnit;
    }

    public String getWaterAdd() {
        return waterAdd;
    }

    public void setWaterAdd(String waterAdd) {
        this.waterAdd = waterAdd;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    public String getWashPermit() {
        return washPermit;
    }

    public void setWashPermit(String washPermit) {
        this.washPermit = washPermit;
    }

    public String getWaterSource() {
        return waterSource;
    }

    public void setWaterSource(String waterSource) {
        this.waterSource = waterSource;
    }

    public String getWatermeterAccountNo() {
        return watermeterAccountNo;
    }

    public void setWatermeterAccountNo(String watermeterAccountNo) {
        this.watermeterAccountNo = watermeterAccountNo;
    }

    public String getWaterNature() {
        return waterNature;
    }

    public void setWaterNature(String waterNature) {
        this.waterNature = waterNature;
    }

    public String getIsIllegal() {
        return isIllegal;
    }

    public void setIsIllegal(String isIllegal) {
        this.isIllegal = isIllegal;
    }

    public String getInspectPersonSign() {
        return inspectPersonSign;
    }

    public void setInspectPersonSign(String inspectPersonSign) {
        this.inspectPersonSign = inspectPersonSign;
    }

    public String getInspectPerson() {
        return inspectPerson;
    }

    public void setInspectPerson(String inspectPerson) {
        this.inspectPerson = inspectPerson;
    }

    public String getInspectDate() {
        return inspectDate;
    }

    public void setInspectDate(String inspectDate) {
        this.inspectDate = inspectDate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }
}
