package cn.com.anyitou.ui.base.coupon;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.adapters.CouponAdapter;
import cn.com.anyitou.api.ApiUserUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.entity.Coupon;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.ui.base.BaseFragment;
import cn.com.anyitou.utils.HttpConnectionUtil;
import cn.com.anyitou.utils.JsonUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.LoadingDialog;
import cn.com.anyitou.views.XListView;
import cn.com.anyitou.views.XListView.IXListViewListener;
import cn.com.gson.JsonElement;
import cn.com.gson.JsonObject;
import cn.com.gson.reflect.TypeToken;

/**
 * 已过期优惠券
 * 
 * @author will
 * 
 */
@SuppressLint("NewApi")
public class OutDateCouponFragment extends BaseFragment implements IXListViewListener{
	private View infoView;

	
	private LoadingDialog loadingDialog;
	private boolean isFirst = true;
	int page = 1;
	
	XListView mListView;
	View mViewEmpty;
	TextView mViewEmptyTip;
	CouponAdapter couponAdapter;
	int type = 3;

	List<Coupon> couponList;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		infoView = inflater.inflate(R.layout.activity_coupon_list, container, false);
		mListView = (XListView) infoView.findViewById(R.id.listView_list);
		mViewEmpty = infoView.findViewById(R.id.empty_listview);
		mViewEmptyTip = (TextView) infoView.findViewById(R.id.xlistview_empty_tip);
		
		mListView.setPullLoadEnable(true);
		mListView.setXListViewListener(this);
		initListener();
		
		
		return infoView;
	}
	   
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		couponList = new ArrayList<Coupon>();
		couponAdapter = new CouponAdapter(couponList, mActivity,type);
		mListView.setAdapter(couponAdapter);
		
	}
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// 判断Fragment中的ListView时候存在，判断该Fragment时候已经正在前台显示
		// 通过这两个判断，就可以知道什么时候去加载数据了
		if (isVisibleToUser && isVisible() && isFirst) {
			getMyOutDateCoupon();
		}
		super.setUserVisibleHint(isVisibleToUser);
	}
	
	

	private void initListener(){
		
	}
	
	
	

	@Override
	public void onResume() {
		super.onResume();
	}

	// 设置activity的导航条
	protected void onConfigureActionBar(ActionBar actionBar) {
		
		
	}
	
	
	/**
	 * 获取投资列表数据
	 */
	private void getMyOutDateCoupon() {
		if(page != 0){
			loadingDialog = new LoadingDialog(mActivity);
			loadingDialog.show();
		}else{
			page++;
		}
		ApiUserUtils.getMyCoupons(mActivity, "", String.valueOf(type), String.valueOf(page), "10", 
				new HttpConnectionUtil.RequestCallback() {

					@Override
					public void execute(ParseModel parseModel) {
						loadingDialog.cancel();
						if (ApiConstants.RESULT_SUCCESS.equals(parseModel
								.getCode())) {
							JsonObject data = parseModel.getData().getAsJsonObject();
							if(data!=null){
								isFirst = false;
								JsonElement list = data.get("list");
								 List<Coupon> coupons = (List<Coupon>)JsonUtils.fromJson(list.toString(),new TypeToken<List<Coupon>>() {});
							 
								if(page == 1){
									couponList.clear();
								}
								if (coupons != null && !coupons.isEmpty()) {
									// initViewPagerData();
									showEmptyListView(false);
									couponList.addAll(coupons);
								} else {
									showEmptyListView(true);
								}
								mListView.onLoadFinish(page, coupons.size(),"加载完毕");
								couponAdapter.notifyDataSetChanged();
							}else{
								showEmptyListView(true);
							}

						} else {
							ToastUtils.showToast(mActivity,
									parseModel.getMsg());
						}
					}
				});
	}
	private void showEmptyListView(boolean isEmpty){
		if(isEmpty){
			mViewEmpty.setVisibility(View.VISIBLE);
			mViewEmptyTip.setText("暂无已过期的优惠券");
		}else{
			mViewEmpty.setVisibility(View.GONE);
		}
	}

	@Override
	public void onRefresh() {
		page = 0;
		getMyOutDateCoupon();
	}

	@Override
	public void onLoadMore() {
		page++;
		getMyOutDateCoupon();
	}
	

}
