package cn.com.anyitou.ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import cn.com.anyitou.R;

import cn.com.anyitou.api.ApiOrderUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.CashFee;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.LoadingDialog;

/**
 * 提现手续费界面
 * @author will
 *
 */
public class WithdrawalsDialogActivity extends BaseActivity {

	private TextView mTvCashMoney;//提现金额
	private TextView mTvCashFee;//提现手续费
	private TextView mTvThirdMoney;//第三方资金托管费
	private TextView mTvCalTip;
	private TextView mTvArrivalDate;//到账日期
	private CashFee cashFee;
	private View mTvToCash,mTvCancel;
	private LoadingDialog loadingDialog;
	private String sessionId = "",msgCode = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.withdrawals_dialog);
		super.onCreate(savedInstanceState);
		
		cashFee = (CashFee)this.getIntent().getSerializableExtra("cashFee");
		sessionId = this.getIntent().getStringExtra("sessionId");
		msgCode =this.getIntent().getStringExtra("msgCode");
		
		initData();
	}

	@Override
	public void initView() {
		mTvCashMoney = (TextView)findViewById(R.id.cash_money);
		mTvCashFee = (TextView)findViewById(R.id.cash_fee);
		mTvThirdMoney = (TextView)findViewById(R.id.third_money);
		mTvArrivalDate = (TextView)findViewById(R.id.arrival_date);
		mTvCalTip =(TextView)findViewById(R.id.cal_tip);
		mTvToCash = findViewById(R.id.confirm);
		mTvCancel = findViewById(R.id.cancel);
	}
	
	private void initData(){
		mTvCashMoney.setText(cashFee.getMoney()+"元");
		mTvCashFee.setText(cashFee.getHf_serv()+"元");
		mTvThirdMoney.setText(cashFee.getServ_fee()+"元");
		mTvArrivalDate.setText(cashFee.getGetdate());
		String tip = "( "+cashFee.getMoney()+" - "+" ( "+cashFee.getRepaymoney()+" - "+cashFee.getRepaymoney()+" ) )*2.5‰";
		mTvCalTip.setText(tip);
	}

	@Override
	public void initListener() {
		mTvToCash.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				loadingDialog = new LoadingDialog(mContext);
				loadingDialog.show();
				ApiOrderUtils.cash(mContext, cashFee.getMoney(), sessionId, msgCode, new RequestCallback() {
					@Override
					public void execute(ParseModel parseModel) {
						loadingDialog.cancel();
						if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){//提现成功
							ToastUtils.showToast(mContext, "提现成功，2秒后返回我的资产");
							logined(parseModel.getToken(), null);
							new Handler().postDelayed(new Runnable() {
								public void run() {
									application.refresh = ApiConstants.TYPE_CASH;
									startActivity(MainActivity.class);
									AppManager.getAppManager().finishActivity();
								} 
							}, 2000);
						}else{
							ToastUtils.showToast(mContext, parseModel.getMsg());
						}
					}
				});
			}
		});
		mTvCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				AppManager.getAppManager().finishActivity();
			}
		});
		
	}
	

}
