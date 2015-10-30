package cn.com.anyitou.entity;

import java.io.Serializable;

public class BankCard implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9121851370286711657L;
	/**
	 * "card":{"id":"79","user_id":"784","bank_card_number":"9955884654846541","bank_name":"\u5de5\u5546\u94f6\u884c","bank_abbr":"ICBC","bank_card_type":"1"}
	 */
	private String id;
	private String user_id;
	private String bank_card_number;
	private String bank_name;
	private String bank_abbr;
	private String bank_card_type;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getBank_card_number() {
		return bank_card_number;
	}
	public void setBank_card_number(String bank_card_number) {
		this.bank_card_number = bank_card_number;
	}
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	public String getBank_abbr() {
		return bank_abbr;
	}
	public void setBank_abbr(String bank_abbr) {
		this.bank_abbr = bank_abbr;
	}
	public String getBank_card_type() {
		return bank_card_type;
	}
	public void setBank_card_type(String bank_card_type) {
		this.bank_card_type = bank_card_type;
	}
	
}
