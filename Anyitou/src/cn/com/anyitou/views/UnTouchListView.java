package cn.com.anyitou.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * 
 */
public class UnTouchListView extends ListView {
	public UnTouchListView(Context context) {
		super(context);
	}

	public UnTouchListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public UnTouchListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}


	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return false;
	}
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return false;
	}
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return false;
	}

}