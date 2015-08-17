package cn.com.anyitou.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import cn.com.anyitou.R;

import cn.com.anyitou.adapters.InvestListAdapter;
import cn.com.anyitou.entity.LableValue;
import cn.com.anyitou.ui.base.BaseFragment;
/**
 * 投资列表
 * @author will
 *
 */
public class InvestDetailSecondListFragment extends BaseFragment {

	View infoView;
	ListView mListView;
	List<LableValue> secondPage;
	InvestListAdapter investAdapter;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		infoView = inflater.inflate(R.layout.project_details, container, false);
		mListView = (ListView)infoView.findViewById(R.id.listView_list);
		
		return infoView;
	}
	public void setSecondeList(List<LableValue> secondPage){
		this.secondPage = secondPage;
		if(secondPage!=null && !secondPage.isEmpty()){
			if(investAdapter == null){
				if(mActivity !=null){
					investAdapter = new InvestListAdapter(secondPage, mActivity);
					mListView.setAdapter(investAdapter);
				}
			}else{
				investAdapter.notifyDataSetChanged();
			}
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if(secondPage==null){
			secondPage = new ArrayList<LableValue>();
		}
		investAdapter = new InvestListAdapter(secondPage, mActivity);
		mListView.setAdapter(investAdapter);
	}

	@Override
	public void onResume() {
		super.onResume();
		setSecondeList(secondPage);
	}

	@Override
	public void onPause() {
		super.onPause();
	}
	
}
