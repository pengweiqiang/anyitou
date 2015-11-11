package cn.com.anyitou.entity;

import java.io.Serializable;

/**
 * 投资明细记录
 * @author will
 *
 */
public class InvestRecords implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6539933435377478147L;
	private String id;
	private String trade_no;
	private String user_id;//+
	private String item_id;
	private String category;//  类型:  invest:企贷  chedai:车贷  fangdai：房贷  reward:新手体验
	private String item_amount; // 投资金额
	private String money;// 支付金额
	private String real_amount;//
	private String transferring_amount;// 转让中金额
	private String transferred_amount;// 已转让金额
	private String item_income; // 预期收益
	private String invest_time; // 投资时间
	private String success_time; // 投资时间
	private String agreement_id;//合同编号
	private String status;//  状态  1:成功
	private String loans_flag; // 放款状态  1:已放款
	private String repay_status;// 还款状态：0:未还款   1：还款中   2:已还款
	private String repay_capital;//// 已还本金
	private String repay_interest;// 已付利息
	private String penalty;
	private String unfreeze_status;
	private String debt_status;// 转让状态：0：未转让  1:部分转让  2：已转让
	private String remark;
	private String is_true;
	private String addtime; // 订单产生时间
	private String type; // 投资方式  1：直投  2：债权
	private String use_coupon;// 优惠券使用状态  0:未使用  1:使用
	private String coupon_ids;// 使用的优惠券id
	private String item_title;//项目名称
	private String invest_status;//投资状态
	private String rate_of_interest;// 年华收益率
	private String reward_apr;//奖励年华收益
	private String repayment_time;// 还款日期
	private String pay_type;//还款方式
	private PayTypeInfo pay_type_info;//
	private String invest_days;//投资天数
	
	public class PayTypeInfo implements Serializable{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 3551027147786639247L;
		private String name;
		private String value;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getItem_amount() {
		return item_amount;
	}
	public void setItem_amount(String item_amount) {
		this.item_amount = item_amount;
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
	public String getSuccess_time() {
		return success_time;
	}
	public void setSuccess_time(String success_time) {
		this.success_time = success_time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLoans_flag() {
		return loans_flag;
	}
	public void setLoans_flag(String loans_flag) {
		this.loans_flag = loans_flag;
	}
	public String getRepay_status() {
		return repay_status;
	}
	public void setRepay_status(String repay_status) {
		this.repay_status = repay_status;
	}
	public String getDebt_status() {
		return debt_status;
	}
	public void setDebt_status(String debt_status) {
		this.debt_status = debt_status;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUse_coupon() {
		return use_coupon;
	}
	public void setUse_coupon(String use_coupon) {
		this.use_coupon = use_coupon;
	}
	public String getCoupon_ids() {
		return coupon_ids;
	}
	public void setCoupon_ids(String coupon_ids) {
		this.coupon_ids = coupon_ids;
	}
	public String getReal_amount() {
		return real_amount;
	}
	public void setReal_amount(String real_amount) {
		this.real_amount = real_amount;
	}
	public String getAgreement_id() {
		return agreement_id;
	}
	public void setAgreement_id(String agreement_id) {
		this.agreement_id = agreement_id;
	}
	public String getRepay_capital() {
		return repay_capital;
	}
	public void setRepay_capital(String repay_capital) {
		this.repay_capital = repay_capital;
	}
	public String getRepay_interest() {
		return repay_interest;
	}
	public void setRepay_interest(String repay_interest) {
		this.repay_interest = repay_interest;
	}
	public String getPenalty() {
		return penalty;
	}
	public void setPenalty(String penalty) {
		this.penalty = penalty;
	}
	public String getUnfreeze_status() {
		return unfreeze_status;
	}
	public void setUnfreeze_status(String unfreeze_status) {
		this.unfreeze_status = unfreeze_status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIs_true() {
		return is_true;
	}
	public void setIs_true(String is_true) {
		this.is_true = is_true;
	}
	public String getItem_title() {
		return item_title;
	}
	public void setItem_title(String item_title) {
		this.item_title = item_title;
	}
	public String getInvest_status() {
		return invest_status;
	}
	public void setInvest_status(String invest_status) {
		this.invest_status = invest_status;
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
	public String getRepayment_time() {
		return repayment_time;
	}
	public void setRepayment_time(String repayment_time) {
		this.repayment_time = repayment_time;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public PayTypeInfo getPay_type_info() {
		return pay_type_info;
	}
	public void setPay_type_info(PayTypeInfo pay_type_info) {
		this.pay_type_info = pay_type_info;
	}
	public String getInvest_days() {
		return invest_days;
	}
	public void setInvest_days(String invest_days) {
		this.invest_days = invest_days;
	}

}
