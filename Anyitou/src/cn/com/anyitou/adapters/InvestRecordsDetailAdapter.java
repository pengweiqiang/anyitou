package cn.com.anyitou.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.entity.InvestRecords;
import cn.com.anyitou.utils.DateUtil;
import cn.com.anyitou.utils.StringUtils;

/**
 * 项目详情--》投资记录
 * @author pengweiqiang
 *
 */
public class InvestRecordsDetailAdapter extends BaseListAdapter{

	private List<InvestRecords> list;
	private Context context;
	private LayoutInflater inflater;
	private int type;
	
	public InvestRecordsDetailAdapter(List<InvestRecords> list,Context context) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}
	public InvestRecordsDetailAdapter(List<InvestRecords> list,Context context,int type) {
		this.context = context;
		this.list = list;
		this.type = type;
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
	public InvestRecords getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		InvestRecords items = getItem(position);
		if(convertView == null){
			viewHolder = new ViewHolder();
			if(type == 2){
				convertView = inflater.inflate(R.layout.invest_record_more_item, null);
			}else{
				convertView = inflater.inflate(R.layout.invest_record_detail_item, null);
			}
			
			viewHolder.mTvName = (TextView)convertView.findViewById(R.id.name);
			viewHolder.mTvDate = (TextView)convertView.findViewById(R.id.check_date);
			viewHolder.mTvStatus = (TextView)convertView.findViewById(R.id.check_status);
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		viewHolder.mTvName.setText(StringUtils.getSubUserName(items.getUsername()));
		viewHolder.mTvDate.setText(items.getItem_amount());
		viewHolder.mTvStatus.setText(DateUtil.getDateString(items.getInvest_time(), DateUtil.DEFAULT_PATTERN, DateUtil.DAY_PATTERN));
		return convertView;
	}
	
	
	
	public class ViewHolder{
		private TextView mTvName;
		private TextView mTvDate;
		private TextView mTvStatus;
		
	}
	
	
}
