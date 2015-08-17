package cn.com.anyitou.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import cn.com.anyitou.R;

/**
 * 标题栏, 可设置标题和左右图标
 * 
 * @author Will
 *
 */
public class ActionBar extends FrameLayout {

	private TextView mTitleView;
	private TextView mLeftActionButton;
	private TextView mActionBarTitle;
	private ImageView mIvCenterImage;
	private View mViewCenter;

	public ActionBar(Context context) {
		super(context);
	}

	public ActionBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ActionBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.action_bar_layout,
				this);
		mTitleView = (TextView) findViewById(R.id.actionBarTitle);
		mLeftActionButton = (TextView) findViewById(R.id.leftActionButton);
		mActionBarTitle = (TextView)findViewById(R.id.actionBarTitle);
		mIvCenterImage = (ImageView)findViewById(R.id.center_image);
		mViewCenter = findViewById(R.id.center_rl);
	}

	public void setTitle(int resId) {
		mTitleView.setText(resId);
	}

	public void setTitle(String text) {
		mTitleView.setText(text);
	}
	
	public void setTitleTextColor(int resId){
		mTitleView.setTextColor(getResources().getColor(resId));
	}
	
	public void setLeftActionButton(int icon, OnClickListener listener) {
		if(icon != R.drawable.btn_back){
			hideLeftActionButtonText();
		}
		if(icon!=0){
			Drawable drawable= getResources().getDrawable(icon);
			/// 这一步必须要做,否则不会显示.
			drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
			mLeftActionButton.setCompoundDrawables(drawable,null,null,null);
		}
		mLeftActionButton.setOnClickListener(listener);
//		mLeftActionButton.setVisibility(View.VISIBLE);
	}
	/**
	 * 给title加点击事件
	 * @param listener
	 */
	public void setActionBarTitleClickListener(OnClickListener listener){
		mViewCenter.setOnClickListener(listener);
		mIvCenterImage.setVisibility(View.VISIBLE);
	}
	
	public void startRotateAnimImageView(Animation animation){
		mIvCenterImage.startAnimation(animation);
	}

	/**
	 * 隐藏左上角按钮的文字
	 */
	public void hideLeftActionButtonText() {
		mLeftActionButton.setText("");
	}

	
}
