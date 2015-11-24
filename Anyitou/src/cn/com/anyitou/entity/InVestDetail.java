package cn.com.anyitou.entity;

import java.io.Serializable;
import java.util.List;

public class InVestDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5147371067939011814L;

	private String id;
	private String name;
	private String number_id;
	private String category;
	private String apr;
	private String reward_apr;
	private String borrow_days;
	private String scale;
	private String invest_status;// 投资状态： 0:未开放  1:开放投资  2:募集完成   3:还款中   4:还款完成   5:逾期
	private String pay_type;
	private LableValue pay_type_info;
	private String financing_amount;
	private String over_amount;
	private String investment;
	private String begin_time;
	private String repayment_time;
	private String raise_begin_time;
	private String raise_delay;
	private String invested_nums;
	private String isrecommend;
	private String guarantee_id;
	private String isstarted;
	
	
	public String getIsstarted() {
		return isstarted;
	}
	public void setIsstarted(String isstarted) {
		this.isstarted = isstarted;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber_id() {
		return number_id;
	}
	public void setNumber_id(String number_id) {
		this.number_id = number_id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getApr() {
		return apr;
	}
	public void setApr(String apr) {
		this.apr = apr;
	}
	public String getReward_apr() {
		return reward_apr;
	}
	public void setReward_apr(String reward_apr) {
		this.reward_apr = reward_apr;
	}
	public String getBorrow_days() {
		return borrow_days;
	}
	public void setBorrow_days(String borrow_days) {
		this.borrow_days = borrow_days;
	}
	public String getScale() {
		return scale;
	}
	public void setScale(String scale) {
		this.scale = scale;
	}
	public String getInvest_status() {
		return invest_status;
	}
	public void setInvest_status(String invest_status) {
		this.invest_status = invest_status;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public LableValue getPay_type_info() {
		return pay_type_info;
	}
	public void setPay_type_info(LableValue pay_type_info) {
		this.pay_type_info = pay_type_info;
	}
	public String getFinancing_amount() {
		return financing_amount;
	}
	public void setFinancing_amount(String financing_amount) {
		this.financing_amount = financing_amount;
	}
	public String getOver_amount() {
		return over_amount;
	}
	public void setOver_amount(String over_amount) {
		this.over_amount = over_amount;
	}
	public String getInvestment() {
		return investment;
	}
	public void setInvestment(String investment) {
		this.investment = investment;
	}
	public String getBegin_time() {
		return begin_time;
	}
	public void setBegin_time(String begin_time) {
		this.begin_time = begin_time;
	}
	public String getRepayment_time() {
		return repayment_time;
	}
	public void setRepayment_time(String repayment_time) {
		this.repayment_time = repayment_time;
	}
	public String getRaise_begin_time() {
		return raise_begin_time;
	}
	public void setRaise_begin_time(String raise_begin_time) {
		this.raise_begin_time = raise_begin_time;
	}
	public String getRaise_delay() {
		return raise_delay;
	}
	public void setRaise_delay(String raise_delay) {
		this.raise_delay = raise_delay;
	}
	public String getInvested_nums() {
		return invested_nums;
	}
	public void setInvested_nums(String invested_nums) {
		this.invested_nums = invested_nums;
	}
	public String getIsrecommend() {
		return isrecommend;
	}
	public void setIsrecommend(String isrecommend) {
		this.isrecommend = isrecommend;
	}
	public String getGuarantee_id() {
		return guarantee_id;
	}
	public void setGuarantee_id(String guarantee_id) {
		this.guarantee_id = guarantee_id;
	}
	
}
