package cn.com.anyitou.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.api.ApiInvestUtils;
import cn.com.anyitou.api.ApiUserUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.entity.IntegralGoods;
import cn.com.anyitou.entity.IntegralGoods.CouponData;
import cn.com.anyitou.entity.InvestCoupons;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.ui.SuccessActivity;
import cn.com.anyitou.utils.DateUtil;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.TextViewUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.InfoDialog;
import cn.com.anyitou.views.LoadingDialog;
import cn.com.gson.JsonObject;


public class InvestCouponsAdapter extends BaseListAdapter{

	private List<InvestCoupons> coupons;
	private Context context;
	private LayoutInflater inflater;
	
	public InvestCouponsAdapter(List<InvestCoupons> coupons,Context context) {
		this.context = context;
		this.coupons = coupons;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		if(coupons!=null){
			return coupons.size();
		}
		return 0;
	}

	@Override
	public InvestCoupons getItem(int position) {
		return coupons.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		InvestCoupons items = getItem(position);
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.invest_coupon_item, null);
			
			viewHolder.mTvName = (TextView)convertView.findViewById(R.id.name);
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		viewHolder.mTvName.setText(items.getName());
		
		return convertView;
	}
	
	
	
	public class ViewHolder{
		private TextView mTvName;
		
		
	}
	
	
}
