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


public class IntegralGoodsAdapter extends BaseListAdapter{

	private List<IntegralGoods> goods;
	private Context context;
	private LayoutInflater inflater;
	
	public IntegralGoodsAdapter(List<IntegralGoods> goods,Context context) {
		this.context = context;
		this.goods = goods;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		if(goods!=null){
			return goods.size()/2+(goods.size()%2==0?0:1);
		}
		return 0;
	}

	@Override
	public List<IntegralGoods> getItem(int position) {
		List<IntegralGoods> items = new ArrayList<IntegralGoods>();
		if(goods.size()>position*2){
			items.add(goods.get(position*2));
		}
		if(goods.size()>position*2+1){
			items.add(goods.get(position*2+1));
		}
		return items;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		List<IntegralGoods> items = getItem(position);
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.goods_item, null);
			viewHolder.mViewBg = convertView.findViewById(R.id.bg_type);
			viewHolder.mTvCouponType = (TextView)convertView.findViewById(R.id.coupon_type);
			viewHolder.mTvPrice = (TextView)convertView.findViewById(R.id.price);
			viewHolder.mTvUseConditions = (TextView)convertView.findViewById(R.id.use_conditions);
			viewHolder.mTvUseTime = (TextView)convertView.findViewById(R.id.use_time);
			viewHolder.mTvWorth = (TextView)convertView.findViewById(R.id.worth);
			
			viewHolder.mViewBg.setOnClickListener(viewHolder);
			
			viewHolder.mViewBg2 = convertView.findViewById(R.id.bg_type2);
			viewHolder.mTvCouponType2 = (TextView)convertView.findViewById(R.id.coupon_type2);
			viewHolder.mTvPrice2 = (TextView)convertView.findViewById(R.id.price2);
			viewHolder.mTvUseConditions2 = (TextView)convertView.findViewById(R.id.use_conditions2);
			viewHolder.mTvUseTime2 = (TextView)convertView.findViewById(R.id.use_time2);
			viewHolder.mViewItem2 = convertView.findViewById(R.id.coupon_item2);
			viewHolder.mTvWorth2 = (TextView)convertView.findViewById(R.id.worth2);
			
			viewHolder.mViewBg2.setOnClickListener(viewHolder);
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		viewHolder.position = position;
		
		IntegralGoods goods = items.get(0);
		
		CouponData couponData = goods.getCoupon_data();
		String category = couponData.getCategory();//现金券cash，利息券interest，提现券draw   
		
		viewHolder.mViewBg.setBackgroundDrawable(context.getResources().getDrawable(getDrawable(category)));
		viewHolder.mTvCouponType.setText(goods.getTitle());
		StringBuffer price = new StringBuffer(couponData.getName());
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
		
		
		viewHolder.mTvUseConditions.setText(couponData.getUse_rules());
		viewHolder.mTvUseTime.setText("截至："+DateUtil.getDateString(couponData.getEnd_time(), DateUtil.DEFAULT_PATTERN,DateUtil.DAY_PATTERN));
		viewHolder.mTvWorth.setText(goods.getWorth());
		
		if(items.size()>1){
			viewHolder.mViewItem2.setVisibility(View.VISIBLE);
			IntegralGoods goods2 = items.get(1);
			CouponData couponData2 = goods2.getCoupon_data();
			String category2 = couponData2.getCategory();
			StringBuffer price2 = new StringBuffer(couponData2.getName());
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
			viewHolder.mTvCouponType2.setText(goods2.getTitle());
			viewHolder.mTvUseConditions2.setText(couponData2.getUse_rules());
			viewHolder.mTvUseTime2.setText("截至："+DateUtil.getDateString(couponData2.getEnd_time(),DateUtil.DEFAULT_PATTERN,DateUtil.DAY_PATTERN));
			viewHolder.mTvWorth2.setText(goods2.getWorth());
		}else{
			viewHolder.mViewItem2.setVisibility(View.INVISIBLE);
		}
		
		
		
		return convertView;
	}
	
	private int getDrawable(String category){
		int drawable = R.drawable.user_xianjinquan_bg;
		if("cash".equals(category)){
			drawable = R.drawable.user_xianjinquan_bg;
		}else if("interest".equals(category)){
			drawable = R.drawable.user_lilvquan_bg;
		}else if("draw".equals(category)){
			drawable = R.drawable.user_tixianquan_bg;
		}
		return drawable;
	}
	
	
	public class ViewHolder implements OnClickListener{
		int position;
		private View mViewBg;
		private TextView mTvCouponType;
		private TextView mTvPrice;
		private TextView mTvUseConditions;
		private TextView mTvUseTime;
		private TextView mTvWorth;
		
		private View mViewBg2;
		private TextView mTvCouponType2;
		private TextView mTvPrice2;
		private TextView mTvUseConditions2;
		private TextView mTvUseTime2;
		private View mViewItem2;
		private TextView mTvWorth2;
		
		@Override
		public void onClick(View v) {
			IntegralGoods goodItem = null;
			if(v.getId() == R.id.bg_type){
				goodItem = goods.get(position*2);
			}else if(v.getId() == R.id.bg_type2){
				goodItem = goods.get(position*2+1);
			}
			getMyAnbi(goodItem);
			
		}
	}
	LoadingDialog loading;
	private void getMyAnbi(final IntegralGoods goodItem){
		loading = new LoadingDialog(context);
		loading.show();
		ApiUserUtils.getMyAnbiInfo(context, new RequestCallback() {
			
			@Override
			public void execute(ParseModel parseModel) {
				loading.cancel();
				if(ApiConstants.RESULT_SUCCESS_BOOLEAN.equals(parseModel.getStatus())){
					JsonObject data = parseModel.getData().getAsJsonObject();
					String usableIntegral = data.get("usable_integral").getAsString();
					showExchangeDialog(goodItem.getGoods_desc(),goodItem.getWorth(),usableIntegral,goodItem.getId());
				}
			}
		});
	}
	
	private void showExchangeDialog(String goodDesc,String worth,String usableIntegral,final String goodsId){
		Double worthDouble = Double.valueOf(worth);
		Double useableDouble = Double.valueOf(usableIntegral);
		String message = null;
		String title = "";
		InfoDialog.Builder builder = new InfoDialog.Builder(context,R.layout.goods_exchange_dialog);
		
		View view = builder.getViewLayout();
		TextView mTvWorth = (TextView) view.findViewById(R.id.worth);
		TextView mTvUseAnbi = (TextView) view.findViewById(R.id.use_anbi);
		
		builder.setButton1("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		builder.setButton2("立即兑换",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						dialog.cancel();
						exchangeGoods(goodsId);
					}
				});
		if(worthDouble<useableDouble){
			message = goodDesc;
			title = "请核对订单信息";
		}else{
			title = ":( 您的安币金额不足";
			
			((Button)view.findViewById(R.id.btn2)).setEnabled(false);
			((Button)view.findViewById(R.id.btn2)).setClickable(false);
//			builder.setButton1Background(R.drawable.btn_left_concer_dialog_grey_selector);
			((Button)view.findViewById(R.id.btn1)).setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_left_concer_dialog_enable));
			((Button)view.findViewById(R.id.btn1)).setTextColor(context.getResources().getColor(R.color.dialog_enable_text_color));
		}
		builder.setMessage(message);
		builder.setTitle(title);
		InfoDialog infoDialog = builder.create();
		
		
		mTvWorth.setText(worth);
		mTvUseAnbi.setText(usableIntegral);
		infoDialog.show();
	}
	/**
	 * 兑换物品
	 */
	private void exchangeGoods(String goodsId){
		loading = new LoadingDialog(context, "兑换中...");
		loading.show();
		ApiInvestUtils.exchangeGoods(context, goodsId, new RequestCallback() {
			
			@Override
			public void execute(ParseModel parseModel) {
				loading.cancel();
				if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
					Intent success = new Intent(context,SuccessActivity.class);
					success.putExtra("message", "√ 恭喜您,兑换成功");
					context.startActivity(success);
				}else{
					ToastUtils.showToast(context, StringUtils.isEmpty(parseModel.getMsg())?"兑换失败":parseModel.getMsg());
				}
			}
		});
	}

	
}
