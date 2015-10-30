package cn.com.anyitou.ui.base;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import cn.com.anyitou.MyApplication;
import cn.com.anyitou.R;

import cn.com.GlobalConfig;
import cn.com.anyitou.api.constant.ReqUrls;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.commons.Constant;
import cn.com.anyitou.entity.User;
import cn.com.anyitou.listener.HomeListener;
import cn.com.anyitou.listener.HomeListener.OnHomePressedListener;
import cn.com.anyitou.ui.GestureLockActivity;
import cn.com.anyitou.ui.SplashActivity;
import cn.com.anyitou.utils.JsonUtils;
import cn.com.anyitou.utils.Log;
import cn.com.anyitou.utils.SharePreferenceManager;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.views.SystemBarTintManager;
import cn.com.anyitou.views.SystemBarTintManager.SystemBarConfig;
import com.umeng.analytics.MobclickAgent;

public abstract class BaseActivity extends Activity implements OnClickListener {

	protected static final String TAG = "BaseActivity";
	protected MyApplication application;
	protected Context mContext;
	public InputMethodManager manager;
//	protected SystemBarTintManager tintManager;
	@SuppressLint("InlinedApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		AppManager.getAppManager().addActivity(this);

		initView();
		mContext = this;
		initListener();
		application = MyApplication.getInstance();
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//			setTranslucentStatus(true);
//		}
//
//		tintManager = new SystemBarTintManager(this);
//		tintManager.setStatusBarTintEnabled(true);
////		tintManager.setStatusBarTintResource(R.color.app_title_color);
//		tintManager.setTintColor(getResources().getColor(R.color.app_bg_color));
////		SystemBarConfig config = tintManager.getConfig();
////		map.setPadding(0, config.getPixelInsetTop(), config.getPixelInsetRight(), config.getPixelInsetBottom());
	}

	/**
	 * 左返回按钮点击事件
	 */
	protected void onLeftBackClick() {
		AppManager.getAppManager().finishActivity(this);
	}

	@Override
	protected void onResume() {
		if(application.getCurrentUser()!=null && !StringUtils.isEmpty(application.gesturePwd) 
				&& !application.isLock && this.getClass() != SplashActivity.class 
				&& this.getClass() != GestureLockActivity.class){
			Intent intent = new Intent(mContext,GestureLockActivity.class);
			intent.putExtra("type", 1);//1解锁
			startActivity(intent);
		}
		if(this.getClass() != GestureLockActivity.class && this.getClass() != SplashActivity.class){
			application.classLast = this.getClass();
		}
		super.onResume();
		MobclickAgent.onPageStart(this.getClass().getName());
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd(this.getClass().getName());
		MobclickAgent.onPause(this);
		
		
	}

	@Override
	protected void onDestroy() {
		dismissWaitingDialog();
		AppManager.getAppManager().finishActivity(this);
		super.onDestroy();
	}

	protected void startActivity(Class<?> aTargetClass, Bundle aBundle) {
		Intent i = new Intent(this, aTargetClass);
		if (aBundle != null) {
			i.putExtras(aBundle);
		}
		startActivity(i);
	}

	protected void dismissWaitingDialog() {
	}

	public void showToast(String aMessage) {
		Toast.makeText(this, aMessage, Toast.LENGTH_SHORT).show();
	}

	public void showToast(int resId) {
		Toast.makeText(getApplicationContext(), resId, Toast.LENGTH_SHORT)
				.show();
	}

	public void startActivity(Class<?> cls) {
		Intent intent = new Intent(mContext, cls);
		startActivity(intent);
	}

	/**
	 * 隐藏键盘
	 */
	protected void hideInputMethod() {
		View view = getCurrentFocus();
		if (view != null) {
			((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
					.hideSoftInputFromWindow(view.getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	}

	@Override
	public void onClick(View v) {
	}

	/**
	 * 初始化控件
	 */
	public abstract void initView();

	/**
	 * 初始化控件的事件
	 */
	public abstract void initListener();

	/**
	 * 展示空白页面
	 * 
	 * @param view
	 * @param tipStr
	 * @param goStr
	 * @param imageId
	 * @param emptyClick
	 */
	/*
	 * public void showEmpty(View view,int tipId,int goId,int
	 * imageId,OnClickListener emptyClick){ ImageView imageView =
	 * (ImageView)view.findViewById(R.id.empty_imageView);
	 * imageView.setImageDrawable(this.getResources().getDrawable(imageId));
	 * TextView textTip = (TextView)view.findViewById(R.id.empty_text_tip);
	 * textTip.setText(getResources().getString(tipId)); TextView textGo =
	 * (TextView)view.findViewById(R.id.empty_text_go);
	 * textGo.setText(getResources().getString(goId));
	 * 
	 * textGo.setOnClickListener(emptyClick); }
	 */

	/*
	 * public void showEmpty(View view,String tip,int goId,int
	 * imageId,OnClickListener emptyClick){ ImageView imageView =
	 * (ImageView)view.findViewById(R.id.empty_imageView);
	 * imageView.setImageDrawable(this.getResources().getDrawable(imageId));
	 * TextView textTip = (TextView)view.findViewById(R.id.empty_text_tip);
	 * textTip.setText(tip); TextView textGo =
	 * (TextView)view.findViewById(R.id.empty_text_go);
	 * textGo.setText(getResources().getString(goId));
	 * 
	 * textGo.setOnClickListener(emptyClick); }
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (getCurrentFocus() != null
					&& getCurrentFocus().getWindowToken() != null) {
				manager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
		return super.onTouchEvent(event);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_HOME) {
			application.isLock = false;
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	/**
	 * 注册Home键的监听
	 */
	HomeListener mHomeWatcher;
	public void registerHomeListener() {
		mHomeWatcher = new HomeListener(this); 
		mHomeWatcher.setOnHomePressedListener(new OnHomePressedListener() {
			
			@Override
			public void onHomePressed() {
				Log.i("xsl", "0000000000000");
				application.isLock =false;
			}
			
			@Override
			public void onHomeLongPressed() {
				Log.i("xsl", "0000000000000");
			}
		});
		mHomeWatcher.startWatch();
	}
	public void unregisterHomeListener(){
		mHomeWatcher.stopWatch();
	}

	/**
	 * 退出登录
	 */
	public void logOut() {
		SharePreferenceManager.saveBatchSharedPreference(mContext,
				Constant.FILE_NAME, "myassets", "");
		SharePreferenceManager.saveBatchSharedPreference(mContext,
				Constant.FILE_NAME, "user", "");
		SharePreferenceManager.saveBatchSharedPreference(mContext,
				Constant.FILE_NAME, ReqUrls.ACCESS_TOKEN, "");
		SharePreferenceManager.saveBatchSharedPreference(mContext,
				Constant.FILE_NAME, ReqUrls.REFRESH_TOKEN, "");
		GlobalConfig.ACCESS_TOKEN = "";
		GlobalConfig.REFRESH_TOKEN = "";
		application.setUser(null);
	}

	/**
	 * 登录成功
	 * 
	 * @param token
	 * @param userName
	 * @param passWord
	 */
	public void logined(String token,String refreshToken, User user) {
		if (!StringUtils.isEmpty(token)) {
			GlobalConfig.ACCESS_TOKEN = token;
			
			long currentTime = System.currentTimeMillis();
			SharePreferenceManager.saveBatchSharedPreference(mContext,
					Constant.FILE_NAME, ReqUrls.ACCESS_TOKEN, token + "_"
							+ currentTime);
		}
		if(!StringUtils.isEmpty(refreshToken)){
			GlobalConfig.REFRESH_TOKEN = refreshToken;
			
			long currentTime = System.currentTimeMillis();
			SharePreferenceManager.saveBatchSharedPreference(mContext,
					Constant.FILE_NAME, ReqUrls.REFRESH_TOKEN, refreshToken + "_"
							+ currentTime);
		}
		if (user != null) {
			application.setUser(user);
			SharePreferenceManager.saveBatchSharedPreference(mContext,
					Constant.FILE_NAME, "user", JsonUtils.toJson(user));
		}

	}

	public boolean isLogined() {
		if (application.getCurrentUser() != null) {
			return true;
		}
		return false;

	}
	
	
	
    
    @TargetApi(19) 
	private void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}

}
