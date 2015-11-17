package cn.com.anyitou.ui;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.adapters.HomeListAdapter;
import cn.com.anyitou.api.ApiInvestUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.entity.Investment;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.ui.base.BaseFragment;
import cn.com.anyitou.utils.HttpConnectionUtil;
import cn.com.anyitou.utils.JsonUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.LoadingDialog;
import cn.com.anyitou.views.XListView;
import cn.com.anyitou.views.XListView.IXListViewListener;
import cn.com.gson.reflect.TypeToken;

/**
 * 项目列表
 * 
 * @author will
 * 
 */
@SuppressLint("NewApi")
public class ProjectListFragment extends BaseFragment implements IXListViewListener{
	private View infoView;

	
	private LoadingDialog loadingDialog;
	
	int page = 1;
	
	XListView mListView;
	private View mViewEmpty;
	private TextView mViewEmptyTip;
	HomeListAdapter homeAdapter;
	private boolean isFirst = true;

	List<Investment> investLists;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		infoView = inflater.inflate(R.layout.activity_project_list, container, false);
		mListView = (XListView) infoView.findViewById(R.id.listView_list);
		mViewEmpty = infoView.findViewById(R.id.empty_listview);
		mViewEmptyTip = (TextView) infoView.findViewById(R.id.xlistview_empty_tip);
		
		mListView.showLastView();
		mListView.setPullLoadEnable(true);
		mListView.setXListViewListener(this);
		
		initListener();
		return infoView;
	}
	   
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		investLists = new ArrayList<Investment>();
		homeAdapter = new HomeListAdapter(investLists, mActivity);
		mListView.setAdapter(homeAdapter);
		if(getUserVisibleHint() && isFirst){
			getInvestList();
		}
	}
	
	@Override
	 public void setUserVisibleHint(boolean isVisibleToUser) {
        //判断Fragment中的ListView时候存在，判断该Fragment时候已经正在前台显示  通过这两个判断，就可以知道什么时候去加载数据了
		Log.e("about", "setUserVisibleHint");
		 if (isVisibleToUser && isVisible() && isFirst) {
			 Log.e("about", "setUserVisibleHint"+"  initdata");
			 getInvestList(); //加载数据的方法
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
				intent.putExtra("type", 1);//投资
				intent.putExtra("invest", investLists.get(position-1));
				startActivity(intent);
			}
			
		});
	}
	
	
	

	@Override
	public void onResume() {
		super.onResume();
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
		ApiInvestUtils.getInvestList(mActivity,String.valueOf(page),"10",
				new HttpConnectionUtil.RequestCallback() {

					@Override
					public void execute(ParseModel parseModel) {
						loadingDialog.cancelDialog(loadingDialog);
						if (ApiConstants.RESULT_SUCCESS.equals(parseModel
								.getCode())) {
							isFirst = false;
							 List<Investment> invests = (List<Investment>)JsonUtils.fromJson(parseModel.getData().toString(),new TypeToken<List<Investment>>() {});
//							List<Investment> invests = getInvests(parseModel);
							 showEmptyListView(invests);
							mListView.onLoadFinish(page, invests.size(),"加载完毕");
							
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
			investLists.clear();
			if(isEmpty){
				mViewEmpty.setVisibility(View.VISIBLE);
				mViewEmptyTip.setText("暂无投资列表");
			}else{
				investLists.addAll(list);
				mViewEmpty.setVisibility(View.GONE);
				homeAdapter.notifyDataSetChanged();
			}
		}else{
			if(!isEmpty){
				investLists.addAll(list);
				homeAdapter.notifyDataSetChanged();
			}
		}
		
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
