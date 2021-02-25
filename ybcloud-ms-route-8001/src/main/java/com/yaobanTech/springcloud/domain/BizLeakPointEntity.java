package com.yaobanTech.springcloud.domain;

import javax.persistence.*;
import java.util.Date;
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
	private Date commitDate;

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
	private Date endDate;

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

	public BizLeakPointEntity() {
	}

	public BizLeakPointEntity(Integer id, String leakPointCode, Integer routeId, String waterUseOffice, String equipmentName, String equipmentSize, String abnormalPhenomena, String abnormalReason, String currentPosition, String autoGetPositon, String memo, String assetType, String commitBy, Date commitDate, Integer enabled, String leakPointStatus, Date endDate, String address, String x, String y, String dept) {
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
		this.leakPointStatus = leakPointStatus;
		this.endDate = endDate;
		this.address = address;
		X = x;
		Y = y;
		this.dept = dept;
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

	public List<HashMap<String, Object>> getAttachment() {
		return attachment;
	}

	public void setAttachment(List<HashMap<String, Object>> attachment) {
		this.attachment = attachment;
	}

	public String getCommitByCN() {
		return commitByCN;
	}

	public void setCommitByCN(String commitByCN) {
		this.commitByCN = commitByCN;
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

	public String getLeakPointStatus() {
		return this.leakPointStatus;
	}

	public void setLeakPointStatus(String leakPointStatus) {
		this.leakPointStatus = leakPointStatus;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getX() {
		return this.X;
	}

	public void setX(String X) {
		this.X = X;
	}

	public String getY() {
		return this.Y;
	}

	public void setY(String Y) {
		this.Y = Y;
	}

	public String getDept() {
		return this.dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

}
