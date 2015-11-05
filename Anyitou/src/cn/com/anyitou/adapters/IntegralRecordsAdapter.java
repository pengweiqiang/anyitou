package cn.com.anyitou.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.entity.IntegralRecords;


public class IntegralRecordsAdapter extends BaseListAdapter{

	private List<IntegralRecords> records;
	private Context context;
	private LayoutInflater inflater;
	
	public IntegralRecordsAdapter(List<IntegralRecords> records,Context context) {
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
		IntegralRecords record = records.get(position);
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.integral_record_item, null);
			
			viewHolder.mTvTitle = (TextView)convertView.findViewById(R.id.title);
			viewHolder.mTvAddTime = (TextView)convertView.findViewById(R.id.addtime);
			viewHolder.mTvContent = (TextView)convertView.findViewById(R.id.content);
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		viewHolder.mTvAddTime.setText(record.getAdd_time());
		String operation = record.getOperation();//1为增加，2为减去
		StringBuffer titleSb = new StringBuffer();
		
		if("1".equals(operation)){
			titleSb.append("+"+record.getSet_integral());
			viewHolder.mTvContent.setTextColor(context.getResources().getColor(R.color.app_bg_color));
		}else if("2".equals(operation)){
			titleSb.append("-"+record.getSet_integral());
			viewHolder.mTvContent.setTextColor(context.getResources().getColor(R.color.subtract_color));
		}
		viewHolder.mTvTitle.setText(record.getTitle());
		
		viewHolder.mTvContent.setText(titleSb.toString());
		
		
		return convertView;
	}
	
	static final class ViewHolder{
		private TextView mTvTitle;
		private TextView mTvAddTime;
		private TextView mTvContent;
	}

	
}
