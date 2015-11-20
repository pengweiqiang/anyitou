package cn.com.anyitou.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.adapters.MemberChangeRecordsAdapter;
import cn.com.anyitou.api.ApiUserUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.MemberChangeRecords;
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
 * 会员级别变更记录
 * 
 * @author will
 * 
 */
public class MemberChangeRecordActivity extends BaseActivity implements
		IXListViewListener {
	private ActionBar mActionBar;

	View view;
	private LoadingDialog loadingDialog;

	private List<MemberChangeRecords> recordLists = new ArrayList<MemberChangeRecords>();
	MemberChangeRecordsAdapter recordsAdapter;
	int page = 1;

	private XListView mListView;
	private View mViewEmpty;
	private TextView mViewEmptyTip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_member_change_record);
		super.onCreate(savedInstanceState);

		recordsAdapter = new MemberChangeRecordsAdapter(recordLists, mContext);

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
		actionBar.setTitle("会员级别变动记录");
		actionBar.setLeftActionButton(
				new OnClickListener() {

					@Override
					public void onClick(View view) {
						AppManager.getAppManager().finishActivity();
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
		ApiUserUtils.getMemberChangeRecord(mContext,String.valueOf(page),
				new RequestCallback() {

					@Override
					public void execute(ParseModel parseModel) {
						loadingDialog.cancelDialog(loadingDialog);
						if (ApiConstants.RESULT_SUCCESS.equals(parseModel
								.getCode())) {
							List<MemberChangeRecords> records = JsonUtils.fromJson(
									parseModel.getData().toString(),
									new TypeToken<List<MemberChangeRecords>>() {
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
		page = 0;
		initData();
	}

	@Override
	public void onLoadMore() {
		page++;
		initData();
	}

}
