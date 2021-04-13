package com.yaobanTech.springcloud.entity;

import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("BIZ_TRACK")
public class Track implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增编号
     */

    private Integer id;

    /**
     * 任务编号
     */
    @TableId(value = "inspect_task_id")
    private String inspectTaskId;

    /**
     * 巡查人
     */
    private String inspectPerson;

    /**
     * gps时间
     */
    private String gpsTime;

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

    public Track() {
    }

    public Track(Integer id, String inspectTaskId, String inspectPerson, String gpsTime, String longitude, String latitude, String mapX, String mapY, String status) {
        this.id = id;
        this.inspectTaskId = inspectTaskId;
        this.inspectPerson = inspectPerson;
        this.gpsTime = gpsTime;
        this.longitude = longitude;
        this.latitude = latitude;
        this.mapX = mapX;
        this.mapY = mapY;
        this.status = status;
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

    public String getGpsTime() {
        return gpsTime;
    }

    public void setGpsTime(String gpsTime) {
        this.gpsTime = gpsTime;
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
