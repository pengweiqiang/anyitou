package cn.com.anyitou.entity;

import java.io.Serializable;

/**
 * 手续费
 * @author will
 *
 */
public class CashFee implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1753970439920237100L;
	
	private String error;
	private String hf_serv;//提现手续费
	private String serv_fee;//第三方平台资金托管费
	private String readyserv;//已付托管费
	private String money;//本次提现金额
	private String repaymoney;//已回款金额
	private String withdraw;//已提现金额
	private String summoney;//提现手续费+第三方平台资金托管费
	private String maxWithdraw;//最大可提现金额
	private String getdate;//预计到账日期
	
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getHf_serv() {
		return hf_serv;
	}
	public void setHf_serv(String hf_serv) {
		this.hf_serv = hf_serv;
	}
	public String getServ_fee() {
		return serv_fee;
	}
	public void setServ_fee(String serv_fee) {
		this.serv_fee = serv_fee;
	}
	public String getReadyserv() {
		return readyserv;
	}
	public void setReadyserv(String readyserv) {
		this.readyserv = readyserv;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getRepaymoney() {
		return repaymoney;
	}
	public void setRepaymoney(String repaymoney) {
		this.repaymoney = repaymoney;
	}
	public String getWithdraw() {
		return withdraw;
	}
	public void setWithdraw(String withdraw) {
		this.withdraw = withdraw;
	}
	public String getSummoney() {
		return summoney;
	}
	public void setSummoney(String summoney) {
		this.summoney = summoney;
	}
	public String getMaxWithdraw() {
		return maxWithdraw;
	}
	public void setMaxWithdraw(String maxWithdraw) {
		this.maxWithdraw = maxWithdraw;
	}
	public String getGetdate() {
		return getdate;
	}
	public void setGetdate(String getdate) {
		this.getdate = getdate;
	}
	
	
	

}
