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
	 * 投资
	 * @param context
	 * @param id
	 * @param money
	 * @param requestCallBack
	 */
	public static void investing(Context context,String id,String money,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put(ReqUrls.ID, id);
		params.put(ReqUrls.AMOUNT, money);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_INVESTING, false,
				requestCallBack, MethodType.LOGIN, context);
	}
	
	/**
	 * 提现  
	 * @param context
	 * @param money  提现金额
	 * @param bankCardId  银行卡ID
	 * @param useCouponStatus 使用优惠券状态
	 * @param requestCallBack
	 */
	public static void cash(Context context,String money,String bankCardId,String useCouponStatus,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put(ReqUrls.AMOUNT, money);
		params.put(ReqUrls.BANK_CARD_ID, bankCardId);
		params.put(ReqUrls.USE_COUPON_STATUS, useCouponStatus);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_CASH, false,
				requestCallBack, MethodType.LOGIN, context);
	}
	/**
	 * 绑定银行卡
	 * @param context
	 * @param requestCallBack
	 */
	public static void bindBank(Context context,RequestCallback requestCallBack){
		Map<String,Object> params = HttpClientAddHeaders.getHeaders(context);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_BINDBANK, false, requestCallBack, MethodType.LOGIN, context);
	}
	/**
	 * 获取支持快捷支付的银行
	 * @param context
	 * @param requestCallBack
	 */
	public static void getBank(Context context,RequestCallback requestCallBack){
		Map<String,Object> params = HttpClientAddHeaders.getHeaders(context);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_BANK, false, requestCallBack, MethodType.LOGIN, context);
	}
	/**
	 * 提现获取银行卡信息
	 * @param context
	 * @param requestCallBack
	 */
	public static void getBankInfo(Context context,RequestCallback requestCallBack){
		Map<String,Object> params = HttpClientAddHeaders.getHeaders(context);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_BANK_INFO, false, requestCallBack, MethodType.LOGIN, context);
	}
	
	/**
	 * 充值 
	 * @param context
	 * @param money 充值金额
	 * @param bankCardId 银行卡ID
	 * @param requestCallBack
	 */
	public static void reCharge(Context context,String money,String bankCardId,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put(ReqUrls.AMOUNT, money);
		params.put(ReqUrls.BANK_CARD_ID, bankCardId);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_RECHARGE, false,
				requestCallBack, MethodType.LOGIN, context);
	}
	
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
	 * 提现
	 * @param context
	 * @param amount
	 * @param bankCardId
	 * @param useCouponStatus
	 * @param requestCallBack
	 */
	public static void cashPage(Context context,String amount,String bankCardId,
			String useCouponStatus,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put(ReqUrls.AMOUNT, amount);
		params.put(ReqUrls.BANK_CARD_ID, bankCardId);
		params.put(ReqUrls.USE_COUPON_STATUS, useCouponStatus);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_CASH, false,
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
		params.put(ReqUrls.AMOUNT, money);
		params.put(ReqUrls.SMSCODE_SESSION_ID, session_id);
		params.put(ReqUrls.MESSAGE_CODE, msgCode);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_CHECK_MONEY, false,
				requestCallBack, MethodType.CHECK_MONEY, context);
	}
	
	
	
}
