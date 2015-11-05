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
import cn.com.anyitou.utils.StringUtils;

/**
 * 标题栏, 可设置标题和左右图标
 * 
 * @author Will
 *
 */
public class ActionBar extends FrameLayout {

	private TextView mTitleView;
	private ImageView mLeftActionButton;
	private TextView mRightActionButton;
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
		mLeftActionButton = (ImageView) findViewById(R.id.leftActionButton);
		mRightActionButton = (TextView) findViewById(R.id.rightActionButton);
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
	
	public void setLeftActionButton(OnClickListener listener) {
		if(listener==null){
			hideLeftActionButtonText();
		}else{
			mLeftActionButton.setOnClickListener(listener);
		}
//		mLeftActionButton.setVisibility(View.VISIBLE);
	}
	public void setRightActionButton(String text,OnClickListener listener){
		if(StringUtils.isEmpty(text)){
			mRightActionButton.setVisibility(View.GONE);
		}else{
			mRightActionButton.setVisibility(View.VISIBLE);
			mRightActionButton.setText(text);
			mRightActionButton.setOnClickListener(listener);
		}
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
		mLeftActionButton.setVisibility(View.GONE);
	}
	/**
	 * 隐藏右上角按钮的文字
	 */
	public void hideRightActionButtonText() {
		mRightActionButton.setVisibility(View.GONE);
	}

	
}
