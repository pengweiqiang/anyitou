package cn.com.anyitou.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

public class VerticalScrollView extends ScrollView {  
	  
    private GestureDetector mGestureDetector;  
    
  //手指按下时记录是否可以继续下拉
  	private boolean canPullDown = false;
  	
  //ScrollView的子View， 也是ScrollView的唯一一个子View
  	private View contentView; 
  	
    public VerticalScrollView(Context context, AttributeSet attrs) {  
        super(context, attrs);  
    }  
    @Override
	protected void onFinishInflate() {
		if (getChildCount() > 0) {
			contentView = getChildAt(0);
		}
	}
  
    double startY;
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startY = ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			double currentY = ev.getY();
			if( (getScrollY() == 0 && isCanPullUp() )||(isCanPullDown() && !isCanPullUp() && currentY-startY>0)){
				return false;
			}
			break;

		default:
			break;
		}
		return super.onTouchEvent(ev);
	}  
	
	/**
	 * 判断是否滚动到顶部
	 */
	private boolean isCanPullDown() {
		return getScrollY() == 0 || 
				contentView.getHeight() < getHeight() + getScrollY();
	}
	
	/**
	 * 判断是否滚动到底部
	 */
	private boolean isCanPullUp() {
		return contentView.getHeight() <= getHeight() + getScrollY();
	}
    
}
