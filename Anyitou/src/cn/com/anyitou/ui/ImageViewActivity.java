package cn.com.anyitou.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.adapters.ImageMorePagerAdapter;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.Urls;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.ZoomImageView;

/**
 * 显示图片
 * 
 * @author will
 * 
 */
public class ImageViewActivity extends BaseActivity {

	ActionBar mActionBar;
	private ZoomImageView imageView;
	String imageUrl;
	String name;
	int position;
	private TextView mTvCurrentPoint;
	
	private List<Urls> urls = new ArrayList<Urls>();
	
	private ViewPager viewPager;
	private ImageMorePagerAdapter imageAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.image_view);
		super.onCreate(savedInstanceState);
		mActionBar = (ActionBar)findViewById(R.id.actionBar);
		urls = this.getIntent().getParcelableArrayListExtra("urls");
//		imageUrl = this.getIntent().getStringExtra("url");
		position = this.getIntent().getIntExtra("position", 0);
		name = this.getIntent().getStringExtra("name");
		onConfigureActionBar(mActionBar);
		
		mTvCurrentPoint.setText((position+1)+"/"+urls.size());
		initData();
	}

	// 设置activity的导航条
	protected void onConfigureActionBar(ActionBar actionBar) {
		actionBar.setTitle(name);
		actionBar.setLeftActionButton(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AppManager.getAppManager().finishActivity(ImageViewActivity.this);
			}
		});
	}

	@Override
	public void initView() {
//		imageView = (ZoomImageView)findViewById(R.id.image);
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		mTvCurrentPoint = (TextView) findViewById(R.id.tip_point);
	}

	@Override
	public void initListener() {
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				mTvCurrentPoint.setText((position+1)+"/"+urls.size());
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
	}
	
	private void initData(){
		
		imageAdapter = new ImageMorePagerAdapter(mContext);
		imageAdapter.setDataList(urls);
		viewPager.setAdapter(imageAdapter);
		viewPager.setCurrentItem(position);
	}

}
