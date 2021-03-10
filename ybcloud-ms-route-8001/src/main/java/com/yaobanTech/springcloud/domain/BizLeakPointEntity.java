package com.yaobanTech.springcloud.domain;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;

/**
 * @Description  
 * @Author yuxy
 * @Date 2021-02-24 09:15:46
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
	 * 提交人员(中文)
	 */
   @Transient
	private String commitByCN;

	/**
	 * 提交时间
	 */
   @Column(name = "commit_Date" )
	private String commitDate;

	/**
	 * 是否有效
	 */
   @Column(name = "enabled" )
	private Integer enabled;

	/**
	 * 漏点状态
	 */
   @Column(name = "leak_point_status" )
	private String leakPointStatus;

	/**
	 * 结束时间
	 */
   @Column(name = "end_date" )
	private String endDate;

	/**
	 * 详细地址
	 */
   @Column(name = "address" )
	private String address;

	/**
	 * 横坐标
	 */
   @Column(name = "x" )
	private String X;

	/**
	 * 纵坐标
	 */
   @Column(name = "y" )
	private String Y;

	/**
	 * 部门
	 */
   @Column(name = "dept" )
	private String dept;

   /**
	 * 操作
	 */
   @Column(name = "opration" )
	private String opration;

   /**
	 * 附件
	 */
   @Transient
	private List<HashMap<String,Object>> attachment;

	/**
	 * 巡查任务记录
	 */
	@Transient
	private List<HashMap<String,Object>> checkRecord;

	/**
	 * 处理记录
	 */
	@Transient
	private List<HashMap<String,Object>> handleRecord;

	/**
	 * 漏点状态枚举
	 */
	@Transient
	private HashMap<String,Object> leakPointStatusEnum;

	/**
	 * 异常现象枚举
	 */
	@Transient
	private HashMap<String,Object> abnormalPhenomenaEnum;

	/**
	 * 资产类别枚举
	 */
	@Transient
	private HashMap<String,Object> assetTypeEnum;

	public BizLeakPointEntity() {
	}

	public BizLeakPointEntity(Integer id, String leakPointCode, Integer routeId, String waterUseOffice, String equipmentName, String equipmentSize, String abnormalPhenomena, String abnormalReason, String currentPosition, String autoGetPositon, String memo, String assetType, String commitBy, String commitByCN, String commitDate, Integer enabled, String leakPointStatus, String endDate, String address, String x, String y, String dept, String opration, List<HashMap<String, Object>> attachment, List<HashMap<String, Object>> checkRecord, List<HashMap<String, Object>> handleRecord, HashMap<String, Object> leakPointStatusEnum, HashMap<String, Object> abnormalPhenomenaEnum, HashMap<String, Object> assetTypeEnum) {
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
		this.commitByCN = commitByCN;
		this.commitDate = commitDate;
		this.enabled = enabled;
		this.leakPointStatus = leakPointStatus;
		this.endDate = endDate;
		this.address = address;
		X = x;
		Y = y;
		this.dept = dept;
		this.opration = opration;
		this.attachment = attachment;
		this.checkRecord = checkRecord;
		this.handleRecord = handleRecord;
		this.leakPointStatusEnum = leakPointStatusEnum;
		this.abnormalPhenomenaEnum = abnormalPhenomenaEnum;
		this.assetTypeEnum = assetTypeEnum;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLeakPointCode() {
		return leakPointCode;
	}

	public void setLeakPointCode(String leakPointCode) {
		this.leakPointCode = leakPointCode;
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

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public String getEquipmentSize() {
		return equipmentSize;
	}

	public void setEquipmentSize(String equipmentSize) {
		this.equipmentSize = equipmentSize;
	}

	public String getAbnormalPhenomena() {
		return abnormalPhenomena;
	}

	public void setAbnormalPhenomena(String abnormalPhenomena) {
		this.abnormalPhenomena = abnormalPhenomena;
	}

	public String getAbnormalReason() {
		return abnormalReason;
	}

	public void setAbnormalReason(String abnormalReason) {
		this.abnormalReason = abnormalReason;
	}

	public String getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(String currentPosition) {
		this.currentPosition = currentPosition;
	}

	public String getAutoGetPositon() {
		return autoGetPositon;
	}

	public void setAutoGetPositon(String autoGetPositon) {
		this.autoGetPositon = autoGetPositon;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getAssetType() {
		return assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public String getCommitBy() {
		return commitBy;
	}

	public void setCommitBy(String commitBy) {
		this.commitBy = commitBy;
	}

	public String getCommitByCN() {
		return commitByCN;
	}

	public void setCommitByCN(String commitByCN) {
		this.commitByCN = commitByCN;
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

	public String getLeakPointStatus() {
		return leakPointStatus;
	}

	public void setLeakPointStatus(String leakPointStatus) {
		this.leakPointStatus = leakPointStatus;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getX() {
		return X;
	}

	public void setX(String x) {
		X = x;
	}

	public String getY() {
		return Y;
	}

	public void setY(String y) {
		Y = y;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getOpration() {
		return opration;
	}

	public void setOpration(String opration) {
		this.opration = opration;
	}

	public List<HashMap<String, Object>> getAttachment() {
		return attachment;
	}

	public void setAttachment(List<HashMap<String, Object>> attachment) {
		this.attachment = attachment;
	}

	public List<HashMap<String, Object>> getCheckRecord() {
		return checkRecord;
	}

	public void setCheckRecord(List<HashMap<String, Object>> checkRecord) {
		this.checkRecord = checkRecord;
	}

	public List<HashMap<String, Object>> getHandleRecord() {
		return handleRecord;
	}

	public void setHandleRecord(List<HashMap<String, Object>> handleRecord) {
		this.handleRecord = handleRecord;
	}

	public HashMap<String, Object> getLeakPointStatusEnum() {
		return leakPointStatusEnum;
	}

	public void setLeakPointStatusEnum(HashMap<String, Object> leakPointStatusEnum) {
		this.leakPointStatusEnum = leakPointStatusEnum;
	}

	public HashMap<String, Object> getAbnormalPhenomenaEnum() {
		return abnormalPhenomenaEnum;
	}

	public void setAbnormalPhenomenaEnum(HashMap<String, Object> abnormalPhenomenaEnum) {
		this.abnormalPhenomenaEnum = abnormalPhenomenaEnum;
	}

	public HashMap<String, Object> getAssetTypeEnum() {
		return assetTypeEnum;
	}

	public void setAssetTypeEnum(HashMap<String, Object> assetTypeEnum) {
		this.assetTypeEnum = assetTypeEnum;
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
				", commitByCN='" + commitByCN + '\'' +
				", commitDate='" + commitDate + '\'' +
				", enabled=" + enabled +
				", leakPointStatus='" + leakPointStatus + '\'' +
				", endDate='" + endDate + '\'' +
				", address='" + address + '\'' +
				", X='" + X + '\'' +
				", Y='" + Y + '\'' +
				", dept='" + dept + '\'' +
				", opration='" + opration + '\'' +
				", attachment=" + attachment +
				", checkRecord=" + checkRecord +
				", handleRecord=" + handleRecord +
				", leakPointStatusEnum=" + leakPointStatusEnum +
				", abnormalPhenomenaEnum=" + abnormalPhenomenaEnum +
				", assetTypeEnum=" + assetTypeEnum +
				'}';
	}
}
