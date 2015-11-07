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
	public static void getRecommend(Context context,int page,String num,RequestCallback requestCallBack){
		ConcurrentHashMap<String, Object> params = HttpClientAddHeaders.getHeaders(context,false);
		params.put(ReqUrls.PAGE, page==0?1:page);
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
	public static  void contentShow(Context context,String id,String type,RequestCallback requestCallBack){
		ConcurrentHashMap<String, Object> params = HttpClientAddHeaders.getHeaders(context,false);
		params.put(ReqUrls.TYPE, type);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_PROJECT_DETAIL+id, false,
				requestCallBack, MethodType.LOGIN, context,HttpMethod.GET,false);
	}
	/**
	 * 获取债权列表
	 * @param context
	 * @param page
	 * @param num
	 * @param order 排序
	 * @param amount 转让金额
	 * @param apr 认购收益
	 * @param repayment 剩余期限
	 * @param requestCallBack
	 */
	public static void getDebtAssignment(Context context,int page,String num,
			String order,String amount,String apr,String repayment,RequestCallback requestCallBack){
		ConcurrentHashMap<String, Object> params = HttpClientAddHeaders.getHeaders(context,false);
		params.put(ReqUrls.PAGE, page==0?1:page);
		params.put(ReqUrls.NUM, num);
		params.put(ReqUrls.ORDER, order);
		params.put(ReqUrls.AMOUNT, amount);
		params.put("apr", apr);
		params.put("repayment", repayment);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_DEBT_ASSIGNMENT, false,
				requestCallBack, MethodType.LOGIN,context,HttpMethod.GET,false);
	}
	/**
	 *  获取兑换物品列表
	 * @param context
	 * @param page
	 * @param num
	 * @param requestCallBack
	 */
	public static  void getIntegalGoods(Context context,String page ,String num,RequestCallback requestCallBack){
		ConcurrentHashMap<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put(ReqUrls.PAGE, page);
		params.put("page_num", num);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_INTEGRAL_GOODS, false,
				requestCallBack, MethodType.LOGIN, context,HttpMethod.GET,false);
	}
	/**
	 * 兑换物品
	 * @param context
	 * @param goodsId 兑换物品ID
	 * @param requestCallBack
	 */
	public static  void exchangeGoods(Context context,String goodsId,RequestCallback requestCallBack){
		ConcurrentHashMap<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put("goods_id", goodsId);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_EXCHANGE_GOODS, false,
				requestCallBack, MethodType.LOGIN,context);
	}
	
	
	/**
	 * 获取债权详情
	 * @param context
	 * @param id
	 * @param requestCallBack
	 */
	public static  void getDebtAssignmentDetail(Context context,String id,String type,RequestCallback requestCallBack){
		ConcurrentHashMap<String, Object> params = HttpClientAddHeaders.getHeaders(context,false);
		params.put(ReqUrls.ID, id);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_DEBT_ASSIGNMENT_DETAIL, false,
				requestCallBack, MethodType.LOGIN, context,HttpMethod.GET,false);
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
