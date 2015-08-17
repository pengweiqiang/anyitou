package cn.com.anyitou.api.constant;

/**
 * api 常量
 */
public interface ApiConstants {
	
	/**
	 * 微信支付相关配置信息  start
	 */
	// APP_ID 替换为你的应用从官方网站申请到的合法appId
    public static final String APP_ID = "wxd930ea5d5a258f4f";
    
    /** 商家向财付通申请的商家id */
    public static final String PARTNER_ID = "1900000109";
	
    /**
     * 微信支付相关配置信息  end
     */
    
	/**
	 * 接口正常
	 */
	public static final String RESULT_SUCCESS = "1";
	
	/**
	 * 系统异常
	 */
	public static final String RESULT_ERROR = "601";
	
	/**
	 * 用户不存在
	 */
	public static final String RESULT_USER_NOT_EXIST = "602";
	/**
	 * 用户账号不能为空
	 */
	public static final String RESULT_USER_NOT_EMPTY = "603";
	/**
	 * 用户帐号被冻结
	 */
	public static final String RESULT_USER_LOCK = "604";
	
	
    public static final String V_BAR = "|";
    
    public static final String S_BAR = "/";
    
    public static final String SEMICOLON = ";";
    
    public static final String UNIT_M = "MB";
    
    public static final String UNIT_KB = "KB";
    
    public static final String UNIT_SEC = "S";
    
    public static final String RESULT_BACK_STR = "backStr";
    
    public static final int RESULT_CODE = 999;
    
    public static final int MAX_CONNECTION_TIME = 10000;
    
    public static final int THREAD_DEAL_NUM = 300; //每个线程处理的数量    
    
    public static final int LIMIT = 10;
    
    public static final int MAX_STATUS = 99999;
    
    //是否打印日志 上线改为false
    public static boolean ISDEBUG = true;
    
    //充值
    public static int TYPE_RECHARGE = 1001;
    
    //提现
    public static int TYPE_CASH = 1002;
    
    //投资
    public static int TYPE_INVEST = 1003;
    
}
