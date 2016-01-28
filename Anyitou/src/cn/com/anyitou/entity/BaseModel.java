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
	
	private String url;
	
	private String time;
	

	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
}
