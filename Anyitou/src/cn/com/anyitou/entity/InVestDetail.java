package cn.com.anyitou.entity;

import java.io.Serializable;
import java.util.List;

public class InVestDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5090349711152643456L;
	private List<LableValue> firstpage;
	private List<LableData> secondpage;
	//
	
	
	public List<LableData> getSecondpage() {
		return secondpage;
	}
	public List<LableValue> getFirstpage() {
		return firstpage;
	}
	public void setFirstpage(List<LableValue> firstpage) {
		this.firstpage = firstpage;
	}
	public void setSecondpage(List<LableData> secondpage) {
		this.secondpage = secondpage;
	}
	
	
}
