package com.uinte.admin.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.uinte.common.entity.BaseEntity;

@Table(name = "gate_log")
public class GateLog extends BaseEntity {
	
	private static final long serialVersionUID = -3684600428684494186L;

	private String menu;

	private String opt;

	private String uri;

	@Column(name = "crt_time")
	private Date crtTime;

	@Column(name = "crt_user")
	private String crtUser;

	@Column(name = "crt_name")
	private String crtName;

	@Column(name = "crt_host")
	private String crtHost;

	@Column(name = "METHOD_ARGS")
	private String methodArgs;

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public String getOpt() {
		return opt;
	}

	public void setOpt(String opt) {
		this.opt = opt;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
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

	public String getMethodArgs() {
		return methodArgs;
	}

	public void setMethodArgs(String methodArgs) {
		this.methodArgs = methodArgs;
	}

}