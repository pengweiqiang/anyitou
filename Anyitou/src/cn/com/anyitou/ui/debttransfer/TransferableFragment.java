package cn.com.anyitou.ui.debttransfer;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.adapters.DebtTransferAdapter;
import cn.com.anyitou.api.ApiUserUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.entity.DebtTransfer;
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
 * 可转让
 * 
 * @author will
 * 
 */
@SuppressLint("NewApi")
public class TransferableFragment extends BaseFragment implements IXListViewListener{
	private View infoView;

	
	private LoadingDialog loadingDialog;
	private boolean isFirst = true;
	int page = 1;
	int type = 0;//0:全部  1:转让中  2:已转让
	
	XListView mListView;
	private View mViewEmpty;
	private TextView mViewEmptyTip;
	DebtTransferAdapter transAdapter;

	List<DebtTransfer> debtTransferLists;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		infoView = inflater.inflate(R.layout.activity_transfer_list, container, false);
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
		debtTransferLists = new ArrayList<DebtTransfer>();
		transAdapter = new DebtTransferAdapter(debtTransferLists, mActivity,type);
		mListView.setAdapter(transAdapter);
		
		getMyUnuseCoupon();
		
	}
	

	private void initListener(){
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ToastUtils.showToast(mActivity, "po: "+(position-1));
//				Intent intent = new Intent(mActivity,InVestmentDetailActivity.class);
//				intent.putExtra("id", debtAssignmentList.get(position).getId());
//				startActivity(intent);
			}
			
		});
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
		ApiUserUtils.getMyDebtTransfer(mActivity, String.valueOf(type),String.valueOf(page), "10",
				new HttpConnectionUtil.RequestCallback() {

					@Override
					public void execute(ParseModel parseModel) {
						loadingDialog.cancel();
						if (ApiConstants.RESULT_SUCCESS.equals(parseModel
								.getCode())) {
							isFirst = false;
							 List<DebtTransfer> debtTransfers
							 = (List<DebtTransfer>)JsonUtils.fromJson(parseModel.getData().toString(),new TypeToken<List<DebtTransfer>>() {});
							 
							if(page == 1){
								debtTransferLists.clear();
							}
							if (debtTransfers != null && !debtTransfers.isEmpty()) {
								// initViewPagerData();
								showEmptyListView(false);
								debtTransferLists.addAll(debtTransfers);
							} else {
								showEmptyListView(true);
							}
							mListView.onLoadFinish(page, debtTransfers.size(),"加载完毕");
							transAdapter.notifyDataSetChanged();

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
			mViewEmptyTip.setText("暂无未使用的优惠券");
		}else{
			mViewEmpty.setVisibility(View.GONE);
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
