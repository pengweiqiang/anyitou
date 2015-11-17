package cn.com.anyitou.entity;

import java.io.Serializable;

public class InvestCoupons implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3178461289476231475L;
	
	private String coupon_id;
	private String category;
	private String name;
	private String price;
	public String getCoupon_id() {
		return coupon_id;
	}
	public void setCoupon_id(String coupon_id) {
		this.coupon_id = coupon_id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
	
	
	
}
