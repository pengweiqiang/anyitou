package cn.com.anyitou.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cn.com.anyitou.R;

import cn.com.anyitou.entity.Investment;
import cn.com.anyitou.ui.InVestmentDetailActivity;
import cn.com.anyitou.utils.StringUtils;

public class HomePagerAdapter extends PagerAdapter{

	private List<Investment> investLists = new ArrayList<Investment>();
	private Context context;
	private LayoutInflater minflater;
	
	
	public HomePagerAdapter(List<Investment> investLists, Context context) {
		this.investLists = investLists;
		this.context = context;
		minflater = LayoutInflater.from(context);
	}
	
	public void setData(List<Investment> investLists){
		this.investLists = investLists;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		if(investLists!=null && !investLists.isEmpty()){
			return investLists.size();
		}
		return 0;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
//		super.destroyItem(container, position, object);
//		((ViewPager)container).removeViewAt(position);
		((ViewPager) container).removeView((View) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View view = minflater.inflate(R.layout.home_item, null);
		ViewHolder viewHolder = new ViewHolder();
		viewHolder.mTvYearRate = (TextView)view.findViewById(R.id.yearrate);
		viewHolder.mTvMoney = (TextView) view.findViewById(R.id.money);
		viewHolder.mTvCalendar = (TextView)view.findViewById(R.id.calendar);
		viewHolder.mTvTitle = (TextView)view.findViewById(R.id.title_top);
		viewHolder.mTvContent = (TextView)view.findViewById(R.id.content);
		viewHolder.mTvCompany = (TextView)view.findViewById(R.id.company);
		viewHolder.mBtnInvestMoney = (Button)view.findViewById(R.id.invest_money);
		viewHolder.mHomeCircleCircle = (ImageView)view.findViewById(R.id.home_circle_circle);
		
		final Investment investment = investLists.get(position);
		viewHolder.mTvTitle.setText(investment.getLoantype()+investment.getPnum());
		StringBuffer sbRate = new StringBuffer(investment.getYearrate()+"%");
		if(!StringUtils.isEmpty(investment.getAddrate()) && !investment.getAddrate().equals("0")){
			sbRate.append("+"+investment.getAddrate()+"%");
		}
		viewHolder.mTvYearRate.setText(sbRate);
		viewHolder.mTvMoney.setText(investment.getMoney());
		viewHolder.mTvCalendar.setText(investment.getXmqx()+"天");
		viewHolder.mTvCompany.setText(investment.getCompany()+"/"+investment.getLoantype());
		viewHolder.mTvContent.setText(StringUtils.ToDBC(investment.getContent()));
		if(position == 0 && !isFinishAnim){
			startRotate(viewHolder.mHomeCircleCircle);
		}
		final String status = investment.getNstatus();
		/**
		 * 2：投标中 3：还款中 4：还款完成 5：投标完成
		 */
		String statusDesc = "投标中";
		if("2".equals(status)){
			statusDesc = "投标中";
			
		}else if("3".equals(status)){
			statusDesc = "还款中";
		}else if("4".equals(status)){
			statusDesc = "还款完成";
		}else if("5".equals(status)){
			statusDesc = "投标完成";
		}
		final String statusDescStr = statusDesc;
		viewHolder.mBtnInvestMoney.setText(statusDesc);
		viewHolder.mBtnInvestMoney.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				
				Intent intent = new Intent(context,InVestmentDetailActivity.class);
				intent.putExtra("id", investment.getId());
				intent.putExtra("day", investment.getXmqx());
				try{
					double rate = Double.valueOf(investment.getYearrate())+Double.valueOf(investment.getAddrate());
					intent.putExtra("rate", rate+"");
				}catch(Exception e){
					intent.putExtra("rate", investment.getYearrate());
				}
				
				intent.putExtra("statusDesc", statusDescStr);
				intent.putExtra("status", status);
				context.startActivity(intent);
			}
		});
		
		
		((ViewPager) container).addView(view, 0);
		return view;
	}
	Animation operatingAnim;
	private boolean isFinishAnim;
	private void startRotate(ImageView imageView){
		if(operatingAnim == null){
			operatingAnim = AnimationUtils.loadAnimation(context, R.anim.home_rotate);  
			LinearInterpolator lin = new LinearInterpolator();  
			operatingAnim.setInterpolator(lin);  
		}
		imageView.startAnimation(operatingAnim);  
		isFinishAnim = true;
	}
	static class ViewHolder {
		private TextView mTvYearRate;//年化率
		private TextView mTvMoney;//金额
		private TextView mTvCalendar;//日期
		private TextView mTvTitle;
		private TextView mTvContent;//内容
		private TextView mTvCompany;//公司
		private Button mBtnInvestMoney;
		private ImageView mHomeCircleCircle;
	}
	

}
