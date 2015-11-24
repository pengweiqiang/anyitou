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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.adapters.DetailImagesAdapter;
import cn.com.anyitou.adapters.InvestRecordsDetailAdapter;
import cn.com.anyitou.api.ApiInvestUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.entity.InvestRecords;
import cn.com.anyitou.entity.InvestmentDetail;
import cn.com.anyitou.entity.ParseModel;
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
 * 项目详情第二页(安企贷)
 * 
 * @author will
 * 
 */
public class InvestDetailSecondFragment extends BaseFragment  {

	View infoView;
	String id = "";
	private TextView mTvIdeaHome;//经营状况分析
	private TextView mTvIdeaCredit;//信用分析
	private TextView mTvCapitalOpration,//资金用途
	mTvCompanyInfo,//企业信息
	mTvIdeaRepay,//还款来源
	mTvProjectContent,//项目简介
	mTvRiskContent;//风险控制
	LoadingDialog loadingDialog ;
	
//	private GridView mGridView;
	private Gallery mGallery;
	List<Urls> imageLists;
	DetailImagesAdapter detailImagesApdater;
	
	private ListView mInvestRecordListView;
	
	private InvestmentDetail investmentDetail;//企贷detail详情
	
//	private View mBtnMoreImage;
	private View mBtnLeft,mBtnRight;
	private View mBtnMoreInvestRecords;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		infoView = inflater.inflate(R.layout.invertment_detail_second,
				container, false);
		id = this.getArguments().getString("id");
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
		mTvIdeaRepay = (TextView)infoView.findViewById(R.id.payment_content);
		mTvIdeaHome = (TextView)infoView.findViewById(R.id.operation_analysis_content);
		mTvCapitalOpration = (TextView)infoView.findViewById(R.id.purpose_content);
		mTvCompanyInfo = (TextView)infoView.findViewById(R.id.company_content);
		mTvIdeaCredit = (TextView)infoView.findViewById(R.id.idea_credit_content);
		mTvProjectContent = (TextView)infoView.findViewById(R.id.content_detail_content);
		mTvRiskContent = (TextView)infoView.findViewById(R.id.risk_content);
//		mGridView = (GridView)infoView.findViewById(R.id.gridView);
		mBtnLeft = infoView.findViewById(R.id.left_image);
		mBtnRight = infoView.findViewById(R.id.right_image);
		mGallery = (Gallery)infoView.findViewById(R.id.gallery);
		mInvestRecordListView = (ListView)infoView.findViewById(R.id.invest_record_listview);
		
//		mBtnMoreImage = infoView.findViewById(R.id.more_image);
		mBtnMoreInvestRecords = infoView.findViewById(R.id.more_invest_record);
	}

	private void initListener() {
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
					investmentDetail = JsonUtils.fromJson(parseModel.getData().toString(), InvestmentDetail.class);
				}else{
					ToastUtils.showToast(mActivity, StringUtils.isEmpty(parseModel.getMsg())?"获取项目详情失败，请稍后重试":parseModel.getMsg());
				}
				setData();
			}
		});
		
		//attachment：相关资料图片
		ApiInvestUtils.contentShow(mActivity, id,"attachment", new RequestCallback() {
			
			@Override
			public void execute(ParseModel parseModel) {
				loadingDialog.cancel();
				if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
					JsonObject data = parseModel.getData().getAsJsonObject();
					if(data != null){
						JsonElement list = data.get("list");
						if(list!=null && list != JsonNull.INSTANCE){
							if(list.isJsonObject() && list.getAsJsonObject().has("default")){
								JsonArray defaultArray = list.getAsJsonObject().get("default").getAsJsonArray();
								if(defaultArray.size() !=0){
									imageLists = (List<Urls>) JsonUtils
											.fromJson(list.getAsJsonObject().get("default").toString(),
													new TypeToken<List<Urls>>() {
													});
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
			//企业信息
			if(StringUtils.isEmpty(investmentDetail.getCompany_info())){
				infoView.findViewById(R.id.rl_company).setVisibility(View.GONE);
			}else{
				mTvCompanyInfo.setText("\t"+Html.fromHtml(investmentDetail.getCompany_info()));
			}
			//项目简介
			if(StringUtils.isEmpty(investmentDetail.getDescription())){
//				mTvProjectContent.setVisibility(View.GONE);
				infoView.findViewById(R.id.rl_project).setVisibility(View.GONE);
			}else{
				mTvProjectContent.setText("\t"+Html.fromHtml(investmentDetail.getDescription()));
			}
			//风险控制
			if(StringUtils.isEmpty(investmentDetail.getRisk_control())){
//				mTvRiskContent.setVisibility(View.GONE);
				infoView.findViewById(R.id.rl_risk).setVisibility(View.GONE);
			}else{
				mTvRiskContent.setText("\t"+Html.fromHtml(investmentDetail.getRisk_control()));
			}
			//资金来源
			if(StringUtils.isEmpty(investmentDetail.getCapital_opration())){
				infoView.findViewById(R.id.rl_purpose).setVisibility(View.GONE);
			}else{
				mTvCapitalOpration.setText("\t"+Html.fromHtml(investmentDetail.getCapital_opration()));
			}
			//信用分析
			if(StringUtils.isEmpty(investmentDetail.getIdea_credit())){
				infoView.findViewById(R.id.rl_idea_credit).setVisibility(View.GONE);
			}else{
				mTvIdeaCredit.setText("\t"+Html.fromHtml(investmentDetail.getIdea_credit()));
			}
			//还款来源
			if(StringUtils.isEmpty(investmentDetail.getIdea_repay())){
				infoView.findViewById(R.id.rl_payment).setVisibility(View.GONE);
			}else{
				mTvIdeaRepay.setText("\t"+Html.fromHtml(investmentDetail.getIdea_repay()));
			}
			//经营状况分析
			if(StringUtils.isEmpty(investmentDetail.getIdea_home())){
				infoView.findViewById(R.id.rl_operation_analysis).setVisibility(View.GONE);
			}else{
				mTvIdeaHome.setText("\t"+Html.fromHtml(investmentDetail.getIdea_home()));
			}
		}else{
			infoView.findViewById(R.id.rl_payment).setVisibility(View.GONE);
			infoView.findViewById(R.id.rl_project).setVisibility(View.GONE);
			infoView.findViewById(R.id.rl_risk).setVisibility(View.GONE);
			infoView.findViewById(R.id.rl_purpose).setVisibility(View.GONE);
			infoView.findViewById(R.id.rl_idea_credit).setVisibility(View.GONE);
			infoView.findViewById(R.id.rl_company).setVisibility(View.GONE);
			infoView.findViewById(R.id.rl_operation_analysis).setVisibility(View.GONE);
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
//		mGridView.setAdapter(detailImagesApdater);
		mGallery.setAdapter(detailImagesApdater);
		mGallery.setSelection(1);
		detailImagesApdater.notifyDataSetChanged();
	}
	/**
	 * 投资记录列表
	 */
	private List<InvestRecords> investRecords = new ArrayList<InvestRecords>();
	private void getInvestRecords(){
		//TODO获取投资记录
			ApiInvestUtils.getInvestTradeById(mActivity, id, "1", "10", new RequestCallback() {
				
				@Override
				public void execute(ParseModel parseModel) {
					if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
						investRecords = (List<InvestRecords>) JsonUtils
								.fromJson(parseModel.getData().toString(),
										new TypeToken<List<InvestRecords>>() {
										});
//							ToastUtils.showToast(mActivity, parseModel.getData().toString());
					}else {
//						ToastUtils.showToast(mActivity, parseModel.getMsg());
					}
					showInvestRecords();
				}
			});
	}
	
	private void showInvestRecords(){
		if(investRecords == null || investRecords.isEmpty()){
			infoView.findViewById(R.id.invest_rl).setVisibility(View.GONE);
			return;
		}
		InvestRecordsDetailAdapter adapter = new InvestRecordsDetailAdapter(investRecords, mActivity);
		mInvestRecordListView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		setListViewHeightBasedOnChildren(mInvestRecordListView);
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
