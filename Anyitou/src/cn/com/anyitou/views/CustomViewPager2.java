package cn.com.anyitou.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
/**
 * 禁止滑动的viewPager
 * @author will
 *
 */
public class CustomViewPager2 extends ViewPager{

	  
    public CustomViewPager2(Context context) {  
        super(context);  
    }  
  
    public CustomViewPager2(Context context, AttributeSet attrs) {  
        super(context, attrs);  
    }  
  
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
     
    	return false;
    }

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		return false;
	}
    
	
	
}
