package cn.com.anyitou.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.entity.DebtAssignment;
import cn.com.anyitou.utils.AnyitouUtils;
import cn.com.anyitou.utils.DateUtils;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.views.PercentageRing;


public class DebtAssignmentAdapter extends BaseListAdapter{

	private List<DebtAssignment> debtAssignments;
	private Context context;
	private LayoutInflater inflater;
	
	public DebtAssignmentAdapter(List<DebtAssignment> debtAssignments,Context context) {
		this.context = context;
		this.debtAssignments = debtAssignments;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		if(debtAssignments!=null){
			return debtAssignments.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return debtAssignments.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		DebtAssignment debtAssignment = debtAssignments.get(position);
//		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.debt_item, null);
			
//			viewHolder.mViewDashLine = convertView.findViewById(R.id.top_dash_line);
			viewHolder.mTvInvestName = (TextView)convertView.findViewById(R.id.invest_name);
			viewHolder.mTvRate = (TextView)convertView.findViewById(R.id.rate);
			viewHolder.mTvStatus = (TextView)convertView.findViewById(R.id.invest_status);
			viewHolder.mTvInvestmentCycle = (TextView)convertView.findViewById(R.id.investment_cycle);
			viewHolder.mTvMoney = (TextView)convertView.findViewById(R.id.total);
			viewHolder.mPercentageRing = (PercentageRing)convertView.findViewById(R.id.progress);
			
			convertView.setTag(viewHolder);
//		}else{
//			viewHolder = (ViewHolder)convertView.getTag();
//		}
		
		viewHolder.mTvInvestName.setText(debtAssignment.getNumber());
		viewHolder.mTvRate.setText(debtAssignment.getBuyer_apr()+"%");
		String status = debtAssignment.getStatus();
		viewHolder.mTvStatus.setText(AnyitouUtils.getDebtStatusName(status));
		viewHolder.mTvInvestmentCycle.setText(debtAssignment.getSell_days()+"天");
		String money = StringUtils.getMoneyFormateWan(debtAssignment.getAmount());
		viewHolder.mTvMoney.setText(money+"万");
		String scale = debtAssignment.getBuyProgress();//0.1%
		viewHolder.mPercentageRing.setTargetPercent(StringUtils.getProgress(scale));
		
		
		return convertView;
	}
	
	
	static final class ViewHolder{
//		private View mViewDashLine;
		private PercentageRing mPercentageRing;
		private TextView mTvInvestName;//项目名称
		private TextView mTvRate;//年化利率
		private TextView mTvStatus;//状态
		private TextView mTvInvestmentCycle;//投资周期
		private TextView mTvMoney;//转让金额
	}

	
}
