package cn.com.anyitou.api;

import java.util.concurrent.ConcurrentHashMap;

import android.content.Context;

import cn.com.anyitou.api.constant.MethodType;
import cn.com.anyitou.api.constant.ReqUrls;
import cn.com.anyitou.http.HttpClientAddHeaders;
import cn.com.anyitou.utils.HttpConnectionUtil.HttpMethod;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;

/**
 * 投资相关api
 * @author will
 *
 */
public class ApiInvestUtils {

	
	
	/**
	 * 投资列表
	 * @param context
	 * @param requestCallBack
	 */
	public static void getInvestList(Context context,RequestCallback requestCallBack){
		ConcurrentHashMap<String, Object> params = HttpClientAddHeaders.getHeaders(context,false);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_INDEX, false,
				requestCallBack, MethodType.LOGIN, context);
		
	}
	/**
	 * 获取推荐项目
	 * @param context
	 * @param page 页码
	 * @param num  条数
	 * @param requestCallBack
	 */
	public static void getRecommend(Context context,String page,String num,RequestCallback requestCallBack){
		ConcurrentHashMap<String, Object> params = HttpClientAddHeaders.getHeaders(context,false);
		params.put(ReqUrls.PAGE, page);
		params.put(ReqUrls.NUM, num);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_RECOMMEND, false,
				requestCallBack, MethodType.LOGIN, context);
		
	}
	/**
	 * 项目详情
	 * @param context
	 * @param id
	 * @param requestCallBack
	 */
	public static  void contentShow(Context context,String id,RequestCallback requestCallBack){
		ConcurrentHashMap<String, Object> params = HttpClientAddHeaders.getHeaders(context,false);
		params.put(ReqUrls.ID, id);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_CONTENT_SHOW, false,
				requestCallBack, MethodType.LOGIN, context,HttpMethod.GET);
	}
	/**
	 * 还款计划
	 * @param context
	 * @param page
	 * @param requestCallBack
	 */
	public static void receiving(Context context,String page,RequestCallback requestCallBack){
		ConcurrentHashMap<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put(ReqUrls.PAGE, page);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_RECEIVING, false,
				requestCallBack, MethodType.LOGIN, context);
	}
}
