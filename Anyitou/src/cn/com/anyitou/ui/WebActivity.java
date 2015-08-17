package cn.com.anyitou.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import cn.com.anyitou.R;

import cn.com.anyitou.api.ApiOrderUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.entity.User;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.LoadingDialog;

public class WebActivity extends BaseActivity {

	private WebView webView;
	private ActionBar mActionBar;
	private String name;
	private String url;
	private ProgressBar progressBar;
	Object mJsObj = new JSInterface();
	LoadingDialog loadingDialog;
	int type; // 1代表注册汇付操作  2 充值操作 3投资操作
	private String ordId = "";//充值订单号
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.link_web);
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		url = intent.getStringExtra("url");
		name = intent.getStringExtra("name");
		type = intent.getIntExtra("type", 0);
		ordId = intent.getStringExtra("ordId");
		onConfigureActionBar(mActionBar);

		webView.setWebViewClient(new WebViewClient() { // 通过webView打开链接，不调用系统浏览器

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				
				return true;
			}
		});

		webView.setInitialScale(25);
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setBuiltInZoomControls(true);
		webSettings.setSupportZoom(true);

		webView.getSettings().setUseWideViewPort(true);
		webView.getSettings().setLoadWithOverviewMode(true);

		webView.loadUrl(url);

		WebChromeClient webChromeClient = new WebChromeClient() {

			@Override
			public void onReceivedTitle(WebView view, String str) {
				super.onReceivedTitle(view, str);
				mActionBar.setTitle(StringUtils.isEmpty(str)?name:str);
			}

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				super.onProgressChanged(view, newProgress);
				if (newProgress == 100) {
					/*if("1818平台".equals(title)){
						startActivity(MainActivity.class);
						AppManager.getAppManager().finishActivity();
					}*/
	                progressBar.setVisibility(View.GONE);
	            } else {
	                if (progressBar.getVisibility() == View.GONE)
	                	progressBar.setVisibility(View.VISIBLE);
	                progressBar.setProgress(newProgress);
	            }
			}
			
			
		};
		webView.setWebChromeClient(webChromeClient);

	}

	@Override
	public void initView() {
		mActionBar = (ActionBar) findViewById(R.id.actionBar);

		webView = (WebView) findViewById(R.id.webview);
		progressBar = (ProgressBar)findViewById(R.id.web_progress);
	}

	@Override
	public void initListener() {

	}

	// 设置activity的导航条
	protected void onConfigureActionBar(ActionBar actionBar) {
		actionBar.setTitle(name);
		actionBar.setLeftActionButton(R.drawable.btn_back, new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				loadingDialog = new LoadingDialog(mContext,"请稍候...");
				loadingDialog.show();
				backOperation();
			}
		});
	}
	/**
	 * 点击返回按钮操作
	 */
	private void backOperation(){
		if(type == 1){//注册汇付结果
			ApiOrderUtils.getRegisterPayResult(mContext, new RequestCallback() {
				@Override
				public void execute(ParseModel parseModel) {
					loadingDialog.cancel();
					if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getStatus())){//注册汇付成功
						User user = application.getCurrentUser();
						user.setIshfuser("1");
						logined("", user);
						startActivity(MainActivity.class);
						AppManager.getAppManager().finishActivity();
					}else{
						ToastUtils.showToast(mContext, parseModel.getDesc());
						AppManager.getAppManager().finishActivity();
					}
				}
			});
		}else if(type == 2){//充值结果
			ApiOrderUtils.getReChargeResult(mContext,ordId, new RequestCallback() {
				@Override
				public void execute(ParseModel parseModel) {
					loadingDialog.cancel();
					if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getStatus())){//充值成功
						application.refresh = ApiConstants.TYPE_RECHARGE;
						startActivity(MainActivity.class);
						AppManager.getAppManager().finishActivity();
					}else{
						ToastUtils.showToast(mContext, parseModel.getDesc());
						AppManager.getAppManager().finishActivity();
					}
				}
			});
		}else if(type == 3){//投资结果
			ApiOrderUtils.getInvestingResult(mContext,ordId, new RequestCallback() {
				@Override
				public void execute(ParseModel parseModel) {
					loadingDialog.cancel();
					if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getStatus())){//投资成功
						application.refresh = ApiConstants.TYPE_INVEST;
//						startActivity(MainActivity.class);
						Intent intent = new Intent(mContext,MyInvestmentActivity.class);//我的资产-我的投资-投标中
						intent.putExtra("type", 2);
						startActivity(intent);
						AppManager.getAppManager().finishActivity();
					}else{
						ToastUtils.showToast(mContext, parseModel.getDesc());
						AppManager.getAppManager().finishActivity();
					}
				}
			});
		}
	}
	
	 class JSInterface {
	        
	    }
	
}
