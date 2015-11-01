package cn.com.anyitou.ui.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import cn.com.anyitou.MyApplication;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.listener.HomeListener;
import cn.com.anyitou.listener.HomeListener.OnHomePressedListener;
import cn.com.anyitou.ui.GestureLockActivity;
import cn.com.anyitou.utils.Log;
import cn.com.anyitou.utils.StringUtils;

import com.umeng.analytics.MobclickAgent;

public class BaseFragmentActivity extends FragmentActivity {

	protected MyApplication application;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
//		getWindow()
//				.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		AppManager.getAppManager().addActivity(this);
		application = MyApplication.getInstance();
		
		
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//			setTranslucentStatus(true);
//		}
//
//		SystemBarTintManager tintManager = new SystemBarTintManager(this);
//		tintManager.setStatusBarTintEnabled(true);
//		tintManager.setTintColor(getResources().getColor(R.color.app_bg_color));
////		SystemBarConfig config = tintManager.getConfig();
////		map.setPadding(0, config.getPixelInsetTop(), config.getPixelInsetRight(), config.getPixelInsetBottom());
	}

	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		return super.onCreateView(name, context, attrs);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		AppManager.getAppManager().finishActivity(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		application.classLast = this.getClass();
		if(application.getCurrentUser()!=null && !StringUtils.isEmpty(application.gesturePwd) 
				&& !application.isLock){
			Intent intent = new Intent(this,GestureLockActivity.class);
			intent.putExtra("type", 1);//1解锁
			startActivity(intent);
		}
		MobclickAgent.onResume(this);
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

}
