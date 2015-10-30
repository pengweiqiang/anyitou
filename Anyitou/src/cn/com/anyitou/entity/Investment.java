package cn.com.anyitou.entity;

import java.io.Serializable;

/**
 * 投资项目
 */
public class Investment implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5203943094386982392L;

	private String id;
	private String item_title;
	private String number_id;
	private String category;
	private String pay_type;
	private String guarantee_type;
	private String guarantee_id;
	private String rate_of_interest;
	private String reward_apr;
	private String financing_amount;
	private String over_amount;
	private String investment;
	private String begin_time;
	private String repayment_time;
	private String delay;
	private String raise_begin_time;
	private String status;
	private String invest_status;
	private String isrecommend;
	private String invested_nums;
	private LableValue pay_type_info;
	private String invest_status_label;
	private String remain_amount;
	private String scale;
	private String borrow_days;
	private String leave_time;
	private String isstarted;
	private RaiseRemainTime raise_remain_time;
	private String EachInvestmentInterest;
	private String rewardEachInvestmentInterest;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getItem_title() {
		return item_title;
	}
	public void setItem_title(String item_title) {
		this.item_title = item_title;
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
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getGuarantee_type() {
		return guarantee_type;
	}
	public void setGuarantee_type(String guarantee_type) {
		this.guarantee_type = guarantee_type;
	}
	public String getGuarantee_id() {
		return guarantee_id;
	}
	public void setGuarantee_id(String guarantee_id) {
		this.guarantee_id = guarantee_id;
	}
	public String getRate_of_interest() {
		return rate_of_interest;
	}
	public void setRate_of_interest(String rate_of_interest) {
		this.rate_of_interest = rate_of_interest;
	}
	public String getReward_apr() {
		return reward_apr;
	}
	public void setReward_apr(String reward_apr) {
		this.reward_apr = reward_apr;
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
	public String getDelay() {
		return delay;
	}
	public void setDelay(String delay) {
		this.delay = delay;
	}
	public String getRaise_begin_time() {
		return raise_begin_time;
	}
	public void setRaise_begin_time(String raise_begin_time) {
		this.raise_begin_time = raise_begin_time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getInvest_status() {
		return invest_status;
	}
	public void setInvest_status(String invest_status) {
		this.invest_status = invest_status;
	}
	public String getIsrecommend() {
		return isrecommend;
	}
	public void setIsrecommend(String isrecommend) {
		this.isrecommend = isrecommend;
	}
	public String getInvested_nums() {
		return invested_nums;
	}
	public void setInvested_nums(String invested_nums) {
		this.invested_nums = invested_nums;
	}
	public LableValue getPay_type_info() {
		return pay_type_info;
	}
	public void setPay_type_info(LableValue pay_type_info) {
		this.pay_type_info = pay_type_info;
	}
	public String getInvest_status_label() {
		return invest_status_label;
	}
	public void setInvest_status_label(String invest_status_label) {
		this.invest_status_label = invest_status_label;
	}
	public String getRemain_amount() {
		return remain_amount;
	}
	public void setRemain_amount(String remain_amount) {
		this.remain_amount = remain_amount;
	}
	public String getScale() {
		return scale;
	}
	public void setScale(String scale) {
		this.scale = scale;
	}
	public String getBorrow_days() {
		return borrow_days;
	}
	public void setBorrow_days(String borrow_days) {
		this.borrow_days = borrow_days;
	}
	public String getLeave_time() {
		return leave_time;
	}
	public void setLeave_time(String leave_time) {
		this.leave_time = leave_time;
	}
	public String getIsstarted() {
		return isstarted;
	}
	public void setIsstarted(String isstarted) {
		this.isstarted = isstarted;
	}
	public RaiseRemainTime getRaise_remain_time() {
		return raise_remain_time;
	}
	public void setRaise_remain_time(RaiseRemainTime raise_remain_time) {
		this.raise_remain_time = raise_remain_time;
	}
	public String getEachInvestmentInterest() {
		return EachInvestmentInterest;
	}
	public void setEachInvestmentInterest(String eachInvestmentInterest) {
		EachInvestmentInterest = eachInvestmentInterest;
	}
	public String getRewardEachInvestmentInterest() {
		return rewardEachInvestmentInterest;
	}
	public void setRewardEachInvestmentInterest(String rewardEachInvestmentInterest) {
		this.rewardEachInvestmentInterest = rewardEachInvestmentInterest;
	}
	
}
