package cn.com.anyitou.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import cn.com.anyitou.R;

import cn.com.anyitou.api.ApiOrderUtils;
import cn.com.anyitou.api.ApiUserUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.CashPageInfo;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.JsonUtils;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.LoadingDialog;
import cn.com.gson.JsonNull;
import cn.com.gson.JsonObject;
/**
 * 充值界面
 * @author will
 *
 */
public class RechargeActivity extends BaseActivity {
	
	private ActionBar mActionBar;
	private View mBtnRechange;
	private EditText mEtMoney;
	private LoadingDialog loadingDialog;
	String money = "";
	private TextView mTvBankName;
	
	CashPageInfo cashPageInfo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_recharge);
		super.onCreate(savedInstanceState);
		money = this.getIntent().getStringExtra("money");//账户余额
		
		mEtMoney.setHint("当前可用余额为"+money+"元");
		
		initData();
	}
	
	@Override
	public void initView() {
		mActionBar = (ActionBar) findViewById(R.id.actionBar);
		onConfigureActionBar(mActionBar);
		
		mBtnRechange = findViewById(R.id.btn_rechange);
		mEtMoney = (EditText) findViewById(R.id.rechange_money);
		mTvBankName = (TextView)findViewById(R.id.bank_name);
	}

	protected void onConfigureActionBar(ActionBar actionBar) {
		actionBar.setTitle("充值");
		actionBar.setLeftActionButton( new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				AppManager.getAppManager().finishActivity();
			}
		});
		
	}
	
	private void initData(){
		loadingDialog = new LoadingDialog(mContext);
		loadingDialog.show();
		ApiUserUtils.getMyCard(mContext, new RequestCallback() {
			
			@Override
			public void execute(ParseModel parseModel) {
				loadingDialog.cancel();
				if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
					cashPageInfo = JsonUtils.fromJson(parseModel.getData().toString(), CashPageInfo.class);
					showData();
				}else{
					ToastUtils.showToast(mContext, parseModel.getMsg());
				}
			}
		});
	}
	private void showData(){
		if(cashPageInfo !=null){
			String bankNumber = cashPageInfo.getCard().getBank_card_number();
			if(!StringUtils.isEmpty(bankNumber) && bankNumber.length()>4){
				String bankNumberLength4 = bankNumber.substring(bankNumber.length()-4);
				mTvBankName.setText(cashPageInfo.getCard().getBank_name()+"（"+bankNumberLength4+"）");
			}
			
			mEtMoney.setHint("当前可用余额为"+cashPageInfo.getMoney().getUsable_money()+"元");
			
		}
	}
	@Override
	public void initListener() {
		mBtnRechange.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String moneyStr = mEtMoney.getText().toString();
				if(StringUtils.isEmpty(moneyStr)){
					ToastUtils.showToast(mContext, "请输入充值金额");
					mEtMoney.requestFocus();
					return;
				}
				try{
					double moneyInput = Double.valueOf(moneyStr);
					if(moneyInput<1){
						ToastUtils.showToast(mContext, "至少充值1元");
						mEtMoney.requestFocus();
						return;
					}
					if(moneyInput>50000){
						ToastUtils.showToast(mContext, "最高充值为5万元");
						mEtMoney.requestFocus();
						return;
					}
					double moneyUser = Double.valueOf(money);
//					if(moneyInput > moneyUser){
//						ToastUtils.showToast(mContext, "您的可用余额不足");
//						mEtMoney.requestFocus();
//						return;
//					}
				}catch(Exception e){
					ToastUtils.showToast(mContext, "输入金额有误");
					mEtMoney.requestFocus();
					return;
				}
				String bankCardId = "";
				loadingDialog = new LoadingDialog(mContext);
				loadingDialog.show();
				ApiOrderUtils.reCharge(mContext,moneyStr,bankCardId, new RequestCallback() {
					
					@Override
					public void execute(ParseModel parseModel) {
						loadingDialog.cancel();
						if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
							JsonObject data = parseModel.getData().getAsJsonObject();
							if(data!=null){
								String url = data.get("request_url").getAsString();
								String ordId = "";
								if(data.has("id") && data.get("id")!=JsonNull.INSTANCE){
									ordId = data.get("id").getAsString();
//									String tradeNo = data.get("trade_no").getAsString();
								}
								
								Intent intent = new Intent(mContext,WebActivity.class);
								intent.putExtra("url", url);
								intent.putExtra("name", "充值");
								intent.putExtra("type", 2);
								intent.putExtra("ordId", ordId);
	//							intent.putExtra("ordId", parseModel.getOrdId());
								startActivity(intent);
								AppManager.getAppManager().finishActivity();
							}
						}else{
							ToastUtils.showToast(mContext, parseModel.getMsg());
							if(ApiConstants.RESULT_UNHF_USER.equals(parseModel.getCode())){
								String url = parseModel.getData().getAsString();
								Intent intent = new Intent(mContext,WebActivity.class);
								intent.putExtra("url", url);
								intent.putExtra("name", "注册汇付");
								intent.putExtra("type", 1);
								startActivity(intent);
								AppManager.getAppManager().finishActivity();
							}
							
						}
					}
				});
			}
		});
	}

}
