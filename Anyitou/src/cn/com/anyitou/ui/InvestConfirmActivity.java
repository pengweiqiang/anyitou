package cn.com.anyitou.ui;

import java.text.DecimalFormat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import cn.com.anyitou.R;

import cn.com.anyitou.api.ApiOrderUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.InvestingPageInfo;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.JsonUtils;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.LoadingDialog;
/**
 * 投资确认信息
 * @author will
 *
 */
public class InvestConfirmActivity extends BaseActivity {
	
	private ActionBar mActionBar;
	private LoadingDialog loadingDialog;
	private TextView mTvRestMoney,mTvMyMoney,mTvTitle,mTvMoney,mTvFutureMoney;
	private EditText mEtBuyMoney;
	private View mViewConfirm;
	private String id = "";
	private String rateStr = "";//年化率
	private String dayStr = "";//天数
	private InvestingPageInfo investingPage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.invest_confirm);
		super.onCreate(savedInstanceState);
		id = this.getIntent().getStringExtra("id");
		rateStr = this.getIntent().getStringExtra("rate");
		dayStr = this.getIntent().getStringExtra("day");
		
		initData();
	}
	
	@Override
	public void initView() {
		mActionBar = (ActionBar)findViewById(R.id.actionBar);
		mActionBar.setTitle("确认信息");
		mActionBar.setLeftActionButton(R.drawable.btn_back, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AppManager.getAppManager().finishActivity();
			}
		});
		mTvRestMoney = (TextView)findViewById(R.id.rest_money);
		mTvMyMoney = (TextView)findViewById(R.id.my_money);
		mTvTitle = (TextView)findViewById(R.id.label_title);
		mTvMoney = (TextView)findViewById(R.id.money);
		mViewConfirm = findViewById(R.id.btn_confirm);
		mEtBuyMoney = (EditText)findViewById(R.id.buy_money);
		mTvFutureMoney = (TextView)findViewById(R.id.future_money);
		
	}
	private void setViewData(){
		if(investingPage!=null){
			mTvTitle.setText(investingPage.getTitle());
			mTvMyMoney.setText(investingPage.getRestBal());
			mTvRestMoney.setText(investingPage.getRestMoney()+"元");//可投金额
			mTvMoney.setText(investingPage.getMoney()+"元");//融资金额
			
		}
	}
	/**
	 * 算出预期收益
	 * //预期收益=投资金额*年利率*（投资期的天数/365）
	 * @param money
	 */
	private void caluFutureMoney(String moneyStr){
		if(StringUtils.isEmpty(moneyStr)){
			mTvFutureMoney.setText("");
			return;
		}
		try{
			double money = Double.valueOf(moneyStr);
			double futureMoney =  money * (Double.parseDouble(rateStr)/100) * (Double.valueOf(dayStr)/365);
			DecimalFormat df   = new DecimalFormat("######0.00");   
			mTvFutureMoney.setText(df.format(futureMoney)+"元");
		}catch(Exception e){
			
		}
	}

	@Override
	public void initListener() {
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
					ToastUtils.showToast(mContext, "请输入投资金额");
					mEtBuyMoney.requestFocus();
					return;
				}
				if(Double.valueOf(moneyStr)<100){
					ToastUtils.showToast(mContext, "购买金额须大于100元");
					mEtBuyMoney.requestFocus();
					return;
				}
				if(Double.valueOf(moneyStr)%100!=0){
					ToastUtils.showToast(mContext, "投资金额须为100的整数倍");
					mEtBuyMoney.requestFocus();
					return;
				}
				try{
					if(Double.valueOf(moneyStr)>Double.valueOf(investingPage.getRestBal())){
						ToastUtils.showToast(mContext, "您的余额不足");
						mEtBuyMoney.requestFocus();
						return;
					}
				}catch(Exception e){
					
				}
				loadingDialog = new LoadingDialog(mContext);
				loadingDialog.show();
				ApiOrderUtils.investing(mContext, id, moneyStr, new RequestCallback() {
					
					@Override
					public void execute(ParseModel parseModel) {
						if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getStatus())){
							logined(parseModel.getToken(), null);
							String url = parseModel.getUrl();
							String ordId = parseModel.getOrdId();
							Intent intent = new Intent(mContext,WebActivity.class);
							intent.putExtra("url", url);
							intent.putExtra("type", 3);
							intent.putExtra("name", "投资");
							intent.putExtra("ordId", ordId);
							startActivity(intent);
							AppManager.getAppManager().finishActivity();
						}else{
							ToastUtils.showToast(mContext, parseModel.getDesc());
						}
					}
				});
				
			}
		});
	}
	
	private void initData(){
		loadingDialog = new LoadingDialog(mContext);
		loadingDialog.show();
		ApiOrderUtils.investingPage(mContext,id, new RequestCallback() {
			
			@Override
			public void execute(ParseModel parseModel) {
				loadingDialog.cancel();
				if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getStatus())){
					investingPage = JsonUtils.fromJson(parseModel.getData().toString(), InvestingPageInfo.class);
					setViewData();
				}else{
					ToastUtils.showToast(mContext, parseModel.getDesc());
					if("3".equals(parseModel.getStatus())){
						new Handler().postDelayed(new Runnable() {
							public void run() {
								startActivity(RegisteredActivity.class);
							} 
						}, 2000);
					}
				}
			}
		});
		
	}
	
}
