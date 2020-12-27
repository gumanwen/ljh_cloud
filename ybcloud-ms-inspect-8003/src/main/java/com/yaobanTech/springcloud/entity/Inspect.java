package com.yaobanTech.springcloud.entity;

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
     * 计划名称
     */
    private String planName;

    /**
     * 计划类型
     */
    private String planType;

    /**
     * 计划开始时间
     */
    private String planBeginTime;

    /**
     * 计划结束时间
     */
    private String planEndTime;

    /**
     * 计划制定人
     */
    private String planMaker;

    /**
     * 计划周期
     */
    private String planCycle;

    /**
     * 描述
     */
    private String description;

    /**
     * 用水管理所
     */
    private String waterManagement;

    /**
     * 区域
     */
    private String area;

    /**
     * 管径
     */
    private String size;

    /**
     * 路线名称
     */
    private String routeName;

    /**
     * 路线类型
     */
    private String routeType;

    /**
     * 定点巡查类型
     */
    private String inspectType;

    /**
     * 签到点数量
     */
    private Integer checkInNum;

    /**
     * 路线制定人
     */
    private String routeMaker;

    /**
     * 计划巡查里程

     */
    private String planInspectMileage;

    /**
     * 隐患编号
     */
    private String hazardId;

    /**
     * 评价
     */
    private String evaluation;

    /**
     * 备注
     */
    private String remark;

    /**
     * 计划id
     */
    private String planId;

    /**
     * 路线id
     */
    private String routeId;

    /**
     * 任务类型
     */
    private String taskType;

    /**
     * 派发时间
     */
    private String sendTime;

    public Inspect() {
    }

    public Inspect(String inspectTaskId, String beginTime, String deadTime, String actBeginTime, String actEndTime, String status, String inspectPerson, String sender, String completeRate, String arrivalRate, String liveSitustion, String checkInPointSituation, String gisMap, String planName, String planType, String planBeginTime, String planEndTime, String planMaker, String planCycle, String description, String waterManagement, String area, String size, String routeName, String routeType, String inspectType, Integer checkInNum, String routeMaker, String planInspectMileage, String hazardId, String evaluation, String remark, String planId, String routeId,String taskType,String sendTime) {
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
        this.planName = planName;
        this.planType = planType;
        this.planBeginTime = planBeginTime;
        this.planEndTime = planEndTime;
        this.planMaker = planMaker;
        this.planCycle = planCycle;
        this.description = description;
        this.waterManagement = waterManagement;
        this.area = area;
        this.size = size;
        this.routeName = routeName;
        this.routeType = routeType;
        this.inspectType = inspectType;
        this.checkInNum = checkInNum;
        this.routeMaker = routeMaker;
        this.planInspectMileage = planInspectMileage;
        this.hazardId = hazardId;
        this.evaluation = evaluation;
        this.remark = remark;
        this.planId = planId;
        this.routeId = routeId;
        this.taskType = taskType;
        this.sendTime = sendTime;
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

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public String getPlanBeginTime() {
        return planBeginTime;
    }

    public void setPlanBeginTime(String planBeginTime) {
        this.planBeginTime = planBeginTime;
    }

    public String getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(String planEndTime) {
        this.planEndTime = planEndTime;
    }

    public String getPlanMaker() {
        return planMaker;
    }

    public void setPlanMaker(String planMaker) {
        this.planMaker = planMaker;
    }

    public String getPlanCycle() {
        return planCycle;
    }

    public void setPlanCycle(String planCycle) {
        this.planCycle = planCycle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWaterManagement() {
        return waterManagement;
    }

    public void setWaterManagement(String waterManagement) {
        this.waterManagement = waterManagement;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    public String getInspectType() {
        return inspectType;
    }

    public void setInspectType(String inspectType) {
        this.inspectType = inspectType;
    }

    public Integer getCheckInNum() {
        return checkInNum;
    }

    public void setCheckInNum(Integer checkInNum) {
        this.checkInNum = checkInNum;
    }

    public String getRouteMaker() {
        return routeMaker;
    }

    public void setRouteMaker(String routeMaker) {
        this.routeMaker = routeMaker;
    }

    public String getPlanInspectMileage() {
        return planInspectMileage;
    }

    public void setPlanInspectMileage(String planInspectMileage) {
        this.planInspectMileage = planInspectMileage;
    }

    public String getHazardId() {
        return hazardId;
    }

    public void setHazardId(String hazardId) {
        this.hazardId = hazardId;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
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
}
