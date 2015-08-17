package cn.com.anyitou.entity;

import java.io.Serializable;

import cn.com.anyitou.utils.StringUtils;
/**
 * 我的资产
 * @author will
 *
 */
public class MyAssets implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8966252426013642832L;
	private LableValue a_0;
	private LableValue a_1;
	private LableValue a_2;
	private LableValue a_3;
	private LableValue a_4;
	private LableValue a_5;
	private LableValue a_6;
	private LableValue a_7;
	private LableValue a_8;
	
	private String token;
	
	public LableValue getA_0() {
		return a_0;
	}
	public void setA_0(LableValue a_0) {
		this.a_0 = a_0;
	}
	public LableValue getA_1() {
		return a_1;
	}
	public void setA_1(LableValue a_1) {
		this.a_1 = a_1;
	}
	public LableValue getA_2() {
		return a_2;
	}
	public void setA_2(LableValue a_2) {
		this.a_2 = a_2;
	}
	public LableValue getA_3() {
		return a_3;
	}
	public void setA_3(LableValue a_3) {
		this.a_3 = a_3;
	}
	public LableValue getA_4() {
		return a_4;
	}
	public void setA_4(LableValue a_4) {
		this.a_4 = a_4;
	}
	public LableValue getA_5() {
		return a_5;
	}
	public void setA_5(LableValue a_5) {
		this.a_5 = a_5;
	}
	public LableValue getA_6() {
		return a_6;
	}
	public void setA_6(LableValue a_6) {
		this.a_6 = a_6;
	}
	public LableValue getA_7() {
		return a_7;
	}
	public void setA_7(LableValue a_7) {
		this.a_7 = a_7;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public LableValue getA_8() {
		if(a_8==null){
			a_8 = new LableValue();
			a_8.setLabel("");
			a_8.setValue("");
		}
		return a_8;
	}
	public void setA_8(LableValue a_8) {
		this.a_8 = a_8;
	}
	
	
	
}
