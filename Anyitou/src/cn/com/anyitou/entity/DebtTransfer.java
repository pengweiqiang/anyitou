package cn.com.anyitou.entity;

import java.io.Serializable;
/**
 * 用户的债权转让列表
 * @author pengweiqiang
 *
 */
public class DebtTransfer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9144388183668526953L;
	/**
	 * 
	 */
	
	private String id;
	private String number;
	private String user_id;
	private String invest_id;
	private String item_id;
	private String status;
	private String is_show;
	private String category;
	private String amount;
	private String real_amount;
	private String buyer_apr;
	private String repayment_time;
	private String price;
	private String transferred_amount;
	private String transferred_num;
	private String addtime;
	private String sell_days;
	private String sell_start_time;
	private String sell_end_time;
	private String real_apr;
	private String desc;
	
	private String buyProgress;
	private String remainAmount;
	
	private InvestData investData;
	
	private String minBuyAmount;
	private TimeData timeData;
	private String realApr;
	
	public class TimeData implements Serializable{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 2617434537635405384L;
		
		private String sell_end_timestamp;
		private String diff;
		private String today_invest_days;
		public String getSell_end_timestamp() {
			return sell_end_timestamp;
		}
		public void setSell_end_timestamp(String sell_end_timestamp) {
			this.sell_end_timestamp = sell_end_timestamp;
		}
		public String getDiff() {
			return diff;
		}
		public void setDiff(String diff) {
			this.diff = diff;
		}
		public String getToday_invest_days() {
			return today_invest_days;
		}
		public void setToday_invest_days(String today_invest_days) {
			this.today_invest_days = today_invest_days;
		}
		
		
	}
	
	public class InvestData implements Serializable{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 6332039208615457223L;
		private String id;
		private String amount;
		private String money;
		private String transferring_amount;
		private String transferred_amount;
		private String invest_time;
		
		private String trade_no;
		private String user_id;
		private String item_id;
		private String category;
		private String item_amount;
		private String real_amount;
		private String status;
		private String debt_status;
		private String type;
		private String use_coupon;
		private String coupon_ids;
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getAmount() {
			return amount;
		}
		public void setAmount(String amount) {
			this.amount = amount;
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
		public String getInvest_time() {
			return invest_time;
		}
		public void setInvest_time(String invest_time) {
			this.invest_time = invest_time;
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
		public String getReal_amount() {
			return real_amount;
		}
		public void setReal_amount(String real_amount) {
			this.real_amount = real_amount;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getDebt_status() {
			return debt_status;
		}
		public void setDebt_status(String debt_status) {
			this.debt_status = debt_status;
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
		
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getInvest_id() {
		return invest_id;
	}

	public void setInvest_id(String invest_id) {
		this.invest_id = invest_id;
	}

	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIs_show() {
		return is_show;
	}

	public void setIs_show(String is_show) {
		this.is_show = is_show;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getReal_amount() {
		return real_amount;
	}

	public void setReal_amount(String real_amount) {
		this.real_amount = real_amount;
	}

	public String getBuyer_apr() {
		return buyer_apr;
	}

	public void setBuyer_apr(String buyer_apr) {
		this.buyer_apr = buyer_apr;
	}

	public String getRepayment_time() {
		return repayment_time;
	}

	public void setRepayment_time(String repayment_time) {
		this.repayment_time = repayment_time;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getTransferred_amount() {
		return transferred_amount;
	}

	public void setTransferred_amount(String transferred_amount) {
		this.transferred_amount = transferred_amount;
	}

	public String getTransferred_num() {
		return transferred_num;
	}

	public void setTransferred_num(String transferred_num) {
		this.transferred_num = transferred_num;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getSell_days() {
		return sell_days;
	}

	public void setSell_days(String sell_days) {
		this.sell_days = sell_days;
	}

	public String getSell_start_time() {
		return sell_start_time;
	}

	public void setSell_start_time(String sell_start_time) {
		this.sell_start_time = sell_start_time;
	}

	public String getSell_end_time() {
		return sell_end_time;
	}

	public void setSell_end_time(String sell_end_time) {
		this.sell_end_time = sell_end_time;
	}

	public String getReal_apr() {
		return real_apr;
	}

	public void setReal_apr(String real_apr) {
		this.real_apr = real_apr;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getBuyProgress() {
		return buyProgress;
	}

	public void setBuyProgress(String buyProgress) {
		this.buyProgress = buyProgress;
	}

	public String getRemainAmount() {
		return remainAmount;
	}

	public void setRemainAmount(String remainAmount) {
		this.remainAmount = remainAmount;
	}

	public InvestData getInvestData() {
		return investData;
	}

	public void setInvestData(InvestData investData) {
		this.investData = investData;
	}

	public String getMinBuyAmount() {
		return minBuyAmount;
	}

	public void setMinBuyAmount(String minBuyAmount) {
		this.minBuyAmount = minBuyAmount;
	}

	public TimeData getTimeData() {
		return timeData;
	}

	public void setTimeData(TimeData timeData) {
		this.timeData = timeData;
	}

	public String getRealApr() {
		return realApr;
	}

	public void setRealApr(String realApr) {
		this.realApr = realApr;
	}
	
	
}
