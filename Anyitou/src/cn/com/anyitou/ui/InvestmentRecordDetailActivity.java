package cn.com.anyitou.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.api.ApiInvestUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.InvestRecords;
import cn.com.anyitou.entity.MyInvestmentDetail;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.JsonUtils;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.LoadingDialog;
/**
 * 用户的投资详情
 * @author pengweiqiang
 *
 */
public class InvestmentRecordDetailActivity extends BaseActivity {
	ActionBar actionBar;
	InvestRecords investment;
	private MyInvestmentDetail myInvestmentDetail;
	
	//项目信息
	private TextView mTvInvestName,mTvInvestStatus,mTvInvestMoneyProject,mTvInvestProgress,mTvRepayType,mTvRepayDate;
	//投资信息
	private TextView mTvInvestMoney,mTvInvestRealMoney;
	//收益信息
	private TextView mTvInvestRate,mTvInvestIncome,mTvTotalIncome,mTvLastIncome,mTvIncomeDay,mTvComedProfit;
	
	LoadingDialog loading;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_invest_record_detail);
		super.onCreate(savedInstanceState);
		
		investment = (InvestRecords) this.getIntent().getSerializableExtra("investment");
		
		initData();
		getInvestDetail();
	}
	
	private void initData(){
		if(investment!=null){
			//项目信息
			mTvInvestName.setText(investment.getItem_title());
			mTvInvestStatus.setText(investment.getStatus());
			mTvInvestMoneyProject.setText("融资金额："+investment.getItem_amount());
			mTvInvestProgress.setText("融资进度："+"无");
			mTvRepayType.setText("还款方式："+investment.getPay_type_info().getName());
			mTvRepayDate.setText("还款日期："+investment.getRepayment_time());
			//投资信息
			mTvInvestMoney.setText("融资金额："+investment.getItem_amount());
			mTvInvestRealMoney.setText("实际支付金额："+investment.getReal_amount());
			//受益信息
			mTvInvestRate.setText("年化收益："+investment.getRate_of_interest());
			mTvInvestIncome.setText("每日收益："+"无");
			mTvTotalIncome.setText("到期总收益："+investment.getItem_income());
			mTvLastIncome.setText("最后一天收益："+"无");
			mTvIncomeDay.setText("计息天数："+investment.getInvest_days());
			mTvComedProfit.setText("已到帐收益："+investment.getRepay_interest());
		}
	}
	private void initDataNetDetail(){
		if(myInvestmentDetail!=null){
			//项目信息
			mTvInvestName.setText(myInvestmentDetail.getProjectData().getItem_title());
			mTvInvestStatus.setText(myInvestmentDetail.getProjectData().getInvest_status_label());
			mTvInvestMoneyProject.setText("融资金额："+StringUtils.getMoneyFormat(myInvestmentDetail.getProjectData().getFinancing_amount()));
			mTvInvestProgress.setText("融资进度："+myInvestmentDetail.getProjectData().getScale()+"%");
			mTvRepayType.setText("还款方式："+myInvestmentDetail.getProjectData().getPayTypeInfo().getName());
			mTvRepayDate.setText("还款日期："+myInvestmentDetail.getProjectData().getRepayment_time());
			//投资信息
			mTvInvestMoney.setText("融资金额："+StringUtils.getMoneyFormat(myInvestmentDetail.getProjectData().getFinancing_amount()));
			mTvInvestRealMoney.setText("实际支付金额："+myInvestmentDetail.getInvestData().getItem_amount()+"元");
			//受益信息
			mTvInvestRate.setText("年化收益："+myInvestmentDetail.getProjectData().getRate_of_interest()+"%");
			mTvInvestIncome.setText("每日收益："+myInvestmentDetail.getInterestData().getEveryDay().getInterest()+"元");
			mTvTotalIncome.setText("到期总收益："+myInvestmentDetail.getInterestData().getTotal().getInterest()+"元");
			mTvLastIncome.setText("最后一天收益："+myInvestmentDetail.getInterestData().getLastDay().getInterest()+"元");
			mTvIncomeDay.setText("计息天数："+myInvestmentDetail.getProjectData().getToday_invest_days()+"天");
			mTvComedProfit.setText("已到帐收益："+myInvestmentDetail.getInvestData().getRepay_interest()+"元");
		}
	}
	private void getInvestDetail(){
		loading = new LoadingDialog(mContext);
		loading.show();
		ApiInvestUtils.getMyInvestmentDetail(mContext, investment.getId(), new RequestCallback() {
			
			@Override
			public void execute(ParseModel parseModel) {
				loading.cancel();
				if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
					myInvestmentDetail = JsonUtils.fromJson(parseModel.getData().toString(), MyInvestmentDetail.class);
					initDataNetDetail();
				}else{
					ToastUtils.showToast(mContext, StringUtils.isEmpty(parseModel.getMsg())?"获取详细失败":parseModel.getMsg());
				}
			}
		});
	}
	@Override
	public void initView() {
		actionBar = (ActionBar)findViewById(R.id.actionBar);
		actionBar.setTitle("投资详情");
		actionBar.setLeftActionButton( new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				AppManager.getAppManager().finishActivity();
			}
		});
		mTvInvestName = (TextView)findViewById(R.id.invest_name);
		mTvInvestStatus = (TextView)findViewById(R.id.invest_status);
		mTvInvestMoneyProject = (TextView)findViewById(R.id.invest_money_project);
		mTvInvestProgress = (TextView)findViewById(R.id.invest_progress_project);
		mTvRepayType = (TextView)findViewById(R.id.repay_type);
		mTvRepayDate = (TextView)findViewById(R.id.repay_date);
		
		mTvInvestMoney = (TextView)findViewById(R.id.invest_money);
		mTvInvestRealMoney = (TextView)findViewById(R.id.invest_real_money);
		
		
		mTvInvestRate = (TextView)findViewById(R.id.invest_rate);
		mTvInvestIncome = (TextView)findViewById(R.id.invest_income);
		mTvTotalIncome = (TextView)findViewById(R.id.total_income);
		mTvLastIncome = (TextView)findViewById(R.id.last_income);
		mTvIncomeDay = (TextView)findViewById(R.id.income_day);
		mTvComedProfit = (TextView)findViewById(R.id.comed_profit);
		
		
	}

	@Override
	public void initListener() {
		
	}

}
