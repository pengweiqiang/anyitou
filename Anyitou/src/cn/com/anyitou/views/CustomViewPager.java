package cn.com.anyitou.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CustomViewPager extends ViewPager {

	// private Context mContext = null;

	public CustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// this.mContext = context;
	}

	public CustomViewPager(Context context) {
		super(context);
		// this.mContext = context;
	}

	public static ViewPager mPager;// 此处我直接在调用的时候静态赋值过来 的
	
	
	/*public boolean dispatchTouchEvent(MotionEvent ev) {
		boolean dipatch = true;
		final float x = ev.getX();
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			//requestDisallowInterceptTouchEvent(true);
			abc = 1;
			mLastMotionX = x;
			break;
		case MotionEvent.ACTION_MOVE:
			if (abc == 1) {
				if (x - mLastMotionX > 5 && getCurrentItem() == 0) {
					abc = 0;
					//requestDisallowInterceptTouchEvent(false);
					System.out.println("false ==========");
					dipatch = false;
					return dipatch;
				}

				if (x - mLastMotionX < -5
						&& getCurrentItem() == getAdapter().getCount() - 1) {
					abc = 0;
					requestDisallowInterceptTouchEvent(false);
					System.out.println("false ==========111111111");
					return false;
				}
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			requestDisallowInterceptTouchEvent(false);
			break;
		}
		return super.dispatchTouchEvent(ev)&&dipatch;
	}*/
	
	private boolean isInter = false;
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if(!isCan){
			System.out.println("ViewPager  onTOuchEvent"+false);
			return false;
		}

//		boolean dipatch = true;
//		final float x = ev.getX();
//		switch (ev.getAction()) {
//		case MotionEvent.ACTION_DOWN:
//			//requestDisallowInterceptTouchEvent(true);
//			abc = 1;
//			firstDownX = ev.getX();
//			firstDownY = ev.getY();
//			mLastMotionX = x;
//			break;
//		case MotionEvent.ACTION_MOVE:
//			if (abc == 1) {
//				System.out.println("move "+(ev.getX()-firstDownX));
//				if (ev.getX()-firstDownX > 0 && /*x - mLastMotionX > 5 && */getCurrentItem() == 0) {
//					abc = 0;
//					System.out.println("false ==========");
//					dipatch = false;
//					
//				}else{
//					dipatch = true;
//				}
//				
////				if (x - mLastMotionX < -5
////						&& getCurrentItem() == getAdapter().getCount() - 1) {
////					abc = 0;
////					System.out.println("false ==========111111111");
////					return false;
////				}
//			}
//			break;
//		}
//		isInter = dipatch;
//		System.out.println(this.getClass().getName()+"   onInterceptTouchEvent"+"  "+dipatch);
//		return dipatch;
		return super.onInterceptTouchEvent(ev);
	}
	private boolean isCan = true;
	public void setCanScoll(boolean isCan){
		this.isCan = isCan;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		System.out.println(this.getClass().getName()+"   onTouchEvent"+(super.onTouchEvent(ev))+"  isCan："+isCan);
		if(!isCan){
			System.out.println("ViewPager  onTOuchEvent"+false);
			return false;
		}
//		boolean dipatch = true;
//		final float x = ev.getX();
//		switch (ev.getAction()) {
//		case MotionEvent.ACTION_DOWN:
//			//requestDisallowInterceptTouchEvent(true);
//			abc = 1;
//			firstDownX = ev.getX();
//			firstDownY = ev.getY();
//			mLastMotionX = x;
//			break;
//		case MotionEvent.ACTION_MOVE:
//			if (abc == 1) {
//				System.out.println("move "+(ev.getX()-firstDownX));
//				if (ev.getX()-firstDownX > 0 && /*x - mLastMotionX > 5 && */getCurrentItem() == 0) {
//					abc = 0;
//					System.out.println("false ==========");
//					dipatch = false;
//					return dipatch;
//				}else{
//					dipatch = true;
//				}
//				
////				if (x - mLastMotionX < -5
////						&& getCurrentItem() == getAdapter().getCount() - 1) {
////					abc = 0;
////					System.out.println("false ==========111111111");
////					return false;
////				}
//			}
//			break;
//		}
//		System.out.println(this.getClass().getName()+"   onTouchEvent"+(super.onTouchEvent(ev))+"  "+dipatch);
//		return super.onTouchEvent(ev)&& dipatch;
		return super.onTouchEvent(ev);
	}

}
