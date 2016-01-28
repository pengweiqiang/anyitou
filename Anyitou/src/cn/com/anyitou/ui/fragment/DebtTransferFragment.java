package cn.com.anyitou.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import cn.com.anyitou.R;
import cn.com.anyitou.adapters.FragmentAdapter;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.ui.base.BaseFragmentActivity;
import cn.com.anyitou.ui.debttransfer.TransferEndFragment;
import cn.com.anyitou.ui.debttransfer.TransferableFragment;
import cn.com.anyitou.ui.debttransfer.TransferingFragment;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.CustomViewPager;
import cn.com.anyitou.views.PagerSlidingTabStrip;

/**
 * 债权转让
 * 
 * @author pengweiqiang
 * 
 */
@SuppressLint("NewApi")
public class DebtTransferFragment extends BaseFragmentActivity {

	
	
	CustomViewPager mViewPager;
	PagerSlidingTabStrip tabs;
	private ActionBar mActionBar;

//	private RadioGroup tab_radioGroup;
//	private RadioButton tab_trasnferable;
//	private RadioButton tab_trasfering;
//	private RadioButton tab_transfer_end;

	List<Fragment> list;
	int showIndex;

	private void initView() {

		mViewPager = (CustomViewPager) findViewById(R.id.viewPager);
		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		// mViewPager.setScanScroll(false);
		mActionBar = (ActionBar) findViewById(R.id.actionBar);
		onConfigureActionBar(mActionBar);

//		tab_radioGroup = (RadioGroup) findViewById(R.id.rg_title);
//		tab_trasnferable = (RadioButton) findViewById(R.id.radio_transferable);
//		tab_trasfering = (RadioButton) findViewById(R.id.radio_trasfering);
//		tab_transfer_end = (RadioButton) findViewById(R.id.radio_transfer_end);
		// 实例化对象
		list = new ArrayList<Fragment>();

	}

	private void initListener() {
		mActionBar.setLeftActionButton(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AppManager.getAppManager().finishActivity();
			}
		});
//		tab_radioGroup
//				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//
//					@Override
//					public void onCheckedChanged(RadioGroup group, int checkedId) {
//						if (checkedId == R.id.radio_transferable) {
//							mViewPager.setCurrentItem(0, false);
//						} else if (checkedId == R.id.radio_trasfering) {
//							mViewPager.setCurrentItem(1, false);
//						}else if (checkedId == R.id.radio_transfer_end) {
//							mViewPager.setCurrentItem(2, false);
//						}
//					}
//				});

	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.my_debt_transfer);
		super.onCreate(savedInstanceState);
		mActionBar = (ActionBar) findViewById(R.id.actionBar);
		
		initView();

		initListener();
		
		initData();
	}
	
	private void initData(){
			// 可转让
			TransferableFragment transferableFragment = new TransferableFragment();
			// 转让中
			TransferingFragment transferingFragment = new TransferingFragment();
			// 已结束
			TransferEndFragment transferEndFragment = new TransferEndFragment();
			
			list.add(transferableFragment);
			list.add(transferingFragment);
			list.add(transferEndFragment);

			// 设置适配器
//			FragmentPagerAdapter adapter = new FragmentPagerAdapter(
//					getSupportFragmentManager()) {
//
//				@Override
//				public int getCount() {
//					return list.size();
//				}
//
//				@Override
//				public Fragment getItem(int arg0) {
//					return list.get(arg0);
//				}
//			};

			// 绑定适配器
			String titles[] = new String[]{"可转让","转让中","已结束"};
			FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), titles, list);
			mViewPager.setAdapter(adapter);
			mViewPager.setOffscreenPageLimit(3);
			tabs.setViewPager(mViewPager);
			// 设置滑动监听
//			mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
//
//				@Override
//				public void onPageSelected(int position) {
//					if(position == 0){
//						tab_trasnferable.setChecked(true);
//					}else if(position ==1){
//						tab_trasfering.setChecked(true);
//					}else if(position == 2){
//						tab_transfer_end.setChecked(true);
//					}
//
//				}
//
//				@Override
//				public void onPageScrolled(int arg0, float arg1, int arg2) {
//					Log.i("tuzi", arg0 + "," + arg1 + "," + arg2);
//
//				}
//
//				@Override
//				public void onPageScrollStateChanged(int arg0) {
//					// TODO Auto-generated method stub
//
//				}
//			});
			mViewPager.setCurrentItem(showIndex);
	}
	   
	

	// 设置activity的导航条
	protected void onConfigureActionBar(ActionBar actionBar) {
		actionBar.setTitle(getResources().getString(R.string.credito_transfer));
//		infoView.findViewById(R.id.actionBarLayout).setBackgroundColor(getResources().getColor(R.color.app_bg_color));
	}
	
	



}
