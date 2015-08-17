package cn.com.anyitou.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import cn.com.anyitou.R;

import cn.com.anyitou.api.ApiUserUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.entity.User;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.utils.CheckInputUtil;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.LoadingDialog;

public class ModifyLoginPassWordActivity extends BaseActivity {
	ActionBar actionBar;
	private EditText mEtOldPwd,mEtNewPwd,mEtNewPwd2;
	private View mBtnConfirm;
	LoadingDialog loadingDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_modify_loginpassword);
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public void initView() {
		actionBar = (ActionBar)findViewById(R.id.actionBar);
		actionBar.setTitle("修改密码");
		actionBar.setLeftActionButton(R.drawable.btn_back, new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				AppManager.getAppManager().finishActivity();
			}
		});
		mEtOldPwd = (EditText)findViewById(R.id.old_pwd);
		mEtNewPwd = (EditText)findViewById(R.id.new_pwd);
		mEtNewPwd2 = (EditText)findViewById(R.id.new_pwd2);
		mBtnConfirm = findViewById(R.id.btn_confirm);
	}

	@Override
	public void initListener() {
		mBtnConfirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String oldPwd = mEtOldPwd.getText().toString().trim();
				
				if(StringUtils.isEmpty(oldPwd)){
					ToastUtils.showToast(mContext, mContext.getResources().getString(R.string.input_old_pwd));
					mEtOldPwd.requestFocus();
					return;
				}
				if(!CheckInputUtil.checkPassword(oldPwd, mContext)){
					return;
				}
				
				String newPwd = mEtNewPwd.getText().toString().trim();
				
				if(StringUtils.isEmpty(newPwd)){
					ToastUtils.showToast(mContext, mContext.getResources().getString(R.string.input_new_pwd));
					mEtNewPwd.requestFocus();
					return;
				}
				if(!CheckInputUtil.checkPassword(newPwd, mContext)){
					return;
				}
				
				String newPwd2 = mEtNewPwd2.getText().toString().trim();
				if(StringUtils.isEmpty(newPwd2)){
					ToastUtils.showToast(mContext, mContext.getResources().getString(R.string.input_correct_pwd));
					mEtNewPwd2.requestFocus();
					return;
				}
				if(!CheckInputUtil.checkPassword(newPwd2, mContext)){
					return;
				}
				if(!newPwd.equals(newPwd2)){
					ToastUtils.showToast(mContext, mContext.getResources().getString(R.string.error_pwd_again));
					mEtNewPwd2.requestFocus();
					return;
				}
				loadingDialog = new LoadingDialog(mContext,"修改密码中...");
				loadingDialog.show();
				User user = application.getCurrentUser();
				ApiUserUtils.updatePwd(mContext, user.getUser_name(), oldPwd, newPwd2, new RequestCallback() {
					
					@Override
					public void execute(ParseModel parseModel) {
						loadingDialog.cancel();
						if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getStatus())){
							ToastUtils.showToast(mContext,"修改成功,请重新登录");
							logOut();
							startActivity(LoginActivity.class);
							AppManager.getAppManager().finishActivity();
						}else{
							ToastUtils.showToast(mContext, StringUtils.isEmpty(parseModel.getDesc())?"修改失败,稍后请重试":parseModel.getDesc());
						}
					}
				});
				
				
			}
		});
	}

}
