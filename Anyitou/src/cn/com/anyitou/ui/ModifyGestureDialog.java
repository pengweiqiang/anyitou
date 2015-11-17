package cn.com.anyitou.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import cn.com.anyitou.R;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.User;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.utils.MD5Util;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;

/**
 * 修改手势密码--》验证登陆密码
 * @author pengweiqiang
 *
 */
public class ModifyGestureDialog extends BaseActivity{
	
	private View mBtnConfirm;
	private EditText mEtPwd;
	ActionBar mActionBar;
	User user ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.modify_gesture_password);
		super.onCreate(savedInstanceState);
		user = application.getCurrentUser();
	}
	@Override
	public void initView() {
		mBtnConfirm = findViewById(R.id.btn_confirm);
		mEtPwd = (EditText)findViewById(R.id.et_pwd);
		mActionBar = (ActionBar)findViewById(R.id.actionBar);
		mActionBar.setTitle("修改手势密码");
	}

	@Override
	public void initListener() {
		mActionBar.setLeftActionButton(new OnClickListener() {
			
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
				password = MD5Util.MD5(password);
				if(user!=null && password.equals(user.getPassword())){
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
