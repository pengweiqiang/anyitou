package cn.com.anyitou.entity;

import java.io.Serializable;
/**
 * // 距离募集结束时间差
 * @author pengweiqiang
 *
 */
public class RaiseRemainTime implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2660714774068861988L;

	private String diff;

	public String getDiff() {
		return diff;
	}

	public void setDiff(String diff) {
		this.diff = diff;
	}
	
	
	
}
