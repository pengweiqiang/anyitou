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
	private String loantype;
	private String pnum;
	private String yearrate;
	private String addrate;
	private String money;
	private String nowmoney;
	private String xmqx;
	private String nstatus;
	private String company;
	private String content;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLoantype() {
		return loantype;
	}
	public void setLoantype(String loantype) {
		this.loantype = loantype;
	}
	public String getPnum() {
		return pnum;
	}
	public void setPnum(String pnum) {
		this.pnum = pnum;
	}
	public String getYearrate() {
		return yearrate;
	}
	public void setYearrate(String yearrate) {
		this.yearrate = yearrate;
	}
	public String getAddrate() {
		return addrate;
	}
	public void setAddrate(String addrate) {
		this.addrate = addrate;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getNowmoney() {
		return nowmoney;
	}
	public void setNowmoney(String nowmoney) {
		this.nowmoney = nowmoney;
	}
	public String getXmqx() {
		return xmqx;
	}
	public void setXmqx(String xmqx) {
		this.xmqx = xmqx;
	}
	public String getNstatus() {
		return nstatus;
	}
	public void setNstatus(String nstatus) {
		this.nstatus = nstatus;
	}
	
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	
}
