package cn.com.anyitou.adapters;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

public class ViewPagerAdapter extends PagerAdapter{
	
	private ArrayList<View> views;
	public ViewPagerAdapter(ArrayList<View> views){
		this.views = views;
	}
	@Override
	public int getCount() {
		if(views != null){
			return views.size();
		}
		return 0;
	}
	/**
	 * 判断是否由对象生成界面
	 */
	@Override
	public boolean isViewFromObject(View view, Object obj) {
		return view == obj;
	}
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		((ViewPager) container).addView(views.get(position), 0);
		return views.get(position);
	}
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {

		((ViewPager)container).removeView(views.get(position));
//		super.destroyItem(container, position, object);
	}
	
	
	
}
