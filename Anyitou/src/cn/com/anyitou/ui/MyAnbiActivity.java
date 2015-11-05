package cn.com.anyitou.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.api.ApiUserUtils;
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

/**
 * 我的安币
 * 
 * @author will
 * 
 */
public class MyAnbiActivity extends BaseActivity {

	ActionBar mActionBar;
	private LoadingDialog loading;
	private View mBtnAnbiTrade,mBtnAnbiExchange;
	private TextView mTvAnbiUsable,mTvAnbiUsed,mTvTotal;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_my_anbi);
		super.onCreate(savedInstanceState);
		
		
		initData();
	}

	// 设置activity的导航条
	protected void onConfigureActionBar(ActionBar actionBar) {
		actionBar.setTitle("我的安币");
	}

	@Override
	public void initView() {
		mActionBar = (ActionBar)findViewById(R.id.actionBar);
		onConfigureActionBar(mActionBar);
		mBtnAnbiTrade = findViewById(R.id.anbi_trade);
		mBtnAnbiExchange = findViewById(R.id.anbi_exchange);
		mTvAnbiUsable = (TextView)findViewById(R.id.anbi_usable);
		mTvAnbiUsed = (TextView)findViewById(R.id.anbi_used);
		mTvTotal = (TextView)findViewById(R.id.anbi_total);
	}
	
	private void initData(){
		loading = new LoadingDialog(mContext);
		loading.show();
		ApiUserUtils.getMyAnbiInfo(mContext, new RequestCallback() {
			
			@Override
			public void execute(ParseModel parseModel) {
				loading.cancel();
				if(ApiConstants.RESULT_SUCCESS_BOOLEAN.equals(parseModel.getStatus())){
					JsonObject data = parseModel.getData().getAsJsonObject();
					if(data !=null){
//						"usable_integral": 4400,                                 //可用安币
//				        "used_integral": 5200,                                   //已用安币
//				        "total_integral": 9600                                   //累计安币
						String usableIntegral = data.get("usable_integral").getAsString();
						String used_integral = data.get("used_integral").getAsString();
						String total_integral = data.get("total_integral").getAsString();
						mTvTotal.setText("累计安币："+total_integral);
						mTvAnbiUsable.setText(usableIntegral);
						mTvAnbiUsed.setText("已用安币："+used_integral);
					}
				}else{
					ToastUtils.showToast(mContext, StringUtils.isEmpty(parseModel.getMsg())?"获取安币信息失败，稍后重试":parseModel.getMsg());
				}
				
			}
		});
	}

	@Override
	public void initListener() {
		mBtnAnbiTrade.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext,IntegralRecordActivity.class);
				startActivity(intent);
			}
		});
		mBtnAnbiExchange.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext,IntegralRecordActivity.class);
				startActivity(intent);
			}
		});
		mActionBar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AppManager.getAppManager().finishActivity();
			}
		});
		
	}

}
