package cn.com.anyitou.ui;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import cn.com.anyitou.R;

import cn.com.gson.reflect.TypeToken;
import cn.com.anyitou.adapters.RecordsAdapter;
import cn.com.anyitou.api.ApiUserUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.entity.Records;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.JsonUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.LoadingDialog;
import cn.com.anyitou.views.XListView;
import cn.com.anyitou.views.XListView.IXListViewListener;

/**
 * 交易记录
 * 
 * @author will
 * 
 */
public class TradingRecordActivity extends BaseActivity implements
		IXListViewListener {
	private ActionBar mActionBar;
	PopupWindow popupWindow;
	private View mViewAllSelcted, mViewExSelected, mViewIncomeSelected;
	private View mViewAll, mViewEx, mViewIncome;
	private View popupWindowOut;
	int type = 0;
	RotateAnimation rotateAnim1, rotateAnim2;

	View view;
	WindowManager windowManager;
	private LoadingDialog loadingDialog;

	private List<Records> recordLists = new ArrayList<Records>();
	RecordsAdapter recordsAdapter;
	int page = 1;

	private XListView mListView;
	private View mViewEmpty;
	private TextView mViewEmptyTip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_trading_record);
		super.onCreate(savedInstanceState);

		recordsAdapter = new RecordsAdapter(recordLists, mContext);

		mListView.setAdapter(recordsAdapter);
		loadingDialog = new LoadingDialog(mContext);
		loadingDialog.show();
		initData();
	}

	@Override
	public void initView() {
		mActionBar = (ActionBar) findViewById(R.id.actionBar);
		onConfigureActionBar(mActionBar);
		mListView = (XListView) findViewById(R.id.listView);
		mViewEmpty = findViewById(R.id.empty_listview);
		mViewEmptyTip = (TextView) findViewById(R.id.xlistview_empty_tip);

		mListView.setPullLoadEnable(true);
		mListView.setXListViewListener(this);
	}

	protected void onConfigureActionBar(final ActionBar actionBar) {
		actionBar.setTitle("交易记录");
		actionBar.setLeftActionButton(R.drawable.btn_back,
				new OnClickListener() {

					@Override
					public void onClick(View view) {
						AppManager.getAppManager().finishActivity();
					}
				});
		actionBar.setActionBarTitleClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (rotateAnim1 == null) {
					rotateAnim1 = new RotateAnimation(0f, 180f,
							Animation.RELATIVE_TO_SELF, 0.5f,
							Animation.RELATIVE_TO_SELF, 0.5f);
					rotateAnim1.setInterpolator(new LinearInterpolator());
					// rotateAnim1.setRepeatCount(1);
					rotateAnim1.setFillAfter(true);
					rotateAnim1.setDuration(300);
				}
				actionBar.startRotateAnimImageView(rotateAnim1);
				showWindow(v);
			}
		});

	}

	private void initData() {
		mViewEmpty.setVisibility(View.GONE);
		ApiUserUtils.getTrade(mContext, String.valueOf(page),
				new RequestCallback() {

					@Override
					public void execute(ParseModel parseModel) {
						loadingDialog.cancelDialog(loadingDialog);
						if (ApiConstants.RESULT_SUCCESS.equals(parseModel
								.getCode())) {
							List<Records> records = JsonUtils.fromJson(
									parseModel.getData().toString(),
									new TypeToken<List<Records>>() {
									});
							if (page == 1) {
								recordLists.clear();

								if (records == null || records.isEmpty()) {
									// mListView.setVisibility(View.GONE);
									mViewEmpty.setVisibility(View.VISIBLE);
								}
							}

							if (records != null && !records.isEmpty()) {
								recordLists.addAll(records);
								mViewEmpty.setVisibility(View.GONE);
							}
							logined(parseModel.getToken(), null);
							if (type != 0) {
								screenAdapter(type);
							} else {
								mListView.onLoadFinish(page, records.size(),
										"加载完毕");
							}
							recordsAdapter.notifyDataSetChanged();
						} else {
							ToastUtils.showToast(mContext, parseModel.getMsg());
							mListView.onLoadFinish(page, 0, "");
						}

					}
				});

	}

	@Override
	public void initListener() {

	}

	OnClickListener popupwindowListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			startAnimationCancelPopupwindow();
			if (v.getId() == R.id.all_rl) {
				type = 0;
				mViewAllSelcted.setVisibility(View.VISIBLE);
				mViewExSelected.setVisibility(View.GONE);
				mViewIncomeSelected.setVisibility(View.GONE);
			} else if (v.getId() == R.id.expenditure) {
				type = 1;
				mViewAllSelcted.setVisibility(View.GONE);
				mViewExSelected.setVisibility(View.VISIBLE);
				mViewIncomeSelected.setVisibility(View.GONE);
			} else if (v.getId() == R.id.income) {
				type = 2;
				mViewAllSelcted.setVisibility(View.GONE);
				mViewExSelected.setVisibility(View.GONE);
				mViewIncomeSelected.setVisibility(View.VISIBLE);
			}

			onRefresh();

		}
	};

	private void showWindow(View parent) {

		if (popupWindow == null) {
			view = View.inflate(this, R.layout.trading_record_pop, null);// 自定义layout

			popupWindowOut = view.findViewById(R.id.linear_will_show);
			popupWindowOut.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {
					startAnimationCancelPopupwindow();
				}
			});

			mViewAllSelcted = view.findViewById(R.id.all_selected);
			mViewExSelected = view.findViewById(R.id.expenditure_selected);
			mViewIncomeSelected = view.findViewById(R.id.income_selected);

			mViewAll = view.findViewById(R.id.all_rl);
			mViewEx = view.findViewById(R.id.expenditure);
			mViewIncome = view.findViewById(R.id.income);
			mViewAll.setOnClickListener(popupwindowListener);
			mViewEx.setOnClickListener(popupwindowListener);
			mViewIncome.setOnClickListener(popupwindowListener);

			popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
		}
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(false);
		popupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				startAnimationCancelPopupwindow();
			}
		});
		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		if (!popupWindow.isShowing()) {
			// PopupWindow 弹出时外部区域背景透明度降低
			popupWindow.showAsDropDown(mActionBar);
			// WindowManager.LayoutParams params = getWindow().getAttributes();
			// params.alpha = 0.7f;
			// getWindow().setAttributes(params);
		}
	}

	/**
	 * @param type
	 *            0 是全部 1是支出 2是收入
	 */
	private void screenAdapter(int type) {

		List<Records> records = new ArrayList<Records>();
		for (Records record : recordLists) {
			String transAmt = record.getTrans_amt();
			if (type == 1 && transAmt.startsWith("-")) {
				records.add(record);
			} else if (type == 2 && transAmt.startsWith("+")) {
				records.add(record);
			}
		}
		recordLists.clear();
		recordLists.addAll(records);
		if (recordLists == null || recordLists.isEmpty()) {
			// mListView.setVisibility(View.GONE);
			mViewEmpty.setVisibility(View.VISIBLE);
			String tipStr = "暂无收入交易记录";
			if (type == 1) {
				tipStr = "暂无支出交易记录";
			} else if (type == 2) {
				tipStr = "暂无收入交易记录";
			}
			mViewEmptyTip.setText(tipStr);
		}

		mListView.onLoadFinish(page, recordLists.size(), "加载完毕");
	}

	private void startAnimationCancelPopupwindow() {
		if (popupWindow.isShowing()) {
			popupWindow.dismiss();
		}
		if (rotateAnim2 == null) {
			rotateAnim2 = new RotateAnimation(180f, 0f,
					Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 0.5f);
			rotateAnim2.setInterpolator(new LinearInterpolator());
			// rotateAnim1.setRepeatCount(1);
			rotateAnim2.setFillAfter(true);
			rotateAnim2.setDuration(300);
		}
		mActionBar.startRotateAnimImageView(rotateAnim2);
	}

	@Override
	public void onRefresh() {
		page = 1;
		initData();
	}

	@Override
	public void onLoadMore() {
		page++;
		initData();
	}

}
