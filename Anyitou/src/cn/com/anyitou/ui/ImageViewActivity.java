package cn.com.anyitou.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import cn.com.anyitou.MyApplication;
import cn.com.anyitou.R;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.ZoomImageView;
import cn.com.universalimageloader.core.ImageLoader;
import cn.com.universalimageloader.core.listener.SimpleImageLoadingListener;

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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.image_view);
		super.onCreate(savedInstanceState);
		mActionBar = (ActionBar)findViewById(R.id.actionBar);
		imageUrl = this.getIntent().getStringExtra("url");
		name = this.getIntent().getStringExtra("name");
		onConfigureActionBar(mActionBar);
		
		
		
		ImageLoader.getInstance()
		.displayImage(imageUrl, imageView, 
				MyApplication.getInstance().getOptions2(R.drawable.index_banner),
				new SimpleImageLoadingListener(){

					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						// TODO Auto-generated method stub
						super.onLoadingComplete(imageUri, view, loadedImage);
					}
					
			
		}
		);
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
		imageView = (ZoomImageView)findViewById(R.id.image);
	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub

	}

}
