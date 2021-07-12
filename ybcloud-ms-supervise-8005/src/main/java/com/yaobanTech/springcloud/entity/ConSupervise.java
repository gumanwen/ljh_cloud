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
//施工用水巡检
@EqualsAndHashCode(callSuper = false)
@TableName("BIZ_CONSTRUCTION_INSPECTION")
public class ConSupervise implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 编号
     */
    @TableId(value = "record_id")
    private String recordId;
    /**
    * 项目名称
    */
    private String projectName;
    /**
     *项目地址
     */
    private String projectAdd;
    /**
     * 建设单位
     */
    private String buildUnit;

    /**
     * 施工单位
     */
    private String constructUnit;

    /**
     * 工程类别
     */
    private String projectType;

    /**
     * 建设面积
     */
    private String buildArea;

    /**
     *施工时间
     */
    private String constructTime;

    /**
     * 施工用水情况
     */
    private String constructMemo;

    /**
     * 水表户号及表位信息
     */
    private String watermeterAccountNo;

    /**
     * 口径
     */
    private String meterSize;

    /**
     * 水表编号
     */
    private String watermeterNo;

    /**
     * 指度
     */
    private String index;

    /**
     * 违规用水情况
     */
    private String isIllegal;

    /**
     * 被调查人签字
     */
    private String inspectPersonSign;

    /**
     * 检查人
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

    public ConSupervise() {
    }

    public ConSupervise(String recordId, String projectName, String projectAdd, String buildUnit, String constructUnit, String projectType, String buildArea, String constructTime, String constructMemo, String watermeterAccountNo, String meterSize, String watermeterNo, String index, String isIllegal, String inspectPersonSign, String inspectPerson, String inspectDate, String memo, String isDelete) {
        this.recordId = recordId;
        this.projectName = projectName;
        this.projectAdd = projectAdd;
        this.buildUnit = buildUnit;
        this.constructUnit = constructUnit;
        this.projectType = projectType;
        this.buildArea = buildArea;
        this.constructTime = constructTime;
        this.constructMemo = constructMemo;
        this.watermeterAccountNo = watermeterAccountNo;
        this.meterSize = meterSize;
        this.watermeterNo = watermeterNo;
        this.index = index;
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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectAdd() {
        return projectAdd;
    }

    public void setProjectAdd(String projectAdd) {
        this.projectAdd = projectAdd;
    }

    public String getBuildUnit() {
        return buildUnit;
    }

    public void setBuildUnit(String buildUnit) {
        this.buildUnit = buildUnit;
    }

    public String getConstructUnit() {
        return constructUnit;
    }

    public void setConstructUnit(String constructUnit) {
        this.constructUnit = constructUnit;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getBuildArea() {
        return buildArea;
    }

    public void setBuildArea(String buildArea) {
        this.buildArea = buildArea;
    }

    public String getConstructTime() {
        return constructTime;
    }

    public void setConstructTime(String constructTime) {
        this.constructTime = constructTime;
    }

    public String getConstructMemo() {
        return constructMemo;
    }

    public void setConstructMemo(String constructMemo) {
        this.constructMemo = constructMemo;
    }

    public String getWatermeterAccountNo() {
        return watermeterAccountNo;
    }

    public void setWatermeterAccountNo(String watermeterAccountNo) {
        this.watermeterAccountNo = watermeterAccountNo;
    }

    public String getMeterSize() {
        return meterSize;
    }

    public void setMeterSize(String meterSize) {
        this.meterSize = meterSize;
    }

    public String getWatermeterNo() {
        return watermeterNo;
    }

    public void setWatermeterNo(String watermeterNo) {
        this.watermeterNo = watermeterNo;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
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
