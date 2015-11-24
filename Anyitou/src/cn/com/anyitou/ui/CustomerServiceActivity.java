package cn.com.anyitou.ui;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import android.widget.TextView;

import cn.com.anyitou.R;

import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.views.ActionBar;

/**
 * 客服服务
 * 
 * @author will
 * 
 */
@SuppressLint("NewApi")
public class CustomerServiceActivity extends BaseActivity {

	ActionBar mActionBar;
	View mCall;
	private PopupWindow popupWindow;
	private View popupWindowView;
	TextView confirmTextView, cancleTextView,copyWeixin,gongzong,qiyeQQ,qiyeYouXiang;
	TextView custNum;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_customer_service);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void initView() {
		mActionBar = (ActionBar) findViewById(R.id.actionBar);
		mActionBar.setTitle("客户服务");
		mActionBar.setLeftActionButton(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						AppManager.getAppManager().finishActivity();
					}
				});

//		mActionBar.hideLeftActionButtonText();

		mCall = findViewById(R.id.call_button);
		custNum = (TextView)findViewById(R.id.cust_num);
		
		copyWeixin = (TextView) findViewById(R.id.copyWeixin);
		gongzong = (TextView) findViewById(R.id.gongzong);
		qiyeQQ = (TextView) findViewById(R.id.qiyeQQ);
		qiyeYouXiang = (TextView) findViewById(R.id.qiyeYouXiang);
		
	}

	@Override
	public void initListener() {
		mActionBar.setLeftActionButton(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AppManager.getAppManager().finishActivity();
			}
		});
		mCall.setOnClickListener(new OnClickListener() {
			@SuppressLint("InlinedApi")
			@Override
			public void onClick(View view) {
				// TODO 自动生成的方法存根
				callPhone(view);
			}
		});
	}
	
	public void weixinCopy(View view){
		 String weixin = "zjxd_18link";
		 copyWeixin.setVisibility(View.VISIBLE);
		 gongzong.setVisibility(View.GONE);
		 qiyeQQ.setVisibility(View.GONE);
		 qiyeYouXiang.setVisibility(View.GONE);
		 Copy(weixin);
	}
	
	public void PublicAccountCopy(View view){
		String PublicAccount = "http://weibo.com/18link";
		gongzong.setVisibility(View.VISIBLE);
		copyWeixin.setVisibility(View.GONE);
		qiyeQQ.setVisibility(View.GONE);
		qiyeYouXiang.setVisibility(View.GONE);
		Copy(PublicAccount);
	}
	
	public void EnterpriseQQCopy(View view){
		String EnterpriseQQ = "2488495574";
		qiyeQQ.setVisibility(View.VISIBLE);
		copyWeixin.setVisibility(View.GONE);
		gongzong.setVisibility(View.GONE);
		qiyeYouXiang.setVisibility(View.GONE);
		Copy(EnterpriseQQ);
	}
	
	public void EnterpriseMailboxCopy(View view){
		String EnterpriseMailbox = "service@18link.com";
		qiyeYouXiang.setVisibility(View.VISIBLE);
		copyWeixin.setVisibility(View.GONE);
		gongzong.setVisibility(View.GONE);
		qiyeQQ.setVisibility(View.GONE);
		Copy(EnterpriseMailbox);
	}
	
	
	@SuppressWarnings("deprecation")
	public void callPhone(View view){
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		popupWindowView = inflater.inflate(R.layout.call_phone_popupwindow, null);
		popupWindow = new PopupWindow(popupWindowView,
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT,
				true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		// 设置PopupWindow的弹出和消失效果
		popupWindow.setAnimationStyle(R.style.popupAnimation);
		confirmTextView = (TextView) popupWindowView
				.findViewById(R.id.confirmTextView);
		confirmTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// 拨打电话
				// 用intent启动拨打电话
				Intent intent = new Intent(Intent.ACTION_CALL, Uri
						.parse("tel:" + custNum.getText().toString()));
				startActivity(intent);
			}
		});
		popupWindow.showAtLocation(confirmTextView, Gravity.CENTER, 0,
				0);
		cancleTextView = (TextView) popupWindowView
				.findViewById(R.id.cancleTextView);
		cancleTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				// 关闭popupWindow.dismiss();
				popupWindow.dismiss();
			}
		});
	}
	
	@SuppressWarnings("deprecation")
	public void Copy(String content){
		int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt > Build.VERSION_CODES.HONEYCOMB) {// api11
                ClipboardManager copy = (ClipboardManager) CustomerServiceActivity.this
                                .getSystemService(Context.CLIPBOARD_SERVICE);
                copy.setText(content);
        } else if (sdkInt <= Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager copyq = (android.text.ClipboardManager) CustomerServiceActivity.this
                                .getSystemService(Context.CLIPBOARD_SERVICE);
                copyq.setText(content);
        }
	}

}
