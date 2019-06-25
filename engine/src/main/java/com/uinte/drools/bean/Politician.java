package com.uinte.drools.bean;

import java.io.Serializable;

public class Politician implements Serializable {
	private static final long serialVersionUID = 1895590207220852050L;

	private String name;

	private boolean honest;

	public Politician() {

	}

	public Politician(String name, boolean honest) {
		super();
		this.name = name;
		this.honest = honest;
	}

	public boolean isHonest() {
		return honest;
	}

	public void setHonest(boolean honest) {
		this.honest = honest;
	}

	public String getName() {
		return name;
	}
}
