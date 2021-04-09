package com.yaobanTech.springcloud.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description  
 * @Author  yuxy
 * @Date 2020-12-22 
 */

@Entity
@Table ( name ="biz_route" )
public class BizRoute  implements Serializable {

	private static final long serialVersionUID =  3387266055639620567L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
   	@Column(name = "id" )
	private Integer id;

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
	 * 路线类型
	 */
   	@Column(name = "route_type" )
	private String routeType;

	/**
	 * 路线制定人
	 */
   	@Column(name = "route_creator" )
	private String routeCreator;

	/**
	 *签到点数
	 */
   	@Column(name = "sign_in" )
	private Integer signIn;

	/**
	 * 计划巡查里程
	 */
   	@Column(name = "plan_inspection_mileage" )
	private Double planInspectionMileage;

	/**
	 * 制定时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(
			pattern = "yyyy-MM-dd HH:mm:ss",
			timezone = "GMT+8"
	)
   	@Column(name = "created_time" )
	private Date createdTime;

	/**
	 * 隐患时长
	 */
   	@Column(name = "hidden_danger_amount" )
	private Integer hiddenDangerAmount;

	/**
	 * 管径
	 */
   	@Column(name = "pipe_diameter" )
	private String pipeDiameter;

	/**
	 * 实际巡查里程
	 */
   	@Column(name = "act_inspection_mileage" )
	private Double actInspectionMileage;

	/**
	 * 是否有效
	 */
   	@Column(name = "enabled" )
	private Integer enabled;

	/**
	 * 坐标
	 */
   	@Column(name = "location" )
	private String location;

	/**
	 * 定点巡查类型
	 */
   	@Column(name = "point_inspection_type" )
	private String pointInspectionType;

   	/**
	 * 隐患原因
	 */
   	@Column(name = "hidden_danger_reason" )
	private String hiddenDangerReason;

	/**
	 * 超时原因
	 */
	@Column(name = "over_reason" )
	private String overReason;

	/**
	 * 修改人
	 */
	@Column(name = "modify_by" )
	private String modifyBy;

	/**
	 * 图片地址
	 */
	@Column(name = "addr" )
	private String addr;

	/**
	 * 用水管理所枚举对象
	 */
    @Transient
	private Map<String,Object> waterOfficeMenu;

	/**
	 * 用水管理所枚举对象
	 */
	@Transient
	private Map<String,Object> routeTypeMenu;

	/**
	 * 定点巡查类型枚举对象
	 */
	@Transient
	private Map<String,Object> pointInspectionTypeMenu;

	/**
	 * 路线制定人
	 */
	@Transient
	private String routeCreatorCN;

	/**
	 * 签到点
	 */
   	@OneToMany(targetEntity = BizSignPoint.class,fetch = FetchType.EAGER)
	@JoinColumn(name = "route_id",referencedColumnName = "id")
   	private List<BizSignPoint> bizSignPoints = new ArrayList<>();

	public BizRoute() {
	}

	public BizRoute(Integer id, String waterManagementOffice, String routeName, String routeType, String routeCreator, Integer signIn, Double planInspectionMileage, Date createdTime, Integer hiddenDangerAmount, String pipeDiameter, Double actInspectionMileage, Integer enabled, String location, String pointInspectionType, String hiddenDangerReason, String overReason, String modifyBy, String addr, Map<String, Object> waterOfficeMenu, Map<String, Object> routeTypeMenu, Map<String, Object> pointInspectionTypeMenu, String routeCreatorCN, List<BizSignPoint> bizSignPoints) {
		this.id = id;
		this.waterManagementOffice = waterManagementOffice;
		this.routeName = routeName;
		this.routeType = routeType;
		this.routeCreator = routeCreator;
		this.signIn = signIn;
		this.planInspectionMileage = planInspectionMileage;
		this.createdTime = createdTime;
		this.hiddenDangerAmount = hiddenDangerAmount;
		this.pipeDiameter = pipeDiameter;
		this.actInspectionMileage = actInspectionMileage;
		this.enabled = enabled;
		this.location = location;
		this.pointInspectionType = pointInspectionType;
		this.hiddenDangerReason = hiddenDangerReason;
		this.overReason = overReason;
		this.modifyBy = modifyBy;
		this.addr = addr;
		this.waterOfficeMenu = waterOfficeMenu;
		this.routeTypeMenu = routeTypeMenu;
		this.pointInspectionTypeMenu = pointInspectionTypeMenu;
		this.routeCreatorCN = routeCreatorCN;
		this.bizSignPoints = bizSignPoints;
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

	public String getRouteType() {
		return routeType;
	}

	public void setRouteType(String routeType) {
		this.routeType = routeType;
	}

	public String getRouteCreator() {
		return routeCreator;
	}

	public void setRouteCreator(String routeCreator) {
		this.routeCreator = routeCreator;
	}

	public Integer getSignIn() {
		return signIn;
	}

	public void setSignIn(Integer signIn) {
		this.signIn = signIn;
	}

	public Double getPlanInspectionMileage() {
		return planInspectionMileage;
	}

	public void setPlanInspectionMileage(Double planInspectionMileage) {
		this.planInspectionMileage = planInspectionMileage;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Integer getHiddenDangerAmount() {
		return hiddenDangerAmount;
	}

	public void setHiddenDangerAmount(Integer hiddenDangerAmount) {
		this.hiddenDangerAmount = hiddenDangerAmount;
	}

	public String getPipeDiameter() {
		return pipeDiameter;
	}

	public void setPipeDiameter(String pipeDiameter) {
		this.pipeDiameter = pipeDiameter;
	}

	public Double getActInspectionMileage() {
		return actInspectionMileage;
	}

	public void setActInspectionMileage(Double actInspectionMileage) {
		this.actInspectionMileage = actInspectionMileage;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPointInspectionType() {
		return pointInspectionType;
	}

	public void setPointInspectionType(String pointInspectionType) {
		this.pointInspectionType = pointInspectionType;
	}

	public String getHiddenDangerReason() {
		return hiddenDangerReason;
	}

	public void setHiddenDangerReason(String hiddenDangerReason) {
		this.hiddenDangerReason = hiddenDangerReason;
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

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public Map<String, Object> getWaterOfficeMenu() {
		return waterOfficeMenu;
	}

	public void setWaterOfficeMenu(Map<String, Object> waterOfficeMenu) {
		this.waterOfficeMenu = waterOfficeMenu;
	}

	public Map<String, Object> getRouteTypeMenu() {
		return routeTypeMenu;
	}

	public void setRouteTypeMenu(Map<String, Object> routeTypeMenu) {
		this.routeTypeMenu = routeTypeMenu;
	}

	public Map<String, Object> getPointInspectionTypeMenu() {
		return pointInspectionTypeMenu;
	}

	public void setPointInspectionTypeMenu(Map<String, Object> pointInspectionTypeMenu) {
		this.pointInspectionTypeMenu = pointInspectionTypeMenu;
	}

	public String getRouteCreatorCN() {
		return routeCreatorCN;
	}

	public void setRouteCreatorCN(String routeCreatorCN) {
		this.routeCreatorCN = routeCreatorCN;
	}

	public List<BizSignPoint> getBizSignPoints() {
		return bizSignPoints;
	}

	public void setBizSignPoints(List<BizSignPoint> bizSignPoints) {
		this.bizSignPoints = bizSignPoints;
	}

	@Override
	public String toString() {
		return "BizRoute{" +
				"id=" + id +
				", waterManagementOffice='" + waterManagementOffice + '\'' +
				", routeName='" + routeName + '\'' +
				", routeType='" + routeType + '\'' +
				", routeCreator='" + routeCreator + '\'' +
				", signIn=" + signIn +
				", planInspectionMileage=" + planInspectionMileage +
				", createdTime=" + createdTime +
				", hiddenDangerAmount=" + hiddenDangerAmount +
				", pipeDiameter=" + pipeDiameter +
				", actInspectionMileage=" + actInspectionMileage +
				", enabled=" + enabled +
				", location='" + location + '\'' +
				", pointInspectionType='" + pointInspectionType + '\'' +
				", bizSignPoints=" + bizSignPoints +
				'}';
	}
}
