package cn.com.anyitou.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.ui.InVestmentDetailActivity;
import cn.com.anyitou.ui.WebActivity;
import cn.com.anyitou.ui.base.BaseFragment;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.TextViewUtils;
import cn.com.anyitou.views.PercentageRing;

/**
 * 主页（新手体验）
 * 
 * @author pengweiqiang
 * 
 */
@SuppressLint("NewApi")
public class RewardItemFragment extends BaseFragment{

	View infoView;
	
	private PercentageRing mYearRate;
	private TextView mTvInvestMoneyMin;//起投金额
	private TextView mTvInvestMoneyAmount;//融资金额
	private TextView mTvDate;//项目期限
	private TextView mTvYearRate;//年化率
	private View mBtnInvest;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		infoView = inflater.inflate(R.layout.home_reward_fragment, container, false);
		
		initView();
		initListener();
		return infoView;
	}
	   
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
	}
	
	private void initView(){
		mBtnInvest = infoView.findViewById(R.id.invest);
		mYearRate = (PercentageRing) infoView.findViewById(R.id.year_rate);
		mTvInvestMoneyMin = (TextView)infoView.findViewById(R.id.invest_money_min);
		mTvInvestMoneyAmount = (TextView)infoView.findViewById(R.id.invest_money_amount);
		mTvDate = (TextView) infoView.findViewById(R.id.project_date);
		mTvYearRate = (TextView)infoView.findViewById(R.id.year_rate_number);
		
		String yearRate = "0%";
		SpannableString yearRateSpann = TextViewUtils.getSpannableStringSize(yearRate, yearRate.length()-1, yearRate.length(), 15);
		mTvYearRate.setText(yearRateSpann);
	}

	private void initListener(){
		mBtnInvest.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				startReward();
			}
		});
	}
	
	private void startReward(){
		Intent intent = new Intent(mActivity,WebActivity.class);
		intent.putExtra("url", "http://www.anyitou.com/novice/detail?id=347");
		intent.putExtra("name", "新手体验");
		startActivity(intent);
	}
}
