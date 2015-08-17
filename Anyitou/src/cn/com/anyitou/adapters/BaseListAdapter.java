package cn.com.anyitou.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 列表Adapter基类
 */
public class BaseListAdapter extends BaseAdapter {

    public boolean mBusy = false;

    public void setBusyState(boolean b) {
	mBusy = b;
    }

    @Override
    public int getCount() {
	return 0;
    }

    @Override
    public Object getItem(int position) {
	return null;
    }

    @Override
    public long getItemId(int position) {
	return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	return null;
    }
}
