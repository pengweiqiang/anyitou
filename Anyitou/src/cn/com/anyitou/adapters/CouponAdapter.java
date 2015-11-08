package cn.com.anyitou.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.entity.Coupon;
import cn.com.anyitou.utils.DateUtil;
import cn.com.anyitou.utils.TextViewUtils;


public class CouponAdapter extends BaseListAdapter{

	private List<Coupon> coupons;
	private Context context;
	private LayoutInflater inflater;
	private int type;
	
	public CouponAdapter(List<Coupon> coupons,Context context,int type) {
		this.context = context;
		this.coupons = coupons;
		this.type = type;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		if(coupons!=null){
			return coupons.size()/2+(coupons.size()%2==0?0:1);
		}
		return 0;
	}

	@Override
	public List<Coupon> getItem(int position) {
		List<Coupon> couponItems = new ArrayList<Coupon>();
		if(coupons.size()>position*2){
			couponItems.add(coupons.get(position*2));
		}
		if(coupons.size()>position*2+1){
			couponItems.add(coupons.get(position*2+1));
		}
		return couponItems;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		List<Coupon> couponItems = getItem(position);
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.coupon_item, null);
//			viewHolder.mViewDashLine = convertView.findViewById(R.id.top_dash_line);
			viewHolder.mViewBg = convertView.findViewById(R.id.bg_type);
			viewHolder.mTvCouponType = (TextView)convertView.findViewById(R.id.coupon_type);
			viewHolder.mTvPrice = (TextView)convertView.findViewById(R.id.price);
			viewHolder.mTvUseConditions = (TextView)convertView.findViewById(R.id.use_conditions);
			viewHolder.mTvUseTime = (TextView)convertView.findViewById(R.id.use_time);
			viewHolder.mViewLine = convertView.findViewById(R.id.line1);
			
			viewHolder.mViewBg2 = convertView.findViewById(R.id.bg_type2);
			viewHolder.mTvCouponType2 = (TextView)convertView.findViewById(R.id.coupon_type2);
			viewHolder.mTvPrice2 = (TextView)convertView.findViewById(R.id.price2);
			viewHolder.mTvUseConditions2 = (TextView)convertView.findViewById(R.id.use_conditions2);
			viewHolder.mTvUseTime2 = (TextView)convertView.findViewById(R.id.use_time2);
			viewHolder.mViewItem2 = convertView.findViewById(R.id.coupon_item2);
			viewHolder.mViewLine2 = convertView.findViewById(R.id.line2);
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		
		Coupon coupon = couponItems.get(0);
		
		String category = coupon.getCategory();//现金券cash，利息券interest，提现券draw   
		
		viewHolder.mViewBg.setBackgroundDrawable(context.getResources().getDrawable(getDrawable(category)));
		viewHolder.mTvCouponType.setText(coupon.getCategory_name());
		StringBuffer price = new StringBuffer(coupon.getName());
//		if(category.equals("interest")){
//			price.append("%");
//		}else{
//			price.append("元");
//		}
		String lastChar = price.substring(price.length()-1);
		try{
			Integer.valueOf(lastChar);
			viewHolder.mTvPrice.setText(price);
		}catch(Exception e){
			SpannableString spannString = TextViewUtils.getSpannableStringSize(price.toString(), price.length()-1, price.length(), 14);
			viewHolder.mTvPrice.setText(spannString);
		}
		
		
		viewHolder.mTvUseConditions.setText(coupon.getUse_rules());
		viewHolder.mTvUseTime.setText("截至："+DateUtil.getDateString(coupon.getEnd_time(), DateUtil.DEFAULT_PATTERN,DateUtil.DAY_PATTERN));
		
		if(getCount() == position+1){
			viewHolder.mViewLine.setVisibility(View.GONE);
			viewHolder.mViewLine2.setVisibility(View.GONE);
		}else{
			viewHolder.mViewLine.setVisibility(View.VISIBLE);
			viewHolder.mViewLine2.setVisibility(View.VISIBLE);
		}
		
		if(couponItems.size()>1){
			viewHolder.mViewItem2.setVisibility(View.VISIBLE);
			Coupon coupon2 = couponItems.get(1);
			String category2 = coupon2.getCategory();
			StringBuffer price2 = new StringBuffer(coupon2.getName());
//			if(category2.equals("interest")){
//				price2.append("%");
//			}else{
//				price2.append("元");
//			}
			String lastChar2 = price2.substring(price2.length()-1);
			try{
				Integer.valueOf(lastChar2);
				viewHolder.mTvPrice2.setText(price2);
			}catch(Exception e){
				SpannableString spannString2 = TextViewUtils.getSpannableStringSize(price2.toString(), price2.length()-1, price2.length(), 14);
				viewHolder.mTvPrice2.setText(spannString2);
			}
			
			
			
			
			viewHolder.mViewBg2.setBackgroundDrawable(context.getResources().getDrawable(getDrawable(category2)));
			viewHolder.mTvCouponType2.setText(coupon2.getCategory_name());
			viewHolder.mTvUseConditions2.setText(coupon2.getUse_rules());
			viewHolder.mTvUseTime2.setText("截至："+DateUtil.getDateString(coupon2.getEnd_time(),DateUtil.DEFAULT_PATTERN,DateUtil.DAY_PATTERN));
		}else{
			viewHolder.mViewItem2.setVisibility(View.INVISIBLE);
		}
		
		
		
		return convertView;
	}
	
	private int getDrawable(String category){
		int drawable = R.drawable.user_xianjinquan_bg;
		switch (type) {
		case 1:
			if("cash".equals(category)){
				drawable = R.drawable.user_xianjinquan_bg;
			}else if("interest".equals(category)){
				drawable = R.drawable.user_lilvquan_bg;
			}else if("draw".equals(category)){
				drawable = R.drawable.user_tixianquan_bg;
			}
			break;
		case 2:
			if("cash".equals(category)){
				drawable = R.drawable.user_xianjinquan_used_bg;
			}else if("interest".equals(category)){
				drawable = R.drawable.user_lilvquan_used_bg;
			}else if("draw".equals(category)){
				drawable = R.drawable.user_tixianquan_used_bg;
			}
			break;
		case 3:
			drawable = R.drawable.user_quan_guoqi_bg;
			break;

		default:
			break;
		}
		return drawable;
	}
	
	
	static final class ViewHolder{
		private View mViewBg;
		private TextView mTvCouponType;
		private TextView mTvPrice;
		private TextView mTvUseConditions;
		private TextView mTvUseTime;
		private View mViewLine;
		
		private View mViewBg2;
		private TextView mTvCouponType2;
		private TextView mTvPrice2;
		private TextView mTvUseConditions2;
		private TextView mTvUseTime2;
		private View mViewItem2;
		private View mViewLine2;
	}

	
}
