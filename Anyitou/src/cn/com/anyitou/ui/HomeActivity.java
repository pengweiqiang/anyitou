package cn.com.anyitou.ui;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import cn.com.anyitou.MyApplication;
import cn.com.anyitou.R;
import cn.com.anyitou.api.ApiHomeUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.listener.ScreenListener;
import cn.com.anyitou.listener.ScreenListener.ScreenStateListener;
import cn.com.anyitou.service.AnyitouService;
import cn.com.anyitou.ui.base.BaseFragmentActivity;
import cn.com.anyitou.ui.fragment.AboutUSFragment;
import cn.com.anyitou.ui.fragment.AccountSettingFragment;
import cn.com.anyitou.ui.fragment.HomeFragment;
import cn.com.anyitou.ui.fragment.InvestmentFragment;
import cn.com.anyitou.ui.fragment.MyFragment;
import cn.com.anyitou.utils.DeviceInfo;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.Log;
import cn.com.anyitou.utils.ShareUtil;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.CustomViewPager2;
import cn.com.anyitou.views.LoadingDialog;
import cn.com.anyitou.views.MyPopupWindow;
import cn.com.gson.JsonObject;
import cn.jpush.android.api.JPushInterface;

import com.umeng.update.UmengUpdateAgent;

/**
 * 首页
 * 
 * @author will
 * 
 */
public class HomeActivity extends BaseFragmentActivity implements OnPageChangeListener{

	// 首页
	private HomeFragment mHomeFragment;
	// 投资
	private InvestmentFragment mInvestmentFragment;
	// 我的
	private MyFragment mMyFragment;
	// 账户设置
	private AccountSettingFragment mAccountSettingFragment;
	// 关于我们
	private AboutUSFragment mAboutUsFragment;

	private FragmentPagerAdapter mAdapter;

	private RadioGroup mTabIndicators;
	private RadioButton mRbShare,mRbMore;
	private CustomViewPager2 mViewPager;

	private FragmentManager mFragmentManager;
	int[] tabIds = { R.id.tab_home, R.id.tab_invest, R.id.tab_my,
			R.id.tab_share, R.id.tab_more };

	private int checkId = tabIds[0];// 默认打开首页

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_home);
		super.onCreate(savedInstanceState);
		
		// 初始化控件
		initView();

		// 初始化事件
		initListener();
		
//		registerHomeListener();
//		intScreenListener();
		UmengUpdateAgent.update(this);
		
		//jpush
//		JPushInterface.setDebugMode(ReqUrls.ISDEBUG);
	    JPushInterface.init(this);
	    
//	    String regId = JPushInterface.getRegistrationID(application);
//	    System.out.println("regId:"+regId);
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		
		int checkIndex = intent.getIntExtra("checkIndex", 0);
		mViewPager.setCurrentItem(checkIndex);
		
		String type = intent.getStringExtra("type");
		if(!StringUtils.isEmpty(type) && "login".equals(type)){
			mViewPager.setCurrentItem(2);
		}
		
		
	}
	/**
	 * init view
	 */
	private void initView() {
		mFragmentManager = getSupportFragmentManager();
		mViewPager = (CustomViewPager2) findViewById(R.id.viewpager);
		mTabIndicators = (RadioGroup) findViewById(R.id.tabIndicators);
		mRbShare = (RadioButton) findViewById(tabIds[3]);
		mRbMore = (RadioButton) findViewById(tabIds[4]);

		Intent intent = getIntent();
		if(intent!=null){
			onNewIntent(intent);
		}
		
	}

	/**
	 * init Listener
	 */
	private void initListener() {

		mAdapter = new FragmentPagerAdapter(mFragmentManager) {

			@Override
			public int getCount() {
				return tabIds.length;
			}

			@Override
			public Fragment getItem(int position) {
				if (position == 0) {
					if(mHomeFragment==null){
						mHomeFragment = new HomeFragment();
					}
					return mHomeFragment;
				} else if (position == 1) {
					if(mInvestmentFragment == null){
						mInvestmentFragment = new InvestmentFragment();
					}
					return mInvestmentFragment;
				} else if (position == 2) {
					if(mMyFragment == null){
						mMyFragment = new MyFragment();
					}
					return mMyFragment;
				} else if (position == 4) {//分享
					if(mAccountSettingFragment == null){
						mAccountSettingFragment = new AccountSettingFragment();
					}
					return mAccountSettingFragment;
				} else if (position == 3) {//更多
					if(mAboutUsFragment ==null){
						mAboutUsFragment = new AboutUSFragment();
					}
					return mAboutUsFragment;
				}
				return null;
			}
		};
		mViewPager.setAdapter(mAdapter);
		mViewPager.setOnPageChangeListener(this);

		mViewPager.setOffscreenPageLimit(5);
		// 实现模块切换
		mTabIndicators
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						mRbMore.setSelected(false);
//						mRbShare.setSelected(false);
						if(checkedId == checkId){
							return;
						}
						if(checkedId == tabIds[3] /*|| checkedId == tabIds[4]*/){//分享  //更多
//							shareFriend(group);
//							((RadioButton)findViewById(checkId)).setChecked(true);
							return;
						}
						Log.e("HomeActivity", "onCheckedChanged");
						if(checkedId == tabIds[2] && MyApplication.getInstance().getCurrentUser() == null){
							((RadioButton)findViewById(checkId)).setChecked(true);
							ToastUtils.showToast(HomeActivity.this, "请先登录");
							Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
							intent.putExtra("isFromMy", true);
							startActivityForResult(intent, 1);
//							startActivity(intent);
						}else{
							for (int i = 0; i < tabIds.length; i++) {
								if (checkedId == tabIds[i]) {
									checkId = tabIds[i];
									mViewPager.setCurrentItem(i, false);
									break;
								}
							}
						}
					}
				});
		mRbShare.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
//				checkId = tabIds[3];
//				mRbShare.setSelected(true);
				((RadioButton)findViewById(checkId)).setChecked(true);
				shareFriend(mTabIndicators);
			}
		});
//		mRbMore.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				((RadioButton)findViewById(checkId)).setChecked(false);
////				checkId = tabIds[4];
//				mRbMore.setSelected(true);
//				showMoreWindow(mRbMore);
//			}
//		});

	}

//	@Override
//	protected void onStop() {
//		if(mTabIndicators.getCheckedRadioButtonId()!=checkId){
//			setCurrentTabId(checkId);
//		}
//		super.onStop();
//	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == 2){
			if(data !=null && data.getBooleanExtra("islogin", false)){
				checkId = tabIds[2];
				((RadioButton)findViewById(checkId)).setChecked(true);
				mViewPager.setCurrentItem(2, false);
			}
		}
	}




	private boolean isExit = false;
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			isExit = false;
		}
	};
	// 退出操作
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isExit == false) {
				isExit = true;
				handler.sendEmptyMessageDelayed(0, 3000);
				ToastUtils.showToast(this, "再按一次进行退出",0,1);
				return true;
			} else {
				AppManager.getAppManager().finishAllActivity();
				Intent stopService = new Intent(HomeActivity.this,AnyitouService.class);
				stopService(stopService);
				return false;
			}
		}
		return true;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	@Override
	public void onPageSelected(int i) {
		if(i==3||i==4){
			return;
		}
		setCurrentTabId(tabIds[i]);
	}
	
	/*
	 * 切換至指定 tabId 的模块
	 */
	public void setCurrentTabId(int tabId) {
		((RadioButton) mTabIndicators.findViewById(tabId)).setChecked(true);
	}
	
	@Override
	protected void onDestroy() {
//		unregisterHomeListener();
//		screenListener.unregisterListener();
		super.onDestroy();
	}
	ScreenListener screenListener = new ScreenListener(this);
	private void intScreenListener(){
		
		screenListener.begin(new ScreenStateListener() {

            @Override
            public void onUserPresent() {
                Log.e("onUserPresent", "onUserPresent");
            }

            @Override
            public void onScreenOn() {
                Log.e("onScreenOn", "onScreenOn");
            }

            @Override
            public void onScreenOff() {
                Log.e("onScreenOff", "onScreenOff");
                application.isLock = false;
            }
        });
	}
	
	
	//share   start
	
	private PopupWindow popupWindow;
	private TextView cancleTextView;
	private View mShareWeixin,mShareWeixinLine,mShareWeibo,mShareQQ;
	
	cn.com.anyitou.utils.ShareUtil shareUtil;
	
	@SuppressWarnings("deprecation")
	public void shareFriend(View view){
		if(application.getCurrentUser()!=null){
			if(StringUtils.isEmpty(inviteUrl)){
				getInviteLink(view);
			}else{
				initShareView(view);
			}
		}else{
			initShareView(view);
		}
		
		
	}
	private void initShareView(View view){
		shareUtil = new ShareUtil(HomeActivity.this);
		popupWindow = MyPopupWindow.getPopupWindow(R.layout.invite_friends_popupwindow, HomeActivity.this);
		View popupWindowView = popupWindow.getContentView();
		cancleTextView = (TextView) popupWindowView.findViewById(R.id.friendsCancleTextView);
		mShareWeixin = popupWindowView.findViewById(R.id.share_wx_friend);
		mShareWeixinLine = popupWindowView.findViewById(R.id.share_wx_line);
		mShareWeibo = popupWindowView.findViewById(R.id.share_weibo_sina_friend);
		mShareQQ = popupWindowView.findViewById(R.id.share_qq_friend);
		
		mShareWeixin.setOnClickListener(shareClickListener);
		mShareWeixinLine.setOnClickListener(shareClickListener);
		mShareWeibo.setOnClickListener(shareClickListener);
		mShareQQ.setOnClickListener(shareClickListener);
		cancleTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				// 关闭popupWindow.dismiss();
				popupWindow.dismiss();
			}
		});
		popupWindow.showAtLocation(view, Gravity.BOTTOM, 0,0);
	}
	
	OnClickListener shareClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.share_wx_friend:
				shareUtil.share(0,"",inviteUrl);
				break;
			case R.id.share_wx_line:
				shareUtil.share(1,"",inviteUrl);
				break;
			case R.id.share_weibo_sina_friend:
				shareUtil.share(5,"",inviteUrl);
				break;
			case R.id.share_qq_friend:
				shareUtil.share(3,"",inviteUrl);
				break;

			default:
				break;
			}
			popupWindow.dismiss();
		}
	};
	
	
	//share end
	
	//more start
	private View mBtnAccountSetting,mBtnAboutUs;
	@SuppressWarnings("deprecation")
	public void showMoreWindow(View view){
		
		popupWindow = MyPopupWindow.getPopupWindow(R.layout.more_popupwindow, HomeActivity.this,0);
		View popupWindowView = popupWindow.getContentView();
//		LayoutParams params = popupWindowView.getLayoutParams();
//		params.width = DeviceInfo.getDisplayMetricsWidth(HomeActivity.this)/4;
//		params.height = DeviceInfo.getDisplayMetricsWidth(HomeActivity.this)/4;
//		popupWindowView.setLayoutParams(params);
		int width = DeviceInfo.getDisplayMetricsWidth(HomeActivity.this)/5;
		if(width<100){
			width = width +10;
		}
		popupWindow.setWidth(width);
		popupWindow.setHeight(DeviceInfo.dp2px(HomeActivity.this, 120));
		mBtnAccountSetting = popupWindowView.findViewById(R.id.tab_account_setting);
		mBtnAboutUs = popupWindowView.findViewById(R.id.tab_about_us);
		
		mBtnAccountSetting.setOnClickListener(moreClickListener);
		mBtnAboutUs.setOnClickListener(moreClickListener);
		
		
//		popupWindow.showAsDropDown(view);
		int[] location = new int[2];
		view.getLocationOnScreen(location);
         
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, location[0], location[1]-popupWindow.getHeight()-DeviceInfo.dp2px(HomeActivity.this, 12));
        mRbShare.setSelected(false);
        mRbMore.setSelected(true);
        popupWindow.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				MyPopupWindow.setBackgroundAlpha(HomeActivity.this, 1f);
				if(checkId == tabIds[4]){
					mRbMore.setSelected(true);
				}else{
					((RadioButton)findViewById(checkId)).setChecked(true);
				}
			}
		});
//		popupWindow.showAtLocation(view, Gravity.BOTTOM, 0,0);
//		popupWindow.showAtLocation(findViewById(R.id.bottom), Gravity.TOP, 0, 0);
		
	}
	OnClickListener moreClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
//			mRbShare.setSelected(false);
			switch (v.getId()) {
			case R.id.tab_account_setting:
				checkId = tabIds[4];
				mViewPager.setCurrentItem(3, false);
				break;
			case R.id.tab_about_us:
				checkId = tabIds[4];
				mViewPager.setCurrentItem(4, false);
				break;

			default:
				
				break;
			}
			popupWindow.dismiss();
			
		}
	};
	//more end
	private String inviteUrl = "";//分享url地址
	private void getInviteLink(final View view){
		final LoadingDialog loadingDialog = new LoadingDialog(HomeActivity.this);
		loadingDialog.show();
		ApiHomeUtils.inviteLinks(HomeActivity.this, new RequestCallback() {
			
			@Override
			public void execute(ParseModel parseModel) {
				loadingDialog.cancel();
				if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
					JsonObject data = parseModel.getData().getAsJsonObject();
					inviteUrl = data.get("invite_url").getAsString();
				}
				initShareView(view);
			}
		});
	}


}
