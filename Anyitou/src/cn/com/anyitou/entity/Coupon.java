package cn.com.anyitou.entity;

import java.io.Serializable;

public class Coupon implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3178461289476231475L;
	
	private String id;
	private String user_id;
	private String coupon_id;
	private String name;//优惠券名称
	private String price; //优惠券价值
	private String category;//优惠券类型 现金券cash，利息券interest，提现券draw
	private String category_name;//优惠券类别名称
	private String give_time;//优惠券发放时间
	private String use_status;//优惠券使用状态 0为未使用，1为已使用，2为已过期
	private String use_time;//优惠券使用时间
	private String begin_time;
	private String end_time;//优惠券过期时间
	private String source_id;//优惠券来源ID
	private String source_type;
	private String source_name;//来源描述
	private String remain_days;
	private String is_expired;
	private String use_rules;//使用规则描述
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getCoupon_id() {
		return coupon_id;
	}
	public void setCoupon_id(String coupon_id) {
		this.coupon_id = coupon_id;
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
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	public String getGive_time() {
		return give_time;
	}
	public void setGive_time(String give_time) {
		this.give_time = give_time;
	}
	public String getUse_status() {
		return use_status;
	}
	public void setUse_status(String use_status) {
		this.use_status = use_status;
	}
	public String getUse_time() {
		return use_time;
	}
	public void setUse_time(String use_time) {
		this.use_time = use_time;
	}
	public String getBegin_time() {
		return begin_time;
	}
	public void setBegin_time(String begin_time) {
		this.begin_time = begin_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getSource_id() {
		return source_id;
	}
	public void setSource_id(String source_id) {
		this.source_id = source_id;
	}
	public String getSource_type() {
		return source_type;
	}
	public void setSource_type(String source_type) {
		this.source_type = source_type;
	}
	public String getSource_name() {
		return source_name;
	}
	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}
	public String getRemain_days() {
		return remain_days;
	}
	public void setRemain_days(String remain_days) {
		this.remain_days = remain_days;
	}
	public String getIs_expired() {
		return is_expired;
	}
	public void setIs_expired(String is_expired) {
		this.is_expired = is_expired;
	}
	public String getUse_rules() {
		return use_rules;
	}
	public void setUse_rules(String use_rules) {
		this.use_rules = use_rules;
	}
	
	
	
}
