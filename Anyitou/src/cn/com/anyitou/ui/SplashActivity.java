package cn.com.anyitou.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import cn.com.GlobalConfig;
import cn.com.anyitou.R;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.commons.Constant;
import cn.com.anyitou.service.AnyitouService;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.utils.DeviceInfo;
import cn.com.anyitou.utils.SharePreferenceManager;
import cn.com.anyitou.utils.StringUtils;

/**
 * 加载页
 * 
 * @author will
 * 
 */
public class SplashActivity extends BaseActivity {

	private long delayTime = 1500;
	private ImageView mImageView;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.splash);
		super.onCreate(savedInstanceState);
		DeviceInfo.setContext(mContext);
		String nowVersionName = DeviceInfo.getVersionName();
		String appVersionName = (String) SharePreferenceManager.getSharePreferenceValue(
				this, Constant.FILE_NAME, "appVersionName", "");
		GlobalConfig.VERSION_NAME_V = nowVersionName;
		
		Intent startService = new Intent(mContext,AnyitouService.class);
		startService(startService);
		
		 LayoutParams imageLayoutParams = mImageView.getLayoutParams();
		 imageLayoutParams.width = DeviceInfo.getScreenWidth(this);
		 imageLayoutParams.height = (int) (imageLayoutParams.width * 1.0 / 720*1280); 
		 mImageView.setLayoutParams(imageLayoutParams);
		 
		if (StringUtils.isEmpty(appVersionName) || !nowVersionName.equals(appVersionName)) {// 首次打开app,进入引导页面
			// findViewById(R.id.loading).setVisibility(View.VISIBLE);
			// application.getBoot(handler);
			SharePreferenceManager.saveBatchSharedPreference(application,
					Constant.FILE_NAME, "appVersionName", nowVersionName);
			
			new Handler().postDelayed(new Runnable() {
				public void run() {
					startNextActivity(WelcomeViewPageActivity.class);
				}
			}, delayTime);
			
		}else{
			if(application.getCurrentUser()!=null){
				String gesturePwds = (String)SharePreferenceManager.getSharePreferenceValue(mContext, Constant.FILE_NAME, application.getCurrentUser().getUsername()+Constant.GESTURE_PWD, "");
				if(!StringUtils.isEmpty(gesturePwds)){
					
					String gpwds [] = gesturePwds.split(",");
					long distance = System.currentTimeMillis()-Long.valueOf(gpwds[0]);
					if(distance>1*60*60*1000){
						// 使用Handler的postDelayed方法，2秒后执行跳转到MainActivity
						new Handler().postDelayed(new Runnable() {
							public void run() {
								startNextActivity(GestureLockActivity.class);
							} 
						}, 1000);
						return;
					}
					
				}
			}
			// 使用Handler的postDelayed方法，2秒后执行跳转到HomeActivity
			new Handler().postDelayed(new Runnable() {
				public void run() {
					startNextActivity(HomeActivity.class);
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
		mImageView = (ImageView) findViewById(R.id.splash);
	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub

	}

}
