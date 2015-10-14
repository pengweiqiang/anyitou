package cn.com.anyitou.api;

import java.util.Map;

import android.content.Context;
import cn.com.anyitou.api.constant.MethodType;
import cn.com.anyitou.api.constant.ReqUrls;
import cn.com.anyitou.http.HttpClientAddHeaders;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;

/**
 * 业务订单相关api
 * @author will
 *
 */
public class ApiOrderUtils {
	
	/**
	 * 查询注册汇付结果
	 * @param context
	 * @param requestCallBack
	 */
	public static void getRegisterPayResult(Context context,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_ISHFUSER, false,
				requestCallBack, MethodType.LOGIN, context);
	}
	
	/**
	 * 充值
	 * @param context
	 * @param requestCallBack
	 */
	public static void reCharge(Context context,String money,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put(ReqUrls.MONEY, money);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_RECHARGE, false,
				requestCallBack, MethodType.LOGIN, context);
	}
	/**
	 * 查询充值结果
	 * @param context
	 * @param ordId
	 * @param requestCallBack
	 */
	public static void getReChargeResult(Context context,String ordId,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put(ReqUrls.ORDER_ID, ordId);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_ISRECHARGE_SUCCESS, false,
				requestCallBack, MethodType.LOGIN, context);
	}
	/**
	 * 投资页面
	 * @param context
	 * @param id
	 * @param requestCallBack
	 */
	public static void investingPage(Context context,String id,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put(ReqUrls.ID, id);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_INVESTING_PAGE, false,
				requestCallBack, MethodType.LOGIN, context);
	}
	/**
	 * 投资
	 * @param context
	 * @param id
	 * @param money
	 * @param requestCallBack
	 */
	public static void investing(Context context,String id,String money,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put(ReqUrls.ID, id);
		params.put(ReqUrls.MONEY, money);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_INVESTING, false,
				requestCallBack, MethodType.LOGIN, context);
	}
	/**
	 * 查询投资结果
	 * @param context
	 * @param ordId
	 * @param requestCallBack
	 */
	public static void getInvestingResult(Context context,String ordId,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put(ReqUrls.ORDER_ID, ordId);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_ISINVESTING_SUCCESS, false,
				requestCallBack, MethodType.LOGIN, context);
	}
	/**
	 * 发送短信验证码（提现）
	 * @param context
	 * @param requestCallBack
	 */
	public static void cashCode(Context context,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_SMS_CODE_USER, false,
				requestCallBack, MethodType.LOGIN, context);
	}
	/**
	 * 提现页面
	 * @param context
	 * @param requestCallBack
	 */
	public static void cashPage(Context context,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_CASHPAGE, false,
				requestCallBack, MethodType.LOGIN, context);
	}
	/**
	 * 提现手续费查询
	 * @param context
	 * @param money
	 * @param session_id
	 * @param msgCode
	 * @param requestCallBack
	 */
	public static void checkMoney(Context context,String money,String session_id,String msgCode,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put(ReqUrls.MONEY, money);
		params.put(ReqUrls.SESSION_ID, session_id);
		params.put(ReqUrls.MESSAGE_CODE, msgCode);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_CHECK_MONEY, false,
				requestCallBack, MethodType.CHECK_MONEY, context);
	}
	/**
	 * 提现
	 * @param context
	 * @param money
	 * @param sessionId
	 * @param msgCode
	 * @param requestCallBack
	 */
	public static void cash(Context context,String money,String sessionId,String msgCode,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put(ReqUrls.MONEY, money);
		params.put(ReqUrls.SESSION_ID, sessionId);
		params.put(ReqUrls.MESSAGE_CODE, msgCode);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_CASH, false,
				requestCallBack, MethodType.LOGIN, context);
	}
	
	
}
