package com.yaobanTech.springcloud.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	 * 计划类型
	 */
   	@Column(name = "plan_type" )
	private String planType;

	/**
	 * 开始时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(
			pattern = "yyyy-MM-dd",
			timezone = "GMT+8"
	)
   	@Column(name = "start_time" )
	private Date startTime;

	/**
	 * 结束时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(
			pattern = "yyyy-MM-dd",
			timezone = "GMT+8"
	)
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
	private String planCreatedTime;

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
	 * 区域
	 */
   	@Column(name = "area" )
	private String area;

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

   	/**
	 * 路线表id
	 */
   	@Column(name = "route_id" )
	private Integer routeId;

	/**
	 * 关键字
	 */
	@Column(name = "main_key" )
	private String mainKey;

	/**
	 * 问题编号
	 */
	@Column(name = "trouble_code" )
	private String troubleCode;

	/**
	 * 路线对象
	 */
    @Transient
	private Object routeObj;

	/**
	 * 计划类型枚举
	 */
    @Transient
	private Map<String,Object> planTypeMenu;

    /**
	 * 计划周期枚举
	 */
    @Transient
	private Map<String,Object> planPoridMenu;

    /**
	 * 计划状态枚举
	 */
    @Transient
	private Map<String,Object> planStatusMenu;

	/**
	 * 路线名称
	 */
	@Transient
	private String routeName;

	/**
	 * 附件
	 */
	@Transient
	private List<HashMap<String,Object>> attachment;

    /**
	 * 计划制定人
	 */
    @Transient
	private String planCreatedByCN;

	/**
	 * 用水管理所
	 */
	@Column(name="water_use_office")
	private String waterUseOffice;

	/**
	 * 计划制定人
	 */
	@Transient
	private HashMap<String,Object> waterUseOfficeEnum;

	/**
	 * 审批时间
	 */
	@Column(name="examine_date")
	private String examineDate;

	/**
	 * 审批意见
	 */
	@Column(name="examine_advice")
	private String examineAdvice;

	public BizPlan() {
	}

	public BizPlan(Integer id, String planName, String planType, Date startTime, Date endTime, String planCreatedBy, String planCreatedTime, String planStatus, String actProcess, String planProcess, String planPorid, String taskDesc, String area, String memo, Integer enabled, Integer routeId, String mainKey, String troubleCode, Object routeObj, Map<String, Object> planTypeMenu, Map<String, Object> planPoridMenu, Map<String, Object> planStatusMenu, String routeName, List<HashMap<String, Object>> attachment, String planCreatedByCN, String waterUseOffice, HashMap<String, Object> waterUseOfficeEnum, String examineDate, String examineAdvice) {
		this.id = id;
		this.planName = planName;
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
		this.area = area;
		this.memo = memo;
		this.enabled = enabled;
		this.routeId = routeId;
		this.mainKey = mainKey;
		this.troubleCode = troubleCode;
		this.routeObj = routeObj;
		this.planTypeMenu = planTypeMenu;
		this.planPoridMenu = planPoridMenu;
		this.planStatusMenu = planStatusMenu;
		this.routeName = routeName;
		this.attachment = attachment;
		this.planCreatedByCN = planCreatedByCN;
		this.waterUseOffice = waterUseOffice;
		this.waterUseOfficeEnum = waterUseOfficeEnum;
		this.examineDate = examineDate;
		this.examineAdvice = examineAdvice;
	}


	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public String getExamineDate() {
		return examineDate;
	}

	public void setExamineDate(String examineDate) {
		this.examineDate = examineDate;
	}

	public String getExamineAdvice() {
		return examineAdvice;
	}

	public void setExamineAdvice(String examineAdvice) {
		this.examineAdvice = examineAdvice;
	}

	public String getWaterUseOffice() {
		return waterUseOffice;
	}

	public void setWaterUseOffice(String waterUseOffice) {
		this.waterUseOffice = waterUseOffice;
	}

	public HashMap<String, Object> getWaterUseOfficeEnum() {
		return waterUseOfficeEnum;
	}

	public void setWaterUseOfficeEnum(HashMap<String, Object> waterUseOfficeEnum) {
		this.waterUseOfficeEnum = waterUseOfficeEnum;
	}

	public List<HashMap<String, Object>> getAttachment() {
		return attachment;
	}

	public void setAttachment(List<HashMap<String, Object>> attachment) {
		this.attachment = attachment;
	}

	public String getPlanCreatedByCN() {
		return planCreatedByCN;
	}

	public void setPlanCreatedByCN(String planCreatedByCN) {
		this.planCreatedByCN = planCreatedByCN;
	}

	public Map<String, Object> getPlanPoridMenu() {
		return planPoridMenu;
	}

	public void setPlanPoridMenu(Map<String, Object> planPoridMenu) {
		this.planPoridMenu = planPoridMenu;
	}

	public Map<String, Object> getPlanStatusMenu() {
		return planStatusMenu;
	}

	public void setPlanStatusMenu(Map<String, Object> planStatusMenu) {
		this.planStatusMenu = planStatusMenu;
	}

	public Object getRouteObj() {
		return routeObj;
	}

	public void setRouteObj(Object routeObj) {
		this.routeObj = routeObj;
	}

	public Map<String, Object> getPlanTypeMenu() {
		return planTypeMenu;
	}

	public void setPlanTypeMenu(Map<String, Object> planTypeMenu) {
		this.planTypeMenu = planTypeMenu;
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

	public String getPlanCreatedTime() {
		return planCreatedTime;
	}

	public void setPlanCreatedTime(String planCreatedTime) {
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

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
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

	public Integer getRouteId() {
		return routeId;
	}

	public void setRouteId(Integer routeId) {
		this.routeId = routeId;
	}

	public String getMainKey() {
		return mainKey;
	}

	public void setMainKey(String mainKey) {
		this.mainKey = mainKey;
	}

	public String getTroubleCode() {
		return troubleCode;
	}

	public void setTroubleCode(String troubleCode) {
		this.troubleCode = troubleCode;
	}

	@Override
	public String toString() {
		return "BizPlan{" +
				"id=" + id +
				", planName='" + planName + '\'' +
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
				", area='" + area + '\'' +
				", memo='" + memo + '\'' +
				", enabled=" + enabled +
				", routeId=" + routeId +
				", mainKey='" + mainKey + '\'' +
				", troubleCode='" + troubleCode + '\'' +
				'}';
	}
}
