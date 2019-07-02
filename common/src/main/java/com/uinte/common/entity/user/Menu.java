package com.uinte.common.entity.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.uinte.common.constant.AdminConstant;
import com.uinte.common.entity.BaseEntity;

@Table(name = "uinte_menu")
public class Menu extends BaseEntity {

	private static final long serialVersionUID = -3630836072137359720L;

	private String code;

	private String title;

	@Column(name = "parent_id")
	private String parentId = AdminConstant.ROOT_PATH;

	private String href;

	private String icon;

	private String type;

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

	private String path;

	@Column(name = "ui_component")
	private String uiComponent;
	
	@Column(name = "default_expand")
	private Boolean defaultExpand; //默认展开

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
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
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
	 * @return href
	 */
	public String getHref() {
		return href;
	}

	/**
	 * @param href
	 */
	public void setHref(String href) {
		this.href = href;
	}

	/**
	 * @return icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * @param icon
	 */
	public void setIcon(String icon) {
		this.icon = icon;
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

	public String getUiComponent() {
		return uiComponent;
	}

	public void setUiComponent(String uiComponent) {
		this.uiComponent = uiComponent;
	}

	
	public Boolean getDefaultExpand() {
		return defaultExpand;
	}

	public void setDefaultExpand(Boolean defaultExpand) {
		this.defaultExpand = defaultExpand;
	}

	@Override
	public String toString() {
		return "Menu [code=" + code + ", title=" + title + ", parentId=" + parentId + ", href=" + href + ", icon="
				+ icon + ", type=" + type + ", description=" + description + ", crtTime=" + crtTime + ", crtUser="
				+ crtUser + ", crtName=" + crtName + ", crtHost=" + crtHost + ", updTime=" + updTime + ", updUser="
				+ updUser + ", updName=" + updName + ", updHost=" + updHost + ", path=" + path + ", uiComponent="
				+ uiComponent + ", defaultExpand=" + defaultExpand + "]";
	}

}