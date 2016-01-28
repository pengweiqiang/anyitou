package cn.com.anyitou.adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.entity.Urls;
import cn.com.anyitou.ui.ImageViewActivity;
import cn.com.anyitou.utils.DeviceInfo;

public class CheDaiFangDaiDetailImagesAdapter extends BaseListAdapter {

	private Map<String, List<Urls>> images;// 图片列表
	private List<String[]> titles;// 分类标题列表
	private Context context;
	private LayoutInflater inflater;

	public CheDaiFangDaiDetailImagesAdapter(List<String[]> titles,
			Map<String, List<Urls>> images, Context context) {
		this.context = context;
		this.titles = titles;
		this.images = images;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		if (titles != null) {
			return titles.size();
		}
		return 0;
	}

	@Override
	public List<Urls> getItem(int position) {
		return images.get(titles.get(position)[0]);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		String title[] = titles.get(position);
		List<Urls> images = getItem(position);
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.related_pics_chedai_item,
					null);

			viewHolder.mTvCategoryTitle = (TextView) convertView
					.findViewById(R.id.title);
			viewHolder.mGallery = (Gallery) convertView
					.findViewById(R.id.gallery);
			viewHolder.mBtnLeft = convertView.findViewById(R.id.left_image);
			viewHolder.mBtnRight = convertView.findViewById(R.id.right_image);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.mTvCategoryTitle.setText(title[1]);
		if (images != null) {
			viewHolder.imageAdapter = new DetailImagesAdapter(images, context);
			viewHolder.mGallery.setAdapter(viewHolder.imageAdapter);
			viewHolder.position = position;
			setGalleryGravityLeft(viewHolder.mGallery);
			// viewHolder.mBtnLeft.setOnClickListener(viewHolder);
			// viewHolder.mBtnRight.setOnClickListener(viewHolder);
//			if (images.size() > 2) {
//				viewHolder.mGallery.setSelection(1);
//			}
			initGalleryListener(images.size(), position, viewHolder.mGallery,
					viewHolder.mBtnLeft, viewHolder.mBtnRight);
		}

		return convertView;
	}
	
	private void setGalleryGravityLeft(Gallery g){
		DisplayMetrics metrics = new DisplayMetrics();
		Activity activity = (Activity)context;
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        
        // set gallery to left side
        MarginLayoutParams mlp = (MarginLayoutParams) g.getLayoutParams();
        mlp.setMargins(-(metrics.widthPixels / 2 + (DeviceInfo.dp2px(context, 100)/2)), mlp.topMargin,
                    mlp.rightMargin, mlp.bottomMargin);
	}

	public class ViewHolder implements OnClickListener {
		private TextView mTvCategoryTitle;
		@SuppressWarnings("deprecation")
		private Gallery mGallery;
		DetailImagesAdapter imageAdapter;
		private View mBtnLeft, mBtnRight;
		int position;

		@Override
		public void onClick(View view) {
			if (view.getId() == R.id.left_image) {
				int curPosition = mGallery.getSelectedItemPosition();
				if (curPosition > 1) {
					mGallery.setSelection(curPosition - 1);
				}
			} else if (view.getId() == R.id.right_image) {
				int curPosition = mGallery.getSelectedItemPosition();
				if (curPosition < getItem(position).size() - 2) {
					mGallery.setSelection(curPosition + 1);
				}
			}
		}

	}

	private void initGalleryListener(final int imageSize,
			final int lastPosition, Gallery mGallery, final View mBtnLeft,
			final View mBtnRight) {
		// mGallery.setOnItemSelectedListener(new OnItemSelectedListener() {
		//
		// @Override
		// public void onItemSelected(AdapterView<?> parent, View view,
		// int position, long id) {
		// if(imageSize<=3){
		// ((ImageView)mBtnRight).setImageDrawable(context.getResources().getDrawable(R.drawable.right_btn_over_icon));
		// return;
		// }
		// if(position <= 1){
		// ((ImageView)mBtnLeft).setImageDrawable(context.getResources().getDrawable(R.drawable.left_btn_over_icon));
		// }else if(position>=getItem(position).size()-2){
		// ((ImageView)mBtnRight).setImageDrawable(context.getResources().getDrawable(R.drawable.right_btn_over_icon));
		// }else{
		// ((ImageView)mBtnLeft).setImageDrawable(context.getResources().getDrawable(R.drawable.left_btn_icon));
		// ((ImageView)mBtnRight).setImageDrawable(context.getResources().getDrawable(R.drawable.right_btn_icon));
		// }
		// }
		// //
		// @Override
		// public void onNothingSelected(AdapterView<?> parent) {
		//
		// }
		//
		// });
		mGallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(context, ImageViewActivity.class);
				intent.putExtra("url", getItem(lastPosition).get(position)
						.getUrl());
				intent.putParcelableArrayListExtra("urls",
						(ArrayList<? extends Parcelable>) getItem(lastPosition));
				intent.putExtra("position", position);
				intent.putExtra("name", "图片详情");
				context.startActivity(intent);
			}

		});
	}

}
