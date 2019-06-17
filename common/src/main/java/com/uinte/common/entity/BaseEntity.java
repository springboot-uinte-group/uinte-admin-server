package com.uinte.common.entity;

import java.io.Serializable;
import javax.persistence.Id;

public class BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 3120923112723273673L;
	
	@Id
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
}
