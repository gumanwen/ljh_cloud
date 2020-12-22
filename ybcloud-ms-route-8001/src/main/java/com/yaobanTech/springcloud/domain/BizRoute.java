package com.yaobanTech.springcloud.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "biz_route")
public class BizRoute {
    //id
    private Integer id;
    //用水管理所
    private Enum waterManagementOffice;
    //状态
    private Enum status;
    //路线名称
    private String routeName;
    //路线类型
    private Enum routeType;
    //路线创建人
    private String routeCreator;
    //签到点
    private Integer signIn;
    //定点巡查类型
    private Enum fixedPointInspectionType;
    //计划巡查里程
    private Double planInspectionMileage;
    //创建时间
    private Date createdTime;
    //隐患时长
    private Integer hiddenDangerAmount;
    //管径
    private Double pipeDiameter;
    //实际巡查里程
    private Double actInspectionMileage;
    //是否有效
    private Integer enabled;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "water_management_office")
    public Enum getWaterManagementOffice() {
        return waterManagementOffice;
    }

    public void setWaterManagementOffice(Enum waterManagementOffice) {
        this.waterManagementOffice = waterManagementOffice;
    }

    @Basic
    @Column(name = "status")
    public Enum getStatus() {
        return status;
    }

    public void setStatus(Enum status) {
        this.status = status;
    }

    @Basic
    @Column(name = "route_name")
    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    @Basic
    @Column(name = "route_type")
    public Enum getRouteType() {
        return routeType;
    }

    public void setRouteType(Enum routeType) {
        this.routeType = routeType;
    }

    @Basic
    @Column(name = "route_creator")
    public String getRouteCreator() {
        return routeCreator;
    }

    public void setRouteCreator(String routeCreator) {
        this.routeCreator = routeCreator;
    }

    @Basic
    @Column(name = "sign_in")
    public Integer getSignIn() {
        return signIn;
    }

    public void setSignIn(Integer signIn) {
        this.signIn = signIn;
    }

    @Basic
    @Column(name = "\r\nfixed_point_inspection_type")
    public Enum getFixedPointInspectionType() {
        return fixedPointInspectionType;
    }

    public void setFixedPointInspectionType(Enum fixedPointInspectionType) {
        this.fixedPointInspectionType = fixedPointInspectionType;
    }

    @Basic
    @Column(name = "plan_inspection_mileage")
    public Double getPlanInspectionMileage() {
        return planInspectionMileage;
    }

    public void setPlanInspectionMileage(Double planInspectionMileage) {
        this.planInspectionMileage = planInspectionMileage;
    }

    @Basic
    @Column(name = "created_time")
    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Basic
    @Column(name = "hidden_danger_amount")
    public Integer getHiddenDangerAmount() {
        return hiddenDangerAmount;
    }

    public void setHiddenDangerAmount(Integer hiddenDangerAmount) {
        this.hiddenDangerAmount = hiddenDangerAmount;
    }

    @Basic
    @Column(name = "pipe_diameter")
    public Double getPipeDiameter() {
        return pipeDiameter;
    }

    public void setPipeDiameter(Double pipeDiameter) {
        this.pipeDiameter = pipeDiameter;
    }

    @Basic
    @Column(name = "act_inspection_mileage")
    public Double getActInspectionMileage() {
        return actInspectionMileage;
    }

    public void setActInspectionMileage(Double actInspectionMileage) {
        this.actInspectionMileage = actInspectionMileage;
    }

    @Basic
    @Column(name = "enabled")
    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public BizRoute() {
    }

    public BizRoute(Integer id, Enum waterManagementOffice, Enum status, String routeName, Enum routeType, String routeCreator, Integer signIn, Enum fixedPointInspectionType, Double planInspectionMileage, Date createdTime, Integer hiddenDangerAmount, Double pipeDiameter, Double actInspectionMileage, Integer enabled) {
        this.id = id;
        this.waterManagementOffice = waterManagementOffice;
        this.status = status;
        this.routeName = routeName;
        this.routeType = routeType;
        this.routeCreator = routeCreator;
        this.signIn = signIn;
        this.fixedPointInspectionType = fixedPointInspectionType;
        this.planInspectionMileage = planInspectionMileage;
        this.createdTime = createdTime;
        this.hiddenDangerAmount = hiddenDangerAmount;
        this.pipeDiameter = pipeDiameter;
        this.actInspectionMileage = actInspectionMileage;
        this.enabled = enabled;
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
                ", fixedPointInspectionType=" + fixedPointInspectionType +
                ", planInspectionMileage=" + planInspectionMileage +
                ", createdTime=" + createdTime +
                ", hiddenDangerAmount=" + hiddenDangerAmount +
                ", pipeDiameter=" + pipeDiameter +
                ", actInspectionMileage=" + actInspectionMileage +
                ", enabled=" + enabled +
                '}';
    }
}
