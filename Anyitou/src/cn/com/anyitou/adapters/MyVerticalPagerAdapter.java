package cn.com.anyitou.adapters;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyVerticalPagerAdapter extends FragmentPagerAdapter {
	private List<Fragment> fragments;
	

	public MyVerticalPagerAdapter(FragmentManager fm) {
		super(fm);
	}
	
	public MyVerticalPagerAdapter(FragmentManager fm,List<Fragment> oneListFragments){
		super(fm);
		this.fragments=oneListFragments;
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}
	
	@Override
	public int getItemPosition(Object object) {
		return super.getItemPosition(object);
	}

}
