package cn.com.anyitou.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.apache.http.conn.util.InetAddressUtils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class DeviceInfo {
	public static final int IMEI_ERROR = 403;
	public static final int IMEI_USER_ERROR = 405;
	
	private static Context mContext;
	
	public static void setContext(Context aContext) {
		mContext = aContext.getApplicationContext();
	}
	
	/**
	 * get device's IMSI
	 * 
	 * @return IMSI number
	 */
	public static String getIMSI() {
		return ((TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
	}
	
	/**
	 * get device's IMEI
	 * 
	 * @return IMEI number
	 */
	public static String getIMEI() {
		return ((TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
	}
	
	/**
	 * get device's MODEL
	 * 
	 * @return model string
	 */
	public static String getMobileModel() {
		return Build.MODEL;
	}
	
	/**
	 * Get application SDK version
	 * 
	 * @return
	 */
	public static int getSDKVersionNumber() {
		return Build.VERSION.SDK_INT;
	}
	
	/**
	 * Get application OS version
	 * 
	 * @return
	 */
	public static String getOSVersion() {
		return Build.VERSION.RELEASE;
	}
	
	/**
	 * Test if wifi is activated
	 * 
	 * @param
	 * @return
	 */
	public static boolean isWifi() {
		ConnectivityManager connectivityManager = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI;
	}
	
	public static String getVersionName() {
		String versionName = "1.1.0";
		try {
			versionName = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionName;
	}
	
	public static int getVersionCode() {
		int versionCode = 2;
		try {
			versionCode = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}
	
	/**
	 * 获取version
	 * 
	 * @return
	 */
	public static String getDeviceVersion() {
		return android.os.Build.VERSION.RELEASE;
	}
	
	public static String getMac() {
		if (mContext == null) {
			return null;
		}
		WifiManager wifi = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		return info.getMacAddress();
	}
	
	/**
	 * 获取屏幕宽度
	 * 
	 * @return Wight of window
	 */
	public static int getScreenWidth(Context context) {
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics mMetric = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(mMetric);
		return mMetric.widthPixels;
	}
	//获取屏幕宽度
		public static int getDisplayMetricsWidth(Activity activity) {
//			int i = activity.getWindowManager().getDefaultDisplay().getWidth();
//			int j = activity.getWindowManager().getDefaultDisplay().getHeight();
//			return Math.min(i, j);
			WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
	        DisplayMetrics mMetric = new DisplayMetrics();
	        windowManager.getDefaultDisplay().getMetrics(mMetric);
	        return mMetric.widthPixels;
		}

    /**
     * 获取屏幕高度
     *
     * @return Wight of window
     */
    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics mMetric = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(mMetric);
        return mMetric.heightPixels;
    }

    /**
     * 获取屏幕宽度
     *
     * @return Wight of window
     */
    public static int getScreenWidth() {
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics mMetric = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(mMetric);
        return mMetric.widthPixels;
    }
	
	/**
	 * 获取屏幕高度
	 * 
	 * @return Height of window
	 */
	public static int getScreenHeight() {
		WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics mMetric = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(mMetric);
		return mMetric.heightPixels;
	}
	 /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     * @param context 上下文，一般为Activity
     * @param pxValue px像素值
     * @return dp数据值
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale=context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }
    public static int dp2px(Context context,float dpValue){
    	final float scale=context.getResources().getDisplayMetrics().density;
        return (int)((dpValue-0.5) * scale);
    }
	
	/**
	 * 获取ip
	 * 
	 * @return
	 */
	public static String getHostIp() {
		try {
			Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
			while (en.hasMoreElements()) {
				NetworkInterface nif = en.nextElement();
				Enumeration<InetAddress> enumIpAddr = nif.getInetAddresses();
				while (enumIpAddr.hasMoreElements()) {
					InetAddress mInetAddress = enumIpAddr.nextElement();
					
					if (!mInetAddress.isLoopbackAddress()
							&& InetAddressUtils.isIPv4Address(mInetAddress.getHostAddress())) {
						
						return mInetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
			ex.printStackTrace();
		}
		return "";
	}
	
//	/**
//	 * 获取手机设备号IMEI
//	 * 
//	 * @return MD5校验值（32位字符串）
//	 */
//	public static String getTelephoneSerialNum(Context mContext) {
//		TelephonyManager TelephonyMgr = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
//		String m_szImei = TelephonyMgr.getDeviceId();
//		if (m_szImei == null || "".equals(m_szImei)) {
//			m_szImei = "0000000000000000";
//		}
//		StringBuffer res = new StringBuffer();
//		res.append(m_szImei);
//		res.append(Secure.getString(mContext.getContentResolver(), Secure.ANDROID_ID));
//		WifiManager wm = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
//		res.append(wm.getConnectionInfo().getMacAddress());
//		BluetoothAdapter m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//		res.append(m_BluetoothAdapter.getAddress());
//		res.append(Build.BOARD).append(Build.BRAND).append(Build.CPU_ABI).append(Build.DEVICE);
//		res.append(Build.DISPLAY).append(Build.HOST).append(Build.ID).append(Build.MANUFACTURER);
//		res.append(Build.MODEL).append(Build.PRODUCT).append(Build.TAGS).append(Build.TYPE).append(Build.USER);
//		return getMD5Str(res.toString());
//	}
}
