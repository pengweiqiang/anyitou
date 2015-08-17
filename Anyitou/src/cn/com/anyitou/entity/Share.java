package cn.com.anyitou.entity;

import java.io.Serializable;

public class Share implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4523586730279657769L;
	
	private String qq_space;
	private String qq_friend;
	private String wx_friend;
	private String wx_allfriend;
	private String sina_wb;
	private String email;
	public String getQq_space() {
		return qq_space;
	}
	public void setQq_space(String qq_space) {
		this.qq_space = qq_space;
	}
	public String getQq_friend() {
		return qq_friend;
	}
	public void setQq_friend(String qq_friend) {
		this.qq_friend = qq_friend;
	}
	public String getWx_friend() {
		return wx_friend;
	}
	public void setWx_friend(String wx_friend) {
		this.wx_friend = wx_friend;
	}
	public String getWx_allfriend() {
		return wx_allfriend;
	}
	public void setWx_allfriend(String wx_allfriend) {
		this.wx_allfriend = wx_allfriend;
	}
	public String getSina_wb() {
		return sina_wb;
	}
	public void setSina_wb(String sina_wb) {
		this.sina_wb = sina_wb;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	
}
