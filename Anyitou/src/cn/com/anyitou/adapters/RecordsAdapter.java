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
			
			viewHolder.mTvMonth = (TextView)convertView.findViewById(R.id.month);
			viewHolder.mTvCategory = (TextView)convertView.findViewById(R.id.category);
			viewHolder.mTvCashNum = (TextView)convertView.findViewById(R.id.cash_num);
			viewHolder.mTvTime = (TextView)convertView.findViewById(R.id.time);
			viewHolder.mTvStatus = (TextView)convertView.findViewById(R.id.status);
			viewHolder.mViewDateTile = convertView.findViewById(R.id.date_title);
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		if(StringUtils.isEmpty(record.getMonthFirstDate())){
			viewHolder.mViewDateTile.setVisibility(View.GONE);
		}else{
			viewHolder.mViewDateTile.setVisibility(View.VISIBLE);
			viewHolder.mTvMonth.setText(record.getMonthFirstDate());
		}
		viewHolder.mTvCategory.setText(record.getCategory_data().getLabel());
		StringBuffer sbCashNum = new StringBuffer();
		if(!StringUtils.isEmpty(record.getCash_status()) && record.getCash_status().equals("1")){
			sbCashNum.append("+");
			viewHolder.mTvCashNum.setTextColor(context.getResources().getColor(R.color.app_bg_color));
		}else if(!StringUtils.isEmpty(record.getCash_status()) && record.getCash_status().equals("2")){
			sbCashNum.append("-");
			viewHolder.mTvCashNum.setTextColor(context.getResources().getColor(R.color.trade_reduce2));
		}else{
			viewHolder.mTvCashNum.setTextColor(context.getResources().getColor(R.color.app_bg_color));
		}
		viewHolder.mTvCashNum.setText(sbCashNum.toString()+record.getCash_num());
		viewHolder.mTvTime.setText(record.getDeal_time());
		String status = record.getStatus();
		if("1".equals(status)){
			viewHolder.mTvStatus.setText("交易成功");
		}else {
			viewHolder.mTvStatus.setText(record.getStatus());
		}
		
		
		
		return convertView;
	}
	
	static final class ViewHolder{
		private TextView mTvMonth;
		private TextView mTvCategory;
		private TextView mTvCashNum;
		private TextView mTvTime;
		private TextView mTvStatus;
		private View mViewDateTile;
	}

	
}
