package cn.com.anyitou.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import cn.com.anyitou.R;

import cn.com.gson.JsonObject;
import cn.com.gson.reflect.TypeToken;
import cn.com.anyitou.adapters.PaymentPlanAdapter;
import cn.com.anyitou.api.ApiInvestUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.entity.PaymentPlan;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.JsonUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.LoadingDialog;
import cn.com.anyitou.views.XListView;
import cn.com.anyitou.views.XListView.IXListViewListener;

/**
 * 回款计划
 * 
 * @author will
 * 
 */
@SuppressLint("NewApi") public class PaymentPlanActivity extends BaseActivity implements
		IXListViewListener {
	private ActionBar mActionBar;
	List<PaymentPlan> paymentLists = new ArrayList<PaymentPlan>();
	PaymentPlanAdapter paymentPlanAdapter;
	private XListView mListView;
	
	int page = 1;

	private LoadingDialog loadingDialog;
	private View mViewEmpty;
	private TextView mEmptyTip;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_payment_plan);
		super.onCreate(savedInstanceState);
		
		paymentPlanAdapter = new PaymentPlanAdapter(paymentLists, mContext);
		
		mListView.setAdapter(paymentPlanAdapter);
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
		mEmptyTip = (TextView)findViewById(R.id.xlistview_empty_tip);
		
		mListView.setPullLoadEnable(true);
		mListView.setXListViewListener(this);
	}

	protected void onConfigureActionBar(ActionBar actionBar) {
		actionBar.setTitle("回款计划");
		actionBar.setLeftActionButton(R.drawable.btn_back,
				new OnClickListener() {

					@Override
					public void onClick(View view) {
						AppManager.getAppManager().finishActivity();
					}
				});

	}

	private void initData() {
		mViewEmpty.setVisibility(View.GONE);
		ApiInvestUtils.receiving(mContext, String.valueOf(page),
				new RequestCallback() {

					@Override
					public void execute(ParseModel parseModel) {
						loadingDialog.cancelDialog(loadingDialog);
						if (ApiConstants.RESULT_SUCCESS.equals(parseModel
								.getCode())) {
							
//							List<PaymentPlan> paymentPlans = getPaymentPlans(parseModel);
							List<PaymentPlan> paymentPlans = JsonUtils.fromJson(parseModel.getData().toString(), new TypeToken<List<PaymentPlan>>(){});
							if(paymentPlans == null){
								paymentPlans = new ArrayList<PaymentPlan>();
							}
							if (page == 1) {
								paymentLists.clear();
								if(paymentPlans ==null || paymentPlans.isEmpty()){
//									LayoutParams listViewParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//									mListView.setLayoutParams(listViewParams);
									mViewEmpty.setVisibility(View.VISIBLE);
									mEmptyTip.setText("暂无回款计划");
//									mListView.setVisibility(View.GONE);
									mListView.setBackgroundColor(getResources().getColor(R.color.home_bg));
								}
							}
							if (paymentPlans != null && !paymentPlans.isEmpty()) {
//								LayoutParams listViewParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//								mListView.setLayoutParams(listViewParams);
								mListView.setBackground(getResources().getDrawable(R.drawable.tab_title_bg));
								mViewEmpty.setVisibility(View.GONE);
								paymentLists.addAll(paymentPlans);
							}
							paymentPlanAdapter.notifyDataSetChanged();
							logined(parseModel.getToken(), null);
							mListView.onLoadFinish(page, paymentPlans.size(), "加载完毕");
							mListView.hideFooterView();
						} else {
							ToastUtils.showToast(mContext, parseModel.getMsg());
							mListView.onLoadFinish(page, 0, "");
						}

					}
				});

	}
	private List<PaymentPlan> getPaymentPlans(ParseModel parseModel){
		List<PaymentPlan> paymentPlans = new ArrayList<PaymentPlan>();
		if(parseModel.getData().isJsonObject()){
			JsonObject paymentPlanJsonObject = parseModel.getData().getAsJsonObject();
			Set sets = paymentPlanJsonObject.entrySet();
			Iterator keys = sets.iterator();
			while (keys.hasNext()) {
				String key = (String)keys.next();
				PaymentPlan paymentPlan = JsonUtils.fromJson(paymentPlanJsonObject.getAsJsonObject(key).toString(), PaymentPlan.class);
				if(paymentPlan!=null){
					paymentPlan.setDate(key);
					paymentPlans.add(paymentPlan);
				}
			}
		}
		return paymentPlans;
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

}
