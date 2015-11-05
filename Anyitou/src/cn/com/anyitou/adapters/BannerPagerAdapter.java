package cn.com.anyitou.adapters;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import cn.com.anyitou.MyApplication;
import cn.com.anyitou.R;
import cn.com.anyitou.entity.Banner;
import cn.com.anyitou.ui.WebActivity;
import cn.com.anyitou.views.banner.InfinitePagerAdapter;
import cn.com.universalimageloader.core.ImageLoader;

public class BannerPagerAdapter extends InfinitePagerAdapter{

    private final LayoutInflater mInflater;
    private final Context mContext;

    public List<Banner> mList;

    private int height;
    public void setDataList(List<Banner> list) {
        this.mList = list;
        this.notifyDataSetChanged();
    }

    
    public BannerPagerAdapter(Context context,int height) {
        mContext=context;
        this.height = height;
        mInflater = LayoutInflater.from(mContext);
    }


    @Override
    public View getView(int position, View view, ViewGroup container) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = mInflater.inflate(R.layout.banner_item_viewpager, container, false);
    		LayoutParams viewParams = view.getLayoutParams();
    		viewParams.height = height;
    		view.setLayoutParams(viewParams);
            holder = new ViewHolder(view);
            view.setTag(holder);
            view.setOnClickListener(holder);
//            holder.image.setOnClickListener(holder);
        }
        Banner item = mList.get(position);
        holder.position = position;
//        holder.name.setText(item.getName());
//        holder.description.setText(item.getName()+"position:"+position);
        
        ImageLoader.getInstance().displayImage(
				item.getPic(),
				holder.image,
				MyApplication.getInstance()
						.getOptions(R.drawable.index_banner));
        return view;
    }

    

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }


    public class ViewHolder implements OnClickListener{
        public int position;
        TextView name;
        ImageView image;

        public ViewHolder(View view) {
//            name = (TextView) view.findViewById(R.id.item_name);
            image = (ImageView) view.findViewById(R.id.item_image);
        }

		@Override
		public void onClick(View v) {
			Banner banner = mList.get(position);
			Intent webIntent = new Intent(mContext,WebActivity.class);
			webIntent.putExtra("url", banner.getAction());
			webIntent.putExtra("name", banner.getName());
			mContext.startActivity(webIntent);
		}
    }

}
