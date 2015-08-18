package cn.com.anyitou.ui;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar.LayoutParams;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import cn.com.anyitou.R;

import cn.com.gson.reflect.TypeToken;
import cn.com.anyitou.adapters.HomePagerAdapter;
import cn.com.anyitou.adapters.LeftMenuAdapter;
import cn.com.anyitou.api.ApiHomeUtils;
import cn.com.anyitou.api.ApiInvestUtils;
import cn.com.anyitou.api.ApiUserUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.commons.Constant;
import cn.com.anyitou.entity.Investment;
import cn.com.anyitou.entity.LableValue;
import cn.com.anyitou.entity.Menu;
import cn.com.anyitou.entity.MyAssets;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.entity.Share;
import cn.com.anyitou.entity.User;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.utils.HttpConnectionUtil;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.JsonUtils;
import cn.com.anyitou.utils.SharePreferenceManager;
import cn.com.anyitou.utils.ShareUtil;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.LoadingDialog;
import cn.com.anyitou.views.PageIndicator;
import cn.com.anyitou.views.SlidingMenu;
import cn.com.anyitou.views.SpringScrollView;
import com.umeng.update.UmengUpdateAgent;

@SuppressLint("NewApi")
public class MainActivity extends BaseActivity {

	private SlidingMenu mMenu;
	private View mRightView;
	private TextView mCallCustom;
	private ListView mLeftListView;
	private LeftMenuAdapter mLeftMenuAdapter;
	private ActionBar mActionBar;

	private HomePagerAdapter mHomePagerAdater;
	private cn.com.anyitou.views.CustomViewPager mHomeViewPager;
	PageIndicator mIndicator;
	private LoadingDialog loadingDialog ;

	private TextView mTvUserName;
	private ImageView mImageHeader;
	private View mTopUser;

	private User user;

	// 投资列表数据
	private List<Investment> investLists = new ArrayList<Investment>();
	private View mActivityHome, mSetting, mMyAsset;

	// 设置 start

	private View mGesture, mUpdatePwd, mLogin, mCustomService, mInviteFriends,
			mAboutUs;
	private TextView mLoginTitle, friendsCancleTextView;
	private PopupWindow popupWindow;
	private View mViewShareWxFriend,mViewShareWxLine,mViewShareQQ,mViewShareWeibo,mViewShareSina;

	ShareUtil shareUtil;
	// 设置 end

	// 我的资产 start
	private MyAssets myAssets;
	private View mBtnToCash, mBtnToRechange, mBtnPaymentPlan, mBtnMyInvest,
			mBtnMyCard;
//	private TextView mTvlabel1, mTvlabel2, mTvlabel3, mTvlabel4, mTvlabel5,
//			mTvlabel6, mTvlabel7, mTvlabel8, mTvlabel9;
	private TextView mTvValue1, mTvValue2, mTvValue3, mTvValue4, mTvValue5,
			mTvValue6, mTvValue7, mTvValue8, mTvValue9;
	private View mToRecodes;// 交易记录
	private SpringScrollView scrollView;
	private LinearLayout mViewBottom;

	// 我的资产end
	
	private boolean showMenu = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_main);
		super.onCreate(savedInstanceState);

		loadingDialog = new LoadingDialog(mContext);
		initData();
		registerHomeListener();
		showGestureTip();
		UmengUpdateAgent.update(this);
		showMenu = this.getIntent().getBooleanExtra("showMenu", false);
		if(showMenu){
			mMenu.openMenu();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		refreshData();
	}
	

	@Override
	protected void onPause() {
		super.onPause();
	}
	

	@Override
	protected void onDestroy() {
		unregisterHomeListener();
		super.onDestroy();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		/*if (investLists == null || investLists.isEmpty()) {
			getInvestList();
		}*/
		if (application.refresh == ApiConstants.TYPE_CASH
				|| application.refresh == ApiConstants.TYPE_RECHARGE
				|| application.refresh == ApiConstants.TYPE_INVEST
				|| (mMyAsset.getVisibility() == View.VISIBLE && myAssets == null)) {// 只有在金额变化的情况下刷新我的资产（充值，提现,投资）
			application.refresh = 0;
			getMyInvestData();
		}
	}

	@Override
	public void initView() {
		mActionBar = (ActionBar) findViewById(R.id.actionBar);
		onConfigureActionBar(mActionBar);
		mMenu = (SlidingMenu) findViewById(R.id.id_menu);
		mRightView = findViewById(R.id.right_ll);
		mCallCustom = (TextView) findViewById(R.id.call_custom);
		mLeftListView = (ListView) findViewById(R.id.left_listView);

		mTvUserName = (TextView) findViewById(R.id.user_name);
		mImageHeader = (ImageView) findViewById(R.id.user_head);
		mTopUser = findViewById(R.id.top);

		mHomeViewPager = (cn.com.anyitou.views.CustomViewPager) findViewById(R.id.viewpager);
		mIndicator = (PageIndicator) findViewById(R.id.indicator);

		mActivityHome = findViewById(R.id.activity_home);
		mSetting = findViewById(R.id.activity_setting);
		mMyAsset = findViewById(R.id.activity_my_asset);
		
		
		initSettingView();

		initMyInvestView();
		mMenu.setRightView(mHomeViewPager, scrollView);
	}

	/**
	 * 设置界面的控件初始化
	 */
	private void initSettingView() {
		mGesture = findViewById(R.id.btn_gesture);
		mUpdatePwd = findViewById(R.id.update_pwd);
		mCustomService = findViewById(R.id.custom_service);
		mLogin = findViewById(R.id.login);
		mLoginTitle = (TextView) findViewById(R.id.login_title);
		mInviteFriends = findViewById(R.id.invite_friends);
		mAboutUs = findViewById(R.id.about_us);
	}
	/**
	 * 显示分享弹出框
	 */
	private void showShareDialog(){
		if(popupWindow == null){
			LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
			mInviteFriends = inflater.inflate(
					R.layout.invite_friends_popupwindow, null);
			popupWindow = new PopupWindow(mInviteFriends,
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
					true);
			mViewShareWxFriend = mInviteFriends.findViewById(R.id.share_wx_friend);
			
			mViewShareWxLine = mInviteFriends.findViewById(R.id.share_wx_line);
			mViewShareQQ = mInviteFriends.findViewById(R.id.share_qq_friend);
			mViewShareWeibo = mInviteFriends.findViewById(R.id.share_weibo_qq_friend);
			mViewShareSina = mInviteFriends.findViewById(R.id.share_weibo_sina_friend);
			
			mViewShareWxFriend.setOnClickListener(onClickShareListener);
			mViewShareWxLine.setOnClickListener(onClickShareListener);
			mViewShareQQ.setOnClickListener(onClickShareListener);
			mViewShareWeibo.setOnClickListener(onClickShareListener);
			mViewShareSina.setOnClickListener(onClickShareListener);
			
			popupWindow.setBackgroundDrawable(new BitmapDrawable());
			// 设置PopupWindow的弹出和消失效果
			popupWindow.setAnimationStyle(R.style.popupAnimation);
			friendsCancleTextView = (TextView) mInviteFriends
					.findViewById(R.id.friendsCancleTextView);
			// 取消
			friendsCancleTextView.setOnClickListener(onClickShareListener);
			
		}
		shareUtil = new ShareUtil(mContext);
		popupWindow.showAtLocation(friendsCancleTextView,
				Gravity.CENTER, 0, 0);
	}
	


	/**
	 * 我的资产页面的控件初始化
	 */
	private void initMyInvestView() {
		mBtnToCash = findViewById(R.id.to_cash);
		mBtnToRechange = findViewById(R.id.to_rechange);
		mBtnPaymentPlan = findViewById(R.id.payment_plan);
		mBtnMyInvest = findViewById(R.id.my_invest);
		mBtnMyCard = findViewById(R.id.my_card);
		mToRecodes = findViewById(R.id.to_recodes);
		scrollView = (SpringScrollView)findViewById(R.id.scrollview);
		mViewBottom = (LinearLayout)findViewById(R.id.bottom_my_assets);
		/*int height = mViewBottom.getLayoutParams().height;
		android.widget.RelativeLayout.LayoutParams layoutParams = (android.widget.RelativeLayout.LayoutParams) scrollView.getLayoutParams();
		layoutParams.setMargins(0, 0, 0, mViewBottom.getHeight());
		scrollView.setLayoutParams(layoutParams);*/
		scrollView.setBottomView(mViewBottom);

		mTvValue1 = (TextView) findViewById(R.id.value1);
		mTvValue2 = (TextView) findViewById(R.id.value2);
		mTvValue3 = (TextView) findViewById(R.id.value3);
		mTvValue4 = (TextView) findViewById(R.id.value4);
		mTvValue5 = (TextView) findViewById(R.id.value5);
		mTvValue6 = (TextView) findViewById(R.id.value6);
		mTvValue7 = (TextView) findViewById(R.id.value7);
		mTvValue8 = (TextView) findViewById(R.id.value8);
		mTvValue9 = (TextView) findViewById(R.id.value9);
	}

	private void initMyAssetsData(MyAssets myAssets) {
		if (myAssets != null) {
			LableValue lableValue1 = myAssets.getA_0();//账户名
//			mTvlabel1.setText(lableValue1.getLabel() + "：");
			mTvValue1.setText(lableValue1.getValue());

			LableValue lableValue2 = myAssets.getA_1();//汇付账户名
//			mTvlabel2.setText(lableValue2.getLabel() + "：");
			mTvValue2.setText(lableValue2.getValue());
			LableValue lableValue3 = myAssets.getA_2();//账户余额
//			mTvlabel3.setText(lableValue3.getLabel());
			mTvValue3.setText(lableValue3.getValue());
			
			
			LableValue lableValue4 = myAssets.getA_3();//冻结资产
			DecimalFormat df = new DecimalFormat("######0.00");
			double myMoney = Double.valueOf(lableValue3.getValue()) - Double.valueOf(lableValue4.getValue());
			mTvValue4.setText(String.valueOf(df.format(myMoney)));//可用余额
			
//			mTvlabel4.setText(lableValue4.getLabel());
			mTvValue5.setText(lableValue4.getValue());
			LableValue lableValue5 = myAssets.getA_4();//待收收益
//			mTvlabel5.setText(lableValue5.getLabel());
			mTvValue6.setText(lableValue5.getValue());
			LableValue lableValue6 = myAssets.getA_5();//待收本金
//			mTvlabel6.setText(lableValue6.getLabel());
			mTvValue7.setText(lableValue6.getValue());
			LableValue lableValue7 = myAssets.getA_6();//已获收益
//			mTvlabel7.setText(lableValue7.getLabel());
			mTvValue8.setText(lableValue7.getValue());

			LableValue lableValue8 = myAssets.getA_7();//累计投资
//			mTvlabel8.setText(lableValue8.getLabel());
			mTvValue9.setText(lableValue8.getValue());

		}
	}

	protected void onConfigureActionBar(ActionBar actionBar) {
		actionBar.setTitle("安宜投");
		actionBar.setLeftActionButton(R.drawable.nav_menu,
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						mMenu.toggle();
					}
				});
	}

	private void selectView(int position) {
		switch (position) {
		case 0:// 首页
			mMenu.setDispatchTouch(true);
			mLeftMenuAdapter.setSelectedPosition(position);
			if(investLists == null || investLists.isEmpty()){
				getInvestList();
			}
			mActionBar.setTitle("安宜投");
			mActivityHome.setVisibility(View.VISIBLE);
			mSetting.setVisibility(View.GONE);
			mMyAsset.setVisibility(View.GONE);
			mMenu.toggle();
			
			break;
		case 1:// 我的资产
			if(investLists!=null){
				investLists.clear();
				mHomePagerAdater = new HomePagerAdapter(investLists, this);
				mHomeViewPager.setAdapter(mHomePagerAdater);

				mIndicator.setViewPager(mHomeViewPager);
			}
			mMenu.setDispatchTouch(false);
			if (application.getCurrentUser() == null) {
				ToastUtils.showToast(mContext, "亲，请先登录");
				startActivity(LoginActivity.class);
			} else {
				mLeftMenuAdapter.setSelectedPosition(position);
				if(myAssets==null){
					//从缓存取得上次我的资产
					String myAssetsCacheStr = (String)SharePreferenceManager.getSharePreferenceValue(mContext, Constant.FILE_NAME, "myassets", "");
					if(!StringUtils.isEmpty(myAssetsCacheStr)){
						MyAssets myAssetsCache = (MyAssets)JsonUtils.fromJson(myAssetsCacheStr, MyAssets.class);
						initMyAssetsData(myAssetsCache);
					}
					
					getMyInvestData();
				}
				mActionBar.setTitle("我的资产");
				mActivityHome.setVisibility(View.GONE);
				mSetting.setVisibility(View.GONE);
				mMyAsset.setVisibility(View.VISIBLE);
				mMenu.toggle();
			}
			break;
		case 2:// 设置
			if(investLists!=null){
				investLists.clear();
				mHomePagerAdater = new HomePagerAdapter(investLists, this);
				mHomeViewPager.setAdapter(mHomePagerAdater);
				mIndicator.setViewPager(mHomeViewPager);
				
			}
			mLeftMenuAdapter.setSelectedPosition(position);
			mMenu.setDispatchTouch(true);
			getShareText();
			mActionBar.setTitle("设置");
			mMyAsset.setVisibility(View.GONE);
			mActivityHome.setVisibility(View.GONE);
			mSetting.setVisibility(View.VISIBLE);
			mMenu.toggle();
			break;

		default:
			break;
		}
	}
	
	@Override
	public void initListener() {
		
		mCallCustom.setOnClickListener(onClickListener);
		mLeftListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {
				selectView(position);
			}

		});
		mRightView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(mMenu.isOpen()){
					mMenu.closeMenu();
				}				
			}
		});
		
		mTopUser.setOnClickListener(onClickListener);
		mLogin.setOnClickListener(onClickListener);
		mGesture.setOnClickListener(onClickListener);
		mUpdatePwd.setOnClickListener(onClickListener);
		mCustomService.setOnClickListener(onClickListener);
		mInviteFriends.setOnClickListener(onClickListener);
		mAboutUs.setOnClickListener(onClickListener);
		mImageHeader.setOnClickListener(onClickListener);
		
		mLogin.setOnTouchListener(onTouchListener);
		mGesture.setOnTouchListener(onTouchListener);
		mUpdatePwd.setOnTouchListener(onTouchListener);
		mCustomService.setOnTouchListener(onTouchListener);
		mInviteFriends.setOnTouchListener(onTouchListener);
		mAboutUs.setOnTouchListener(onTouchListener);
		mBtnToCash.setOnTouchListener(onTouchListener);
		mBtnToRechange.setOnTouchListener(onTouchListener);
		
		

		// 我的资产start
		mBtnToCash.setOnClickListener(onClickListener);
		mBtnToRechange.setOnClickListener(onClickListener);
		mBtnPaymentPlan.setOnClickListener(onClickListener);
		mBtnMyInvest.setOnClickListener(onClickListener);
		mBtnMyCard.setOnClickListener(onClickListener);
		mToRecodes.setOnClickListener(onClickListener);
		// 我的资产end

	}
	/**
	 * 分享事件
	 */
	private OnClickListener onClickShareListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.share_wx_friend:
				shareUtil.share(0,share==null ? "" : share.getWx_friend());
				break;
			case R.id.share_wx_line:
				shareUtil.share(1,share==null ? "" : share.getWx_allfriend());
				break;
			case R.id.share_qq_friend:
				shareUtil.share(3,share==null ? "" : share.getQq_friend());
				break;
			case R.id.share_weibo_qq_friend:
				shareUtil.share(4,share==null ? "" : share.getQq_space());
				break;
			case R.id.share_weibo_sina_friend:
				shareUtil.share(5,share==null ? "" : share.getSina_wb());
				break;
			case R.id.friendsCancleTextView:
				break;

			default:
				break;
			}
			if(popupWindow!=null && popupWindow.isShowing()){
				popupWindow.dismiss();
			}
		}
	};
	
	private OnTouchListener onTouchListener = new OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if(mMenu.isOpen()){
				
				if(event.getAction() == MotionEvent.ACTION_UP){
					mMenu.closeMenu();
				}
				return true;
			}
			return false;
		}
	};
	
	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.user_head://点击头像
				if(isLogined()){
					showOtherUserLogin();
				}else{
					startActivity(LoginActivity.class);
				}
				break;
			case R.id.btn_gesture:// 修改手势密码
				
				if (isLogined()) {
					startActivity(ModifyGestureDialog.class);
				} else {
					ToastUtils.showToast(mContext, "亲，请先登录");
					startActivity(LoginActivity.class);
				}
				break;
			case R.id.update_pwd:// 修改登录密码
				
				if (isLogined()) {
					startActivity(ModifyLoginPassWordActivity.class);
				} else {
					ToastUtils.showToast(mContext, "亲，请先登录");
					startActivity(LoginActivity.class);
				}
				break;
			case R.id.call_custom:// 拨打客服

				String tel = mCallCustom.getText().toString();
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
						+ tel));
				startActivity(intent);
				break;
			case R.id.top:
				if (!isLogined()) {
					ToastUtils.showToast(mContext, "亲，请先登录");
					startActivity(LoginActivity.class);
				}
				break;
			case R.id.login:
				

				if (isLogined()) {// 退出登录
					ToastUtils.showToast(mContext, "退出登录成功");
					logOut();
					refreshData();
				} else {
					startActivity(LoginActivity.class);
				}
				break;
			case R.id.invite_friends:// 邀请好友
				

				showShareDialog();
				break;
			case R.id.about_us://关于我们
				

				startActivity(AboutUSActivity.class);
				break;
			case R.id.custom_service://客服服务
				

				startActivity(CustomerServiceActivity.class);
				break;

			case R.id.to_cash:// 提现
				

				if(!isGetMyAssets()){
					return;
				}
				if (!StringUtils.isEmpty(application.getCurrentUser()
						.getIshfuser())
						&& "1".equals(application.getCurrentUser()
								.getIshfuser())) {
					Intent intentRecharge = new Intent(mContext,
							WithdrawalsActivity.class);
					intentRecharge.putExtra("money", mTvValue4.getText().toString());// 当前
					startActivity(intentRecharge);
				} else {
					ToastUtils.showToast(mContext, "请先开通汇付");
					startActivity(RegisteredActivity.class);
				}
				break;
			case R.id.to_rechange:// 充值

				if(!isGetMyAssets()){
					return;
				}
				if (!StringUtils.isEmpty(application.getCurrentUser()
						.getIshfuser())
						&& "1".equals(application.getCurrentUser()
								.getIshfuser())) {
					Intent intentRecharge = new Intent(mContext,
							RechargeActivity.class);
					intentRecharge.putExtra("money", myAssets.getA_2()
							.getValue());// 当前
					startActivity(intentRecharge);
				} else {
					ToastUtils.showToast(mContext, "请先开通汇付");
					startActivity(RegisteredActivity.class);
				}
				break;
			case R.id.to_recodes:// 交易记录
				startActivity(TradingRecordActivity.class);
				break;
			case R.id.payment_plan:// 回款计划
				startActivity(PaymentPlanActivity.class);
				break;
			case R.id.my_invest:// 我的投资
				startActivity(MyInvestmentActivity.class);
				break;
			case R.id.my_card:// 我的银行卡
				if(!StringUtils.isEmpty(application.getCurrentUser().getIshfuser()) && "1".equals(application.getCurrentUser().getIshfuser())){
					startActivity(MyBankCardActivity.class);
				}else{
					ToastUtils.showToast(mContext, "请先开通汇付");
					startActivity(RegisteredActivity.class);
				}
				break;
			default:
				break;
			}
		}
	};

	private void refreshData() {
		if (isLogined()) {
			user = application.getCurrentUser();
			mLoginTitle.setText("退出登录");
			mTvUserName.setText(user.getUser_name());

		} else {
			myAssets = null;
			user = null;
			mTvUserName.setText("安宜投用户名");
			mLoginTitle.setText("登录");
		}

	}

	private void initData() {

		List<Menu> dataList = new ArrayList<Menu>();
		Menu menuHome = new Menu();
		menuHome.setTitle("首页");
		menuHome.setDrawable(R.drawable.left_home);

		Menu menuMy = new Menu();
		menuMy.setTitle("我的资产");
		menuMy.setDrawable(R.drawable.left_money);

		Menu menuSetting = new Menu();
		menuSetting.setTitle("设置");
		menuSetting.setDrawable(R.drawable.left_setting);

		dataList.add(menuHome);
		dataList.add(menuMy);
		dataList.add(menuSetting);

		mLeftMenuAdapter = new LeftMenuAdapter(dataList, this);
		mLeftListView.setAdapter(mLeftMenuAdapter);

		mHomePagerAdater = new HomePagerAdapter(investLists, this);
		mHomeViewPager.setAdapter(mHomePagerAdater);

		mIndicator.setViewPager(mHomeViewPager);

		getInvestList();
	}

	/**
	 * 获取投资列表数据
	 */
	private void getInvestList() {
		loadingDialog.showDialog(loadingDialog);
		ApiInvestUtils.index(mContext,
				new HttpConnectionUtil.RequestCallback() {

					@Override
					public void execute(ParseModel parseModel) {
						loadingDialog.cancelDialog(loadingDialog);
						if (ApiConstants.RESULT_SUCCESS.equals(parseModel
								.getStatus())) {
							List<Investment> invests = (List<Investment>) JsonUtils
									.fromJson(parseModel.getData().toString(),
											new TypeToken<List<Investment>>() {
											});

							if (invests != null && !invests.isEmpty()) {
								// initViewPagerData();
								investLists.clear();
								investLists.addAll(invests);
								mHomePagerAdater.notifyDataSetChanged();
							} else {
								ToastUtils.showToast(mContext, "暂时没有投资列表");
							}

						} else {
							ToastUtils.showToast(mContext, parseModel.getDesc());
						}
					}
				});
	}

	
	/**
	 * 获取我的资产
	 */
	private void getMyInvestData() {

		loadingDialog.showDialog(loadingDialog);
		ApiUserUtils.home(mContext, new RequestCallback() {

			@Override
			public void execute(ParseModel parseModel) {
				loadingDialog.cancelDialog(loadingDialog);
				if (ApiConstants.RESULT_SUCCESS.equals(parseModel.getStatus())) {
					myAssets = JsonUtils.fromJson(parseModel.getData()
							.toString(), MyAssets.class);
					initMyAssetsData(myAssets);
					int ishfUser = parseModel.getIshfuser();
					if (myAssets != null
							&& !StringUtils.isEmpty(myAssets.getToken())) {
						User user = application.getCurrentUser();
						user.setIshfuser(String.valueOf(ishfUser));
						logined(myAssets.getToken(), user);
						SharePreferenceManager.saveBatchSharedPreference(mContext, Constant.FILE_NAME, "myassets", JsonUtils.toJson(myAssets));
					}
				} else {
					ToastUtils.showToast(mContext, parseModel.getDesc());
				}
			}
		});

	}
	/**
	 * 获取分享文案
	 * @return
	 */
	private Share share;
	private void getShareText(){
		if(share == null){
			ApiHomeUtils.getShare(mContext, new RequestCallback() {
				
				@Override
				public void execute(ParseModel parseModel) {
					if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getStatus())){
						share = JsonUtils.fromJson(parseModel.getData().toString(), Share.class);
					}else{
						
					}
				}
			});
		}
	}
	/**
	 * 切换账号提示
	 */
	public void showOtherUserLogin(){
        Builder builder = new AlertDialog.Builder(mContext)
        .setMessage("确定切换帐号？")
        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
            
            @Override
            public void onClick(DialogInterface dialog, int which) {
            	SharePreferenceManager.saveBatchSharedPreference(mContext,
        				Constant.FILE_NAME, Constant.GESTURE_PWD, "");
            	application.isLock = false;
            	application.gesturePwd = "";
                logOut();
                refreshData();
                startActivity(LoginActivity.class);
            	dialog.dismiss();
                
            }
        })
        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
            
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                
            }
        });
        builder.setCancelable(false);
        builder.show();
    }
	/**
	 * 如果登录状态没有设置手势给予提示
	 */
	public void showGestureTip(){
		if(isLogined() && StringUtils.isEmpty(application.gesturePwd)){
	        Builder builder = new AlertDialog.Builder(mContext)
	        .setMessage("是否设置手势密码？")
	        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
	            
	            @Override
	            public void onClick(DialogInterface dialog, int which) {
	            	Intent intent = new Intent(mContext,GestureLockActivity.class);
	            	intent.putExtra("type", 2);
	            	startActivity(intent);
	            }
	        })
	        .setNegativeButton("暂不设置", new DialogInterface.OnClickListener() {
	            
	            @Override
	            public void onClick(DialogInterface dialog, int which) {
	                dialog.dismiss();
	                
	            }
	        });
	        builder.setCancelable(false);
	        builder.show();
		}
    }
	
	
	private boolean isGetMyAssets(){
		if(myAssets==null){
			ToastUtils.showToast(mContext, getResources().getString(R.string.NETWORK_ERROR));
			return false;
		}
		return true;
	}

	private boolean isExit = false;

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
				return false;
			}
		}
		return true;
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			isExit = false;
		}
	};

	

}
