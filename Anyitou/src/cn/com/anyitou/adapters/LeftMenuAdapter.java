package cn.com.anyitou.adapters;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import cn.com.anyitou.R;

import cn.com.anyitou.entity.Menu;

public class LeftMenuAdapter extends BaseAdapter {

	List<Menu> dataList = new ArrayList<Menu>();
	private LayoutInflater mInflater;
	private Context context;
	private int selectedPosition = 0;

	public LeftMenuAdapter(List<Menu> dataList, Context context) {
		this.dataList = dataList;
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
		;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dataList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return dataList.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = mInflater.inflate(R.layout.left_menu_item, null);
		Menu menu = dataList.get(position);
		TextView tvTitle = (TextView) convertView.findViewById(R.id.title);
		ImageView ivLeftImage = (ImageView) convertView
				.findViewById(R.id.image);

		tvTitle.setText(menu.getTitle());
		if(position == selectedPosition){
			switch (selectedPosition) {
			case 0:
				ivLeftImage.setImageDrawable(context.getResources().getDrawable(R.drawable.left_home_selected));
				break;
			case 1:
				ivLeftImage.setImageDrawable(context.getResources().getDrawable(R.drawable.left_money_selected));
				break;
			case 2:
				ivLeftImage.setImageDrawable(context.getResources().getDrawable(R.drawable.left_setting_selected));
				break;
			}
		}else{
			ivLeftImage.setImageDrawable(context.getResources().getDrawable(
					menu.getDrawable()));
		}
		
		
		// convertView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.left_cell_hight));
		return convertView;
	}
	
	public void setSelectedPosition(int position){
		this.selectedPosition = position;
		notifyDataSetChanged();
	}
	
	

}
