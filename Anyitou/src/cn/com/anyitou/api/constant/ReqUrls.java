package cn.com.anyitou.api.constant;

public interface ReqUrls {

	public static final String PROJECT_NAME = "";
	
	public static final boolean neuSecure = true;
	
//	public static final String Connection_Type_Seccurity = "https://";
//	
//	public static final String Connection_Type_Common = "http://";
	
	public static String ONLINE_CONFIG_FILE_TYPE=".json";
	
	public static String USER_NAME="user_name"; //用户账号
	
	public static String TOKEN = "token";
	
	public static String USERNAME = "username";
	
	public static String PASSWORD="password";
	
	public static String PASS_WORD = "pass_word";
	
	public static String SESSION_ID = "session_id";
	
	public static String MESSAGE_CODE = "msgcode";
	
	public static String AUTH_CODE = "auth_code";
	
	public static String TEL = "tel";
	
	public static String CHOOSE = "choose";
	
	public static String NPASS_WORD = "npass_word";
	
	public static String REPASSWORD = "repassword";
	
	public static String STREAM = "stream";
	
	public static String CURPAGE = "curpage";
	
	public static String PAGE = "page";
	
	public static String V = "v";//1.回款中|2.投标中|3.已完成
	
	public static String ID = "id";
	
	public static String START = "start";
	
	public static String LIMIT = "limit" ;
	
	public static String USER_ID = "userId" ;
	
	public static String USER_LON = "lon" ;
	
	public static String USER_LAT = "lat" ;
	
	public static String UIDS = "uids";
	
	public static String TYPE = "type";
	
	public static String MONEY = "money";
	
	public static String ITEM_TYPE = "itemType";
	
	public static String OPER_TYPE = "operType";
	
	public static String KIND_TYPE = "kindType";
	
	public static String ITEM_ID = "itemId";
	
	public static String INFO_STR = "infoStr";
	
	public static String CATEGORY_ID = "industryId";
	
	public static String APP_ID = "appId";

    public static String INDUSTRY_ID = "industryId";
	
	public static String PRODUCT_MODEL = "productModel";
	

	
	public static final String CONFIG_HOST_IP="172.21.0.41:18082"; //dns ip
//	public static final String CONFIG_HOST_IP="http://bldj.com" 
	
	public static final String DEFAULT_REQ_HOST_IP = "https://www.18link.com/"; //正式接口请求地址
//	public static final String DEFAULT_REQ_HOST_IP = "http://18test.18link.com/";//测试地址
	
	public static final String LIMIT_DEFAULT_NUM="10";
	
	public static final String SEARCH_KEY="keyWord";
	
	public static final String ORDER_ID = "ordId";
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
	
//////////////////////////////////////////////接口地址//////////////////////////////////////////////////////////////////////////////
	/**
	 * 引导页
	 */
	public static final String MOBIAPI_BOOT = "mobiapi/boot";
	/**
	 * 发送短信验证码（注册）
	 */
	public static final String MOBIAPI_REGISTERCODE = "mobiapi/registerCode";
	
	/**
	 * 注册
	 */
	public static final String MOBIAPI_REGISTER = "mobiapi/register";
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
	 * 充值
	 */
	public static final String MOBIAPI_RECHARGE = "mobiapi/recharge";
	
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
	 * 投资
	 */
	public static final String MOBIAPI_INVESTING = "mobiapi/investing";
	/**
	 *查询投资结果 
	 */
	public static final String MOBIAPI_ISINVESTING_SUCCESS = "mobiapi/isInvestingSuccess";
	/**
	 * 发送短信验证码（提现）
	 */
	public static final String MOBIAPI_CASHCODE = "mobiapi/cashCode";
	/**
	 * 提现页面
	 */
	public static final String MOBIAPI_CASHPAGE = "mobiapi/cashPage";
	
	/**
	 * 提现手续费查询
	 */
	public static final String MOBIAPI_CHECK_MONEY = "mobiapi/checkmoney";
	
	/**
	 * 提现
	 */
	public static final String MOBIAPI_CASH = "mobiapi/cash";
	
	/**
	 * 投资列表
	 */
	public static final String MOBIAPI_INDEX = "mobiapi/index";
	
	/**
	 * 项目详情
	 */
	public static final String MOBIAPI_CONTENT_SHOW = "mobiapi/contentShow";
	
	/**
	 * 还款计划
	 */
	public static final String MOBIAPI_RECEIVING = "mobiapi/receiving";
	
	/**
	 * 我的投资
	 */
	public static final String MOBIAPI_INVEST = "mobiapi/invest";
	/**
	 * 交易记录
	 */
	public static final String MOBIAPI_TRADE = "mobiapi/trade";
	/**
	 * 我的银行卡
	 */
	public static final String MOBIAPI_MYCARD = "mobiapi/myCard";
	
	/**
	 * 修改密码
	 */
	public static final String MOBIAPI_CHANGE_PASSWORD = "mobiapi/changePassword";
	
	/**
	 * 密码找回（发送短信验证码）
	 */
	public static final String MOBIAPI_GETPWDSENDCODE = "mobiapi/getPwdSendCode";
	
	/**
	 * 密码找回（验证短信验证码）
	 */
	public static final String MOBIAPI_GETPWD_CHECKCODE = "mobiapi/getPwdCheckCode";
	
	/**
	 * 密码找回
	 */
	public static final String MOBIAPI_GETPWD = "mobiapi/getPwd";
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
