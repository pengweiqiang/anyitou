package cn.com.anyitou.ui;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.adapters.InvestCheck2Adapter;
import cn.com.anyitou.api.ApiInvestUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.entity.DetailFangdai;
import cn.com.anyitou.entity.HouseInfo;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.entity.ProjectVerifyLogs;
import cn.com.anyitou.ui.base.BaseFragment;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.JsonUtils;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.ToastUtils;
/**
 * 项目详情--》安房贷
 * @author will
 *
 */
public class InvestDetailSecondProDescFangDaiFragment extends BaseFragment {

	View infoView;
	String id = "";
	ListView mListViewCheck;//项目审核信息
//	ListView mListViewCheck2;//借款人信息
	
	
	
	private DetailFangdai investmentDetail;//企贷detail详情
	
	
	
	//house_info  start
	private TextView mTvSizes,
			mTvFloors,
			mTvLocation,
			mTvOrientation,
			mTvTypes,
			mTvBuyDate,
			mTvBuyPrice,
			mTvMortgageInfo,
			mTvValuation;
	
	//house_info end
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		infoView = inflater.inflate(R.layout.project_info_fangdai,
				container, false);
		id = this.getArguments().getString("id");
		initView();
		initListener();
		
		return infoView;
	}
	

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initData();
	}
	
	private void initView(){
		mListViewCheck = (ListView)infoView.findViewById(R.id.project_check_listview);
//		mListViewCheck2 = (ListView)infoView.findViewById(R.id.project_collateral_listview);
//		mGridView = (GridView)infoView.findViewById(R.id.gridView);
		
		initHouseInfoView();
	}
	/**
	 * 初始化house_info控件
	 */
	private void initHouseInfoView(){
		mTvSizes = (TextView)infoView.findViewById(R.id.tv_sizes_value);
		mTvFloors = (TextView)infoView.findViewById(R.id.tv_floor_value);
		mTvLocation = (TextView)infoView.findViewById(R.id.tv_location_value);
		mTvOrientation = (TextView)infoView.findViewById(R.id.tv_orientation_value);
		mTvTypes = (TextView)infoView.findViewById(R.id.tv_type_value);
		mTvBuyDate = (TextView)infoView.findViewById(R.id.tv_buy_date_value);
		mTvBuyPrice = (TextView)infoView.findViewById(R.id.tv_buy_price_value);
		mTvMortgageInfo = (TextView)infoView.findViewById(R.id.tv_mortgage_info_value);
		mTvValuation = (TextView)infoView.findViewById(R.id.tv_valuation_value);
	}

	private void initListener() {
	}
	
	private void initData(){
		//detail: 详情，返回数据与项目类型相关
		ApiInvestUtils.contentShow(mActivity, id,"detail", new RequestCallback() {
			
			@Override
			public void execute(ParseModel parseModel) {
				
				if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
					investmentDetail = JsonUtils.fromJson(parseModel.getData().toString(), DetailFangdai.class);
					setData();
				}else{
					ToastUtils.showToast(mActivity, StringUtils.isEmpty(parseModel.getMsg())?"获取项目详情失败，请稍后重试":parseModel.getMsg());
				}
				((InvestDetailSecond2Fragment)getParentFragment()).cancelDialog();
			}
		});
		
		
	}
	private void setData() {
		if(investmentDetail!=null){
			infoView.findViewById(R.id.content).setVisibility(View.VISIBLE);
			List<ProjectVerifyLogs> checks = investmentDetail.getProject_verify_logs();
			if(checks!=null && !checks.isEmpty()){
				
				//项目资料信息
				InvestCheck2Adapter checkAdapter = new InvestCheck2Adapter(checks, mActivity);
				mListViewCheck.setAdapter(checkAdapter);
				checkAdapter.notifyDataSetChanged();
				setListViewHeightBasedOnChildren(mListViewCheck);
			}else{
				infoView.findViewById(R.id.rl_check_project).setVisibility(View.GONE);
			}
			
//			List<BorrowerVerifyLogs> checks = investmentDetail.getBorrower_verify_logs();
//			if(checks!=null && !checks.isEmpty()){
//				//借款人信息
//				InvestCheck2Adapter checkAdapter2 = new InvestCheck2Adapter(checks2, mActivity);
//				mListViewCheck2.setAdapter(checkAdapter2);
//				checkAdapter2.notifyDataSetChanged();
//				setListViewHeightBasedOnChildren(mListViewCheck2);
//			}else{
//				infoView.findViewById(R.id.rl_collateral).setVisibility(View.GONE);
//				
//			}
			//抵押物信息
			HouseInfo houseInfo = investmentDetail.getHouse_info();
			if(houseInfo!=null){
				mTvSizes.setText(houseInfo.getSizes());
				mTvFloors.setText(houseInfo.getFloor());
				mTvLocation.setText(houseInfo.getLocation());
				mTvOrientation.setText(houseInfo.getOrientation());
				mTvTypes.setText(houseInfo.getType());
				mTvBuyDate.setText(houseInfo.getBuy_date());
				mTvBuyPrice.setText(houseInfo.getBuy_price());
				mTvMortgageInfo.setText(houseInfo.getMortgage_info());
				mTvValuation.setText(houseInfo.getValuation());
			}
			
		}else{//无项目审核信息、隐藏控件
			infoView.findViewById(R.id.rl_check_project).setVisibility(View.GONE);
			infoView.findViewById(R.id.house_info).setVisibility(View.GONE);
//			infoView.findViewById(R.id.rl_collateral).setVisibility(View.GONE);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}
}
