package cn.com.anyitou.ui;

import android.content.DialogInterface;
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
import cn.com.anyitou.views.InfoDialog;
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
	private View mViewBankInfo;
	private TextView mTvUseMoney;
	
	CashPageInfo cashPageInfo;
	String mobileStatus;
	String baseStatus;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_recharge);
		super.onCreate(savedInstanceState);
		money = this.getIntent().getStringExtra("money");//账户余额
		
//		mEtMoney.setHint("可用余额为"+money+"元");
		mTvUseMoney.setText("可用余额："+money+"元");
		
		initData();
	}
	@Override
	protected void onRestart() {
		super.onRestart();
		//重新回到界面 得到最新数据
		initData();
	}
	
	@Override
	public void initView() {
		mActionBar = (ActionBar) findViewById(R.id.actionBar);
		onConfigureActionBar(mActionBar);
		
		mBtnRechange = findViewById(R.id.btn_rechange);
		mEtMoney = (EditText) findViewById(R.id.rechange_money);
		mTvBankName = (TextView)findViewById(R.id.bank_name);
		
		mViewBankInfo = findViewById(R.id.bank_info);
		mTvUseMoney = (TextView)findViewById(R.id.use_money);
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
				if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
					cashPageInfo = JsonUtils.fromJson(parseModel.getData().toString(), CashPageInfo.class);
					showData();
				}else{
					ToastUtils.showToast(mContext, parseModel.getMsg());
				}
				loadingDialog.cancel();
			}
		});
		getUserInfo();
	}
	/**
	 * 获取用户信息
	 */
	private void getUserInfo(){
		ApiUserUtils.getUserInfo(mContext,new RequestCallback() {
			
			@Override
			public void execute(ParseModel parseModel) {
				loadingDialog.cancel();
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
	
	private void showData(){
		if(cashPageInfo !=null){
			String bankNumber = cashPageInfo.getCard().getBank_card_number();
			if(!StringUtils.isEmpty(bankNumber) && bankNumber.length()>4){
				String bankNumberLength4 = bankNumber.substring(bankNumber.length()-4);
				mTvBankName.setText(cashPageInfo.getCard().getBank_name()+"（"+bankNumberLength4+"）");
			}
			mTvUseMoney.setText("可用余额："+cashPageInfo.getMoney().getUsable_money()+"元");
//			mEtMoney.setHint("可用余额为"+cashPageInfo.getMoney().getUsable_money()+"元");
			
			if(cashPageInfo.getCard()==null){
				showUnbindBankCard();
			}
		}else{
			showUnbindBankCard();
		}
	}
	/*
	 * 未绑定银行卡
	 */
	private void showUnbindBankCard(){
		mViewBankInfo.setVisibility(View.GONE);
//		InfoDialog.Builder builder = new InfoDialog.Builder(mContext,R.layout.unbind_bankcard_dialog);
//		View view = builder.getViewLayout();
//		builder.setMessage("你未绑定银行卡 不能充值");
//		builder.setButton1("取消", new DialogInterface.OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.cancel();
//			}
//		});
//		builder.setButton2("立即绑定",
//				new DialogInterface.OnClickListener() {
//
//					@Override
//					public void onClick(DialogInterface dialog,
//							int which) {
//						dialog.cancel();
//						bindingBankCard();
//					}
//				});
//		InfoDialog infoDialog = builder.create();
//		infoDialog.show();
	}
	
	private void bindingBankCard(){
		loadingDialog = new LoadingDialog(mContext);
		loadingDialog.show();
		ApiOrderUtils.bindBank(mContext, new RequestCallback() {
			
			@Override
			public void execute(ParseModel parseModel) {
				loadingDialog.cancel();
				if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
					String url = parseModel.getData().getAsJsonObject().get("request_url").getAsString();
					Intent intent = new Intent(mContext,WebActivity.class);
					intent.putExtra("url", url);
					intent.putExtra("name", "绑定银行卡");
					startActivity(intent);
				}else if(ApiConstants.RESULT_UNHF_USER.equals(parseModel.getCode())){
					ToastUtils.showToastSingle(mContext, parseModel.getMsg());
//					escrowRegister();
					String url = parseModel.getData().getAsString();
					Intent intent = new Intent(mContext,WebActivity.class);
					intent.putExtra("url", url);
					intent.putExtra("name", "开通汇付");
					startActivity(intent);
				}else{
					ToastUtils.showToastSingle(mContext, parseModel.getMsg());
				}
			}
		});
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
	@Override
	public void initListener() {
		//充值按钮
		mBtnRechange.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
//				if(cashPageInfo==null){
//					showUnbindBankCard();
//					return;
//				}
				if(!checkUser()){
					return;
				}
				
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
