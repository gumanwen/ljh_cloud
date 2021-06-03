package com.yaobanTech.springcloud.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author lijh
 * @since 2020-12-18
 */

@EqualsAndHashCode(callSuper = false)
@TableName("BIZ_INSPECT")
public class Inspect implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 巡查任务编号
     */
    @TableId(value = "inspect_task_id")
    private String inspectTaskId;
    /**
    * 用水管理所
    */
    private String waterManagementOffice;
    /**
     *管径
     */
    private String diameter;
    /**
     * 任务开始时间
     */
    private String beginTime;

    /**
     * 任务截止时间
     */
    private String deadTime;

    /**
     * 任务实际开始时间
     */
    private String actBeginTime;

    /**
     * 任务实际完成时间
     */
    private String actEndTime;

    /**
     * 任务状态
     */
    private String status;

    /**
     * 巡查人员
     */
    private String inspectPerson;

    /**
     * 巡查人员中文
     */
    private String name;

    /**
     * 派发人
     */
    private String sender;

    /**
     * 完成率
     */
    private String completeRate;

    /**
     * 到位率
     */
    private String arrivalRate;

    /**
     * 现场情况
     */
    private String liveSitustion;

    /**
     * 签到点情况
     */
    private String checkInPointSituation;

    /**
     * GIS 地图
     */
    private String gisMap;

    /**
     * 任务类型
     */
    private String taskType;

    /**
     * 派发时间
     */
    private String sendTime;

    /**
     * 超时原因
     */
    private String overReason;

    /**
     * 修改人
     */
    private String modifyBy;

    /**
     * 完成时间
     */
    private String completeTime;

    /**
     * 计划id
     */
    private Integer planId;

    /**
     * 路线id
     */
    private Integer routeId;

    /**
     * 巡查周期
     */
    private String cycle;

    /**
     * 巡查结果
     */
    private String result;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 终止原因
     */
    private String endReason;

    /**
     * 终止时间
     */
    private String endTime;
    /**
     * 未到位时间
     */
    private String notinplace;

    /**
     * 任务总数
     */
    private int allpoint;
    /**
     * 已完成签到点
     */
    private int donepoint;
    /**
     * 已到位签到点
     */
    private int inplacepoint;
    /**
     * 区域
     */
    private String area;

    public Inspect() {
    }

    public Inspect(String inspectTaskId, String waterManagementOffice, String diameter, String beginTime, String deadTime, String actBeginTime, String actEndTime, String status, String inspectPerson, String name, String sender, String completeRate, String arrivalRate, String liveSitustion, String checkInPointSituation, String gisMap, String taskType, String sendTime, String overReason, String modifyBy, String completeTime, Integer planId, Integer routeId, String cycle, String result, String createTime, String endReason, String endTime, String notinplace, int allpoint, int donepoint, int inplacepoint, String area) {
        this.inspectTaskId = inspectTaskId;
        this.waterManagementOffice = waterManagementOffice;
        this.diameter = diameter;
        this.beginTime = beginTime;
        this.deadTime = deadTime;
        this.actBeginTime = actBeginTime;
        this.actEndTime = actEndTime;
        this.status = status;
        this.inspectPerson = inspectPerson;
        this.name = name;
        this.sender = sender;
        this.completeRate = completeRate;
        this.arrivalRate = arrivalRate;
        this.liveSitustion = liveSitustion;
        this.checkInPointSituation = checkInPointSituation;
        this.gisMap = gisMap;
        this.taskType = taskType;
        this.sendTime = sendTime;
        this.overReason = overReason;
        this.modifyBy = modifyBy;
        this.completeTime = completeTime;
        this.planId = planId;
        this.routeId = routeId;
        this.cycle = cycle;
        this.result = result;
        this.createTime = createTime;
        this.endReason = endReason;
        this.endTime = endTime;
        this.notinplace = notinplace;
        this.allpoint = allpoint;
        this.donepoint = donepoint;
        this.inplacepoint = inplacepoint;
        this.area = area;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getInplacepoint() {
        return inplacepoint;
    }

    public void setInplacepoint(int inplacepoint) {
        this.inplacepoint = inplacepoint;
    }

    public int getAllpoint() {
        return allpoint;
    }

    public void setAllpoint(int allpoint) {
        this.allpoint = allpoint;
    }

    public int getDonepoint() {
        return donepoint;
    }

    public void setDonepoint(int donepoint) {
        this.donepoint = donepoint;
    }

    public String getInspectTaskId() {
        return inspectTaskId;
    }

    public void setInspectTaskId(String inspectTaskId) {
        this.inspectTaskId = inspectTaskId;
    }

    public String getWaterManagementOffice() {
        return waterManagementOffice;
    }

    public void setWaterManagementOffice(String waterManagementOffice) {
        this.waterManagementOffice = waterManagementOffice;
    }

    public String getDiameter() {
        return diameter;
    }

    public void setDiameter(String diameter) {
        this.diameter = diameter;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getDeadTime() {
        return deadTime;
    }

    public void setDeadTime(String deadTime) {
        this.deadTime = deadTime;
    }

    public String getActBeginTime() {
        return actBeginTime;
    }

    public void setActBeginTime(String actBeginTime) {
        this.actBeginTime = actBeginTime;
    }

    public String getActEndTime() {
        return actEndTime;
    }

    public void setActEndTime(String actEndTime) {
        this.actEndTime = actEndTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInspectPerson() {
        return inspectPerson;
    }

    public void setInspectPerson(String inspectPerson) {
        this.inspectPerson = inspectPerson;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getCompleteRate() {
        return completeRate;
    }

    public void setCompleteRate(String completeRate) {
        this.completeRate = completeRate;
    }

    public String getArrivalRate() {
        return arrivalRate;
    }

    public void setArrivalRate(String arrivalRate) {
        this.arrivalRate = arrivalRate;
    }

    public String getLiveSitustion() {
        return liveSitustion;
    }

    public void setLiveSitustion(String liveSitustion) {
        this.liveSitustion = liveSitustion;
    }

    public String getCheckInPointSituation() {
        return checkInPointSituation;
    }

    public void setCheckInPointSituation(String checkInPointSituation) {
        this.checkInPointSituation = checkInPointSituation;
    }

    public String getGisMap() {
        return gisMap;
    }

    public void setGisMap(String gisMap) {
        this.gisMap = gisMap;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getOverReason() {
        return overReason;
    }

    public void setOverReason(String overReason) {
        this.overReason = overReason;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }

    public String getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEndReason() {
        return endReason;
    }

    public void setEndReason(String endReason) {
        this.endReason = endReason;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getNotinplace() {
        return notinplace;
    }

    public void setNotinplace(String notinplace) {
        this.notinplace = notinplace;
    }
}
