package cn.com.anyitou.ui.debttransfer;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.adapters.MyDebtTransferableAdapter;
import cn.com.anyitou.api.ApiUserUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.entity.DebtAssignment;
import cn.com.anyitou.entity.MyDebtTransferable;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.ui.InVestmentDetailActivity;
import cn.com.anyitou.ui.base.BaseFragment;
import cn.com.anyitou.utils.HttpConnectionUtil;
import cn.com.anyitou.utils.JsonUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.LoadingDialog;
import cn.com.anyitou.views.XListView;
import cn.com.anyitou.views.XListView.IXListViewListener;
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
	
	XListView mListView;
	private View mViewEmpty;
	private TextView mViewEmptyTip;
	MyDebtTransferableAdapter transAdapter;

	List<MyDebtTransferable> debtTransferLists;
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
		debtTransferLists = new ArrayList<MyDebtTransferable>();
		transAdapter = new MyDebtTransferableAdapter(debtTransferLists, mActivity);
		mListView.setAdapter(transAdapter);
		
		getMyUnuseCoupon();
		
	}
	

	private void initListener(){
//		mListView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				Intent intent = new Intent(mActivity,InVestmentDetailActivity.class);
//				DebtAssignment debtAssgiment = new DebtAssignment();
//				MyDebtTransferable debtTransfer = debtTransferLists.get(position);
//				debtAssgiment.setId(debtTransfer.getId());
//				debtAssgiment.setStatus(debtTransfer.getStatus());
//				debtAssgiment.setBuyer_apr(debtTransfer.getRate_of_interest());
//				debtAssgiment.setSell_days(debtTransfer.getRemainDays());
//				intent.putExtra("debt", debtAssgiment);
//				intent.putExtra("type", 2);
//				startActivity(intent);
//			}
//			
//		});
	}
//	mTvInvestName.setText(debtAssignment.getNumber());
//	mTvYearrate.setText(debtAssignment.getBuyer_apr()+"%");
//	mTvRepayType.setText("");
//	mTvRepayDate.setText(debtAssignment.getRepayment_time());
//	mTvEndDate.setText(debtAssignment.getSell_end_time());
//	
//	mTvInvestMoney.setText(StringUtils.getMoneyFormat(debtAssignment.getRemainAmount()));
//	mTvFinancingAmount.setText(StringUtils.getMoneyFormat(debtAssignment.getTransferred_amount()));
//	mTvInvestStatus.setText(debtAssignment.getStatus());
//	mTvFinancingDate.setText("获取哪个值？");
	
	

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
		if (page == 1) {
			loadingDialog = new LoadingDialog(mActivity);
			loadingDialog.show();
		} else if(page == 0){
			page++;
		}
		ApiUserUtils.getMyDebtTransferable(mActivity,String.valueOf(page), "10",
				new HttpConnectionUtil.RequestCallback() {

					@Override
					public void execute(ParseModel parseModel) {
						loadingDialog.cancel();
						if (ApiConstants.RESULT_SUCCESS.equals(parseModel
								.getCode())) {
							isFirst = false;
							 List<MyDebtTransferable> debtTransfers
							 = (List<MyDebtTransferable>)JsonUtils.fromJson(parseModel.getData().toString(),new TypeToken<List<MyDebtTransferable>>() {});
							 
							showEmptyListView(debtTransfers);
							mListView.onLoadFinish(page, debtTransfers.size(),"加载完毕");
							transAdapter.notifyDataSetChanged();

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
			debtTransferLists.clear();
			if(isEmpty){
				mViewEmpty.setVisibility(View.VISIBLE);
				mViewEmptyTip.setText("暂无记录");
			}else{
				debtTransferLists.addAll(list);
				mViewEmpty.setVisibility(View.GONE);
			}
		}else{
			if(!isEmpty){
				debtTransferLists.addAll(list);
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
