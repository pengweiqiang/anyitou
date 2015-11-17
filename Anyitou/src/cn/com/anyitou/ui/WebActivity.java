package cn.com.anyitou.ui;

import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;
import cn.com.anyitou.R;
import cn.com.anyitou.api.ApiOrderUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.LoadingDialog;
import cn.com.gson.JsonObject;

public class WebActivity extends BaseActivity {

	private WebView webView;
	private ActionBar mActionBar;
	private String name;
	private String url;
	private ProgressBar progressBar;
	Object mJsObj = new JSInterface();
	LoadingDialog loadingDialog;
	int type; // 1代表注册汇付操作  2 充值操作 3投资操作  4 提现   5 债权
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

			@Override
			public void onReceivedSslError(WebView view,
					SslErrorHandler handler, SslError error) {
				//super.onReceivedSslError(view, handler, error);
				handler.proceed();
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
				getWebTitle(str);
			}

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				if (newProgress == 100) {
					/*if("安宜投".equals(title)){
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
		mActionBar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AppManager.getAppManager().finishActivity();
			}
		});
	}
	private void getWebTitle(final String title){
		if("充值成功".equals(title) || "提现成功".equals(title) ||"投资成功".equals(title)||"认购成功".equals(title)){
			new Handler().postDelayed(new Runnable(){   
			    public void run() {   
			    	getTradeStatus(title);
			    }   
			 }, 1000); 
			
		}
	}
	private void getTradeStatus(final String title){
		
		String operationTpe = "";
		switch (type) {
		case 1://注册汇付结果
			
			break;
		case 2://充值结果	
			operationTpe = "recharge";
			break;
		case 3://投资结果
			operationTpe = "invest";
			break;
		case 4://提现结果
			operationTpe = "withdraw";
			break;
		case 5://购买债权结果
			operationTpe = "debt";
			break;
		default:
			break;
		}
		ApiOrderUtils.getTradeStatus(mContext, String.valueOf(ordId), operationTpe, new RequestCallback() {
			
			@Override
			public void execute(ParseModel parseModel) {
				if(ApiConstants.RESULT_SUCCESS_BOOLEAN.equals(parseModel.getStatus())){
					JsonObject data = parseModel.getData().getAsJsonObject();
					if(data!=null){
						String status = data.get("status").getAsString();//I:初始  S:成功  F:失败
						if("S".equals(status)){//交易成功
							ToastUtils.showToast(mContext, title+",3秒后跳入主页",Toast.LENGTH_LONG);
							new Handler().postDelayed(new Runnable(){   
							    public void run() {   
							    	startActivity(HomeActivity.class);
									AppManager.getAppManager().finishActivity();
							    }   
							 }, 3000); 
							
						}else if("F".equals(status)){
							ToastUtils.showToast(mContext, "操作失败");
						}else if("I".equalsIgnoreCase(status)){
							ToastUtils.showToast(mContext, "状态初始化I");
//							startActivity(HomeActivity.class);
//							AppManager.getAppManager().finishActivity();
						}
					}
				}
			}
		});
	}
	
	 class JSInterface {
	        
	    }
	
}
