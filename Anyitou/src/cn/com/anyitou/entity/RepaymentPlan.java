package cn.com.anyitou.entity;

import java.io.Serializable;

public class RepaymentPlan implements Serializable{

	/**
	 * "date": "1970-01-15",
                        "type": "利息",
                        "money": "498.63"
	 */
	private static final long serialVersionUID = -761746634084020389L;
	private String date;
	private String type;
	private String money;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	
	
	
	
	
	
}
