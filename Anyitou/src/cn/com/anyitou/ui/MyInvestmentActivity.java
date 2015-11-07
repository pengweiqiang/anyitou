package cn.com.anyitou.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
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
		loadingDialog = new LoadingDialog(mContext);
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
		loadingDialog.show();
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
								if (page == 1) {
									myInvestList.clear();
									if(myInvestments ==null || myInvestments.isEmpty()){
										showEmptyListView(true);
									}
								}
								if (myInvestments != null && !myInvestments.isEmpty()) {
									showEmptyListView(false);
									myInvestList.addAll(myInvestments);
								}
								myInvestAdapter.notifyDataSetChanged();
								mListView.onLoadFinish(page, myInvestments.size(), "加载完毕");
							}else{
								showEmptyListView(true);
							}
						} else {
							showEmptyListView(true);
							ToastUtils.showToast(mContext, parseModel.getMsg());
							mListView.onLoadFinish(page, 0, "");
						}

					}
				});

	}
	private void showEmptyListView(boolean isEmpty){
		if(isEmpty){
			mViewEmpty.setVisibility(View.VISIBLE);
			mViewEmptyTip.setText("暂无记录");
		}else{
			mViewEmpty.setVisibility(View.GONE);
		}
	}

	@Override
	public void initListener() {
		
	}

	@Override
	public void onRefresh() {
		page = 1;
		initData();
	}

	@Override
	public void onLoadMore() {
		page++;
		initData();
	}
	
	
	private PopupWindow popupWindow;
	@SuppressWarnings("deprecation")
	public void showConditionDialog(View view){
		popupWindow = MyPopupWindow.getPopupWindow(R.layout.invest_condition_popupwindow, MyInvestmentActivity.this,0);
		popupWindow.getContentView().findViewById(R.id.category_all).setOnClickListener(popupWindowListener);
		popupWindow.getContentView().findViewById(R.id.category_anqidai).setOnClickListener(popupWindowListener);
		popupWindow.getContentView().findViewById(R.id.category_anchedai).setOnClickListener(popupWindowListener);
		popupWindow.getContentView().findViewById(R.id.category_anfangdai).setOnClickListener(popupWindowListener);
		popupWindow.getContentView().findViewById(R.id.category_debt).setOnClickListener(popupWindowListener);
		popupWindow.getContentView().findViewById(R.id.status_all).setOnClickListener(popupWindowListener);
		popupWindow.getContentView().findViewById(R.id.status_repayment).setOnClickListener(popupWindowListener);
		popupWindow.getContentView().findViewById(R.id.status_repayed).setOnClickListener(popupWindowListener);
		popupWindow.getContentView().findViewById(R.id.status_part_debt).setOnClickListener(popupWindowListener);
		popupWindow.getContentView().findViewById(R.id.status_all_debt).setOnClickListener(popupWindowListener);
		
		popupWindow.getContentView().findViewById(R.id.time_all).setOnClickListener(popupWindowListener);
		popupWindow.getContentView().findViewById(R.id.time_day).setOnClickListener(popupWindowListener);
		popupWindow.getContentView().findViewById(R.id.time_week).setOnClickListener(popupWindowListener);
		popupWindow.getContentView().findViewById(R.id.time_month).setOnClickListener(popupWindowListener);
		popupWindow.getContentView().findViewById(R.id.time_three_month).setOnClickListener(popupWindowListener);
		popupWindow.showAsDropDown(view);
	}
	private OnClickListener popupWindowListener = new OnClickListener() {
		
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
			popupWindow.dismiss();
			page = 1;
			initData();
			
		}
	};
	
}
