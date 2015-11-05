package cn.com.anyitou.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import cn.com.anyitou.R;

import cn.com.gson.reflect.TypeToken;
import cn.com.anyitou.adapters.MyInvestListAdapter;
import cn.com.anyitou.api.ApiUserUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.MyInvestment;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.JsonUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.LoadingDialog;
import cn.com.anyitou.views.XListView;
import cn.com.anyitou.views.XListView.IXListViewListener;
/**
 * 我的投资
 * @author will
 *
 */
public class MyInvestmentActivity extends BaseActivity implements IXListViewListener{
	
	private ActionBar mActionBar;
	MyInvestListAdapter myInvestAdapter;
	List<MyInvestment> myInvestList = new ArrayList<MyInvestment>();
	private XListView mListView;
	
	int page = 1;
	private String lastV = "1";
	private String v = "1";//1.回款中|2.投标中|3.已完成

	private LoadingDialog loadingDialog;
	private View mViewEmpty;
	private TextView mEmptyTip;
	private RadioGroup rgTitle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_my_investment);
		super.onCreate(savedInstanceState);
		
		myInvestAdapter = new MyInvestListAdapter(myInvestList, mContext);
		v = String.valueOf(this.getIntent().getIntExtra("type", 1));
		mListView.setAdapter(myInvestAdapter);
		loadingDialog = new LoadingDialog(mContext);
		if("2".equals(v)){
			rgTitle.check(R.id.radio_investing);
		}else{
			initData();
		}
	}
	
	@Override
	public void initView() {
		mActionBar = (ActionBar) findViewById(R.id.actionBar);
		onConfigureActionBar(mActionBar);
		
		rgTitle = (RadioGroup)findViewById(R.id.rg_title);
		mListView = (XListView) findViewById(R.id.listView);
		mViewEmpty = findViewById(R.id.empty_listview);
		mEmptyTip = (TextView)findViewById(R.id.xlistview_empty_tip);
		
		mListView.setPullLoadEnable(true);
		mListView.setXListViewListener(this);
	}
	
	protected void onConfigureActionBar(ActionBar actionBar) {
		actionBar.setTitle("我的投资");
		actionBar.setLeftActionButton( new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				AppManager.getAppManager().finishActivity();
			}
		});
		
	}
	
	private void initData() {
		if(!v.equals(lastV)){
			loadingDialog.show();
		}
		mViewEmpty.setVisibility(View.GONE);
		lastV = v;
		ApiUserUtils.getMyInvest(mContext, v,String.valueOf(page),
				new RequestCallback() {

					@Override
					public void execute(ParseModel parseModel) {
						loadingDialog.cancelDialog(loadingDialog);
						if (ApiConstants.RESULT_SUCCESS.equals(parseModel
								.getCode())) {
							
							List<MyInvestment> myInvestments = JsonUtils.fromJson(parseModel.getData().toString(), new TypeToken<List<MyInvestment>>() {});
							if (page == 1) {
								myInvestList.clear();
								if(myInvestments ==null || myInvestments.isEmpty()){
									mViewEmpty.setVisibility(View.VISIBLE);
									mEmptyTip.setText("暂无投资记录");
								}
							}
							if (myInvestments != null && !myInvestments.isEmpty()) {
								mViewEmpty.setVisibility(View.GONE);
								myInvestList.addAll(myInvestments);
							}
							myInvestAdapter.notifyDataSetChanged();
//							logined(parseModel.getToken(), null);
							mListView.onLoadFinish(page, myInvestments.size(), "加载完毕");
						} else {
							ToastUtils.showToast(mContext, parseModel.getMsg());
							mListView.onLoadFinish(page, 0, "");
						}

					}
				});

	}

	@Override
	public void initListener() {
		rgTitle.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup radiogroup, int checkId) {
				if(checkId == R.id.radio_backing){
					v = "1";
				}else if(checkId == R.id.radio_investing){
					v = "2";
				}else if(checkId == R.id.radio_finished){
					v = "3";
				}
				onRefresh();
			}
		});
	}

	@Override
	public void onRefresh() {
		page = 1;
		mListView.setSelection(0);
		initData();
	}

	@Override
	public void onLoadMore() {
		page++;
		initData();
	}
}
