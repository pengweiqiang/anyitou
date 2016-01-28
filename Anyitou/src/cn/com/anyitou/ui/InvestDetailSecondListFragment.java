package cn.com.anyitou.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.com.anyitou.R;
import cn.com.anyitou.adapters.InvestRecordsDetailAdapter;
import cn.com.anyitou.api.ApiInvestUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.entity.InvestRecords;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.ui.base.BaseFragment;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.JsonUtils;
import cn.com.anyitou.views.MyListView;
import cn.com.anyitou.views.XListView.IXListViewListener;
import cn.com.gson.reflect.TypeToken;
/**
 * 投资列表
 * @author will
 *
 */
public class InvestDetailSecondListFragment extends BaseFragment implements IXListViewListener{

	View infoView;
	String id = "";
//	LoadingDialog loadingDialog ;
	int page = 1;
	private MyListView mInvestRecordListView;
	InvestRecordsDetailAdapter adapter ;
	
//	private View mBtnMoreImage;
//	private View mBtnMoreInvestRecords;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		infoView = inflater.inflate(R.layout.project_details,
				container, false);
		id = this.getArguments().getString("id");
		initView();
		initListener();
		
		return infoView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		adapter = new InvestRecordsDetailAdapter(investRecordLists, mActivity);
		mInvestRecordListView.setAdapter(adapter);
		initData();
		
	}
	private void initView(){
		mInvestRecordListView = (MyListView)infoView.findViewById(R.id.invest_record_listview);
		mInvestRecordListView.setXListViewListener(this);
		mInvestRecordListView.setPullRefreshEnable(false);
		mInvestRecordListView.setFooterDividersEnabled(false);
		mInvestRecordListView.setPullLoadEnable(true);
//		mBtnMoreImage = infoView.findViewById(R.id.more_image);
//		mBtnMoreInvestRecords = infoView.findViewById(R.id.more_invest_record);
	}

	private void initListener() {
//		//点击查看更多图片
//		mBtnMoreImage.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(mActivity,ImagesMoreActivity.class);
//				intent.putExtra("id", id);
//				intent.putExtra("category", "default");
//				startActivity(intent);
//			}
//		});
		//点击查看更多投资记录
//		mBtnMoreInvestRecords.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(mActivity,InvestRecordMoreActivity.class);
//				intent.putExtra("id", id);
//				startActivity(intent);
//			}
//		});
	}
	
	private void initData(){
		//获取项目投资记录
		getInvestRecords();
		
	}
	
	/**
	 * 投资记录列表
	 */
	private List<InvestRecords> investRecordLists = new ArrayList<InvestRecords>();
	private void getInvestRecords(){
			ApiInvestUtils.getInvestTradeById(mActivity, id, String.valueOf(page==0?1:page), "10", new RequestCallback() {
				
				@Override
				public void execute(ParseModel parseModel) {
					if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
						List<InvestRecords> investRecords = (List<InvestRecords>) JsonUtils
								.fromJson(parseModel.getData().toString(),
										new TypeToken<List<InvestRecords>>() {
										});
						showInvestRecords(investRecords);
						mInvestRecordListView.onLoadFinish(page, investRecords.size(),
								"加载完毕");
//							ToastUtils.showToast(mActivity, parseModel.getData().toString());
					}else {
//						ToastUtils.showToast(mActivity, parseModel.getMsg());
						mInvestRecordListView.onLoadFinish(page, 0,
								parseModel.getMsg());
					}
					if(page==1){
						((InvestDetailSecond2Fragment)getParentFragment()).cancelDialog();
					}
				}
			});
	}
	
	private void showInvestRecords(List<InvestRecords> investRecords){
		if(page == 1){
			if(investRecords == null || investRecords.isEmpty()){
				infoView.findViewById(R.id.invest_rl).setVisibility(View.GONE);
				return;
			}else{
				investRecordLists.clear();
			}
		}
		investRecordLists.addAll(investRecords);
		adapter.notifyDataSetChanged();
//		setListViewHeightBasedOnChildren(mInvestRecordListView);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onRefresh() {
		page = 1;
		getInvestRecords();
	}

	@Override
	public void onLoadMore() {
		page ++;
		getInvestRecords();
	}

	
}
