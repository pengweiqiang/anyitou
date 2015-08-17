package cn.com.anyitou.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.conn.util.InetAddressUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;

@SuppressLint("SimpleDateFormat")
public class CommonHelper {
	
	private static String deviceid_ = null;
	private static Set<Long> set_ = new HashSet<Long>();
	private static boolean loginStatus_ = false;
	private static Map<Integer, Double> map_ = new ConcurrentHashMap<Integer, Double>();
	private static boolean sdcardRemoved = false;
	public static float density = 0f,zoomScale = 0.0f;
	
	private static int mScreenWidth;
	private static int mScreenHeight;
	
	/**
	 * 
	 * @param status
	 */
	public static void setSdCardRemoved(boolean status) {
		sdcardRemoved = status;
	}
	
	public static boolean isSdCardRemoved() {
		return sdcardRemoved;
	}

    public static int getDp2px(Context context,int dp){
        return (int)(dp*CommonHelper.getZoomScale(context));
    }

	@SuppressWarnings("deprecation")
	public static Drawable getAssertDrawable(Context context,String fileName){
        try {
            InputStream inStream=context.getAssets().open(fileName);
            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inDensity = 320;
            options.inTargetDensity = context.getResources().getDisplayMetrics().densityDpi;
            options.inScaled  = true;
            BitmapDrawable bd = new BitmapDrawable(BitmapFactory.decodeStream(inStream,null,options));
             bd.setTargetDensity(context.getResources().getDisplayMetrics());

            return bd;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
	
	public static Bitmap getAssertBitmap(Context context,String fileName){
        try {
            InputStream inStream=context.getAssets().open(fileName);
            return BitmapFactory.decodeStream(inStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }




    @SuppressWarnings("deprecation")
	public static Drawable getAssertNinePatchDrawable(Context context,String fileName){
        try {
            InputStream inStream=context.getAssets().open(fileName);
            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inDensity = 320;
            options.inTargetDensity = context.getResources().getDisplayMetrics().densityDpi;
            options.inScaled  = true;
            //options.inJustDecodeBounds = false;
            //options.inSampleSize = 2;
            Bitmap bitmap = BitmapFactory.decodeStream(inStream,new Rect(),options);
            //return NinePatchDrawableFactory.decodeStream(inStream);
           return new NinePatchDrawable(bitmap,bitmap.getNinePatchChunk(),new Rect(),null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

	
	public static Drawable getDefaultAdDrawable(Context context){
		return getAssertDrawable(context,"app_store/default_ad.png");        
    }
	
	public static Drawable getDefaultAppIconDrawable(Context context){
		return getAssertDrawable(context,"app_store/default_icon.png");
    }

    public static StateListDrawable getStateDrawable(Drawable normal, Drawable pressed, Drawable focused) {
        StateListDrawable sd = new StateListDrawable();

        //注意该处的顺序，只要有一个状态与之相配，背景就会被换掉
        //所以不要把大范围放在前面了，如果sd.addState(new[]{},normal)放在第一个的话，就没有什么效果了
        sd.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_focused}, focused);
        sd.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, pressed);
        sd.addState(new int[]{android.R.attr.state_focused}, focused);
        sd.addState(new int[]{android.R.attr.state_pressed}, pressed);
        sd.addState(new int[]{android.R.attr.state_checked}, pressed);
        sd.addState(new int[]{android.R.attr.state_enabled}, normal);
        sd.addState(new int[]{}, normal);
        return sd;
    }

	public static StateListDrawable getStateDrawable(Context context,  String normalStr, String pressedStr) {  
	        StateListDrawable sd = new StateListDrawable();  
	        Drawable normal = normalStr == null ? new ColorDrawable(Color.TRANSPARENT) : getAssertDrawable(context,normalStr);
	        Drawable pressed = pressedStr == null ? new ColorDrawable(Color.TRANSPARENT) : getAssertDrawable(context,pressedStr);
	        Drawable focus = pressedStr == null ? new ColorDrawable(Color.TRANSPARENT) : getAssertDrawable(context,pressedStr);
	        //注意该处的顺序，只要有一个状态与之相配，背景就会被换掉  
	        //所以不要把大范围放在前面了，如果sd.addState(new[]{},normal)放在第一个的话，就没有什么效果了   
	        sd.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_focused}, focus);  
	        sd.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, pressed);  
	        sd.addState(new int[]{android.R.attr.state_focused}, focus);  
	        sd.addState(new int[]{android.R.attr.state_pressed}, pressed);  
	        sd.addState(new int[]{android.R.attr.state_checked}, pressed); 
	        sd.addState(new int[]{android.R.attr.state_selected}, pressed); 
	        sd.addState(new int[]{android.R.attr.state_enabled}, normal);  
	        sd.addState(new int[]{}, normal);  
	        return sd;  
	    } 
	
	public static StateListDrawable getNinePatchStateDrawable(Context context,  String normalStr, String pressedStr) {  
        StateListDrawable sd = new StateListDrawable();  
        Drawable normal = normalStr == null ? null : getAssertNinePatchDrawable(context,normalStr);  
        Drawable pressed = pressedStr == null ? null : getAssertNinePatchDrawable(context,pressedStr);  
        Drawable focus = pressedStr == null ? null : getAssertNinePatchDrawable(context,pressedStr);  
        //注意该处的顺序，只要有一个状态与之相配，背景就会被换掉  
        //所以不要把大范围放在前面了，如果sd.addState(new[]{},normal)放在第一个的话，就没有什么效果了   
        sd.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_focused}, focus);  
        sd.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, pressed);  
        sd.addState(new int[]{android.R.attr.state_focused}, focus);  
        sd.addState(new int[]{android.R.attr.state_pressed}, pressed);  
        sd.addState(new int[]{android.R.attr.state_selected}, pressed);
        sd.addState(new int[]{android.R.attr.state_checked}, pressed); 
        sd.addState(new int[]{android.R.attr.state_enabled}, normal);  
        sd.addState(new int[]{}, normal);  
        return sd;  
    }  
	 
	public static StateListDrawable getProgressBarDrawable(Context context,  String normalStr, String filledStr) {  
        StateListDrawable sd = new StateListDrawable();  
        Drawable normal = normalStr == null ? null : getAssertDrawable(context,normalStr);   
        Drawable focus = filledStr == null ? null : getAssertDrawable(context,filledStr);  
      
        sd.addState(new int[]{android.R.attr.background}, normal);  
        sd.addState(new int[]{android.R.attr.secondaryProgress}, normal); 
        sd.addState(new int[]{android.R.attr.progress}, focus);  
        sd.addState(new int[]{}, normal);  
        return sd;  
    }


    public static Drawable getDefaultBtnBgDrawable() {
        GradientDrawable normal = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{0xFF2F81F8, 0xFF438BF7});
        normal.setCornerRadius(8);

        GradientDrawable pressed = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{0xFF438BF7, 0xFF2F81F8});
          //      new int[]{0xFF004DBC, 0xFF00409F});
        pressed.setCornerRadius(8);

        return getStateDrawable(normal,pressed,pressed);
    }

    public static Drawable getRatingBarDrawables(Context context,  String normalStr, String filledStr) {
	    final int[] requiredIds = { android.R.id.background,
	            android.R.id.secondaryProgress, android.R.id.progress };
	    final float[] roundedCorners = new float[] { 5, 5, 5, 5, 5, 5, 5, 5 };
	    Bitmap[] images = new Bitmap[3];
	    images[0] = getAssertBitmap(context, normalStr);
	    images[1] = images[0];
	    images[2] = getAssertBitmap(context, filledStr);
	    Drawable[] pieces = new Drawable[3];
	    for (int i = 0; i < 3; i++) {
	        ShapeDrawable sd = new ShapeDrawable(new RoundRectShape(
	                roundedCorners, null, null));
	        BitmapShader bitmapShader = new BitmapShader(images[i],
	                TileMode.REPEAT, TileMode.CLAMP);
	        sd.getPaint().setShader(bitmapShader);
	        ClipDrawable cd = new ClipDrawable(sd, Gravity.LEFT,
	                ClipDrawable.HORIZONTAL);
	        if (i == 0) {
	            pieces[i] = sd;
	        } else {
	            pieces[i] = cd;
	        }
	    }
	    LayerDrawable ld = new LayerDrawable(pieces);
	    for (int i = 0; i < 3; i++) {
	        ld.setId(i, requiredIds[i]);
	    }
	    return ld;
	}
	
	/**
	 * @param key
	 * @param value
	 */
	public static void putCommpressSize(int key, double value) {
		map_.put(key, value);
	}
	
	public static String getCompressSize() {
		double sum = 0.0d;
		Iterator<Double> iters = map_.values().iterator();
		while (iters.hasNext()) {
			sum += iters.next();
		}
		return new java.text.DecimalFormat("#0.00").format(sum);
	}
	
	/**
	 * 
	 * @param status
	 */
	public static void setLoginStatus(boolean status) {
		loginStatus_ = status;
	}
	
	public static boolean getLoginStatus() {
		return loginStatus_;
	}
	
	public static boolean isCompress(long mAppid) {
		if (set_.contains(mAppid)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param mAppid
	 */
	public synchronized static void addCompress(long mAppid) {
		set_.add(mAppid);
	}
	
	/**
	 * 
	 * @param mAppid
	 */
	public synchronized static void removeCompress(long mAppid) {
		set_.remove(mAppid);
	}
	
	/**
	 * 
	 * @param manager
	 * @param packagename
	 * @return
	 */
	public static boolean isTopActivity(ActivityManager manager, String packagename) {
		List<RunningTaskInfo> list = manager.getRunningTasks(1);
		if (list.size() > 0) {
			String pn = list.get(0).topActivity.getPackageName();
			return packagename.equals(pn);
		}
		return false;
	}
	
	/**
	 * 
	 * @param value
	 * @param charset
	 * @return
	 */
	public static String getEncoderString(String value, String charset) {
		if (value != null) {
			try {
				return URLEncoder.encode(value, charset);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return value;
	}
	
	
	/**
	 * 获取Wifi网卡MAC地址
	 * 
	 * @return String Wifi网卡MAC地址
	 */
	public static String getLocalMacAddress(Context context) {
		String macAddress = "";
		try {
			if (null != context) {
				WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
				WifiInfo info = wifi.getConnectionInfo();
				macAddress = info.getMacAddress();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return macAddress;
	}
	
	/**
	 * 
	 * @param context
	 * @param apkPath
	 * @return
	 * @throws Exception
	 */
	public static Resources getResources(Context context, String apkPath) throws Exception {
		String PATH_AssetManager = "android.content.res.AssetManager";
		Class<?> assetMagCls = Class.forName(PATH_AssetManager);
		Constructor<?> assetMagCt = assetMagCls.getConstructor((Class[]) null);
		Object assetMag = assetMagCt.newInstance((Object[]) null);
		Class<?>[] typeArgs = new Class[1];
		typeArgs[0] = String.class;
		Method assetMag_addAssetPathMtd = assetMagCls.getDeclaredMethod("addAssetPath", typeArgs);
		Object[] valueArgs = new Object[1];
		valueArgs[0] = apkPath;
		assetMag_addAssetPathMtd.invoke(assetMag, valueArgs);
		Resources res = context.getResources();
		typeArgs = new Class[3];
		typeArgs[0] = assetMag.getClass();
		typeArgs[1] = res.getDisplayMetrics().getClass();
		typeArgs[2] = res.getConfiguration().getClass();
		Constructor<?> resCt = Resources.class.getConstructor(typeArgs);
		valueArgs = new Object[3];
		valueArgs[0] = assetMag;
		valueArgs[1] = res.getDisplayMetrics();
		valueArgs[2] = res.getConfiguration();
		res = (Resources) resCt.newInstance(valueArgs);
		return res;
	}
	
	
	
	
	
	/**
	 * 
	 * @param context
	 * @param dp
	 * @return
	 */
	public static int dp2px(Context context, int dp) {
		if (density == 0f) {
			density = context.getResources().getDisplayMetrics().density;
		}
		return (int) (dp * density + 0.5f);
	}

    /**
     *获取density
     * @param context
     * @return
     */
    public static float getZoomScale(Context context) {
        if(zoomScale > 0.1f){
            return zoomScale;
        }
        if (density == 0f) {
            density = context.getResources().getDisplayMetrics().density;
        }
        zoomScale = density/1.5f;
        return zoomScale;
    }

	
	/**
	 * 
	 * @param m_szLongID
	 * @return
	 */
	@SuppressLint("DefaultLocale")
	public static String getMD5Str(String m_szLongID) {
		MessageDigest m = null;
		try {
			m = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		m.update(m_szLongID.getBytes(), 0, m_szLongID.length());
		// get md5 bytes
		byte p_md5Data[] = m.digest();
		// create a hex string
		String m_szUniqueID = new String();
		for (int i = 0; i < p_md5Data.length; i++) {
			int b = (0xFF & p_md5Data[i]);
			// if it is a single digit, make sure it have 0 in front (proper
			// padding)
			if (b <= 0xF)
				m_szUniqueID += "0";
			// add number to string
			m_szUniqueID += Integer.toHexString(b);
		}
		// hex string to uppercase
		m_szUniqueID = m_szUniqueID.toUpperCase();
		return m_szUniqueID;
	}
	
	
	/**
	 * 
	 * @param permission
	 * @param path
	 */
	public static void chmod(String permission, String path) {
		try {
			String command = "chmod " + permission + " " + path;
			Runtime runtime = Runtime.getRuntime();
			runtime.exec(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static boolean isExistTargetPackage(Context context, String packageName) {
		try {
			context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
			return true;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 获取安卓手机设备型号
	 * 
	 * @return
	 */
	public static String getDeviceModel() {
		return Build.MODEL;
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
			Log.e("MyFeiGeActivity", "获取本地IP地址失败");
			ex.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 通过包名获取app的uid
	 */
	public static int getAppUid(Context context, String packname) {
		int uid = -1;
		PackageManager pm = context.getPackageManager();
		List<PackageInfo> packageInfos = pm.getInstalledPackages(0);
		for (PackageInfo packageInfo : packageInfos) {
			if (packageInfo.applicationInfo.packageName.equals(packname)) {// 判断包名是否相等
				uid = packageInfo.applicationInfo.uid;
				break;
			}
		}
		
		return uid;
	}
	
	// 关闭键盘
	public static void KeyBoardCancle(Context context) {
		View view = ((Activity) context).getWindow().peekDecorView();
		if (view != null) {
			InputMethodManager inputmanger = (InputMethodManager) context
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}
	
	// 打开软件盘
	public static void keyBoard(Context context) {
		InputMethodManager inputMethodManager = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	}
	
	/**
	 * sd卡是否安装
	 * 
	 * @return
	 */
	public static boolean isSDCardMounted() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}
	
	/**
	 * 截屏方法
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Bitmap shot(Activity context) {
		View view = context.getWindow().getDecorView();
		Display display = context.getWindowManager().getDefaultDisplay();
		view.layout(0, 0, display.getWidth(), display.getHeight());
		view.setDrawingCacheEnabled(true);// 允许当前窗口保存缓存信息，这样getDrawingCache()方法才会返回一个Bitmap
		Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache());
		return bmp;
	}
	
	/**
	 * 开打新的界面
	 * 
	 * @param source
	 * @param dest
	 * @param data
	 */
	public static void startActivity(Context source, Class<?> dest, Bundle data) {
		Intent intent = setDataToIntent(source, dest, data);
		source.startActivity(intent);
	}
	
	/**
	 * 开打新的界面
	 * 
	 * @param source
	 * @param dest
	 */
	public static void startActivity(Context source, Class<?> dest) {
		startActivity(source, dest, null);
	}
	
	/**
	 * 开打新的界面
	 * 
	 * @param source
	 * @param dest
	 * @param data
	 */
	public static void startActivityForRresult(Context source, Class<?> dest, Bundle data, int requestCode) {
		Intent intent = setDataToIntent(source, dest, data);
		((Activity) source).startActivityForResult(intent, requestCode);
	}
	
	/**
	 * 开打新的界面
	 * 
	 * @param source
	 * @param dest
	 */
	public static void startActivityForRresult(Context source, Class<?> dest, int requestCode) {
		startActivityForRresult(source, dest, null, requestCode);
	}
	
	/**
	 * 设置传递的参数
	 * 
	 * @param source
	 * @param dest
	 * @param data
	 * @return
	 */
	private static Intent setDataToIntent(Context source, Class<?> dest, Bundle data) {
		Intent intent = new Intent(source, dest);
		if (data != null) {
			intent.putExtras(data);
		}
		return intent;
	}
	
	public static RadioButton[] initButtons(LinearLayout mRadioGroup, Activity act) {
		int count = mRadioGroup.getChildCount();
		RadioButton[] mButtons = new RadioButton[count];
		for (int i = 0; i < (count + 1) / 2; i++) {
			int ii = i * 2;
			mButtons[i] = (RadioButton) (mRadioGroup.getChildAt(ii));
			mButtons[i].setTag(i);
			mButtons[i].setChecked(false);
			mButtons[i].setOnClickListener((android.view.View.OnClickListener) act);
		}
		return mButtons;
	}
	
	
	
	/**
	 * 以文件的形式返回外部存储的目录
	 */
	public static File getExternalStorageDirectory() {
		return android.os.Environment.getExternalStorageDirectory();
	}
	
	/**
	 * 以字符串形式返回外部存储的目录
	 */
	public static String getExternalStoragePathString() {
		return android.os.Environment.getExternalStorageDirectory().toString();
	}
	
	public static int randomNum(int[] array) {
		
		int index = (int) Math.floor(Math.random() * array.length);
		return array[index];
	}
	
	public static void recordFindPwd(Context context, String account) {
		String dateKey = account + "_date";
		String frequencyKey = account + "_fre";
		
		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dataFormat.format(new Date());
		
		SharedPreferences sp = context.getSharedPreferences("findpwd_limit", Context.MODE_APPEND);
		Editor edit = sp.edit();
		String oldDate = sp.getString(dateKey, "");
		
		if (date.equals(oldDate)) {
			int i = sp.getInt(frequencyKey, 0);
			i++;
			edit.putInt(frequencyKey, i);
		} else {
			edit.putInt(frequencyKey, 1);
			edit.putString(dateKey, date);
		}
		
		edit.commit();
	}
	
	public static boolean canFindPwd(Context context, String account) {
		String dateKey = account + "_date";
		String frequencyKey = account + "_fre";
		
		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dataFormat.format(new Date());
		
		SharedPreferences sp = context.getSharedPreferences("findpwd_limit", Context.MODE_APPEND);
		String oldDate = sp.getString(dateKey, "");
		
		if (!oldDate.equals(date)) {
			return true;
		}
		
		return sp.getInt(frequencyKey, 0) < 3;
	}
	
	/**
	 * 获取UUID2的值做MD5加密
	 * 
	 * @param context
	 * @return
	 */
	public static String getUUID2Mds(Context context) {
		String md5 = "";
		try {
			md5 += android.provider.Settings.System.getString(context.getContentResolver(), "android_id") + "_";
			md5 += DeviceInfo.getIMEI() + "_";
			String t_mac = CommonHelper.getLocalMacAddress(context);
			String tempMd5 = "";
			if (t_mac != null) {
				tempMd5 = t_mac.replaceAll(":", "").toUpperCase(Locale.getDefault());
			}
			md5 += tempMd5;
			byte[] btInput = md5.getBytes();
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			md5 = new String(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return md5;
	}
	
	private static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	
	
	/**
	 * 对网络连接状态进行判断
	 * 
	 * @return true, 可用； false， 不可用
	 */
	public static boolean isOpenNetwork(Context context) {
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connManager.getActiveNetworkInfo() != null) {
			return connManager.getActiveNetworkInfo().isAvailable();
		}
		
		return false;
	}
	/**
	 * 
	 * @param height
	 */
	public static void setScreenHeight(int height) {
		mScreenHeight = height;
	}

	/**
	 * 
	 * @return
	 */
	public static int getScreenHeight() {
		return mScreenHeight;
	}

	/**
	 * 
	 * @param width
	 */
	public static void setScreenWidth(int width) {
		mScreenWidth = width;
	}

	/**
	 * 
	 * @return
	 */
	public static int getScreenWidth() {
		return mScreenWidth;
	}

    /**
     * 对次数进行格式化
     */
    public static String formatTimes(long times) {
        String result = "";
        String timesStr = String.valueOf(times);
        int firstNum = Integer.parseInt(timesStr.substring(0,1));
        if(firstNum >= 5){
            firstNum = 5;
        }else{
            firstNum = 1;
        }
        int length = timesStr.length();
        switch (length){
            case 1:
                result = timesStr;
                break;
            case 2:
                result = firstNum + "0+";
                break;
            case 3:
                result = firstNum + "00+";
                break;
            case 4:
                result = firstNum + "千+";
                break;
            case 5:
                result = firstNum + "万+";
                break;
            case 6:
                result = firstNum + "0万+";
                break;
            case 7:
                result = firstNum + "000万+";
                break;
            case 8:
                result = firstNum + "0000万+";
                break;
            case 9:
                result = firstNum + "亿+";
                break;
        }
        return result;
    }
    
    public static List<String> getInstalledApps(Context activity){
    	ArrayList<String> appPkgList = new ArrayList<String>();
        PackageManager pckMan = activity.getPackageManager();
        List<PackageInfo> packs = pckMan.getInstalledPackages(0);
        int count = packs.size();
        for (int i = 0;i<count;i++){
        	appPkgList.add(packs.get(i).packageName);
        }
        return appPkgList;
    }

}
