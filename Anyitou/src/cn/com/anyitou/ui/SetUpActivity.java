package cn.com.anyitou.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import cn.com.anyitou.R;

import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.views.ActionBar;
/**
 * 设置
 * @author pengweiqiang
 *
 */
public class SetUpActivity extends BaseActivity {
	ActionBar actionBar;
	private View mGesture;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_set_up);
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public void initView() {
		actionBar = (ActionBar)findViewById(R.id.actionBar);
		actionBar.setTitle("设置");
		actionBar.setLeftActionButton(R.drawable.nav_menu, new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				AppManager.getAppManager().finishActivity();
			}
		});
		actionBar.hideLeftActionButtonText();
		
		mGesture = findViewById(R.id.btn_gesture);
		
	}

	@Override
	public void initListener() {
		mGesture.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext,GestureLockActivity.class);
				startActivity(intent);
			}
		});
	}

}
