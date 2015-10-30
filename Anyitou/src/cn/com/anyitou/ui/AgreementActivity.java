package cn.com.anyitou.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import cn.com.anyitou.R;

import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.views.ActionBar;
/**
 * 协议
 * @author will
 *
 */
public class AgreementActivity extends BaseActivity {
	
	private ActionBar mActionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_agree);
		super.onCreate(savedInstanceState);
		
	}
	
	
	@Override
	public void initView() {
		mActionBar = (ActionBar)findViewById(R.id.actionBar);
		mActionBar.setTitle("用户协议");
		mActionBar.setLeftActionButton(R.drawable.btn_back, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AppManager.getAppManager().finishActivity();
			}
		});
	}

	@Override
	public void initListener() {
		
	}

}
