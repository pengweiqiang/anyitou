package cn.com.anyitou.entity;

import java.io.Serializable;

/**
 * 我的投资
 * @author pengweiqiang
 *
 */
public class MyInvestment implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8808444071454538952L;
	
	private String id;
	private String pid;
	private String loantypename;
	private String pnum;
	private String trans_amt;//投资金额
	private String shoudget;//应回款
	private String alreadyget;//已回款
	private String worseget;//未回款
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getLoantypename() {
		return loantypename;
	}
	public void setLoantypename(String loantypename) {
		this.loantypename = loantypename;
	}
	public String getPnum() {
		return pnum;
	}
	public void setPnum(String pnum) {
		this.pnum = pnum;
	}
	public String getTrans_amt() {
		return trans_amt;
	}
	public void setTrans_amt(String trans_amt) {
		this.trans_amt = trans_amt;
	}
	public String getShoudget() {
		return shoudget;
	}
	public void setShoudget(String shoudget) {
		this.shoudget = shoudget;
	}
	public String getAlreadyget() {
		return alreadyget;
	}
	public void setAlreadyget(String alreadyget) {
		this.alreadyget = alreadyget;
	}
	public String getWorseget() {
		return worseget;
	}
	public void setWorseget(String worseget) {
		this.worseget = worseget;
	}
	
	
	
	
	
}
