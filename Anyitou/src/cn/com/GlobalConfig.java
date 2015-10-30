package cn.com;

import android.content.Context;

public class GlobalConfig {
    public static String USER_ID = "";//用户id
    public static String USER_NAME = "";//用户名
    public static String ENTER_ID = "";//企业id

    public static boolean GLOBAL_NET_STATE = true; //网络状态 true:有网络 false:无网络

    public static Context appContext;  //app上下文

    public static final String DEVICE = "device";
    public static final String DEVICE_ID = "deviceid";

    // 网络设置部分
    public static int HTTP_CONNECTION_TIMEOUT = 20 * 1000;
    public static int HTTP_SO_TIMEOUT = 30 * 1000;

    public static final int NETWORK_STATE_IDLE = -1;
    public static final int NETWORK_STATE_WIFI = 1;
    public static final int NETWORK_STATE_CMNET = 2;
    public static final int NETWORK_STATE_CMWAP = 3;
    public static final int NETWORK_STATE_CTWAP = 4;
    public static final int NETWORK_STATE_MOBILE = 5;
    public static final int NETWORK_STATE_EDGE = 6;

    //设置页面属性
    public static boolean SETTING_WIFI = true;//WIFI模式下下载提示
    public static boolean SETTING_AUTO_DELETE_PKG = true;//游戏安装成功后安装包自动删除

    public static String VERSION_NAME_V = "V1.0.0";
    public static String VERSION_CODE_V = "";
    public static String EQUIPMENT_V = "";
    public static String APP_NAME = "";
    public static String CLIENT_TOKEN = "";//客户端授权码
    public static String ACCESS_TOKEN = "";//用户授权码
    public static String REFRESH_TOKEN = "";


    //服务器返回码。
    public static final String NETWORK_RESULT = "600";

    // 设置当前网络状态
    public static int CURRENT_NETWORK_STATE_TYPE = 0;

    public static final String PARENT_PATH = "/shopApp";
	public static String CACHE_IMG_PATH; // 存放图片路径
	public static String CACHE_DOWNLOAD_APK_PATH; // 应用下载路径
	public static String CACHE_DOWNLOAD_HOME;
	
	public static String CACHE_DATA_PATH; // 存放缓存数据路径
	public static String CACHE_ERROR_LOG_PATH; // 错误日志路径
	public static String CACHE_DOWNLOAD_TEMP_PATH; // 应用推荐下载路径
	public static String CACHE_UPDATE_PATH; // 更新包
	
	public static boolean isOpenCache = false; // 是否开启缓存功能


    /**
     * 所有列表每次列表请求的数目()
     */
    private final static int BASE_LIMIT_COUNT = 10;
    public static int SC_NUM = 0;//购物车商品数量

    public static int getLimitCount() {
        return BASE_LIMIT_COUNT;
    }
}
