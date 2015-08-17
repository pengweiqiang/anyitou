package cn.com.anyitou.ui;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import cn.com.anyitou.R;

import cn.com.anyitou.api.ApiOrderUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.CashFee;
import cn.com.anyitou.entity.CashPageInfo;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.JsonUtils;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.LoadingDialog;
import cn.com.universalimageloader.core.ImageLoader;

/**
 * 提现
 * @author will
 *
 */
public class WithdrawalsActivity extends BaseActivity {

	private ActionBar mActionBar;
	private EditText mEtMoney,mEtMsgCode;
	private View mBtnCash;
	private TextView mBtnGetCode;
	private LoadingDialog loadingDialog;
	
	private String sessionId = "";
	private TextView mTvMoney;
	String nowMoney = "";//当前账户余额
	private CashPageInfo cashPageInfo;//提现界面信息
	
	private TextView mTvCardNo;
	private ImageView mIvBankLogo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_withdrawals);
		super.onCreate(savedInstanceState);
		
		nowMoney = this.getIntent().getStringExtra("money");
		mTvMoney.setText(nowMoney);
		
		initData();
	}
	
	@Override
	public void initView() {
		mActionBar = (ActionBar) findViewById(R.id.actionBar);
		onConfigureActionBar(mActionBar);
		
		mEtMoney = (EditText)findViewById(R.id.money);
		mEtMsgCode = (EditText)findViewById(R.id.msg_code);
		mBtnCash = findViewById(R.id.btn_cash);
		mBtnGetCode = (TextView)findViewById(R.id.get_code);
		mTvMoney = (TextView)findViewById(R.id.now_money);
		mTvCardNo = (TextView)findViewById(R.id.card_no);
		mIvBankLogo = (ImageView)findViewById(R.id.bank_logo);
	}

	protected void onConfigureActionBar(ActionBar actionBar) {
		actionBar.setTitle("提现");
		actionBar.setLeftActionButton(R.drawable.btn_back, new OnClickListener() {
			
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
		ApiOrderUtils.cashPage(mContext, new RequestCallback() {
			
			@Override
			public void execute(ParseModel parseModel) {
				loadingDialog.cancel();
				if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getStatus())){
					logined(parseModel.getToken(), null);
					cashPageInfo = JsonUtils.fromJson(parseModel.getData().toString(), CashPageInfo.class);
					mTvMoney.setText(cashPageInfo.getAvl_bal());
					mTvCardNo.setText("默认帐号：**** **** **** "+cashPageInfo.getIdentity().substring(cashPageInfo.getIdentity().length()-4));
					ImageLoader.getInstance().displayImage(cashPageInfo.getLogo(), mIvBankLogo);
				}else{
					ToastUtils.showToast(mContext, parseModel.getDesc());
				}
			}
		});
	}
	
	
	@Override
	public void initListener() {
		mBtnGetCode.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				loadingDialog = new LoadingDialog(mContext);
				loadingDialog.show();
				if(mBtnGetCode.isEnabled()){
					mBtnGetCode.setEnabled(false);
					ApiOrderUtils.cashCode(mContext, new RequestCallback() {
						
						@Override
						public void execute(ParseModel parseModel) {
							loadingDialog.cancel();
							if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getStatus())){
								regainCode();
								ToastUtils.showToast(mContext, "请等待接收验证码");
								sessionId = parseModel.getSession_id();
							}else{
								ToastUtils.showToast(mContext, parseModel.getDesc());
							}
						}
					});
				}
			}
		});
		//提现
		mBtnCash.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String msgCode = mEtMsgCode.getText().toString();
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
						ToastUtils.showToast(mContext, "提现金额大于账户余额");
						mEtMoney.requestFocus();
						return;
					}
				}catch(Exception e){
					ToastUtils.showToast(mContext, "输入金额有误");
					mEtMoney.requestFocus();
					return;
				}
				
				if(StringUtils.isEmpty(sessionId)){
					ToastUtils.showToast(mContext, "请先获取验证码");
					return;
				}
				if(StringUtils.isEmpty(msgCode)){
					ToastUtils.showToast(mContext, "请输入验证码");
					mEtMsgCode.requestFocus();
					return;
				}
				
				getCashCheckMoney(moneyStr, sessionId, msgCode);
			}
		});
	}
	/**
	 * 提现手续费查询
	 */
	private void getCashCheckMoney(String money,final String sessionId,final String msgCode){
		loadingDialog = new LoadingDialog(mContext);
		loadingDialog.show();
		ApiOrderUtils.checkMoney(mContext, money, sessionId, msgCode, new RequestCallback() {
			
			@Override
			public void execute(ParseModel parseModel) {
				loadingDialog.cancel();
				if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getStatus())){//查询手续费
					CashFee cashFee = (CashFee)JsonUtils.fromJson(parseModel.getOtherStr(), CashFee.class);
					if(cashFee==null || "2".equals(cashFee.getError())){
						ToastUtils.showToast(mContext, "提现失败");
						return;
					}
					Intent intent = new Intent(mContext,WithdrawalsDialogActivity.class);
					intent.putExtra("cashFee", cashFee);
					intent.putExtra("msgCode", msgCode);
					intent.putExtra("sessionId", sessionId);
					startActivityForResult(intent, 0);
				}else{
					ToastUtils.showToast(mContext, parseModel.getDesc());
				}
			}
		});
	}
	
	private Timer timer;// 计时器
	private int time = 60;//倒计时60秒

	private void regainCode() {
		time = 60;
		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				handler.sendEmptyMessage(time--);
			}
		}, 0, 1000);
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what <= 0) {
				mBtnGetCode.setEnabled(true);
				mBtnGetCode.setText("获取验证码");
				timer.cancel();
			} else {
				mBtnGetCode.setText(msg.what + "秒重发");
			}
		};
	};

}
