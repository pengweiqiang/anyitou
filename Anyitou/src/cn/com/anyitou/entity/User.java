package cn.com.anyitou.entity;

import java.io.Serializable;

import cn.com.anyitou.utils.StringUtils;

public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;//用户id,全局唯一
	private String username;//用户名
	private String password;//密码
	private String mobile;//手机号
	private String ishfuser;//1代表开通汇付 0 代表未开通汇付
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getIshfuser() {
		return StringUtils.isEmpty(ishfuser)?"":ishfuser;
	}
	public void setIshfuser(String ishfuser) {
		this.ishfuser = ishfuser;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
	
	
}
