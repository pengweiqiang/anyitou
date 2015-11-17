package cn.com.anyitou.api;

import java.util.Map;
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
	public static void getInvestList(Context context,String page,String num,RequestCallback requestCallBack){
		ConcurrentHashMap<String, Object> params = HttpClientAddHeaders.getHeaders(context,false);
		params.put(ReqUrls.PAGE, page);
		params.put(ReqUrls.NUM, num);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_INDEX, false,
				requestCallBack, MethodType.LOGIN, context,HttpMethod.GET,false);
		
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
				requestCallBack, MethodType.LOGIN, context,HttpMethod.GET,false);
		
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
	 * 获取项目投资记录列表
	 * @param context
	 * @param pid
	 * @param page
	 * @param num
	 * @param requestCallBack
	 */
	public static void getInvestTradeById(Context context,String pid,String page,String num,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put("pid", pid);
		params.put(ReqUrls.PAGE, page);
		params.put(ReqUrls.NUM, num);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_INVESTMENTS, false,
				requestCallBack, MethodType.LOGIN, context,HttpMethod.GET,false);
	}
	/**
	 * 投资计算器
	 * @param context
	 * @param id
	 * @param amount
	 * @param coupons
	 * @param requestCallBack
	 */
	public static void calculatorInvest(Context context,String id,String amount,String coupons,RequestCallback requestCallBack){
		ConcurrentHashMap<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put("pid", id);
		params.put(ReqUrls.AMOUNT, amount);
		params.put("coupons", coupons);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_INVESTMENT_CALCULATOR, false,
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
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context,false);
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
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
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
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put("goods_id", goodsId);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_EXCHANGE_GOODS, false,
				requestCallBack, MethodType.LOGIN,context);
	}
	/**
	 * 获取投资记录详情
	 * @param context
	 * @param id
	 * @param requestCallBack
	 */
	public static void getMyInvestmentDetail(Context context,String id,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put(ReqUrls.ID, id);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_INVESTMENT_VIEW, false,
				requestCallBack, MethodType.LOGIN,context,HttpMethod.GET,false);
	}
	/**
	 * 获取债权转让参数
	 * @param context
	 * @param investId
	 * @param amount
	 * @param buyapr
	 * @param price
	 * @param gt
	 * @param sellDays
	 * @param requestCallBack
	 */
	public static  void getDebtParams(Context context,String investId,String amount,String buyapr,String price,String gt,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put("invest_id", investId);
		params.put("amount", amount);
		params.put("buyapr", buyapr);
		params.put("price", price);
		params.put("gt", gt);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_DEBT_GETPARAMS, false,
				requestCallBack, MethodType.LOGIN,context,HttpMethod.GET,false);
	}
	
	/**
	 * 发起债权转让
	 * @param context
	 * @param investId 投资ID
	 * @param amount 转让金额
	 * @param buyapr 认购方年化收益率（gt=buyapr 时为必须参数）
	 * @param price 转让价格 （gt=price时为必须参数）
	 * @param gt 获取方式buyapr: 根据认购方年化收益率获取   price:    根据转让价格获取
	 * @param sellDays 发布天数
	 * @param requestCallBack
	 */
	public static  void debtCreate(Context context,String investId,String amount,String buyapr,String price,String gt,String sellDays,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put("invest_id", investId);
		params.put("amount", amount);
		params.put("buyapr", buyapr);
		params.put("price", price);
		params.put("gt", gt);
		params.put("sell_days", sellDays);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_DEBT_CREATE, false,
				requestCallBack, MethodType.LOGIN,context);
	}
	
	
	
	/**
	 * 获取债权详情
	 * @param context
	 * @param id
	 * @param requestCallBack
	 */
	public static  void getDebtAssignmentDetail(Context context,String id,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context,false);
		params.put(ReqUrls.ID, id);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_DEBT_ASSIGNMENT_DETAIL, false,
				requestCallBack, MethodType.LOGIN, context,HttpMethod.GET,false);
	}
	/**
	 * 获取投资可用优惠券
	 * @param context
	 * @param id  项目ID
	 * @param amount 转让金额
	 * @param requestCallBack
	 */
	public static  void getCouponForProject(Context context,String id,String amount,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put("pid", id);
		params.put(ReqUrls.AMOUNT, amount);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_PROJECT_COUPON, false,
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
