package com.uinte.admin.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.uinte.common.entity.BaseEntity;

@Table(name = "uinte_group_leader")
public class GroupLeader extends BaseEntity {

	private static final long serialVersionUID = -2491205720569454098L;

	@Column(name = "group_id")
	private String groupId;
	
	@Column(name = "user_id")
	private String userId;

	private String description;

	@Column(name = "create_time")
	private Date crtTime;

	@Column(name = "create_user")
	private String crtUser;

	@Column(name = "create_name")
	private String crtName;
	
	@Column(name = "create_host")
	private String crtHost;

	@Column(name = "update_time")
	private Date updTime;

	@Column(name = "update_user")
	private String updUser;

	@Column(name = "update_name")
	private String updName;

	@Column(name = "update_host")
	private String updHost;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}

	public String getCrtUser() {
		return crtUser;
	}

	public void setCrtUser(String crtUser) {
		this.crtUser = crtUser;
	}

	public String getCrtName() {
		return crtName;
	}

	public void setCrtName(String crtName) {
		this.crtName = crtName;
	}

	public String getCrtHost() {
		return crtHost;
	}

	public void setCrtHost(String crtHost) {
		this.crtHost = crtHost;
	}

	public Date getUpdTime() {
		return updTime;
	}

	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}

	public String getUpdUser() {
		return updUser;
	}

	public void setUpdUser(String updUser) {
		this.updUser = updUser;
	}

	public String getUpdName() {
		return updName;
	}

	public void setUpdName(String updName) {
		this.updName = updName;
	}

	public String getUpdHost() {
		return updHost;
	}

	public void setUpdHost(String updHost) {
		this.updHost = updHost;
	}
	
	

}