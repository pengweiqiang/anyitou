package cn.com.anyitou.ui;

import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.api.ApiInvestUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.utils.DateUtil;
import cn.com.anyitou.utils.DateUtils;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.LoadingDialog;
/**
 * 确认债权转让
 * @author pengweiqiang
 *
 */
public class ConfirmPublishDebtTransferActivity extends BaseActivity {
	ActionBar actionBar;
	private View mBtnConfirm;
	
	private TextView mTvProject,mTvDebtCount,mTvPublishProfit,mTvDebtMoney,mTvPublishDate,mTvServiceChange;
	
	String projectName,debtCount,publishProfit,debtMoney,publishDate,fee;
	
	String investId;//投资id
	LoadingDialog loading;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_confirm_publish_debt);
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		investId = intent.getStringExtra("investId");
		projectName = intent.getStringExtra("projectName");
		debtCount = intent.getStringExtra("debtCount");
		publishProfit = intent.getStringExtra("publishProfit");
		debtMoney = intent.getStringExtra("debtMoney");
		publishDate = intent.getStringExtra("publishDate");
		fee = intent.getStringExtra("fee");
		
		initData();
	}
	
	@Override
	public void initView() {
		actionBar = (ActionBar)findViewById(R.id.actionBar);
		actionBar.setTitle("确认债权转让");
		actionBar.setLeftActionButton( new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				AppManager.getAppManager().finishActivity();
			}
		});
		mBtnConfirm = findViewById(R.id.btn_confirm);
		
		mTvProject = (TextView)findViewById(R.id.project_name);
		mTvDebtCount = (TextView)findViewById(R.id.debt_count);
		mTvPublishProfit = (TextView)findViewById(R.id.publish_profit);
		mTvDebtMoney = (TextView)findViewById(R.id.debt_money);
		mTvPublishDate = (TextView)findViewById(R.id.publish_date);
		mTvServiceChange = (TextView)findViewById(R.id.service_chagne);
		
	}
	int day;
	private void initData(){
		mTvProject.setText(projectName);
		mTvDebtCount.setText(debtCount+" 份");
		mTvPublishProfit.setText(publishProfit+" %");
		mTvDebtMoney.setText(debtMoney+" 元");
		day = DateUtils.daysBetween(DateUtil.getDateString(new Date(), DateUtil.DAY_PATTERN), publishDate);
		mTvPublishDate.setText(day+" 天");
		mTvServiceChange.setText(fee+"元");
	}

	@Override
	public void initListener() {
		mBtnConfirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				debtCreate();
			}
		});
	}
	/**
	 * 发起债权转让
	 */
	private void debtCreate(){
		loading = new LoadingDialog(mContext);
		loading.show();
		String gt = "buyapr";
		ApiInvestUtils.debtCreate(mContext, investId, debtMoney, publishProfit, debtMoney, gt, day+"", new RequestCallback() {
			
			@Override
			public void execute(ParseModel parseModel) {
				loading.cancel();
				if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
					Intent success = new Intent(mContext,SuccessActivity.class);
					success.putExtra("message", "√ 恭喜您,发起债权转让成功");
					startActivity(success);
				}else{
					ToastUtils.showToast(mContext, StringUtils.isEmpty(parseModel.getMsg())?"发布失败，稍后请重试":parseModel.getMsg());
				}
			}
		});
	}

}
