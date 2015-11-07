package cn.com.anyitou.entity;

import java.io.Serializable;

public class IntegralGoods implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9144388183668526953L;
	/**
	 * 
	 */
	
	private String id;
	private String coupon_id;
	private String type;
	private String title;
	private String worth;
	private String reference_price;
	private String goods_stock;
	private String pic;
	private String goods_desc;
	private String sort;
	private String status;
	private String add_time;
	private String update_time;
	private CouponData coupon_data;
	
	public class CouponData{
		private String id;
		private String name;
		private String pic_link;
		private String small_pic_link;
		private String category;
		private String price;
		private String begin_time;
		private String delay;
		private String end_time;
		private String num;
		private String give_num;
		private String descript;
		private String status;
		private String part_use;
		private String addup_use;
		private String use_rules;
		private String is_convert;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPic_link() {
			return pic_link;
		}
		public void setPic_link(String pic_link) {
			this.pic_link = pic_link;
		}
		public String getSmall_pic_link() {
			return small_pic_link;
		}
		public void setSmall_pic_link(String small_pic_link) {
			this.small_pic_link = small_pic_link;
		}
		public String getCategory() {
			return category;
		}
		public void setCategory(String category) {
			this.category = category;
		}
		public String getPrice() {
			return price;
		}
		public void setPrice(String price) {
			this.price = price;
		}
		public String getBegin_time() {
			return begin_time;
		}
		public void setBegin_time(String begin_time) {
			this.begin_time = begin_time;
		}
		public String getDelay() {
			return delay;
		}
		public void setDelay(String delay) {
			this.delay = delay;
		}
		public String getEnd_time() {
			return end_time;
		}
		public void setEnd_time(String end_time) {
			this.end_time = end_time;
		}
		public String getNum() {
			return num;
		}
		public void setNum(String num) {
			this.num = num;
		}
		public String getGive_num() {
			return give_num;
		}
		public void setGive_num(String give_num) {
			this.give_num = give_num;
		}
		public String getDescript() {
			return descript;
		}
		public void setDescript(String descript) {
			this.descript = descript;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getPart_use() {
			return part_use;
		}
		public void setPart_use(String part_use) {
			this.part_use = part_use;
		}
		public String getAddup_use() {
			return addup_use;
		}
		public void setAddup_use(String addup_use) {
			this.addup_use = addup_use;
		}
		public String getUse_rules() {
			return use_rules;
		}
		public void setUse_rules(String use_rules) {
			this.use_rules = use_rules;
		}
		public String getIs_convert() {
			return is_convert;
		}
		public void setIs_convert(String is_convert) {
			this.is_convert = is_convert;
		}
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCoupon_id() {
		return coupon_id;
	}

	public void setCoupon_id(String coupon_id) {
		this.coupon_id = coupon_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWorth() {
		return worth;
	}

	public void setWorth(String worth) {
		this.worth = worth;
	}

	public String getReference_price() {
		return reference_price;
	}

	public void setReference_price(String reference_price) {
		this.reference_price = reference_price;
	}

	public String getGoods_stock() {
		return goods_stock;
	}

	public void setGoods_stock(String goods_stock) {
		this.goods_stock = goods_stock;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getGoods_desc() {
		return goods_desc;
	}

	public void setGoods_desc(String goods_desc) {
		this.goods_desc = goods_desc;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
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

	public CouponData getCoupon_data() {
		return coupon_data;
	}

	public void setCoupon_data(CouponData coupon_data) {
		this.coupon_data = coupon_data;
	}
	
	
	
}
