package cn.com.anyitou.entity;

import java.io.Serializable;

/**
 * 提货界面信息
 * @author will
 *
 */
public class CashPageInfo implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1974903275485835423L;
	private String eng;//
	private String zh;//银行名称
	private String logo;//银行Logo
	private String identity;//卡号
	private String avl_bal;//可用余额
	
	
	
	public String getEng() {
		return eng;
	}
	public void setEng(String eng) {
		this.eng = eng;
	}
	public String getZh() {
		return zh;
	}
	public void setZh(String zh) {
		this.zh = zh;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public String getAvl_bal() {
		return avl_bal;
	}
	public void setAvl_bal(String avl_bal) {
		this.avl_bal = avl_bal;
	}
	
	
	
	
}
