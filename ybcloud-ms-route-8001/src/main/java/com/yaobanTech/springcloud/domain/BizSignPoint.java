package com.yaobanTech.springcloud.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description  
 * @Author  yuxy
 * @Date 2020-12-22 
 */

@Entity
@Table ( name ="biz_sign_point" )
public class BizSignPoint  implements Serializable {

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
	private String signPointCode;

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
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(
			pattern = "yyyy-MM-dd HH:mm:ss",
			timezone = "GMT+8"
	)
	@Column(name = "discharge_time" )
	private Date dischargeTime;

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
	 * 签到点类型
	 */
	@Column(name = "sign_point_type" )
	private String signPointType;

	public BizSignPoint() {
	}

	public BizSignPoint(Integer id, String signPointCode, String currentChoosedAddress, String troubleCode, String troubleReason, String hiddenDangerType, String handleSuggestion, Integer enabled, Integer routeId, String location, String pipeDiameter, String memo, String siteConditions, String siteConditionsDesc, String hiddenDangerAddress, String hiddenDangerReason, String dischargeAddress, String dischargeTimeLast, Date dischargeTime, String estimatedDischarge, String routeType, String pointInspectionType, String signPointStatus, Date modifyTime) {
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
	}

	public String getSignPointType() {
		return signPointType;
	}

	public void setSignPointType(String signPointType) {
		this.signPointType = signPointType;
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

	public String getSignPointCode() {
		return signPointCode;
	}

	public void setSignPointCode(String signPointCode) {
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

	public Date getDischargeTime() {
		return dischargeTime;
	}

	public void setDischargeTime(Date dischargeTime) {
		this.dischargeTime = dischargeTime;
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
