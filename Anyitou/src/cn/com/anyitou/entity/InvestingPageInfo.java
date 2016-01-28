package cn.com.anyitou.entity;

import java.io.Serializable;

/**
 * 投资确认界面信息
 * @author pengweiqiang
 *
 */
public class InvestingPageInfo implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1974903275485835423L;
	private String title;
	private String money;
	private String restMoney;
	private String restBal;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getRestMoney() {
		return restMoney;
	}
	public void setRestMoney(String restMoney) {
		this.restMoney = restMoney;
	}
	public String getRestBal() {
		return restBal;
	}
	public void setRestBal(String restBal) {
		this.restBal = restBal;
	}
	
	
	
	
}
