package cn.com.anyitou.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import android.widget.TextView;
import cn.com.GlobalConfig;
import cn.com.anyitou.R;
import cn.com.anyitou.api.ApiHomeUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.LoadingDialog;
import cn.com.anyitou.views.MyPopupWindow;
import cn.com.gson.JsonObject;
/**
 * 关于我们
 * @author pengweiqiang
 *
 */
public class AboutUsActivity extends BaseActivity {
	
	private ActionBar mActionBar;
	
	private View mBtnCustom,mBtnFeedback;
	
	private TextView mTvContent,mTvWeixin,mTvCallCustom,mTvVersion;
	

	LoadingDialog loadingDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_about_us);
		super.onCreate(savedInstanceState);
		
		
		initData();
	}
	@Override
	public void initView(){
		mBtnCustom = findViewById(R.id.btn_custom);
		mBtnFeedback = findViewById(R.id.btn_feedback);
		mActionBar = (ActionBar) findViewById(R.id.actionBar);
		onConfigureActionBar(mActionBar);
		mTvContent = (TextView) findViewById(R.id.tv_content);
		mTvWeixin = (TextView) findViewById(R.id.tv_weixin);
		mTvCallCustom = (TextView) findViewById(R.id.tv_call_custom);
		mTvVersion = (TextView) findViewById(R.id.version);
	}
	@Override
	public void initListener(){
		mActionBar.setLeftActionButton(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				AppManager.getAppManager().finishActivity();
			}
		});
		mBtnCustom.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				callPhone(v);
			}
		});
		
		mBtnFeedback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext,FeedBackActivity.class);
				startActivity(intent);
			}
		});
	}
	
	private void initData(){
		mTvVersion.setText(GlobalConfig.VERSION_NAME_V);
		loadingDialog = new LoadingDialog(mContext);
		loadingDialog.show();
		ApiHomeUtils.getIntroduction(mContext, new RequestCallback() {
			
			@Override
			public void execute(ParseModel parseModel) {
				loadingDialog.cancel();
				if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
					JsonObject dataObject = parseModel.getData().getAsJsonObject();
					if(dataObject != null){
						if(!StringUtils.isEmpty(dataObject.get("content").getAsString())){
							mTvContent.setText(dataObject.get("content").getAsString());
						}
						if(!StringUtils.isEmpty(dataObject.get("weixin").getAsString())){
							mTvWeixin.setText(dataObject.get("weixin").getAsString());
						}
						if(!StringUtils.isEmpty(dataObject.get("service_number").getAsString())){
							mTvCallCustom.setText(dataObject.get("service_number").getAsString());
						}
					}
				}
			}
		});
	}
	

	// 设置activity的导航条
	protected void onConfigureActionBar(ActionBar actionBar) {
		actionBar.setTitle(getResources().getString(R.string.tab_about_us));
//		findViewById(R.id.actionBarLayout).setBackgroundColor(
//				getResources().getColor(R.color.app_bg_color));
	}
	
	private PopupWindow popupWindow;
	private TextView confirmTextView,cancleTextView;
	@SuppressWarnings("deprecation")
	public void callPhone(View view){
		popupWindow = MyPopupWindow.getPopupWindow(R.layout.call_phone_popupwindow, AboutUsActivity.this);
		confirmTextView = (TextView) popupWindow.getContentView().findViewById(R.id.confirmTextView);
		confirmTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				popupWindow.dismiss();
				// 拨打电话
				// 用intent启动拨打电话
				Intent intent = new Intent(Intent.ACTION_CALL, Uri
						.parse("tel:" + getResources().getString(R.string.call_custom)));
				startActivity(intent);
			}
		});
		popupWindow.showAtLocation(view, Gravity.BOTTOM, 0,0);
		cancleTextView = (TextView) popupWindow.getContentView().findViewById(R.id.cancleTextView);
		cancleTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				popupWindow.dismiss();
			}
		});
	}

}
