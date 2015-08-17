package cn.com.anyitou.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;

import cn.com.anyitou.R;

import cn.com.anyitou.utils.DeviceInfo;
import com.nineoldandroids.view.ViewHelper;

public class SlidingMenu extends HorizontalScrollView {
	/**
	 * 屏幕宽度
	 */
	private int mScreenWidth;
	/**
	 * dp
	 */
	private int mMenuRightPadding;
	/**
	 * 菜单的宽度
	 */
	private int mMenuWidth;
	private int mHalfMenuWidth;

	private boolean isOpen;

	private boolean once;

	private ViewGroup mMenu;
	private ViewGroup mContent;
	private View mLeftNavi;

	public SlidingMenu(Context context, AttributeSet attrs) {
		this(context, attrs, 0);

	}

	public SlidingMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mScreenWidth = DeviceInfo.getScreenWidth(context);

		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.SlidingMenu, defStyle, 0);
		int n = a.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = a.getIndex(i);
			switch (attr) {
			case R.styleable.SlidingMenu_rightPadding:
				// 默认50
				mMenuRightPadding = a.getDimensionPixelSize(attr,
						(int) TypedValue.applyDimension(
								TypedValue.COMPLEX_UNIT_DIP, 50f,
								getResources().getDisplayMetrics()));// 默认为10DP
				break;
			}
		}
		a.recycle();
	}

	public SlidingMenu(Context context) {
		this(context, null, 0);
	}

	@Override
	public void fling(int velocityX) {
		// 重写fling方法，将速度除以三，减缓其滑动速度
		super.fling(velocityX / 3);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		/**
		 * 显示的设置一个宽度
		 */
		if (!once) {
			RelativeLayout wrapper = (RelativeLayout) getChildAt(0);
			mMenu = (ViewGroup) wrapper.getChildAt(0);
			mContent = (ViewGroup) wrapper.getChildAt(1);
			mLeftNavi = mContent.findViewById(R.id.leftActionButton);

			mMenuWidth = mScreenWidth - mMenuRightPadding;
			mHalfMenuWidth = mMenuWidth / 2;
			mMenu.getLayoutParams().width = mMenuWidth;
			mContent.getLayoutParams().width = mScreenWidth;

		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (changed) {
			// 将菜单隐藏
			this.scrollTo(mMenuWidth, 0);
			once = true;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		switch (action) {
		// Up时，进行判断，如果显示区域大于菜单宽度一半则完全显示，否则隐藏
		case MotionEvent.ACTION_UP:
			int scrollX = getScrollX();
			if (scrollX > mHalfMenuWidth) {
				this.smoothScrollTo(mMenuWidth, 0);
				isOpen = false;
			} else {
				this.smoothScrollTo(0, 0);
				isOpen = true;
			}
			return true;
		}
		System.out.println(this.getClass().getName()+"  onTouchEvent"+  (super.onTouchEvent(ev)&& isDispatchTouch));
		return super.onTouchEvent(ev)&& isDispatchTouch;
	}

	/**
	 * 打开菜单
	 */
	public void openMenu() {
		if (isOpen)
			return;
		this.smoothScrollTo(0, 0);
		isOpen = true;
		setNotClick(false);
	}
	
	private CustomViewPager viewPager;
	private SpringScrollView scrollview;
	public void setRightView(CustomViewPager viewPager,SpringScrollView scrollView){
		this.viewPager = viewPager;
		this.scrollview = scrollView;
	}
	
	private void setNotClick(boolean isCan){
		if(viewPager!=null){
			viewPager.setCanScoll(isCan);
		}
		if(scrollview!=null){
			scrollview.setCanScoll(isCan);
		}
	}

	/**
	 * 关闭菜单
	 */
	public void closeMenu() {
		if (isOpen) {
			this.smoothScrollTo(mMenuWidth, 0);
			isOpen = false;
		}
		setNotClick(true);
	}
	/**
	 * 获取菜单状态
	 * @return
	 */
	public boolean isOpen(){
		return isOpen;
	}
	/**
	 * 切换菜单状态
	 */
	public void toggle() {
		if (isOpen) {
			closeMenu();
		} else {
			openMenu();
		}
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		float scale = l * 1.0f / mMenuWidth;
		float leftScale = 1 - 0.3f * scale;
		float rightScale = 0.8f + scale * 0.2f;

		ViewHelper.setScaleX(mMenu, leftScale);
		ViewHelper.setScaleY(mMenu, leftScale);
		ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));
		ViewHelper.setTranslationX(mMenu, mMenuWidth * scale * 0.7f);

		ViewHelper.setPivotX(mContent, 0);
		ViewHelper.setPivotY(mContent, mContent.getHeight() / 2);
		if(rightScale<1){
			float rightScaleX = rightScale * 0.9f;
			ViewHelper.setScaleX(mLeftNavi, rightScaleX);
			ViewHelper.setScaleY(mLeftNavi, rightScaleX);
			setNotClick(false);
		}else{
			setNotClick(true);
		}
//		else{
//			ViewHelper.setScaleX(mLeftNavi, rightScale/0.9f);
//			ViewHelper.setScaleY(mLeftNavi, rightScale/0.9f);
//		}
		ViewHelper.setScaleX(mContent, rightScale);
		ViewHelper.setScaleY(mContent, rightScale);
		
		

	}

	private float xDistance, yDistance, xLast, yLast;

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			xDistance = yDistance = 0f;
			xLast = ev.getX();
			yLast = ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			final float curX = ev.getX();
			final float curY = ev.getY();
			if(!isOpen && curX-xLast >10 && viewPager.getCurrentItem()==0){
				
				return true;
			}
			xDistance += Math.abs(curX - xLast);
			yDistance += Math.abs(curY - yLast);
			xLast = curX;
			yLast = curY;

			/**
			 * X轴滑动距离大于Y轴滑动距离，也就是用户横向滑动时，返回false，ScrollView不处理这次事件，
			 * 让子控件中的TouchEvent去处理，所以横向滑动的事件交由ViewPager处理， ScrollView只处理纵向滑动事件
			 */
			if (xDistance > yDistance && !isOpen) {
				System.out.println("  x>y"+false);
				return false;
			}else if(xDistance > yDistance && xDistance>10  && isOpen){
				return true;
			}
/*		case MotionEvent.ACTION_UP:
			final float curXs = ev.getX();
			final float curYs = ev.getY();
			
			xDistance = Math.abs(curXs - xLast);
			yDistance = Math.abs(curYs - yLast);
			break;
*/		}
		System.out.println(this.getClass().getName()+ super.onInterceptTouchEvent(ev) +  isDispatchTouch+ "   onInterceptTouchEvent"+" "+(super.onInterceptTouchEvent(ev) && isDispatchTouch));
		return super.onInterceptTouchEvent(ev) && isDispatchTouch;
	}
	private boolean isDispatchTouch = true;
	
	public void setDispatchTouch(boolean isDispatchTouch){
		this.isDispatchTouch = isDispatchTouch;
	}

}
