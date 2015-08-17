package cn.com.anyitou.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.com.anyitou.R;

import cn.com.anyitou.entity.LableValue;


public class RepaymentPlanListAdapter extends BaseListAdapter{

	private List<LableValue> lableValues;
	private Context context;
	private LayoutInflater inflater;
	
	public RepaymentPlanListAdapter(List<LableValue> lableValues,Context context) {
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
			convertView = inflater.inflate(R.layout.repayment_schedule_item, null);
			
			viewHolder.mViewDashLine = convertView.findViewById(R.id.top_dash_line);
			viewHolder.mTvDate = (TextView)convertView.findViewById(R.id.date);
			viewHolder.mTvBackStyle = (TextView)convertView.findViewById(R.id.back_style);
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
		
		viewHolder.mTvDate.setText(invest.getDate());
		viewHolder.mTvBackStyle.setText(invest.getType());
		viewHolder.mTvMoney.setText(invest.getMoney());
		
		
		return convertView;
	}
	
	static final class ViewHolder{
		private View mViewDashLine;
		private TextView mTvDate;//日期
		private TextView mTvBackStyle;//还款类型
		private TextView mTvMoney;//还款金额
	}

	
}
