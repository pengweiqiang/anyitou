package cn.com.anyitou.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.entity.DebtTransfer;
import cn.com.anyitou.utils.DateUtil;


public class DebtTransferAdapter extends BaseListAdapter{

	private List<DebtTransfer> transfers;
	private Context context;
	private LayoutInflater inflater;
	private int status;//0:全部  1:转让中  2:已转让
	
	public DebtTransferAdapter(List<DebtTransfer> transfers,Context context,int status) {
		this.context = context;
		this.transfers = transfers;
		this.status = status;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		if(transfers!=null){
			return transfers.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return transfers.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		DebtTransfer transfer = transfers.get(position);
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.debt_transfer_item, null);
			
			viewHolder.mTvDate = (TextView)convertView.findViewById(R.id.time);
			viewHolder.mTvDateTitle = (TextView)convertView.findViewById(R.id.time_title);
			viewHolder.mTvInvestName = (TextView)convertView.findViewById(R.id.invest_name);
			viewHolder.mTvRate = (TextView)convertView.findViewById(R.id.rate);
			viewHolder.mTvRateTitle = (TextView)convertView.findViewById(R.id.rate_title);
			viewHolder.mTvTransferCount = (TextView)convertView.findViewById(R.id.transfer_count);
			
			if(status==1 || status == 2){
				viewHolder.mTvDateTitle.setText("转让时间");
				viewHolder.mTvRateTitle.setText("转让后收益");
				viewHolder.mLeftView = convertView.findViewById(R.id.icon_left);
				viewHolder.mLeftView.setVisibility(View.GONE);
			}
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		viewHolder.mTvInvestName.setText(transfer.getNumber());
		String date = DateUtil.getDateString(transfer.getSell_end_time(), DateUtil.DEFAULT_PATTERN, DateUtil.DAY_PATTERN);
		viewHolder.mTvDate.setText(date);
		viewHolder.mTvRate.setText(transfer.getBuyer_apr()+"%");
		viewHolder.mTvTransferCount.setText(transfer.getRemainAmount());
		
		
		return convertView;
	}
	
	static final class ViewHolder{
		int position ;
		private TextView mTvInvestName;//项目名称
		private TextView mTvRate;//年化利率
		private TextView mTvRateTitle;
		private TextView mTvDate;//结束日期
		private TextView mTvDateTitle;
		private TextView mTvTransferCount;//转让份额
		private View mLeftView;
	}

	
}
