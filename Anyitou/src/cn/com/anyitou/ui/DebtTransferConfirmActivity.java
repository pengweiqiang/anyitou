package cn.com.anyitou.ui;


import java.math.BigDecimal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import cn.com.anyitou.MyApplication;
import cn.com.anyitou.R;
import cn.com.anyitou.api.ApiOrderUtils;
import cn.com.anyitou.api.ApiUserUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.DebtTransferDetail;
import cn.com.anyitou.entity.MyCapital;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.utils.AnyitouUtils;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.JsonUtils;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.TextViewUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.InfoDialog;
import cn.com.anyitou.views.LoadingDialog;
import cn.com.gson.JsonObject;
/**
 * 债权确认信息
 * @author will
 *
 */
public class DebtTransferConfirmActivity extends BaseActivity {
	
	private ActionBar mActionBar;
	private LoadingDialog loadingDialog;
	private TextView mTvInvestName;
	private TextView mTvYearRate;
	private TextView mTvInvestDay;
	private TextView mTvRestMoney,mTvMyMoney,mTvPreProfit;
	private EditText mEtBuyMoney;
	private View mViewConfirm;
	private View mBtnProfitCal;
	
	private DebtTransferDetail debt;//债权项目
	private int investmentMoney = 1000;//最小认购份额
	private Double remainMoney ;//剩余份额
//	String id = "";
	String useMoney = "";//可用金额
	String myUserId = "";
	
	String mobileStatus;//是否验证手机
	String baseStatus;//是否开通汇付
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.debt_confirm);
		super.onCreate(savedInstanceState);
		
		debt = (DebtTransferDetail)this.getIntent().getSerializableExtra("debt");
//		id = debt.getDebtData().getInvest_id();
		investmentMoney = StringUtils.getMoney2Int(Double.valueOf(debt.getDebtData().getMinBuyAmount()));
		remainMoney = Double.valueOf(debt.getDebtData().getRemainAmount());
		if(MyApplication.getInstance().getMyCapital()!=null){
			useMoney = MyApplication.getInstance().getMyCapital().getUse_money();
			myUserId = MyApplication.getInstance().getMyCapital().getUser_id();
		}else{
			//加载可用余额
			getMyUseMoney();
		}
		initData();
	}
	
	private void getMyUseMoney(){
		ApiUserUtils.getMyAccount(mContext, new RequestCallback() {
			
			@Override
			public void execute(ParseModel parseModel) {
				if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
					MyCapital myCapital = JsonUtils.fromJson(parseModel.getData()
							.toString(), MyCapital.class);
					if(myCapital != null){
						MyApplication.getInstance().setMyCapital(myCapital);
						useMoney = myCapital.getUse_money();
						myUserId = myCapital.getUser_id();
					}
				}
			}
		});
	}
	
	@Override
	public void initView() {
		mActionBar = (ActionBar)findViewById(R.id.actionBar);
		mActionBar.setTitle("确认投资");
		mActionBar.setLeftActionButton(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AppManager.getAppManager().finishActivity();
			}
		});
		mActionBar.setRightActionButton("充值", new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent (mContext,RechargeActivity.class);
				intent.putExtra("money", useMoney);
				startActivity(intent);
			}
		});
		mTvInvestName = (TextView)findViewById(R.id.invest_name);
		mTvYearRate = (TextView)findViewById(R.id.year_rate);
		mTvInvestDay = (TextView)findViewById(R.id.invest_day);
		mTvRestMoney = (TextView)findViewById(R.id.invest_money);
		mTvMyMoney = (TextView)findViewById(R.id.useable_money);
		mTvPreProfit = (TextView)findViewById(R.id.pre_profit);
		mBtnProfitCal = findViewById(R.id.invest_calu);
		
		mViewConfirm = findViewById(R.id.bottom_invest);
		mEtBuyMoney = (EditText)findViewById(R.id.buy_money);
		
	}
	private void initData(){
		if(debt!=null){
			mTvInvestName.setText(debt.getProjectData().getItem_title());
			mTvYearRate.setText(debt.getDebtData().getBuyer_apr()+"%");
			mTvInvestDay.setText(debt.getDebtData().getSell_days()+"天");
			mTvRestMoney.setText(StringUtils.getMoneyFormat(debt.getDebtData().getRemainAmount()));
			mTvMyMoney.setText(StringUtils.getMoneyFormat(useMoney));
		}
		getUserInfo();
	}
	@Override
	protected void onRestart() {
		super.onRestart();
		getUserInfo();
	}
	/**
	 * 债权收益计算器
	 * @param moneyStr
	 * @return
	 */
	private String caluProfitMoney(String moneyStr){
		if(StringUtils.isEmpty(moneyStr)){
			return "0";
		}
		double money = Double.valueOf(moneyStr);
		double yushu = money%investmentMoney;
		if(money<investmentMoney || yushu!=0d){
			return "0";
		}
		String proFit = AnyitouUtils.getDebtPreProfit(debt.getDebtData().getPrice(),debt.getDebtData().getAmount(),moneyStr, debt.getDebtData().getBuyer_apr(), debt.getDebtData().getSell_days());//预期收益
		return proFit;	
	}
	private long lastTime;
	TextView mTvRate;
	@Override
	public void initListener() {
		mBtnProfitCal.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				InfoDialog.Builder builder = new InfoDialog.Builder(mContext,R.layout.profit_calu_info_dialog);
				builder.setTitle("收益计算器");
				final InfoDialog infoDialog = builder.create();
				mTvRate = (TextView)infoDialog.findViewById(R.id.pre_profit);
				TextView mTvInvestDay = (TextView)infoDialog.findViewById(R.id.invest_day);
				mTvInvestDay.setText(debt.getDebtData().getSell_days()+"天");
				infoDialog.findViewById(R.id.close).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						infoDialog.cancel();
					}
				});
				final EditText mEtInvestMoney = (EditText)infoDialog.findViewById(R.id.invest_money);
				mEtInvestMoney.setHint("最小认购"+investmentMoney+"份");
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
						if(s.toString().startsWith("0")){
							mEtBuyMoney.setText(s.toString().substring(1));
							return;
						}
						String moneyStr = mEtInvestMoney.getText().toString().trim();
						String proFit = caluProfitMoney(moneyStr)+"元";
						SpannableString ss = TextViewUtils.getSpannableStringSizeAndColor(proFit, proFit.length()-1, proFit.length(), 14,getResources().getColor(R.color.dialog_text_color));
						mTvRate.setText(ss);
					}
				});
				
				infoDialog.show();
			}
		});
		mEtBuyMoney.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(s.toString().startsWith("0")){
					mEtBuyMoney.setText(s.toString().substring(1));
					return;
				}
				long now = System.currentTimeMillis();
				if(now-lastTime>100){
					String moneyStr = mEtBuyMoney.getText().toString().trim();
					checkInvestMoney(moneyStr,"edittext");
					lastTime = now;
				}
			}
		});
		mViewConfirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String moneyStr = mEtBuyMoney.getText().toString().trim();
				if(StringUtils.isEmpty(moneyStr)){
					ToastUtils.showToastSingle(mContext, "请输入认购份额");
					return;
				}
				if(!checkInvestMoney(moneyStr,"btn")){
					return ;
				}
				loadingDialog = new LoadingDialog(mContext);
				loadingDialog.show();
				String id = debt.getDebtData().getId();
				ApiOrderUtils.buyDebt(mContext, id, moneyStr, new RequestCallback() {
					
					@Override
					public void execute(ParseModel parseModel) {
						loadingDialog.cancel();
						if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
							String url = parseModel.getData().getAsJsonObject().get("url").getAsString();
							String ordId = parseModel.getData().getAsJsonObject().get("id").getAsString();
//							String tradeNo = parseModel.getData().getAsJsonObject().get("trade_no").getAsString();
							Intent intent = new Intent(mContext,WebActivity.class);
							intent.putExtra("url", url);
							intent.putExtra("type", 5);
							intent.putExtra("name", "债权");
							intent.putExtra("ordId", ordId);
							startActivity(intent);
							AppManager.getAppManager().finishActivity();
						}else{
							ToastUtils.showToast(mContext, parseModel.getMsg());
						}
					}
				});
				
			}
		});
	}
	
	private void showNotenoughMoney(String money){
		InfoDialog.Builder builder = new InfoDialog.Builder(mContext,R.layout.invest_info_dialog);
		builder.setTitle("账户余额不足");
		String message = "差值为："+money+"元";
		builder.setMessage(message);
		builder.setButton1("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		builder.setButton2("立即充值",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						dialog.cancel();
						Intent intent = new Intent (mContext,RechargeActivity.class);
						intent.putExtra("money", useMoney);
						startActivity(intent);
					}
				});
		TextView mTvMessgae = (TextView)builder.getViewLayout().findViewById(R.id.message);
		InfoDialog infoDialog = builder.create();
		mTvMessgae.setText(TextViewUtils.getSpannableStringColor(message, 4, message.lastIndexOf("元"), getResources().getColor(R.color.app_bg_color)));
		infoDialog.show();
	}
	
	private boolean checkInvestMoney(String moneyStr,String from){
		if(myUserId.equals(debt.getDebtData().getUser_id())){
			ToastUtils.showToastSingle(mContext, "不能认购自己债权");
			return false;
		}
		if(StringUtils.isEmpty(moneyStr)){
			ToastUtils.showToastSingle(mContext, "请输入认购份额");
			mEtBuyMoney.requestFocus();
			
			mTvPreProfit.setText("0");
			return false;
		}
		if(Double.valueOf(moneyStr)<investmentMoney){
			ToastUtils.showToastSingle(mContext, "最小认购"+investmentMoney);
			mEtBuyMoney.requestFocus();
			
			mTvPreProfit.setText("0");
			return false;
		}
		if(Double.valueOf(moneyStr) > remainMoney){
			ToastUtils.showToastSingle(mContext, "超过剩余份额"+remainMoney);
			mEtBuyMoney.requestFocus();
			
			mTvPreProfit.setText("0");
			return false ;
		}
		try{
			if(Double.valueOf(moneyStr)>Double.valueOf(useMoney)){
				if("btn".equals(from)){
					BigDecimal b1=new BigDecimal(moneyStr);  
			        BigDecimal b2=new BigDecimal(useMoney);
					showNotenoughMoney(b1.subtract(b2).doubleValue()+ "");
					mEtBuyMoney.requestFocus();
				}else if("edittext".equals(from)){
					ToastUtils.showToast(mContext, "您的余额不足");
				}
				
				mTvPreProfit.setText("0");
				return false ;
			}
		}catch(Exception e){
			
		}
		String profit = caluProfitMoney(moneyStr);
		mTvPreProfit.setText(profit);
		return true;
	}
	
	
	
	/**
	 * 获取用户信息
	 */
	private void getUserInfo(){
		loadingDialog = new LoadingDialog(mContext);
		loadingDialog.show();
		ApiUserUtils.getUserInfo(mContext,new RequestCallback() {
			
			@Override
			public void execute(ParseModel parseModel) {
				if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
					try{
						JsonObject data = parseModel.getData().getAsJsonObject();
						if(data!=null){
							String mobile = data.get("mobile").getAsString();
							baseStatus = data.get("base_status").getAsString();//  用户状态： 1：正常  2：开通资金账户  
							mobileStatus = data.get("mobile_status").getAsString();//"mobile_status": "1",  //  手机认证状态： 0：未认证  1:已认证
							checkUser();
						}
					}catch(Exception e){
						
					}
				}
				loadingDialog.cancel();
			}
		});
	}
	private boolean checkUser(){
		if(!StringUtils.isEmpty(mobileStatus) && "0".equals(mobileStatus)){//未认证手机号
			bindingMobile();
			return false;
		}
		if(!StringUtils.isEmpty(baseStatus) && Long.valueOf(baseStatus)<2){//未开通资金账户  
			escrowRegister();
			return false;
		}
		return true;
	}
	/**
	 * 绑定手机号
	 */
	private void bindingMobile(){
		InfoDialog.Builder builder = new InfoDialog.Builder(mContext,R.layout.info_dialog);
		builder.setMessage("为了您的账户安全，请先通过手机验证");
		builder.setTitle("");
		builder.setButton1("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		builder.setButton2("认证手机",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						dialog.cancel();
						Intent intent = new Intent(mContext,MobilePhoneVerificationActivity2.class);
						startActivity(intent);
						AppManager.getAppManager().finishActivity();
					}
				});
		builder.create().show();
	}
	
	/**
	 * 开通汇付
	 */
	private void escrowRegister(){
		
		InfoDialog.Builder builder = new InfoDialog.Builder(mContext,R.layout.info_dialog);
		builder.setMessage("为了您的资金安全，请先开通资金托管账户");
		builder.setTitle("");
		builder.setButton1("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		builder.setButton2("开通资金账户",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						dialog.cancel();
						loadingDialog = new LoadingDialog(mContext);
						loadingDialog.show();
						ApiUserUtils.escrowRegister(mContext, new RequestCallback() {
							
							@Override
							public void execute(ParseModel parseModel) {
								if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
									String url = parseModel.getData().getAsJsonObject().get("url").getAsString();
									Intent intent = new Intent(mContext,WebActivity.class);
									intent.putExtra("url", url);
									intent.putExtra("type", 1);//开通汇付
									intent.putExtra("name", "开通汇付");
									startActivity(intent);
								}else{
									ToastUtils.showToastSingle(mContext, parseModel.getMsg());
								}
								loadingDialog.cancel();
							}
						});
					}
				});
		builder.create().show();
		
	}
	
}
