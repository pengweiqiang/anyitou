package cn.com.anyitou.views.banner;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by RxRead on 2015/9/24.
 */
public abstract class InfinitePagerAdapter extends RecyclingPagerAdapter {


    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        return null;
    }

    @Override
    /**
     * Note: use getItemCount instead*/
    public int getCount() {
        return getItemCount() * InfiniteViewPager.FakePositionHelper.MULTIPLIER;
    }

    @Deprecated

    protected View getViewInternal(int position, View convertView, ViewGroup container) {
        return getView(position % getItemCount(), convertView, container);
    }

    public abstract int getItemCount();

}
