package cn.com.anyitou.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import cn.com.anyitou.R;

import cn.com.anyitou.api.ApiOrderUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.LoadingDialog;
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
	private TextView mTvMoney;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_recharge);
		super.onCreate(savedInstanceState);
		money = this.getIntent().getStringExtra("money");//账户余额
		
		mTvMoney.setText(money);
	}
	
	@Override
	public void initView() {
		mActionBar = (ActionBar) findViewById(R.id.actionBar);
		onConfigureActionBar(mActionBar);
		
		mBtnRechange = findViewById(R.id.btn_rechange);
		mEtMoney = (EditText) findViewById(R.id.rechange_money);
		mTvMoney = (TextView)findViewById(R.id.money);
	}

	protected void onConfigureActionBar(ActionBar actionBar) {
		actionBar.setTitle("充值");
		actionBar.setLeftActionButton(R.drawable.btn_back, new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				AppManager.getAppManager().finishActivity();
			}
		});
		
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
					double money = Double.valueOf(moneyStr);
					if(money<1){
						ToastUtils.showToast(mContext, "至少充值1元");
						mEtMoney.requestFocus();
						return;
					}
				}catch(Exception e){
					ToastUtils.showToast(mContext, "输入金额有误");
					mEtMoney.requestFocus();
					return;
				}
				loadingDialog = new LoadingDialog(mContext);
				loadingDialog.show();
				ApiOrderUtils.reCharge(mContext,moneyStr, new RequestCallback() {
					
					@Override
					public void execute(ParseModel parseModel) {
						loadingDialog.cancel();
						if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getStatus())){
							if(!StringUtils.isEmpty(parseModel.getToken())){
								logined(parseModel.getToken(), null);//刷新token
							}
							String url = parseModel.getUrl();
							Intent intent = new Intent(mContext,WebActivity.class);
							intent.putExtra("url", url);
							intent.putExtra("name", "充值");
							intent.putExtra("type", 2);
							intent.putExtra("ordId", parseModel.getOrdId());
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

}
