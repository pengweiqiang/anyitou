package cn.com.anyitou.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.adapters.FragmentAdapter;
import cn.com.anyitou.ui.CreditoTransferFragment;
import cn.com.anyitou.ui.ProjectListFragment;
import cn.com.anyitou.ui.base.BaseFragment;
import cn.com.anyitou.utils.Log;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.CustomViewPager;
import cn.com.anyitou.views.MyPopupWindow;
import cn.com.anyitou.views.PagerSlidingTabStrip;
import cn.com.anyitou.views.PagerSlidingTabStrip.OnPageChange;

/**
 * 投资
 * 
 * @author pengweiqiang
 * 
 */
@SuppressLint("NewApi")
public class InvestmentFragment extends BaseFragment {
	private View infoView;

	
	
	CustomViewPager mViewPager;
	PagerSlidingTabStrip tabs;
	private ActionBar mActionBar;

//	private RadioGroup tab_radioGroup;
//	private RadioButton tab_project_list;
//	private RadioButton tab_credito_transfer;

	List<Fragment> list;
	int showIndex;

	private void initView() {

		mViewPager = (CustomViewPager) infoView.findViewById(R.id.viewPager);
		// mViewPager.setScanScroll(false);
		mActionBar = (ActionBar) infoView.findViewById(R.id.actionBar);
		onConfigureActionBar(mActionBar);

//		tab_radioGroup = (RadioGroup) infoView.findViewById(R.id.rg_title);
//		tab_project_list = (RadioButton) infoView.findViewById(R.id.radio_project);
//		tab_credito_transfer = (RadioButton) infoView.findViewById(R.id.radio_credito);
		tabs = (PagerSlidingTabStrip)infoView.findViewById(R.id.tabs);
		

		// 实例化对象
		list = new ArrayList<Fragment>();

	}

	private void initListener() {
		mActionBar.hideLeftActionButtonText();
//		tab_radioGroup
//				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//
//					@Override
//					public void onCheckedChanged(RadioGroup group, int checkedId) {
//						if (checkedId == R.id.radio_project) {
//							mViewPager.setCurrentItem(0, false);
//						} else if (checkedId == R.id.radio_credito) {
//							mViewPager.setCurrentItem(1, false);
//						}
//					}
//				});
		mActionBar.setRightActionButton("筛选", new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showProjectCategory(v);
			}
		});

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		infoView = inflater.inflate(R.layout.project_list, container, false);
		mActionBar = (ActionBar) infoView.findViewById(R.id.actionBar);
		
		initView();

		initListener();
		showRightCondition(true);
		return infoView;
	}
	// 项目列表
	ProjectListFragment projectFragment;
	// 债券转让
	CreditoTransferFragment creTransferFragment ;
	
	private void initData(){
		// 设置数据源
		// 项目列表
		projectFragment = new ProjectListFragment();
		// 债券转让
		creTransferFragment = new CreditoTransferFragment();
		
		list.add(projectFragment);
		list.add(creTransferFragment);
		String [] titles = new String[]{"项目列表","债权转让"};
		// 设置适配器
		FragmentAdapter adapter = new FragmentAdapter(this.getChildFragmentManager(),titles,list);

		// 绑定适配器
		mViewPager.setAdapter(adapter);
		mViewPager.setOffscreenPageLimit(2);
		tabs.setViewPager(mViewPager);
//			// 设置滑动监听
		OnPageChange onPageChange = new OnPageChange() {
			
			@Override
			public void changePage(int position) {
				if(position==0){
					showRightCondition(true);
				}else {
					showRightCondition(false);
				}
			}
		};
		tabs.setOnPageListener(onPageChange);
		mViewPager.setCurrentItem(showIndex);
	}
	
	   
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if(getUserVisibleHint()){
			Log.e("InvestmentFragment", "onActivityCreated"+"  initdata");
			initData();
		}
		
	}
	
	private void showRightCondition(boolean isShow){
		if(isShow){
			mActionBar.showRightActionButtonText();
		}else {
			mActionBar.hideRightActionButtonText();
		}
	}
	@Override
	 public void setUserVisibleHint(boolean isVisibleToUser) {
        //判断Fragment中的ListView时候存在，判断该Fragment时候已经正在前台显示  通过这两个判断，就可以知道什么时候去加载数据了
		Log.e("about", "setUserVisibleHint");
		 if (isVisibleToUser && isVisible() && list.isEmpty()) {
			 Log.e("InvestmentFragment", "setUserVisibleHint"+"  initdata");
			 initData(); //加载数据的方法
		 }
		 super.setUserVisibleHint(isVisibleToUser);
	 }

	// 设置activity的导航条
	protected void onConfigureActionBar(ActionBar actionBar) {
		actionBar.setTitle(getResources().getString(R.string.project_list));
//		infoView.findViewById(R.id.actionBarLayout).setBackgroundColor(getResources().getColor(R.color.app_bg_color));
	}
	
	
	private PopupWindow popupWindow;
	private View mCategoryAll,mCategoryQidai,mCategoryFangDai,mCategoryChedai;
	
	@SuppressWarnings("deprecation")
	public void showProjectCategory(View view){
		
		popupWindow = MyPopupWindow.getPopupWindow(R.layout.project_condition_popupwindow, mActivity,0);
		View popupWindowView = popupWindow.getContentView();
		mCategoryAll = (TextView) popupWindowView.findViewById(R.id.category_all);
		mCategoryQidai = popupWindowView.findViewById(R.id.category_qidai);
		mCategoryFangDai = popupWindowView.findViewById(R.id.category_fangdai);
		mCategoryChedai = popupWindowView.findViewById(R.id.category_chedai);
		
		
		mCategoryAll.setOnClickListener(categoryClickListener);
		mCategoryQidai.setOnClickListener(categoryClickListener);
		mCategoryFangDai.setOnClickListener(categoryClickListener);
		mCategoryChedai.setOnClickListener(categoryClickListener);
		
		popupWindow.showAsDropDown(view);
	}
	OnClickListener categoryClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			popupWindow.dismiss();
			String category = "";
			switch (v.getId()) {
			case R.id.category_all:
				category = "";
				break;
			case R.id.category_qidai:
				category = "invest";
				break;
			case R.id.category_fangdai:
				category = "fangdai";
				break;
			case R.id.category_chedai:
				category = "chedai";
				break;
				
			default:
				break;
			}
			projectFragment.getInvestListByCategory(category);
		}
	};
	
	



}
