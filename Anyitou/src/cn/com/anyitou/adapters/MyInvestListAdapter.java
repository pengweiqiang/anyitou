package cn.com.anyitou.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.com.anyitou.R;

import cn.com.anyitou.entity.MyInvestment;


public class MyInvestListAdapter extends BaseListAdapter{

	private List<MyInvestment> myInvestments;
	private Context context;
	private LayoutInflater inflater;
	
	public MyInvestListAdapter(List<MyInvestment> myInvestments,Context context) {
		this.context = context;
		this.myInvestments = myInvestments;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		if(myInvestments!=null){
			return myInvestments.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return myInvestments.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		MyInvestment myInvest = myInvestments.get(position);
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.activity_my_investment_item, null);
			
			viewHolder.mTvTitle = (TextView)convertView.findViewById(R.id.label_title);
			viewHolder.mTvTransAmtMoney = (TextView)convertView.findViewById(R.id.trans_amt_money);
			viewHolder.mTvAlreadyMoney = (TextView)convertView.findViewById(R.id.already_money);
			viewHolder.mTvWorseMoney = (TextView)convertView.findViewById(R.id.worse_money);
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		viewHolder.mTvTitle.setText(myInvest.getLoantypename()+" "+myInvest.getPnum());
		viewHolder.mTvTransAmtMoney.setText(myInvest.getTrans_amt());
		viewHolder.mTvAlreadyMoney.setText(myInvest.getAlreadyget());
		viewHolder.mTvWorseMoney.setText(myInvest.getWorseget());
		
		
		return convertView;
	}
	
	static final class ViewHolder{
		private TextView mTvTitle;
		private TextView mTvTransAmtMoney;//初始债权
		private TextView mTvAlreadyMoney;//已回款
		private TextView mTvWorseMoney;//未回款
	}

	
}
