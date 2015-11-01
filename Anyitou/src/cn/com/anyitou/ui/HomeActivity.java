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
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import cn.com.anyitou.MyApplication;
import cn.com.anyitou.R;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.listener.ScreenListener;
import cn.com.anyitou.listener.ScreenListener.ScreenStateListener;
import cn.com.anyitou.service.AnyitouService;
import cn.com.anyitou.ui.base.BaseFragmentActivity;
import cn.com.anyitou.ui.fragment.AboutUSFragment;
import cn.com.anyitou.ui.fragment.AccountSettingFragment;
import cn.com.anyitou.ui.fragment.HomeFragment;
import cn.com.anyitou.ui.fragment.InvestmentFragment;
import cn.com.anyitou.ui.fragment.MyFragment;
import cn.com.anyitou.utils.Log;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.CustomViewPager2;

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
	private CustomViewPager2 mViewPager;

	private FragmentManager mFragmentManager;
	int[] tabIds = { R.id.tab_home, R.id.tab_invest, R.id.tab_my,
			R.id.tab_account_setting, R.id.tab_about_us };

	private int checkId = tabIds[0];// 默认打开首页

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_home);
		super.onCreate(savedInstanceState);

		// 初始化控件
		initView();

		// 初始化事件
		initListener();
		
		registerHomeListener();
		intScreenListener();
	}

	/**
	 * init view
	 */
	private void initView() {
		mFragmentManager = getSupportFragmentManager();
		mViewPager = (CustomViewPager2) findViewById(R.id.viewpager);
		mTabIndicators = (RadioGroup) findViewById(R.id.tabIndicators);

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
						return mMyFragment = new MyFragment();
					}
					return mMyFragment;
				} else if (position == 3) {
					if(mAccountSettingFragment == null){
						mAccountSettingFragment = new AccountSettingFragment();
					}
					return mAccountSettingFragment;
				} else if (position == 4) {
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
						if(checkedId == checkId){
							return;
						}
						if(checkedId == tabIds[2] && MyApplication.getInstance().getCurrentUser() == null){
							ToastUtils.showToast(HomeActivity.this, "请先登录");
							Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
							intent.putExtra("isFromMy", true);
							startActivityForResult(intent, 123);
							((RadioButton)findViewById(checkId)).setChecked(true);
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

	}

	


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == 123){
			checkId = tabIds[2];
			((RadioButton)findViewById(checkId)).setChecked(true);
			mViewPager.setCurrentItem(2, false);
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
				ToastUtils.showToast(this, "再按一次进行退出");
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
		unregisterHomeListener();
		screenListener.unregisterListener();
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


}
