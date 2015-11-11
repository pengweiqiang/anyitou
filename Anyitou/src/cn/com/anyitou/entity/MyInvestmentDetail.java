package cn.com.anyitou.entity;

import java.io.Serializable;

import cn.com.anyitou.entity.InvestRecords.PayTypeInfo;

/**
 *  获取投资记录详情
 * @author pengweiqiang
 *
 */
public class MyInvestmentDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5385405941913063687L;

	private InvestData investData;//投资数据

	private String couponDiscount;// 优惠券抵用金额

	private ProjectData projectData;// 项目数据
//
	private InterestData interestData;// 投资收益信息
//
//	private UsedCouponsByClassData usedCouponsByClassData;//  使用优惠券数据

//	 private List<interest_coupon_repaydata> interest_coupon_repaydata; //  利息券收益数据
	//
	// private List<rebate_coupon_repaydata> rebate_coupon_repaydata;//  返利券收益数据

	public void setInvestData(InvestData investData) {
		this.investData = investData;
	}

	public InvestData getInvestData() {
		return this.investData;
	}

	public void setCouponDiscount(String couponDiscount) {
		this.couponDiscount = couponDiscount;
	}

	public String getCouponDiscount() {
		return this.couponDiscount;
	}

	public void setProjectData(ProjectData projectData) {
		this.projectData = projectData;
	}

	public ProjectData getProjectData() {
		return this.projectData;
	}

	public void setInterestData(InterestData interestData) {
		this.interestData = interestData;
	}

	public InterestData getInterestData() {
		return this.interestData;
	}

//	public void setUsedCouponsByClassData(
//			UsedCouponsByClassData usedCouponsByClassData) {
//		this.usedCouponsByClassData = usedCouponsByClassData;
//	}
//
//	public UsedCouponsByClassData getUsedCouponsByClassData() {
//		return this.usedCouponsByClassData;
//	}

	// public void setInterest_coupon_repaydata(
	// List<interest_coupon_repaydata> interest_coupon_repaydata) {
	// this.interest_coupon_repaydata = interest_coupon_repaydata;
	// }
	//
	// public List<interest_coupon_repaydata> getInterest_coupon_repaydata() {
	// return this.interest_coupon_repaydata;
	// }
	//
	// public void setRebate_coupon_repaydata(
	// List<rebate_coupon_repaydata> rebate_coupon_repaydata) {
	// this.rebate_coupon_repaydata = rebate_coupon_repaydata;
	// }
	//
	// public List<rebate_coupon_repaydata> getRebate_coupon_repaydata() {
	// return this.rebate_coupon_repaydata;
	// }

	public class InvestData {
		private String id;

		private String trade_no;

		private String user_id;

		private String item_id;

		private String category;

		private String item_amount;

		private String money;

		private String real_amount;

		private String transferring_amount;

		private String transferred_amount;

		private String has_reward;

		private String item_income;

		private String invest_time;

		private String success_time;

		private String agreement_id;

		private String status;

		private String loans_flag;

		private String repay_status;

		private String repay_capital;

		private String repay_interest;

		private String penalty;

		private String unfreeze_status;

		private String debt_status;

		private String is_true;

		private String addtime;

		private String type;

		private String use_coupon;

		private String coupon_ids;

		private double totalInterest;

		private double everyDayTotalInterest;

		private double lastDayTotalInterest;

		public void setId(String id) {
			this.id = id;
		}

		public String getId() {
			return this.id;
		}

		public void setTrade_no(String trade_no) {
			this.trade_no = trade_no;
		}

		public String getTrade_no() {
			return this.trade_no;
		}

		public void setUser_id(String user_id) {
			this.user_id = user_id;
		}

		public String getUser_id() {
			return this.user_id;
		}

		public void setItem_id(String item_id) {
			this.item_id = item_id;
		}

		public String getItem_id() {
			return this.item_id;
		}

		public void setCategory(String category) {
			this.category = category;
		}

		public String getCategory() {
			return this.category;
		}

		public void setItem_amount(String item_amount) {
			this.item_amount = item_amount;
		}

		public String getItem_amount() {
			return this.item_amount;
		}

		public void setMoney(String money) {
			this.money = money;
		}

		public String getMoney() {
			return this.money;
		}

		public void setReal_amount(String real_amount) {
			this.real_amount = real_amount;
		}

		public String getReal_amount() {
			return this.real_amount;
		}

		public void setTransferring_amount(String transferring_amount) {
			this.transferring_amount = transferring_amount;
		}

		public String getTransferring_amount() {
			return this.transferring_amount;
		}

		public void setTransferred_amount(String transferred_amount) {
			this.transferred_amount = transferred_amount;
		}

		public String getTransferred_amount() {
			return this.transferred_amount;
		}

		public void setHas_reward(String has_reward) {
			this.has_reward = has_reward;
		}

		public String getHas_reward() {
			return this.has_reward;
		}

		public void setItem_income(String item_income) {
			this.item_income = item_income;
		}

		public String getItem_income() {
			return this.item_income;
		}

		public void setInvest_time(String invest_time) {
			this.invest_time = invest_time;
		}

		public String getInvest_time() {
			return this.invest_time;
		}

		public void setSuccess_time(String success_time) {
			this.success_time = success_time;
		}

		public String getSuccess_time() {
			return this.success_time;
		}

		public void setAgreement_id(String agreement_id) {
			this.agreement_id = agreement_id;
		}

		public String getAgreement_id() {
			return this.agreement_id;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getStatus() {
			return this.status;
		}

		public void setLoans_flag(String loans_flag) {
			this.loans_flag = loans_flag;
		}

		public String getLoans_flag() {
			return this.loans_flag;
		}

		public void setRepay_status(String repay_status) {
			this.repay_status = repay_status;
		}

		public String getRepay_status() {
			return this.repay_status;
		}

		public void setRepay_capital(String repay_capital) {
			this.repay_capital = repay_capital;
		}

		public String getRepay_capital() {
			return this.repay_capital;
		}

		public void setRepay_interest(String repay_interest) {
			this.repay_interest = repay_interest;
		}

		public String getRepay_interest() {
			return this.repay_interest;
		}

		public void setPenalty(String penalty) {
			this.penalty = penalty;
		}

		public String getPenalty() {
			return this.penalty;
		}

		public void setUnfreeze_status(String unfreeze_status) {
			this.unfreeze_status = unfreeze_status;
		}

		public String getUnfreeze_status() {
			return this.unfreeze_status;
		}

		public void setDebt_status(String debt_status) {
			this.debt_status = debt_status;
		}

		public String getDebt_status() {
			return this.debt_status;
		}

		public void setIs_true(String is_true) {
			this.is_true = is_true;
		}

		public String getIs_true() {
			return this.is_true;
		}

		public void setAddtime(String addtime) {
			this.addtime = addtime;
		}

		public String getAddtime() {
			return this.addtime;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getType() {
			return this.type;
		}

		public void setUse_coupon(String use_coupon) {
			this.use_coupon = use_coupon;
		}

		public String getUse_coupon() {
			return this.use_coupon;
		}

		public void setCoupon_ids(String coupon_ids) {
			this.coupon_ids = coupon_ids;
		}

		public String getCoupon_ids() {
			return this.coupon_ids;
		}

		public void setTotalInterest(double totalInterest) {
			this.totalInterest = totalInterest;
		}

		public double getTotalInterest() {
			return this.totalInterest;
		}

		public void setEveryDayTotalInterest(double everyDayTotalInterest) {
			this.everyDayTotalInterest = everyDayTotalInterest;
		}

		public double getEveryDayTotalInterest() {
			return this.everyDayTotalInterest;
		}

		public void setLastDayTotalInterest(double lastDayTotalInterest) {
			this.lastDayTotalInterest = lastDayTotalInterest;
		}

		public double getLastDayTotalInterest() {
			return this.lastDayTotalInterest;
		}

	}

	public class ProjectData {
		private String id;

		private String item_title;

		private String sub_title;

		private String number_id;

		private String category;

		private String borrower_user_id;

		private String company_id;

		private String pay_type;

		private String guarantee_type;

		private String guarantee_id;

		private String evaluation_company_id;

		private String rate_of_interest;

		private String reward_apr;

		private String scale;

		private String financing_amount;

		private String credito_amount;

		private String investment;

		private String over_amount;

		private String begin_time;

		private String repayment_time;

		private String delay;

		private String raise_begin_time;

		private String raise_delay;

		private String credit;

		private String status;

		private String invest_status;

		private String item_pic;

		private String description;

		private String capital_opration;

		private String collateral_info;

		private String risk_control;

		private String desc_detail;

		private String addtime;

		private String isrecommend;

		private String invested_nums;

		private String send_status;

		private PayTypeInfo pay_type_info;

		private String invest_status_label;

		private String remain_amount;

		private String borrow_days;

		private String leave_time;

		private String isstarted;

		private RaiseRemainTime raise_remain_time;

		private String today_invest_days;

		public void setId(String id) {
			this.id = id;
		}

		public String getId() {
			return this.id;
		}

		public void setItem_title(String item_title) {
			this.item_title = item_title;
		}

		public String getItem_title() {
			return this.item_title;
		}

		public void setSub_title(String sub_title) {
			this.sub_title = sub_title;
		}

		public String getSub_title() {
			return this.sub_title;
		}

		public void setNumber_id(String number_id) {
			this.number_id = number_id;
		}

		public String getNumber_id() {
			return this.number_id;
		}

		public void setCategory(String category) {
			this.category = category;
		}

		public String getCategory() {
			return this.category;
		}

		public void setBorrower_user_id(String borrower_user_id) {
			this.borrower_user_id = borrower_user_id;
		}

		public String getBorrower_user_id() {
			return this.borrower_user_id;
		}

		public void setCompany_id(String company_id) {
			this.company_id = company_id;
		}

		public String getCompany_id() {
			return this.company_id;
		}

		public void setPay_type(String pay_type) {
			this.pay_type = pay_type;
		}

		public String getPay_type() {
			return this.pay_type;
		}

		public void setGuarantee_type(String guarantee_type) {
			this.guarantee_type = guarantee_type;
		}

		public String getGuarantee_type() {
			return this.guarantee_type;
		}

		public void setGuarantee_id(String guarantee_id) {
			this.guarantee_id = guarantee_id;
		}

		public String getGuarantee_id() {
			return this.guarantee_id;
		}

		public void setEvaluation_company_id(String evaluation_company_id) {
			this.evaluation_company_id = evaluation_company_id;
		}

		public String getEvaluation_company_id() {
			return this.evaluation_company_id;
		}

		public void setRate_of_interest(String rate_of_interest) {
			this.rate_of_interest = rate_of_interest;
		}

		public String getRate_of_interest() {
			return this.rate_of_interest;
		}

		public void setReward_apr(String reward_apr) {
			this.reward_apr = reward_apr;
		}

		public String getReward_apr() {
			return this.reward_apr;
		}

		public void setScale(String scale) {
			this.scale = scale;
		}

		public String getScale() {
			return this.scale;
		}

		public void setFinancing_amount(String financing_amount) {
			this.financing_amount = financing_amount;
		}

		public String getFinancing_amount() {
			return this.financing_amount;
		}

		public void setCredito_amount(String credito_amount) {
			this.credito_amount = credito_amount;
		}

		public String getCredito_amount() {
			return this.credito_amount;
		}

		public void setInvestment(String investment) {
			this.investment = investment;
		}

		public String getInvestment() {
			return this.investment;
		}

		public void setOver_amount(String over_amount) {
			this.over_amount = over_amount;
		}

		public String getOver_amount() {
			return this.over_amount;
		}

		public void setBegin_time(String begin_time) {
			this.begin_time = begin_time;
		}

		public String getBegin_time() {
			return this.begin_time;
		}

		public void setRepayment_time(String repayment_time) {
			this.repayment_time = repayment_time;
		}

		public String getRepayment_time() {
			return this.repayment_time;
		}

		public void setDelay(String delay) {
			this.delay = delay;
		}

		public String getDelay() {
			return this.delay;
		}

		public void setRaise_begin_time(String raise_begin_time) {
			this.raise_begin_time = raise_begin_time;
		}

		public String getRaise_begin_time() {
			return this.raise_begin_time;
		}

		public void setRaise_delay(String raise_delay) {
			this.raise_delay = raise_delay;
		}

		public String getRaise_delay() {
			return this.raise_delay;
		}

		public void setCredit(String credit) {
			this.credit = credit;
		}

		public String getCredit() {
			return this.credit;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getStatus() {
			return this.status;
		}

		public void setInvest_status(String invest_status) {
			this.invest_status = invest_status;
		}

		public String getInvest_status() {
			return this.invest_status;
		}

		public void setItem_pic(String item_pic) {
			this.item_pic = item_pic;
		}

		public String getItem_pic() {
			return this.item_pic;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getDescription() {
			return this.description;
		}

		public void setCapital_opration(String capital_opration) {
			this.capital_opration = capital_opration;
		}

		public String getCapital_opration() {
			return this.capital_opration;
		}

		public void setCollateral_info(String collateral_info) {
			this.collateral_info = collateral_info;
		}

		public String getCollateral_info() {
			return this.collateral_info;
		}

		public void setRisk_control(String risk_control) {
			this.risk_control = risk_control;
		}

		public String getRisk_control() {
			return this.risk_control;
		}

		public void setDesc_detail(String desc_detail) {
			this.desc_detail = desc_detail;
		}

		public String getDesc_detail() {
			return this.desc_detail;
		}

		public void setAddtime(String addtime) {
			this.addtime = addtime;
		}

		public String getAddtime() {
			return this.addtime;
		}

		public void setIsrecommend(String isrecommend) {
			this.isrecommend = isrecommend;
		}

		public String getIsrecommend() {
			return this.isrecommend;
		}

		public void setInvested_nums(String invested_nums) {
			this.invested_nums = invested_nums;
		}

		public String getInvested_nums() {
			return this.invested_nums;
		}

		public void setSend_status(String send_status) {
			this.send_status = send_status;
		}

		public String getSend_status() {
			return this.send_status;
		}

		public void setPayTypeInfo(PayTypeInfo pay_type_info) {
			this.pay_type_info = pay_type_info;
		}

		public PayTypeInfo getPayTypeInfo() {
			return this.pay_type_info;
		}

		public void setInvest_status_label(String invest_status_label) {
			this.invest_status_label = invest_status_label;
		}

		public String getInvest_status_label() {
			return this.invest_status_label;
		}

		public void setRemain_amount(String remain_amount) {
			this.remain_amount = remain_amount;
		}

		public String getRemain_amount() {
			return this.remain_amount;
		}

		public void setBorrow_days(String borrow_days) {
			this.borrow_days = borrow_days;
		}

		public String getBorrow_days() {
			return this.borrow_days;
		}

		public void setLeave_time(String leave_time) {
			this.leave_time = leave_time;
		}

		public String getLeave_time() {
			return this.leave_time;
		}

		public void setIsstarted(String isstarted) {
			this.isstarted = isstarted;
		}

		public String getIsstarted() {
			return this.isstarted;
		}

		public void setRaise_remain_time(RaiseRemainTime raise_remain_time) {
			this.raise_remain_time = raise_remain_time;
		}

		public RaiseRemainTime getRaise_remain_time() {
			return this.raise_remain_time;
		}

		public void setToday_invest_days(String today_invest_days) {
			this.today_invest_days = today_invest_days;
		}

		public String getToday_invest_days() {
			return this.today_invest_days;
		}

	}

	public class Total {
		private String days;

		private String interest;

		private String dayRate;

		public void setDays(String days) {
			this.days = days;
		}

		public String getDays() {
			return this.days;
		}

		public void setInterest(String interest) {
			this.interest = interest;
		}

		public String getInterest() {
			return this.interest;
		}

		public void setDayRate(String dayRate) {
			this.dayRate = dayRate;
		}

		public String getDayRate() {
			return this.dayRate;
		}

	}

	public class EveryDay {
		private String interest;

		public void setInterest(String interest) {
			this.interest = interest;
		}

		public String getInterest() {
			return this.interest;
		}

	}

	public class LastDay {
		private String interest;

		public void setInterest(String interest) {
			this.interest = interest;
		}

		public String getInterest() {
			return this.interest;
		}

	}

	public class InterestData {
		private Total total;

		// private List<detail> detail;

		private EveryDay everyDay;

		private LastDay lastDay;

		public void setTotal(Total total) {
			this.total = total;
		}

		public Total getTotal() {
			return this.total;
		}

		// public void setDetail(List<detail> detail) {
		// this.detail = detail;
		// }
		//
		// public List<detail> getDetail() {
		// return this.detail;
		// }

		public void setEveryDay(EveryDay everyDay) {
			this.everyDay = everyDay;
		}

		public EveryDay getEveryDay() {
			return this.everyDay;
		}

		public void setLastDay(LastDay lastDay) {
			this.lastDay = lastDay;
		}

		public LastDay getLastDay() {
			return this.lastDay;
		}

	}

	public class UsedCouponsByClassData {

	}

}
