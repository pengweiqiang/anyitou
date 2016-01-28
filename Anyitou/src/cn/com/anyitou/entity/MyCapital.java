package cn.com.anyitou.entity;

import java.io.Serializable;

import cn.com.anyitou.utils.StringUtils;
/**
 * 我的资金
 * @author pengweiqiang
 *
 */
public class MyCapital implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8966252426013642832L;
	
	private String user_id;
	private String all_assets;
	private String use_money;
	private String frozen_money;
	private String collected_interest;
	private String all_income;
	private String prize_num;
	private String status;
	private String yesterday_income;
	private String user_name;
	
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getAll_assets() {
		return all_assets;
	}
	public void setAll_assets(String all_assets) {
		this.all_assets = all_assets;
	}
	public String getUse_money() {
		return use_money;
	}
	public void setUse_money(String use_money) {
		this.use_money = use_money;
	}
	public String getFrozen_money() {
		return frozen_money;
	}
	public void setFrozen_money(String frozen_money) {
		this.frozen_money = frozen_money;
	}
	public String getCollected_interest() {
		return collected_interest;
	}
	public void setCollected_interest(String collected_interest) {
		this.collected_interest = collected_interest;
	}
	public String getAll_income() {
		return all_income;
	}
	public void setAll_income(String all_income) {
		this.all_income = all_income;
	}
	public String getPrize_num() {
		return prize_num;
	}
	public void setPrize_num(String prize_num) {
		this.prize_num = prize_num;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getYesterday_income() {
		return yesterday_income;
	}
	public void setYesterday_income(String yesterday_income) {
		this.yesterday_income = yesterday_income;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
}
