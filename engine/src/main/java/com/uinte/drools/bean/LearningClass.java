package com.uinte.drools.bean;

import java.io.Serializable;

import com.uinte.drools.bean.base.EngineEntity;

public class LearningClass extends EngineEntity implements Serializable {

	private static final long serialVersionUID = -7459367846209525575L;
	
	private String className;
	
	private int classLevel;
	
	
	public LearningClass(String className, int classLevel) {
		super();
		this.className = className;
		this.classLevel = classLevel;
	}

	public LearningClass() {
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getClassLevel() {
		return classLevel;
	}

	public void setClassLevel(int classLevel) {
		this.classLevel = classLevel;
	}
	
}