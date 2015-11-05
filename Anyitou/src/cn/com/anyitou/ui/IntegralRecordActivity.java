package cn.com.anyitou.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.adapters.IntegralRecordsAdapter;
import cn.com.anyitou.api.ApiUserUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.IntegralRecords;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.JsonUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.LoadingDialog;
import cn.com.anyitou.views.XListView;
import cn.com.anyitou.views.XListView.IXListViewListener;
import cn.com.gson.reflect.TypeToken;

/**
 * 安币变更记录
 * 
 * @author will
 * 
 */
public class IntegralRecordActivity extends BaseActivity implements
		IXListViewListener {
	private ActionBar mActionBar;

	View view;
	private LoadingDialog loadingDialog;

	private List<IntegralRecords> recordLists = new ArrayList<IntegralRecords>();
	IntegralRecordsAdapter recordsAdapter;
	int page = 1;

	private XListView mListView;
	private View mViewEmpty;
	private TextView mViewEmptyTip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_integral_record);
		super.onCreate(savedInstanceState);

		recordsAdapter = new IntegralRecordsAdapter(recordLists, mContext);

		mListView.setAdapter(recordsAdapter);
		loadingDialog = new LoadingDialog(mContext);
		loadingDialog.show();
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
		actionBar.setTitle("安币记录");
		actionBar.setLeftActionButton(
				new OnClickListener() {

					@Override
					public void onClick(View view) {
						AppManager.getAppManager().finishActivity();
					}
				});

	}

	private void initData() {
		mViewEmpty.setVisibility(View.GONE);
		String beginTime = "";
		String endTime = "";
		ApiUserUtils.getUserIntegralRecord(mContext,String.valueOf(page),beginTime,endTime,
				new RequestCallback() {

					@Override
					public void execute(ParseModel parseModel) {
						loadingDialog.cancelDialog(loadingDialog);
						if (ApiConstants.RESULT_SUCCESS.equals(parseModel
								.getCode())) {
							List<IntegralRecords> records = JsonUtils.fromJson(
									parseModel.getData().toString(),
									new TypeToken<List<IntegralRecords>>() {
									});
							if (page == 1) {
								recordLists.clear();

								if (records == null || records.isEmpty()) {
									// mListView.setVisibility(View.GONE);
									mViewEmpty.setVisibility(View.VISIBLE);
								}
							}

							if (records != null && !records.isEmpty()) {
								recordLists.addAll(records);
								mViewEmpty.setVisibility(View.GONE);
							}
							mListView.onLoadFinish(page, records.size(),
									"加载完毕");
							recordsAdapter.notifyDataSetChanged();
						} else {
							mViewEmpty.setVisibility(View.VISIBLE);
							ToastUtils.showToast(mContext, parseModel.getMsg());
							mListView.onLoadFinish(page, 0, "");
						}

					}
				});

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
		page = 1;
		initData();
	}

	@Override
	public void onLoadMore() {
		page++;
		initData();
	}

}
