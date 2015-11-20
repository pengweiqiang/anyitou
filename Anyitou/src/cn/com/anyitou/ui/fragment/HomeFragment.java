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
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import cn.com.anyitou.MyApplication;
import cn.com.anyitou.R;
import cn.com.anyitou.adapters.BannerPagerAdapter;
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
import cn.com.anyitou.utils.DeviceInfo;
import cn.com.anyitou.utils.HttpConnectionUtil;
import cn.com.anyitou.utils.JsonUtils;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.LoadingDialog;
import cn.com.anyitou.views.MyListView;
import cn.com.anyitou.views.XListView.IXListViewListener;
import cn.com.anyitou.views.banner.InfiniteViewPager;
import cn.com.anyitou.views.banner.LinePageIndicator;
import cn.com.gson.reflect.TypeToken;

/**
 * 首页
 * 
 * @author will
 * 
 */
@SuppressLint("NewApi")
public class HomeFragment extends BaseFragment implements IXListViewListener {
	private View infoView;
	private ActionBar mActionBar;

	private LoadingDialog loadingDialog;

	MyListView mListView;
	HomeListAdapter homeAdapter;
	int page = 1;

	List<Investment> investLists;

	// banner
	InfiniteViewPager mViewPager;
	LinePageIndicator mLineIndicator;
	private View mBannerView;
	int height;
	 List<Banner> bannerList=new ArrayList<Banner>();
	
	//header view
	private View mHeaderView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		infoView = inflater.inflate(R.layout.activity_main, container, false);
		mActionBar = (ActionBar) infoView.findViewById(R.id.actionBar);
		mListView = (MyListView) infoView.findViewById(R.id.listView_list);
		mActionBar.setTitle(getResources().getString(R.string.app_name));
		onConfigureActionBar(mActionBar);
		
		//header view
		getHeaderView();

		mListView.showLastView();
		mListView.setPullLoadEnable(true);
		mListView.setXListViewListener(this);
		initListener();
		return infoView;
	}
	
	private void getHeaderView(){
		mHeaderView = mActivity.getLayoutInflater().inflate(R.layout.home_header_view, null);
	
		//banner
		mViewPager = (InfiniteViewPager) mHeaderView.findViewById(R.id.viewpager);
		mLineIndicator = (LinePageIndicator) mHeaderView.findViewById(R.id.indicator);
		mBannerView = mHeaderView.findViewById(R.id.banner);
		int width = DeviceInfo.getDisplayMetricsWidth(mActivity);
		height = (int) (width * 1.0 / 720 * 270);
		LayoutParams bannerViewParams = mBannerView.getLayoutParams();
		bannerViewParams.width = width;
		bannerViewParams.height = height;
		mBannerView.setLayoutParams(bannerViewParams);
		mListView.addHeaderView(mHeaderView);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		investLists = new ArrayList<Investment>();
		homeAdapter = new HomeListAdapter(investLists, mActivity);
		mListView.setAdapter(homeAdapter);
		getInvestList();
		getAppBanner();
		
	}

	private void initBanner() {
		
		BannerPagerAdapter pagerAdapter = new BannerPagerAdapter(mActivity,height);
		pagerAdapter.setDataList(bannerList);
		mViewPager.setAdapter(pagerAdapter);
		mViewPager.setAutoScrollTime(5000);
		mViewPager.startAutoScroll();
		mLineIndicator.setViewPager(mViewPager);
	}
	

	@Override
	public void onStart() {
		super.onStart();
		if (mViewPager != null)
			mViewPager.startAutoScroll();
	}

	@Override
	public void onStop() {
		if (mViewPager != null)
			mViewPager.stopAutoScroll();
		super.onStop();
	}

	private void initListener() {
		mActionBar.hideLeftActionButtonText();
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(mActivity,
						InVestmentDetailActivity.class);
				intent.putExtra("type", 1);//投资
				intent.putExtra("invest", investLists.get(position-2));
				startActivity(intent);
			}

		});
	}

	@Override
	public void onResume() {
		super.onResume();
		if (MyApplication.getInstance().getCurrentUser() == null) {
			mActionBar.setRightActionButton("登录", new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent loginIntent = new Intent(mActivity,
							LoginActivity.class);
					mActivity.startActivity(loginIntent);
				}
			});
		} else {
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
		if (page == 1) {
			loadingDialog = new LoadingDialog(mActivity);
			loadingDialog.show();
		} else if(page == 0){
			page++;
		}
		ApiInvestUtils.getRecommend(mActivity, page, "10",
				new HttpConnectionUtil.RequestCallback() {

					@Override
					public void execute(ParseModel parseModel) {
						if (ApiConstants.RESULT_SUCCESS.equals(parseModel
								.getCode())) {
							List<Investment> invests = (List<Investment>) JsonUtils
									.fromJson(parseModel.getData().toString(),
											new TypeToken<List<Investment>>() {
											});

							if (page == 1) {
								investLists.clear();
							}

							if (invests != null && !invests.isEmpty()) {
								// initViewPagerData();
								investLists.addAll(invests);
								homeAdapter.notifyDataSetChanged();
							} else {
								ToastUtils.showToast(mActivity, "暂时没有投资列表");
							}
							mListView.onLoadFinish(page, invests.size(), "加载完毕");
							

						} else {
							mListView.onLoadFinish(page, 0, "加载完毕");
							ToastUtils.showToast(mActivity, parseModel.getMsg());
						}
						
						loadingDialog.cancelDialog(loadingDialog);
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
						if (ApiConstants.RESULT_SUCCESS.equals(parseModel
								.getCode())) {
							if (StringUtils.isEmpty(parseModel.getData()
									.toString())) {
								mBannerView.setVisibility(View.GONE);
//								ToastUtils.showToast(mActivity, "banner无数据");
								return;
							}
							List<Banner> banners = (List<Banner>) JsonUtils
									.fromJson(parseModel.getData().toString(),
											new TypeToken<List<Banner>>() {
											});
							if (banners != null && !banners.isEmpty()) {
								mBannerView.setVisibility(View.VISIBLE);
								bannerList.clear();
								bannerList.addAll(banners);
								initBanner();
							} else {
//								initBanner();
								mBannerView.setVisibility(View.GONE);
//								ToastUtils.showToast(mActivity, "暂时没有广告");
							}

						} else {
							ToastUtils.showToast(mActivity, parseModel.getMsg());
						}
					}
				});
	}

	@Override
	public void onRefresh() {
		page = 0;
		if(bannerList == null || bannerList.isEmpty()){
			getAppBanner();
		}
		getInvestList();
	}

	@Override
	public void onLoadMore() {
		page++;
		getInvestList();
	}

	// private List<Investment> getInvests(ParseModel parseModel) {
	// List<Investment> investments = new ArrayList<Investment>();
	// if (parseModel.getData().isJsonObject()) {
	// JsonObject investmentJsonObject = parseModel.getData()
	// .getAsJsonObject();
	// Set<Entry<String, JsonElement>> sets = investmentJsonObject
	// .entrySet();
	// Iterator<Entry<String, JsonElement>> keys = sets.iterator();
	// while (keys.hasNext()) {
	// Entry<String, JsonElement> entry = keys.next();
	// Investment investment = JsonUtils.fromJson(entry.getValue()
	// .toString(), Investment.class);
	// if (investment != null) {
	// investments.add(investment);
	// }
	// }
	// }
	// return investments;
	// }

}
