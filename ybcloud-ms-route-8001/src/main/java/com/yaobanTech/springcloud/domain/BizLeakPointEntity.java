package com.yaobanTech.springcloud.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * @Description  
 * @Author  浣滆�呭悕绉癨n * @Date 2021-01-22 14:07:11 
 */

@Entity
@Table ( name ="biz_leak_point")
public class BizLeakPointEntity {


	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id" )
	private Integer id;

	/**
	 * 漏点编号
	 */
   @Column(name = "leak_point_code" )
	private String leakPointCode;

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
	 * 设备名称
	 */
   @Column(name = "equipment_name" )
	private String equipmentName;

	/**
	 * 设备口径
	 */
   @Column(name = "equipment_size" )
	private String equipmentSize;

	/**
	 * 异常现象
	 */
   @Column(name = "abnormal_phenomena" )
	private String abnormalPhenomena;

	/**
	 * 异常原因
	 */
   @Column(name = "abnormal_reason" )
	private String abnormalReason;

	/**
	 * 当前位置
	 */
   @Column(name = "current_position" )
	private String currentPosition;

	/**
	 * 自动获取位置
	 */
   @Column(name = "auto_get_positon" )
	private String autoGetPositon;

	/**
	 * 备注
	 */
   @Column(name = "memo" )
	private String memo;

	/**
	 * 资产类别
	 */
   @Column(name = "asset_type" )
	private String assetType;

	/**
	 * 提交人员
	 */
   @Column(name = "commit_by" )
	private String commitBy;

	/**
	 * 提交时间
	 */
   @Column(name = "commit_Date" )
	private Date commitDate;

	/**
	 * 是否有效
	 */
   @Column(name = "enabled" )
	private Integer enabled;

	public BizLeakPointEntity() {
	}

	public BizLeakPointEntity(Integer id, String leakPointCode, Integer routeId, String waterUseOffice, String equipmentName, String equipmentSize, String abnormalPhenomena, String abnormalReason, String currentPosition, String autoGetPositon, String memo, String assetType, String commitBy, Date commitDate, Integer enabled) {
		this.id = id;
		this.leakPointCode = leakPointCode;
		this.routeId = routeId;
		this.waterUseOffice = waterUseOffice;
		this.equipmentName = equipmentName;
		this.equipmentSize = equipmentSize;
		this.abnormalPhenomena = abnormalPhenomena;
		this.abnormalReason = abnormalReason;
		this.currentPosition = currentPosition;
		this.autoGetPositon = autoGetPositon;
		this.memo = memo;
		this.assetType = assetType;
		this.commitBy = commitBy;
		this.commitDate = commitDate;
		this.enabled = enabled;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLeakPointCode() {
		return this.leakPointCode;
	}

	public void setLeakPointCode(String leakPointCode) {
		this.leakPointCode = leakPointCode;
	}

	public Integer getRouteId() {
		return this.routeId;
	}

	public void setRouteId(Integer routeId) {
		this.routeId = routeId;
	}

	public String getWaterUseOffice() {
		return this.waterUseOffice;
	}

	public void setWaterUseOffice(String waterUseOffice) {
		this.waterUseOffice = waterUseOffice;
	}

	public String getEquipmentName() {
		return this.equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public String getEquipmentSize() {
		return this.equipmentSize;
	}

	public void setEquipmentSize(String equipmentSize) {
		this.equipmentSize = equipmentSize;
	}

	public String getAbnormalPhenomena() {
		return this.abnormalPhenomena;
	}

	public void setAbnormalPhenomena(String abnormalPhenomena) {
		this.abnormalPhenomena = abnormalPhenomena;
	}

	public String getAbnormalReason() {
		return this.abnormalReason;
	}

	public void setAbnormalReason(String abnormalReason) {
		this.abnormalReason = abnormalReason;
	}

	public String getCurrentPosition() {
		return this.currentPosition;
	}

	public void setCurrentPosition(String currentPosition) {
		this.currentPosition = currentPosition;
	}

	public String getAutoGetPositon() {
		return this.autoGetPositon;
	}

	public void setAutoGetPositon(String autoGetPositon) {
		this.autoGetPositon = autoGetPositon;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getAssetType() {
		return this.assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public String getCommitBy() {
		return this.commitBy;
	}

	public void setCommitBy(String commitBy) {
		this.commitBy = commitBy;
	}

	public Date getCommitDate() {
		return this.commitDate;
	}

	public void setCommitDate(Date commitDate) {
		this.commitDate = commitDate;
	}

	public Integer getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "BizLeakPointEntity{" +
				"id=" + id +
				", leakPointCode='" + leakPointCode + '\'' +
				", routeId=" + routeId +
				", waterUseOffice='" + waterUseOffice + '\'' +
				", equipmentName='" + equipmentName + '\'' +
				", equipmentSize='" + equipmentSize + '\'' +
				", abnormalPhenomena='" + abnormalPhenomena + '\'' +
				", abnormalReason='" + abnormalReason + '\'' +
				", currentPosition='" + currentPosition + '\'' +
				", autoGetPositon='" + autoGetPositon + '\'' +
				", memo='" + memo + '\'' +
				", assetType='" + assetType + '\'' +
				", commitBy='" + commitBy + '\'' +
				", commitDate=" + commitDate +
				", enabled=" + enabled +
				'}';
	}
}
