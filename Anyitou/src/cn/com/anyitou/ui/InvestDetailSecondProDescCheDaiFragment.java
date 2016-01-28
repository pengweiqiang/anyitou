package cn.com.anyitou.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

import cn.com.anyitou.R;

import cn.com.anyitou.adapters.DetailImagesAdapter;
import cn.com.anyitou.adapters.InvestCheck2Adapter;
import cn.com.anyitou.adapters.InvestCheckAdapter;
import cn.com.anyitou.adapters.InvestRecordsDetailAdapter;
import cn.com.anyitou.api.ApiInvestUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.entity.BorrowerVerifyLogs;
import cn.com.anyitou.entity.CarInfo;
import cn.com.anyitou.entity.DetailChedai;
import cn.com.anyitou.entity.InvestRecords;
import cn.com.anyitou.entity.InvestmentDetail;
import cn.com.anyitou.entity.LableValue;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.entity.ProjectVerifyLogs;
import cn.com.anyitou.entity.Urls;
import cn.com.anyitou.ui.base.BaseFragment;
import cn.com.anyitou.utils.JsonUtils;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.views.LoadingDialog;
import cn.com.gson.JsonArray;
import cn.com.gson.JsonElement;
import cn.com.gson.JsonNull;
import cn.com.gson.JsonObject;
import cn.com.gson.reflect.TypeToken;
/**
 * 项目详情--》安车贷
 * @author will
 *
 */
public class InvestDetailSecondProDescCheDaiFragment extends BaseFragment {

	View infoView;
	String id = "";
	ListView mListViewCheck;//项目审核信息
	ListView mListViewCheckBorrower;//借款人信息
	
	
	
	private DetailChedai investmentDetail;//车贷detail详情
	
	
	
	//car_info  start
	private TextView mTvBrandModel,
			mTvVehicleType,
			mTvBuyTime,
			mTvBuyPrice,
			mTvKilometre,
			mTvEmissions,
			mTvAnnualInspectionDueDate,
			mTvInsuranceDueDate,
			mTvViolationName,
			mTvValuation;
	
	//car_info end
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		infoView = inflater.inflate(R.layout.project_info_chedai,
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
	boolean isFirst = true;
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// 判断Fragment中的ListView时候存在，判断该Fragment时候已经正在前台显示
		// 通过这两个判断，就可以知道什么时候去加载数据了
//		if (isVisibleToUser && isVisible() && isFirst) {
//			initData();
//		}
		super.setUserVisibleHint(isVisibleToUser);
	}
	
	private void initView(){
		mListViewCheck = (ListView)infoView.findViewById(R.id.project_check_listview);
		mListViewCheckBorrower = (ListView)infoView.findViewById(R.id.borrower_listview);
		
		initHouseInfoView();
	}
	/**
	 * 初始化house_info控件
	 */
	private void initHouseInfoView(){
		mTvBrandModel = (TextView)infoView.findViewById(R.id.tv_brand_model_value);
		mTvVehicleType = (TextView)infoView.findViewById(R.id.tv_vehicle_type_value);
		mTvBuyTime = (TextView)infoView.findViewById(R.id.tv_buy_time_value);
		mTvBuyPrice = (TextView)infoView.findViewById(R.id.tv_buy_price_value);
		mTvKilometre = (TextView)infoView.findViewById(R.id.tv_kilometre_value);
		mTvEmissions = (TextView)infoView.findViewById(R.id.tv_emissions_value);
		mTvAnnualInspectionDueDate = (TextView)infoView.findViewById(R.id.tv_annual_inspection_due_date_value);
		mTvInsuranceDueDate = (TextView)infoView.findViewById(R.id.tv_insurance_due_date_value);
		mTvViolationName = (TextView)infoView.findViewById(R.id.tv_violation_name_value);
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
					investmentDetail = JsonUtils.fromJson(parseModel.getData().toString(), DetailChedai.class);
					setData();
				}else{
					ToastUtils.showToast(mActivity, StringUtils.isEmpty(parseModel.getMsg())?"获取项目详情失败，请稍后重试":parseModel.getMsg());
				}
				isFirst = false;
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
			
			List<BorrowerVerifyLogs> checksBorrower = investmentDetail.getBorrower_verify_logs();
			if(checksBorrower!=null && !checksBorrower.isEmpty()){
				//借款人信息
				InvestCheckAdapter checkAdapter2 = new InvestCheckAdapter(checksBorrower, mActivity);
				mListViewCheckBorrower.setAdapter(checkAdapter2);
				checkAdapter2.notifyDataSetChanged();
				setListViewHeightBasedOnChildren(mListViewCheckBorrower);
			}else{
				infoView.findViewById(R.id.rl_borrower).setVisibility(View.GONE);
				
			}
			//抵押物信息
			CarInfo carInfo = investmentDetail.getCarInfo();
			if(carInfo!=null){
				mTvBrandModel.setText(carInfo.getBrand_model());
				mTvVehicleType.setText(carInfo.getVehicle_type_name());
				mTvBuyTime.setText(carInfo.getBuy_time());
				mTvBuyPrice.setText(carInfo.getBuy_price());
				mTvKilometre.setText(carInfo.getKilometre());
				mTvEmissions.setText(carInfo.getEmissions());
				mTvAnnualInspectionDueDate.setText(carInfo.getAnnual_inspection_due_date());
				mTvInsuranceDueDate.setText(carInfo.getInsurance_due_date());
				mTvViolationName.setText(carInfo.getViolation_name());
				mTvValuation.setText(carInfo.getValuation());
			}
			
		}else{//无项目审核信息、隐藏控件
			infoView.findViewById(R.id.rl_check_project).setVisibility(View.GONE);
			infoView.findViewById(R.id.car_info).setVisibility(View.GONE);
			infoView.findViewById(R.id.rl_borrower).setVisibility(View.GONE);
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
