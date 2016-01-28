package cn.com.anyitou.ui.fragment;

import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import cn.com.anyitou.MyApplication;
import cn.com.anyitou.R;
import cn.com.anyitou.api.ApiMessageUtils;
import cn.com.anyitou.api.ApiUserUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.commons.Constant;
import cn.com.anyitou.entity.Grades;
import cn.com.anyitou.entity.Message;
import cn.com.anyitou.entity.MyCapital;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.entity.User;
import cn.com.anyitou.ui.LoginActivity;
import cn.com.anyitou.ui.MessagesActivity;
import cn.com.anyitou.ui.MyAnbiActivity;
import cn.com.anyitou.ui.MyInvestmentActivity;
import cn.com.anyitou.ui.MyLevelActivity;
import cn.com.anyitou.ui.RechargeActivity;
import cn.com.anyitou.ui.TradingRecordActivity;
import cn.com.anyitou.ui.WithdrawalsActivity;
import cn.com.anyitou.ui.base.BaseFragment;
import cn.com.anyitou.utils.DateUtil;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.JsonUtils;
import cn.com.anyitou.utils.SharePreferenceManager;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.LoadingDialog;
import cn.com.gson.JsonElement;
import cn.com.gson.JsonNull;
import cn.com.gson.JsonObject;
import cn.com.gson.reflect.TypeToken;

/**
 * 我的
 * 
 * @author pengweiqiang
 * 
 */
@SuppressLint("NewApi")
public class MyFragment extends BaseFragment {
	private View infoView;
	private ActionBar mActionBar;
	private View mBtnCash,mBtnRechange,mBtnInvestDetail,mBtnTradeDetail,mBtnBondAssign,mBtnCoupon,mBtnCoin,mBtnLevel;
	private TextView mTvUserSign;//签到
	private TextView mTvSignValue;

	private TextView mTvUserName,mTvEarningsYesterday;
	private TextView mTvBalance,mTvWaitProfit,mTvWaitPrincipal,mTvProfitCount;
	private TextView mTvLevelTitle;//会员等级名称
	private ImageView mIvLevelLogo;

	private User user ;
	private MyCapital myCapital;//我的资金
	private Grades mGrades;//等级
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		infoView = inflater.inflate(R.layout.activity_my_assets, container, false);
		mActionBar = (ActionBar) infoView.findViewById(R.id.actionBar);
		onConfigureActionBar(mActionBar);
		
		initView();
		initListener();
		
//		initUserSignType();
		return infoView;
	}
	
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// 判断Fragment中的ListView时候存在，判断该Fragment时候已经正在前台显示
		// 通过这两个判断，就可以知道什么时候去加载数据了
		if (isVisibleToUser && isVisible()) {
//			getMyInfo();
			getMessage();
//			getSignStatus();
		}
		super.setUserVisibleHint(isVisibleToUser);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		if (getUserVisibleHint()) {
//			if (MyApplication.getInstance().getCurrentUser() == null) {
//				Intent intent = new Intent(mActivity, LoginActivity.class);
//				startActivity(intent);
//			}
		}
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		getMyInfo();
//		if(myCapital == null){
//			getMyInfo();
//		}
	}



	private void initListener() {
		mActionBar.hideLeftActionButtonText();
		mActionBar.setHasMsg(false);
		mActionBar.setMessageListener(onClickListener);
		mBtnCash.setOnClickListener(onClickListener);
		mBtnRechange.setOnClickListener(onClickListener);
		mBtnInvestDetail.setOnClickListener(onClickListener);
		mBtnTradeDetail.setOnClickListener(onClickListener);
		mBtnBondAssign.setOnClickListener(onClickListener);
		mBtnCoupon.setOnClickListener(onClickListener);
		mBtnCoin.setOnClickListener(onClickListener);
		mBtnLevel.setOnClickListener(onClickListener);
		mTvUserSign.setOnClickListener(onClickListener);
		mIvLevelLogo.setOnClickListener(onClickListener);
	}
	private void initView(){
		
		mBtnCash = infoView.findViewById(R.id.to_cash);
		mBtnRechange = infoView.findViewById(R.id.to_rechange);
		mBtnInvestDetail = infoView.findViewById(R.id.invest_detail);
		mBtnTradeDetail = infoView.findViewById(R.id.trade_detail);
		mBtnBondAssign = infoView.findViewById(R.id.bond_assign);
		mBtnCoupon = infoView.findViewById(R.id.coupon);
		mBtnCoin = infoView.findViewById(R.id.my_coin);
		mBtnLevel = infoView.findViewById(R.id.my_level);
		
		mTvUserName = (TextView)infoView.findViewById(R.id.username);
		mTvEarningsYesterday = (TextView)infoView.findViewById(R.id.earnings_yesterday);
		mTvBalance = (TextView)infoView.findViewById(R.id.balance);
		mTvWaitProfit = (TextView)infoView.findViewById(R.id.wait_profit);
		mTvWaitPrincipal = (TextView)infoView.findViewById(R.id.wait_principal);
		mTvProfitCount = (TextView)infoView.findViewById(R.id.profit_count);
		
		mTvLevelTitle = (TextView)infoView.findViewById(R.id.level_title);
		mIvLevelLogo = (ImageView)infoView.findViewById(R.id.level_logo);
		
		
		mTvUserSign = (TextView)infoView.findViewById(R.id.user_signin);
		mTvSignValue = (TextView)infoView.findViewById(R.id.sign_value);
		
	}
	
	OnClickListener onClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			if(user == null){
				ToastUtils.showToast(mActivity, "请先登录");
				intent.setClass(mActivity, LoginActivity.class);
				startActivity(intent);
				return;
			}
			
			switch (v.getId()) {
			case R.id.level_logo://会员等级图标
				if(mGrades==null){
					ToastUtils.showToast(mActivity, getResources().getText(R.string.NETWORK_ERROR).toString());
					return;
				}
				intent.putExtra("grades", mGrades);
				intent.setClass(mActivity, MyLevelActivity.class);
				break;
			case R.id.user_signin://签到
				userSignIn();
				return;
			case R.id.msg://消息
				intent.setClass(mActivity, MessagesActivity.class);
				break;
			case R.id.to_cash://提现
				if(myCapital==null){
					ToastUtils.showToast(mActivity, getResources().getText(R.string.NETWORK_ERROR).toString());
					return;
				}
				intent.setClass(mActivity, WithdrawalsActivity.class);
				intent.putExtra("money", myCapital.getUse_money());
				break;
			case R.id.to_rechange://充值
				if(myCapital==null){
					ToastUtils.showToast(mActivity, getResources().getText(R.string.NETWORK_ERROR).toString());
					return;
				}
				intent.putExtra("money", myCapital.getUse_money());
				intent.setClass(mActivity, RechargeActivity.class);
				break;
			case R.id.invest_detail://投资明细
				intent.setClass(mActivity, MyInvestmentActivity.class);
				break;
			case R.id.trade_detail://交易记录
				intent.setClass(mActivity, TradingRecordActivity.class);
				break;
			case R.id.bond_assign://债券转让
				intent.setClass(mActivity, DebtTransferFragment.class);
				break;
			case R.id.coupon://优惠券
				intent.setClass(mActivity, MyCouponFragment.class);
				break;
			case R.id.my_coin://我的安币
				intent.setClass(mActivity, MyAnbiActivity.class);
				break;
			case R.id.my_level://我的等级
				if(mGrades==null){
					ToastUtils.showToast(mActivity, getResources().getText(R.string.NETWORK_ERROR).toString());
					return;
				}
				intent.putExtra("grades", mGrades);
				intent.setClass(mActivity, MyLevelActivity.class);
				break;

			default:
				break;
			}
			startActivity(intent);
		}
	};

	// 设置activity的导航条
	protected void onConfigureActionBar(ActionBar actionBar) {
		actionBar.setTitle("我的账户");
		actionBar.setRightActionButton("签到", new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(user == null){
					ToastUtils.showToast(mActivity, "请先登录");
					Intent loginIntent = new Intent(mActivity,LoginActivity.class);
					mActivity.startActivity(loginIntent);
				}
			}
		});
	}
	
	/**
	 * 获取我的资金信息
	 */
	private void getMyInfo(){
		user = MyApplication.getInstance().getCurrentUser();
		if(user != null){
			mTvUserName.setText(user.getUsername());
			ApiUserUtils.getMyAccount(mActivity, new RequestCallback() {
				
				@Override
				public void execute(ParseModel parseModel) {
					if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
						myCapital = JsonUtils.fromJson(parseModel.getData()
								.toString(), MyCapital.class);
						showMyCapital();
					}
				}
			});
			
			ApiUserUtils.getGrades(mActivity, new RequestCallback() {
				
				@Override
				public void execute(ParseModel parseModel) {
					if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
						mGrades = JsonUtils.fromJson(parseModel.getData()
								.toString(), Grades.class);
						showMyLevel();
					}
				}
			});
			
			getSignStatus();
				
		}
	}
	/**
	 * 显示我的资金信息
	 */
	private void showMyCapital(){
		if(myCapital!=null){
			mTvEarningsYesterday.setText(StringUtils.getMoneyFormat(myCapital.getYesterday_income()));
			mTvBalance.setText(StringUtils.getMoneyFormat(myCapital.getUse_money()));
			mTvWaitProfit.setText(StringUtils.getMoneyFormat(myCapital.getCollected_interest()));//待收收益
			mTvWaitPrincipal.setText(StringUtils.getMoneyFormat(myCapital.getFrozen_money()));//待收本金
			mTvProfitCount.setText(StringUtils.getMoneyFormat(myCapital.getAll_income()));
			mTvUserName.setText(myCapital.getUser_name());
			
			MyApplication.getInstance().setMyCapital(myCapital);
		}
	}
	
	private void showMyLevel(){
		if(mGrades !=null){
//			mTvLevelTitle.setText(mGrades.getName());
			int levelLogo = R.drawable.user_level_normal_big_icon;
			if("1".equals(mGrades.getUser_level())){
				levelLogo = R.drawable.user_level_normal_big_icon;
			}else if("2".equals(mGrades.getUser_level())){
				levelLogo = R.drawable.user_level_star_big_icon;
			}else if("3".equals(mGrades.getUser_level())){
				levelLogo = R.drawable.user_level_gold_big_icon;
			}else if("4".equals(mGrades.getUser_level())){
				levelLogo = R.drawable.user_level_diamond_big_icon;
			}
			mIvLevelLogo.setImageDrawable(getResources().getDrawable(levelLogo));
		}
	}
	
	/**
	 * 取得未读的消息
	 */
	private void getMessage(){
		ApiMessageUtils.getMessageList(mActivity, "0", "1", new RequestCallback() {
			
			@Override
			public void execute(ParseModel parseModel) {
				if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
					JsonObject data = parseModel.getData().getAsJsonObject();
					boolean isHas = false;
					if(data != null){
						JsonElement list = data.get("list");
						if(list!=null && list != JsonNull.INSTANCE){
							List<Message> messages = (List<Message>) JsonUtils
									.fromJson(list.toString(),
											new TypeToken<List<Message>>() {
											});
							if(messages!=null && !messages.isEmpty()){
								isHas = true;
							}
						}
					}
					mActionBar.setHasMsg(isHas);
				}
			}
		});
	}
	
	/**
	 * 签到
	 */
	LoadingDialog loadingDialog;
	private void userSignIn(){
		loadingDialog = new LoadingDialog(mActivity,"签到中...");
		loadingDialog.show();
		ApiUserUtils.userSignIn(mActivity,new RequestCallback() {
			
			@Override
			public void execute(ParseModel parseModel) {
				if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
					JsonElement checked_get_grow_value = parseModel.getData().getAsJsonObject().get("checked_get_grow_value");
					String value = checked_get_grow_value.getAsString();
					signSuccess(value);
					mTvUserSign.setText("已签到");
					mTvUserSign.setClickable(false);
					mTvUserSign.setEnabled(false);
					mTvUserSign.setTextColor(getResources().getColor(R.color.aleady_sign));
					SharePreferenceManager.saveBatchSharedPreference(mActivity, Constant.FILE_NAME, MyApplication.getInstance().getCurrentUser().getUsername()+Constant.USER_SIGN,
							DateUtil.getDateString(new Date(), DateUtil.DAY_PATTERN));
					ToastUtils.showToastSingle(mActivity, "签到成功，获取成长值"+value);
				}else{
					ToastUtils.showToastSingle(mActivity, parseModel.getMsg());
					mTvUserSign.setText("签到");
					mTvUserSign.setTextColor(getResources().getColor(R.color.aleady_sign));
					mTvUserSign.setClickable(false);
					mTvUserSign.setEnabled(false);
					SharePreferenceManager.saveBatchSharedPreference(mActivity, Constant.FILE_NAME, MyApplication.getInstance().getCurrentUser().getUsername()+Constant.USER_SIGN,
							DateUtil.getDateString(new Date(), DateUtil.DAY_PATTERN));
				}
				loadingDialog.cancel();
			}
		});
	}
	
	private void signSuccess(String value){
//		mTvSignValue.setVisibility(View.VISIBLE);
//		mTvSignValue.setText("+"+value);
//		PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 0.7f,  
//                0.7f, 1.5f);  
//        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 0.7f,  
//                0.7f, 1.5f);  
//        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 0f,  
//                0.3f, 1f);  
//        ObjectAnimator objectAnimator =  ObjectAnimator.ofPropertyValuesHolder(mTvSignValue,alpha, pvhY,pvhZ).setDuration(2500);
//        objectAnimator.addListener(new AnimatorListener() {
//			
//			@Override
//			public void onAnimationStart(Animator arg0) {
//				
//			}
//			
//			@Override
//			public void onAnimationRepeat(Animator arg0) {
//			}
//			
//			@Override
//			public void onAnimationEnd(Animator arg0) {
//				mTvSignValue.setVisibility(View.GONE);
//			}
//			
//			@Override
//			public void onAnimationCancel(Animator arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
//        objectAnimator.start();
	}
	
	private void initUserSignType(boolean signStatus){
//		user = MyApplication.getInstance().getCurrentUser();
//		if(user!=null){
//			String signDate = (String)SharePreferenceManager.getSharePreferenceValue(mActivity, Constant.FILE_NAME, MyApplication.getInstance().getCurrentUser().getUsername()+Constant.USER_SIGN, "");
//			if(!StringUtils.isEmpty(signDate) && signDate.equals(DateUtil.getDateString(new Date(), DateUtil.DAY_PATTERN))){
//				mTvUserSign.setText("已签到");
//				mTvUserSign.setTextColor(getResources().getColor(R.color.aleady_sign));
//				mTvUserSign.setClickable(false);
//				mTvUserSign.setEnabled(false);
//			}
//		}else{
//			mTvUserSign.setText("签到");
//			mTvUserSign.setTextColor(getResources().getColor(R.color.un_sign));
//			mTvUserSign.setClickable(true);
//			mTvUserSign.setEnabled(true);
//		}
		if(signStatus){
			mTvUserSign.setText("已签到");
			mTvUserSign.setTextColor(getResources().getColor(R.color.aleady_sign));
			mTvUserSign.setClickable(false);
			mTvUserSign.setEnabled(false);
		}else{
			mTvUserSign.setText("签到");
			mTvUserSign.setTextColor(getResources().getColor(R.color.un_sign));
			mTvUserSign.setClickable(true);
			mTvUserSign.setEnabled(true);
		}
	}
	/**
	 * 获取今日签到状态
	 */
	private void getSignStatus(){
		user = MyApplication.getInstance().getCurrentUser();
		if(user ==null){	
			initUserSignType(false);
			return;
		}
		ApiUserUtils.getSignStatus(mActivity, new RequestCallback() {
			
			@Override
			public void execute(ParseModel parseModel) {
				boolean signStatus = false;
				if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
					JsonElement data = parseModel.getData();
					if(data != null && data.isJsonObject()){
						signStatus = data.getAsJsonObject().get("signin_status").getAsBoolean();
					}
				}else if("5009".equals(parseModel.getCode())){
					signStatus = true;
				}
				initUserSignType(signStatus);
			}
		});
	}
	



}
