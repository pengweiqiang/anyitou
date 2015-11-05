package cn.com.anyitou.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import cn.com.anyitou.R;

import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.views.ActionBar;

public class RegistereResultActivity extends BaseActivity {
	private ActionBar mActionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_registere_result);
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public void initView() {
		mActionBar = (ActionBar) findViewById(R.id.actionBar);
		onConfigureActionBar(mActionBar);
	}

	protected void onConfigureActionBar(ActionBar actionBar) {
		actionBar.setTitle("注册");
//		actionBar.hideLeftActionButtonText();
		actionBar.setLeftActionButton(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				AppManager.getAppManager().finishActivity();
			}
		});
		
	}
	
	@Override
	public void initListener() {
		// TODO 自动生成的方法存根

	}

}
