package cn.com.anyitou.api;

import java.util.Map;

import android.content.Context;
import cn.com.anyitou.api.constant.MethodType;
import cn.com.anyitou.api.constant.ReqUrls;
import cn.com.anyitou.http.HttpClientAddHeaders;
import cn.com.anyitou.http.MyConcurrentHashMap;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.StringUtils;

/**
 * api user用户相关的接口
 * 
 * @author will
 */
public class ApiUserUtils {
	
	/**
	 * 请求授权
	 * 
	 * a1.客户端请求授权详细说明：接口使用HTTP Basic Authentication认证方式，添加Authorization 到 header， 
	 * 加密方式 Authorization = Basic Base64.encode(client_key:client_secret)；
	 * POST请求参数只需grant_type，值为client_credentials
	 * 
	 * a2.用户请求授权详细说明：接口使用HTTP Basic Authentication认证方式，添加Authorization 到 header， 
	 * 加密方式 Authorization = Basic Base64.encode(client_key:client_secret)；
	 * POST请求参数需要grant_type，值为password，以及用户的username和password
	 * @param context
	 * @param requestCallBack
	 */
	public static void oauthAccessToken(Context context,String grant_type,String username,String password,String refreshToken,RequestCallback requestCallBack,boolean isTimer){
		Map<String,Object> params = new MyConcurrentHashMap<String,Object>();
		
		params.put(ReqUrls.GRANT_TYPE, grant_type);
		params.put(ReqUrls.USERNAME, username);
		params.put(ReqUrls.PASSWORD, password);
		params.put(ReqUrls.REFRESH_TOKEN, refreshToken);
		params.put("isUserToken", false);
		
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_AUTH, false,
				requestCallBack, MethodType.LOGIN, context,isTimer);
	}

	/**
	 * 获取用户资金信息
	 * @param context
	 * @param requestCallBack
	 */
	public static void getMyAccount(Context context,RequestCallback requestCallBack){
		Map<String,Object> params = HttpClientAddHeaders.getHeaders(context);
		
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_MY_ACCOUNT, false,
				requestCallBack, MethodType.LOGIN, context);
		
	}
	/**
	 * 获取用户安币信息
	 * @param context
	 * @param requestCallBack
	 */
	public static void getMyAnbiInfo(Context context,RequestCallback requestCallBack){
		Map<String,Object> params = HttpClientAddHeaders.getHeaders(context);
		
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_USER_INTEGRAL, false,
				requestCallBack, MethodType.LOGIN, context);
		
	}
	
	/**
	 * 验证短信验证码
	 * 
	 * @param context
	 * @param captchaKey 验证码存储于服务器键名
	 * @param captcha 验证码
	 * @param requestCallBack
	 */
	public static void registerCode(Context context, String captchaKey,String captcha,
			RequestCallback requestCallBack) {
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context,false);
		params.put(ReqUrls.CAPTCHA_KEY, captchaKey);
		params.put(ReqUrls.CAPTCHA, captcha);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_REGISTERCODE, false,
				requestCallBack, MethodType.LOGIN, context);
	}
	/**
	 * 修改手机号
	 * @param context
	 * @param mobile 手机号码
	 * @param captcha_key 存储于服务器验证码键名
	 * @param captcha 手机验证码
	 * @param requestBack
	 */
	public static void modifyMobile(Context context,String mobile,String captcha_key,String captcha,RequestCallback requestCallBack){
		Map<String,Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put(ReqUrls.MOBILE, mobile);
		params.put(ReqUrls.CAPTCHA_KEY, captcha_key);
		params.put(ReqUrls.CAPTCHA, captcha);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_MODIFY_MOBILE, false, requestCallBack, MethodType.LOGIN, context);
		
	}
	/**
	 * 会员特权信息
	 * @param context
	 * @param requestCallBack
	 */
	public static void getGrades(Context context,RequestCallback requestCallBack){
		Map<String,Object> params = HttpClientAddHeaders.getHeaders(context);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_GRADES, false, requestCallBack, MethodType.LOGIN, context);
		
	}

	/**
	 * 注册
	 * @param context  
	 * @param mobile 手机号
	 * @param userName 用户名
	 * @param passWord 密码
	 * @param smscode 短信验证码
	 * @param smscodeSessionId 短信验证码存储于服务器的键名
	 * @param recid 邀请人ID
	 * @param from 渠道标识
	 * @param requestCallBack
	 */
	public static void register(Context context,  String mobile,String userName, String passWord,
			String smscode ,String smscodeSessionId,String recid,
			String from, RequestCallback requestCallBack) {
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context,false);
		params.put(ReqUrls.MOBILE, mobile);
		params.put(ReqUrls.SMSCODE_SESSION_ID, smscodeSessionId);
		params.put(ReqUrls.SMS_CODE, smscode);
		params.put(ReqUrls.USERNAME, userName);
		params.put(ReqUrls.PASSWORD, passWord);
		params.put(ReqUrls.RECID, recid);
		params.put(ReqUrls.FROM, from);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_REGISTER, false,
				requestCallBack, MethodType.LOGIN, context);
	}
	/**
	 * 发送短信验证码（不需要用户进行登录）
	 * @param context
	 * @param mobile 手机号码
	 * @param type
	 *  app_check_current_mobile（验证当前手机），
		app_change_mobile（修改手机号），
		app_register（注册），	
		app_find_password（找回密码），
		app_change_password（修改密码）
	 * @param requestCallBack
	 */
	public static void sendMobileCode(Context context,String mobile,String type,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context,false);
		params.put(ReqUrls.MOBILE, mobile);
		params.put(ReqUrls.TYPE, type);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_SMS_CODE, false,
				requestCallBack, MethodType.LOGIN, context);
	}
	
	/**
	 * 发送短信验证码（需要用户进行登录）
	 * @param context
	 * @param type
	 *  app_check_current_mobile（验证当前手机），
		app_change_mobile（修改手机号），
		app_register（注册），	
		app_find_password（找回密码），
		app_change_password（修改密码）
	 * @param requestCallBack
	 */
	public static void sendUserCode(Context context,String type,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put(ReqUrls.TYPE, type);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_SMS_CODE_USER, false,
				requestCallBack, MethodType.LOGIN, context);
	}
	
	/**
	 * 获取用户银行卡信息
	 * @param context
	 * @param requestCallBack
	 */
	public static void getMyCard(Context context,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_RECHARGE_INFO, false,
				requestCallBack, MethodType.LOGIN, context);
	}

	/**
	 * 登录
	 * 
	 * @param context
	 * @param userName
	 *            用户名
	 * @param passWord
	 *            密码
	 * @param requestCallBack
	 */
	public static void login(Context context, String userName, String passWord,
			RequestCallback requestCallBack) {
		
		oauthAccessToken(context, "password", userName, passWord, "", requestCallBack,false);
	}
	
	/**
	 * 修改密码
	 * @param context
	 * @param password
	 * @param newpassword
	 * @param captchaKey 验证码存储于服务器键名
	 * @param captcha 验证码
	 * @param requestCallback
	 */
	public static void updatePwd(Context context,String password, String newpassword, 
			 String captchaKey,String captcha,RequestCallback requestCallback) {
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put(ReqUrls.PASSWORD, password);
		params.put(ReqUrls.NEW_PASSWORD, newpassword);
		params.put(ReqUrls.REPASSWORD, newpassword);
		params.put(ReqUrls.CAPTCHA_KEY, captchaKey);
		params.put(ReqUrls.CAPTCHA, captcha);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_CHANGE_PASSWORD, false,
				requestCallback, MethodType.UPDATE, context);
	}
	
	/**
	 * 登出
	 * @param context
	 * @param requestCallBack
	 */
	public static void logout(Context context,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_LOGOUT, false,
				requestCallBack, MethodType.LOGIN, context);
	}
	
	/**
	 * 注册汇付
	 * @param context
	 * @param requestCallBack
	 */
	public static void registerPay(Context context,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_HF_REGISTER, false,
				requestCallBack, MethodType.LOGIN, context);
	}
	/**
	 * 我的投资 
	 * @param context
	 * @param category  交易分类
	 * all: 全部 qidai：企贷 reward：新手体验 chedai: 车贷 fangdai: 房贷 debt: 债权
	 * @param status 状态
	 * 1:还款中 2: 已还款 3: 部分转让 4: 全部转让
	 * @param order 排序
	 * all: 全部 repaytime_up: 还款日期升序 repaytime_down: amount_up: 投资金额升序 amount_down: investtime_up: 投资时间升序 investtime_down:
	 * @param investDateRange 投资时间范围
	 * day:当日  oneWeek:最近7天  oneMonth:一个月  threeMonth:三个月  all:全部
	 * @param investBeginDate 投资日期起始日期
	 * @param investEndDate 投资日期结束日期 
	 * @param repayDateRange 还款时间范围
	 * day:当日  oneWeek:最近7天  oneMonth:一个月  threeMonth:三个月  all:全部
	 * @param repayBeginDate 还款日期起始日期
	 * @param repayEndDate 还款日期结束日期
	 * @param page
	 * @param num
	 * @param requestCallBack
	 */
	public static void getMyInvestList(Context context,String category,String status,String order,
			String investDateRange,String investBeginDate,String investEndDate,String repayDateRange,
			String repayBeginDate,String repayEndDate,String page,String num,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put(ReqUrls.CATEGORY, category);
		params.put("status", status);
		params.put(ReqUrls.ORDER, order);
		params.put("investDateRange", investDateRange);
		params.put("investBeginDate", investBeginDate);
		params.put("investEndDate", investEndDate);
		params.put("repayDateRange", repayDateRange);
		params.put("repayBeginDate", repayBeginDate);
		params.put("repayEndDate", repayEndDate);
		params.put(ReqUrls.PAGE, page);
		params.put(ReqUrls.NUM, num);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_USER_INVESTMENT_LIST, false,
				requestCallBack, MethodType.LOGIN, context);
	}
	/**
	 * 交易记录
	 * @param context
	 * @param category 交易分类
	 * @param order 排序
	 * @param dateRange 时间范围
	 * @param beginDate 开始日期
	 * @param endDate 结束日期
	 * @param page 当前页
	 * @param num 每页条数
	 * @param requestCallBack
	 */
	public static void getTrade(Context context,String category,String order,String dateRange,String beginDate
			,String endDate,String page,String num,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put(ReqUrls.CATEGORY, category);
		params.put(ReqUrls.ORDER, order);
		params.put(ReqUrls.DATE_RANGE, dateRange);
		params.put(ReqUrls.BEGIN_DATE, beginDate);
		params.put(ReqUrls.END_DATE, endDate);
		params.put(ReqUrls.PAGE, page);
		params.put(ReqUrls.NUM, num);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_TRANSACTION_LIST, false,
				requestCallBack, MethodType.LOGIN, context);
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
				requestCallBack, MethodType.LOGIN, context);
	}
	/**
	 * 获取用户优惠券
	 * @param context
	 * @param category 现金券cash，利息券interest，提现券draw
	 * @param type 1未使用，2已使用，3已过期
	 * @param page
	 * @param pageNum
	 * @param requestCallBack
	 */
	public static void getMyCoupons(Context context,String category,String type,String page,String pageNum,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put(ReqUrls.CATEGORY, category);
		params.put(ReqUrls.TYPE, type);
		params.put(ReqUrls.PAGE, page);
		params.put("page_num", pageNum);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_USER_COUPON, false,
				requestCallBack, MethodType.LOGIN, context);
	}
	/**
	 * 获取用户自己债权转让列表
	 * @param context
	 * @param status
	 * @param page
	 * @param pageNum
	 * @param requestCallBack
	 */
	public static void getMyDebtTransfer(Context context,String status,String page,String pageNum,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put("status", status);
		params.put(ReqUrls.PAGE, page);
		params.put(ReqUrls.NUM, pageNum);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_DEBT_TRANSFER_LIST, false,
				requestCallBack, MethodType.LOGIN, context);
	}
	
	/**
	 * 用户成长值记录
	 * @param context
	 * @param page
	 * @param num
	 * @param requestCallBack
	 */
	public static void getGrowthRecord(Context context,String page,String num,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put(ReqUrls.PAGE, page);
		params.put("page_num", num);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_GROWTH_RECORD, false,
				requestCallBack, MethodType.LOGIN, context);
	}
	/**
	 * 会员等级变更记录
	 * @param context
	 * @param page
	 * @param requestCallBack
	 */
	public static void getMemberChangeRecord(Context context,String page,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put(ReqUrls.PAGE, page);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_MEMBER_CHANGE_RECORD, false,
				requestCallBack, MethodType.LOGIN, context);
	}
	/**
	 * 获取用户安币变更记录
	 * @param context
	 * @param page
	 * @param beginTime
	 * @param endTime
	 * @param requestCallBack
	 */
	public static void getUserIntegralRecord(Context context,String page,String beginTime,String endTime,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put(ReqUrls.PAGE, page);
		params.put("begin_time", beginTime);
		params.put("end_time", endTime);
//		params.put("page_num", num);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_USER_INTEGRAL_RECORD, false,
				requestCallBack, MethodType.LOGIN, context);
	}
	
	
	/**
	 * 密码找回（发送短信验证码）
	 * @param context
	 * @param phone
	 * @param requestCallBack
	 */
	public static void getPwdSendCode(Context context,String phone,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put(ReqUrls.PHONE, phone);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_GETPWDSENDCODE, false,
				requestCallBack, MethodType.UPDATE, context);
	}
	/**
	 * 密码找回（验证短信验证码）
	 * @param context
	 * @param sessionId
	 * @param msgCode
	 * @param requestCallBack
	 */
	public static void getPwdCheckCode(Context context,String sessionId,String msgCode,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put(ReqUrls.SMSCODE_SESSION_ID, sessionId);
		params.put(ReqUrls.MESSAGE_CODE, msgCode);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_GETPWD_CHECKCODE, false,
				requestCallBack, MethodType.UPDATE, context);
	}
	/**
	 * 找回密码
	 * @param context
	 * @param mobile 手机号码
	 * @param captchaKey 手机验证码key
	 * @param captcha 手机验证码
	 * @param newPassword 新密码
	 * @param requestCallBack
	 */
	public static void getPwd(Context context,String mobile,String captchaKey,String captcha,String newPassword,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context,false);
		params.put(ReqUrls.MOBILE, mobile);
		params.put(ReqUrls.CAPTCHA_KEY, captchaKey);
		params.put(ReqUrls.CAPTCHA, captcha);
		params.put(ReqUrls.PASSWORD, newPassword);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_GETPWD, false,
				requestCallBack, MethodType.UPDATE, context);
	}
	
	/**
	 * 我的资产
	 * @param context
	 * @param requestCallBack
	 */
	public static void home(Context context,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_HOME, false,
				requestCallBack, MethodType.LOGIN, context);
	}
	
	/**
	 * 红包列表
	 * @param context
	 * @param requestCallBack
	 */
	public static void getEnvelopes(Context context,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_ENVELOPES, false,
				requestCallBack, MethodType.UPDATE, context);
	}
	
	/**
	 * 修改头像
	 * 
	 * @param context
	 * @param username
	 * @param stream
	 *            经过Base64转码过的字符串
	 * @param requestCallback
	 */
	// public static void updateHeader(Context context, String username,
	// String stream, File formFile, RequestCallback requestCallback) {
	// Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
	// params.put(ReqUrls.USERNAME, username);
	// params.put(ReqUrls.IMG_FILE, stream);
	// // Map<String,HttpBodyData> paramsImage = new HashMap<String,
	// // HttpBodyData>();
	// // paramsImage.put("source", new HttpBodyData(HttpBodyData.TYPE_STRING,
	// // "Android"));
	// // paramsImage.put("version", new HttpBodyData(HttpBodyData.TYPE_STRING,
	// // (String)params.get("version")));
	// // paramsImage.put("appname", new HttpBodyData(HttpBodyData.TYPE_STRING,
	// // (String)params.get("appname")));
	// // paramsImage.put(ReqUrls.USERNAME, new
	// // HttpBodyData(HttpBodyData.TYPE_STRING, username));
	// // paramsImage.put(ReqUrls.IMG_FILE, new
	// // HttpBodyData(HttpBodyData.TYPE_IMAGE,formFile));
	//
	// // params.put(ReqUrls.STREAM, stream);
	// ApiUtils.getParseModel(params, ReqUrls.UPDATE_HEADER_URL, false,
	// requestCallback, MethodType.UPDATE, context);
	// }

	/**
	 * 
	 * @param context
	 * @param username
	 * @param stream
	 * @param formFile
	 * @param requestCallback
	 */
	/*
	 * public static void updateHeader2(Context context, final String username,
	 * final String filePath, final RequestCallback requestCallback) { final
	 * Handler handler = new Handler() {
	 * 
	 * @Override public void handleMessage(Message message) { switch
	 * (message.what) { case 1: requestCallback.execute(((ParseModel)
	 * message.obj)); break; } } }; ThreadUtil.getTheadPool(true).submit(new
	 * Runnable() {
	 * 
	 * @Override public void run() { ParseModel pm = new ParseModel(); try { //
	 * 设定服务地址 String serverUrl = ReqUrls.UPDATE_HEADER_URL;// 上传地址
	 * 
	 * ArrayList<FormFieldKeyValuePair> ffkvp = new
	 * ArrayList<FormFieldKeyValuePair>(); ffkvp.add(new
	 * FormFieldKeyValuePair(ReqUrls.USERNAME, username));// 其他参数 ffkvp.add(new
	 * FormFieldKeyValuePair("type", "png")); // 设定要上传的文件
	 * ArrayList<UploadFileItem> ufi = new ArrayList<UploadFileItem>();
	 * ufi.add(new UploadFileItem("formFile", filePath)); HttpPostEmulator hpe =
	 * new HttpPostEmulator(); String response =
	 * hpe.sendHttpPostRequest(serverUrl, ffkvp, ufi); if
	 * (!StringUtils.isEmpty(response)) { pm = JsonUtils.fromJson(response,
	 * ParseModel.class); Message msg = handler.obtainMessage(); msg.what = 1;
	 * msg.obj = pm; handler.sendMessage(msg); }else{ Message msg =
	 * handler.obtainMessage(); msg.what = 1; pm = new ParseModel();
	 * pm.setMsg(NetUtil.NET_ERR_MSG);
	 * pm.setStatus(String.valueOf(NetUtil.FAIL_CODE)); msg.obj = pm;
	 * handler.sendMessage(msg); } } catch (Exception e) {
	 * 
	 * Thread.yield(); e.printStackTrace(); Message msg =
	 * handler.obtainMessage(); msg.what = 1; pm = new ParseModel();
	 * pm.setMsg(NetUtil.NET_ERR_MSG);
	 * pm.setStatus(String.valueOf(NetUtil.FAIL_CODE)); msg.obj = pm;
	 * handler.sendMessage(msg); } } });
	 * 
	 * }
	 */

	// /**
	// * 信息收集接口(意见、招聘、打分)
	// *
	// * @param context
	// * @param userId
	// * 用户ID(手机号)(可以为空)
	// * @param content
	// * 提出的建议内容
	// * @param type
	// * 0 意见反馈 1招聘 2 评论
	// * @param contactor
	// * 联系人姓名
	// * @param contact
	// * 联系方式(手机 or 邮箱拼接)
	// * @param ordernum
	// * 订单号(点评)
	// * @param productName
	// * 产品名称
	// * @param sellerName
	// * 美容师名称
	// * @param infoType
	// * 0好评 1中评 2差评
	// * @param sellerId
	// * 卖家id
	// * @param requestCallback
	// */
	// public static void unifor(Context context, long userId, String content,
	// int type, String contactor, String contact, String ordernum,
	// String productName, String sellerName, int infoType, long sellerId,
	// RequestCallback requestCallback) {
	// Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
	// if (userId != 0) {
	// params.put(ReqUrls.USER_ID, userId);
	// }
	// params.put("content", content);
	// params.put("type", type);
	// params.put("contactor", contactor);
	// params.put("contact", contact);
	// params.put("ordernum", ordernum);
	// params.put("productName", productName);
	// params.put("sellerName", sellerName);
	// if (infoType != 0) {
	// params.put("infoType", infoType);
	// }
	// if (sellerId != 0) {
	// params.put("sellerId", sellerId);
	// }
	//
	// ApiUtils.getParseModel(params, ReqUrls.UNIFOR, false, requestCallback,
	// MethodType.UPDATE, context);
	// }
	//
	// /**
	// * 忘记密码
	// *
	// * @param context
	// * @param username
	// * @param password
	// * @param requestCallback
	// */
	// public static void forgetPwd(Context context, String username,
	// String password, RequestCallback requestCallback) {
	// Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
	// params.put(ReqUrls.USERNAME, username);
	// params.put(ReqUrls.PASSWORD, password);
	// ApiUtils.getParseModel(params, ReqUrls.FORGET_PWD, false,
	// requestCallback, MethodType.UPDATE, context);
	// }
	//
	// /**
	// * 注册
	// *
	// * @param context
	// * @param mobile
	// * @param password
	// * @param lon
	// * @param lat
	// * @param requestCallback
	// */
	// public static void register(Context context, String mobile,
	// String password, double lon, double lat,
	// RequestCallback requestCallback) {
	// Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
	// params.put(ReqUrls.PASSWORD, password);
	// params.put(ReqUrls.MOBILE, mobile);
	// ApiUtils.getParseModel(params, ReqUrls.REGISTER_USER, false,
	// requestCallback, MethodType.UPDATE, context);
	// }
	//
	// /**
	// * 地址管理
	// *
	// * @param context
	// * @param type
	// * 0:添加 1删除 2修改 3查询
	// * @param userId
	// * 用户id
	// * @param curLocation
	// * 当前位置
	// * @param detailAddress
	// * 详细地址
	// * @param id
	// * 地址id
	// * @param requestCallback
	// */
	// public static void addressManager(Context context, int type, long userId,
	// String curLocation, String detailAddress, String id,String name,
	// RequestCallback requestCallback) {
	// Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
	// params.put("type", String.valueOf(type));
	// switch (type) {
	// case 0:// 增加地址
	// params.put(ReqUrls.USER_ID, userId);
	// params.put("curLocation", curLocation);
	// params.put("detailAddress", detailAddress);
	// params.put("contactor", name);
	// break;
	// case 1:// 删除地址
	// params.put(ReqUrls.ID, id);
	// break;
	// case 2:// 修改地址
	// params.put(ReqUrls.USER_ID, userId);
	// params.put("curLocation", curLocation);
	// params.put("detailAddress", detailAddress);
	// params.put("contactor", name);
	// params.put(ReqUrls.ID, id);
	// break;
	// case 3:// 查询地址
	// params.put(ReqUrls.USER_ID, userId);
	// break;
	//
	// default:
	// break;
	// }
	//
	// ApiUtils.getParseModel(params, ReqUrls.ADDRESS_MANAGER, false,
	// requestCallback, MethodType.UPDATE, context);
	// }
	//
	// /**
	// * 短信验证
	// *
	// * @param context
	// * @param mobile
	// * @param code
	// * @param type
	// * @param name
	// * @param requestCallback
	// */
	// public static void checkCode(Context context, String mobile, String code,
	// String type, String name, RequestCallback requestCallback) {
	// Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
	// params.put(ReqUrls.MOBILE, mobile);
	// params.put("code", code);
	// params.put("type", type);
	// params.put(ReqUrls.NAME, name);
	// ApiUtils.getParseModel(params, ReqUrls.CHECK_CODE, false,
	// requestCallback, MethodType.UPDATE, context);
	// }
	// /**
	// * 获取我的档案
	// * @param context
	// * @param id
	// * @param limit
	// * @param dealDate
	// * @param requestCallBack
	// */
	// public static void getMyFiles(Context context,long id ,int limit,String
	// dealDate,RequestCallback requestCallBack){
	// // /user/getArchives?id=21&dealDate=20141216&limit=3
	// Map<String,Object> params = HttpClientAddHeaders.getHeaders(context);
	// params.put(ReqUrls.ID, id);
	// params.put(ReqUrls.LIMIT, limit);
	// params.put("dealDate", dealDate);
	// ApiUtils.getParseModel(params, ReqUrls.REQUEST_MY_FILES,false,
	// requestCallBack, MethodType.UPDATE, context);
	// }
	// /**
	// * 获取验证码
	// * @param context
	// * @param mobile
	// * @param requestCallBack
	// */
	// public static void getCode(Context context,String mobile,RequestCallback
	// requestCallBack){
	// Map<String,Object> params = HttpClientAddHeaders.getHeaders(context);
	// params.put("mobile", mobile);
	// ApiUtils.getParseModel(params, ReqUrls.GET_CODE,false, requestCallBack,
	// MethodType.UPDATE, context);
	// }
	//
	// /**
	// * 获取关于我们的地址、分享的各类文案等等常用信息
	// * @param context
	// * @param requestCallBack
	// */
	// public static void getConfParams(Context context,RequestCallback
	// requestCallBack){
	// Map<String,Object> params = HttpClientAddHeaders.getHeaders(context);
	// ApiUtils.getParseModel(params, ReqUrls.REQUEST_CONFPARAMS,false,
	// requestCallBack, MethodType.UPDATE, context);
	// }
	// /**
	// * 获取用户个人中心界面
	// *
	// * @param userId
	// * @return
	// */
	// public static void getUserInfo(Context context,
	// RequestCallback requestCallBack) {
	// Map<String,Object> params = HttpClientAddHeaders.getHeaders(context);
	// // ApiUtils.getParseModel(params, ReqUrls.REQUEST_USER_INFO, false,
	// // requestCallBack,MethodType.USER_PAGE_INFO,null);
	// }
	//
	// /**
	// * 修改用户信息
	// *
	// * @param enterId
	// * @return
	// */
	// public static void modifyUserInfo(Context context, String name,
	// String address, String mobile, RequestCallback requestCallBack) {
	// Map<String,Object> params = HttpClientAddHeaders.getHeaders(context);
	// params.put(ReqUrls.NAME, name);
	// params.put(ReqUrls.MOBILE, mobile);
	// params.put(ReqUrls.ADDRESS, address);
	// ApiUtils.getParseModel(params, ReqUrls.UPDATE_USER_INFO, false,
	// requestCallBack, MethodType.GET_MAINPAGE_AD, null);
	// }
	//
	// /**
	// * 修改用户信息
	// *
	// * @param
	// * @return
	// */
	// public static void downloadRepoInfo(Context context, long appId) {
	// Map<String,Object> params = HttpClientAddHeaders.getHeaders(context);
	// params.put(ReqUrls.APP_ID, String.valueOf(appId));
	// ApiUtils.repo(params, ReqUrls.DOWNLOAD_REPO_INFO, HttpMethod.GET);
	// // ApiUtils.getParseModel(params, ReqUrls.DOWNLOAD_REPO_INFO, false,
	// // null,MethodType.DOWNLOAD_REPO_INFO,null);
	// }
	//
	// //
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// public static String parseLong2Str(long userId) {
	// return String.valueOf(userId);
	// }
}
