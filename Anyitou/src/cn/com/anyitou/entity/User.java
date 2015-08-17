package cn.com.anyitou.entity;

import java.io.Serializable;

import cn.com.anyitou.utils.StringUtils;

public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;//用户id,全局唯一
	private String user_name;//用户名
	private String pass_word;//密码
	private String ishfuser;//1代表开通汇付 0 代表未开通汇付
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getPass_word() {
		return pass_word;
	}
	public void setPass_word(String pass_word) {
		this.pass_word = pass_word;
	}
	public String getIshfuser() {
		return StringUtils.isEmpty(ishfuser)?"":ishfuser;
	}
	public void setIshfuser(String ishfuser) {
		this.ishfuser = ishfuser;
	}
	
	
	
	
}
