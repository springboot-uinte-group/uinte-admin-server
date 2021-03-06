package com.uinte.common.entity.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.uinte.common.entity.BaseEntity;

@Table(name = "uinte_group_type")
public class GroupType extends BaseEntity {

	private static final long serialVersionUID = 2514848856221621499L;

	private String code;

	private String name;

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

	/**
	 * @return code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return crt_time
	 */
	public Date getCrtTime() {
		return crtTime;
	}

	/**
	 * @param crtTime
	 */
	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}

	/**
	 * @return crt_user
	 */
	public String getCrtUser() {
		return crtUser;
	}

	/**
	 * @param crtUser
	 */
	public void setCrtUser(String crtUser) {
		this.crtUser = crtUser;
	}

	/**
	 * @return crt_name
	 */
	public String getCrtName() {
		return crtName;
	}

	/**
	 * @param crtName
	 */
	public void setCrtName(String crtName) {
		this.crtName = crtName;
	}

	/**
	 * @return crt_host
	 */
	public String getCrtHost() {
		return crtHost;
	}

	/**
	 * @param crtHost
	 */
	public void setCrtHost(String crtHost) {
		this.crtHost = crtHost;
	}

	/**
	 * @return upd_time
	 */
	public Date getUpdTime() {
		return updTime;
	}

	/**
	 * @param updTime
	 */
	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}

	/**
	 * @return upd_user
	 */
	public String getUpdUser() {
		return updUser;
	}

	/**
	 * @param updUser
	 */
	public void setUpdUser(String updUser) {
		this.updUser = updUser;
	}

	/**
	 * @return upd_name
	 */
	public String getUpdName() {
		return updName;
	}

	/**
	 * @param updName
	 */
	public void setUpdName(String updName) {
		this.updName = updName;
	}

	/**
	 * @return upd_host
	 */
	public String getUpdHost() {
		return updHost;
	}

	/**
	 * @param updHost
	 */
	public void setUpdHost(String updHost) {
		this.updHost = updHost;
	}

}