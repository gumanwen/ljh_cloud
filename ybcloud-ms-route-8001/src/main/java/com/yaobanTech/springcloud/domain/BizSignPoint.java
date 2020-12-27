package com.yaobanTech.springcloud.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description  
 * @Author  yuxy
 * @Date 2020-12-22 
 */

@Entity
@Table ( name ="biz_sign_point" )
public class BizSignPoint  implements Serializable {

	private static final long serialVersionUID =  96987551705552753L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
   	@Column(name = "id" )
	private Long id;

	/**
	 * 当前所选地址
	 */
   	@Column(name = "current_choosed_address" )
	private String currentChoosedAddress;

	/**
	 * 问题编号
	 */
   	@Column(name = "trouble_code" )
	private String troubleCode;

	/**
	 * 问题原因
	 */
   	@Column(name = "trouble_reason" )
	private String troubleReason;

	/**
	 * 隐患类型
	 */
   	@Column(name = "hidden_danger_type" )
	private Enum hiddenDangerType;

	/**
	 * 处理意见
	 */
   	@Column(name = "handle_suggestion" )
	private String handleSuggestion;

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
	 * 坐标
	 */
	@Column(name = "location" )
	private String location;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCurrentChoosedAddress() {
		return this.currentChoosedAddress;
	}

	public void setCurrentChoosedAddress(String currentChoosedAddress) {
		this.currentChoosedAddress = currentChoosedAddress;
	}

	public String getTroubleCode() {
		return this.troubleCode;
	}

	public void setTroubleCode(String troubleCode) {
		this.troubleCode = troubleCode;
	}

	public String getTroubleReason() {
		return this.troubleReason;
	}

	public void setTroubleReason(String troubleReason) {
		this.troubleReason = troubleReason;
	}

	public Enum getHiddenDangerType() {
		return this.hiddenDangerType;
	}

	public void setHiddenDangerType(Enum hiddenDangerType) {
		this.hiddenDangerType = hiddenDangerType;
	}

	public String getHandleSuggestion() {
		return this.handleSuggestion;
	}

	public void setHandleSuggestion(String handleSuggestion) {
		this.handleSuggestion = handleSuggestion;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public BizSignPoint() {
	}

	public BizSignPoint(Long id, String currentChoosedAddress, String troubleCode, String troubleReason, Enum hiddenDangerType, String handleSuggestion, Integer enabled, Integer routeId, String location) {
		this.id = id;
		this.currentChoosedAddress = currentChoosedAddress;
		this.troubleCode = troubleCode;
		this.troubleReason = troubleReason;
		this.hiddenDangerType = hiddenDangerType;
		this.handleSuggestion = handleSuggestion;
		this.enabled = enabled;
		this.routeId = routeId;
		this.location = location;
	}

	@Override
	public String toString() {
		return "BizSignPoint{" +
				"id=" + id +
				", currentChoosedAddress='" + currentChoosedAddress + '\'' +
				", troubleCode='" + troubleCode + '\'' +
				", troubleReason='" + troubleReason + '\'' +
				", hiddenDangerType=" + hiddenDangerType +
				", handleSuggestion='" + handleSuggestion + '\'' +
				", enabled=" + enabled +
				", routeId=" + routeId +
				", location=" + location +
				'}';
	}
}
