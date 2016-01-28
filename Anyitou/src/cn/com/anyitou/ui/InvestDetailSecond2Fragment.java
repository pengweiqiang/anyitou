package cn.com.anyitou.ui;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.com.anyitou.R;
import cn.com.anyitou.adapters.FragmentAdapter;
import cn.com.anyitou.api.constant.OperationType;
import cn.com.anyitou.ui.base.BaseFragment;
import cn.com.anyitou.views.CustomViewPager;
import cn.com.anyitou.views.LoadingDialog;
import cn.com.anyitou.views.PagerSlidingTabStrip;

/**
 * 项目详情第二页
 * 
 * @author will
 * 
 */
@SuppressLint("NewApi")
public class InvestDetailSecond2Fragment extends BaseFragment {

	CustomViewPager mViewPager;
	PagerSlidingTabStrip tabs;
	View infoView;
	String id = "";//项目id
	String category = "";//分类

	List<Fragment> list;
	int showIndex;

	
	private Fragment investDetailProdescFragment;//项目详情
	InvestDetailSecondRiskFragment riskFragment;//风险控制
	Fragment relatedPicsFragment;//相关资料
	InvestDetailSecondListFragment investListFragment;//投资记录
	
	private void initView() {

		mViewPager = (CustomViewPager) infoView.findViewById(R.id.viewPager);
		tabs = (PagerSlidingTabStrip) infoView.findViewById(R.id.tabs);

	}

	private void initListener() {

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		infoView = inflater.inflate(R.layout.invest_detail_second2,
				container, false);
		
		Bundle bundle = getArguments();
		id = bundle.getString("id");
		category = bundle.getString("category");
		
		initView();

		initListener();

		
		return infoView;
	}
	private boolean isFirst = true;
	LoadingDialog loadingDialog;
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// 判断Fragment中的ListView时候存在，判断该Fragment时候已经正在前台显示
		// 通过这两个判断，就可以知道什么时候去加载数据了
		if (isVisibleToUser && isVisible() && isFirst) {
			loadingDialog = new LoadingDialog(mActivity);
			loadingDialog.show();
			initData();
		}
		super.setUserVisibleHint(isVisibleToUser);
	}


	private void initData() {
		list = new ArrayList<Fragment>();
		
		//1.项目详情
		Bundle bundle = new Bundle();
		bundle.putString("id", id);
		bundle.putString("category", category);

		FragmentAdapter adapter = new FragmentAdapter(this.getChildFragmentManager());
		String titles[] = new String[] { "项目详情","相关资料","投资记录"};
		if(OperationType.CATEGORY_FANGDAI.getName().equals(category)){//房贷
			investDetailProdescFragment = new InvestDetailSecondProDescFangDaiFragment();
			list.add(investDetailProdescFragment);
			
			//3.相关资料
			relatedPicsFragment = new InvestDetailSecondRelatedPicsCheDaiFragment();
			relatedPicsFragment.setArguments(bundle);
			
			
		}else if(OperationType.CATEGORY_CHEDAI.getName().equals(category)){//车贷
			investDetailProdescFragment = new InvestDetailSecondProDescCheDaiFragment();
			list.add(investDetailProdescFragment);
			
			//3.相关资料
			relatedPicsFragment = new InvestDetailSecondRelatedPicsCheDaiFragment();
			relatedPicsFragment.setArguments(bundle);
		}else {//企贷
			investDetailProdescFragment = new InvestDetailSecondProDescFragment();
			list.add(investDetailProdescFragment);
			
			//2.风险控制
			riskFragment = new InvestDetailSecondRiskFragment();
			list.add(riskFragment);
			
			titles = new String[] { "项目详情","风险控制","相关资料","投资记录"};
			
			//3.相关资料
			relatedPicsFragment = new InvestDetailSecondRelatedPicsFragment();
			relatedPicsFragment.setArguments(bundle);
			
		}
		investDetailProdescFragment.setArguments(bundle);
		
		
		//4.投资记录
		investListFragment = new InvestDetailSecondListFragment();
		investListFragment.setArguments(bundle);
		
		
		
		list.add(relatedPicsFragment);
		list.add(investListFragment);

		// 绑定适配器
		adapter.setData(titles, list);
		mViewPager.setAdapter(adapter);
		mViewPager.setOffscreenPageLimit(titles.length);
		tabs.setViewPager(mViewPager);
		mViewPager.setCurrentItem(showIndex);
		isFirst = false;
	}
	public void cancelDialog(){
		if(loadingDialog!=null){
			loadingDialog.cancel();
		}
	}
	/**
	 * 展示风险控制数据
	 * @param riskContent
	 */
	public void showRiskContent(String guaranteeOpinion,String riskContent,String ideaRisk){
		riskFragment.setRiskContent(guaranteeOpinion,riskContent,ideaRisk);
	}


}
