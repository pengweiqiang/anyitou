package cn.com.anyitou.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import cn.com.anyitou.R;

import cn.com.anyitou.api.ApiUserUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.utils.CheckInputUtil;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.LoadingDialog;

public class BackLoginPassWordActivity extends BaseActivity{
	
	ActionBar mActionBar;
	private View mBtnNext,mBtnRegister;
	private LoadingDialog loadingDialog;
	private EditText mEtUserName,mEtPhone;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_back_loginpassword);
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public void initView() {
		mActionBar = (ActionBar)findViewById(R.id.actionBar);
		mActionBar.setTitle("找回密码");
		mActionBar.setLeftActionButton(R.drawable.btn_back, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AppManager.getAppManager().finishActivity();
			}
		});
		mBtnNext = findViewById(R.id.btn_next);
		mBtnRegister = findViewById(R.id.to_register);
		mEtUserName = (EditText)findViewById(R.id.username);
		mEtPhone = (EditText)findViewById(R.id.phone);
	}

	@Override
	public void initListener() {
		//下一步
		mBtnNext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String userName = mEtUserName.getText().toString().trim();
				
				if(StringUtils.isEmpty(userName)){
					ToastUtils.showToast(mContext, "请输入用户名");
					mEtUserName.requestFocus();
					return;
				}
				if(!CheckInputUtil.checkUser(userName, mContext)){
					return;
				}
				
				final String phone = mEtPhone.getText().toString().trim();
				
				if(StringUtils.isEmpty(phone)){
					ToastUtils.showToast(mContext, mContext.getResources().getString(R.string.input_phone));
					mEtPhone.requestFocus();
					return;
				}
				if(!CheckInputUtil.checkPhone(phone, mContext)){
					return;
				}
				loadingDialog = new LoadingDialog(mContext);
				loadingDialog.show();
				ApiUserUtils.getPwdSendCode(mContext, phone, new RequestCallback() {
					
					@Override
					public void execute(ParseModel parseModel) {
						loadingDialog.cancel();
						if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
							Intent intent = new Intent(mContext,BackLoginPWVCodeActivity.class);
							intent.putExtra("sessionId", parseModel.getSession_id());
							intent.putExtra("phone", phone);
							startActivity(intent);
							AppManager.getAppManager().finishActivity();
						}else{
							ToastUtils.showToast(mContext, parseModel.getMsg());
						}
					}
				});
				
			}
		});
		mBtnRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(RegisteredAccountActivity.class);
			}
		});
	}

}
