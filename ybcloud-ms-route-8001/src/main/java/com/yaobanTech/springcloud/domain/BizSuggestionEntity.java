package com.yaobanTech.springcloud.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * @Description  
 * @Author  yuxy
 * @Date 2021-01-22 14:18:28
 */

@Entity
@Table ( name ="biz_suggestion")
public class BizSuggestionEntity {


	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id" )
	private Integer id;

	/**
	 * 外键编号
	 */
   @Column(name = "f_code" )
	private String fCode;

	/**
	 * 意见内容
	 */
   @Column(name = "commit_content" )
	private String commitContent;

	/**
	 * 意见时间
	 */
   @Column(name = "commit_date" )
	private Date commitDate;

	/**
	 * 意见人
	 */
   @Column(name = "commit_by" )
	private String commitBy;

	/**
	 * 是否有效
	 */
   @Column(name = "enabled" )
	private Integer enabled;

	public BizSuggestionEntity() {
	}

	public BizSuggestionEntity(Integer id, String fCode, String commitContent, Date commitDate, String commitBy, Integer enabled) {
		this.id = id;
		this.fCode = fCode;
		this.commitContent = commitContent;
		this.commitDate = commitDate;
		this.commitBy = commitBy;
		this.enabled = enabled;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFCode() {
		return this.fCode;
	}

	public void setFCode(String fCode) {
		this.fCode = fCode;
	}

	public String getCommitContent() {
		return this.commitContent;
	}

	public void setCommitContent(String commitContent) {
		this.commitContent = commitContent;
	}

	public Date getCommitDate() {
		return this.commitDate;
	}

	public void setCommitDate(Date commitDate) {
		this.commitDate = commitDate;
	}

	public String getCommitBy() {
		return this.commitBy;
	}

	public void setCommitBy(String commitBy) {
		this.commitBy = commitBy;
	}

	public Integer getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "BizSuggestionEntity{" +
				"id=" + id +
				", fCode='" + fCode + '\'' +
				", commitContent='" + commitContent + '\'' +
				", commitDate=" + commitDate +
				", commitBy='" + commitBy + '\'' +
				", enabled=" + enabled +
				'}';
	}
}
