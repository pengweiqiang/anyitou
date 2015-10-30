package cn.com.anyitou.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.com.anyitou.R;

import cn.com.anyitou.entity.LableValue;
import cn.com.anyitou.utils.DateUtil;


public class InvestListAdapter extends BaseListAdapter{

	private List<LableValue> lableValues;
	private Context context;
	private LayoutInflater inflater;
	
	public InvestListAdapter(List<LableValue> lableValues,Context context) {
		this.context = context;
		this.lableValues = lableValues;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		if(lableValues!=null){
			return lableValues.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return lableValues.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		LableValue invest = lableValues.get(position);
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.project_details_item, null);
			
			viewHolder.mViewDashLine = convertView.findViewById(R.id.top_dash_line);
			viewHolder.mTvDate = (TextView)convertView.findViewById(R.id.date);
			viewHolder.mTvUsername = (TextView)convertView.findViewById(R.id.username);
			viewHolder.mTvMoney = (TextView)convertView.findViewById(R.id.money);
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		if(position == 0){
			viewHolder.mViewDashLine.setVisibility(View.VISIBLE);
		}else{
			viewHolder.mViewDashLine.setVisibility(View.GONE);
		}
//		try{
//			String date = DateUtil.getDateStringByMill(Long.valueOf(invest.getTime())*1000, DateUtil.DEFAULT_PATTERN);
//			viewHolder.mTvDate.setText(date);
//		}catch(Exception e){
//			viewHolder.mTvDate.setText(invest.getTime());
//		}
//		
//		viewHolder.mTvUsername.setText(invest.getUsername());
//		viewHolder.mTvMoney.setText(invest.getMoney());
		
		
		return convertView;
	}
	
	static final class ViewHolder{
		private View mViewDashLine;
		private TextView mTvDate;//日期
		private TextView mTvUsername;//用户
		private TextView mTvMoney;//投资金额
	}

	
}
