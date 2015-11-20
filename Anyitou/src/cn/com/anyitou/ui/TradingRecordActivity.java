package cn.com.anyitou.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.RotateAnimation;
import android.widget.PopupWindow;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.adapters.RecordsAdapter;
import cn.com.anyitou.api.ApiUserUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.entity.Records;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.utils.DateUtil;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.JsonUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.LoadingDialog;
import cn.com.anyitou.views.MyPopupWindow;
import cn.com.anyitou.views.XListView;
import cn.com.anyitou.views.XListView.IXListViewListener;
import cn.com.gson.reflect.TypeToken;

/**
 * 交易记录
 * 
 * @author will
 * 
 */
public class TradingRecordActivity extends BaseActivity implements
		IXListViewListener {
	private ActionBar mActionBar;
	RotateAnimation rotateAnim1, rotateAnim2;

	View view;
	WindowManager windowManager;
	private LoadingDialog loadingDialog;

	private List<Records> recordLists = new ArrayList<Records>();
	RecordsAdapter recordsAdapter;
	int page = 1;

	private XListView mListView;
	private View mViewEmpty;
	private TextView mViewEmptyTip;
	
	//condition
	String category = "all";//交易分类
	String order = "default";//排序
	String dateRange = "all";//时间范围 day:当日  oneWeek:最近7天  oneMonth:一个月  threeMonth:三个月  all:全部
	String beginDate= "";//开始日期
	String endDate = "";//结束日期
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_trading_record);
		super.onCreate(savedInstanceState);

		recordsAdapter = new RecordsAdapter(recordLists, mContext);

		mListView.setAdapter(recordsAdapter);
		initData();
	}

	@Override
	public void initView() {
		mActionBar = (ActionBar) findViewById(R.id.actionBar);
		onConfigureActionBar(mActionBar);
		mListView = (XListView) findViewById(R.id.listView);
		mViewEmpty = findViewById(R.id.empty_listview);
		mViewEmptyTip = (TextView) findViewById(R.id.xlistview_empty_tip);

		mListView.setPullLoadEnable(true);
		mListView.setXListViewListener(this);
	}

	protected void onConfigureActionBar(final ActionBar actionBar) {
		actionBar.setTitle("交易明细");
		actionBar.setLeftActionButton(
				new OnClickListener() {

					@Override
					public void onClick(View view) {
						AppManager.getAppManager().finishActivity();
					}
				});
//		actionBar.setActionBarTitleClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				if (rotateAnim1 == null) {
//					rotateAnim1 = new RotateAnimation(0f, 180f,
//							Animation.RELATIVE_TO_SELF, 0.5f,
//							Animation.RELATIVE_TO_SELF, 0.5f);
//					rotateAnim1.setInterpolator(new LinearInterpolator());
//					// rotateAnim1.setRepeatCount(1);
//					rotateAnim1.setFillAfter(true);
//					rotateAnim1.setDuration(300);
//				}
//				actionBar.startRotateAnimImageView(rotateAnim1);
//				showWindow(v);
//			}
//		});
		actionBar.setRightActionButton("筛选",new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showConditionDialog(v);
			}
		});

	}
	
	private void initData() {
		if (page == 1) {
			loadingDialog = new LoadingDialog(mContext);
			loadingDialog.show();
		} else if(page == 0){
			page++;
		}
		mViewEmpty.setVisibility(View.GONE);
		ApiUserUtils.getTrade(mContext,category,order,dateRange,beginDate,endDate, String.valueOf(page),"10",
				new RequestCallback() {

					@Override
					public void execute(ParseModel parseModel) {
						loadingDialog.cancelDialog(loadingDialog);
						if (ApiConstants.RESULT_SUCCESS_BOOLEAN.equals(parseModel
								.getStatus())) {
							List<Records> records = JsonUtils.fromJson(
									parseModel.getData().toString(),
									new TypeToken<List<Records>>() {
									});
							
							showEmptyListView(records);
							recordLists = sortRecords(recordLists);
							mListView.onLoadFinish(page, records.size(),
										"加载完毕");
							recordsAdapter.notifyDataSetChanged();
						} else {
							ToastUtils.showToast(mContext, parseModel.getMsg());
							mListView.onLoadFinish(page, 0, "");
						}

					}
				});

	}
	
	private List<Records> sortRecords(List<Records> records){
		
		if(records==null || records.isEmpty()){
			return records;
		}
		List<String> date = new ArrayList<String>();
		List<Records> recordSorts = new ArrayList<Records>();
		for (int i = 0; i < records.size(); i++) {
			Records record = records.get(i);
			String dealDate = DateUtil.getDateString(record.getDeal_time(),DateUtil.DEFAULT_PATTERN, DateUtil.CUSTOM_PATTERN5);
			if(!date.contains(dealDate)){
				date.add(dealDate);
				record.setMonthFirstDate(dealDate);
			}
			recordSorts.add(record);
		}
		
		return records;
	}
	
	private void showEmptyListView(List list){
		boolean isEmpty =false;
		if(list == null || list.isEmpty()){
			isEmpty = true;
		}
		if(page == 1){
			recordLists.clear();
			if(isEmpty){
				mViewEmpty.setVisibility(View.VISIBLE);
				mViewEmptyTip.setText("暂无记录");
			}else{
				recordLists.addAll(list);
				mViewEmpty.setVisibility(View.GONE);
			}
		}else{
			if(!isEmpty){
				recordLists.addAll(list);
			}
		}
		
	}

	@Override
	public void initListener() {
		mActionBar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AppManager.getAppManager().finishActivity();
			}
		});
	}


	@Override
	public void onRefresh() {
		page = 0;
		initData();
	}

	@Override
	public void onLoadMore() {
		page++;
		initData();
	}
	
	private PopupWindow popupWindow;
	private TextView mTvCategoryAll,mTvCategoryRecharge,mTvCategoryCash,mTvCategoryProfit,mTvCategoryCapital,mTvCategoryOther,mTvCategoryInvest;
	private TextView mTvTimeAll,mTvTimeDay,mTvTimeWeek,mTvTimeMonth,mTvTimeThreeMonth;
	
	private View mLastCategory,mLastTime;
	@SuppressWarnings("deprecation")
	public void showConditionDialog(View view){
		if(popupWindow == null){
			popupWindow = MyPopupWindow.getPopupWindow(R.layout.trade_condition_popupwindow, TradingRecordActivity.this,0);
			
			
			mTvCategoryAll = (TextView)popupWindow.getContentView().findViewById(R.id.category_all);
			mLastCategory = mTvCategoryAll;
			mTvCategoryRecharge = (TextView)popupWindow.getContentView().findViewById(R.id.category_recharge);
			mTvCategoryCash = (TextView)popupWindow.getContentView().findViewById(R.id.category_cash);
			mTvCategoryProfit = (TextView)popupWindow.getContentView().findViewById(R.id.category_profit);
			mTvCategoryCapital = (TextView)popupWindow.getContentView().findViewById(R.id.category_capital);
			mTvCategoryOther = (TextView)popupWindow.getContentView().findViewById(R.id.category_other);
			mTvCategoryInvest = (TextView)popupWindow.getContentView().findViewById(R.id.category_invest);
			
			
			mTvCategoryAll.setOnClickListener(popupWindowCategoryListener);
			mTvCategoryRecharge.setOnClickListener(popupWindowCategoryListener);
			mTvCategoryCash.setOnClickListener(popupWindowCategoryListener);
			mTvCategoryProfit.setOnClickListener(popupWindowCategoryListener);
			mTvCategoryCapital.setOnClickListener(popupWindowCategoryListener);
			mTvCategoryOther.setOnClickListener(popupWindowCategoryListener);
			mTvCategoryInvest.setOnClickListener(popupWindowCategoryListener);
			
			popupWindow.getContentView().findViewById(R.id.time_all).setOnClickListener(popupWindowTimeListener);
			popupWindow.getContentView().findViewById(R.id.time_day).setOnClickListener(popupWindowTimeListener);
			popupWindow.getContentView().findViewById(R.id.time_week).setOnClickListener(popupWindowTimeListener);
			popupWindow.getContentView().findViewById(R.id.time_month).setOnClickListener(popupWindowTimeListener);
			popupWindow.getContentView().findViewById(R.id.time_three_month).setOnClickListener(popupWindowTimeListener);
			mLastTime = popupWindow.getContentView().findViewById(R.id.time_all);
			
		}else{
			MyPopupWindow.setBackgroundAlpha(TradingRecordActivity.this, 0.4f);
		}
			popupWindow.showAsDropDown(view);
	}
	private OnClickListener popupWindowCategoryListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.category_all:
				category = "all";
				break;
			case R.id.category_recharge:
				category = "recharge";
				break;
			case R.id.category_cash:
				category = "withdraw";
				break;
			case R.id.category_profit:
				category = "income";
				break;
			case R.id.category_capital:
				category = "capital";
				break;
			case R.id.category_other:
				category = "other";
				break;
			case R.id.category_invest:
				category = "invest";
				break;

			default:
				break;
			}
			mLastCategory.setEnabled(true);
			v.setEnabled(false);
			mLastCategory = v;
			popupWindow.dismiss();
			page = 1;
			initData();
			
		}
	};
	
	private OnClickListener popupWindowTimeListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.time_all:
				dateRange = "all";
				break;
			case R.id.time_day:
				dateRange = "day";
				break;
			case R.id.time_week:
				dateRange = "oneWeek";
				break;
			case R.id.time_month:
				dateRange = "oneMonth";
				break;
			case R.id.time_three_month:
				dateRange = "threeMonth";
				break;

			default:
				break;
			}
			mLastTime.setEnabled(true);
			v.setEnabled(false);
			mLastTime = v;
			popupWindow.dismiss();
			page = 1;
			initData();
			
		}
	};

}
