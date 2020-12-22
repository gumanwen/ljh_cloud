package com.yaobanTech.springcloud.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	private Enum waterManagementOffice;

	/**
	 * 状态
	 */
   	@Column(name = "status" )
	private Enum status;

	/**
	 * 路线名称
	 */
   	@Column(name = "route_name" )
	private String routeName;

	/**
	 * 路线类型
	 */
   	@Column(name = "route_type" )
	private Enum routeType;

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
	private Double pipeDiameter;

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
	private Enum pointInspectionType;

   	@OneToMany(targetEntity = BizSignPoint.class)
	@JoinColumn(name = "route_id",referencedColumnName = "id")
   	private List<BizSignPoint> bizSignPoints = new ArrayList<>();

	public BizRoute() {
	}

	public BizRoute(Integer id, Enum waterManagementOffice, Enum status, String routeName, Enum routeType, String routeCreator, Integer signIn, Double planInspectionMileage, Date createdTime, Integer hiddenDangerAmount, Double pipeDiameter, Double actInspectionMileage, Integer enabled, String location, Enum pointInspectionType, List<BizSignPoint> bizSignPoints) {
		this.id = id;
		this.waterManagementOffice = waterManagementOffice;
		this.status = status;
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
		this.bizSignPoints = bizSignPoints;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Enum getWaterManagementOffice() {
		return waterManagementOffice;
	}

	public void setWaterManagementOffice(Enum waterManagementOffice) {
		this.waterManagementOffice = waterManagementOffice;
	}

	public Enum getStatus() {
		return status;
	}

	public void setStatus(Enum status) {
		this.status = status;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public Enum getRouteType() {
		return routeType;
	}

	public void setRouteType(Enum routeType) {
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

	public Double getPipeDiameter() {
		return pipeDiameter;
	}

	public void setPipeDiameter(Double pipeDiameter) {
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

	public Enum getPointInspectionType() {
		return pointInspectionType;
	}

	public void setPointInspectionType(Enum pointInspectionType) {
		this.pointInspectionType = pointInspectionType;
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
				", waterManagementOffice=" + waterManagementOffice +
				", status=" + status +
				", routeName='" + routeName + '\'' +
				", routeType=" + routeType +
				", routeCreator='" + routeCreator + '\'' +
				", signIn=" + signIn +
				", planInspectionMileage=" + planInspectionMileage +
				", createdTime=" + createdTime +
				", hiddenDangerAmount=" + hiddenDangerAmount +
				", pipeDiameter=" + pipeDiameter +
				", actInspectionMileage=" + actInspectionMileage +
				", enabled=" + enabled +
				", location='" + location + '\'' +
				", pointInspectionType=" + pointInspectionType +
				", bizSignPoints=" + bizSignPoints +
				'}';
	}
}
