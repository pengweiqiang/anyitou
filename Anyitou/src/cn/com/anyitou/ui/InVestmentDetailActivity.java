package cn.com.anyitou.ui;

import java.text.DecimalFormat;
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
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.DebtAssignment;
import cn.com.anyitou.entity.DebtTransferDetail;
import cn.com.anyitou.entity.InVestDetail;
import cn.com.anyitou.entity.Investment;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.ui.base.BaseFragmentActivity;
import cn.com.anyitou.utils.AnyitouStatusUtils;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.JsonUtils;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.TextViewUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.InfoDialog;
import cn.com.anyitou.views.LoadingDialog;
import cn.com.anyitou.views.VerticalViewPager;
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
	private View mBtnInvest;
	private View mBtnInvestCal;
	InvestDetailFirstFragment investDetailFirstFragment;
	InvestDetailSecondFragment investDetailSecondFragment;
	
	private int type ;//1投资 2债权
	Investment investment;//项目详细
	DebtAssignment debtAssigment;//债权详细
	
	String day = "";
	String yearRate = "";
	
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
	
	private void initListener(){
		//投资计算器
		mBtnInvestCal.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				InfoDialog.Builder builder = new InfoDialog.Builder(InVestmentDetailActivity.this,R.layout.profit_calu_info_dialog);
				builder.setTitle("收益计算器");
				final InfoDialog infoDialog = builder.create();
				final TextView mTvRate = (TextView)infoDialog.findViewById(R.id.pre_profit);
				TextView mTvInvestDay = (TextView)infoDialog.findViewById(R.id.invest_day);
				mTvInvestDay.setText(day+"天");
				infoDialog.findViewById(R.id.close).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						infoDialog.cancel();
					}
				});
				final EditText mEtInvestMoney = (EditText)infoDialog.findViewById(R.id.invest_money);
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
						String proFit = caluProfitMoney(moneyStr)+"元";
						SpannableString ss = TextViewUtils.getSpannableStringSizeAndColor(proFit, proFit.length()-1, proFit.length(), 14,getResources().getColor(R.color.dialog_text_color));
						mTvRate.setText(ss);
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
				if(type == 1){
					Intent intent = new Intent(InVestmentDetailActivity.this,InvestConfirmActivity.class);
					intent.putExtra("investment", investment);
//					intent.putExtra("day", day);
//					intent.putExtra("id", id);
					startActivity(intent);
				}else if(type == 2){
					Intent intent = new Intent(InVestmentDetailActivity.this,DebtTransferConfirmActivity.class);
					intent.putExtra("debt", debtAssigment);
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
		mBtnInvest = findViewById(R.id.bottom_invest);
		mBtnInvestCal = findViewById(R.id.invest_calu);
		
		if(1==type){
			mActionBar.setTitle("项目详情");
			String status = investment.getInvest_status();
			if("1".equals(status)){//开放投资
				mBtnInvest.setEnabled(true);
				mBtnInvest.setClickable(true);
			}
		}else if(2==type){
			if("1".equals(debtAssigment.getStatus())){
				mBtnInvest.setEnabled(true);
				mBtnInvest.setClickable(true);
			}
			mActionBar.setTitle("债权详情");
		}
		
		
		
		fragmentLists = new ArrayList<Fragment>();
		investDetailFirstFragment = new InvestDetailFirstFragment();
		
		investDetailSecondFragment = new InvestDetailSecondFragment();
		
		Bundle bundle = new Bundle();
		bundle.putSerializable("investment", investment);//投资
		bundle.putSerializable("debt", debtAssigment);//债权
		investDetailFirstFragment.setArguments(bundle);
		
		fragmentLists.add(investDetailFirstFragment);
		fragmentLists.add(investDetailSecondFragment);
		MyVerticalPagerAdapter fragmentAdapter = new MyVerticalPagerAdapter(getSupportFragmentManager(), fragmentLists);
		mVerticalViewPager.setAdapter(fragmentAdapter);
		
	}
	
	/**
	 * 收益计算器
	 * @param moneyStr
	 * @return
	 */
	private String caluProfitMoney(String moneyStr){
		if(StringUtils.isEmpty(moneyStr)){
			return "0";
		}
		String moneyProfit = "0";
		try{
			double money = Double.valueOf(moneyStr);
			double futureMoney =  money * (Double.parseDouble(yearRate)/100) * (Double.valueOf(day)/365);
			DecimalFormat df   = new DecimalFormat("######0.00");   
			moneyProfit = df.format(futureMoney);
		}catch(Exception e){
			
		}
		return moneyProfit;
	}
	
	


}
