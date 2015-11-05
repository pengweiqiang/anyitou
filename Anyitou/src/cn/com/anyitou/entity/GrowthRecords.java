package cn.com.anyitou.entity;

import java.io.Serializable;

/**
 * 用户成长值
 * @author will
 *
 */
public class GrowthRecords implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6539933435377478147L;
	private String id;//记录id
	private String user_id;//用户id
	private String rule_id;
	private String after_grow_val;//变更前成长值
	private String change_val;//当前变更成长值
	private String  operation;//1为增加，2为减去
	private String status;//状态 1成功 0失败
	private String add_time;
	private String operation_unit;
	private String rule_name;
	
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
	public String getRule_id() {
		return rule_id;
	}
	public void setRule_id(String rule_id) {
		this.rule_id = rule_id;
	}
	public String getAfter_grow_val() {
		return after_grow_val;
	}
	public void setAfter_grow_val(String after_grow_val) {
		this.after_grow_val = after_grow_val;
	}
	public String getChange_val() {
		return change_val;
	}
	public void setChange_val(String change_val) {
		this.change_val = change_val;
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
	public String getOperation_unit() {
		return operation_unit;
	}
	public void setOperation_unit(String operation_unit) {
		this.operation_unit = operation_unit;
	}
	public String getRule_name() {
		return rule_name;
	}
	public void setRule_name(String rule_name) {
		this.rule_name = rule_name;
	}
	

	
}
