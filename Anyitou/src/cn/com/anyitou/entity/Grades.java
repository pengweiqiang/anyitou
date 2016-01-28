package cn.com.anyitou.entity;

import java.io.Serializable;

/**
 * 会员特权
 * @author pengweiqiang
 *
 */
public class Grades implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1753970439920237100L;
	
	private String info;//等级权益信息
	private String user_level;//当前等级值
	private String grade_level;//当前等级细分等级（当前只有黄金会员user_level为3的时候才有细分等级）
	private String name;//当前等级名称
	private String grow_val;//当前成长值
	private String needGrowVal;//距升级所需成长值
	private String nextgradename;//下一等级名称
	
	
	
	public String getUser_level() {
		return user_level;
	}
	public void setUser_level(String user_level) {
		this.user_level = user_level;
	}
	public String getGrade_level() {
		return grade_level;
	}
	public void setGrade_level(String grade_level) {
		this.grade_level = grade_level;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGrow_val() {
		return grow_val;
	}
	public void setGrow_val(String grow_val) {
		this.grow_val = grow_val;
	}
	public String getNeedGrowVal() {
		return needGrowVal;
	}
	public void setNeedGrowVal(String needGrowVal) {
		this.needGrowVal = needGrowVal;
	}
	public String getNextgradename() {
		return nextgradename;
	}
	public void setNextgradename(String nextgradename) {
		this.nextgradename = nextgradename;
	}
	

	
	

}
