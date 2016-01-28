package cn.com.anyitou.ui.debttransfer;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import cn.com.gson.reflect.TypeToken;

/**
 * 已结束
 * 
 * @author pengweiqiang
 * 
 */
@SuppressLint("NewApi")
public class TransferEndFragment extends BaseFragment implements IXListViewListener{
	private View infoView;

	
	private LoadingDialog loadingDialog;
	private boolean isFirst = true;
	int page = 1;
	
	XListView mListView;
	View mViewEmpty;
	TextView mViewEmptyTip;
	DebtTransferAdapter transferAdapter;
	int type = 2;// 2:已转让

	List<DebtTransfer> transferLists;
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
		transferLists = new ArrayList<DebtTransfer>();
		transferAdapter = new DebtTransferAdapter(transferLists, mActivity,type);
		mListView.setAdapter(transferAdapter);
		
	}
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// 判断Fragment中的ListView时候存在，判断该Fragment时候已经正在前台显示
		// 通过这两个判断，就可以知道什么时候去加载数据了
		if (isVisibleToUser && isVisible() && isFirst) {
			getMyDebtTransfer();
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
	private void getMyDebtTransfer() {
		if (page == 1) {
			loadingDialog = new LoadingDialog(mActivity);
			loadingDialog.show();
		} else if(page == 0){
			page++;
		}
		ApiUserUtils.getMyDebtTransfer(mActivity, String.valueOf(type), String.valueOf(page), "10", 
				new HttpConnectionUtil.RequestCallback() {

					@Override
					public void execute(ParseModel parseModel) {
						loadingDialog.cancel();
						if (ApiConstants.RESULT_SUCCESS.equals(parseModel
								.getCode())) {
							isFirst = false;
							 List<DebtTransfer> transfers = (List<DebtTransfer>)JsonUtils.fromJson(parseModel.getData().toString(),new TypeToken<List<DebtTransfer>>() {});
						 
							showEmptyListView(transfers);
							mListView.onLoadFinish(page, transfers.size(),"加载完毕");
							transferAdapter.notifyDataSetChanged();

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
			transferLists.clear();
			if(isEmpty){
				mViewEmpty.setVisibility(View.VISIBLE);
				mViewEmptyTip.setText("暂无记录");
			}else{
				transferLists.addAll(list);
				mViewEmpty.setVisibility(View.GONE);
			}
		}else{
			if(!isEmpty){
				transferLists.addAll(list);
			}
		}
		
	}

	@Override
	public void onRefresh() {
		page = 0;
		getMyDebtTransfer();
	}

	@Override
	public void onLoadMore() {
		page++;
		getMyDebtTransfer();
	}
	

}
