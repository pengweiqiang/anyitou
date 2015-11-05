package cn.com.anyitou.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import cn.com.anyitou.R;

import cn.com.anyitou.api.ApiUserUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.LoadingDialog;

public class RegisteredActivity extends BaseActivity{
	
	private ActionBar mActionBar;
	private View mTvUnRegister;
	private View mRegister_now;
	LoadingDialog loadingDialog ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_registered);
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public void initView() {
		mActionBar = (ActionBar) findViewById(R.id.actionBar);
		onConfigureActionBar(mActionBar);
		mTvUnRegister = findViewById(R.id.un_register);
		mRegister_now = findViewById(R.id.register_now);
	}

	protected void onConfigureActionBar(ActionBar actionBar) {
		actionBar.setTitle("注册");
		actionBar.setLeftActionButton(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				AppManager.getAppManager().finishActivity();
			}
		});
		
	}
	
	@Override
	public void initListener() {
		mRegister_now.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				loadingDialog = new LoadingDialog(mContext);
				loadingDialog.show();
				ApiUserUtils.registerPay(mContext, new RequestCallback() {
					
					@Override
					public void execute(ParseModel parseModel) {
						loadingDialog.cancel();
						if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
//							String hfRegisterUrl = parseModel.getHfRegisterUrl();
							Intent intent = new Intent(mContext,WebActivity.class);
//							intent.putExtra("url", hfRegisterUrl);
							intent.putExtra("name", "注册汇付");
							intent.putExtra("type", 1);
							startActivity(intent);
							AppManager.getAppManager().finishActivity();
						}else{
							ToastUtils.showToast(mContext, parseModel.getMsg());
						}
					}
				});
			}
		});
		//暂不开通
		mTvUnRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(HomeActivity.class);
				AppManager.getAppManager().finishActivity();
			}
		});
	}
	
}
