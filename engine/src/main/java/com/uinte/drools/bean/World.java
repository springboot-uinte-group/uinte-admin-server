package com.uinte.drools.bean;

import java.io.Serializable;

public class World implements Serializable {
	private static final long serialVersionUID = 4618231900866769118L;
	private Integer a;
	private Integer b;
	private Integer c;

	public World(Integer a, Integer b, Integer c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}

	public Integer getA() {
		return a;
	}

	public void setA(Integer a) {
		this.a = a;
	}

	public Integer getB() {
		return b;
	}

	public void setB(Integer b) {
		this.b = b;
	}

	public Integer getC() {
		return c;
	}

	public void setC(Integer c) {
		this.c = c;
	}

	@Override
	public String toString() {
		return "World{" + "a=" + a + ", b=" + b + ", c=" + c + '}';
	}
}
