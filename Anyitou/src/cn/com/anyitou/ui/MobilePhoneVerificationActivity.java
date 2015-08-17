package cn.com.anyitou.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import cn.com.anyitou.R;

import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.views.ActionBar;

public class MobilePhoneVerificationActivity extends BaseActivity {

	private ActionBar actionBar;
	private EditText mEtPhone, mEtCode;
	private View mGetCode, mNowCheck;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_mobile_phone_verification);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void initView() {
		actionBar = (ActionBar) findViewById(R.id.actionBar);
		onConfigureActionBar(actionBar);

		mEtPhone = (EditText) findViewById(R.id.registered_phone);
		mEtCode = (EditText) findViewById(R.id.registered_code);
		mGetCode = findViewById(R.id.get_code);
		mNowCheck = findViewById(R.id.now_check);
	}

	protected void onConfigureActionBar(ActionBar actionBar) {
		actionBar.setTitle("手机验证");
		actionBar.setLeftActionButton(R.drawable.btn_back,
				new OnClickListener() {

					@Override
					public void onClick(View view) {
						AppManager.getAppManager().finishActivity();
					}
				});
//		actionBar.hideLeftActionButtonText();
	}

	@Override
	public void initListener() {
		mGetCode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
			}
		});
		mNowCheck.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
	}

}
