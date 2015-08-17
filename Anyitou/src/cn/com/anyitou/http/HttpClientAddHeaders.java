package cn.com.anyitou.http;

import java.util.concurrent.ConcurrentHashMap;

import android.content.Context;

import cn.com.GlobalConfig;
import cn.com.anyitou.api.constant.ReqUrls;
import cn.com.anyitou.commons.Constant;
import cn.com.anyitou.utils.SharePreferenceManager;
import cn.com.anyitou.utils.StringUtils;

/**
 * 添加请求头
 */
public class HttpClientAddHeaders {
	/**
	 * 获取请求头集合
	 * isAuthentication 是否需要认证，注册，密码重置 不需要
	 * @return
	 */
	public static ConcurrentHashMap<String, Object> getHeaders(Context context){
		MyConcurrentHashMap<String, Object> headers = new MyConcurrentHashMap<String,Object>();
		/*headers.put("source","Android");//客户端系统

		if("".equals(GlobalConfig.VERSION_NAME_V)){
			try {
				String pName = context.getPackageName();
				PackageInfo pinfo = context.getPackageManager().getPackageInfo(
						pName, PackageManager.GET_CONFIGURATIONS);
                GlobalConfig.VERSION_NAME_V = String.valueOf(pinfo.versionName);
                GlobalConfig.VERSION_CODE_V = String.valueOf(pinfo.versionCode);
                GlobalConfig.APP_NAME = String.valueOf(context.getPackageManager().getApplicationLabel(context.getPackageManager().getApplicationInfo(pName, 0)));

			} catch (NameNotFoundException e) {
			}
		}
		headers.put("version",GlobalConfig.VERSION_NAME_V);//客户端版本
		headers.put("appname",GlobalConfig.APP_NAME);//客户端名称
*/		
		if("".equals(GlobalConfig.USER_NAME)){
			GlobalConfig.TOKEN = (String)SharePreferenceManager.getSharePreferenceValue(context, Constant.FILE_NAME, ReqUrls.TOKEN, "");
		}
		
		if(!StringUtils.isEmpty(GlobalConfig.TOKEN)){
			headers.put(ReqUrls.TOKEN,GlobalConfig.TOKEN);//token
		}
//        headers.put("userId",GlobalConfig.USER_ID);
//        headers.put("enterId",GlobalConfig.ENTER_ID);
//		if("".equals(GlobalConfig.EQUIPMENT_V)){
//            GlobalConfig.EQUIPMENT_V = Build.MODEL;
//		}
//		headers.put("equipment",GlobalConfig.EQUIPMENT_V);

		return headers;
	}
}