package cn.com.anyitou.api;

import java.util.Map;

import android.content.Context;

import cn.com.anyitou.api.constant.MethodType;
import cn.com.anyitou.api.constant.ReqUrls;
import cn.com.anyitou.http.HttpClientAddHeaders;
import cn.com.anyitou.utils.HttpConnectionUtil.HttpMethod;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;

/**
 * API消息相关接口
 * 
 * @author pengweiqiang
 * 
 */
public class ApiMessageUtils {
	
	/**
	 * 绑定用户设备号等信息(/push/device)
	 * @param context
	 * @param registration_id
	 * @param alias
	 * @param requestCallBack
	 */
	public static void pushBindingDevice(Context context,String registration_id,String alias,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put("registration_id", registration_id);
		params.put("alias", alias);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_PUSH_DEVICE, false,
				requestCallBack, MethodType.LOGIN,context,HttpMethod.POST,false);
	}

	/**
	 * 消息列表
	 * @param context
	 * @param status  0为未读，1为已读
	 * @param page  
	 * @param requestCallBack
	 */
	public static void getMessageList(Context context,String status,String page,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put("status", status);
		params.put(ReqUrls.PAGE, page);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_MESSAGE_LIST, false,
				requestCallBack, MethodType.LOGIN,context,HttpMethod.GET,false);
	}
	/**
	 * 消息详情
	 * @param context
	 * @param id
	 * @param requestCallBack
	 */
	public static void getMessageDetail(Context context,String id,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put(ReqUrls.ID, id);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_MESSAGE_VIEW, false,
				requestCallBack, MethodType.LOGIN,context,HttpMethod.GET,false);
	}
	/**
	 * 阅读消息
	 * @param context
	 * @param id
	 * @param requestCallBack
	 */
	public static void messageUpdate(Context context,String id,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put(ReqUrls.ID, id);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_MESSAGE_UPDATE, false,
				requestCallBack, MethodType.LOGIN,context,false);
	}
	
}
