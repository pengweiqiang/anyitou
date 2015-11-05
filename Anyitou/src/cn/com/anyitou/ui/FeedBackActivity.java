package cn.com.anyitou.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import cn.com.anyitou.R;
import cn.com.anyitou.api.ApiHomeUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.utils.HttpConnectionUtil;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.LoadingDialog;

/**
 * 反馈意见
 * 
 * @author will
 * 
 */
public class FeedBackActivity extends BaseActivity {

	ActionBar mActionBar;
	private EditText mEtSuggestion,mEtEmail;
	private Button btn_submit;
	private LoadingDialog loading;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.feed_back);
		super.onCreate(savedInstanceState);
	}

	// 设置activity的导航条
	protected void onConfigureActionBar(ActionBar actionBar) {
		actionBar.setTitle("意见反馈");
	}

	@Override
	public void initView() {
		mActionBar = (ActionBar)findViewById(R.id.actionBar);
		onConfigureActionBar(mActionBar);
		mEtSuggestion = (EditText)findViewById(R.id.suggestion);
		btn_submit = (Button)findViewById(R.id.btn_submit);
		mEtEmail = (EditText)findViewById(R.id.email);
	}

	@Override
	public void initListener() {
		mActionBar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AppManager.getAppManager().finishActivity();
			}
		});
		
		btn_submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String suggestion = mEtSuggestion.getText().toString().trim();
				if(StringUtils.isEmpty(suggestion)){
					mEtSuggestion.requestFocus();
					ToastUtils.showToast(FeedBackActivity.this, "请输入您的宝贵意见");
					return ;
				}
				if(suggestion.length()>100){
					ToastUtils.showToast(FeedBackActivity.this, "字数不能超过100字");
					return ;
				}
				String email = mEtEmail.getText().toString().trim();
				loading = new LoadingDialog(mContext);
				loading.show();
				ApiHomeUtils.report(FeedBackActivity.this, email, suggestion, new HttpConnectionUtil.RequestCallback(){

							@Override
							public void execute(ParseModel parseModel) {
								loading.cancel();
								if (!ApiConstants.RESULT_SUCCESS.equals(parseModel
										.getCode())) {
									ToastUtils.showToast(FeedBackActivity.this, parseModel.getMsg());
								}else{
									ToastUtils.showToast(FeedBackActivity.this, "感谢您提出宝贵的意见");
									AppManager.getAppManager().finishActivity();
								}
							}
					
					
				});
			}
		});
	}

}
