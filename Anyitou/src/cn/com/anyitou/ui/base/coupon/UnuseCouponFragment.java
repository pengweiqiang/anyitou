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
 * 未使用
 * 
 * @author will
 * 
 */
@SuppressLint("NewApi")
public class UnuseCouponFragment extends BaseFragment implements IXListViewListener{
	private View infoView;

	
	private LoadingDialog loadingDialog;
	private boolean isFirst = true;
	int page = 1;
	int type = 1;
	
	XListView mListView;
	private View mViewEmpty;
	private TextView mViewEmptyTip;
	CouponAdapter couponAdapter;

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
		
		getMyUnuseCoupon();
		
	}
	

	private void initListener(){
//		mListView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
////				Intent intent = new Intent(mActivity,InVestmentDetailActivity.class);
////				intent.putExtra("id", debtAssignmentList.get(position).getId());
////				startActivity(intent);
//			}
//			
//		});
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
	private void getMyUnuseCoupon() {
		if(page != 0){
			loadingDialog = new LoadingDialog(mActivity);
			loadingDialog.show();
		}else{
			page++;
		}
		ApiUserUtils.getMyCoupons(mActivity, "", String.valueOf(type),String.valueOf(page), "10",
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
	//							List<Investment> invests = getInvests(parseModel);
								 
								 showEmptyListView(coupons);
								mListView.onLoadFinish(page, coupons.size(),"加载完毕");
								couponAdapter.notifyDataSetChanged();
							}else{
								showEmptyListView(null);
							}

						} else {
							ToastUtils.showToast(mActivity,
									parseModel.getMsg());
						}
					}
				});
	}
	private void showEmptyListView(List list){
		boolean isEmpty =false;
		if(list == null || list.isEmpty()){
			isEmpty = true;
		}
		if(page == 1){
			couponList.clear();
			if(isEmpty){
				mViewEmpty.setVisibility(View.VISIBLE);
				mViewEmptyTip.setText("暂无未使用的优惠券");
			}else{
				couponList.addAll(list);
				mViewEmpty.setVisibility(View.GONE);
			}
		}else{
			if(!isEmpty){
				couponList.addAll(list);
			}
		}
		
	}

	@Override
	public void onRefresh() {
		page = 0;
		getMyUnuseCoupon();
	}

	@Override
	public void onLoadMore() {
		page++;
		getMyUnuseCoupon();
	}
	
	
	

}
