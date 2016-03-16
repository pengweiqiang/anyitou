package cn.com.anyitou.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import cn.com.anyitou.MyApplication;
import cn.com.anyitou.R;
import cn.com.anyitou.adapters.BannerPagerAdapter;
import cn.com.anyitou.adapters.FragmentAdapter;
import cn.com.anyitou.api.ApiHomeUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.entity.Banner;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.ui.LoginActivity;
import cn.com.anyitou.ui.base.BaseFragment;
import cn.com.anyitou.utils.DeviceInfo;
import cn.com.anyitou.utils.HttpConnectionUtil;
import cn.com.anyitou.utils.JsonUtils;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.LoadingDialog;
import cn.com.anyitou.views.PagerSlidingTabStrip;
import cn.com.anyitou.views.banner.CirclePageIndicator2;
import cn.com.anyitou.views.banner.InfiniteViewPager;
import cn.com.gson.reflect.TypeToken;

/**
 * 首页
 * 
 * @author pengweiqiang
 * 
 */
@SuppressLint("NewApi")
public class HomeFragment extends BaseFragment {
	private View infoView;
	private ActionBar mActionBar;

	private LoadingDialog loadingDialog;

	
	// banner height
	int height;
	//banner data
	List<Banner> bannerList=new ArrayList<Banner>();
	
	//header banner view
	InfiniteViewPager mViewPager;
	CirclePageIndicator2 mLineIndicator;
	private View mBannerView;
	//category view 
	private ViewPager mTabViewPager;
	private PagerSlidingTabStrip mCategoryTabs;
	private List<Fragment> list = new ArrayList<Fragment>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		infoView = inflater.inflate(R.layout.activity_main2, container, false);
		mActionBar = (ActionBar) infoView.findViewById(R.id.actionBar);
		mTabViewPager = (ViewPager) infoView.findViewById(R.id.viewPager_index);
		mCategoryTabs = (PagerSlidingTabStrip) infoView.findViewById(R.id.tabs_index);
		mActionBar.setTitle(getResources().getString(R.string.app_name));
		onConfigureActionBar(mActionBar);
		
		//header banner view
		getHeaderView();
		//category tabs view
		initCategoryTabsView();
		
		initListener();
		return infoView;
	}
	
	private void getHeaderView(){
	
		//banner
		mViewPager = (InfiniteViewPager) infoView.findViewById(R.id.viewpager);
		mLineIndicator = (CirclePageIndicator2) infoView.findViewById(R.id.indicator);
		mBannerView = infoView.findViewById(R.id.banner);
		int width = DeviceInfo.getDisplayMetricsWidth(mActivity);
		height = (int) (width * 1.0 / 720 * 270);
		LayoutParams bannerViewParams = mBannerView.getLayoutParams();
		bannerViewParams.width = width;
		bannerViewParams.height = height;
		mBannerView.setLayoutParams(bannerViewParams);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getAppBanner();
		
	}
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// 判断Fragment中的ListView时候存在，判断该Fragment时候已经正在前台显示
		// 通过这两个判断，就可以知道什么时候去加载数据了
		if (isVisibleToUser && isVisible() && (bannerList==null || bannerList.isEmpty())) {
			getAppBanner();
		}
		super.setUserVisibleHint(isVisibleToUser);
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

	private boolean isReward = false;
	@Override
	public void onStop() {
		super.onStop();
		if (mViewPager != null)
			mViewPager.stopAutoScroll();
		if(isReward){
			mTabViewPager.setCurrentItem(1);
			isReward = false;
		}
		
	}
	

	private void initListener() {
		mActionBar.hideLeftActionButtonText();
//		mListView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				Intent intent = new Intent(mActivity,
//						InVestmentDetailActivity.class);
//				intent.putExtra("type", 1);//投资
//				intent.putExtra("invest", investLists.get(position-2));
//				startActivity(intent);
//			}
//
//		});
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
	 * 初始化分类tabs
	 */
	private void initCategoryTabsView() {
		// 设置数据源
//		RewardItemFragment mRewardFragment = new RewardItemFragment();
		// 安企贷
		HomeItemFragment mInvestFragment = new HomeItemFragment();
		Bundle bundle = new Bundle();
		bundle.putString("category", "invest");
		mInvestFragment.setArguments(bundle);
		// 安房贷
		HomeItemFragment mFangDaiFragment = new HomeItemFragment();
		Bundle bundleFangdai = new Bundle();
		bundleFangdai.putString("category", "fangdai");
		mFangDaiFragment.setArguments(bundleFangdai);
		
		//安车贷
		HomeItemFragment mCheDaiFragment = new HomeItemFragment();
		Bundle bundleCheDai = new Bundle();
		bundleCheDai.putString("category", "chedai");
		mCheDaiFragment.setArguments(bundleCheDai);
		
//		list.add(mRewardFragment);
		list.add(mInvestFragment);
		list.add(mFangDaiFragment);
		list.add(mCheDaiFragment);
		
		String [] titles = new String[]{/*"新手体验",*/"安企贷","安房贷","安车贷"};
		// 设置适配器
		FragmentAdapter adapter = new FragmentAdapter(this.getChildFragmentManager(),titles,list);

		// 绑定适配器
		mTabViewPager.setAdapter(adapter);
		mTabViewPager.setOffscreenPageLimit(titles.length);
		mCategoryTabs.setViewPager(mTabViewPager);
//		mCategoryTabs.setOnPageListener(new OnPageChange() {
//			
//			@Override
//			public void changePage(int position) {
//				if(position == 0){
//					isReward = true;
//					Intent intent = new Intent(mActivity,WebActivity.class);
//					intent.putExtra("url", "http://www.anyitou.com/novice/detail?id=347");
//					intent.putExtra("name", "新手体验");
//					startActivity(intent);
//				}
//			}
//		});
		mTabViewPager.setCurrentItem(0);
	}
	

	/**
	 * 获取APP Banner
	 */
	private void getAppBanner() {
		loadingDialog = new LoadingDialog(mActivity);
		loadingDialog.show();
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
						loadingDialog.cancelDialog(loadingDialog);
					}
				});
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(loadingDialog!=null){
			loadingDialog.cancel();
			loadingDialog = null;
		}
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
