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
 * @author pengweiqiang
 * 
 */
public class ApiHomeUtils {

	/**
	 * 获取APP BANNER
	 * @param context
	 * @param requestCallBack
	 */
	public static void getBanner(Context context,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context,false);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_BANNER, false,
				requestCallBack, MethodType.LOGIN, context);
	}
	/**
	 * 意见反馈
	 * @param context
	 * @param title
	 * @param content
	 * @param requestCallBack
	 */
	public static void report(Context context,String title,String content,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put(ReqUrls.TITLE, title);
		params.put(ReqUrls.CONTENT, content);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_REPORT, false,
				requestCallBack, MethodType.LOGIN, context);
	}
	/**
	 * 内容介绍相关
	 * @param context
	 * @param requestCallBack
	 */
	public static void getIntroduction(Context context,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context,false);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_INTRODUCTION, false,
				requestCallBack, MethodType.LOGIN, context);
	}
	
	/**
	 * 启动引导
	 * @param context
	 * @param requestCallBack
	 */
	public static void getGuides(Context context, RequestCallback requestCallBack) {
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context,false);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_GUIDES, false,
				requestCallBack, MethodType.LOGIN, context);
	}
	/**
	 * 邀请好友链接
	 * @param context
	 * @param requestCallBack
	 */
	public static void inviteLinks(Context context,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_INVITE_LINKS, false,
				requestCallBack, MethodType.LOGIN, context,HttpMethod.GET,false);
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
