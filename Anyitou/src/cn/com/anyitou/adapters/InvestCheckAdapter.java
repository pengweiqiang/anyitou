package cn.com.anyitou.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.entity.BorrowerVerifyLogs;
import cn.com.anyitou.utils.DateUtil;


public class InvestCheckAdapter extends BaseListAdapter{

	private List<BorrowerVerifyLogs> list;
	private Context context;
	private LayoutInflater inflater;
	
	public InvestCheckAdapter(List<BorrowerVerifyLogs> list,Context context) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		if(list!=null){
			return list.size();
		}
		return 0;
	}

	@Override
	public BorrowerVerifyLogs getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		BorrowerVerifyLogs items = getItem(position);
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.invest_check_item, null);
			
			viewHolder.mTvName = (TextView)convertView.findViewById(R.id.name);
			viewHolder.mTvDate = (TextView)convertView.findViewById(R.id.check_date);
			viewHolder.mTvStatus = (TextView)convertView.findViewById(R.id.check_status);
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		viewHolder.mTvName.setText(items.getType_name());
//		viewHolder.mTvDate.setText(items.getVerify_time());
		viewHolder.mTvDate.setText(DateUtil.getDateString(items.getVerify_time(), DateUtil.DEFAULT_PATTERN, DateUtil.DAY_PATTERN));
		String status  = items.getStatus();
		if("1".equals(status)){
			viewHolder.mTvStatus.setText("审核通过");
		}else{
			viewHolder.mTvStatus.setText("未通过审核");
		}
		
		return convertView;
	}
	
	
	
	public class ViewHolder{
		private TextView mTvName;
		private TextView mTvDate;
		private TextView mTvStatus;
		
	}
	
	
}
