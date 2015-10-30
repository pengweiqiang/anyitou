package cn.com.anyitou.entity;

import java.io.Serializable;

public class Money implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2660714774068861988L;

	private String usable_money;//可提现金额

	public String getUsable_money() {
		return usable_money;
	}

	public void setUsable_money(String usable_money) {
		this.usable_money = usable_money;
	}
}
