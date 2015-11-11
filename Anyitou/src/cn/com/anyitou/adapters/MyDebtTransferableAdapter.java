package cn.com.anyitou.adapters;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.entity.MyDebtTransferable;
import cn.com.anyitou.ui.PublishDebtTransferActivity;


public class MyDebtTransferableAdapter extends BaseListAdapter{

	private List<MyDebtTransferable> transfers;
	private Context context;
	private LayoutInflater inflater;
	
	public MyDebtTransferableAdapter(List<MyDebtTransferable> transfers,Context context) {
		this.context = context;
		this.transfers = transfers;
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
		MyDebtTransferable transfer = transfers.get(position);
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.debt_transfer_item, null);
			
			viewHolder.mTvDate = (TextView)convertView.findViewById(R.id.time);
			viewHolder.mTvDateTitle = (TextView)convertView.findViewById(R.id.time_title);
			viewHolder.mTvInvestName = (TextView)convertView.findViewById(R.id.invest_name);
			viewHolder.mTvRate = (TextView)convertView.findViewById(R.id.rate);
			viewHolder.mTvRateTitle = (TextView)convertView.findViewById(R.id.rate_title);
			viewHolder.mTvTransferCount = (TextView)convertView.findViewById(R.id.transfer_count);
			viewHolder.mBtnDebt = convertView.findViewById(R.id.btn_debt);
			viewHolder.mBtnDebt.setVisibility(View.VISIBLE);
			viewHolder.mBtnDebt.setOnClickListener(viewHolder);
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		viewHolder.position = position;
		viewHolder.mTvInvestName.setText(transfer.getItem_title());
//		String date = DateUtil.getDateString(transfer.getRepayment_time(), DateUtil.DEFAULT_PATTERN, DateUtil.DAY_PATTERN);
		viewHolder.mTvDate.setText(transfer.getRepayment_time());
		viewHolder.mTvRate.setText(transfer.getRate_of_interest()+"%");
		viewHolder.mTvTransferCount.setText(transfer.getTransferring_amount());
		
		
		return convertView;
	}
	
	public final class ViewHolder implements OnClickListener{
		int position ;
		private TextView mTvInvestName;//项目名称
		private TextView mTvRate;//年化利率
		private TextView mTvRateTitle;
		private TextView mTvDate;//结束日期
		private TextView mTvDateTitle;
		private TextView mTvTransferCount;//转让份额
		private View mBtnDebt;//发起债权转让
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(context,PublishDebtTransferActivity.class);
			intent.putExtra("debt", transfers.get(position));
			context.startActivity(intent);
		}
		
		
	}

	
}
