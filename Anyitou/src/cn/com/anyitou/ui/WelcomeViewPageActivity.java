package cn.com.anyitou.ui;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import cn.com.anyitou.R;

import cn.com.anyitou.adapters.ViewPagerAdapter;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.utils.DeviceInfo;

public class WelcomeViewPageActivity extends BaseActivity implements
		OnPageChangeListener, OnClickListener {

	private ViewPager viewPager;
	private ViewPagerAdapter vpAdapter;
	private ArrayList<View> views;
	private int currentIndex;
	ImageView[] points;

	// 引导图片资源

	private static final int[] pics = { R.drawable.guide1,R.drawable.guide2, R.drawable.guide3,R.drawable.guide4 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		setContentView(R.layout.welcome);
		super.onCreate(savedInstanceState);
//		tintManager.setTintColor(getResources().getColor(R.color.white));
		initData();
	}

	/**
	 * 启动主页面
	 */
	private void startMainActivity() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		AppManager.getAppManager().finishActivity();
	}

	@Override
	public void initView() {
		views = new ArrayList<View>();
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		vpAdapter = new ViewPagerAdapter(views);
	}

	@Override
	public void initListener() {

	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		// 定义一个布局并设置参数
		int width = DeviceInfo.getScreenWidth(mContext);

		LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
				width, (int) (width * 1.0 / 640 * 1136));

		// 初始化引导图片列表
		for (int i = 0; i < pics.length; i++) {
			ImageView iv = new ImageView(this);
			iv.setLayoutParams(mParams);
			// 防止图片不能填满屏幕
//			iv.setScaleType(ScaleType.FIT_XY);
			// 加载图片资源
			iv.setImageResource(pics[i]);
			// ImageLoader.getInstance().displayImage(mImageViews.get(i), iv);
			if (i == pics.length - 1) {
				iv.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						startMainActivity();
					}
				});
			}
			views.add(iv);
		}

		// 设置数据
		viewPager.setAdapter(vpAdapter);
		// 设置监听
		viewPager.setOnPageChangeListener(this);

		// 初始化底部小点
		initPoint();
	}

	/**
	 * 初始化底部小点
	 */
	private void initPoint() {
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll);

		points = new ImageView[pics.length];

		// 循环取得小点图片
		for (int i = 0; i < pics.length; i++) {
			// 得到一个LinearLayout下面的每一个子元素
			points[i] = (ImageView) linearLayout.getChildAt(i);
			points[i].setVisibility(View.VISIBLE);
			// 默认都设为灰色
			points[i].setEnabled(true);
			// 给每个小点设置监听
			points[i].setOnClickListener(this);
			// 设置位置tag，方便取出与当前位置对应
			points[i].setTag(i);
		}

		// 设置当面默认的位置
		currentIndex = 0;
		// 设置为白色，即选中状态
		points[currentIndex].setEnabled(false);
	}

	/**
	 * 滑动状态改变时调用
	 */
	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	/**
	 * 当前页面滑动时调用
	 */
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	/**
	 * 新的页面被选中时调用
	 */
	@Override
	public void onPageSelected(int arg0) {
		// 设置底部小点选中状态
		setCurDot(arg0);
	}

	@Override
	public void onClick(View v) {
		int position = (Integer) v.getTag();
		setCurView(position);
		setCurDot(position);
	}

	/**
	 * 设置当前页面的位置
	 */
	private void setCurView(int position) {
		if (position < 0 || position >= pics.length) {
			return;
		}
		viewPager.setCurrentItem(position);
	}

	/**
	 * 设置当前的小点的位置
	 */
	private void setCurDot(int positon) {
		if (positon < 0 || positon > pics.length - 1 || currentIndex == positon) {
			return;
		}
		points[positon].setEnabled(false);
		points[currentIndex].setEnabled(true);

		currentIndex = positon;
	}

}
