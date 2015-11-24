package cn.com.anyitou.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.com.anyitou.MyApplication;
import cn.com.anyitou.R;
import cn.com.anyitou.entity.Urls;
import cn.com.anyitou.views.DragImageView;
import cn.com.anyitou.views.banner.InfinitePagerAdapter;
import cn.com.universalimageloader.core.ImageLoader;

public class ImageMorePagerAdapter extends InfinitePagerAdapter{

    private final LayoutInflater mInflater;
    private final Context mContext;

    public List<Urls> mList;

    private int height;
    public void setDataList(List<Urls> list) {
        this.mList = list;
        this.notifyDataSetChanged();
    }

    
    @Override
	public int getCount() {
    	return mList==null?0:mList.size();
	}


	public ImageMorePagerAdapter(Context context) {
        mContext=context;
        mInflater = LayoutInflater.from(mContext);
    }
    


    @Override
    public View getView(int position, View view, ViewGroup container) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = mInflater.inflate(R.layout.image_view_item, container, false);
//    		LayoutParams viewParams = view.getLayoutParams();
//    		viewParams.height = height;
//    		view.setLayoutParams(viewParams);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        Urls item = mList.get(position);
        
        ImageLoader.getInstance().displayImage(
				item.getUrl(),
				holder.image,
				MyApplication.getInstance()
						.getOptions2(R.drawable.index_banner));
//        holder.image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.index_banner)); 
        return view;
    }

    

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }


    public class ViewHolder{
    	cn.com.anyitou.views.DragImageView image;

        public ViewHolder(View view) {
//            name = (TextView) view.findViewById(R.id.item_name);
        	image = (DragImageView) view.findViewById(R.id.image);
        }
    }

}
