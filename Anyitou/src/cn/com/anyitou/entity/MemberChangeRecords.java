package cn.com.anyitou.entity;

import java.io.Serializable;

/**
 * 会员等级变更
 * @author pengweiqiang
 *
 */
public class MemberChangeRecords implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1753970439920237100L;
	
	private String id;
	private String user_id;//用户id
	private String grow_val;//成长值
	private String operation;//1为升级2为降级
	private String status;//状态 1成功 0失败
	private String change_grade;
	private String change_grade_level;
	private String update_time;
	private String operation_unit;
	private String grade_name;//等级名称
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
	public String getGrow_val() {
		return grow_val;
	}
	public void setGrow_val(String grow_val) {
		this.grow_val = grow_val;
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
	public String getChange_grade() {
		return change_grade;
	}
	public void setChange_grade(String change_grade) {
		this.change_grade = change_grade;
	}
	public String getChange_grade_level() {
		return change_grade_level;
	}
	public void setChange_grade_level(String change_grade_level) {
		this.change_grade_level = change_grade_level;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getOperation_unit() {
		return operation_unit;
	}
	public void setOperation_unit(String operation_unit) {
		this.operation_unit = operation_unit;
	}
	public String getGrade_name() {
		return grade_name;
	}
	public void setGrade_name(String grade_name) {
		this.grade_name = grade_name;
	}
	

}
