package com.yaobanTech.springcloud.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Description  
 * @Author  yuxy
 * @Date 2020-12-22 
 */

@Entity
@Table ( name ="biz_signed_point" )
public class BizSignedPoint implements Serializable {

	private static final long serialVersionUID =  96987551705552753L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
   	@Column(name = "id" )
	private Integer id;

	/**
	 * 签到点编号
	 */
	@Column(name = "sign_point_code" )
	private Integer signPointCode;

	/**
	 * 当前所选地址
	 */
   	@Column(name = "current_choosed_address" )
	private String currentChoosedAddress;

	/**
	 * 问题编号
	 */
   	@Column(name = "trouble_code" )
	private String troubleCode;

	/**
	 * 问题原因
	 */
   	@Column(name = "trouble_reason" )
	private String troubleReason;

	/**
	 * 隐患类型
	 */
   	@Column(name = "hidden_danger_type" )
	private String hiddenDangerType;

	/**
	 * 处理意见
	 */
   	@Column(name = "handle_suggestion" )
	private String handleSuggestion;

	/**
	 * 是否有效
	 */
	@Column(name = "enabled" )
	private Integer enabled;

	/**
	 * 路线表id
	 */
	@Column(name = "route_id" )
	private Integer routeId;

	/**
	 * 坐标
	 */
	@Column(name = "location" )
	private String location;

	/**
	 * 管径
	 */
	@Column(name = "pipe_diameter" )
	private String pipeDiameter;

	/**
	 * 备注
	 */
	@Column(name = "memo" )
	private String memo;

	/**
	 * 现场情况
	 */
	@Column(name = "site_conditions" )
	private String siteConditions;

	/**
	 * 现场情况说明
	 */
	@Column(name = "site_conditions_desc" )
	private String siteConditionsDesc;

	/**
	 * 隐患地点
	 */
	@Column(name = "hidden_danger_address" )
	private String hiddenDangerAddress;

	/**
	 * 隐患原因
	 */
	@Column(name = "hidden_danger_reason" )
	private String hiddenDangerReason;

	/**
	 * 排放地点
	 */
	@Column(name = "discharge_address" )
	private String dischargeAddress;

	/**
	 * 排放时长
	 */
	@Column(name = "discharge_time_last" )
	private String dischargeTimeLast;

	/**
	 * 排放时间
	 */
	@Column(name = "discharge_time" )
	private String dischargeTime;

	/**
	 * 估算排水量
	 */
	@Column(name = "estimated_discharge" )
	private String estimatedDischarge;

	/**
	 * 路线类型
	 */
	@Column(name = "route_type" )
	private String routeType;

	/**
	 * 定点巡查类型
	 */
	@Column(name = "point_inspection_type" )
	private String pointInspectionType;

	/**
	 * 签到点状态
	 */
	@Column(name = "sign_point_status" )
	private String signPointStatus;

	/**
	 * 修改时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(
			pattern = "yyyy-MM-dd HH:mm:ss",
			timezone = "GMT+8"
	)
	@Column(name = "modify_time" )
	private Date modifyTime;

	/**
	 * 签到时间
	 */
	@Column(name = "signed_time" )
	private String signedTime;

	/**
	 * 任务表id
	 */
	@Column(name = "task_id" )
	private String taskId;

	/**
	 * 文件类型
	 */
	@Column(name = "file_type" )
	private String fileType;

	/**
	 * 签到点类型
	 */
	@Column(name = "sign_point_type" )
	private String signPointType;

	/**
	 * 附件
	 */
    @Transient
	private List<HashMap<String, Object>> fileList;

	/**
	 * 是否到位
	 */
	@Column(name = "in_place" )
	private String inPlace;

	/**
	 * 未到位原因
	 */
	@Column(name = "not_in_place_reason" )
	private String notInPlaceReason;


	/**
	 * 计划id
	 */
	@Column(name = "plan_id" )
	private Integer planId;

	/**
	 * 操作人员
	 */
	@Column(name = "opration_by" )
	private String oprationBy;

	/**
	 * 距离
	 */
	@Column(name = "distance" )
	private String distance;


	public BizSignedPoint() {
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public BizSignedPoint(Integer id, Integer signPointCode, String currentChoosedAddress, String troubleCode, String troubleReason, String hiddenDangerType, String handleSuggestion, Integer enabled, Integer routeId, String location, String pipeDiameter, String memo, String siteConditions, String siteConditionsDesc, String hiddenDangerAddress, String hiddenDangerReason, String dischargeAddress, String dischargeTimeLast, String dischargeTime, String estimatedDischarge, String routeType, String pointInspectionType, String signPointStatus, Date modifyTime, String signedTime, String taskId, String fileType, String signPointType, List<HashMap<String, Object>> fileList, String inPlace, String notInPlaceReason, Integer planId, String oprationBy, String distance) {
		this.id = id;
		this.signPointCode = signPointCode;
		this.currentChoosedAddress = currentChoosedAddress;
		this.troubleCode = troubleCode;
		this.troubleReason = troubleReason;
		this.hiddenDangerType = hiddenDangerType;
		this.handleSuggestion = handleSuggestion;
		this.enabled = enabled;
		this.routeId = routeId;
		this.location = location;
		this.pipeDiameter = pipeDiameter;
		this.memo = memo;
		this.siteConditions = siteConditions;
		this.siteConditionsDesc = siteConditionsDesc;
		this.hiddenDangerAddress = hiddenDangerAddress;
		this.hiddenDangerReason = hiddenDangerReason;
		this.dischargeAddress = dischargeAddress;
		this.dischargeTimeLast = dischargeTimeLast;
		this.dischargeTime = dischargeTime;
		this.estimatedDischarge = estimatedDischarge;
		this.routeType = routeType;
		this.pointInspectionType = pointInspectionType;
		this.signPointStatus = signPointStatus;
		this.modifyTime = modifyTime;
		this.signedTime = signedTime;
		this.taskId = taskId;
		this.fileType = fileType;
		this.signPointType = signPointType;
		this.fileList = fileList;
		this.inPlace = inPlace;
		this.notInPlaceReason = notInPlaceReason;
		this.planId = planId;
		this.oprationBy = oprationBy;
		this.distance = distance;
	}

	public String getDischargeTime() {
		return dischargeTime;
	}

	public void setDischargeTime(String dischargeTime) {
		this.dischargeTime = dischargeTime;
	}

	public String getOprationBy() {
		return oprationBy;
	}

	public void setOprationBy(String oprationBy) {
		this.oprationBy = oprationBy;
	}

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public String getNotInPlaceReason() {
		return notInPlaceReason;
	}

	public void setNotInPlaceReason(String notInPlaceReason) {
		this.notInPlaceReason = notInPlaceReason;
	}

	public String getInPlace() {
		return inPlace;
	}

	public void setInPlace(String inPlace) {
		this.inPlace = inPlace;
	}

	public String getSignedTime() {
		return signedTime;
	}

	public void setSignedTime(String signedTime) {
		this.signedTime = signedTime;
	}

	public List<HashMap<String, Object>> getFileList() {
		return fileList;
	}

	public void setFileList(List<HashMap<String, Object>> fileList) {
		this.fileList = fileList;
	}

	public String getSignPointType() {
		return signPointType;
	}

	public void setSignPointType(String signPointType) {
		this.signPointType = signPointType;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSignPointCode() {
		return signPointCode;
	}

	public void setSignPointCode(Integer signPointCode) {
		this.signPointCode = signPointCode;
	}

	public String getCurrentChoosedAddress() {
		return currentChoosedAddress;
	}

	public void setCurrentChoosedAddress(String currentChoosedAddress) {
		this.currentChoosedAddress = currentChoosedAddress;
	}

	public String getTroubleCode() {
		return troubleCode;
	}

	public void setTroubleCode(String troubleCode) {
		this.troubleCode = troubleCode;
	}

	public String getTroubleReason() {
		return troubleReason;
	}

	public void setTroubleReason(String troubleReason) {
		this.troubleReason = troubleReason;
	}

	public String getHiddenDangerType() {
		return hiddenDangerType;
	}

	public void setHiddenDangerType(String hiddenDangerType) {
		this.hiddenDangerType = hiddenDangerType;
	}

	public String getHandleSuggestion() {
		return handleSuggestion;
	}

	public void setHandleSuggestion(String handleSuggestion) {
		this.handleSuggestion = handleSuggestion;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public Integer getRouteId() {
		return routeId;
	}

	public void setRouteId(Integer routeId) {
		this.routeId = routeId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPipeDiameter() {
		return pipeDiameter;
	}

	public void setPipeDiameter(String pipeDiameter) {
		this.pipeDiameter = pipeDiameter;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getSiteConditions() {
		return siteConditions;
	}

	public void setSiteConditions(String siteConditions) {
		this.siteConditions = siteConditions;
	}

	public String getSiteConditionsDesc() {
		return siteConditionsDesc;
	}

	public void setSiteConditionsDesc(String siteConditionsDesc) {
		this.siteConditionsDesc = siteConditionsDesc;
	}

	public String getHiddenDangerAddress() {
		return hiddenDangerAddress;
	}

	public void setHiddenDangerAddress(String hiddenDangerAddress) {
		this.hiddenDangerAddress = hiddenDangerAddress;
	}

	public String getHiddenDangerReason() {
		return hiddenDangerReason;
	}

	public void setHiddenDangerReason(String hiddenDangerReason) {
		this.hiddenDangerReason = hiddenDangerReason;
	}

	public String getDischargeAddress() {
		return dischargeAddress;
	}

	public void setDischargeAddress(String dischargeAddress) {
		this.dischargeAddress = dischargeAddress;
	}

	public String getDischargeTimeLast() {
		return dischargeTimeLast;
	}

	public void setDischargeTimeLast(String dischargeTimeLast) {
		this.dischargeTimeLast = dischargeTimeLast;
	}

	public String getEstimatedDischarge() {
		return estimatedDischarge;
	}

	public void setEstimatedDischarge(String estimatedDischarge) {
		this.estimatedDischarge = estimatedDischarge;
	}

	public String getRouteType() {
		return routeType;
	}

	public void setRouteType(String routeType) {
		this.routeType = routeType;
	}

	public String getPointInspectionType() {
		return pointInspectionType;
	}

	public void setPointInspectionType(String pointInspectionType) {
		this.pointInspectionType = pointInspectionType;
	}

	public String getSignPointStatus() {
		return signPointStatus;
	}

	public void setSignPointStatus(String signPointStatus) {
		this.signPointStatus = signPointStatus;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Override
	public String toString() {
		return "BizSignPoint{" +
				"id=" + id +
				", signPointCode='" + signPointCode + '\'' +
				", currentChoosedAddress='" + currentChoosedAddress + '\'' +
				", troubleCode='" + troubleCode + '\'' +
				", troubleReason='" + troubleReason + '\'' +
				", hiddenDangerType='" + hiddenDangerType + '\'' +
				", handleSuggestion='" + handleSuggestion + '\'' +
				", enabled=" + enabled +
				", routeId=" + routeId +
				", location='" + location + '\'' +
				", pipeDiameter='" + pipeDiameter + '\'' +
				", memo='" + memo + '\'' +
				", siteConditions='" + siteConditions + '\'' +
				", siteConditionsDesc='" + siteConditionsDesc + '\'' +
				", hiddenDangerAddress='" + hiddenDangerAddress + '\'' +
				", hiddenDangerReason='" + hiddenDangerReason + '\'' +
				", dischargeAddress='" + dischargeAddress + '\'' +
				", dischargeTimeLast='" + dischargeTimeLast + '\'' +
				", dischargeTime=" + dischargeTime +
				", estimatedDischarge='" + estimatedDischarge + '\'' +
				", routeType='" + routeType + '\'' +
				", pointInspectionType='" + pointInspectionType + '\'' +
				", signPointStatus='" + signPointStatus + '\'' +
				", modifyTime=" + modifyTime +
				'}';
	}
}
