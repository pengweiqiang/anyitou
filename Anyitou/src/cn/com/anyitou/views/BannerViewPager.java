package cn.com.anyitou.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import cn.com.anyitou.views.banner.InfiniteViewPager;

public class BannerViewPager extends InfiniteViewPager{
	public BannerViewPager(Context context) {  
        super(context);  
    }  
      
    public BannerViewPager(Context context, AttributeSet attrs) {  
        super(context, attrs);  
    }  
  
    @Override  
    public boolean dispatchTouchEvent(MotionEvent ev) {  
        getParent().requestDisallowInterceptTouchEvent(true);//这句话的作用 告诉父view，我的单击事件我自行处理，不要阻碍我。    
        return super.dispatchTouchEvent(ev);  
    }  
}
