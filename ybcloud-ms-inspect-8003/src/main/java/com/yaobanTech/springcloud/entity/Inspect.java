package com.yaobanTech.springcloud.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
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

    public Inspect() {
    }

    public Inspect(String inspectTaskId, String beginTime, String deadTime, String actBeginTime, String actEndTime, String status, String inspectPerson, String sender, String completeRate, String arrivalRate, String liveSitustion, String checkInPointSituation, String gisMap, String taskType, String sendTime, String overReason, String modifyBy, String completeTime, Integer planId, Integer routeId) {
        this.inspectTaskId = inspectTaskId;
        this.beginTime = beginTime;
        this.deadTime = deadTime;
        this.actBeginTime = actBeginTime;
        this.actEndTime = actEndTime;
        this.status = status;
        this.inspectPerson = inspectPerson;
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
    }

    public String getInspectTaskId() {
        return inspectTaskId;
    }

    public void setInspectTaskId(String inspectTaskId) {
        this.inspectTaskId = inspectTaskId;
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
}
