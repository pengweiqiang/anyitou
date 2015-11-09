package cn.com.anyitou.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.entity.DebtAssignment;
import cn.com.anyitou.entity.DebtTransfer;
import cn.com.anyitou.entity.DebtTransferDetail;
import cn.com.anyitou.entity.DebtTransferDetail.ProjectData;
import cn.com.anyitou.entity.InVestDetail;
import cn.com.anyitou.entity.Investment;
import cn.com.anyitou.ui.base.BaseFragment;
import cn.com.anyitou.utils.AnyitouStatusUtils;
import cn.com.anyitou.utils.StringUtils;
//import android.text.style.AbsoluteSizeSpan;
/**
 * 项目详情第一页
 * @author will
 *
 */
public class InvestDetailFirstFragment extends BaseFragment {

	View infoView;
	private TextView mTvInvestName;//投资名称
	private TextView mTvYearrate;
	private ImageView mIvRepayingLogo;//还款中
	private ImageView mIvPreOnlineLogo;//预上线
	
	private TextView mTvRepayType;//还款方式
	private View mViewFinancingDate;
	private TextView mTvRepayDate;//还款日期
	private TextView mTvEndDate;//截至日期
	private TextView mTvInvestMoney;//可投金额
	private TextView mTvFinancingAmount;//融资金额
	private TextView mTvInvestStatus;//项目状态
	private TextView mTvFinancingDate;//融资期限
	
	private TextView mTvDealLine,mTvMoneyTitle,mTvLastTitle;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		infoView = inflater.inflate(R.layout.invertment_detail_firstpage, container, false);
		mTvInvestName = (TextView)infoView.findViewById(R.id.invest_name);
		mTvYearrate = (TextView)infoView.findViewById(R.id.rate);
		mTvRepayType = (TextView)infoView.findViewById(R.id.repay_type);
		mViewFinancingDate = infoView.findViewById(R.id.financing_date_rl);
		mTvRepayDate = (TextView)infoView.findViewById(R.id.repay_date);
		mTvEndDate = (TextView)infoView.findViewById(R.id.end_date);
		mTvInvestMoney = (TextView)infoView.findViewById(R.id.invest_money);
		mTvFinancingAmount = (TextView)infoView.findViewById(R.id.financing_money);
		mTvInvestStatus = (TextView)infoView.findViewById(R.id.invest_status);
		mTvFinancingDate = (TextView)infoView.findViewById(R.id.financing_date);
		mTvDealLine = (TextView)infoView.findViewById(R.id.deadline_title);
		mTvMoneyTitle = (TextView)infoView.findViewById(R.id.money_title);
		mTvLastTitle = (TextView)infoView.findViewById(R.id.last_title);
		
		
		mIvRepayingLogo = (ImageView)infoView.findViewById(R.id.repaying_logo);
		mIvPreOnlineLogo = (ImageView)infoView.findViewById(R.id.pre_on_line_logo);
		
		Investment investment = (Investment)this.getArguments().getSerializable("investment");
		setFirstPageInvestList(investment,null);
		
		DebtAssignment debtAssignment = (DebtAssignment)this.getArguments().getSerializable("debt");
		setFirstPageDebtList(debtAssignment,null);
		
		return infoView;
	}
	
	
	/**
	 * 投资显示
	 */
	InVestDetail investDetail;
	public void setFirstPageInvestList(Investment investment,InVestDetail investDetail){
		this.investDetail = investDetail;
		if(investment!=null){
			mTvInvestName.setText(investment.getItem_title());
			mTvYearrate.setText(investment.getRate_of_interest()+"%");
			mTvRepayType.setText(investment.getPay_type_info().getName());
			mTvRepayDate.setText(investment.getRepayment_time());
			mTvEndDate.setText(investment.getLeave_time());//截至时间
			mTvInvestMoney.setText(StringUtils.getMoneyFormat(investment.getRemain_amount()));
			mTvFinancingAmount.setText(StringUtils.getMoneyFormat(investment.getFinancing_amount()));
			mTvInvestStatus.setText(investment.getInvest_status_label());
			mTvFinancingDate.setText(investment.getBorrow_days()+"天");//融资期限
			investTypeShow(investment.getInvest_status());
		}
		
		if(investDetail!=null){
			mTvInvestName.setText(investDetail.getName());
			mTvYearrate.setText(investDetail.getApr()+"%");
			mTvRepayType.setText(investDetail.getPay_type_info().getName());
			mTvRepayDate.setText(investDetail.getRepayment_time());
//			mTvEndDate.setText("获取哪个值?2");
			//可投金额
			mTvInvestMoney.setText(StringUtils.getMoneyFormat(investDetail.getInvestment()));
			mTvFinancingAmount.setText(StringUtils.getMoneyFormat(investDetail.getFinancing_amount()));
//			mTvInvestStatus.setText(investDetail.getInvest_status());
			mTvFinancingDate.setText(investDetail.getBorrow_days()+"天");
			investTypeShow(investDetail.getInvest_status());
		}
		
	}
	
	/**
	 * 按不同投资状态显示
	 * // 投资状态： 0:未开放  1:开放投资  2:募集完成   3:还款中   4:还款完成   5:逾期
	 */
	private void investTypeShow(String investStatus){
//		try{
		int status = Integer.valueOf(investStatus);
		
//		}catch(Exception e){
//			e.printStackTrace();
//		}
		switch (status) {
		case 0://投资预上线
			mIvPreOnlineLogo.setVisibility(View.VISIBLE);
			mViewFinancingDate.setVisibility(View.GONE);
			break;
//		case 2://募集中	
//			
//			break;
		case 3://还款中
			mIvRepayingLogo.setVisibility(View.VISIBLE);
			break;
		default:
			mIvPreOnlineLogo.setVisibility(View.GONE);
			mViewFinancingDate.setVisibility(View.VISIBLE);
			break;
		}
	}
	
	/*
	 * 债权显示
	 */
	private DebtTransferDetail debtDetail;
	public void setFirstPageDebtList(DebtAssignment debtAssignment,DebtTransferDetail debtDetail){
		if(debtAssignment == null && debtDetail == null){
			return ;
		}
		this.debtDetail = debtDetail;
		mTvDealLine.setText("剩余期限：");
		mTvMoneyTitle.setText("转让份额：");
		mTvLastTitle.setText("可认购份额：");
		if(debtAssignment!=null){
			mTvInvestName.setText(debtAssignment.getNumber());
			mTvYearrate.setText(debtAssignment.getBuyer_apr()+"%");
			mTvRepayType.setText("");
			mTvRepayDate.setText(debtAssignment.getRepayment_time());
			mTvEndDate.setText(debtAssignment.getSell_end_time());
			
			mTvInvestMoney.setText(StringUtils.getMoneyFormat(debtAssignment.getRemainAmount()));
			mTvFinancingAmount.setText(StringUtils.getMoneyFormat(debtAssignment.getTransferred_amount()));
			mTvInvestStatus.setText(debtAssignment.getStatus());
			mTvFinancingDate.setText("获取哪个值？");
		}
		
		if(debtDetail!=null){
			ProjectData projectData = debtDetail.getProjectData();
			DebtTransfer debtData = debtDetail.getDebtData();
			
			mTvInvestName.setText(projectData.getItem_title());
			mTvYearrate.setText(debtData.getBuyer_apr()+"%");
			
			mTvRepayType.setText("获取哪个值");
			mTvRepayDate.setText(projectData.getRepayment_time());
			mTvEndDate.setText(debtData.getSell_end_time());
			mTvInvestMoney.setText(debtData.getTransferred_num()+"份");
			mTvFinancingAmount.setText(debtData.getAmount());
			mTvInvestStatus.setText(AnyitouStatusUtils.getDebtStatusName(debtData.getStatus()));
			mTvFinancingDate.setText("获取哪个值？");
		}
		
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
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
