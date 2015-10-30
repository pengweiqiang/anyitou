package cn.com.anyitou.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.com.anyitou.R;

import cn.com.anyitou.entity.Records;
import cn.com.anyitou.utils.StringUtils;


public class RecordsAdapter extends BaseListAdapter{

	private List<Records> records;
	private Context context;
	private LayoutInflater inflater;
	
	public RecordsAdapter(List<Records> records,Context context) {
		this.context = context;
		this.records = records;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		if(records!=null){
			return records.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return records.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		Records record = records.get(position);
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.trading_record_item, null);
			
			viewHolder.mTvTitle = (TextView)convertView.findViewById(R.id.title);
			viewHolder.mTvAddTime = (TextView)convertView.findViewById(R.id.addtime);
			viewHolder.mTvContent = (TextView)convertView.findViewById(R.id.content);
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		viewHolder.mTvAddTime.setText(record.getDeal_time());
		if(!StringUtils.isEmpty(record.getCash_status()) && record.getCash_status().equals("1")){
			viewHolder.mTvContent.setTextColor(context.getResources().getColor(R.color.trade_add));
		}else if(!StringUtils.isEmpty(record.getCash_status()) && record.getCash_status().equals("1")){
			viewHolder.mTvContent.setTextColor(context.getResources().getColor(R.color.trade_reduce));
		}else{
			viewHolder.mTvContent.setTextColor(context.getResources().getColor(R.color.app_bg_color));
		}
		viewHolder.mTvTitle.setText(record.getCategory_data().getLabel());
		viewHolder.mTvContent.setText(record.getCash_num());
		
		
		return convertView;
	}
	
	static final class ViewHolder{
		private TextView mTvTitle;
		private TextView mTvAddTime;
		private TextView mTvContent;
	}

	
}
