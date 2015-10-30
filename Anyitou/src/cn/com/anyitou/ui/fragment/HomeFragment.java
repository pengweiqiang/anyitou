package cn.com.anyitou.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import cn.com.anyitou.MyApplication;
import cn.com.anyitou.R;
import cn.com.anyitou.adapters.HomeListAdapter;
import cn.com.anyitou.api.ApiHomeUtils;
import cn.com.anyitou.api.ApiInvestUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.entity.Banner;
import cn.com.anyitou.entity.Investment;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.ui.InVestmentDetailActivity;
import cn.com.anyitou.ui.LoginActivity;
import cn.com.anyitou.ui.base.BaseFragment;
import cn.com.anyitou.utils.HttpConnectionUtil;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.JsonUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.LoadingDialog;
import cn.com.anyitou.views.MyListView;
import cn.com.gson.reflect.TypeToken;

/**
 * 首页
 * 
 * @author will
 * 
 */
@SuppressLint("NewApi")
public class HomeFragment extends BaseFragment {
	private View infoView;
	private ActionBar mActionBar;

	
	private LoadingDialog loadingDialog;
	
	int page = 1;
	
	MyListView myListView;
	HomeListAdapter homeAdapter;

	List<Investment> investLists;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		infoView = inflater.inflate(R.layout.activity_main, container, false);
		mActionBar = (ActionBar) infoView.findViewById(R.id.actionBar);
		myListView = (MyListView) infoView.findViewById(R.id.listView_list);
		mActionBar.setTitle(getResources().getString(R.string.app_name));
		onConfigureActionBar(mActionBar);
		
		initListener();
		return infoView;
	}
	   
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		investLists = new ArrayList<Investment>();
		homeAdapter = new HomeListAdapter(investLists, mActivity);
		myListView.setAdapter(homeAdapter);
		getInvestList();
		getAppBanner();
		
	}
	
	

	private void initListener(){
		myListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(mActivity,InVestmentDetailActivity.class);
				intent.putExtra("id", investLists.get(position).getId());
				startActivity(intent);
			}
			
		});
	}
	
	
	

	@Override
	public void onResume() {
		super.onResume();
		if(MyApplication.getInstance().getCurrentUser() == null){
			mActionBar.setRightActionButton("登录", new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent loginIntent = new Intent(mActivity, LoginActivity.class);
					mActivity.startActivity(loginIntent);
				}
			});
		}else{
			mActionBar.hideRightActionButtonText();
		}
	}

	// 设置activity的导航条
	protected void onConfigureActionBar(ActionBar actionBar) {
		
		
	}
	
	
	/**
	 * 获取投资列表数据
	 */
	private void getInvestList() {
		loadingDialog = new LoadingDialog(mActivity);
		loadingDialog.show();
		ApiInvestUtils.getRecommend(mActivity,String.valueOf(page),"10",
				new HttpConnectionUtil.RequestCallback() {

					@Override
					public void execute(ParseModel parseModel) {
						loadingDialog.cancel();
						if (ApiConstants.RESULT_SUCCESS.equals(parseModel
								.getCode())) {
							 List<Investment> invests = (List<Investment>)JsonUtils.fromJson(parseModel.getData().toString(),new TypeToken<List<Investment>>() {});
//							List<Investment> invests = getInvests(parseModel);

							if (invests != null && !invests.isEmpty()) {
								// initViewPagerData();
								 investLists.clear();
								 investLists.addAll(invests);
								 homeAdapter.notifyDataSetChanged();
							} else {
								ToastUtils.showToast(mActivity,
										"暂时没有投资列表");
							}

						} else {
							ToastUtils.showToast(mActivity,
									parseModel.getMsg());
						}
					}
				});
	}
	/**
	 * 获取APP Banner
	 */
	private void getAppBanner() {
		ApiHomeUtils.getBanner(mActivity,
				new HttpConnectionUtil.RequestCallback() {

					@Override
					public void execute(ParseModel parseModel) {
						loadingDialog.cancel();
						if (ApiConstants.RESULT_SUCCESS.equals(parseModel
								.getCode())) {
							 List<Banner> banners = (List<Banner>)JsonUtils.fromJson(parseModel.getData().toString(),new TypeToken<List<Banner>>() {});
							 
							if (banners != null && !banners.isEmpty()) {
								
							} else {
								ToastUtils.showToast(mActivity,
										"暂时没有广告");
							}

						} else {
							ToastUtils.showToast(mActivity,
									parseModel.getMsg());
						}
					}
				});
	}
	
	
//	private List<Investment> getInvests(ParseModel parseModel) {
//		List<Investment> investments = new ArrayList<Investment>();
//		if (parseModel.getData().isJsonObject()) {
//			JsonObject investmentJsonObject = parseModel.getData()
//					.getAsJsonObject();
//			Set<Entry<String, JsonElement>> sets = investmentJsonObject
//					.entrySet();
//			Iterator<Entry<String, JsonElement>> keys = sets.iterator();
//			while (keys.hasNext()) {
//				Entry<String, JsonElement> entry = keys.next();
//				Investment investment = JsonUtils.fromJson(entry.getValue()
//						.toString(), Investment.class);
//				if (investment != null) {
//					investments.add(investment);
//				}
//			}
//		}
//		return investments;
//	}
	

}
