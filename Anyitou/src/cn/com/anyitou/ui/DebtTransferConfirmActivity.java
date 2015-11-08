package cn.com.anyitou.ui;

import java.text.DecimalFormat;

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
import cn.com.anyitou.entity.DebtAssignment;
import cn.com.anyitou.entity.MyCapital;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.JsonUtils;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.TextViewUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.InfoDialog;
import cn.com.anyitou.views.LoadingDialog;
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
	private TextView mTvRestMoney,mTvMyMoney,mTvPreProfit,mTvPreAnbi;
	private EditText mEtBuyMoney;
	private View mViewConfirm;
	private View mBtnProfitCal;
	
	private DebtAssignment debt;//债权项目
	String useMoney = "";//可用金额
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.debt_confirm);
		super.onCreate(savedInstanceState);
		
		debt = (DebtAssignment)this.getIntent().getSerializableExtra("debt");
		if(MyApplication.getInstance().getMyCapital()!=null){
			useMoney = MyApplication.getInstance().getMyCapital().getUse_money();
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
			mTvInvestName.setText(debt.getNumber());
			mTvYearRate.setText(debt.getBuyer_apr()+"%");
			mTvInvestDay.setText(debt.getSell_days()+"天");
			mTvRestMoney.setText(StringUtils.getMoneyFormat(debt.getAmount()));
			mTvMyMoney.setText(StringUtils.getMoneyFormat(useMoney));
			
		}
	}
	/**
	 * 算出预期收益
	 * //预期收益=投资金额*年利率*（投资期的天数/365）
	 * @param money
	 */
	private void caluFutureMoney(String moneyStr){
		if(StringUtils.isEmpty(moneyStr)){
			mTvPreProfit.setText("");
			mTvPreAnbi.setText("");
			return;
		}
		try{
			double money = Double.valueOf(moneyStr);
			double futureMoney =  money * (Double.parseDouble(debt.getBuyer_apr())/100) * (Double.valueOf(debt.getSell_days())/365);
			DecimalFormat df   = new DecimalFormat("######0.00");   
			mTvPreProfit.setText(df.format(futureMoney));
		}catch(Exception e){
			
		}
	}
	
	private String caluProfitMoney(String moneyStr){
		if(StringUtils.isEmpty(moneyStr)){
			return "0";
		}
		String moneyProfit = "0";
		try{
			double money = Double.valueOf(moneyStr);
			double futureMoney =  money * (Double.parseDouble(debt.getBuyer_apr())/100) * (Double.valueOf(debt.getSell_days())/365);
			DecimalFormat df   = new DecimalFormat("######0.00");   
			moneyProfit = df.format(futureMoney);
		}catch(Exception e){
			
		}
		return moneyProfit;
	}

	@Override
	public void initListener() {
		mBtnProfitCal.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				InfoDialog.Builder builder = new InfoDialog.Builder(mContext,R.layout.profit_calu_info_dialog);
				builder.setTitle("收益计算器");
				final InfoDialog infoDialog = builder.create();
				final TextView mTvRate = (TextView)infoDialog.findViewById(R.id.pre_profit);
				TextView mTvInvestDay = (TextView)infoDialog.findViewById(R.id.invest_day);
				mTvInvestDay.setText(debt.getSell_days()+"天");
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
				String moneyStr = mEtBuyMoney.getText().toString().trim();
				caluFutureMoney(moneyStr);
			}
		});
		mViewConfirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String moneyStr = mEtBuyMoney.getText().toString().trim();
				if(StringUtils.isEmpty(moneyStr)){
					ToastUtils.showToast(mContext, "请输入认购份额");
					mEtBuyMoney.requestFocus();
					return;
				}
				if(Double.valueOf(moneyStr)<100){
					ToastUtils.showToast(mContext, "购买金额须大于1000元");
					mEtBuyMoney.requestFocus();
					return;
				}
				try{
					if(Double.valueOf(moneyStr)>Double.valueOf(useMoney)){
//						ToastUtils.showToast(mContext, "您的余额不足");
						showNotenoughMoney(Double.valueOf(moneyStr)-Double.valueOf(useMoney)+"");
//						mEtBuyMoney.requestFocus();
						return;
					}
				}catch(Exception e){
					
				}
				loadingDialog = new LoadingDialog(mContext);
				loadingDialog.show();
				String id = debt.getId();
				ApiOrderUtils.buyDebt(mContext, id, moneyStr, new RequestCallback() {
					
					@Override
					public void execute(ParseModel parseModel) {
						loadingDialog.cancel();
						if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
							String url = parseModel.getUrl();
							String ordId = parseModel.getData().getAsJsonObject().get("id").getAsString();
							String tradeNo = parseModel.getData().getAsJsonObject().get("trade_no").getAsString();
							Intent intent = new Intent(mContext,WebActivity.class);
							intent.putExtra("url", url);
							intent.putExtra("type", 3);
							intent.putExtra("name", "投资");
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
	
//	private void initData(){
//		loadingDialog = new LoadingDialog(mContext);
//		loadingDialog.show();
//		ApiOrderUtils.investingPage(mContext,id, new RequestCallback() {
//			
//			@Override
//			public void execute(ParseModel parseModel) {
//				loadingDialog.cancel();
//				if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
//					investingPage = JsonUtils.fromJson(parseModel.getData().toString(), InvestingPageInfo.class);
//					setViewData();
//				}else{
//					ToastUtils.showToast(mContext, parseModel.getMsg());
//					if("3".equals(parseModel.getCode())){
//						new Handler().postDelayed(new Runnable() {
//							public void run() {
//								startActivity(RegisteredActivity.class);
//							} 
//						}, 2000);
//					}
//				}
//			}
//		});
//		
//	}
	
}
