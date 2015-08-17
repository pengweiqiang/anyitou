package cn.com.anyitou.entity;

import java.io.Serializable;

public class PaymentPlan implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1647209236154590768L;
	private String date;
	private String shoudpay;//应回款
	private String alreadypay;//已回款
	private String worsepay;//未回款
	
	
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getShoudpay() {
		return shoudpay;
	}
	public void setShoudpay(String shoudpay) {
		this.shoudpay = shoudpay;
	}
	public String getAlreadypay() {
		return alreadypay;
	}
	public void setAlreadypay(String alreadypay) {
		this.alreadypay = alreadypay;
	}
	public String getWorsepay() {
		return worsepay;
	}
	public void setWorsepay(String worsepay) {
		this.worsepay = worsepay;
	}
	
	
	
	
}
