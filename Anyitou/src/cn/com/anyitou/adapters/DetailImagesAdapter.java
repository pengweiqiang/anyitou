package cn.com.anyitou.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import cn.com.anyitou.MyApplication;
import cn.com.anyitou.R;
import cn.com.anyitou.entity.Urls;
import cn.com.universalimageloader.core.ImageLoader;


public class DetailImagesAdapter extends BaseListAdapter{

	private List<Urls> images;
	private Context context;
	private LayoutInflater inflater;
	
	public DetailImagesAdapter(List<Urls> images,Context context) {
		this.context = context;
		this.images = images;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		if(images!=null){
			return images.size();
		}
		return 0;
	}

	@Override
	public Urls getItem(int position) {
		return images.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		String imageUrl = images.get(position).getUrl();
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.detail_image_item, null);
			
			viewHolder.mIvImage = (ImageView)convertView.findViewById(R.id.image);
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		if(!imageUrl.startsWith("http://www.anyitou.com")){
			imageUrl = "http://www.anyitou.com"+imageUrl;
		}
		 ImageLoader.getInstance().displayImage(
					imageUrl,
					viewHolder.mIvImage,
					MyApplication.getInstance()
							.getOptions(R.drawable.index_banner));
		
		
		
		return convertView;
	}
	
	static final class ViewHolder{
		private ImageView mIvImage;
	}

	
}
