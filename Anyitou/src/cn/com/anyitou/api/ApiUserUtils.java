package cn.com.anyitou.api;

import java.util.Map;

import android.content.Context;

import cn.com.anyitou.api.constant.MethodType;
import cn.com.anyitou.api.constant.ReqUrls;
import cn.com.anyitou.http.HttpClientAddHeaders;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;

/**
 * api user用户相关的接口
 * 
 * @author will
 */
public class ApiUserUtils {

	/**
	 * 发送短信验证码（注册）
	 * 
	 * @param context
	 * @param phone
	 *            电话号码
	 * @param requestCallBack
	 */
	public static void registerCode(Context context, String phone,
			RequestCallback requestCallBack) {
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put(ReqUrls.PHONE, phone);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_REGISTERCODE, false,
				requestCallBack, MethodType.LOGIN, context);
	}

	/**
	 * 注册
	 * 
	 * @param context
	 * @param session_id
	 *            用于验证短信验证码
	 * @param authCode
	 *            短信验证码
	 * @param userName
	 *            用户名
	 * @param passWord
	 *            密码
	 * @param tel
	 *            手机号码
	 * @param phone
	 *            推荐人手机号码
	 * @param choose
	 *            是否同意协议
	 * @param requestCallBack
	 */
	public static void register(Context context, String session_id,
			String authCode, String userName, String passWord, String tel,
			String phone, String choose, RequestCallback requestCallBack) {
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put(ReqUrls.SESSION_ID, session_id);
		params.put(ReqUrls.AUTH_CODE, authCode);
		params.put(ReqUrls.USER_NAME, userName);
		params.put(ReqUrls.PASS_WORD, passWord);
		params.put(ReqUrls.TEL, tel);
		params.put(ReqUrls.PHONE, phone);
		params.put(ReqUrls.CHOOSE, choose);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_REGISTER, false,
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
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put(ReqUrls.USER_NAME, userName);
		params.put(ReqUrls.PASS_WORD, passWord);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_LOGIN, false,
				requestCallBack, MethodType.LOGIN, context);
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
	 * @param v
	 * @param page
	 * @param requestCallBack
	 */
	public static void getMyInvest(Context context,String v,String page,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put(ReqUrls.V, v);
		params.put(ReqUrls.PAGE, page);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_INVEST, false,
				requestCallBack, MethodType.LOGIN, context);
	}
	/**
	 * 交易记录
	 * @param context
	 * @param page
	 * @param requestCallBack
	 */
	public static void getTrade(Context context,String page,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put(ReqUrls.PAGE, page);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_TRADE, false,
				requestCallBack, MethodType.LOGIN, context);
	}
	/**
	 * 我的银行卡
	 * @param context
	 * @param requestCallBack
	 */
	public static void getMyCard(Context context,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_MYCARD, false,
				requestCallBack, MethodType.LOGIN, context);
	}
	
	/**
	 * 修改密码
	 * 
	 * @param context
	 * @param username
	 * @param newpassword 新密码
	 * @param password 原密码
	 * @param requestCallback
	 */
	public static void updatePwd(Context context, String username,
			String password, String newpassword, RequestCallback requestCallback) {
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put(ReqUrls.USERNAME, username);
		params.put(ReqUrls.PASS_WORD, password);
		params.put(ReqUrls.NPASS_WORD, newpassword);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_CHANGE_PASSWORD, false,
				requestCallback, MethodType.UPDATE, context);
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
		params.put(ReqUrls.SESSION_ID, sessionId);
		params.put(ReqUrls.MESSAGE_CODE, msgCode);
		ApiUtils.getParseModel(params, ReqUrls.MOBIAPI_GETPWD_CHECKCODE, false,
				requestCallBack, MethodType.UPDATE, context);
	}
	/**
	 * 密码找回
	 * @param context
	 * @param sessionId
	 * @param password 新密码
	 * @param repassword 重复密码
	 * @param requestCallBack
	 */
	public static void getPwd(Context context,String sessionId,String password,String repassword,RequestCallback requestCallBack){
		Map<String, Object> params = HttpClientAddHeaders.getHeaders(context);
		params.put(ReqUrls.SESSION_ID, sessionId);
		params.put(ReqUrls.PASSWORD, password);
		params.put(ReqUrls.REPASSWORD, repassword);
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
