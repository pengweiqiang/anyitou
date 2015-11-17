package cn.com.anyitou.api.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 操作枚举
 */
public enum OperationType {

	APP_CHECK_CURRENT_MOBILE("app_check_current_mobile"), //（验证当前手机），

	APP_CHANGE_MOBILE("app_change_mobile"),//（修改手机号），

	APP_REGISTER("app_register"),//（注册），

	APP_FIND_PASSWORD("app_find_password"),//（找回密码），

	APP_CHANGE_PASSWORD("app_change_password"),//（修改密码）
	
	CATEGORY_INVEST("invest"),//企贷
	
	CATEGORY_FANGDAI("fangdai"),//房贷
	
	CATEGORY_CHEDAI("chedai");//车贷
	
	private String name;
	
	private static Map<String, OperationType> map;
	
	static {
        map = new HashMap<String, OperationType>();
        for (OperationType ust : OperationType.values()) {
            map.put(ust.getName(), ust);
        }
    }
	private OperationType(String name) {
        this.name = name;
    }
	public String getName(){
		return name;
	}
	public static Map<String, OperationType> getMap() {
        return map;
    }
}
