package cn.com.anyitou.entity;

import java.io.Serializable;
/**
 * 用户的可转让债权列表
 * @author pengweiqiang
 *
 */
public class MyDebtTransferable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9144388183668526953L;
	/**
	 * 
	 */
	
	private String id;
	private String status;
	private String type;
	private String trade_no;
	private String user_id;
	private String item_id;
	private String item_amout;
	private String money;
	private String transferring_amount;
	private String transferred_amount;
	
	private String item_income;
	private String invest_time;
	private String item_title;
	private String rate_of_interest;
	private String repayment_time;
	private String invest_status;
	private String real_transferable_amount;
	private String transferable_amount_max;
	
	private String transferable_amount_min;
	private String remainDays;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTrade_no() {
		return trade_no;
	}
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getItem_id() {
		return item_id;
	}
	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}
	public String getItem_amout() {
		return item_amout;
	}
	public void setItem_amout(String item_amout) {
		this.item_amout = item_amout;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getTransferring_amount() {
		return transferring_amount;
	}
	public void setTransferring_amount(String transferring_amount) {
		this.transferring_amount = transferring_amount;
	}
	public String getTransferred_amount() {
		return transferred_amount;
	}
	public void setTransferred_amount(String transferred_amount) {
		this.transferred_amount = transferred_amount;
	}
	public String getItem_income() {
		return item_income;
	}
	public void setItem_income(String item_income) {
		this.item_income = item_income;
	}
	public String getInvest_time() {
		return invest_time;
	}
	public void setInvest_time(String invest_time) {
		this.invest_time = invest_time;
	}
	public String getItem_title() {
		return item_title;
	}
	public void setItem_title(String item_title) {
		this.item_title = item_title;
	}
	public String getRate_of_interest() {
		return rate_of_interest;
	}
	public void setRate_of_interest(String rate_of_interest) {
		this.rate_of_interest = rate_of_interest;
	}
	public String getRepayment_time() {
		return repayment_time;
	}
	public void setRepayment_time(String repayment_time) {
		this.repayment_time = repayment_time;
	}
	public String getInvest_status() {
		return invest_status;
	}
	public void setInvest_status(String invest_status) {
		this.invest_status = invest_status;
	}
	public String getReal_transferable_amount() {
		return real_transferable_amount;
	}
	public void setReal_transferable_amount(String real_transferable_amount) {
		this.real_transferable_amount = real_transferable_amount;
	}
	public String getTransferable_amount_max() {
		return transferable_amount_max;
	}
	public void setTransferable_amount_max(String transferable_amount_max) {
		this.transferable_amount_max = transferable_amount_max;
	}
	public String getTransferable_amount_min() {
		return transferable_amount_min;
	}
	public void setTransferable_amount_min(String transferable_amount_min) {
		this.transferable_amount_min = transferable_amount_min;
	}
	public String getRemainDays() {
		return remainDays;
	}
	public void setRemainDays(String remainDays) {
		this.remainDays = remainDays;
	}
	
	
	
	
	
}
