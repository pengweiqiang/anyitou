package cn.com.anyitou.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.adapters.IntegralGoodsAdapter;
import cn.com.anyitou.adapters.IntegralRecordsAdapter;
import cn.com.anyitou.api.ApiInvestUtils;
import cn.com.anyitou.api.ApiUserUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.IntegralGoods;
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
import cn.com.gson.JsonElement;
import cn.com.gson.JsonObject;
import cn.com.gson.reflect.TypeToken;

/**
 * 安币兑换
 * 
 * @author will
 * 
 */
public class IntegralGoodActivity extends BaseActivity implements
		IXListViewListener {
	private ActionBar mActionBar;

	View view;
	private LoadingDialog loadingDialog;

	private List<IntegralGoods> goodsLists = new ArrayList<IntegralGoods>();
	IntegralGoodsAdapter goodsAdapter;
	int page = 1;

	private XListView mListView;
	private View mViewEmpty;
	private TextView mViewEmptyTip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_integral_good);
		super.onCreate(savedInstanceState);

		goodsAdapter = new IntegralGoodsAdapter(goodsLists, mContext);

		mListView.setAdapter(goodsAdapter);
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
		actionBar.setTitle("安币兑换");
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
		ApiInvestUtils.getIntegalGoods(mContext,String.valueOf(page),"10",
				new RequestCallback() {

					@Override
					public void execute(ParseModel parseModel) {
						loadingDialog.cancelDialog(loadingDialog);
						if (ApiConstants.RESULT_SUCCESS.equals(parseModel
								.getCode())) {
							JsonObject data = parseModel.getData().getAsJsonObject();
							if(data !=null){
								List<IntegralGoods> goods = JsonUtils.fromJson(
										data.get("list").toString(),
										new TypeToken<List<IntegralGoods>>() {
										});
								if (page == 1) {
									goodsLists.clear();
	
									if (goods == null || goods.isEmpty()) {
										// mListView.setVisibility(View.GONE);
										mViewEmpty.setVisibility(View.VISIBLE);
									}
								}
	
								if (goods != null && !goods.isEmpty()) {
									goodsLists.addAll(goods);
									showEmptyListView(false);
								}else {
									showEmptyListView(true);
								}
								mListView.onLoadFinish(page, goods.size(),
										"加载完毕");
								goodsAdapter.notifyDataSetChanged();
							}else{
								showEmptyListView(true);
							}
						} else {
							showEmptyListView(true);
						}

					}
				});

	}
	
	private void showEmptyListView(boolean isEmpty){
		if(page == 1){
			if(isEmpty){
				mListView.onLoadFinish(page, 0, "");
				mViewEmpty.setVisibility(View.VISIBLE);
				mViewEmptyTip.setText("暂无数据");
			}else{
				mViewEmpty.setVisibility(View.GONE);
			}
		}else{
			mListView.onLoadFinish(page, 0, "");
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
