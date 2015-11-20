package cn.com.anyitou.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupWindow;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.adapters.InvestRecordsAdapter;
import cn.com.anyitou.api.ApiUserUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.InvestRecords;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.JsonUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.LoadingDialog;
import cn.com.anyitou.views.MyPopupWindow;
import cn.com.anyitou.views.XListView;
import cn.com.anyitou.views.XListView.IXListViewListener;
import cn.com.gson.JsonElement;
import cn.com.gson.JsonObject;
import cn.com.gson.reflect.TypeToken;
/**
 * 我的投资
 * @author will
 *
 */
public class MyInvestmentActivity extends BaseActivity implements IXListViewListener{
	
	private ActionBar mActionBar;
	InvestRecordsAdapter myInvestAdapter;
	List<InvestRecords> myInvestList = new ArrayList<InvestRecords>();
	private XListView mListView;
	
	int page = 1;

	private LoadingDialog loadingDialog;
	private View mViewEmpty;
	private TextView mViewEmptyTip;
	
	private String category = "all";
	private String status ="";
	private String order ="";
	private String investDateRange = "";
	private String investBeginDate = "";
	private String investEndDate = "";
	private String repayDateRange = "";
	private String repayBeginDate = "";
	private String repayEndDate = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_my_investment);
		super.onCreate(savedInstanceState);
		
		myInvestAdapter = new InvestRecordsAdapter(myInvestList, mContext);
		mListView.setAdapter(myInvestAdapter);
		initData();
	}
	
	@Override
	public void initView() {
		mActionBar = (ActionBar) findViewById(R.id.actionBar);
		onConfigureActionBar(mActionBar);
		
		mListView = (XListView) findViewById(R.id.listView);
		mViewEmpty = findViewById(R.id.empty_listview);
		mViewEmptyTip = (TextView)findViewById(R.id.xlistview_empty_tip);
		
		mListView.setPullLoadEnable(true);
		mListView.setXListViewListener(this);
	}
	
	protected void onConfigureActionBar(ActionBar actionBar) {
		actionBar.setTitle("投资明细");
		actionBar.setLeftActionButton( new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				AppManager.getAppManager().finishActivity();
			}
		});
		actionBar.setRightActionButton("筛选", new OnClickListener() {
			
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
		ApiUserUtils.getMyInvestList(mContext, category, status, order, investDateRange, investBeginDate, 
				investEndDate, repayDateRange, repayBeginDate, repayEndDate,String.valueOf(page),"10",
				new RequestCallback() {

					@Override
					public void execute(ParseModel parseModel) {
						loadingDialog.cancelDialog(loadingDialog);
						if (ApiConstants.RESULT_SUCCESS.equals(parseModel
								.getCode())) {
							JsonObject data = parseModel.getData().getAsJsonObject();
							if(data !=null){
								JsonElement list = data.get("record_invest");
								List<InvestRecords> myInvestments = JsonUtils.fromJson(list.toString(), new TypeToken<List<InvestRecords>>() {});
								showEmptyListView(myInvestments);
								myInvestAdapter.notifyDataSetChanged();
								mListView.onLoadFinish(page, myInvestments.size(), "加载完毕");
							}else{
								showEmptyListView(null);
							}
						} else {
							ToastUtils.showToast(mContext, parseModel.getMsg());
							mListView.onLoadFinish(page, 0, "");
						}

					}
				});

	}
	private void showEmptyListView(List list){
		boolean isEmpty =false;
		if(list == null || list.isEmpty()){
			isEmpty = true;
		}
		if(page == 1){
			myInvestList.clear();
			if(isEmpty){
				mViewEmpty.setVisibility(View.VISIBLE);
				mViewEmptyTip.setText("暂无记录");
			}else{
				myInvestList.addAll(list);
				mViewEmpty.setVisibility(View.GONE);
			}
		}else{
			if(!isEmpty){
				myInvestList.addAll(list);
			}
		}
		
	}

	@Override
	public void initListener() {
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(mContext,InvestmentRecordDetailActivity.class);
				intent.putExtra("investment", myInvestList.get(position-1));
				startActivity(intent);
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
	private View mLastCategory,mLastStatus,mLastTime;
	@SuppressWarnings("deprecation")
	public void showConditionDialog(View view){
		if(popupWindow == null){
			popupWindow = MyPopupWindow.getPopupWindow(R.layout.invest_condition_popupwindow, MyInvestmentActivity.this,0);
			popupWindow.getContentView().findViewById(R.id.category_all).setOnClickListener(popupWindowCategoryListener);
			popupWindow.getContentView().findViewById(R.id.category_anqidai).setOnClickListener(popupWindowCategoryListener);
			popupWindow.getContentView().findViewById(R.id.category_anchedai).setOnClickListener(popupWindowCategoryListener);
			popupWindow.getContentView().findViewById(R.id.category_anfangdai).setOnClickListener(popupWindowCategoryListener);
			popupWindow.getContentView().findViewById(R.id.category_debt).setOnClickListener(popupWindowCategoryListener);
			
			mLastCategory = popupWindow.getContentView().findViewById(R.id.category_all);
			
			popupWindow.getContentView().findViewById(R.id.status_all).setOnClickListener(popupWindowStatusListener);
			popupWindow.getContentView().findViewById(R.id.status_repayment).setOnClickListener(popupWindowStatusListener);
			popupWindow.getContentView().findViewById(R.id.status_repayed).setOnClickListener(popupWindowStatusListener);
			popupWindow.getContentView().findViewById(R.id.status_part_debt).setOnClickListener(popupWindowStatusListener);
			popupWindow.getContentView().findViewById(R.id.status_all_debt).setOnClickListener(popupWindowStatusListener);
			
			mLastStatus = popupWindow.getContentView().findViewById(R.id.status_all);
			
			popupWindow.getContentView().findViewById(R.id.time_all).setOnClickListener(popupWindowTimeListener);
			popupWindow.getContentView().findViewById(R.id.time_day).setOnClickListener(popupWindowTimeListener);
			popupWindow.getContentView().findViewById(R.id.time_week).setOnClickListener(popupWindowTimeListener);
			popupWindow.getContentView().findViewById(R.id.time_month).setOnClickListener(popupWindowTimeListener);
			popupWindow.getContentView().findViewById(R.id.time_three_month).setOnClickListener(popupWindowTimeListener);
			
			mLastTime = popupWindow.getContentView().findViewById(R.id.time_all);
			
		}else{
			MyPopupWindow.setBackgroundAlpha(MyInvestmentActivity.this, 0.4f);
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
			case R.id.category_anqidai:
				category = "qidai";
				break;
			case R.id.category_anchedai:
				category = "chedai";
				break;
			case R.id.category_anfangdai:
				category = "fangdai";
				break;
			case R.id.category_debt:
				category = "debt";
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
	
	private OnClickListener popupWindowStatusListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.status_all:
				status = "";
				break;
			case R.id.status_repayment:
				status = "1";
				break;
			case R.id.status_repayed:
				status = "2";
				break;
			case R.id.status_part_debt:
				status = "3";
				break;
			case R.id.status_all_debt:
				status = "4";
				break;

			default:
				break;
			}
			mLastStatus.setEnabled(true);
			v.setEnabled(false);
			mLastStatus = v;
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
				investDateRange = "all";
				break;
			case R.id.time_day:
				investDateRange = "day";
				break;
			case R.id.time_week:
				investDateRange = "oneWeek";
				break;
			case R.id.time_month:
				investDateRange = "oneMonth";
				break;
			case R.id.time_three_month:
				investDateRange = "threeMonth";
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
