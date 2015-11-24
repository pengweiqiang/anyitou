package cn.com.anyitou.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.adapters.InvestRecordsDetailAdapter;
import cn.com.anyitou.api.ApiInvestUtils;
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
import cn.com.anyitou.views.XListView;
import cn.com.anyitou.views.XListView.IXListViewListener;
import cn.com.gson.reflect.TypeToken;

/**
 * 投资记录--》更多
 * 
 * @author will
 * 
 */
public class InvestRecordMoreActivity extends BaseActivity implements
		IXListViewListener {
	private ActionBar mActionBar;

	View view;
	private LoadingDialog loadingDialog;

	private List<InvestRecords> investRecordsList = new ArrayList<InvestRecords>();
	InvestRecordsDetailAdapter adapter;
	int page = 1;

	private XListView mListView;
	private View mViewEmpty;
	private TextView mViewEmptyTip;
	
	
	String id = "";//项目投资id
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_trading_record);
		super.onCreate(savedInstanceState);
		id = this.getIntent().getStringExtra("id");
		
		adapter =  new InvestRecordsDetailAdapter(investRecordsList, mContext,2);
		mListView.setAdapter(adapter);
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
		actionBar.setTitle("投资记录");
		actionBar.setLeftActionButton(
				new OnClickListener() {

					@Override
					public void onClick(View view) {
						AppManager.getAppManager().finishActivity();
					}
				});

	}
	
	private void initData() {
		if(page == 1){
			loadingDialog = new LoadingDialog(mContext);
			loadingDialog.show();
		}else{
			page ++;
		}
		mViewEmpty.setVisibility(View.GONE);
		
		//TODO获取投资记录
		ApiInvestUtils.getInvestTradeById(mContext, id, String.valueOf(page), "10", new RequestCallback() {
			
			@Override
			public void execute(ParseModel parseModel) {
				loadingDialog.cancel();
				if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
					List<InvestRecords> investRecords = (List<InvestRecords>) JsonUtils
							.fromJson(parseModel.getData().toString(),
									new TypeToken<List<InvestRecords>>() {
									});
					showEmptyListView(investRecords);
					mListView.onLoadFinish(page, investRecords.size(),
								"加载完毕");
					adapter.notifyDataSetChanged();
//						ToastUtils.showToast(mActivity, parseModel.getData().toString());
				}else {
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
			investRecordsList.clear();
			if(isEmpty){
				mViewEmpty.setVisibility(View.VISIBLE);
				mViewEmptyTip.setText("暂无记录");
			}else{
				investRecordsList.addAll(list);
				mViewEmpty.setVisibility(View.GONE);
			}
		}else{
			if(!isEmpty){
				investRecordsList.addAll(list);
			}
		}
		
	}

	@Override
	public void initListener() {
		mActionBar.setLeftActionButton(new OnClickListener() {
			
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
