package cn.com.anyitou.entity;

import java.io.Serializable;

public class LableValue implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1074067197374220136L;
	private String name;
	private String value;
	
	

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
