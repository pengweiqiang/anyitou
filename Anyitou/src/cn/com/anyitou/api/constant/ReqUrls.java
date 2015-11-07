package cn.com.anyitou.api.constant;

public interface ReqUrls {

	public static final String PROJECT_NAME = "";
	
	public static final boolean neuSecure = true;
	
//	public static final String Connection_Type_Seccurity = "https://";
//	
//	public static final String Connection_Type_Common = "http://";
	
	public static String ONLINE_CONFIG_FILE_TYPE=".json";
	
	public static String USER_NAME="username"; //用户账号
	
	public static String AUTHORIZATION = "Authorization";
	
	public static String GRANT_TYPE = "grant_type";
	
	//客户端授权默认(有效期2小时)
	public static String CLIENT_CREDENTIALS = "client_credentials";
	//用户授权token(有效期2小时)
	public static String ACCESS_TOKEN = "access_token";
	//通过 refresh_token 获取新的token(有效期30天)
	public static String REFRESH_TOKEN = "refresh_token";
	
	public static String USERNAME = "username";
	
	public static String PASSWORD="password";
	
	public static String SMSCODE_SESSION_ID = "smscode_session_id";
	
	
	public static String TITLE = "title";
	
	public static String CONTENT = "content";
	
	public static String MESSAGE_CODE = "msgcode";
	
	public static String SMS_CODE = "smscode";
	
	public static String MOBILE = "mobile";
	
	public static String RECID = "recid";
	
	public static String FROM = "from";
	
	public static String CAPTCHA_KEY = "captcha_key";
	
	public static String CAPTCHA = "captcha";
	
	public static String CHOOSE = "choose";
	
	public static String NEW_PASSWORD = "new_password";
	
	public static String REPASSWORD = "confirm_password";
	
	public static String STREAM = "stream";
	
	public static String CURPAGE = "curpage";
	
	public static String PAGE = "page";
	
	public static String NUM = "num";
	
	public static String V = "v";//1.回款中|2.投标中|3.已完成
	
	public static String ID = "id";
	
	public static String START = "start";
	
	public static String LIMIT = "limit" ;
	
	public static String USER_ID = "userId" ;
	
	public static String USER_LON = "lon" ;
	
	public static String USER_LAT = "lat" ;
	
	public static String UIDS = "uids";
	
	public static String TYPE = "type";
	
	public static String AMOUNT = "amount";
	
	public static String BANK_CARD_ID = "bank_card_id";
	
	public static String USE_COUPON_STATUS = "use_coupon_status";
	
	public static String ITEM_TYPE = "itemType";
	
	public static String OPER_TYPE = "operType";
	
	public static String KIND_TYPE = "kindType";
	
	public static String ITEM_ID = "itemId";
	
	public static String INFO_STR = "infoStr";
	
	public static String CATEGORY = "category";
	
	public static String APP_ID = "appId";

    public static String INDUSTRY_ID = "industryId";
	
	public static String PRODUCT_MODEL = "productModel";
	
	public static String DATE_RANGE = "dateRange";
	
	public static String BEGIN_DATE = "beginDate";
	
	public static String END_DATE = "endDate";
	

	
	public static final String CONFIG_HOST_IP="172.21.0.41:18082"; //dns ip
//	public static final String CONFIG_HOST_IP="http://bldj.com" 
	
//	public static final String DEFAULT_REQ_HOST_IP = "https://anyitou.com/"; //正式接口请求地址
	public static final String DEFAULT_REQ_HOST_IP = "http://testapi.anyitou.com/";//测试地址
	
	public static final String LIMIT_DEFAULT_NUM="10";
	
	public static final String SEARCH_KEY="keyWord";
	
	public static final String ORDER = "order";
	public static final String ORDER_TYPE = "orderType";
	
	public static final String NICK_NAME = "nickname";
	public static final String NAME = "name";
	public static final String PHONE = "phone";
	public static final String ADDRESS = "address";

	
	public static final String OPEN_TYPE = "operType";
	
	public static final String IMG_FILE = "formFile";
	
	public static final String CUR_VERSION = "curVersion";
	
	public static final String FILE_STATE = "fileState";
	
	public static final String DEVICE_VERSION = "deviceVersion";
	
	public static final String CLIENT_IP = "clientIp";
	
	public static final String DEVICE = "device";
	
	public static final String EXCEPTION_MSG="exceptionMsg";
	
	public static final String USER_REQUEST_INFO="userRequestInfo";
	
	//测试环境，客户端用于验证授权head头部信息：
	public static final String CLIENT_KEY = "android";
	
	public static final String CLIENT_SECRET = "android";
	
//////////////////////////////////////////////接口地址//////////////////////////////////////////////////////////////////////////////
	/**
	 * 请求授权
	 */
	public static final String MOBIAPI_AUTH = "oauth2/accessToken";
	
	/**
	 * 获取项目信息
	 */
	public static final String MOBIAPI_PROJECT_DETAIL = "projects/";
	/**
	 * 获取用户资金信息
	 */
	public static final String MOBIAPI_MY_ACCOUNT = "user/account";
	
	/**
	 * 推荐项目
	 */
	public static final String MOBIAPI_RECOMMEND = "project/recommend";
	
	/**
	 * 引导页
	 */
	public static final String MOBIAPI_BOOT = "mobiapi/boot";
	/**
	 * 验证短信验证码
	 */
	public static final String MOBIAPI_REGISTERCODE = "smscode/validate";
	/**
	 * 修改手机号
	 */
	public static final String MOBIAPI_MODIFY_MOBILE = "userAssist/modifyMobile";
	
	/**
	 * 注册
	 */
	public static final String MOBIAPI_REGISTER = "user/register";
	
	/**
	 * 提现
	 */
	public static final String MOBIAPI_CASH = "withdraw/create";
	
	/**
	 * 绑定银行卡
	 */
	public static final String MOBIAPI_BINDBANK = "withdraw/bindBank";
	
	/**
	 * 获取支持快捷支付的银行
	 */
	public static final String MOBIAPI_BANK = "withdraw/bank";
	/**
	 * 提现获取银行卡信息
	 */
	public static final String MOBIAPI_BANK_INFO = "withdraw/info";
	/**
	 * 获取APP Banner
	 */
	public static final String MOBIAPI_BANNER = "resources/banner";
	/**
	 * 意见反馈
	 */
	public static final String MOBIAPI_REPORT = "resources/report";
	/**
	 * 内容介绍相关
	 */
	public static final String MOBIAPI_INTRODUCTION = "resources/introduction";
	/**
	 * 启动引导
	 */
	public static final String MOBIAPI_GUIDES = "resources/guides";
	
	/**
	 * 发送短信验证码（不需要用户进行登录）
	 */
	public static final String MOBIAPI_SMS_CODE = "smscode/mobile";
	
	/**
	 * 发送短信验证码（需要用户进行登录）
	 */
	public static final String MOBIAPI_SMS_CODE_USER = "smscode/user";
	
	/**
	 * 充值
	 */
	public static final String MOBIAPI_RECHARGE = "recharge/create";
	
	/**
	 * 获取项目列表
	 */
	public static final String MOBIAPI_INDEX = "project/list";
	/**
	 * 获取用户银行卡信息
	 */
	public static final String MOBIAPI_RECHARGE_INFO = "recharge/info";
	
	/**
	 * 投资
	 */
	public static final String MOBIAPI_INVESTING = "investment/create";
	
	/**
	 * 26.交易状态查询接口
	 */
	public static final String MOBIAPI_TRANSACTION_STATUS = "transaction/status";
	
	/**
	 * 27.获取交易详情
	 */
	public static final String MOBIAPI_TRANSACTION_VIEW = "transaction/view";
	
	/**
	 * 28.获取交易记录列表
	 */
	public static final String MOBIAPI_TRANSACTION_LIST = "transaction/list";
	
	/**
	 * 29.获取项目投资记录列表接口
	 */
	public static final String MOBIAPI_INVESTMENTS = "project/investments";
	
	/**
	 * 30.修改密码
	 */
	public static final String MOBIAPI_CHANGE_PASSWORD = "userAssist/modifyPassword";
	
	/**
	 * 31.修改手势密码
	 */
	public static final String MOBIAPI_CHANGE_GESTURE_PASSWORD = "userAssist/modifySignpassword";
	
	/**
	 * 32.设置手势密码
	 */
	public static final String MOBIAPI_SET_GESTURE_PASSWORD = "userAssist/setSignpassword";
	
	/**
	 * 33.获取债权列表
	 */
	public static final String MOBIAPI_DEBT_ASSIGNMENT = "debtAssignment/list";
	
	/**
	 * 34.获取债权详情
	 */
	public static final String MOBIAPI_DEBT_ASSIGNMENT_DETAIL = "debtAssignment/view";
	
	/**
	 * 38.获取用户自己债权转让列表
	 */
	public static final String MOBIAPI_DEBT_TRANSFER_LIST = "debt/TransferList";
	
	
	/**
	 * 41.获取兑换物品列表
	 */
	public static final String MOBIAPI_INTEGRAL_GOODS = "integral/goods";
	/**
	 * 43.安币变更记录
	 */
	public static final String MOBIAPI_USER_INTEGRAL_RECORD = "integral/userIntegralRecord";
	/**
	 * 44.密码找回
	 */
	public static final String MOBIAPI_GETPWD = "userAssist/findPassword";
	
	/**
	 * 45.兑换物品
	 */
	public static final String MOBIAPI_EXCHANGE_GOODS = "integral/exchangeGoods";
	/**
	 * 47.会员特权信息(/grade/info)
	 */
	public static final String MOBIAPI_GRADES = "grade/info";
	
	/**
	 * 48.会员等级变更记录
	 */
	public static final String MOBIAPI_MEMBER_CHANGE_RECORD = "grade/gradeRecord";

	/**
	 * 49.用户成长值记录
	 */
	public static final String MOBIAPI_GROWTH_RECORD = "grade/growthRecord";
	
	/**
	 * 50.用户安币信息
	 */
	public static final String MOBIAPI_USER_INTEGRAL = "integral/userIntegral";
	
	/**
	 * 51.获取用户优惠券信息
	 */
	public static final String MOBIAPI_USER_COUPON = "coupon/list";
	
	/**
	 * 52. 获取用户自己的投资记录
	 */
	public static final String MOBIAPI_USER_INVESTMENT_LIST = "investment/list";
	
	
	
	
	
	//////////////////////////////////////////////
	
	
	
	/**
	 * 登录
	 */
	public static final String MOBIAPI_LOGIN = "mobiapi/login";
	
	/**
	 * 登出
	 */
	public static final String MOBIAPI_LOGOUT = "mobiapi/logout";

	/**
	 * 注册汇付
	 */
	public static final String MOBIAPI_HF_REGISTER = "mobiapi/hFRegister";
	
	/**
	 * 查询注册汇付结果
	 */
	public static final String MOBIAPI_ISHFUSER = "mobiapi/isHFUser";
	
	
	/**
	 * 查询充值结果
	 */
	public static final String MOBIAPI_ISRECHARGE_SUCCESS = "mobiapi/isRechargeSuccess";
	
	/**
	 * 我的资产
	 */
	public static final String MOBIAPI_HOME = "mobiapi/home";
	
	/**
	 * 投资页面
	 */
	public static final String MOBIAPI_INVESTING_PAGE = "mobiapi/investingPage";
	/**
	 *查询投资结果 
	 */
	public static final String MOBIAPI_ISINVESTING_SUCCESS = "mobiapi/isInvestingSuccess";
	
	/**
	 * 提现页面
	 */
	public static final String MOBIAPI_CASHPAGE = "mobiapi/cashPage";
	
	/**
	 * 提现手续费查询
	 */
	public static final String MOBIAPI_CHECK_MONEY = "mobiapi/checkmoney";
	
	
	/**
	 * 还款计划
	 */
	public static final String MOBIAPI_RECEIVING = "mobiapi/receiving";
	
	/**
	 * 我的投资
	 */
	public static final String MOBIAPI_INVEST = "mobiapi/invest";
	
	/**
	 * 我的银行卡
	 */
	public static final String MOBIAPI_MYCARD = "mobiapi/myCard";
	
	
	/**
	 * 密码找回（发送短信验证码）
	 */
	public static final String MOBIAPI_GETPWDSENDCODE = "mobiapi/getPwdSendCode";
	
	/**
	 * 密码找回（验证短信验证码）
	 */
	public static final String MOBIAPI_GETPWD_CHECKCODE = "mobiapi/getPwdCheckCode";
	
	
	/**
	 * 红包列表
	 */
	public static final String MOBIAPI_ENVELOPES = "mobiapi/envelopes";
	/**
	 * 分享文案
	 */
	public static final String MOBIAPI_SHARE = "mobiapi/share";
	
	/**
	 * 关于我门
	 */
	public static final String MOBIAPI_ABOUTUS = "mobiapi/aboutUs";
	
	
    public static final String DOWNLOAD_REPO_INFO = "/app/downLoadApp";
    public static final String REQUEST_EXCEPTION_REPO = "";
}
