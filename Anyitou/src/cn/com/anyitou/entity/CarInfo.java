package cn.com.anyitou.entity;

import java.io.Serializable;

public class CarInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3887620180667005611L;
	
//	 "id": "3",
//     "item_id": "59",    //  项目名称
//     "owner": "",        //  车辆所有人 即借款人
//     "used_time": "",    //  使用期限
//     "license_plate": "京P1564456",    //  车牌号
//     "vehicle_type": "5",              //  车辆类型
//     "use_type": "0",                  //  使用性质
//     "violation": "0",                 //  违章处理状态
//     "buy_time": "2015-02-01",         //  购置时间
//     "buy_price": "400000.00",         //  购置价格
//     "color": "白色",                  //  颜色
//     "brand_model": "BMW-X5",          //  品牌型号
//     "emissions": "2.0L",              //  排气量
//     "kilometre": "1000",              //  公里数
//     "annual_inspection_due_date": "2015-01-01",    //  车检到期日
//     "insurance_due_date": "2015-12-31",            //  保险到期日
//     "valuation": "300000.00",                      //  评估价值
//     "vehicle_type_name": "高级轿车",              //  车辆类型名称
//     "violation_name": "无"                        //  违章处理状态名称
		private String id;
		private String item_id;
		private String owner;
		private String used_time;
		private String license_plate;
		private String vehicle_type;
		private String use_type;
		private String violation;
		private String buy_time;
		private String buy_price;
		private String color;
		private String brand_model;
		private String emissions;
		private String kilometre;
		private String annual_inspection_due_date;
		private String insurance_due_date;
		private String valuation;
		private String vehicle_type_name;
		private String violation_name;
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getItem_id() {
			return item_id;
		}
		public void setItem_id(String item_id) {
			this.item_id = item_id;
		}
		public String getOwner() {
			return owner;
		}
		public void setOwner(String owner) {
			this.owner = owner;
		}
		public String getUsed_time() {
			return used_time;
		}
		public void setUsed_time(String used_time) {
			this.used_time = used_time;
		}
		public String getLicense_plate() {
			return license_plate;
		}
		public void setLicense_plate(String license_plate) {
			this.license_plate = license_plate;
		}
		public String getVehicle_type() {
			return vehicle_type;
		}
		public void setVehicle_type(String vehicle_type) {
			this.vehicle_type = vehicle_type;
		}
		public String getUse_type() {
			return use_type;
		}
		public void setUse_type(String use_type) {
			this.use_type = use_type;
		}
		public String getViolation() {
			return violation;
		}
		public void setViolation(String violation) {
			this.violation = violation;
		}
		public String getBuy_time() {
			return buy_time;
		}
		public void setBuy_time(String buy_time) {
			this.buy_time = buy_time;
		}
		public String getBuy_price() {
			return buy_price;
		}
		public void setBuy_price(String buy_price) {
			this.buy_price = buy_price;
		}
		public String getColor() {
			return color;
		}
		public void setColor(String color) {
			this.color = color;
		}
		public String getBrand_model() {
			return brand_model;
		}
		public void setBrand_model(String brand_model) {
			this.brand_model = brand_model;
		}
		public String getEmissions() {
			return emissions;
		}
		public void setEmissions(String emissions) {
			this.emissions = emissions;
		}
		public String getKilometre() {
			return kilometre;
		}
		public void setKilometre(String kilometre) {
			this.kilometre = kilometre;
		}
		public String getAnnual_inspection_due_date() {
			return annual_inspection_due_date;
		}
		public void setAnnual_inspection_due_date(String annual_inspection_due_date) {
			this.annual_inspection_due_date = annual_inspection_due_date;
		}
		public String getInsurance_due_date() {
			return insurance_due_date;
		}
		public void setInsurance_due_date(String insurance_due_date) {
			this.insurance_due_date = insurance_due_date;
		}
		public String getValuation() {
			return valuation;
		}
		public void setValuation(String valuation) {
			this.valuation = valuation;
		}
		public String getVehicle_type_name() {
			return vehicle_type_name;
		}
		public void setVehicle_type_name(String vehicle_type_name) {
			this.vehicle_type_name = vehicle_type_name;
		}
		public String getViolation_name() {
			return violation_name;
		}
		public void setViolation_name(String violation_name) {
			this.violation_name = violation_name;
		}
	
}
