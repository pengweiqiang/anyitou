package cn.com.anyitou.entity;

import java.io.Serializable;

import cn.com.gson.JsonElement;

public class BaseModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8998410069756234518L;
	
	
	private String code;
	
	private String status;
	
	private JsonElement data;
	
	private String msg;
	

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public JsonElement getData() {
		return data;
	}

	public void setData(JsonElement data) {
		this.data = data;
	}
}
