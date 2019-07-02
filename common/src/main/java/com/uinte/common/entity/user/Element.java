package com.uinte.common.entity.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.uinte.common.entity.BaseEntity;

@Table(name = "uinte_element")
public class Element extends BaseEntity {

	private static final long serialVersionUID = -6319945719231638726L;

	private String code;

	private String type;

	private String name;

	private String uri;

	@Column(name = "menu_id")
	private String menuId;

	@Column(name = "parent_id")
	private String parentId;

	private String path;

	private String method;

	private String description;

	@Column(name = "create_time")
	private Date crtTime;

	@Column(name = "create_user")
	private String crtUser;

	@Column(name = "create_name")
	private String crtName;

	@Column(name = "create_host")
	private String crtHost;

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
	 * @return type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
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
	 * @return uri
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * @param uri
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

	/**
	 * @return menu_id
	 */
	public String getMenuId() {
		return menuId;
	}

	/**
	 * @param menuId
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * @return parent_id
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * @param parentId
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method
	 */
	public void setMethod(String method) {
		this.method = method;
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

}