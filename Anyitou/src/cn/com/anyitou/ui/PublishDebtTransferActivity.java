package cn.com.anyitou.ui;

import java.text.ParseException;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.api.ApiInvestUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.MyDebtTransferable;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.utils.DateUtil;
import cn.com.anyitou.utils.DateUtils;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.gson.JsonObject;

/**
 * 发起债权转让
 * 
 * @author pengweiqiang
 * 
 */
public class PublishDebtTransferActivity extends BaseActivity {
	ActionBar actionBar;
	private View mBtnNext;

	private EditText mEtProjectName, mEtDebtCount, mEtPublishProfit,
			mEtDebtMoney;
	private TextView mEtPublishDate;

	private View mViewDate;
	private TextView mTvProjectName, mTvDebtCount, mTvPublishProfit,
			mTvDebtMoney, mTvPublishDate;
	private TextView mTvRealMoney;//出让方实际收益
	private TextView mTvYearRate;//出让方年化收益
	private TextView mTvServiceChange;//手续费

	int year,month,day = 0;
	
	MyDebtTransferable debt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_publish_debt);
		super.onCreate(savedInstanceState);
		
		debt = (MyDebtTransferable)this.getIntent().getSerializableExtra("debt");
		mEtProjectName.setText(debt.getItem_title());
	}

	@Override
	public void initView() {
		actionBar = (ActionBar) findViewById(R.id.actionBar);
		actionBar.setTitle("发布债权转让");
		actionBar.setLeftActionButton(new OnClickListener() {

			@Override
			public void onClick(View view) {
				AppManager.getAppManager().finishActivity();
			}
		});

		mBtnNext = findViewById(R.id.next);
		mEtProjectName = (EditText) findViewById(R.id.et_project_name);
		mEtDebtCount = (EditText) findViewById(R.id.et_debt_count);
		mEtPublishProfit = (EditText) findViewById(R.id.et_publish_profit);
		mEtDebtMoney = (EditText) findViewById(R.id.et_debt_money);
		mEtPublishDate = (TextView) findViewById(R.id.et_publish_date);

		mTvProjectName = (TextView) findViewById(R.id.title_project_name);
		mTvDebtCount = (TextView) findViewById(R.id.title_debt_count);
		mTvPublishProfit = (TextView) findViewById(R.id.title_publish_profit);
		mTvDebtMoney = (TextView) findViewById(R.id.title_debt_money);
		mTvPublishDate = (TextView) findViewById(R.id.title_publish_date);
		
		
		mTvServiceChange = (TextView)findViewById(R.id.service_charge);
		mTvYearRate = (TextView)findViewById(R.id.year_rate);
		mTvRealMoney = (TextView)findViewById(R.id.real_money);
		
		mViewDate = findViewById(R.id.select_date);

	}

	private Calendar calendar;// 用来装日期的
	private DatePickerDialog dialog;

	@Override
	public void initListener() {
//		mEtProjectName.setOnFocusChangeListener(onfocusListener);
		mEtDebtCount.setOnFocusChangeListener(onfocusListener);
		mEtPublishProfit.setOnFocusChangeListener(onfocusListener);
		mEtDebtMoney.setOnFocusChangeListener(onfocusListener);
//		mEtPublishDate.setOnFocusChangeListener(onfocusListener);
		mViewDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				mEtPublishDate.requestFocus();
				calendar = Calendar.getInstance();
				if(year == 0){
					year= calendar.get(Calendar.YEAR);
				}
				if(month == 0){
					month = calendar.get(Calendar.MONTH);
				}
				if(day == 0){
					day = calendar.get(Calendar.DAY_OF_MONTH);
				}
				dialog = new DatePickerDialog(mContext,
						new DatePickerDialog.OnDateSetListener() {
							@Override
							public void onDateSet(DatePicker view, int yearT,
									int monthOfYear, int dayOfMonth) {
								System.out.println("年-->" + year + "月-->"
										+ monthOfYear + "日-->" + dayOfMonth);
								StringBuffer sbDate = new StringBuffer(yearT+"-");
								monthOfYear ++;
								if(monthOfYear<10){
									sbDate.append("0"+monthOfYear);
								}else{
									sbDate.append(monthOfYear);
								}
								if(dayOfMonth<10){
									sbDate.append("-0"+dayOfMonth);
								}else{
									sbDate.append("-"+dayOfMonth);
								}
								year = yearT;
								month =monthOfYear-1;
								day = dayOfMonth;
								mEtPublishDate.setText(sbDate.toString());		
							}
						}, year,month,day);
				dialog.show();
			}
		});
		//出让价格
		mEtDebtMoney.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				getPublishDebtInfo();
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
		//出让份额
		mEtDebtCount.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				getPublishDebtInfo();
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
		//发布收益
		mEtPublishProfit.addTextChangedListener(new TextWatcher() {
	
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				getPublishDebtInfo();
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
//				getPublishDebtInfo();
			}
		});

		mEtPublishDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mEtPublishDate.requestFocus();
				calendar = Calendar.getInstance();
				if(year == 0){
					year= calendar.get(Calendar.YEAR);
				}
				if(month == 0){
					month = calendar.get(Calendar.MONTH);
				}
				if(day == 0){
					day = calendar.get(Calendar.DAY_OF_MONTH);
				}
				dialog = new DatePickerDialog(mContext,
						new DatePickerDialog.OnDateSetListener() {
							@Override
							public void onDateSet(DatePicker view, int yearT,
									int monthOfYear, int dayOfMonth) {
								System.out.println("年-->" + year + "月-->"
										+ monthOfYear + "日-->" + dayOfMonth);
								StringBuffer sbDate = new StringBuffer(yearT+"-");
								monthOfYear ++;
								if(monthOfYear<10){
									sbDate.append("0"+monthOfYear);
								}else{
									sbDate.append(monthOfYear);
								}
								if(dayOfMonth<10){
									sbDate.append("-0"+dayOfMonth);
								}else{
									sbDate.append("-"+dayOfMonth);
								}
								year = yearT;
								month =monthOfYear-1;
								day = dayOfMonth;
								mEtPublishDate.setText(sbDate.toString());		
							}
						}, year,month,day);
				dialog.show();
			}
		});
		mBtnNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				next();
			}
		});
	}

	private void next() {
		String projectName = mEtProjectName.getText().toString().trim();
		if (StringUtils.isEmpty(projectName)) {
//			ToastUtils.showToast(mContext, "请输入项目名称");
//			mEtProjectName.requestFocus();
			return;
		}
		String debtCount = mEtDebtCount.getText().toString().trim();
		if (StringUtils.isEmpty(debtCount)) {
			ToastUtils.showToast(mContext, "请输入出让债权份额");
			mEtDebtCount.requestFocus();
			return;
		}

		String publishProfit = mEtPublishProfit.getText().toString().trim();
		if (StringUtils.isEmpty(publishProfit)) {
			ToastUtils.showToast(mContext, "请输入发布收益");
			mEtPublishProfit.requestFocus();
			return;
		}

		String debtMoney = mEtDebtMoney.getText().toString().trim();
		if (StringUtils.isEmpty(debtMoney)) {
			ToastUtils.showToast(mContext, "请输入出让价格");
			mEtDebtMoney.requestFocus();
			return;
		}

		String publishDate = mEtPublishDate.getText().toString().trim();
		if (StringUtils.isEmpty(publishDate)) {
			ToastUtils.showToast(mContext, "请输入发布期限");
			mEtPublishDate.requestFocus();
			return;
		}
		try {
			boolean isBefore = DateUtils.beforeNow(DateUtil.getDate(publishDate, DateUtil.DAY_PATTERN));
			if(isBefore){
				ToastUtils.showToast(mContext, "发布期限填写错误");
				mEtPublishDate.requestFocus();
				return;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(StringUtils.isEmpty(fee)){
			ToastUtils.showToast(mContext, "手续费获取为空");
			return;
		}
		Intent intent = new Intent(mContext,
				ConfirmPublishDebtTransferActivity.class);
		if(debt!=null){
			intent.putExtra("investId", debt.getId());
		}
		intent.putExtra("fee", fee);
		intent.putExtra("projectName", projectName);
		intent.putExtra("debtCount", debtCount);
		intent.putExtra("publishProfit", publishProfit);
		intent.putExtra("debtMoney", debtMoney);
		intent.putExtra("publishDate", publishDate);
		startActivity(intent);
	}

	private OnFocusChangeListener onfocusListener = new OnFocusChangeListener() {

		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			switch (v.getId()) {
			case R.id.et_project_name:
				if (hasFocus) {
					mTvProjectName.setTextColor(getResources().getColor(
							R.color.tab_title_color));
				} else {
					mTvProjectName.setTextColor(getResources().getColor(
							R.color.et_un_focus));
				}
				break;
			case R.id.et_debt_count://出让份额
				if (hasFocus) {
					mTvDebtCount.setTextColor(getResources().getColor(
							R.color.tab_title_color));
				} else {
					mTvDebtCount.setTextColor(getResources().getColor(
							R.color.et_un_focus));
				}
				break;
			case R.id.et_publish_profit:
				if (hasFocus) {
					mTvPublishProfit.setTextColor(getResources().getColor(
							R.color.tab_title_color));
				} else {
					mTvPublishProfit.setTextColor(getResources().getColor(
							R.color.et_un_focus));
				}
				break;
			case R.id.et_debt_money:
				if (hasFocus) {
					mTvDebtMoney.setTextColor(getResources().getColor(
							R.color.tab_title_color));
				} else {
					mTvDebtMoney.setTextColor(getResources().getColor(
							R.color.et_un_focus));
				}
				break;
			case R.id.et_publish_date:
				if (hasFocus) {
					mTvPublishDate.setTextColor(getResources().getColor(
							R.color.tab_title_color));
				} else {
					mTvPublishDate.setTextColor(getResources().getColor(
							R.color.et_un_focus));
				}
				break;

			default:
				break;
			}

		}
	};
	
	/**
	 * 获取转让参数
	 */
	String fee = "";
	private void getPublishDebtInfo(){
		//出让份额
		String debtCount = mEtDebtCount.getText().toString().trim();
		if(StringUtils.isEmpty(debtCount)){
			return;
		}
		String gt = "";//price
		//发布收益
		String publishProfit = mEtPublishProfit.getText().toString().trim();
		//转让价格
		String debtMoney = mEtDebtMoney.getText().toString().trim();
		if(StringUtils.isEmpty(debtMoney) && StringUtils.isEmpty(publishProfit)){
			return;
		}else if(!StringUtils.isEmpty(publishProfit)){
			gt = "buyapr";
		}else if(!StringUtils.isEmpty(debtMoney)){
			gt = "price";
		}
		
		ApiInvestUtils.getDebtParams(mContext,debt.getId(), debtCount, publishProfit, debtMoney, gt, new RequestCallback() {
			
			@Override
			public void execute(ParseModel parseModel) {
				if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
					JsonObject data = parseModel.getData().getAsJsonObject();
					mTvYearRate.setText(data.get("soldRealApr").getAsString()+"元");
					mTvRealMoney.setText(data.get("soldRealIncome").getAsString()+"元");
					fee = data.get("fee").getAsString();
					mTvServiceChange.setText(fee+"元");
				}else{
					ToastUtils.showToast(mContext, parseModel.getMsg());
				}
			}
		});
	}

}
