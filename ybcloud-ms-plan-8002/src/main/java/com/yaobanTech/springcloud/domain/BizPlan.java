package com.yaobanTech.springcloud.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description  
 * @Author  yuxy
 * @Date 2020-12-24 
 */

@Entity
@Table ( name ="biz_plan" )
public class BizPlan  implements Serializable {

	private static final long serialVersionUID =  2251926673890410940L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
   	@Column(name = "id" )
	private Integer id;

	/**
	 * 计划名称
	 */
   	@Column(name = "plan_name" )
	private String planName;

	/**
	 * 用水管理所
	 */
   	@Column(name = "water_management_office" )
	private String waterManagementOffice;

	/**
	 * 路线名称
	 */
   	@Column(name = "route_name" )
	private String routeName;

	/**
	 * 计划类型
	 */
   	@Column(name = "plan_type" )
	private String planType;

	/**
	 * 开始时间
	 */
   	@Column(name = "start_time" )
	private Date startTime;

	/**
	 * 结束时间
	 */
   	@Column(name = "end_time" )
	private Date endTime;

	/**
	 * 计划制定人
	 */
   	@Column(name = "plan_created_by" )
	private String planCreatedBy;

	/**
	 * 计划制定时间
	 */
   	@Column(name = "plan_created_time" )
	private Date planCreatedTime;

	/**
	 * 计划状态
	 */
   	@Column(name = "plan_status" )
	private String planStatus;

	/**
	 * 实际进度
	 */
   	@Column(name = "act_process" )
	private String actProcess;

	/**
	 * 计划进度
	 */
   	@Column(name = "plan_process" )
	private String planProcess;

	/**
	 * 计划周期
	 */
   	@Column(name = "plan_porid" )
	private String planPorid;

	/**
	 * 任务描述
	 */
   	@Column(name = "task_desc" )
	private String taskDesc;

	/**
	 * 路线类型
	 */
   	@Column(name = "route_type" )
	private String routeType;

	/**
	 * 签到点数量
	 */
   	@Column(name = "sign_in" )
	private Integer signIn;

	/**
	 * 区域
	 */
   	@Column(name = "area" )
	private String area;

	/**
	 * 管径
	 */
   	@Column(name = "pipe_diameter" )
	private Double pipeDiameter;

	/**
	 * 定点巡查类型
	 */
   	@Column(name = "fixed_point_inspection_type" )
	private String fixedPointInspectionType;

	/**
	 * 计划巡查里程
	 */
   	@Column(name = "plan_inspection_mileage" )
	private Double planInspectionMileage;

	/**
	 * 路线制定人
	 */
   	@Column(name = "route_created_by" )
	private String routeCreatedBy;

	/**
	 * 隐患原因
	 */
   	@Column(name = "hidden_danger_reason" )
	private String hiddenDangerReason;

	/**
	 * 备注
	 */
   	@Column(name = "memo" )
	private String memo;

	/**
	 * 是否有效
	 */
   	@Column(name = "enabled" )
	private Integer enabled;

	public BizPlan() {
	}

	public BizPlan(Integer id, String planName, String waterManagementOffice, String routeName, String planType, Date startTime, Date endTime, String planCreatedBy, Date planCreatedTime, String planStatus, String actProcess, String planProcess, String planPorid, String taskDesc, String routeType, Integer signIn, String area, Double pipeDiameter, String fixedPointInspectionType, Double planInspectionMileage, String routeCreatedBy, String hiddenDangerReason, String memo, Integer enabled) {
		this.id = id;
		this.planName = planName;
		this.waterManagementOffice = waterManagementOffice;
		this.routeName = routeName;
		this.planType = planType;
		this.startTime = startTime;
		this.endTime = endTime;
		this.planCreatedBy = planCreatedBy;
		this.planCreatedTime = planCreatedTime;
		this.planStatus = planStatus;
		this.actProcess = actProcess;
		this.planProcess = planProcess;
		this.planPorid = planPorid;
		this.taskDesc = taskDesc;
		this.routeType = routeType;
		this.signIn = signIn;
		this.area = area;
		this.pipeDiameter = pipeDiameter;
		this.fixedPointInspectionType = fixedPointInspectionType;
		this.planInspectionMileage = planInspectionMileage;
		this.routeCreatedBy = routeCreatedBy;
		this.hiddenDangerReason = hiddenDangerReason;
		this.memo = memo;
		this.enabled = enabled;
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

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getWaterManagementOffice() {
		return waterManagementOffice;
	}

	public void setWaterManagementOffice(String waterManagementOffice) {
		this.waterManagementOffice = waterManagementOffice;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public String getPlanType() {
		return planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getPlanCreatedBy() {
		return planCreatedBy;
	}

	public void setPlanCreatedBy(String planCreatedBy) {
		this.planCreatedBy = planCreatedBy;
	}

	public Date getPlanCreatedTime() {
		return planCreatedTime;
	}

	public void setPlanCreatedTime(Date planCreatedTime) {
		this.planCreatedTime = planCreatedTime;
	}

	public String getPlanStatus() {
		return planStatus;
	}

	public void setPlanStatus(String planStatus) {
		this.planStatus = planStatus;
	}

	public String getActProcess() {
		return actProcess;
	}

	public void setActProcess(String actProcess) {
		this.actProcess = actProcess;
	}

	public String getPlanProcess() {
		return planProcess;
	}

	public void setPlanProcess(String planProcess) {
		this.planProcess = planProcess;
	}

	public String getPlanPorid() {
		return planPorid;
	}

	public void setPlanPorid(String planPorid) {
		this.planPorid = planPorid;
	}

	public String getTaskDesc() {
		return taskDesc;
	}

	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}

	public String getRouteType() {
		return routeType;
	}

	public void setRouteType(String routeType) {
		this.routeType = routeType;
	}

	public Integer getSignIn() {
		return signIn;
	}

	public void setSignIn(Integer signIn) {
		this.signIn = signIn;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Double getPipeDiameter() {
		return pipeDiameter;
	}

	public void setPipeDiameter(Double pipeDiameter) {
		this.pipeDiameter = pipeDiameter;
	}

	public String getFixedPointInspectionType() {
		return fixedPointInspectionType;
	}

	public void setFixedPointInspectionType(String fixedPointInspectionType) {
		this.fixedPointInspectionType = fixedPointInspectionType;
	}

	public Double getPlanInspectionMileage() {
		return planInspectionMileage;
	}

	public void setPlanInspectionMileage(Double planInspectionMileage) {
		this.planInspectionMileage = planInspectionMileage;
	}

	public String getRouteCreatedBy() {
		return routeCreatedBy;
	}

	public void setRouteCreatedBy(String routeCreatedBy) {
		this.routeCreatedBy = routeCreatedBy;
	}

	public String getHiddenDangerReason() {
		return hiddenDangerReason;
	}

	public void setHiddenDangerReason(String hiddenDangerReason) {
		this.hiddenDangerReason = hiddenDangerReason;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "BizPlan{" +
				"id=" + id +
				", planName='" + planName + '\'' +
				", waterManagementOffice='" + waterManagementOffice + '\'' +
				", routeName='" + routeName + '\'' +
				", planType='" + planType + '\'' +
				", startTime=" + startTime +
				", endTime=" + endTime +
				", planCreatedBy='" + planCreatedBy + '\'' +
				", planCreatedTime=" + planCreatedTime +
				", planStatus='" + planStatus + '\'' +
				", actProcess='" + actProcess + '\'' +
				", planProcess='" + planProcess + '\'' +
				", planPorid='" + planPorid + '\'' +
				", taskDesc='" + taskDesc + '\'' +
				", routeType='" + routeType + '\'' +
				", signIn=" + signIn +
				", area='" + area + '\'' +
				", pipeDiameter=" + pipeDiameter +
				", fixedPointInspectionType='" + fixedPointInspectionType + '\'' +
				", planInspectionMileage=" + planInspectionMileage +
				", routeCreatedBy='" + routeCreatedBy + '\'' +
				", hiddenDangerReason='" + hiddenDangerReason + '\'' +
				", memo='" + memo + '\'' +
				", enabled=" + enabled +
				'}';
	}
}
