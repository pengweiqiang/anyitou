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
	private String item_title;//项目名称
	private String number_id;//项目编号
	private String category;//项目类型
	private String pay_type;//还款方式，名字见：pay_type_info
	private String guarantee_type;//担保方式
	private String guarantee_id;//担保机构id
	private String rate_of_interest;//年化收益率
	private String reward_apr;//平台奖励年化收益率
	private String financing_amount;//募集金额
	private String over_amount;//已募集金额
	private String investment;//起投金额投资递增金额
	private String begin_time;//项目起始日期
	private String repayment_time;//还款日期
	private String delay;//募集天数
	private String raise_begin_time;//募集开始时间
	private String status;//项目状态：0：待审核 1：审核通过
	private String invest_status;//投资状态： 0:未开放  1:开放投资  2:募集完成   3:还款中   4:还款完成   5:逾期
	private String isrecommend;//推荐状态： 0:不推荐  1:推荐
	private String invested_nums; // 投资次数
	private LableValue pay_type_info;// 还款方式信息
	private String invest_status_label; // 投资状态名称
	private String remain_amount; // 投资状态名称
	private String scale;// 募集进度
	private String borrow_days;// 项目期限
	private String leave_time;// 募集结束时间戳
	private String isstarted;// 开放投资状态  true:开放    false:未开放
	private RaiseRemainTime raise_remain_time;// 距离募集结束时间差
	private String EachInvestmentInterest; // 千份收益
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
