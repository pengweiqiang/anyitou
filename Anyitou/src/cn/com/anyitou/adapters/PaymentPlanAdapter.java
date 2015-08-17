package cn.com.anyitou.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.com.anyitou.R;

import cn.com.anyitou.entity.PaymentPlan;


public class PaymentPlanAdapter extends BaseListAdapter{

	private List<PaymentPlan> paymentPlans;
	private Context context;
	private LayoutInflater inflater;
	
	public PaymentPlanAdapter(List<PaymentPlan> paymentPlans,Context context) {
		this.context = context;
		this.paymentPlans = paymentPlans;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		if(paymentPlans!=null){
			return paymentPlans.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return paymentPlans.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		PaymentPlan paymentPlan = paymentPlans.get(position);
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.payment_plan_item, null);
			
			viewHolder.mTvDate = (TextView)convertView.findViewById(R.id.date);
			viewHolder.mTvHaveBeanBack = (TextView)convertView.findViewById(R.id.have_bean_back);
			viewHolder.mTvUnHaveBeanBack = (TextView)convertView.findViewById(R.id.un_back);
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		viewHolder.mTvDate.setText(paymentPlan.getDate());
		viewHolder.mTvHaveBeanBack.setText(paymentPlan.getAlreadypay());
		viewHolder.mTvUnHaveBeanBack.setText(paymentPlan.getWorsepay());
		if(position == paymentPlans.size()-1){
			convertView.findViewById(R.id.bottom).setVisibility(View.VISIBLE);
		}
		
		
		return convertView;
	}
	
	static final class ViewHolder{
		private TextView mTvDate;//日期
		private TextView mTvHaveBeanBack;//已回款
		private TextView mTvUnHaveBeanBack;//未回款
	}

	
}
