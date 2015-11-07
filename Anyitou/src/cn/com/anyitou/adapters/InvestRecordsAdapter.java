package cn.com.anyitou.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.com.anyitou.R;

import cn.com.anyitou.entity.InvestRecords;
import cn.com.anyitou.entity.Records;
import cn.com.anyitou.utils.DateUtil;
import cn.com.anyitou.utils.StringUtils;


public class InvestRecordsAdapter extends BaseListAdapter{

	private List<InvestRecords> records;
	private Context context;
	private LayoutInflater inflater;
	
	public InvestRecordsAdapter(List<InvestRecords> records,Context context) {
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
		InvestRecords record = records.get(position);
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.invest_record_item, null);
			
			viewHolder.mTvTitle = (TextView)convertView.findViewById(R.id.invest_name);
			viewHolder.mTvEndTime = (TextView)convertView.findViewById(R.id.end_time);
			viewHolder.mTvInvestTime = (TextView)convertView.findViewById(R.id.invest_time);
			viewHolder.mTvMoney = (TextView)convertView.findViewById(R.id.money);
			
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		viewHolder.mTvTitle.setText(record.getItem_title());
//		String investTime = DateUtil.getDateString(record.getInvest_time(), DateUtil.DEFAULT_PATTERN, DateUtil.DAY_PATTERN);
//		String endTime = DateUtil.getDateString(record.getRepayment_time(), DateUtil.DEFAULT_PATTERN, DateUtil.DAY_PATTERN);
		viewHolder.mTvEndTime.setText(record.getRepayment_time());
		viewHolder.mTvInvestTime.setText(record.getInvest_time());
		viewHolder.mTvMoney.setText("￥"+record.getItem_amount());
		
		return convertView;
	}
	
	static final class ViewHolder{
		private TextView mTvTitle;
		private TextView mTvEndTime;//截至时间
		private TextView mTvInvestTime;//投资时间
		private TextView mTvMoney;//
	}

	
}
