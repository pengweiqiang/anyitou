package cn.com.anyitou.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import cn.com.anyitou.R;

import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.User;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.ToastUtils;

public class ModifyGestureDialog extends BaseActivity{
	
	private View mBtnCancel,mBtnConfirm;
	private EditText mEtPwd;
	private TextView mTvTitle;
	User user ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.modify_gesture_password);
		super.onCreate(savedInstanceState);
		user = application.getCurrentUser();
		
		
		initData();
	}
	private void initData(){
		mTvTitle.setText("您好,"+user.getUser_name());
	}
	@Override
	public void initView() {
		mBtnCancel = findViewById(R.id.btn_cancel);
		mBtnConfirm = findViewById(R.id.btn_confirm);
		mEtPwd = (EditText)findViewById(R.id.et_pwd);
		mTvTitle = (TextView)findViewById(R.id.user_title);
	}

	@Override
	public void initListener() {
		mBtnCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AppManager.getAppManager().finishActivity();
			}
		});
		mBtnConfirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String password = mEtPwd.getText().toString().trim();
				if(StringUtils.isEmpty(password)){
					mEtPwd.requestFocus();
					ToastUtils.showToast(mContext, "请输入密码");
					return;
				}
				if(password.equals(user.getPass_word())){
					Intent intent = new Intent(mContext,GestureLockActivity.class);
					intent.putExtra("type", 2);
					startActivity(intent);
					AppManager.getAppManager().finishActivity();
				}else{
					ToastUtils.showToast(mContext, "请输入正确的密码");
				}
			}
		});
	}
	

}
