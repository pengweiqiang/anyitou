package cn.com.anyitou.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import cn.com.anyitou.R;
import cn.com.anyitou.ui.CreditoTransferFragment;
import cn.com.anyitou.ui.ProjectListFragment;
import cn.com.anyitou.ui.base.BaseFragment;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.CustomViewPager;

/**
 * 投资
 * 
 * @author will
 * 
 */
@SuppressLint("NewApi")
public class InvestmentFragment extends BaseFragment {
	private View infoView;

	
	
	CustomViewPager mViewPager;
	private ActionBar mActionBar;

	private RadioGroup tab_radioGroup;
	private RadioButton tab_project_list;
	private RadioButton tab_credito_transfer;

	List<Fragment> list;
	int showIndex;

	private void initView() {

		mViewPager = (CustomViewPager) infoView.findViewById(R.id.viewPager);
		// mViewPager.setScanScroll(false);
		mActionBar = (ActionBar) infoView.findViewById(R.id.actionBar);
		onConfigureActionBar(mActionBar);

		tab_radioGroup = (RadioGroup) infoView.findViewById(R.id.rg_title);
		tab_project_list = (RadioButton) infoView.findViewById(R.id.radio_project);
		tab_credito_transfer = (RadioButton) infoView.findViewById(R.id.radio_credito);

		// 实例化对象
		list = new ArrayList<Fragment>();

	}

	private void initListener() {
		mActionBar.hideLeftActionButtonText();
		tab_radioGroup
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						if (checkedId == R.id.radio_project) {
							mViewPager.setCurrentItem(0, false);
						} else if (checkedId == R.id.radio_credito) {
							mViewPager.setCurrentItem(1, false);
						}
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
		return infoView;
	}
	
	private void initData(){
		// 设置数据源
			// 项目列表
			ProjectListFragment jlysFragment = new ProjectListFragment();
			// 债券转让
			CreditoTransferFragment kmrsFragment = new CreditoTransferFragment();
			
			list.add(jlysFragment);
			list.add(kmrsFragment);

			// 设置适配器
			FragmentPagerAdapter adapter = new FragmentPagerAdapter(
					this.getChildFragmentManager()) {

				@Override
				public int getCount() {
					return list.size();
				}

				@Override
				public Fragment getItem(int arg0) {
					return list.get(arg0);
				}
			};

			// 绑定适配器
			mViewPager.setAdapter(adapter);
			mViewPager.setOffscreenPageLimit(2);
			// 设置滑动监听
			mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

				@Override
				public void onPageSelected(int position) {
					if(position == 0){
						tab_project_list.setChecked(true);
					}else if(position ==1){
						tab_credito_transfer.setChecked(true);
					}

				}

				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					Log.i("tuzi", arg0 + "," + arg1 + "," + arg2);

				}

				@Override
				public void onPageScrollStateChanged(int arg0) {
					// TODO Auto-generated method stub

				}
			});
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
	
	



}
