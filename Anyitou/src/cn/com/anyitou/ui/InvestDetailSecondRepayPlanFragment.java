package cn.com.anyitou.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ListView;

import cn.com.anyitou.R;

import cn.com.anyitou.adapters.InvestListAdapter;
import cn.com.anyitou.adapters.RepaymentPlanListAdapter;
import cn.com.anyitou.entity.LableValue;
import cn.com.anyitou.ui.base.BaseFragment;
/**
 * 还款计划
 * @author will
 *
 */
public class InvestDetailSecondRepayPlanFragment extends BaseFragment {

	View infoView;
	ListView mListView;
	List<LableValue> secondPage;
	RepaymentPlanListAdapter repaymentAdapter;
	private View mEmptyListView;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		infoView = inflater.inflate(R.layout.repayment_schedule, container, false);
		mListView = (ListView)infoView.findViewById(R.id.listView_repayplan);
		mEmptyListView = infoView.findViewById(R.id.empty_listview);
		
		return infoView;
	}
	public void setSecondeList(List<LableValue> secondPage){
		this.secondPage = secondPage;
		if(secondPage!=null && !secondPage.isEmpty()){
			if(repaymentAdapter == null){
				if(mActivity !=null){
					repaymentAdapter = new RepaymentPlanListAdapter(secondPage, mActivity);
					mListView.setAdapter(repaymentAdapter);
				}
			}else{
				repaymentAdapter.notifyDataSetChanged();
			}
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if(secondPage==null){
			secondPage = new ArrayList<LableValue>();
		}
		repaymentAdapter = new RepaymentPlanListAdapter(secondPage, mActivity);
		mListView.setAdapter(repaymentAdapter);
	}

	@Override
	public void onResume() {
		super.onResume();
		if(secondPage.isEmpty()){
			mEmptyListView.setVisibility(View.VISIBLE);
		}
		setSecondeList(secondPage);
	}

	@Override
	public void onPause() {
		super.onPause();
	}
	
}
