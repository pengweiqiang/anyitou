package cn.com.anyitou.entity;

import java.io.Serializable;

/**
 * 安币变更
 * @author pengweiqiang
 *
 */
public class IntegralRecords implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6539933435377478147L;
	private String id;//记录id
	private String user_id;//用户id
	private String event_id;
	private String before_integral;
	private String set_integral;
	private String after_integral;
	private String operation;
	private String status;
	private String add_time;
	private String update_time;
	private String title;
	private String event_desc;
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
	public String getEvent_id() {
		return event_id;
	}
	public void setEvent_id(String event_id) {
		this.event_id = event_id;
	}
	public String getBefore_integral() {
		return before_integral;
	}
	public void setBefore_integral(String before_integral) {
		this.before_integral = before_integral;
	}
	public String getSet_integral() {
		return set_integral;
	}
	public void setSet_integral(String set_integral) {
		this.set_integral = set_integral;
	}
	public String getAfter_integral() {
		return after_integral;
	}
	public void setAfter_integral(String after_integral) {
		this.after_integral = after_integral;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAdd_time() {
		return add_time;
	}
	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getEvent_desc() {
		return event_desc;
	}
	public void setEvent_desc(String event_desc) {
		this.event_desc = event_desc;
	}
	
	
	

	
}
