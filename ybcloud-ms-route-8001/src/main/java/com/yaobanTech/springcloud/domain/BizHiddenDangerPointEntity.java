package com.yaobanTech.springcloud.domain;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;

/**
 * @Description  
 * @Author  yuxy
 * * @Date 2021-01-22 11:25:28
 */

@Entity
@Table ( name ="biz_hidden_danger_point")
public class BizHiddenDangerPointEntity {


	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id" )
	private Integer id;

	/**
	 * 隐患点编号
	 */
   @Column(name = "hidden_danger_point_code" )
	private String hiddenDangerPointCode;

	/**
	 * 路线id
	 */
   @Column(name = "route_id" )
	private Integer routeId;

	/**
	 * 用水管理所
	 */
   @Column(name = "water_use_office" )
	private String waterUseOffice;

	/**
	 * 工地名称
	 */
   @Column(name = "project_name" )
	private String projectName;

	/**
	 * 施工位置
	 */
   @Column(name = "construction_position" )
	private String constructionPosition;

	/**
	 * 建设单位
	 */
   @Column(name = "build_company" )
	private String buildCompany;

	/**
	 * 建设单位负责人
	 */
   @Column(name = "build_company_agent" )
	private String buildCompanyAgent;

	/**
	 * 建设单位负责人电话
	 */
   @Column(name = "build_company_agent_phone" )
	private String buildCompanyAgentPhone;

	/**
	 * 施工单位
	 */
   @Column(name = "construction_company" )
	private String constructionCompany;

	/**
	 * 施工单位负责人电话
	 */
   @Column(name = "construction_company_agent_phone" )
	private String constructionCompanyAgentPhone;

	/**
	 * 设备口径
	 */
   @Column(name = "equipment_size" )
	private String networkSize;

	/**
	 * 工地类型
	 */
   @Column(name = "project_type" )
	private String projectType;

	/**
	 * 施工方式
	 */
   @Column(name = "construction_type" )
	private String constructionType;

	/**
	 * 风险等级
	 */
   @Column(name = "risk_level" )
	private String riskLevel;

	/**
	 * 保护供水管网设施告知书
	 */
   @Column(name = "network_notification" )
	private String networkNotification;

	/**
	 * 备注
	 */
   @Column(name = "memo" )
	private String memo;

	/**
	 * 提交人员
	 */
   @Column(name = "commit_by" )
	private String commitBy;

   /**
	 * 提交
	 */
   @Column(name = "opration" )
	private String opration;

	/**
	 * 提交时间
	 */
	@Column(name = "commit_date" )
	private String commitDate;

	/**
	 * 是否有效
	 */
   @Column(name = "enabled" )
	private Integer enabled;

	/**
	 * 隐患状态
	 */
	@Column(name = "hidden_danger_point_status" )
	private String hiddenDangerStatus;

	/**
	 * 结束时间
	 */
	@Column(name = "end_date" )
	private String endDate;

	/**
	 * 隐患原因
	 */
	@Column(name = "reason" )
	private String reason;

	/**
	 * 施工开始时间
	 */
	@Column(name = "construction_start_date" )
	private String constructionStartDate;

	/**
	 * 施工结束时间
	 */
	@Column(name = "construction_end_date" )
	private String constructionEndDate;

	/**
	 * x坐标
	 */
	@Column(name = "x" )
	private String x;

	/**
	 * x坐标
	 */
	@Column(name = "y" )
	private String y;

	/**
	 * 隐患时长
	 */
	@Transient
	private String hiddenLast;

	/**
	 * 处理意见
	 */
	@Transient
	private List<BizSuggestionEntity> handleAdvice;
	/**
	 * 提交人
	 */
	@Transient
	private String commitByCN;

	/**
	 * 隐患点状态枚举
	 */
	@Transient
	private HashMap<String,Object> hiddenDangerPointStatusEnum;

	/**
	 * 工地类型枚举
	 */
	@Transient
	private HashMap<String,Object> projectTypeEnum;

	/**
	 * 风险等级枚举
	 */
	@Transient
	private HashMap<String,Object> riskLevelEnum;

	/**
	 * 施工方式枚举
	 */
	@Transient
	private HashMap<String,Object> constructionTypeEnum;

	/**
	 * app附件
	 */
	@Transient
	private List<HashMap<String,Object>> fileList;

	/**
	 * 施工方式数组
	 */
	@Transient
	private String[] sgfs;

	/**
	 * 工地类型数组
	 */
	@Transient
	private String[] gdlx;



	public BizHiddenDangerPointEntity() {
	}

	public BizHiddenDangerPointEntity(Integer id, String hiddenDangerPointCode, Integer routeId, String waterUseOffice, String projectName, String constructionPosition, String buildCompany, String buildCompanyAgent, String buildCompanyAgentPhone, String constructionCompany, String constructionCompanyAgentPhone, String networkSize, String projectType, String constructionType, String riskLevel, String networkNotification, String memo, String commitBy, String opration, String commitDate, Integer enabled, String hiddenDangerStatus, String endDate, String hiddenLast, List<BizSuggestionEntity> handleAdvice, String commitByCN, HashMap<String, Object> hiddenDangerPointStatusEnum, HashMap<String, Object> projectTypeEnum, HashMap<String, Object> riskLevelEnum, HashMap<String, Object> constructionTypeEnum) {
		this.id = id;
		this.hiddenDangerPointCode = hiddenDangerPointCode;
		this.routeId = routeId;
		this.waterUseOffice = waterUseOffice;
		this.projectName = projectName;
		this.constructionPosition = constructionPosition;
		this.buildCompany = buildCompany;
		this.buildCompanyAgent = buildCompanyAgent;
		this.buildCompanyAgentPhone = buildCompanyAgentPhone;
		this.constructionCompany = constructionCompany;
		this.constructionCompanyAgentPhone = constructionCompanyAgentPhone;
		this.networkSize = networkSize;
		this.projectType = projectType;
		this.constructionType = constructionType;
		this.riskLevel = riskLevel;
		this.networkNotification = networkNotification;
		this.memo = memo;
		this.commitBy = commitBy;
		this.opration = opration;
		this.commitDate = commitDate;
		this.enabled = enabled;
		this.hiddenDangerStatus = hiddenDangerStatus;
		this.endDate = endDate;
		this.hiddenLast = hiddenLast;
		this.handleAdvice = handleAdvice;
		this.commitByCN = commitByCN;
		this.hiddenDangerPointStatusEnum = hiddenDangerPointStatusEnum;
		this.projectTypeEnum = projectTypeEnum;
		this.riskLevelEnum = riskLevelEnum;
		this.constructionTypeEnum = constructionTypeEnum;
	}

	public String[] getSgfs() {
		return sgfs;
	}

	public void setSgfs(String[] sgfs) {
		this.sgfs = sgfs;
	}

	public String[] getGdlx() {
		return gdlx;
	}

	public void setGdlx(String[] gdlx) {
		this.gdlx = gdlx;
	}

	public String getConstructionStartDate() {
		return constructionStartDate;
	}

	public void setConstructionStartDate(String constructionStartDate) {
		this.constructionStartDate = constructionStartDate;
	}

	public String getConstructionEndDate() {
		return constructionEndDate;
	}

	public void setConstructionEndDate(String constructionEndDate) {
		this.constructionEndDate = constructionEndDate;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public List<HashMap<String, Object>> getFileList() {
		return fileList;
	}

	public void setFileList(List<HashMap<String, Object>> fileList) {
		this.fileList = fileList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHiddenDangerPointCode() {
		return hiddenDangerPointCode;
	}

	public void setHiddenDangerPointCode(String hiddenDangerPointCode) {
		this.hiddenDangerPointCode = hiddenDangerPointCode;
	}

	public Integer getRouteId() {
		return routeId;
	}

	public void setRouteId(Integer routeId) {
		this.routeId = routeId;
	}

	public String getWaterUseOffice() {
		return waterUseOffice;
	}

	public void setWaterUseOffice(String waterUseOffice) {
		this.waterUseOffice = waterUseOffice;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getConstructionPosition() {
		return constructionPosition;
	}

	public void setConstructionPosition(String constructionPosition) {
		this.constructionPosition = constructionPosition;
	}

	public String getBuildCompany() {
		return buildCompany;
	}

	public void setBuildCompany(String buildCompany) {
		this.buildCompany = buildCompany;
	}

	public String getBuildCompanyAgent() {
		return buildCompanyAgent;
	}

	public void setBuildCompanyAgent(String buildCompanyAgent) {
		this.buildCompanyAgent = buildCompanyAgent;
	}

	public String getBuildCompanyAgentPhone() {
		return buildCompanyAgentPhone;
	}

	public void setBuildCompanyAgentPhone(String buildCompanyAgentPhone) {
		this.buildCompanyAgentPhone = buildCompanyAgentPhone;
	}

	public String getConstructionCompany() {
		return constructionCompany;
	}

	public void setConstructionCompany(String constructionCompany) {
		this.constructionCompany = constructionCompany;
	}

	public String getConstructionCompanyAgentPhone() {
		return constructionCompanyAgentPhone;
	}

	public void setConstructionCompanyAgentPhone(String constructionCompanyAgentPhone) {
		this.constructionCompanyAgentPhone = constructionCompanyAgentPhone;
	}

	public String getNetworkSize() {
		return networkSize;
	}

	public void setNetworkSize(String networkSize) {
		this.networkSize = networkSize;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getConstructionType() {
		return constructionType;
	}

	public void setConstructionType(String constructionType) {
		this.constructionType = constructionType;
	}

	public String getRiskLevel() {
		return riskLevel;
	}

	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}

	public String getNetworkNotification() {
		return networkNotification;
	}

	public void setNetworkNotification(String networkNotification) {
		this.networkNotification = networkNotification;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getCommitBy() {
		return commitBy;
	}

	public void setCommitBy(String commitBy) {
		this.commitBy = commitBy;
	}

	public String getOpration() {
		return opration;
	}

	public void setOpration(String opration) {
		this.opration = opration;
	}

	public String getCommitDate() {
		return commitDate;
	}

	public void setCommitDate(String commitDate) {
		this.commitDate = commitDate;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public String getHiddenDangerStatus() {
		return hiddenDangerStatus;
	}

	public void setHiddenDangerStatus(String hiddenDangerStatus) {
		this.hiddenDangerStatus = hiddenDangerStatus;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getHiddenLast() {
		return hiddenLast;
	}

	public void setHiddenLast(String hiddenLast) {
		this.hiddenLast = hiddenLast;
	}

	public List<BizSuggestionEntity> getHandleAdvice() {
		return handleAdvice;
	}

	public void setHandleAdvice(List<BizSuggestionEntity> handleAdvice) {
		this.handleAdvice = handleAdvice;
	}

	public String getCommitByCN() {
		return commitByCN;
	}

	public void setCommitByCN(String commitByCN) {
		this.commitByCN = commitByCN;
	}

	public HashMap<String, Object> getHiddenDangerPointStatusEnum() {
		return hiddenDangerPointStatusEnum;
	}

	public void setHiddenDangerPointStatusEnum(HashMap<String, Object> hiddenDangerPointStatusEnum) {
		this.hiddenDangerPointStatusEnum = hiddenDangerPointStatusEnum;
	}

	public HashMap<String, Object> getProjectTypeEnum() {
		return projectTypeEnum;
	}

	public void setProjectTypeEnum(HashMap<String, Object> projectTypeEnum) {
		this.projectTypeEnum = projectTypeEnum;
	}

	public HashMap<String, Object> getRiskLevelEnum() {
		return riskLevelEnum;
	}

	public void setRiskLevelEnum(HashMap<String, Object> riskLevelEnum) {
		this.riskLevelEnum = riskLevelEnum;
	}

	public HashMap<String, Object> getConstructionTypeEnum() {
		return constructionTypeEnum;
	}

	public void setConstructionTypeEnum(HashMap<String, Object> constructionTypeEnum) {
		this.constructionTypeEnum = constructionTypeEnum;
	}

	@Override
	public String toString() {
		return "BizHiddenDangerPointEntity{" +
				"id=" + id +
				", hiddenDangerPointCode='" + hiddenDangerPointCode + '\'' +
				", routeId=" + routeId +
				", waterUseOffice='" + waterUseOffice + '\'' +
				", projectName='" + projectName + '\'' +
				", constructionPosition='" + constructionPosition + '\'' +
				", buildCompany='" + buildCompany + '\'' +
				", buildCompanyAgent='" + buildCompanyAgent + '\'' +
				", buildCompanyAgentPhone='" + buildCompanyAgentPhone + '\'' +
				", constructionCompany='" + constructionCompany + '\'' +
				", constructionCompanyAgentPhone='" + constructionCompanyAgentPhone + '\'' +
				", networkSize='" + networkSize + '\'' +
				", projectType='" + projectType + '\'' +
				", constructionType='" + constructionType + '\'' +
				", riskLevel='" + riskLevel + '\'' +
				", networkNotification='" + networkNotification + '\'' +
				", memo='" + memo + '\'' +
				", commitBy='" + commitBy + '\'' +
				", opration='" + opration + '\'' +
				", commitDate='" + commitDate + '\'' +
				", enabled=" + enabled +
				", hiddenDangerStatus='" + hiddenDangerStatus + '\'' +
				", endDate='" + endDate + '\'' +
				", hiddenLast='" + hiddenLast + '\'' +
				", handleAdvice=" + handleAdvice +
				", commitByCN='" + commitByCN + '\'' +
				", hiddenDangerPointStatusEnum=" + hiddenDangerPointStatusEnum +
				", projectTypeEnum=" + projectTypeEnum +
				", riskLevelEnum=" + riskLevelEnum +
				", constructionTypeEnum=" + constructionTypeEnum +
				'}';
	}
}
