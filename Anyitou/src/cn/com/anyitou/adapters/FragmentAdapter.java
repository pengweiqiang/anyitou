package cn.com.anyitou.adapters;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter {
	String[] _titles;
	private List<Fragment> fragments;

	public FragmentAdapter(FragmentManager fm, String[] titles,List<Fragment> framents) {
		super(fm);
		_titles = titles;
		this.fragments = framents;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return _titles[position];
	}

	@Override
	public int getCount() {
		return _titles.length;
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}
}
