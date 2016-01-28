package cn.com.anyitou.utils;

import cn.com.anyitou.api.constant.ReqUrls;

public class Log{
	/**
	 *  public static final int VERBOSE = 2;
    	public static final int DEBUG = 3;
    	public static final int INFO = 4;
    	public static final int WARN = 5;
    	public static final int ERROR = 6;
    	public static final int ASSERT = 7;
	 */
	
	public static void i(String tag,String log){
		if(ReqUrls.ISDEBUG){
			android.util.Log.i(tag,log);
		}
	}
	
	public static void d(String tag,String log){
		if(ReqUrls.ISDEBUG){
			android.util.Log.d(tag,log);
		}
	}
	
	public static void v(String tag,String log){
		if(ReqUrls.ISDEBUG){
			android.util.Log.v(tag,log);
		}
	}
	
	public static void w(String tag,String log){
		if(ReqUrls.ISDEBUG){
			android.util.Log.w(tag,log);
		}
	}
	
	public static void e(String tag,String log){
		if(ReqUrls.ISDEBUG){
			android.util.Log.e(tag,log);
		}
	}
	
}
