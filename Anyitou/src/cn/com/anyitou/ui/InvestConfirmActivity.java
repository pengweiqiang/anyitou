package cn.com.anyitou.ui;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.anyitou.MyApplication;
import cn.com.anyitou.R;
import cn.com.anyitou.adapters.InvestCouponsAdapter;
import cn.com.anyitou.api.ApiInvestUtils;
import cn.com.anyitou.api.ApiOrderUtils;
import cn.com.anyitou.api.ApiUserUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.InvestCoupons;
import cn.com.anyitou.entity.Investment;
import cn.com.anyitou.entity.MyCapital;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.JsonUtils;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.TextViewUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.InfoDialog;
import cn.com.anyitou.views.LoadingDialog;
import cn.com.gson.JsonElement;
import cn.com.gson.JsonNull;
import cn.com.gson.JsonObject;

/**
 * 投资确认信息
 * 
 * @author will
 * 
 */
public class InvestConfirmActivity extends BaseActivity {

	private ActionBar mActionBar;
	private LoadingDialog loadingDialog;
	private TextView mTvInvestName;
	private TextView mTvYearRate;
	private TextView mTvInvestDay;
	private TextView mTvRestMoney, mTvMyMoney, mTvPreProfit, mTvPreAnbi;
	private View mViewCoupon;
	private TextView mTvCouponName;
	private EditText mEtBuyMoney;
	private View mViewConfirm;
	private View mBtnAllInvest;
	private View mBtnProfitCal;

	private ListView mCouponListView;// 优惠券

	private Investment investment;// 投资项目
	private int investmentMoney =1000;//起投金额
	String id = "";
	String useMoney = "";// 可用金额
	String couponId = "";//优惠券id

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.invest_confirm);
		super.onCreate(savedInstanceState);

		investment = (Investment) this.getIntent().getSerializableExtra(
				"investment");
		id = investment.getId();
		investmentMoney = StringUtils.getMoney2Int(Double.valueOf(investment.getInvestment()));
		if (MyApplication.getInstance().getMyCapital() != null) {
			useMoney = MyApplication.getInstance().getMyCapital()
					.getUse_money();
		} else {
			// 加载可用余额
			getMyUseMoney();
		}
		initData();
	}

	private void getMyUseMoney() {
		ApiUserUtils.getMyAccount(mContext, new RequestCallback() {

			@Override
			public void execute(ParseModel parseModel) {
				if (ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())) {
					MyCapital myCapital = JsonUtils.fromJson(parseModel
							.getData().toString(), MyCapital.class);
					if (myCapital != null) {
						MyApplication.getInstance().setMyCapital(myCapital);
						useMoney = myCapital.getUse_money();
					}
				}
			}
		});
	}

	@Override
	public void initView() {
		mActionBar = (ActionBar) findViewById(R.id.actionBar);
		mActionBar.setTitle("确认投资");
		mActionBar.setLeftActionButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AppManager.getAppManager().finishActivity();
			}
		});
		mActionBar.setRightActionButton("充值", new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, RechargeActivity.class);
				intent.putExtra("money", useMoney);
				startActivity(intent);
			}
		});
		mTvInvestName = (TextView) findViewById(R.id.invest_name);
		mTvYearRate = (TextView) findViewById(R.id.year_rate);
		mTvInvestDay = (TextView) findViewById(R.id.invest_day);
		mTvRestMoney = (TextView) findViewById(R.id.invest_money);
		mTvMyMoney = (TextView) findViewById(R.id.useable_money);
		mTvPreProfit = (TextView) findViewById(R.id.pre_profit);
		mTvPreAnbi = (TextView) findViewById(R.id.pre_anbi);
		mBtnAllInvest = findViewById(R.id.all_invest);
		mBtnProfitCal = findViewById(R.id.invest_calu);

		mViewConfirm = findViewById(R.id.bottom_invest);
		mEtBuyMoney = (EditText) findViewById(R.id.buy_money);
		mViewCoupon = findViewById(R.id.coupon_right);

		mTvCouponName = (TextView)findViewById(R.id.coupon_name);
		mCouponListView = (ListView) findViewById(R.id.coupon_listview);

	}

	private void initData() {
		if (investment != null) {
			mEtBuyMoney.setHint("请按"+investmentMoney+"的倍数起投");
			mTvInvestName.setText(investment.getItem_title());
			mTvYearRate.setText(investment.getRate_of_interest() + "%");
			mTvInvestDay.setText(investment.getBorrow_days() + "天");
			mTvRestMoney.setText(StringUtils.getMoneyFormat(investment
					.getRemain_amount()));
			mTvMyMoney.setText(StringUtils.getMoneyFormat(useMoney));

		}
	}

	/**
	 * 算出预期收益 //预期收益=投资金额*年利率*（投资期的天数/365）
	 * 
	 * @param money
	 */
	private void caluFutureMoney(String moneyStr) {
		if (StringUtils.isEmpty(moneyStr)) {
			mTvPreProfit.setText("0");
			mTvPreAnbi.setText("0");
			return;
		}
		try {
			double money = Double.valueOf(moneyStr);
			double futureMoney = money
					* (Double.parseDouble(investment.getRate_of_interest()) / 100)
					* (Double.valueOf(investment.getBorrow_days()) / 365);
			DecimalFormat df = new DecimalFormat("######0.00");
			mTvPreProfit.setText(df.format(futureMoney));
		} catch (Exception e) {

		}
	}

	/**
	 * 收益计算器（数据从服务器获取）
	 * 
	 * @param moneyStr
	 * @return
	 */
	private void caluProfitMoneyForService(String moneyStr, final int type) {
		if (StringUtils.isEmpty(moneyStr)) {
			return;
		}
		double money = Double.valueOf(moneyStr);
		double yushu = money % investmentMoney;
		if (money < investmentMoney || yushu != 0d) {
			return;
		}
		ApiInvestUtils.calculatorInvest(mContext, id, moneyStr, couponId,
				new RequestCallback() {

					@Override
					public void execute(ParseModel parseModel) {
						if (ApiConstants.RESULT_SUCCESS.equals(parseModel
								.getCode())) {
							JsonObject data = parseModel.getData()
									.getAsJsonObject();
							if (data != null) {

								if (data.get("interestData") != JsonNull.INSTANCE) {
									String proFit = data
											.getAsJsonObject("interestData")
											.getAsJsonObject("total")
											.get("interest").getAsString()
											+ "元";// 预期收益
									if (type == 1) {// 计算器来源
										if (mTvRate != null) {
											SpannableString ss = TextViewUtils
													.getSpannableStringSizeAndColor(
															proFit,
															proFit.length() - 1,
															proFit.length(),
															14,
															getResources()
																	.getColor(
																			R.color.dialog_text_color));
											mTvRate.setText(ss);
										}
									} else if (type == 2) {// 界面来源
										String point = data.get("points")
												.getAsString();
										mTvPreProfit.setText(proFit);
										mTvPreAnbi.setText(point);
									}
								}

							}
						} else {
							ToastUtils.showToast(mContext, parseModel.getMsg());
						}
					}
				});
	}
	
	private TextView mTvRate;

	@Override
	public void initListener() {
		mBtnProfitCal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!"true".equals(investment.getIsstarted())){
					ToastUtils.showToast(mContext, "项目不能投资");
					return;
				}
				InfoDialog.Builder builder = new InfoDialog.Builder(mContext,
						R.layout.profit_calu_info_dialog);
				builder.setTitle("收益计算器");
				final InfoDialog infoDialog = builder.create();
				mTvRate = (TextView) infoDialog.findViewById(R.id.pre_profit);
				TextView mTvInvestDay = (TextView) infoDialog
						.findViewById(R.id.invest_day);
				mTvInvestDay.setText(investment.getBorrow_days() + "天");
				infoDialog.findViewById(R.id.close).setOnClickListener(
						new OnClickListener() {

							@Override
							public void onClick(View v) {
								infoDialog.cancel();
							}
						});
				final EditText mEtInvestMoney = (EditText) infoDialog
						.findViewById(R.id.invest_money);
				mEtInvestMoney.setHint(investmentMoney+"元起投,"+investmentMoney+"元递增");
				mEtInvestMoney.addTextChangedListener(new TextWatcher() {

					@Override
					public void onTextChanged(CharSequence s, int start,
							int before, int count) {

					}

					@Override
					public void beforeTextChanged(CharSequence s, int start,
							int count, int after) {

					}

					@Override
					public void afterTextChanged(Editable s) {
						String moneyStr = mEtInvestMoney.getText().toString()
								.trim();
						caluProfitMoneyForService(moneyStr, 1);
						// String proFit = caluProfitMoney(moneyStr)+"元";
						// SpannableString ss =
						// TextViewUtils.getSpannableStringSizeAndColor(proFit,
						// proFit.length()-1, proFit.length(),
						// 14,getResources().getColor(R.color.dialog_text_color));
						// mTvRate.setText(ss);
					}
				});

				infoDialog.show();
			}
		});
		mBtnAllInvest.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mEtBuyMoney.setText(investment.getRemain_amount());
			}
		});
		// 获取可用的优惠券
		mViewCoupon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getCouponForProject();
			}
		});
		mEtBuyMoney.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				String moneyStr = mEtBuyMoney.getText().toString().trim();
				checkInvestMoney(moneyStr);
				showEmptyCouponView(true);
			}
		});
		mViewConfirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String moneyStr = mEtBuyMoney.getText().toString().trim();
				if(!checkInvestMoney(moneyStr)){
					return;
				}
				loadingDialog = new LoadingDialog(mContext);
				loadingDialog.show();
				String id = investment.getId();
				ApiOrderUtils.investing(mContext, id, moneyStr, couponId,
						new RequestCallback() {

							@Override
							public void execute(ParseModel parseModel) {
								loadingDialog.cancel();
								if (ApiConstants.RESULT_SUCCESS
										.equals(parseModel.getCode())) {
									String url = parseModel.getUrl();
									String ordId = parseModel.getData()
											.getAsJsonObject().get("id")
											.getAsString();
									String tradeNo = parseModel.getData()
											.getAsJsonObject().get("trade_no")
											.getAsString();
									Intent intent = new Intent(mContext,
											WebActivity.class);
									intent.putExtra("url", url);
									intent.putExtra("type", 3);
									intent.putExtra("name", "投资");
									intent.putExtra("ordId", ordId);
									startActivity(intent);
									AppManager.getAppManager().finishActivity();
								} else {
									ToastUtils.showToast(mContext,
											parseModel.getMsg());
								}
							}
						});

			}
		});
	}

	private void showNotenoughMoney(String money) {
		InfoDialog.Builder builder = new InfoDialog.Builder(mContext,
				R.layout.invest_info_dialog);
		builder.setTitle("账户余额不足");
		String message = "差值为：" + money + "元";
		builder.setMessage(message);
		builder.setButton1("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		builder.setButton2("立即充值", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				Intent intent = new Intent(mContext, RechargeActivity.class);
				intent.putExtra("money", useMoney);
				startActivity(intent);
			}
		});
		TextView mTvMessgae = (TextView) builder.getViewLayout().findViewById(
				R.id.message);
		InfoDialog infoDialog = builder.create();
		mTvMessgae.setText(TextViewUtils.getSpannableStringColor(message, 4,
				message.lastIndexOf("元"),
				getResources().getColor(R.color.app_bg_color)));
		infoDialog.show();
	}

	/**
	 * 获取项目可用的优惠券
	 */
	private void getCouponForProject() {
		String amount = mEtBuyMoney.getText().toString().trim();
		if (StringUtils.isEmpty(amount)) {
			mEtBuyMoney.requestFocus();
			ToastUtils.showToast(mContext, "请输入投资金额");
			return;
		}
		double money = Double.valueOf(amount);
		double yushu = money % investmentMoney;
		if (money < investmentMoney || yushu != 0d) {
			mEtBuyMoney.requestFocus();
			ToastUtils.showToast(mContext, "请按"+investmentMoney+"元的倍数投资");
			return;
		}
		loadingDialog = new LoadingDialog(mContext);
		loadingDialog.show();

		ApiInvestUtils.getCouponForProject(mContext, investment.getId(),
				amount, new RequestCallback() {

					@Override
					public void execute(ParseModel parseModel) {
						if (ApiConstants.RESULT_SUCCESS.equals(parseModel
								.getCode())) {
							json2Coupon(parseModel.getData());
						} else {
							showEmptyCouponView(false);
							ToastUtils.showToast(mContext, parseModel.getMsg());
						}
						loadingDialog.cancel();
					}
				});

	}

	/**
	 * 解析优惠券json字符串
	 * TODO wait for easy from implements
	 */
	private void json2Coupon(JsonElement data) {
		investCoupons.clear();
		if (data != null) {
			
			//1.检查有用的优惠券
			JsonObject couponVerifyRes = data.getAsJsonObject().getAsJsonObject("couponVerifyRes");
			if(couponVerifyRes ==null){
				showEmptyCouponView(false);
				ToastUtils.showToast(mContext, "没有可用的优惠券");
				return;
			}
			List<String> couponIds = getCouponVerifyRes(couponVerifyRes);
			if(couponIds==null || couponIds.isEmpty()){
				showEmptyCouponView(false);
				ToastUtils.showToast(mContext, "没有可用的优惠券");
				return;
			}
			//2.取出有用优惠券的详细信息
			JsonObject classCouponList = data.getAsJsonObject().getAsJsonObject("classCouponList");
			if(classCouponList ==null){
				showEmptyCouponView(false);
				ToastUtils.showToast(mContext, "没有可用的优惠券");
				return;
			}
			findViewById(R.id.coupon_rl).setEnabled(true);
			Set<Entry<String, JsonElement>> sets = classCouponList.entrySet();
			Iterator<Entry<String, JsonElement>> keys = sets.iterator();
			while (keys.hasNext()) {
				Entry<String, JsonElement> entry = keys.next();
				if(entry.getKey().equals("cash")){//现金券
					cashCoupon(entry.getValue(),couponIds);
				}else if("interest".equals(entry.getKey())){//利息券
					cashCoupon(entry.getValue(),couponIds);
				}else if("rebate".equals(entry.getKey())){//返利券
					cashCoupon(entry.getValue(),couponIds);
				}else if("draw".equals(entry.getKey())){//
					cashCoupon(entry.getValue(),couponIds);
				}
			}
		}else{
			showEmptyCouponView(false);
		}
	}
	private void showEmptyCouponView(boolean isEnabled){
		findViewById(R.id.coupon_rl).setEnabled(isEnabled);
		if(investCoupons!=null && !investCoupons.isEmpty()){
			investCoupons.clear();
			
			investCouponAdapter = new InvestCouponsAdapter(investCoupons, mContext);
			mCouponListView.setAdapter(investCouponAdapter);
			investCouponAdapter.notifyDataSetChanged();
		}
		
	}
	/**
	 * 获取此次投资可用的优惠券
	 */
	private List<String> getCouponVerifyRes(JsonObject jsonObject){
		List<String> ids = new ArrayList<String>();
		try{
			JsonObject detail = jsonObject.get("detail").getAsJsonObject();
			if(detail!=null){
				Set<Entry<String, JsonElement>> sets = detail.entrySet();
				Iterator<Entry<String, JsonElement>> keys = sets.iterator();
				while (keys.hasNext()) {
					Entry<String, JsonElement> entry = keys.next();
					JsonElement detailItem = entry.getValue();
					String status = detailItem.getAsJsonObject().get("status").getAsString();
					if("true".equals(status)){
						//取到有用优惠券的id
						ids.add(entry.getKey());
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return ids;
	}
	
	private List<InvestCoupons> investCoupons = new ArrayList<InvestCoupons>();
	InvestCouponsAdapter investCouponAdapter;
	private void cashCoupon(JsonElement cash,List<String> ids){
		if (cash != null) {
				Set<Entry<String, JsonElement>> sets = cash.getAsJsonObject().entrySet();
				Iterator<Entry<String, JsonElement>> keys = sets.iterator();
				while (keys.hasNext()) {
					Entry<String, JsonElement> entry = keys.next();
					InvestCoupons coupon = new InvestCoupons();
					JsonObject values = entry.getValue().getAsJsonObject();
					if(ids.contains(entry.getKey())){//可用优惠券
						coupon.setCoupon_id(values.get("id").getAsString());
						coupon.setCategory(values.get("category").getAsString());
						if("cash".equals(coupon.getCategory())){
							coupon.setName(values.get("name").getAsString()+"现金优惠券");
						}else if("interest".equals(coupon.getCategory())){
							coupon.setName(values.get("name").getAsString()+"利息优惠券");
						}else if("rebate".equals(coupon.getCategory())){
							coupon.setName(values.get("name").getAsString()+"返利优惠券");
						}else if("draw".equals(coupon.getCategory())){
							coupon.setName(values.get("name").getAsString()+"提现优惠券");
						}
						coupon.setPrice(values.get("price").getAsString());
						investCoupons.add(coupon);
					}
				}
			}
		if(investCoupons!=null){
			mCouponListView.setVisibility(View.VISIBLE);
			investCouponAdapter = new InvestCouponsAdapter(investCoupons, mContext);
			mCouponListView.setAdapter(investCouponAdapter);
			mCouponListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					InvestCoupons coupon = investCoupons.get(position);
					couponId = coupon.getCoupon_id();
					mTvCouponName.setText(coupon.getName());
					mCouponListView.setVisibility(View.GONE);
				}
				
			});
		}
	}
	
	private boolean checkInvestMoney(String moneyStr){
		if(StringUtils.isEmpty(moneyStr)){
			return false;
		}
		if (Double.valueOf(moneyStr) < investmentMoney) {
			ToastUtils.showToastSingle(mContext, "购买金额必须大于"+investmentMoney+"元");
			mEtBuyMoney.requestFocus();
			return false;
		}
		if(Double.valueOf(moneyStr) > Double.valueOf(investment
			.getRemain_amount())){
			ToastUtils.showToastSingle(mContext, "超过了可投金额"+investment.getRemain_amount());
			mEtBuyMoney.requestFocus();
			return false;
		}
		if (Double.valueOf(moneyStr) % investmentMoney != 0) {
			ToastUtils.showToastSingle(mContext, "请输入"+investmentMoney+"的整数倍");
			mEtBuyMoney.requestFocus();
			return false ;
		}
		try {
			if (Double.valueOf(moneyStr) > Double.valueOf(useMoney)) {
//				 ToastUtils.showToastSingle(mContext, "您的余额不足");
				showNotenoughMoney(Double.valueOf(moneyStr)
						- Double.valueOf(useMoney) + "");
				// mEtBuyMoney.requestFocus();
				return false;
			}
		} catch (Exception e) {

		}
		caluProfitMoneyForService(moneyStr, 2);
		return true;
	}

}
