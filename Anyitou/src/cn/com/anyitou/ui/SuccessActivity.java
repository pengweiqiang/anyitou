package cn.com.anyitou.ui;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.ui.base.BaseActivity;

/**
 * 成功页面
 * 
 * @author will
 * 
 */
public class SuccessActivity extends BaseActivity {

	String message ;
	TextView mTvMessage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.success_layout);
		super.onCreate(savedInstanceState);
		message = this.getIntent().getStringExtra("message");
		mTvMessage.setText(message);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		AppManager.getAppManager().finishActivity(this);
		return super.onTouchEvent(event);
	}


	@Override
	public void initView() {
		mTvMessage = (TextView)findViewById(R.id.message);
	}

	@Override
	public void initListener() {
		
	}

}
