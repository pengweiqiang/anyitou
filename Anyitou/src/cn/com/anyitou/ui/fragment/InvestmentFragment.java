package cn.com.anyitou.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.com.anyitou.R;
import cn.com.anyitou.ui.base.BaseFragment;
import cn.com.anyitou.views.ActionBar;

/**
 * 投资
 * 
 * @author will
 * 
 */
@SuppressLint("NewApi")
public class InvestmentFragment extends BaseFragment {
	private View infoView;
	private ActionBar mActionBar;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		infoView = inflater.inflate(R.layout.activity_my_bankcard, container, false);
		mActionBar = (ActionBar) infoView.findViewById(R.id.actionBar);
		onConfigureActionBar(mActionBar);
		return infoView;
	}
	   
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
	}

	// 设置activity的导航条
	protected void onConfigureActionBar(ActionBar actionBar) {
		actionBar.setTitle(getResources().getString(R.string.app_name));
		infoView.findViewById(R.id.actionBarLayout).setBackgroundColor(getResources().getColor(R.color.app_bg_color));
	}
	
	



}
