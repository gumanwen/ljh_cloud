package com.yaobanTech.springcloud.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author lijh
 * @since 2020-12-18
 */

@EqualsAndHashCode(callSuper = false)
@TableName("BIZ_DEVICE")
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增编号
     */

    private Integer id;

    /**
     * 任务编号
     */
    private String inspectTaskId;

    /**
     * 巡查人
     */
    @TableId(value = "inspect_person")
    private String inspectPerson;

    /**
     * 巡查中文人
     */
    @TableField(exist = false)
    private String inspectPersonName;

    /**
     * 基础时间
     */
    private String countTime;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * x坐标
     */
    private String mapX;

    /**
     * y坐标
     */
    private String mapY;

    /**
     * 状态
     */
    private String status;

    @TableField(exist = false)
    private String stoptime;

    @TableField(exist = false)
    private Map<String, Object> routeInfo;



    public Device() {
    }

    public Device(Integer id, String inspectTaskId, String inspectPerson, String inspectPersonName, String countTime, String longitude, String latitude, String mapX, String mapY, String status, String stoptime, Map<String, Object> routeInfo) {
        this.id = id;
        this.inspectTaskId = inspectTaskId;
        this.inspectPerson = inspectPerson;
        this.inspectPersonName = inspectPersonName;
        this.countTime = countTime;
        this.longitude = longitude;
        this.latitude = latitude;
        this.mapX = mapX;
        this.mapY = mapY;
        this.status = status;
        this.stoptime = stoptime;
        this.routeInfo = routeInfo;
    }

    public String getInspectPersonName() {
        return inspectPersonName;
    }

    public void setInspectPersonName(String inspectPersonName) {
        this.inspectPersonName = inspectPersonName;
    }

    public String getStoptime() {
        return stoptime;
    }

    public void setStoptime(String stoptime) {
        this.stoptime = stoptime;
    }

    public Map<String, Object> getRouteInfo() {
        return routeInfo;
    }

    public void setRouteInfo(Map<String, Object> routeInfo) {
        this.routeInfo = routeInfo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInspectTaskId() {
        return inspectTaskId;
    }

    public void setInspectTaskId(String inspectTaskId) {
        this.inspectTaskId = inspectTaskId;
    }

    public String getInspectPerson() {
        return inspectPerson;
    }

    public void setInspectPerson(String inspectPerson) {
        this.inspectPerson = inspectPerson;
    }

    public String getCountTime() {
        return countTime;
    }

    public void setCountTime(String gpsTime) {
        this.countTime = gpsTime;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getMapX() {
        return mapX;
    }

    public void setMapX(String mapX) {
        this.mapX = mapX;
    }

    public String getMapY() {
        return mapY;
    }

    public void setMapY(String mapY) {
        this.mapY = mapY;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
