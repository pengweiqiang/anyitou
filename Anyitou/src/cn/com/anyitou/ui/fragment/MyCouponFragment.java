package cn.com.anyitou.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import cn.com.anyitou.R;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.ui.base.BaseFragmentActivity;
import cn.com.anyitou.ui.base.coupon.OutDateCouponFragment;
import cn.com.anyitou.ui.base.coupon.UnuseCouponFragment;
import cn.com.anyitou.ui.base.coupon.UsedCouponFragment;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.CustomViewPager;

/**
 * 我的优惠券
 * 
 * @author will
 * 
 */
@SuppressLint("NewApi")
public class MyCouponFragment extends BaseFragmentActivity {

	
	
	CustomViewPager mViewPager;
	private ActionBar mActionBar;

	private RadioGroup tab_radioGroup;
	private RadioButton tab_unuse_coupon;
	private RadioButton tab_used_coupon;
	private RadioButton tab_outdate_coupon;

	List<Fragment> list;
	int showIndex;

	private void initView() {

		mViewPager = (CustomViewPager) findViewById(R.id.viewPager);
		// mViewPager.setScanScroll(false);
		mActionBar = (ActionBar) findViewById(R.id.actionBar);
		onConfigureActionBar(mActionBar);

		tab_radioGroup = (RadioGroup) findViewById(R.id.rg_title);
		tab_unuse_coupon = (RadioButton) findViewById(R.id.radio_coupon_unuse);
		tab_used_coupon = (RadioButton) findViewById(R.id.radio_coupon_used);
		tab_outdate_coupon = (RadioButton) findViewById(R.id.radio_coupon_outdate);
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
		tab_radioGroup
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						if (checkedId == R.id.radio_coupon_unuse) {
							mViewPager.setCurrentItem(0, false);
						} else if (checkedId == R.id.radio_coupon_used) {
							mViewPager.setCurrentItem(1, false);
						}else if (checkedId == R.id.radio_coupon_outdate) {
							mViewPager.setCurrentItem(2, false);
						}
					}
				});

	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.my_coupon);
		super.onCreate(savedInstanceState);
		mActionBar = (ActionBar) findViewById(R.id.actionBar);
		
		initView();

		initListener();
		
		initData();
	}
	
	private void initData(){
			// 未使用
			UnuseCouponFragment unuseCouponFragment = new UnuseCouponFragment();
			// 已使用
			UsedCouponFragment usedCouponFragment = new UsedCouponFragment();
			// 已过期
			OutDateCouponFragment outDateCouponFragment = new OutDateCouponFragment();
			
			list.add(unuseCouponFragment);
			list.add(usedCouponFragment);
			list.add(outDateCouponFragment);

			// 设置适配器
			FragmentPagerAdapter adapter = new FragmentPagerAdapter(
					getSupportFragmentManager()) {

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
			mViewPager.setOffscreenPageLimit(3);
			// 设置滑动监听
			mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

				@Override
				public void onPageSelected(int position) {
					if(position == 0){
						tab_unuse_coupon.setChecked(true);
					}else if(position ==1){
						tab_used_coupon.setChecked(true);
					}else if(position == 2){
						tab_outdate_coupon.setChecked(true);
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
	   
	

	// 设置activity的导航条
	protected void onConfigureActionBar(ActionBar actionBar) {
		actionBar.setTitle(getResources().getString(R.string.my_coupon));
//		infoView.findViewById(R.id.actionBarLayout).setBackgroundColor(getResources().getColor(R.color.app_bg_color));
	}
	
	



}
