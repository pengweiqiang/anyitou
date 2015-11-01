package cn.com.anyitou.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import cn.com.anyitou.R;

import cn.com.anyitou.adapters.MyVerticalPagerAdapter;
import cn.com.anyitou.entity.LableData;
import cn.com.anyitou.ui.base.BaseFragment;
import cn.com.anyitou.views.CustomViewPager2;

/**
 * 项目详情第二页
 * 
 * @author will
 * 
 */
public class InvestDetailSecondFragment extends BaseFragment implements
		OnPageChangeListener {

	View infoView;
	private CustomViewPager2 mViewPager;
	private List<Fragment> fragmentLists;
	MyVerticalPagerAdapter fragmentAdapter;
	private RadioGroup tab_radioGroup;
	public List<LableData> secondPage;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		infoView = inflater.inflate(R.layout.invertment_detail_second,
				container, false);

		mViewPager = (CustomViewPager2) infoView.findViewById(R.id.viewpager);
		tab_radioGroup = (RadioGroup) infoView.findViewById(R.id.rg_title);

		return infoView;
	}
//	public void setSecondeList(List<LableData> secondPage){
//		this.secondPage = secondPage;
//		for (LableData lableData : secondPage) {
//			if(getResources().getString(R.string.invest_description).equals(lableData.getLabel())){
//				investProDesc.setSecondeList(lableData.getData());
//			}else if(getResources().getString(R.string.invest_risk_control).equals(lableData.getLabel())){
//				investRisk.setSecondeList(lableData.getData());
//			}else if(getResources().getString(R.string.invest_repayment_plan).equals(lableData.getLabel())){
//				investRepayPlan.setSecondeList(lableData.getData());
//			}else if(getResources().getString(R.string.invest_list).equals(lableData.getLabel())){
//				investList.setSecondeList(lableData.getData());
//			}
//		}
//		
//	}
	InvestDetailSecondProDescFragment investProDesc;
	InvestDetailSecondRiskFragment investRisk ;
	InvestDetailSecondRepayPlanFragment investRepayPlan;
	InvestDetailSecondListFragment investList ;
	private void getFragmentLists() {
		fragmentLists = new ArrayList<Fragment>();

		investProDesc = new InvestDetailSecondProDescFragment();// 项目描述
		investRisk = new InvestDetailSecondRiskFragment();// 风险控制
		investRepayPlan = new InvestDetailSecondRepayPlanFragment();// 还款计划
		investList = new InvestDetailSecondListFragment();// 投资列表

		fragmentLists.add(investProDesc);
		fragmentLists.add(investRisk);
		fragmentLists.add(investRepayPlan);
		fragmentLists.add(investList);

		fragmentAdapter = new MyVerticalPagerAdapter(
				this.getChildFragmentManager(), fragmentLists);
		mViewPager.setAdapter(fragmentAdapter);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initListener();
		getFragmentLists();
		
		mViewPager.setOnPageChangeListener(this);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	/**
	 * 滑动状态改变时调用
	 */
	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	/**
	 * 当前页面滑动时调用
	 */
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	/**
	 * 新的页面被选中时调用
	 */
	@Override
	public void onPageSelected(int position) {
		int checkId = R.id.invest_descript;
		switch (position) {
		case 0:
			checkId = R.id.invest_descript;
			break;
		case 1:
			checkId = R.id.invest_risk_control;
			break;
		case 2:
			checkId = R.id.invest_repayment_plan;
			break;
		case 3:
			checkId = R.id.invest_list;
			break;

		default:
			break;
		}
		tab_radioGroup.check(checkId);
//		RadioButton rbChecked= (RadioButton)infoView.findViewById(checkId);
//		rbChecked.setChecked(true);
	}

	private void initListener() {
		tab_radioGroup
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						if (checkedId == R.id.invest_descript) {
							mViewPager.setCurrentItem(0, false);
						} else if (checkedId == R.id.invest_risk_control) {
							mViewPager.setCurrentItem(1, false);
						} else if (checkedId == R.id.invest_repayment_plan) {
							mViewPager.setCurrentItem(2, false);
						} else if (checkedId == R.id.invest_list) {
							mViewPager.setCurrentItem(3, false);
						}
					}
				});
	}

}
