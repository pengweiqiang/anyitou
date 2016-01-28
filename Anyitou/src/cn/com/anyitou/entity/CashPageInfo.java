package cn.com.anyitou.entity;

import java.io.Serializable;

/**
 * 提现界面信息
 * @author pengweiqiang
 *
 */
public class CashPageInfo implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1974903275485835423L;
	
	
	private Money money;
	
	private BankCard card;

	public Money getMoney() {
		return money;
	}

	public void setMoney(Money money) {
		this.money = money;
	}

	public BankCard getCard() {
		return card;
	}

	public void setCard(BankCard card) {
		this.card = card;
	}
	
	
	
	
}
