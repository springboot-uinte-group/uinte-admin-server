package com.uinte.drools.bean;

import java.io.Serializable;

import com.uinte.drools.bean.base.EngineEntity;

public class Golfer extends EngineEntity implements Serializable {

	private static final long serialVersionUID = -3811212517138842579L;
	private String name;
	private String color;
	private int position;

	public Golfer(String name, String color, int position) {
		this.name = name;
		this.color = color;
		this.position = position;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
}