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
//供水设施巡检
@EqualsAndHashCode(callSuper = false)
@TableName("BIZ_FACILITY_INSPECTION")
public class FacSupervise implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 编号
     */
    @TableId(value = "record_id")
    private String recordId;
    /**
    * 检查时间
    */
    private String checkTime;
    /**
     *检查口径
     */
    private String checkCaliber;
    /**
     * 检查公里数
     */
    private String checkKilometers;

    /**
     * 检查人
     */
    private String checker;

    /**
     * 检查起点
     */
    private String checkStartPoint;

    /**
     * 检查终点
     */
    private String checkEndPoint;

    /**
     * 施工危及供水设施安全
     */
    private String isSafe;

    /**
     * 施工危及供水设施安全备注
     */
    private String isSafeMemo;

    /**
     * 供水设施漏水
     */
    private String isLeak;

    /**
     * 供水设施漏水备注
     */
    private String isLeakMemo;

    /**
     * 占压供水设施
     */
    private String isPress;

    /**
     * 占压供水设施备注
     */
    private String isPressMemo;

    /**
     * 消防栓（取水、漏水）
     */
    private String isFirehydrant;

    /**
     * 消防栓（取水、漏水）备注
     */
    private String isFirehydrantMemo;

    /**
     * 违规用水
     */
    private String isIllegal;

    /**
     * 违规用水备注
     */
    private String isIllegalMemo;

    /**
     * 检查情况
     */
    private String inspection;

    /**
     * 签字日期
     */
    private String signTime;
    /**
     * 备注
     */
    private String memo;

    /**
     * 是否删除
     */
    private String isDelete;

    public FacSupervise() {
    }

    public FacSupervise(String recordId, String checkTime, String checkCaliber, String checkKilometers, String checker, String checkStartPoint, String checkEndPoint, String isSafe, String isSafeMemo, String isLeak, String isLeakMemo, String isPress, String isPressMemo, String isFirehydrant, String isFirehydrantMemo, String isIllegal, String isIllegalMemo, String inspection, String signTime, String memo, String isDelete) {
        this.recordId = recordId;
        this.checkTime = checkTime;
        this.checkCaliber = checkCaliber;
        this.checkKilometers = checkKilometers;
        this.checker = checker;
        this.checkStartPoint = checkStartPoint;
        this.checkEndPoint = checkEndPoint;
        this.isSafe = isSafe;
        this.isSafeMemo = isSafeMemo;
        this.isLeak = isLeak;
        this.isLeakMemo = isLeakMemo;
        this.isPress = isPress;
        this.isPressMemo = isPressMemo;
        this.isFirehydrant = isFirehydrant;
        this.isFirehydrantMemo = isFirehydrantMemo;
        this.isIllegal = isIllegal;
        this.isIllegalMemo = isIllegalMemo;
        this.inspection = inspection;
        this.signTime = signTime;
        this.memo = memo;
        this.isDelete = isDelete;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getCheckCaliber() {
        return checkCaliber;
    }

    public void setCheckCaliber(String checkCaliber) {
        this.checkCaliber = checkCaliber;
    }

    public String getCheckKilometers() {
        return checkKilometers;
    }

    public void setCheckKilometers(String checkKilometers) {
        this.checkKilometers = checkKilometers;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public String getCheckStartPoint() {
        return checkStartPoint;
    }

    public void setCheckStartPoint(String checkStartPoint) {
        this.checkStartPoint = checkStartPoint;
    }

    public String getCheckEndPoint() {
        return checkEndPoint;
    }

    public void setCheckEndPoint(String checkEndPoint) {
        this.checkEndPoint = checkEndPoint;
    }

    public String getIsSafe() {
        return isSafe;
    }

    public void setIsSafe(String isSafe) {
        this.isSafe = isSafe;
    }

    public String getIsSafeMemo() {
        return isSafeMemo;
    }

    public void setIsSafeMemo(String isSafeMemo) {
        this.isSafeMemo = isSafeMemo;
    }

    public String getIsLeak() {
        return isLeak;
    }

    public void setIsLeak(String isLeak) {
        this.isLeak = isLeak;
    }

    public String getIsLeakMemo() {
        return isLeakMemo;
    }

    public void setIsLeakMemo(String isLeakMemo) {
        this.isLeakMemo = isLeakMemo;
    }

    public String getIsPress() {
        return isPress;
    }

    public void setIsPress(String isPress) {
        this.isPress = isPress;
    }

    public String getIsPressMemo() {
        return isPressMemo;
    }

    public void setIsPressMemo(String isPressMemo) {
        this.isPressMemo = isPressMemo;
    }

    public String getIsFirehydrant() {
        return isFirehydrant;
    }

    public void setIsFirehydrant(String isFirehydrant) {
        this.isFirehydrant = isFirehydrant;
    }

    public String getIsFirehydrantMemo() {
        return isFirehydrantMemo;
    }

    public void setIsFirehydrantMemo(String isFirehydrantMemo) {
        this.isFirehydrantMemo = isFirehydrantMemo;
    }

    public String getIsIllegal() {
        return isIllegal;
    }

    public void setIsIllegal(String isIllegal) {
        this.isIllegal = isIllegal;
    }

    public String getIsIllegalMemo() {
        return isIllegalMemo;
    }

    public void setIsIllegalMemo(String isIllegalMemo) {
        this.isIllegalMemo = isIllegalMemo;
    }

    public String getInspection() {
        return inspection;
    }

    public void setInspection(String inspection) {
        this.inspection = inspection;
    }

    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
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
