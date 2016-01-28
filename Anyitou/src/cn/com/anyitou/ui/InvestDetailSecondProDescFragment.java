package cn.com.anyitou.ui;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.api.ApiInvestUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.entity.InvestmentDetail;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.ui.base.BaseFragment;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.JsonUtils;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.ToastUtils;
/**
 * 项目详情--》安企贷
 * @author will
 *
 */
public class InvestDetailSecondProDescFragment extends BaseFragment {

	View infoView;
	
	String id = "";
	
	private TextView mTvIdeaHome;//经营状况分析
	private TextView mTvIdeaCredit;//信用分析
	private TextView mTvCapitalOpration,//资金用途
	mTvCompanyInfo,//企业信息
	mTvIdeaRepay,//还款来源
	mTvProjectContent;//项目简介
	private InvestmentDetail investmentDetail;//企贷detail详情
	
	private void initView(){
		mTvIdeaRepay = (TextView)infoView.findViewById(R.id.payment_content);
		mTvIdeaHome = (TextView)infoView.findViewById(R.id.operation_analysis_content);
		mTvCapitalOpration = (TextView)infoView.findViewById(R.id.purpose_content);
		mTvCompanyInfo = (TextView)infoView.findViewById(R.id.company_content);
		mTvIdeaCredit = (TextView)infoView.findViewById(R.id.idea_credit_content);
		mTvProjectContent = (TextView)infoView.findViewById(R.id.content_detail_content);
//		mGridView = (GridView)infoView.findViewById(R.id.gridView);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		infoView = inflater.inflate(R.layout.project_info, container, false);
		
		id = this.getArguments().getString("id");
		initView();
		
		
		return infoView;
	}
	
	private void initData(){
		//detail: 详情，返回数据与项目类型相关
		ApiInvestUtils.contentShow(mActivity, id,"detail", new RequestCallback() {
			
			@Override
			public void execute(ParseModel parseModel) {
				
				if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
					investmentDetail = JsonUtils.fromJson(parseModel.getData().toString(), InvestmentDetail.class);
				}else{
					ToastUtils.showToast(mActivity, StringUtils.isEmpty(parseModel.getMsg())?"获取项目详情失败，请稍后重试":parseModel.getMsg());
				}
				setData();
			}
		});
		
		
	}
	
	private void setData() {
		if(investmentDetail!=null){
			infoView.findViewById(R.id.content).setVisibility(View.VISIBLE);
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
			((InvestDetailSecond2Fragment)getParentFragment()).showRiskContent(investmentDetail.getGuarantee_opinion(),investmentDetail.getRisk_control(),investmentDetail.getIdea_risk());
			
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
			infoView.findViewById(R.id.rl_purpose).setVisibility(View.GONE);
			infoView.findViewById(R.id.rl_idea_credit).setVisibility(View.GONE);
			infoView.findViewById(R.id.rl_company).setVisibility(View.GONE);
			infoView.findViewById(R.id.rl_operation_analysis).setVisibility(View.GONE);
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initData();
	}
	
}
