package cn.com.anyitou.entity;

import java.io.Serializable;

public class CategoryData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1074067197374220136L;
	private String type;
	private String label;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	

}
