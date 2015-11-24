package cn.com.anyitou.ui;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.api.ApiMessageUtils;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.Message;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.Log;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.LoadingDialog;

/**
 * 消息正文
 * 
 * @author will
 * 
 */
public class MessageDetailActivity extends BaseActivity {

	ActionBar mActionBar;
	LoadingDialog loadingDialog;
	private Message message;
	private TextView mTvInfo;
	private TextView mTvDate;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_message_detail);
		super.onCreate(savedInstanceState);
		message = (Message)this.getIntent().getSerializableExtra("message");
		showData();
		
	}

	// 设置activity的导航条
	protected void onConfigureActionBar(ActionBar actionBar) {
		actionBar.setTitle("正文");
	}

	@Override
	public void initView() {
		mActionBar = (ActionBar)findViewById(R.id.actionBar);
		onConfigureActionBar(mActionBar);
		mTvInfo = (TextView) findViewById(R.id.content);
		mTvDate = (TextView) findViewById(R.id.date);
	}
	private void showData(){
		if(message!=null){
			mTvInfo.setText(Html.fromHtml(message.getContent()));
			mTvDate.setText(message.getTime());
			
			updateMessage();
		}
	}
	@Override
	public void initListener() {
		mActionBar.setLeftActionButton(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AppManager.getAppManager().finishActivity();
			}
		});
		
	}
	
	private void updateMessage(){
		ApiMessageUtils.messageUpdate(mContext, message.getId(), new RequestCallback() {
			
			@Override
			public void execute(ParseModel parseModel) {
				Log.i(TAG, parseModel.getData().toString());
//				System.out.println(parseModel.getData().toString());
			}
		});
	}

}
