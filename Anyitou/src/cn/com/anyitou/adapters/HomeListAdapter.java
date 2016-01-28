package cn.com.anyitou.adapters;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.entity.Investment;
import cn.com.anyitou.utils.DateUtil;
import cn.com.anyitou.utils.DateUtils;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.views.PercentageRing;
import cn.com.anyitou.views.RoundProgressBar;


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
//		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.home_item, null);
			
//			viewHolder.mViewDashLine = convertView.findViewById(R.id.top_dash_line);
			viewHolder.mTvDate = (TextView)convertView.findViewById(R.id.deadline_time);
			viewHolder.mTvInvestName = (TextView)convertView.findViewById(R.id.invest_name);
			viewHolder.mTvRate = (TextView)convertView.findViewById(R.id.rate);
			viewHolder.mTvStatus = (TextView)convertView.findViewById(R.id.invest_status);
			viewHolder.mTvInvestmentCycle = (TextView)convertView.findViewById(R.id.investment_cycle);
			viewHolder.mTvMoney = (TextView)convertView.findViewById(R.id.total);
			viewHolder.mPercentageRing = (PercentageRing)convertView.findViewById(R.id.progress);
//			viewHolder.mPercentageRing.setTag(position);
			convertView.setTag(viewHolder);
			
//		}else{
//			viewHolder = (ViewHolder)convertView.getTag();
//		}
		
//		viewHolder.mPercentageRing.setTag(position);
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
//		Date timeDate;
//		try {
//			timeDate = DateUtil.getDate(invest.getRaise_begin_time(), DateUtil.DEFAULT_PATTERN);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		long mss = timeDate.getTime();
//		String deadTime = DateUtils.formatDuring(invest.getRaise_begin_time());
//		if(StringUtils.isEmpty(deadTime)){
//			viewHolder.mTvDate.setVisibility(View.GONE);
//		}else{
//			viewHolder.mTvDate.setVisibility(View.VISIBLE);
//			viewHolder.mTvDate.setText(deadTime);
//			deadTime = deadTime+"后截止";
//			
//		}
		
		
//		String status = invest.getInvest_status();
		viewHolder.mTvStatus.setText(invest.getInvest_status_label());
		viewHolder.mTvInvestmentCycle.setText(invest.getBorrow_days()+"天");
		String money = StringUtils.getMoneyFormateWanNoDecimaPoint(invest.getFinancing_amount());
		viewHolder.mTvMoney.setText(money+"万");
		String scale = invest.getScale();//0.1%
//		if(viewHolder.mPercentageRing.getTag()!=null && position == (Integer)viewHolder.mPercentageRing.getTag()){
//			viewHolder.mPercentageRing.setTargetPercent(StringUtils.getProgress(scale));
//		}
		
		viewHolder.mPercentageRing.setTargetPercent(StringUtils.getProgress(scale));
			viewHolder.mTvRate.setText(invest.getRate_of_interest()+"%");
		return convertView;
	}
	
	
	public String getStatusName(String status){
		String statusName = "";
		if("1".equals(status)){
			statusName = "未开放";
		}else if("1".equals(status)){
			statusName = "募集中...";
		}else if("2".equals(status)){
			statusName = "募集完成";
		}else if("3".equals(status)){
			statusName = "还款中...";
		}else if("4".equals(status)){
			statusName = "还款完成";
		}else if("5".equals(status)){
			statusName = "逾期";
		}
		return statusName;
	}
	
	static final class ViewHolder{
//		private View mViewDashLine;
		private PercentageRing mPercentageRing;
		private TextView mTvInvestName;//项目名称
		private TextView mTvRate;//年化利率
		private TextView mTvDate;//结束日期
		private TextView mTvStatus;//状态
		private TextView mTvInvestmentCycle;//投资周期
		private TextView mTvMoney;//投资金额
	}

	
}
