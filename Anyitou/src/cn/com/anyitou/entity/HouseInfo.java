package cn.com.anyitou.entity;

import java.io.Serializable;

public class HouseInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3028812779389854665L;

//	"id": "1",
//    "pid": "63",                  //  项目id
//    "serial_number": "asdfaf",    //  编号
//    "valuation_date": "2015-02-09",    //  评估日期
//    "owner": "23",                //  房屋所有人用户ID
//    "sizes": "23",                //  房产面积
//    "floor": "1",                 //  楼层
//    "location": "21esdfsaf",      //  房产坐落
//    "type": "fasdfsd",            //  房产性质
//    "buy_date": "2015-02-09",     //  购置时间
//    "buy_price": "10.00",         //  购置价格（发票）
//    "orientation": "asdf",        //  朝  向
//    "mortgage_info": "232",       //  抵押情况
//    "valuation": "10.00",         //  评估价值
//    "update_time": "2015-02-09 13:53:25"    //  更新时间
	
	private String id;
	private String pid;
	private String serial_number;
	private String valuation_date;
	private String owner;
	private String sizes;
	private String floor;
	private String location;
	private String type;
	private String buy_date;
	private String buy_price;
	private String orientation;
	private String mortgage_info;
	private String valuation;
	private String update_time;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getSerial_number() {
		return serial_number;
	}
	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}
	public String getValuation_date() {
		return valuation_date;
	}
	public void setValuation_date(String valuation_date) {
		this.valuation_date = valuation_date;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getSizes() {
		return sizes;
	}
	public void setSizes(String sizes) {
		this.sizes = sizes;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBuy_date() {
		return buy_date;
	}
	public void setBuy_date(String buy_date) {
		this.buy_date = buy_date;
	}
	public String getBuy_price() {
		return buy_price;
	}
	public void setBuy_price(String buy_price) {
		this.buy_price = buy_price;
	}
	public String getOrientation() {
		return orientation;
	}
	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}
	public String getMortgage_info() {
		return mortgage_info;
	}
	public void setMortgage_info(String mortgage_info) {
		this.mortgage_info = mortgage_info;
	}
	public String getValuation() {
		return valuation;
	}
	public void setValuation(String valuation) {
		this.valuation = valuation;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	
}
