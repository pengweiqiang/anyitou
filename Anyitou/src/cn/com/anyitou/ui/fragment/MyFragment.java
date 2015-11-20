package cn.com.anyitou.ui.fragment;

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
import cn.com.anyitou.api.ApiUserUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.entity.Grades;
import cn.com.anyitou.entity.MyCapital;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.entity.User;
import cn.com.anyitou.ui.LoginActivity;
import cn.com.anyitou.ui.MyAnbiActivity;
import cn.com.anyitou.ui.MyInvestmentActivity;
import cn.com.anyitou.ui.MyLevelActivity;
import cn.com.anyitou.ui.RechargeActivity;
import cn.com.anyitou.ui.TradingRecordActivity;
import cn.com.anyitou.ui.WithdrawalsActivity;
import cn.com.anyitou.ui.base.BaseFragment;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.JsonUtils;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;

/**
 * 我的
 * 
 * @author will
 * 
 */
@SuppressLint("NewApi")
public class MyFragment extends BaseFragment {
	private View infoView;
	private ActionBar mActionBar;
	private View mBtnCash,mBtnRechange,mBtnInvestDetail,mBtnTradeDetail,mBtnBondAssign,mBtnCoupon,mBtnCoin,mBtnLevel;

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
		
		return infoView;
	}
	
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// 判断Fragment中的ListView时候存在，判断该Fragment时候已经正在前台显示
		// 通过这两个判断，就可以知道什么时候去加载数据了
//		if (isVisibleToUser && isVisible()) {
//			getMyInfo();
//		}
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
//		if(myCapital == null){
			getMyInfo();
//		}
	}



	private void initListener() {
		mActionBar.hideLeftActionButtonText();
		mActionBar.setHasMsg(false);
		mBtnCash.setOnClickListener(onClickListener);
		mBtnRechange.setOnClickListener(onClickListener);
		mBtnInvestDetail.setOnClickListener(onClickListener);
		mBtnTradeDetail.setOnClickListener(onClickListener);
		mBtnBondAssign.setOnClickListener(onClickListener);
		mBtnCoupon.setOnClickListener(onClickListener);
		mBtnCoin.setOnClickListener(onClickListener);
		mBtnLevel.setOnClickListener(onClickListener);
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
			case R.id.to_cash://提现
				intent.setClass(mActivity, WithdrawalsActivity.class);
				intent.putExtra("money", myCapital.getUse_money());
				break;
			case R.id.to_rechange://充值
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
			mTvUserName.setText("^_^  你好,"+user.getUsername());
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
			mTvUserName.setText("^_^  你好,"+myCapital.getUser_name());
			
			MyApplication.getInstance().setMyCapital(myCapital);
		}
	}
	
	private void showMyLevel(){
		if(mGrades !=null){
			mTvLevelTitle.setText(mGrades.getName());
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
	



}
