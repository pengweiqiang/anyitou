package cn.com.anyitou.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import cn.com.anyitou.R;

import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.commons.Constant;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.utils.SharePreferenceManager;
import cn.com.anyitou.utils.StringUtils;

/**
 * 加载页
 * 
 * @author will
 * 
 */
public class SplashActivity extends BaseActivity {

	private long delayTime = 2000;
//	private ImageView mImageView;
	private boolean isFirst = false;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.splash);
		super.onCreate(savedInstanceState);
//		tintManager.setTintColor(getResources().getColor(R.color.white));
		isFirst = (Boolean) SharePreferenceManager.getSharePreferenceValue(
				this, Constant.FILE_NAME, "isFirst", true);
		/*
		 * LayoutParams imageLayoutParams = mImageView.getLayoutParams();
		 * imageLayoutParams.width = DeviceInfo.getScreenWidth(this);
		 * imageLayoutParams.height = (int) (imageLayoutParams.width * 1.0 / 553
		 * * 297); mImageView.setLayoutParams(imageLayoutParams);
		 */
		if (isFirst) {// 首次打开app,进入引导页面
			// findViewById(R.id.loading).setVisibility(View.VISIBLE);
			// application.getBoot(handler);
			SharePreferenceManager.saveBatchSharedPreference(application,
					Constant.FILE_NAME, "isFirst", false);
			
			new Handler().postDelayed(new Runnable() {
				public void run() {
					startNextActivity(WelcomeViewPageActivity.class);
				}
			}, delayTime);
			
		}else{
			if(application.getCurrentUser()!=null){
				String gesturePwd = (String)SharePreferenceManager.getSharePreferenceValue(mContext, Constant.FILE_NAME, application.getCurrentUser().getUser_name()+Constant.GESTURE_PWD, "");
				if(!StringUtils.isEmpty(gesturePwd)){
					// 使用Handler的postDelayed方法，2秒后执行跳转到MainActivity
					new Handler().postDelayed(new Runnable() {
						public void run() {
							startNextActivity(GestureLockActivity.class);
						} 
					}, 1000);
					
					return;
				}
			}
			// 使用Handler的postDelayed方法，2秒后执行跳转到MainActivity
			new Handler().postDelayed(new Runnable() {
				public void run() {
					startNextActivity(MainActivity.class);
				} 
			}, delayTime);
		}

	}

	private void startNextActivity(Class<?> cls) {
		Intent intent = new Intent(SplashActivity.this, cls);
		startActivity(intent);
		overridePendingTransition(R.anim.activity_right_in,
				R.anim.activity_left_out);
		AppManager.getAppManager().finishActivity();
	}

	@Override
	public void initView() {
//		mImageView = (ImageView) findViewById(R.id.splash);
	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub

	}

}
