package cn.com.anyitou.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.entity.Investment;


public class HomeListAdapter extends BaseListAdapter{

	private List<Investment> investments;
	private Context context;
	private LayoutInflater inflater;
	
	public HomeListAdapter(List<Investment> investments,Context context) {
		this.context = context;
		this.investments = investments;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		if(investments!=null){
			return investments.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return investments.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		Investment invest = investments.get(position);
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.home_item, null);
			
//			viewHolder.mViewDashLine = convertView.findViewById(R.id.top_dash_line);
			viewHolder.mTvDate = (TextView)convertView.findViewById(R.id.deadline_time);
			viewHolder.mTvInvestName = (TextView)convertView.findViewById(R.id.invest_name);
			viewHolder.mTvRate = (TextView)convertView.findViewById(R.id.rate);
			viewHolder.mTvStatus = (TextView)convertView.findViewById(R.id.invest_status);
			viewHolder.mTvInvestmentCycle = (TextView)convertView.findViewById(R.id.investment_cycle);
			viewHolder.mTvMoney = (TextView)convertView.findViewById(R.id.total);
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
//		if(position == 0){
//			viewHolder.mViewDashLine.setVisibility(View.VISIBLE);
//		}else{
//			viewHolder.mViewDashLine.setVisibility(View.GONE);
//		}
//		try{
//			String date = DateUtil.getDateStringByMill(Long.valueOf(invest.getTime())*1000, DateUtil.DEFAULT_PATTERN);
//			viewHolder.mTvDate.setText(date);
//		}catch(Exception e){
//			viewHolder.mTvDate.setText(invest.getTime());
//		}
		
		viewHolder.mTvInvestName.setText(invest.getItem_title());
		viewHolder.mTvDate.setText(invest.getInvestment());
		viewHolder.mTvRate.setText(invest.getRate_of_interest()+"%");
		viewHolder.mTvStatus.setText(invest.getInvest_status());
		viewHolder.mTvInvestmentCycle.setText(invest.getBorrow_days()+"天");
		viewHolder.mTvMoney.setText(invest.getInvestment()+"万");
		
		
		return convertView;
	}
	
	static final class ViewHolder{
//		private View mViewDashLine;
		private TextView mTvInvestName;//项目名称
		private TextView mTvRate;//年化利率
		private TextView mTvDate;//结束日期
		private TextView mTvStatus;//状态
		private TextView mTvInvestmentCycle;//投资周期
		private TextView mTvMoney;//投资金额
	}

	
}
