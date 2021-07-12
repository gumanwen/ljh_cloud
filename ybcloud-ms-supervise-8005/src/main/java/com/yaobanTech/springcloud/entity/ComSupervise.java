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
//小区居民用水检查表
@EqualsAndHashCode(callSuper = false)
@TableName("BIZ_COMMUNITY_INSPECTION")
public class ComSupervise implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 编号
     */
    @TableId(value = "record_id")
    private String recordId;
    /**
    * 小区名称
    */
    private String communityName;
    /**
     *单位名称
     */
    private String unitName;
    /**
     * 地址
     */
    private String address;

    /**
     * 联系人
     */
    private String contactPerson;

    /**
     * 电话
     */
    private String telephone;

    /**
     * 供水方式
     */
    private String waterSupply;

    /**
     * 水池清洗
     */
    private String poolClean;

    /**
     * 水池容积
     */
    private String poolVolume;

    /**
     * 清洗日期
     */
    private String cleanTime;

    /**
     * 水池溢流
     */
    private String poolOverflow;

    /**
     * 溢流口径
     */
    private String overflowCaliber;

    /**
     * 溢流时间
     */
    private String overflowTime;

    /**
     * 溢流水量
     */
    private String overflowVolume;

    /**
     * 运行状况是否正常
     */
    private String isNormal;

    /**
     * 违规用水
     */
    private String isIllegal;

    /**
     * 处理意见
     */
    private String handlingOpinion;

    /**
     * 被检单位，人
     */
    private String inspectedUnit;

    /**
     * 检查人
     */
    private String inspectPerson;

    /**
     * 日期
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

    public ComSupervise() {
    }

    public ComSupervise(String recordId, String communityName, String unitName, String address, String contactPerson, String telephone, String waterSupply, String poolClean, String poolVolume, String cleanTime, String poolOverflow, String overflowCaliber, String overflowTime, String overflowVolume, String isNormal, String isIllegal, String handlingOpinion, String inspectedUnit, String inspectPerson, String inspectDate, String memo, String isDelete) {
        this.recordId = recordId;
        this.communityName = communityName;
        this.unitName = unitName;
        this.address = address;
        this.contactPerson = contactPerson;
        this.telephone = telephone;
        this.waterSupply = waterSupply;
        this.poolClean = poolClean;
        this.poolVolume = poolVolume;
        this.cleanTime = cleanTime;
        this.poolOverflow = poolOverflow;
        this.overflowCaliber = overflowCaliber;
        this.overflowTime = overflowTime;
        this.overflowVolume = overflowVolume;
        this.isNormal = isNormal;
        this.isIllegal = isIllegal;
        this.handlingOpinion = handlingOpinion;
        this.inspectedUnit = inspectedUnit;
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

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getWaterSupply() {
        return waterSupply;
    }

    public void setWaterSupply(String waterSupply) {
        this.waterSupply = waterSupply;
    }

    public String getPoolClean() {
        return poolClean;
    }

    public void setPoolClean(String poolClean) {
        this.poolClean = poolClean;
    }

    public String getPoolVolume() {
        return poolVolume;
    }

    public void setPoolVolume(String poolVolume) {
        this.poolVolume = poolVolume;
    }

    public String getCleanTime() {
        return cleanTime;
    }

    public void setCleanTime(String cleanTime) {
        this.cleanTime = cleanTime;
    }

    public String getPoolOverflow() {
        return poolOverflow;
    }

    public void setPoolOverflow(String poolOverflow) {
        this.poolOverflow = poolOverflow;
    }

    public String getOverflowCaliber() {
        return overflowCaliber;
    }

    public void setOverflowCaliber(String overflowCaliber) {
        this.overflowCaliber = overflowCaliber;
    }

    public String getOverflowTime() {
        return overflowTime;
    }

    public void setOverflowTime(String overflowTime) {
        this.overflowTime = overflowTime;
    }

    public String getOverflowVolume() {
        return overflowVolume;
    }

    public void setOverflowVolume(String overflowVolume) {
        this.overflowVolume = overflowVolume;
    }

    public String getIsNormal() {
        return isNormal;
    }

    public void setIsNormal(String isNormal) {
        this.isNormal = isNormal;
    }

    public String getIsIllegal() {
        return isIllegal;
    }

    public void setIsIllegal(String isIllegal) {
        this.isIllegal = isIllegal;
    }

    public String getHandlingOpinion() {
        return handlingOpinion;
    }

    public void setHandlingOpinion(String handlingOpinion) {
        this.handlingOpinion = handlingOpinion;
    }

    public String getInspectedUnit() {
        return inspectedUnit;
    }

    public void setInspectedUnit(String inspectedUnit) {
        this.inspectedUnit = inspectedUnit;
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
