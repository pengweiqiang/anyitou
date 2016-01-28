package cn.com.anyitou.ui;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import cn.com.GlobalConfig;
import cn.com.anyitou.R;
import cn.com.anyitou.api.constant.Push2Page;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.commons.Constant;
import cn.com.anyitou.entity.DebtAssignment;
import cn.com.anyitou.entity.Investment;
import cn.com.anyitou.entity.User;
import cn.com.anyitou.service.AnyitouService;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.ui.fragment.InvestmentFragment;
import cn.com.anyitou.ui.fragment.MyCouponFragment;
import cn.com.anyitou.utils.DeviceInfo;
import cn.com.anyitou.utils.SharePreferenceManager;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.TokenUtil.InitTokenCallBack;
import cn.jpush.android.api.JPushInterface;

/**
 * 加载页
 * 
 * @author will
 * 
 */
public class SplashActivity extends BaseActivity {

	private long delayTime = 500;
	private ImageView mImageView;
	String appVersionName = "";
	String nowVersionName = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.splash);
		super.onCreate(savedInstanceState);
		DeviceInfo.setContext(mContext);
		nowVersionName = DeviceInfo.getVersionName();
		appVersionName = (String) SharePreferenceManager.getSharePreferenceValue(
				this, Constant.FILE_NAME, "appVersionName", "");
		GlobalConfig.VERSION_NAME_V = nowVersionName;
		
		setImageParams();
		
		Intent startService = new Intent(mContext,AnyitouService.class);
		startService(startService);
		
		application.initToken(new InitTokenCallBack() {
			@Override
			public void initTokenSuccess() {
				toNextActivity();
			}

			@Override
			public void initTokenError() {
				
			}
		});
		
	}
	
	

	@Override
	protected void onResume() {
		super.onResume();
	}
	
	private void toNextActivity(){
		if (StringUtils.isEmpty(appVersionName) || !nowVersionName.equals(appVersionName)) {// 首次打开app,进入引导页面
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
					if(distance>1*60*60*1000){//大于一个小时进入解锁页面
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
		Bundle bundle = this.getIntent().getExtras();
		if(bundle == null){
			Intent intent = new Intent(SplashActivity.this, cls);
			startActivity(intent);
			overridePendingTransition(R.anim.activity_right_in,
					R.anim.activity_left_out);
			AppManager.getAppManager().finishActivity();
		}else{
			startPushActivity();
			
		}
	}

	@Override
	public void initView() {
		mImageView = (ImageView) findViewById(R.id.splash);
		
		
	}
	
	private void setImageParams(){
		LayoutParams imageLayoutParams = mImageView.getLayoutParams();
		imageLayoutParams.width = DeviceInfo.getScreenWidth(this);
		imageLayoutParams.height = (int) (imageLayoutParams.width * 1.0 / 720*1280); 
		mImageView.setLayoutParams(imageLayoutParams);
	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub

	}
	
	private void startPushActivity(){
		Bundle bunlde = this.getIntent().getExtras();
		Intent i = new Intent();
		if(bunlde!=null){
			String toPage = "";
			JSONObject json ;
			try {
				json = new JSONObject(bunlde.getString(JPushInterface.EXTRA_EXTRA));
				toPage = json.getString("toPage");
			
				if(Push2Page.MainActivity.getToPage().equalsIgnoreCase(toPage)){//首页
					i.setClass(mContext, HomeActivity.class);
				}else if(Push2Page.InvestmentDetailActivity.getToPage().equalsIgnoreCase(toPage)){//项目详情
					Investment invest = new Investment();
					String id = json.getString("id");
					invest.setId(id);
					i.putExtra("invest", invest);
					i.putExtra("type", 1);
					i.setClass(mContext, InVestmentDetailActivity.class);
				}else if(Push2Page.DebtDetailActivity.getToPage().equalsIgnoreCase(toPage)){//债权详情
					DebtAssignment debt = new DebtAssignment();
					String id = json.getString("id");
					debt.setId(id);
					i.putExtra("type", 2);
					i.putExtra("debt", debt);
					i.setClass(mContext, InVestmentDetailActivity.class);
				}else if(Push2Page.MessagesActivity.getToPage().equalsIgnoreCase(toPage)){//消息中心
					i.setClass(mContext, MessagesActivity.class);
				}else if(Push2Page.MyActivity.getToPage().equalsIgnoreCase(toPage)){//个人中心
					i.setClass(mContext, HomeActivity.class);
					i.putExtra("checkIndex", 2);
				}else if(Push2Page.MyCouponActivity.getToPage().equalsIgnoreCase(toPage)){//我的优惠券
					User user = application.getCurrentUser();
					if(user!=null){
						i.setClass(mContext, MyCouponFragment.class);
					}else{
						i.setClass(mContext, LoginActivity.class);
					}
				}else if(Push2Page.ProjectActivity.getToPage().equalsIgnoreCase(toPage)){//项目列表
					i.setClass(mContext, HomeActivity.class);
					i.putExtra("checkIndex", 1);
				}else {
					i.setClass(mContext, HomeActivity.class);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}	
	    	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
	    	startActivity(i);
	    	overridePendingTransition(R.anim.activity_right_in,
					R.anim.activity_left_out);
			AppManager.getAppManager().finishActivity();
		}
	}

}
