package cn.com.anyitou.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import cn.com.anyitou.R;

import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.views.ActionBar;

/**
 * 充值结果
 * @author will
 *
 */
public class RechargeResultActivity extends BaseActivity {
	private ActionBar mActionBar;
	private View mBtnResumeRechange,mBtnBackMyInvest;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_recharge_result);
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public void initView() {
		mActionBar = (ActionBar) findViewById(R.id.actionBar);
		onConfigureActionBar(mActionBar);
		mBtnResumeRechange = findViewById(R.id.resume_rechange);
		mBtnBackMyInvest = findViewById(R.id.btn_back_my_invest);
	}

	@Override
	public void initListener() {
		//返回我的资产
		mBtnBackMyInvest.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				startActivity(MainActivity.class);
			}
		});
		mBtnResumeRechange.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				startActivity(RechargeActivity.class);
			}
		});
	}
	
	protected void onConfigureActionBar(ActionBar actionBar) {
		actionBar.setTitle("充值结果");
		actionBar.setLeftActionButton(R.drawable.btn_back, new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				AppManager.getAppManager().finishActivity();
			}
		});
		
	}

}
