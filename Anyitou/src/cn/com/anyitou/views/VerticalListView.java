package cn.com.anyitou.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

/**
 * 重写ListView解决ListView内部ViewPaper滑动事件冲突问题
 * 
 */
public class VerticalListView extends ListView {
	private GestureDetector mGestureDetector;
	View.OnTouchListener mGestureListener;

	public VerticalListView(Context context) {
		super(context);
	}

	public VerticalListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mGestureDetector = new GestureDetector(new YScrollDetector());
		setFadingEdgeLength(0);
	}

	public VerticalListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return super.onInterceptTouchEvent(ev)
				&& mGestureDetector.onTouchEvent(ev);
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
			System.out.println(String.valueOf(currentY-startY));
			if( this.getChildCount() > 0 && this.getFirstVisiblePosition() == 0 && currentY-startY>0 && this.getChildAt(0).getTop() >= 0){//第一个item完成展示
				return false;
			}
			break;
		case MotionEvent.ACTION_UP:
			
			break;

		default:
			break;
		}
		return super.onTouchEvent(ev);
	}



	class YScrollDetector extends SimpleOnGestureListener {
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			if (distanceY != 0 && distanceX != 0) {

			}
			System.out.println("  Y"+Math.abs(distanceY) + "   X"+Math.abs(distanceX));
			if (getFirstVisiblePosition() != 0 && Math.abs(distanceY) >= Math.abs(distanceX)) {
				return true;
			}
			return false;
		}
	}

}