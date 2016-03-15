package cn.com.anyitou.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.api.ApiOrderUtils;
import cn.com.anyitou.api.ApiUserUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.BankCard;
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
import cn.com.anyitou.views.ToggleButton;
import cn.com.anyitou.views.ToggleButton.OnToggleChanged;
import cn.com.gson.JsonObject;

/**
 * 提现
 * @author will
 *
 */
public class WithdrawalsActivity extends BaseActivity {

	private ActionBar mActionBar;
	private EditText mEtMoney,mEtMsgCode;
	private View mBtnCash;
//	private TextView mBtnGetCode;
	private LoadingDialog loadingDialog;
	
	private TextView mTvBankName,mTvServiceChangeMoney,mTvRealMoney;
	private ToggleButton mToggleQuan;
	String nowMoney = "";//当前账户余额
	private CashPageInfo cashPageInfo;//提现界面信息
	private String quanStatus = "0";//提现券状态，默认0
	private TextView mTvUseMoney;
	
	String mobileStatus;
	String baseStatus;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_withdrawals);
		super.onCreate(savedInstanceState);
		
		nowMoney = this.getIntent().getStringExtra("money");
//		mEtMoney.setHint("当前可用余额"+nowMoney+"元");
		mTvUseMoney.setText("可提现金额："+nowMoney+"元");
		
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
		
		mEtMoney = (EditText)findViewById(R.id.money);
		mBtnCash = findViewById(R.id.btn_cash);
		mTvBankName = (TextView)findViewById(R.id.bank_name);
		mTvServiceChangeMoney = (TextView)findViewById(R.id.service_change_money);
		mTvRealMoney = (TextView)findViewById(R.id.real_money);
		mToggleQuan = (ToggleButton)findViewById(R.id.switch_quan);
		mTvUseMoney = (TextView)findViewById(R.id.use_money);
	}

	protected void onConfigureActionBar(ActionBar actionBar) {
		actionBar.setTitle("提现");
		actionBar.setLeftActionButton( new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				AppManager.getAppManager().finishActivity();
			}
		});
		
	}
	/**
	 * 获取提现页面信息
	 */
	private void initData(){
		loadingDialog = new LoadingDialog(mContext);
		loadingDialog.show();
		
		getUserInfo();
		
	}
	private void showData(){
		if(cashPageInfo != null){
			nowMoney = cashPageInfo.getMoney().getUsable_money();
//			mEtMoney.setHint("当前可用余额"+nowMoney+"元");
			mTvUseMoney.setText("可提现金额："+nowMoney+"元");
			BankCard bankCard = cashPageInfo.getCard();
			if(bankCard == null){//未绑定银行卡
				showUnbindBankCard();
				mTvBankName.setText("无");
				findViewById(R.id.cash_bank).setVisibility(View.GONE);
				return;
			}
			String bankNumber = bankCard.getBank_card_number();
			if(!StringUtils.isEmpty(bankNumber) && bankNumber.length() >4){
				mTvBankName.setText(bankCard.getBank_name()+"（"+bankNumber.substring(bankNumber.length()-4)+"）");
			}
		}else{
			mTvBankName.setText("无");
			findViewById(R.id.cash_bank).setVisibility(View.GONE);
			showUnbindBankCard();
		}
	}
	/*
	 * 未绑定银行卡
	 */
	private void showUnbindBankCard(){
		InfoDialog.Builder builder = new InfoDialog.Builder(mContext,R.layout.unbind_bankcard_dialog);
		View view = builder.getViewLayout();
		builder.setMessage("你未绑定银行卡 不能提现");
		builder.setButton1("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		builder.setButton2("立即绑定",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						dialog.cancel();
						bindingBankCard();
					}
				});
		InfoDialog infoDialog = builder.create();
		infoDialog.show();
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
	 * 获取用户信息
	 */
	private void getUserInfo(){
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
							if(checkUser()){
								getBankInfo();
							}
						}
					}catch(Exception e){
						
					}
				}else{
					loadingDialog.cancel();
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
	private void getBankInfo(){
		ApiOrderUtils.getBankInfo(mContext, new RequestCallback() {
			
			@Override
			public void execute(ParseModel parseModel) {
				loadingDialog.cancel();
				if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
//					logined(parseModel.getToken(), null);
					cashPageInfo = JsonUtils.fromJson(parseModel.getData().toString(), CashPageInfo.class);
					showData();
//					ImageLoader.getInstance().displayImage(cashPageInfo.getLogo(), mIvBankLogo);
				}else{
					
					ToastUtils.showToast(mContext, parseModel.getMsg());
				}
			}
		});
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
	
	@Override
	public void initListener() {
		mEtMoney.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
//				String money = Double.valueOf(s+"");
				if(s.length()<=0){
					mTvRealMoney.setText("0.00元");
				}else{
					String realMoneyStr = "0.00";
					try{
						double realMoney = Double.valueOf(s.toString());
						if(realMoney<0){
							realMoneyStr = "0.00";
						}else{
							realMoneyStr = realMoney+"";
						}
					}catch(Exception e){
						realMoneyStr = "0.00";
					}
					mTvRealMoney.setText(realMoneyStr+"元");
				}
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		mActionBar.setLeftActionButton(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AppManager.getAppManager().finishActivity();
			}
		});
		mToggleQuan.setOnToggleChanged(new OnToggleChanged(){
            @Override
            public void onToggle(boolean on) {
            	if(on){
            		quanStatus = "1";
            	}else{
            		quanStatus = "0";
            	}
            }
		});
//		mBtnGetCode.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				loadingDialog = new LoadingDialog(mContext);
//				loadingDialog.show();
//				if(mBtnGetCode.isEnabled()){
//					mBtnGetCode.setEnabled(false);
//					ApiOrderUtils.cashCode(mContext, new RequestCallback() {
//						
//						@Override
//						public void execute(ParseModel parseModel) {
//							loadingDialog.cancel();
//							if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
//								regainCode();
//								ToastUtils.showToast(mContext, "请等待接收验证码");
////								sessionId = parseModel.getSession_id();
//							}else{
//								ToastUtils.showToast(mContext, parseModel.getMsg());
//							}
//						}
//					});
//				}
//			}
//		});
		//提现
		mBtnCash.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
//				if(cashPageInfo==null){
//					showUnbindBankCard();
//					return;
//				}
				if(!checkUser()){
					return;
				}
				if(cashPageInfo==null || cashPageInfo.getCard()==null ){
					showUnbindBankCard();
					return ;
				}
//				String msgCode = mEtMsgCode.getText().toString();
				String moneyStr = mEtMoney.getText().toString();
				if(StringUtils.isEmpty(moneyStr)){
					ToastUtils.showToast(mContext, "请输入提现金额");
					mEtMoney.requestFocus();
					return;
				}
				double money = 0;
				try{
					money = Double.valueOf(moneyStr);
					if(money<1){
						ToastUtils.showToast(mContext, "至少提现1元");
						mEtMoney.requestFocus();
						return;
					}
					if(money>Double.valueOf(nowMoney)){
						ToastUtils.showToast(mContext, "输入金额不能大于可提现金额");
						mEtMoney.requestFocus();
						return;
					}
				}catch(Exception e){
					ToastUtils.showToast(mContext, "输入金额有误");
					mEtMoney.requestFocus();
					return;
				}
				
//				if(StringUtils.isEmpty(sessionId)){
//					ToastUtils.showToast(mContext, "请先获取验证码");
//					return;
//				}
//				if(StringUtils.isEmpty(msgCode)){
//					ToastUtils.showToast(mContext, "请输入验证码");
//					mEtMsgCode.requestFocus();
//					return;
//				}
				cash(moneyStr, cashPageInfo.getCard().getBank_card_number(), quanStatus);
			}
		});
	}
	/**
	 * 提现手续费查询
	 */
	private void cash(String money,final String bankCardId,final String useCouponStatus){
		loadingDialog = new LoadingDialog(mContext);
		loadingDialog.show();
		ApiOrderUtils.cash(mContext, money, bankCardId, useCouponStatus, new RequestCallback() {
			@Override
			public void execute(ParseModel parseModel) {
				loadingDialog.cancel();
				if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){//提现成功
					JsonObject data = parseModel.getData().getAsJsonObject();
					if(data!=null){
						String url = data.get("request_url").getAsString();
						String ordId = "";
						if(data.has("id")){
							ordId = data.get("id").getAsString();
							String tradeNo = data.get("trade_no").getAsString();
						}
						Intent intent = new Intent(mContext,WebActivity.class);
						intent.putExtra("type", 4);
						intent.putExtra("url", url);
						intent.putExtra("ordId", ordId);
						intent.putExtra("name", "提现");
						startActivity(intent);
				}else{
					ToastUtils.showToast(mContext, parseModel.getMsg());
				}
			}
			}
		});
	}
	
//	private Timer timer;// 计时器
//	private int time = 60;//倒计时60秒
//
//	private void regainCode() {
//		time = 60;
//		timer = new Timer();
//		timer.schedule(new TimerTask() {
//
//			@Override
//			public void run() {
//				handler.sendEmptyMessage(time--);
//			}
//		}, 0, 1000);
//	}
//
//	private Handler handler = new Handler() {
//		public void handleMessage(android.os.Message msg) {
//			if (msg.what <= 0) {
//				mBtnGetCode.setEnabled(true);
//				mBtnGetCode.setText("获取验证码");
//				timer.cancel();
//			} else {
//				mBtnGetCode.setText(msg.what + "秒重发");
//			}
//		};
//	};

}
