package cn.com.anyitou.ui;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.adapters.MyVerticalPagerAdapter;
import cn.com.anyitou.api.ApiInvestUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.api.constant.OperationType;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.DebtAssignment;
import cn.com.anyitou.entity.DebtTransferDetail;
import cn.com.anyitou.entity.InVestDetail;
import cn.com.anyitou.entity.Investment;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.ui.base.BaseFragmentActivity;
import cn.com.anyitou.utils.AnyitouUtils;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.JsonUtils;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.TextViewUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.InfoDialog;
import cn.com.anyitou.views.LoadingDialog;
import cn.com.anyitou.views.VerticalViewPager;
import cn.com.gson.JsonNull;
import cn.com.gson.JsonObject;
/**
 * 项目详情
 * @author will
 *
 */
@SuppressLint("NewApi") public class InVestmentDetailActivity extends BaseFragmentActivity {
	
	ActionBar mActionBar;
	
	LoadingDialog loadingDialog;
	private VerticalViewPager mVerticalViewPager;
	private List<Fragment> fragmentLists;
	private InVestDetail investDetail;
	DebtTransferDetail debtTransfer;
	private TextView mBtnInvest;
	private View mBtnInvestCal;
	
	InvestDetailFirstFragment investDetailFirstFragment;
	Fragment investDetailSecondFragment;
	
	
	private int type ;//1投资 2债权
	private String category = "invest";//invest(企贷) fangdai房贷  chedai车贷 reward新手项目
	Investment investment;//项目详细
	DebtAssignment debtAssigment;//债权详细
	boolean isStarted = true;//是否开始状态
	
	String day = "";
	String yearRate = "";
	int invest = 1000;//最小投资金额/最小认购金额
	
	
	String id = "";//项目投资id
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.invertment_detail);
		super.onCreate(savedInstanceState);
		
		investment = (Investment) this.getIntent().getSerializableExtra("invest");
		debtAssigment = (DebtAssignment) this.getIntent().getSerializableExtra("debt");
		type = this.getIntent().getIntExtra("type", 1);
		
		
		initView();
		initData();
		initListener();
		
	}
	private TextView mTvRate ;//预期收益
	private void initListener(){
		//投资计算器
		mBtnInvestCal.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!isStarted){//没有开始或已经结束
					ToastUtils.showToast(InVestmentDetailActivity.this, "项目不能投资");
					return;
				}
				if(!isLogined()){
					ToastUtils.showToast(InVestmentDetailActivity.this, "请先登录");
					Intent intent = new Intent(InVestmentDetailActivity.this,LoginActivity.class);
					startActivity(intent);
					return;
				}
				InfoDialog.Builder builder = new InfoDialog.Builder(InVestmentDetailActivity.this,R.layout.profit_calu_info_dialog);
				builder.setTitle("收益计算器");
				final InfoDialog infoDialog = builder.create();
				mTvRate = (TextView)infoDialog.findViewById(R.id.pre_profit);
				TextView mTvInvestDay = (TextView)infoDialog.findViewById(R.id.invest_day);
				mTvInvestDay.setText(day+"天");
				infoDialog.findViewById(R.id.close).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						infoDialog.cancel();
					}
				});
				final EditText mEtInvestMoney = (EditText)infoDialog.findViewById(R.id.invest_money);
				if(type==1){
					mEtInvestMoney.setHint(invest+"元起投,"+invest+"元递增");
				}else if(type == 2){
					mEtInvestMoney.setHint("最小认购"+invest+"份");	
				}
				
				mEtInvestMoney.addTextChangedListener(new TextWatcher() {
					
					@Override
					public void onTextChanged(CharSequence s, int start, int before, int count) {
						
					}
					
					@Override
					public void beforeTextChanged(CharSequence s, int start, int count,
							int after) {
						
					}
					
					@Override
					public void afterTextChanged(Editable s) {
						String moneyStr = mEtInvestMoney.getText().toString().trim();
//						String proFit = caluProfitMoney(moneyStr)+"元";
						caluProfitMoneyForService(moneyStr);
					}
				});
				infoDialog.show();
			}
		});
		//投资
		mBtnInvest.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!isLogined()){
					ToastUtils.showToast(InVestmentDetailActivity.this, "请先登录");
					Intent intent = new Intent(InVestmentDetailActivity.this,LoginActivity.class);
					startActivity(intent);
					return;
				}
//				if(!"1".equals(application.getCurrentUser().getIshfuser())){
//					ToastUtils.showToast(InVestmentDetailActivity.this, "尚未开通汇付，2秒后前往注册汇付界面");
//					new Handler().postDelayed(new Runnable() {
//						public void run() {
//							Intent intent = new Intent(InVestmentDetailActivity.this,RegisteredActivity.class);
//							startActivity(intent);
//						} 
//					}, 2000);
//					return;
//				}
				if(type == 1){//投资项目
					Intent intent = new Intent(InVestmentDetailActivity.this,InvestConfirmActivity.class);
					intent.putExtra("investment", investment);
					startActivity(intent);
				}else if(type == 2){//债权
					Intent intent = new Intent(InVestmentDetailActivity.this,DebtTransferConfirmActivity.class);
					intent.putExtra("debt", debtTransfer);
					startActivity(intent);
				}
				
			}
		});
	}
	private void initData() {
		loadingDialog = new LoadingDialog(this);
		loadingDialog.show();
		
		if(type == 1){//投资
			ApiInvestUtils.contentShow(this, investment.getId(),"base", new RequestCallback() {
				
				@Override
				public void execute(ParseModel parseModel) {
					loadingDialog.cancel();
					if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
						investDetail = JsonUtils.fromJson(parseModel.getData().toString(), InVestDetail.class);
						setData();
					}else{
						ToastUtils.showToast(InVestmentDetailActivity.this, StringUtils.isEmpty(parseModel.getMsg())?"获取项目详情失败，请稍后重试":parseModel.getMsg());
					}
				}
			});
		}else if(type == 2){//债权
			ApiInvestUtils.getDebtAssignmentDetail(this, debtAssigment.getId(), new RequestCallback() {
				
				@Override
				public void execute(ParseModel parseModel) {
					loadingDialog.cancel();
					if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
						debtTransfer = JsonUtils.fromJson(parseModel.getData().toString(), DebtTransferDetail.class);
						setData();
					}else{
						ToastUtils.showToast(InVestmentDetailActivity.this, StringUtils.isEmpty(parseModel.getMsg())?"获取项目详情失败，请稍后重试":parseModel.getMsg());
					}
				}
			});
			
		}
	}
	private void setData(){
		if(investDetail!=null){
			investDetailFirstFragment.setFirstPageInvestList(null,investDetail);
//			investDetailSecondFragment.setSecondeList(investDetail.getSecondpage());
			day = investDetail.getBorrow_days();
			yearRate = investDetail.getApr();
		}
		if(debtTransfer != null){
			investDetailFirstFragment.setFirstPageDebtList(null, debtTransfer);
			//TODO 获取债权的天数
			day = debtTransfer.getDebtData().getSell_days();
			yearRate = debtTransfer.getDebtData().getBuyer_apr();
		}
	}
	private void initView() {
		
		mVerticalViewPager = (VerticalViewPager) findViewById(R.id.verticalViewPager);
		mActionBar = (ActionBar)findViewById(R.id.actionBar);
		mActionBar.setLeftActionButton( new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				AppManager.getAppManager().finishActivity();
			}
		});
		mBtnInvest = (TextView)findViewById(R.id.bottom_invest);
		mBtnInvestCal = findViewById(R.id.invest_calu);
		
		if(1==type){
			id = investment.getId();
			category = investment.getCategory();
			invest = StringUtils.getMoney2Int(Double.valueOf(investment.getInvestment()));
			isStarted = Boolean.valueOf(investment.getIsstarted());
			mActionBar.setTitle("项目详情");
			String status = investment.getInvest_status();
			if("1".equals(status) && isStarted){//开放投资
				mBtnInvest.setEnabled(true);
				mBtnInvest.setClickable(true);
			}else{
				mBtnInvest.setText(investment.getInvest_status_label());
			}
		}else if(2==type){
			id = debtAssigment.getItem_id();
			//最小认购份额
			invest = StringUtils.getMoney2Int(Double.valueOf(debtAssigment.getMinBuyAmount()));
			if("1".equals(debtAssigment.getStatus()) && isStarted){//开放中
				mBtnInvest.setEnabled(true);
				mBtnInvest.setClickable(true);
			}else{
				mBtnInvest.setText(AnyitouUtils.getDebtStatusName(debtAssigment.getStatus()));
			}
			mActionBar.setTitle("债权详情");
		}
		
		
		
		fragmentLists = new ArrayList<Fragment>();
		investDetailFirstFragment = new InvestDetailFirstFragment();
		
		if(OperationType.CATEGORY_FANGDAI.getName().equals(category)){//房贷
			investDetailSecondFragment = new InvestDetailSecondFangDaiFragment();
		}else if(OperationType.CATEGORY_CHEDAI.getName().equals(category)){//车贷
			investDetailSecondFragment = new InvestDetailSecondCheDaiFragment();
		}else {//企贷、新手项目
			investDetailSecondFragment = new InvestDetailSecondFragment();
		}
		
		
		Bundle bundle = new Bundle();
		bundle.putSerializable("investment", investment);//投资
		bundle.putSerializable("debt", debtAssigment);//债权
		investDetailFirstFragment.setArguments(bundle);
		
		Bundle bundleSecond = new Bundle();
		bundleSecond.putString("id", id);//项目id
		bundleSecond.putString("category", category);
		investDetailSecondFragment.setArguments(bundleSecond);
		
		fragmentLists.add(investDetailFirstFragment);
		fragmentLists.add(investDetailSecondFragment);
		MyVerticalPagerAdapter fragmentAdapter = new MyVerticalPagerAdapter(getSupportFragmentManager(), fragmentLists);
		mVerticalViewPager.setAdapter(fragmentAdapter);
		
	}
	
	/**
	 * 债权收益计算器
	 * @param moneyStr
	 * @return
	 */
	private String caluDebtProfitMoney(String moneyStr){
		if(StringUtils.isEmpty(moneyStr)){
			return "0";
		}
		double money = Double.valueOf(moneyStr);
		double yushu = money%invest;
		if(money<invest || yushu!=0d){
			return "0";
		}
		if(debtTransfer==null){
			return "0";
		}
		String proFit = AnyitouUtils.getDebtPreProfit(debtTransfer.getDebtData().getPrice(),debtTransfer.getDebtData().getAmount(),moneyStr, debtTransfer.getDebtData().getBuyer_apr(), debtTransfer.getDebtData().getSell_days());//预期收益
		return proFit;	
	}
	/**
	 * 收益计算器（数据从服务器获取）
	 * @param moneyStr
	 * @return
	 */
	private void caluProfitMoneyForService(String moneyStr){
		if(StringUtils.isEmpty(moneyStr)){
			return ;
		}
		double money = Double.valueOf(moneyStr);
		double yushu = money%invest;
		if(money<invest || yushu!=0d){
			return ;
		}
		String coupons = "";
		if(type == 2){//债权
			String proFit = caluDebtProfitMoney(moneyStr)+"元";
			if(mTvRate!=null){
				SpannableString ss = TextViewUtils.getSpannableStringSizeAndColor(proFit, proFit.length()-1, proFit.length(), 14,getResources().getColor(R.color.dialog_text_color));
				mTvRate.setText(ss);
			}
		}else if(type == 1){//投资项目
			ApiInvestUtils.calculatorInvest(InVestmentDetailActivity.this,id , moneyStr, coupons, new RequestCallback() {
				
				@Override
				public void execute(ParseModel parseModel) {
					if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
						JsonObject data = parseModel.getData().getAsJsonObject();
						if(data != null){
							if(data.get("interestData") != JsonNull.INSTANCE){
								String proFit = data.getAsJsonObject("interestData").getAsJsonObject("total").get("interest").getAsString()+"元";//预期收益
								if(mTvRate!=null){
									SpannableString ss = TextViewUtils.getSpannableStringSizeAndColor(proFit, proFit.length()-1, proFit.length(), 14,getResources().getColor(R.color.dialog_text_color));
									mTvRate.setText(ss);
								}
							}
						}
					}else{
						ToastUtils.showToast(InVestmentDetailActivity.this, parseModel.getMsg());
					}
				}
			});
		}
	}
	
	/**
	 * 上线通知
	 */
	public void online(){
		mBtnInvest.setEnabled(true);
		mBtnInvest.setClickable(true);
		mBtnInvest.setText("立即投标");
	}


}
