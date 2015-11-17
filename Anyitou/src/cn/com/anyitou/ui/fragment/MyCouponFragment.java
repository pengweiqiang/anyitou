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
import cn.com.anyitou.ui.base.coupon.OutDateCouponFragment;
import cn.com.anyitou.ui.base.coupon.UnuseCouponFragment;
import cn.com.anyitou.ui.base.coupon.UsedCouponFragment;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.CustomViewPager;
import cn.com.anyitou.views.PagerSlidingTabStrip;

/**
 * 我的优惠券
 * 
 * @author will
 * 
 */
@SuppressLint("NewApi")
public class MyCouponFragment extends BaseFragmentActivity {

	CustomViewPager mViewPager;
	PagerSlidingTabStrip tabs;
	private ActionBar mActionBar;

	// private RadioGroup tab_radioGroup;
	// private RadioButton tab_unuse_coupon;
	// private RadioButton tab_used_coupon;
	// private RadioButton tab_outdate_coupon;

	List<Fragment> list;
	int showIndex;

	private void initView() {

		mViewPager = (CustomViewPager) findViewById(R.id.viewPager);
		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		// mViewPager.setScanScroll(false);
		mActionBar = (ActionBar) findViewById(R.id.actionBar);
		onConfigureActionBar(mActionBar);

	}

	private void initListener() {
		mActionBar.setLeftActionButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AppManager.getAppManager().finishActivity();
			}
		});
		// tab_radioGroup
		// .setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
		// {
		//
		// @Override
		// public void onCheckedChanged(RadioGroup group, int checkedId) {
		// if (checkedId == R.id.radio_coupon_unuse) {
		// mViewPager.setCurrentItem(0, false);
		// } else if (checkedId == R.id.radio_coupon_used) {
		// mViewPager.setCurrentItem(1, false);
		// }else if (checkedId == R.id.radio_coupon_outdate) {
		// mViewPager.setCurrentItem(2, false);
		// }
		// }
		// });

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

	private void initData() {
		// 未使用
		UnuseCouponFragment unuseCouponFragment = new UnuseCouponFragment();
		// 已使用
		UsedCouponFragment usedCouponFragment = new UsedCouponFragment();
		// 已过期
		OutDateCouponFragment outDateCouponFragment = new OutDateCouponFragment();
		// 实例化对象
		list = new ArrayList<Fragment>();
		list.add(unuseCouponFragment);
		list.add(usedCouponFragment);
		list.add(outDateCouponFragment);

		// 绑定适配器
		String titles[] = new String[] { "未使用", "已使用", "已过期" };
		FragmentAdapter adapter = new FragmentAdapter(
				getSupportFragmentManager(), titles, list);
		mViewPager.setAdapter(adapter);
		mViewPager.setOffscreenPageLimit(3);
		tabs.setViewPager(mViewPager);
		mViewPager.setCurrentItem(showIndex);
	}

	// 设置activity的导航条
	protected void onConfigureActionBar(ActionBar actionBar) {
		actionBar.setTitle(getResources().getString(R.string.my_coupon));
		// infoView.findViewById(R.id.actionBarLayout).setBackgroundColor(getResources().getColor(R.color.app_bg_color));
	}

}
