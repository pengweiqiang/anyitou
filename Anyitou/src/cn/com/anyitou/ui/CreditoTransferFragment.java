package cn.com.anyitou.ui;

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
import cn.com.anyitou.R;
import cn.com.anyitou.adapters.DebtAssignmentAdapter;
import cn.com.anyitou.adapters.HomeListAdapter;
import cn.com.anyitou.api.ApiInvestUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.entity.DebtAssignment;
import cn.com.anyitou.entity.Investment;
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
 * 债券转让
 * 
 * @author will
 * 
 */
@SuppressLint("NewApi")
public class CreditoTransferFragment extends BaseFragment implements IXListViewListener{
	private View infoView;

	
	private LoadingDialog loadingDialog;
	private boolean isFirst = true;
	int page = 1;
	
	XListView mListView;
	DebtAssignmentAdapter debtAssignAdpater;

	List<DebtAssignment> debtAssignmentList;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		infoView = inflater.inflate(R.layout.activity_cretido_transfer, container, false);
		mListView = (XListView) infoView.findViewById(R.id.listView_list);
		
		mListView.setPullLoadEnable(true);
		mListView.setXListViewListener(this);
		initListener();
		
		
		return infoView;
	}
	   
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		debtAssignmentList = new ArrayList<DebtAssignment>();
		debtAssignAdpater = new DebtAssignmentAdapter(debtAssignmentList, mActivity);
		mListView.setAdapter(debtAssignAdpater);
		
	}
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// 判断Fragment中的ListView时候存在，判断该Fragment时候已经正在前台显示
		// 通过这两个判断，就可以知道什么时候去加载数据了
		if (isVisibleToUser && isVisible() && isFirst) {
			getInvestList();
		}
		super.setUserVisibleHint(isVisibleToUser);
	}
	
	

	private void initListener(){
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(mActivity,
						InVestmentDetailActivity.class);
				intent.putExtra("type", 2);//债权
				intent.putExtra("debt", debtAssignmentList.get(position-1));
				startActivity(intent);
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
	private void getInvestList() {
		if(page != 0){
			loadingDialog = new LoadingDialog(mActivity);
			loadingDialog.show();
		}else{
			page++;
		}
		ApiInvestUtils.getDebtAssignment(mActivity,page,"10","","","","",
				new HttpConnectionUtil.RequestCallback() {

					@Override
					public void execute(ParseModel parseModel) {
						loadingDialog.cancel();
						if (ApiConstants.RESULT_SUCCESS.equals(parseModel
								.getCode())) {
							isFirst = false;
							 List<DebtAssignment> debtAssignments = (List<DebtAssignment>)JsonUtils.fromJson(parseModel.getData().toString(),new TypeToken<List<DebtAssignment>>() {});
//							List<Investment> invests = getInvests(parseModel);
							 
							if(page == 1){
								debtAssignmentList.clear();
							}
							if (debtAssignments != null && !debtAssignments.isEmpty()) {
								// initViewPagerData();
								debtAssignmentList.addAll(debtAssignments);
							} else {
								ToastUtils.showToast(mActivity,
										"暂时没有债权列表");
							}
							mListView.onLoadFinish(page, debtAssignments.size(),"加载完毕");
							debtAssignAdpater.notifyDataSetChanged();

						} else {
							ToastUtils.showToast(mActivity,
									parseModel.getMsg());
						}
					}
				});
	}

	@Override
	public void onRefresh() {
		page = 0;
		getInvestList();
	}

	@Override
	public void onLoadMore() {
		page++;
		getInvestList();
	}
	
	
//	private List<Investment> getInvests(ParseModel parseModel) {
//		List<Investment> investments = new ArrayList<Investment>();
//		if (parseModel.getData().isJsonObject()) {
//			JsonObject investmentJsonObject = parseModel.getData()
//					.getAsJsonObject();
//			Set<Entry<String, JsonElement>> sets = investmentJsonObject
//					.entrySet();
//			Iterator<Entry<String, JsonElement>> keys = sets.iterator();
//			while (keys.hasNext()) {
//				Entry<String, JsonElement> entry = keys.next();
//				Investment investment = JsonUtils.fromJson(entry.getValue()
//						.toString(), Investment.class);
//				if (investment != null) {
//					investments.add(investment);
//				}
//			}
//		}
//		return investments;
//	}
	

}
