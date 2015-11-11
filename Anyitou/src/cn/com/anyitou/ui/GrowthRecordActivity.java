package cn.com.anyitou.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.adapters.GrowthRecordsAdapter;
import cn.com.anyitou.api.ApiUserUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.GrowthRecords;
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
 * 用户成长值记录
 * 
 * @author will
 * 
 */
public class GrowthRecordActivity extends BaseActivity implements
		IXListViewListener {
	private ActionBar mActionBar;

	View view;
	private LoadingDialog loadingDialog;

	private List<GrowthRecords> recordLists = new ArrayList<GrowthRecords>();
	GrowthRecordsAdapter recordsAdapter;
	int page = 1;

	private XListView mListView;
	private View mViewEmpty;
	private TextView mViewEmptyTip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_growth_record);
		super.onCreate(savedInstanceState);

		recordsAdapter = new GrowthRecordsAdapter(recordLists, mContext);

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
		actionBar.setTitle("成长值明细");
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
		ApiUserUtils.getGrowthRecord(mContext,String.valueOf(page),"10",
				new RequestCallback() {

					@Override
					public void execute(ParseModel parseModel) {
						loadingDialog.cancelDialog(loadingDialog);
						if (ApiConstants.RESULT_SUCCESS.equals(parseModel
								.getCode())) {
							List<GrowthRecords> records = JsonUtils.fromJson(
									parseModel.getData().toString(),
									new TypeToken<List<GrowthRecords>>() {
									});
							showEmptyListView(records);
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
		page = 1;
		initData();
	}

	@Override
	public void onLoadMore() {
		page++;
		initData();
	}

}
