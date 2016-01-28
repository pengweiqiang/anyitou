package cn.com.anyitou.api.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 推送到具体页面枚举
 */
public enum Push2Page {

	MainActivity("MainActivity"),//主页
	MyActivity("MyActivity"),//个人中心
	
	MessagesActivity("MessagesActivity"),//消息列表
	
	InvestmentDetailActivity("InvestmentDetailActivity"),//项目详情
	
	DebtDetailActivity("DebtDetailActivity"),   //债权详情
	
	ProjectActivity("ProjectActivity"),   //项目列表
	
	MyCouponActivity("MyCouponActivity"),//优惠券列表
	
	RegisterActivity("RegisterActivity");//注册页面
	
	
	
	private String toPage;//MainActivity,MyActivity,MessagesActivity,InvestmentDetailActivity,DebtDetailActivity,MyCouponActivity,RegisterActivity
	
	
	
	private static Map<String, Push2Page> map;
	
	static {
        map = new HashMap<String, Push2Page>();
        for (Push2Page ust : Push2Page.values()) {
            map.put(ust.getToPage(), ust);
        }
    }
	private Push2Page(String toPage) {
        this.toPage = toPage;
    }
	public String getToPage() {
		return toPage;
	}
	public static Map<String, Push2Page> getMap() {
        return map;
    }
}
