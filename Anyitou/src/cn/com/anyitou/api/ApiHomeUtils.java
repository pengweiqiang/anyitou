package cn.com.anyitou.api;

import java.util.Map;

import android.content.Context;

import cn.com.anyitou.api.constant.MethodType;
import cn.com.anyitou.api.constant.ReqUrls;
import cn.com.anyitou.http.HttpClientAddHeaders;
import cn.com.anyitou.utils.HttpConnectionUtil.HttpMethod;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;

/**
 * API首页相关接口
 * 
 * @author will
 * 
 */
public class ApiHomeUtils {

	/**
	 * 获取引导页图片
	 * @param context
	 * @param requestCallBack
	 */
	public static void getBoot(Context context, RequestCallback requestCallBack) {
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_BOOT, false,
				requestCallBack, MethodType.LOGIN, context);
	}
	/**
	 * 分享文案
	 * @param context
	 * @param requestCallBack
	 */
	public static void getShare(Context context,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_SHARE, false,
				requestCallBack, MethodType.LOGIN, context);
	}
	/**
	 * 关于我们
	 * @param context
	 * @param requestCallBack
	 */
	public static void getAboutUs(Context context,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_ABOUTUS, false,
				requestCallBack, MethodType.LOGIN, context);
	}
}
