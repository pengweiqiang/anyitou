package cn.com.anyitou.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.adapters.DetailImagesAdapter;
import cn.com.anyitou.adapters.InvestCheck2Adapter;
import cn.com.anyitou.adapters.InvestRecordsDetailAdapter;
import cn.com.anyitou.api.ApiInvestUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.entity.DetailFangdai;
import cn.com.anyitou.entity.HouseInfo;
import cn.com.anyitou.entity.InvestRecords;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.entity.ProjectVerifyLogs;
import cn.com.anyitou.entity.Urls;
import cn.com.anyitou.ui.base.BaseFragment;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.JsonUtils;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.LoadingDialog;
import cn.com.gson.JsonArray;
import cn.com.gson.JsonElement;
import cn.com.gson.JsonNull;
import cn.com.gson.JsonObject;
import cn.com.gson.reflect.TypeToken;

/**
 * 项目详情第二页(房贷)
 * 
 * @author will
 * 
 */
public class InvestDetailSecondFangDaiFragment extends BaseFragment  {

	View infoView;
	String id = "";
	ListView mListViewCheck;//项目审核信息
//	ListView mListViewCheck2;//借款人信息
	LoadingDialog loadingDialog ;
	String category = "";
	
//	private GridView mGridView;
	private Gallery mGallery;
	List<Urls> imageLists;
	DetailImagesAdapter detailImagesApdater;
	
	private ListView mInvestRecordListView;
	
	private DetailFangdai investmentDetail;//企贷detail详情
	
//	private View mBtnMoreImage;
	private View mBtnMoreInvestRecords;//查看更多
	private View mBtnLeft,mBtnRight;
	
	
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
		infoView = inflater.inflate(R.layout.invertment_detail_second_fangdai,
				container, false);
		id = this.getArguments().getString("id");
		category = this.getArguments().getString("category");
		initView();
		initListener();
		
		return infoView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initListener();
		initData();
		
	}
	
	private void initView(){
		mListViewCheck = (ListView)infoView.findViewById(R.id.project_check_listview);
//		mListViewCheck2 = (ListView)infoView.findViewById(R.id.project_collateral_listview);
//		mGridView = (GridView)infoView.findViewById(R.id.gridView);
		mBtnLeft = infoView.findViewById(R.id.left_image);
		mBtnRight = infoView.findViewById(R.id.right_image);
		mGallery = (Gallery)infoView.findViewById(R.id.gallery);
		mInvestRecordListView = (ListView)infoView.findViewById(R.id.invest_record_listview);
		
//		mBtnMoreImage = infoView.findViewById(R.id.more_image);
		mBtnMoreInvestRecords = infoView.findViewById(R.id.more_invest_record);
		
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
		mGallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(mActivity,ImageViewActivity.class);
				intent.putExtra("url", imageLists.get(position).getUrl());
				intent.putParcelableArrayListExtra("urls", (ArrayList<? extends Parcelable>) imageLists);
				intent.putExtra("position", position);
				intent.putExtra("name", "图片详情");
				startActivity(intent);
			}
			
		});
		mBtnLeft.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int curPosition = mGallery.getSelectedItemPosition();
				if(curPosition>1){
					mGallery.setSelection(curPosition-1);
				}
			}
		});
		mBtnRight.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int curPosition = mGallery.getSelectedItemPosition();
				if(curPosition<imageLists.size()-2){
					mGallery.setSelection(curPosition+1);
				}
			}
		});
		mGallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
//				ToastUtils.showToast(mActivity, "position:"+position);
				if(position <= 1){
					((ImageView)mBtnLeft).setImageDrawable(getResources().getDrawable(R.drawable.left_btn_over_icon));
				}else if(position>=imageLists.size()-2){
					((ImageView)mBtnRight).setImageDrawable(getResources().getDrawable(R.drawable.right_btn_over_icon));
				}else{
					((ImageView)mBtnLeft).setImageDrawable(getResources().getDrawable(R.drawable.left_btn_icon));
					((ImageView)mBtnRight).setImageDrawable(getResources().getDrawable(R.drawable.right_btn_icon));
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			
		});
		//点击查看更多投资记录
		mBtnMoreInvestRecords.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mActivity,InvestRecordMoreActivity.class);
				intent.putExtra("id", id);
				startActivity(intent);
			}
		});
	}
	
	private void initData(){
		loadingDialog = new LoadingDialog(mActivity);
		//detail: 详情，返回数据与项目类型相关
		ApiInvestUtils.contentShow(mActivity, id,"detail", new RequestCallback() {
			
			@Override
			public void execute(ParseModel parseModel) {
				loadingDialog.cancel();
				if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
					investmentDetail = JsonUtils.fromJson(parseModel.getData().toString(), DetailFangdai.class);
					setData();
				}else{
					ToastUtils.showToast(mActivity, StringUtils.isEmpty(parseModel.getMsg())?"获取项目详情失败，请稍后重试":parseModel.getMsg());
				}
				
			}
		});
		
		//attachment：相关资料图片
		ApiInvestUtils.contentShow(mActivity, id,"attachment", new RequestCallback() {
			
			@Override
			public void execute(ParseModel parseModel) {
				loadingDialog.cancel();
				if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
//					investDetail = JsonUtils.fromJson(parseModel.getData().toString(), InVestDetail.class);
//					ToastUtils.showToast(mActivity, parseModel.getData().getAsJsonObject().toString());
					JsonObject data = parseModel.getData().getAsJsonObject();
					if(data != null){
						JsonElement list = data.get("list");
						if(list!=null && list != JsonNull.INSTANCE){
							if(list.isJsonObject() && list.getAsJsonObject().has("fangdai_housepic")){
								JsonArray defaultArray = list.getAsJsonObject().get("fangdai_housepic").getAsJsonArray();
								if(defaultArray.size() !=0){
									imageLists = new ArrayList<Urls>();
									for (int i = 0; i < defaultArray.size(); i++) {
										JsonObject urlJson = defaultArray.get(i).getAsJsonObject();
										Urls url = new Urls();
										url.setUrl(urlJson.get("url").getAsString());
										imageLists.add(url);
									}
									showInvestImages();
								}
							}
						}
					}
				}else{
					ToastUtils.showToast(mActivity, StringUtils.isEmpty(parseModel.getMsg())?"获取项目详情失败，请稍后重试":parseModel.getMsg());
				}
				showInvestImages();
			}
		
		});
		//获取项目投资记录
		getInvestRecords();
		
	}
	private void setData() {
		if(investmentDetail!=null){
			
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
	/**
	 * 相关资料图片列表
	 */
	private void showInvestImages(){
		if(imageLists==null || imageLists.isEmpty()){
			infoView.findViewById(R.id.image_rl).setVisibility(View.GONE);
			return;
		}
		detailImagesApdater = new DetailImagesAdapter(imageLists, mActivity);
		mGallery.setAdapter(detailImagesApdater);
		mGallery.setSelection(1);
		detailImagesApdater.notifyDataSetChanged();
	}
	/**
	 * 投资记录列表
	 */
	private void getInvestRecords(){
		//TODO获取投资记录
		ApiInvestUtils.getInvestTradeById(mActivity, id, "1", "10", new RequestCallback() {
			
			@Override
			public void execute(ParseModel parseModel) {
				if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
//					ToastUtils.showToast(mActivity, parseModel.getData().toString());
					List<InvestRecords> records = (List<InvestRecords>) JsonUtils
							.fromJson(parseModel.getData().toString(),
									new TypeToken<List<InvestRecords>>() {
									});
					showInvestRecords(records);
				}else {
					showInvestRecords(null);
//					ToastUtils.showToast(mActivity, parseModel.getMsg());
				}
			}
		});
	}
	
	private void showInvestRecords(List<InvestRecords> investRecords){
		if(investRecords == null || investRecords.isEmpty()){
			infoView.findViewById(R.id.invest_rl).setVisibility(View.GONE);
			return;
		}
		InvestRecordsDetailAdapter adapter = new InvestRecordsDetailAdapter(investRecords, mActivity);
		mInvestRecordListView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		setListViewHeightBasedOnChildren(mInvestRecordListView);
		//TODO 显示投资记录
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
