package cn.com.anyitou.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
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
	
	private ImageView mIvLeft,mIvRight;
	
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
		mIvLeft = (ImageView)findViewById(R.id.left_image);
		mIvRight = (ImageView)findViewById(R.id.right_image);
	}

	@Override
	public void initListener() {
		mIvLeft.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				int position = viewPager.getCurrentItem();
				if(position>0){
					viewPager.setCurrentItem(--position);
				}
			}
		});
		mIvRight.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				int position = viewPager.getCurrentItem();
				if(position<urls.size()-1){
					viewPager.setCurrentItem(++position);
				}
			}
		});
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				if(position<=0){
					mIvLeft.setImageDrawable(getResources().getDrawable(R.drawable.left_btn_over_icon));
					if(urls.size()>1){
						mIvRight.setImageDrawable(getResources().getDrawable(R.drawable.right_btn_icon));
					}
				}else if(position>=urls.size()-1){
					if(position>0){
						mIvLeft.setImageDrawable(getResources().getDrawable(R.drawable.left_btn_icon));
					}
					mIvRight.setImageDrawable(getResources().getDrawable(R.drawable.right_btn_over_icon));
				}else {
					mIvLeft.setImageDrawable(getResources().getDrawable(R.drawable.left_btn_icon));
					mIvRight.setImageDrawable(getResources().getDrawable(R.drawable.right_btn_icon));
				}
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
		if(urls==null || urls.isEmpty() || urls.size()==1){
			mIvLeft.setVisibility(View.GONE);
			mIvRight.setVisibility(View.GONE);
		}
		imageAdapter = new ImageMorePagerAdapter(mContext);
		imageAdapter.setDataList(urls);
		viewPager.setAdapter(imageAdapter);
		viewPager.setCurrentItem(position);
	}

}
