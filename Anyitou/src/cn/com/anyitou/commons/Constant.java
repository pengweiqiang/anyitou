package cn.com.anyitou.commons;

public class Constant {

	
    /**
     * 超时时间
     */
    public static final int TIMEOUT = 10000;

    private static final int GD_LOG_LEVEL_INFO = 3;

    private static final int GD_LOG_LEVEL_NONE = 0;
    
    public static final String FILE_NAME = "anyitou";
    //手势密码
    public static final String GESTURE_PWD = "gesture_pwd";
    
    public static final String LOCATION = "cn.com.anyitou.location";

    /**
     * Set this flag to GD_LOG_LEVEL_NONE when releasing your application in
     * order to remove all logs generated by GreenDroid.
     */
    private static final int GD_LOG_LEVEL = GD_LOG_LEVEL_NONE;

    /**
     * Indicates whether info logs are enabled. This should be true only when
     * developing/debugging an application/the library
     */
    public static final boolean GD_INFO_LOGS_ENABLED = (GD_LOG_LEVEL == GD_LOG_LEVEL_INFO);
    
    public static final String  IMAGELOADER_LOCAL_TITLE = "file://";
    
	/**
	 * 我的头像保存目录
	 */
	public static String MyAvatarDir = "/sdcard/cn.com.anyitou/head/";
	
	public static final String ACTION_MESSAGE_COUNT = "cn.com.anyitou.action.MESSAGE_COUNT";
    
}