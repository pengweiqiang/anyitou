package cn.com.anyitou.ui.fragment;

import java.util.List;

import u.aly.ca;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.api.ApiInvestUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.entity.Investment;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.ui.InVestmentDetailActivity;
import cn.com.anyitou.ui.base.BaseFragment;
import cn.com.anyitou.utils.HttpConnectionUtil;
import cn.com.anyitou.utils.JsonUtils;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.TextViewUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.LoadingDialog;
import cn.com.anyitou.views.PercentageRing;
import cn.com.gson.JsonElement;
import cn.com.gson.JsonNull;
import cn.com.gson.reflect.TypeToken;

/**
 * 主页（新手体验、安企贷、安房贷、安车贷）
 * 
 * @author pengweiqiang
 * 
 */
@SuppressLint("NewApi")
public class HomeItemFragment extends BaseFragment{
	private View infoView;

	
	private LoadingDialog loadingDialog;
	private boolean isFirst = true;
	
	private String category = "invest";
	
	private Investment investment;
	
	
	private PercentageRing mYearRate;
	private TextView mTvInvestMoneyMin;//起投金额
	private TextView mTvInvestMoneyAmount;//融资金额
	private TextView mTvDate;//项目期限
	private TextView mTvYearRate;//年化率
	private View mBtnInvest;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		category = this.getArguments().getString("category");
		infoView = inflater.inflate(R.layout.home_fragment_item, container, false);
		initView();
		initListener();
		
		
		
		return infoView;
	}
	   
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if("invest".equals(category)){
			initData();
		}
	}
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// 判断Fragment中的ListView时候存在，判断该Fragment时候已经正在前台显示
		// 通过这两个判断，就可以知道什么时候去加载数据了
		if (isVisibleToUser && isVisible() && isFirst && (!"invest".equals(category) || investment==null)) {
			initData();
		}
		super.setUserVisibleHint(isVisibleToUser);
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
				if(investment!=null){
					Intent intent = new Intent(mActivity,
							InVestmentDetailActivity.class);
					intent.putExtra("type", 1);//投资
					intent.putExtra("invest", investment);
					startActivity(intent);
				}
			}
		});
	}
	
	

	@Override
	public void onResume() {
		super.onResume();
	}
	
	
	/**
	 * 获取数据
	 */
	private void initData() {
		loadingDialog = new LoadingDialog(mActivity);
		loadingDialog.show();
		ApiInvestUtils.getRecommend(mActivity, category, "10",
				new HttpConnectionUtil.RequestCallback() {

					@Override
					public void execute(ParseModel parseModel) {
						if (ApiConstants.RESULT_SUCCESS.equals(parseModel
								.getCode())) {
							JsonElement data = parseModel.getData();
							if(data!=null && !data.isJsonNull() && data.isJsonObject()){
								JsonElement list = data.getAsJsonObject().get(category);
								if(list!=null && list!=JsonNull.INSTANCE){
									List<Investment> invests = (List<Investment>) JsonUtils
											.fromJson(list.toString(),
													new TypeToken<List<Investment>>() {
													});
									showView2Data(invests);
									
								}else{
									ToastUtils.showToast(mActivity, "项目不存在");
								}
							}else{
								ToastUtils.showToast(mActivity, "项目不存在");
							}

						} else {
							ToastUtils.showToast(mActivity, parseModel.getMsg());
						}
						
						loadingDialog.cancelDialog(loadingDialog);
					}
				});
	}
	
	private void showView2Data(List<Investment> invests){
		if(invests ==null || invests.isEmpty()){
			return;
		}
		isFirst = false;
		investment = invests.get(0);
		String moneyAmount = StringUtils.getMoneyFormateWanNoDecimaPoint(investment.getFinancing_amount());
		String investMoneyMin = investment.getInvestment();
		String scale = investment.getScale();//0.1%
		String yearRate = investment.getRate_of_interest();
		yearRate = StringUtils.getMoneyFormat(yearRate)+"%";
		SpannableString yearRateSpann = TextViewUtils.getSpannableStringSize(yearRate, yearRate.length()-1, yearRate.length(), 15);
		mTvYearRate.setText(yearRateSpann);
		mYearRate.setTargetPercent(StringUtils.getProgress(scale),false);
		
		mTvInvestMoneyMin.setText(investMoneyMin);
		
		mTvInvestMoneyAmount.setText(moneyAmount);
		mTvDate.setText(investment.getBorrow_days()+"天");
		
	}

	
	
	

}
