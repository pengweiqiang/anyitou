package cn.com.anyitou.ui;

import java.text.ParseException;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.entity.DebtAssignment;
import cn.com.anyitou.entity.DebtTransfer;
import cn.com.anyitou.entity.DebtTransferDetail;
import cn.com.anyitou.entity.DebtTransferDetail.ProjectData;
import cn.com.anyitou.entity.InVestDetail;
import cn.com.anyitou.entity.Investment;
import cn.com.anyitou.ui.base.BaseFragment;
import cn.com.anyitou.utils.AnyitouUtils;
import cn.com.anyitou.utils.DateUtil;
import cn.com.anyitou.utils.DateUtils;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.TextViewUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ProgressView;
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
	
	//预上线
	String rasieBeginTime;//募集开始时间
	private View mViewOnLineTime;
	private TextView mTvOnlineTime;
	private ProgressView mHourProgress,mMinProgress,mSecondsProgress;
	private int hour ,min,seconds;
	
	//募集中
	private View mViewFundraising;
	private ProgressBar mProgressBar;
	private TextView mTvProgressText;
	
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
		mTvOnlineTime = (TextView)infoView.findViewById(R.id.online_time);
		
		
		mViewFundraising = infoView.findViewById(R.id.fundraising);
		mProgressBar = (ProgressBar)infoView.findViewById(R.id.progressBar);
		mTvProgressText = (TextView)infoView.findViewById(R.id.progress_text);
		
		//预上线的倒计时
		mViewOnLineTime = infoView.findViewById(R.id.count_down);
		mHourProgress = (ProgressView)infoView.findViewById(R.id.hour);
		mMinProgress = (ProgressView)infoView.findViewById(R.id.minute);
		mSecondsProgress = (ProgressView)infoView.findViewById(R.id.seconds);
		
		
		
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
			String rate = investment.getRate_of_interest()+"%";
			SpannableString ss = TextViewUtils.getSpannableStringSize(rate, rate.lastIndexOf("%"), rate.length(), 20);
			mTvYearrate.setText(ss);
			mTvRepayType.setText(investment.getPay_type_info().getName());
			mTvRepayDate.setText(investment.getRepayment_time());
			mTvEndDate.setText(investment.getLeave_time());//截至时间
			mTvInvestMoney.setText(StringUtils.getMoneyFormat(String.valueOf(Double.valueOf(investment.getFinancing_amount())-Double.valueOf(investment.getOver_amount()))));
			mTvFinancingAmount.setText(StringUtils.getMoneyFormat(investment.getFinancing_amount()));
			mTvInvestStatus.setText(investment.getInvest_status_label());
			mTvFinancingDate.setText(investment.getBorrow_days()+"天");//融资期限
			
			String scale = investment.getScale();
			int progress = StringUtils.getProgress(scale);
			mProgressBar.setProgress(progress);
			if(progress<50){
				mTvProgressText.setTextColor(Color.BLACK);
			}
			mTvProgressText.setText("已募集"+progress+"%");
			investTypeShow(investment.getInvest_status());
//			showProgress(investment.getCategory());
		}
		
		if(investDetail!=null){
			mTvInvestName.setText(investDetail.getName());
			String rate = investDetail.getApr()+"%";
			SpannableString ss = TextViewUtils.getSpannableStringSize(rate, rate.lastIndexOf("%"), rate.length(), 20);
			mTvYearrate.setText(ss);//年化率
			mTvRepayType.setText(investDetail.getPay_type_info().getName());
			mTvRepayDate.setText(investDetail.getRepayment_time());//还款日期
//			mTvEndDate.setText("获取哪个值?2");
			//可投金额
			mTvInvestMoney.setText(StringUtils.getMoneyFormat(String.valueOf(Double.valueOf(investDetail.getFinancing_amount())-Double.valueOf(investDetail.getOver_amount()))));
			//融资金额
			mTvFinancingAmount.setText(StringUtils.getMoneyFormat(investDetail.getFinancing_amount()));
//			mTvInvestStatus.setText(investDetail.getInvest_status());
			mTvFinancingDate.setText(investDetail.getBorrow_days()+"天");
			
			String scale = investDetail.getScale();
			int progress = StringUtils.getProgress(scale);
			mProgressBar.setProgress(progress);
			if(progress<50){
				mTvProgressText.setTextColor(Color.BLACK);
			}
			mTvProgressText.setText("已募集"+progress+"%");
			
			investTypeShow(investDetail.getInvest_status());
			rasieBeginTime = investDetail.getRaise_begin_time();
			showProgress(investDetail.getCategory());
		}
		
	}
	/**
	 * 展示倒计时
	 */
	private Timer mTimer;
	private void showProgress(String category){
		if("reward".equals(category)){
			boolean isBeforeNw = true;
			try {
				isBeforeNw = DateUtils.beforeNow(DateUtil.getDate(rasieBeginTime, DateUtil.DEFAULT_PATTERN));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if(!StringUtils.isEmpty(rasieBeginTime) && !isBeforeNw){//募集开始时间
				int times []= DateUtils.getTimeLong(rasieBeginTime);
				mTvOnlineTime.setText("上线时间："+DateUtil.getDateString(rasieBeginTime, DateUtil.DEFAULT_PATTERN, DateUtil.DAY_PATTERN));
				mViewOnLineTime.setVisibility(View.VISIBLE);
				mViewFundraising.setVisibility(View.GONE);
				
				hour = times[0];
				mHourProgress.setScore(hour,"时",100);
				
				min = times[1];
				mMinProgress.setScore(min,"分",60);
				
				seconds = times[2];
				mSecondsProgress.setScore(seconds,"秒",60);
				mTimer = new Timer();
				setTimerTask();
			}
		}else{
			mViewOnLineTime.setVisibility(View.GONE);
			mViewFundraising.setVisibility(View.VISIBLE);
		}
	}
	
	private void setTimerTask(){
		mTimer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				Message msg =  timerHander.obtainMessage();
				msg.what = 1;
				timerHander.sendMessage(msg);
			}
		}, 1000,1000);
	}
	 /** 
     * do some action 
     */  
    private Handler timerHander = new Handler() {  
        @Override  
        public void handleMessage(Message msg) {  
            super.handleMessage(msg);  
            int msgId = msg.what;  
            switch (msgId) {  
                case 1:  
                	if(seconds == 1){
                		
                		if(min == 0){
                			if(hour == 0){
                				//停止
                				seconds=0;
                				mTimer.cancel();
                				ToastUtils.showToast(mActivity, "上线了");
                				((InVestmentDetailActivity)mActivity).online();
                			}else{
                				hour--;
                				min = 59;
                				seconds = 60;
                				mMinProgress.setCurrentCount(min);
                				mHourProgress.setCurrentCount(hour);
                			}
                			
                		}else{
                			min--;
                			seconds = 60;
                			mMinProgress.setCurrentCount(min);
                		}
                	}else{
                		seconds --;
                	}
                	mSecondsProgress.setCurrentCount(seconds);
                    break;  
                default:  
                    break;  
            }  
        }  
    };  
	
	
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(mTimer!=null){
			mTimer.cancel();
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
			String rate = debtAssignment.getBuyer_apr()+"%";
			SpannableString ss = TextViewUtils.getSpannableStringSize(rate, rate.lastIndexOf("%"), rate.length(), 20);
			mTvYearrate.setText(ss);
			mTvRepayType.setText("");
			mTvRepayDate.setText(debtAssignment.getRepayment_time());
			mTvEndDate.setText(debtAssignment.getSell_end_time());
			
//			mTvInvestMoney.setText(StringUtils.getMoneyFormat(debtAssignment.getRemainAmount()));
			mTvInvestMoney.setText(debtAssignment.getAmount());
			//可认购份额
			mTvFinancingAmount.setText(StringUtils.isEmpty(debtAssignment.getRemainAmount())?"0":debtAssignment.getRemainAmount()+"份");
			mTvInvestStatus.setText(debtAssignment.getStatus());
			mTvFinancingDate.setText(debtAssignment.getSell_end_time());
			
			String scale = debtAssignment.getBuyProgress();
			int scaleProgress = StringUtils.getProgress(scale);
			mProgressBar.setProgress(scaleProgress);
			if(scaleProgress<50){
				mTvProgressText.setTextColor(Color.BLACK);
			}
			mTvProgressText.setText("已认购"+scaleProgress+"%");
			showProgress("");
		}
		
		if(debtDetail!=null){
			ProjectData projectData = debtDetail.getProjectData();
			DebtTransfer debtData = debtDetail.getDebtData();
			
			mTvInvestName.setText(projectData.getItem_title());
			
			String rate = debtData.getBuyer_apr()+"%";
			SpannableString ss = TextViewUtils.getSpannableStringSize(rate, rate.lastIndexOf("%"), rate.length(), 20);
			mTvYearrate.setText(ss);
			
			mTvRepayType.setText(projectData.getPay_type_info().getName());
			mTvRepayDate.setText(projectData.getRepayment_time());
			mTvEndDate.setText(debtData.getSell_end_time());
			//转让份额
			mTvInvestMoney.setText(debtData.getAmount()+"份");
			mTvFinancingAmount.setText(StringUtils.isEmpty(debtData.getRemainAmount())?"0":debtData.getRemainAmount()+"份");
			mTvInvestStatus.setText(AnyitouUtils.getDebtStatusName(debtData.getStatus()));
			mTvFinancingDate.setText(debtData.getSell_end_time());
//			showProgress("");
//			String scale = debtData.getBuyProgress();
//			mProgressBar.setProgress(StringUtils.getProgress(scale));
//			mTvProgressText.setText("已认购"+StringUtils.getProgress(scale)+"%");
			
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
