package cn.com.anyitou.entity;

import java.io.Serializable;

/**
 * 交易记录
 * @author will
 *
 */
public class Records implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6539933435377478147L;
	private String id;
	private String user_id;//+
	private String category;
	private String status;
	private String trans_id;
	private String cash_status;
	private String cash_num;
	private String use_money;
	private String deal_time;
	private String remark;
	private CategoryData category_data;
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
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTrans_id() {
		return trans_id;
	}
	public void setTrans_id(String trans_id) {
		this.trans_id = trans_id;
	}
	public String getCash_status() {
		return cash_status;
	}
	public void setCash_status(String cash_status) {
		this.cash_status = cash_status;
	}
	public String getCash_num() {
		return cash_num;
	}
	public void setCash_num(String cash_num) {
		this.cash_num = cash_num;
	}
	public String getUse_money() {
		return use_money;
	}
	public void setUse_money(String use_money) {
		this.use_money = use_money;
	}
	public String getDeal_time() {
		return deal_time;
	}
	public void setDeal_time(String deal_time) {
		this.deal_time = deal_time;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public CategoryData getCategory_data() {
		return category_data;
	}
	public void setCategory_data(CategoryData category_data) {
		this.category_data = category_data;
	}
	

}
