package cn.com.anyitou.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import cn.com.anyitou.R;

import cn.com.anyitou.api.ApiUserUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.LoadingDialog;

public class InputNewPasswordActivity extends BaseActivity {

	ActionBar mActionBar;
	String sessionId = "";
	private EditText mEtNewPwd, mEtNewPwd2;

	private TextView mTvFinish;
	private LoadingDialog loadingDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_input_password);
		super.onCreate(savedInstanceState);

		sessionId = this.getIntent().getStringExtra("sessionId");

	}

	@Override
	public void initView() {
		mActionBar = (ActionBar) findViewById(R.id.actionBar);
		mActionBar.setTitle("找回密码");
		mActionBar.setLeftActionButton(R.drawable.btn_back,
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						AppManager.getAppManager().finishActivity();
					}
				});
		mEtNewPwd = (EditText) findViewById(R.id.new_pwd);
		mEtNewPwd2 = (EditText) findViewById(R.id.new_pwd2);
		mTvFinish = (TextView) findViewById(R.id.btn_finish);
	}

	@Override
	public void initListener() {
		mTvFinish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String newPwd = mEtNewPwd.getText().toString().trim();
				if (StringUtils.isEmpty(newPwd)) {
					ToastUtils.showToast(mContext, "请输入新密码");
					mEtNewPwd.requestFocus();
					return;
				}
				String newPwd2 = mEtNewPwd2.getText().toString().trim();
				if (StringUtils.isEmpty(newPwd2)) {
					ToastUtils.showToast(mContext, "请再次输入新密码");
					mEtNewPwd2.requestFocus();
					return;
				}
				
				if(!newPwd.equals(newPwd2)){
					ToastUtils.showToast(mContext, mContext.getResources().getString(R.string.error_pwd_again));
					mEtNewPwd2.requestFocus();
					return;
				}
				loadingDialog = new LoadingDialog(mContext);
				loadingDialog.show();
				ApiUserUtils.getPwd(mContext, sessionId, newPwd, newPwd2, new RequestCallback() {
					
					@Override
					public void execute(ParseModel parseModel) {
						loadingDialog.cancel();
						if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
							ToastUtils.showToast(mContext, parseModel.getMsg());
							startActivity(LoginActivity.class);
							AppManager.getAppManager().finishActivity();
						}else{
							ToastUtils.showToast(mContext, parseModel.getMsg());
						}
					}
				});


			}
		});
	}

}
