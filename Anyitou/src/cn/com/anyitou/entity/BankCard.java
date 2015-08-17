package cn.com.anyitou.entity;

import java.io.Serializable;

public class BankCard implements Serializable{

	/**
	 * "eng": "BOC",
        "zh": "中国银行",
        "logo": "http://cmstest.18link.com/statics/images/zjxd/banklogo/BOC.png",
        "identity": "6216610100012980328"
	 */
	private static final long serialVersionUID = 4493712182965164379L;
	private String eng;
	private String zh;
	private String logo;
	private String identity;
	
	
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
	
	
	
	
}
