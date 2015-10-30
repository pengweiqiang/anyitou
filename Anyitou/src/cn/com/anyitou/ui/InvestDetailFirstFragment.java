package cn.com.anyitou.ui;

import java.util.List;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
//import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.com.anyitou.R;

import cn.com.anyitou.entity.LableValue;
import cn.com.anyitou.ui.base.BaseFragment;
import cn.com.anyitou.views.NumberCircleProgressBar;
/**
 * 项目详情第一页
 * @author will
 *
 */
public class InvestDetailFirstFragment extends BaseFragment {

	View infoView;
	private TextView mTvName;
	private TextView mTvRepayStyle;
	private TextView mTvBackDate;
	private List<LableValue> firstPage;
	private TextView mTvYearrate;
	private TextView mTvDate;
	private TextView mTvMoney;
//	private TextView mTvFinish;
	private NumberCircleProgressBar mTvFinish;
	private RelativeLayout mImageBg;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		infoView = inflater.inflate(R.layout.invertment_detail_firstpage, container, false);
		mTvName = (TextView)infoView.findViewById(R.id.tv_detail_name);
		mTvRepayStyle = (TextView)infoView.findViewById(R.id.tv_back_style);
		mTvBackDate = (TextView)infoView.findViewById(R.id.tv_dead_time);
		mTvYearrate = (TextView)infoView.findViewById(R.id.yearrate);
		mTvDate = (TextView)infoView.findViewById(R.id.invest_date);
		mTvMoney = (TextView)infoView.findViewById(R.id.now_money);
		mTvFinish = (NumberCircleProgressBar)infoView.findViewById(R.id.finish);
		mTvFinish.setVisibility(View.INVISIBLE);
		mImageBg = (RelativeLayout)infoView.findViewById(R.id.first_page_content);
		
		/*int width = DeviceInfo.getDisplayMetricsWidth(mActivity);
		double dd = 939d/620d;
		int height = Integer.valueOf(Math.round(dd*width)+"");
		LayoutParams layoutParams = new LayoutParams(width, height);
		mImageBg.setLayoutParams(layoutParams);*/
		
		return infoView;
	}
	public void setFirstPageList(List<LableValue> firstPage){
		this.firstPage = firstPage;
		if(firstPage!=null && !firstPage.isEmpty()){
			StringBuffer sbStrMoney = new StringBuffer();
			String alreadyMoney = "",investMoney = "";
//			for (LableValue lableValue : firstPage) {
//				if("title".equals(lableValue.getLabel())){
//					mTvName.setText(lableValue.getValue());
//				}else if("还款方式".equals(lableValue.getLabel())){
//					mTvRepayStyle.setText(lableValue.getValue());
//				}else if("截止时间".equals(lableValue.getLabel())){
//					mTvBackDate.setText(lableValue.getValue());
//				}else if("年化收益率".equals(lableValue.getLabel())){
//					
//					String value = lableValue.getLabel()+":"+lableValue.getValue();
//					int start = value.indexOf(":")+1;
//					Spannable valueSp  = new SpannableString(value);
//					valueSp.setSpan(new StyleSpan(Typeface.BOLD), 0, value.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//					valueSp.setSpan(new RelativeSizeSpan(1.2f), start, value.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//					valueSp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.invest_detail_text_color)), start, value.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
////					mTvYearrate.setText(Html.fromHtml(lableValue.getLabel()+"："+"<font color='#f7fd45'>"+lableValue.getValue()+"</font>"));
//					mTvYearrate.setText(valueSp);
//				}else if("投资期".equals(lableValue.getLabel())){
//					String value = lableValue.getLabel()+":"+lableValue.getValue();
//					int start = value.indexOf(":")+1;
//					Spannable valueSp  = new SpannableString(value);
//					valueSp.setSpan(new StyleSpan(Typeface.BOLD), 0, value.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//					valueSp.setSpan(new RelativeSizeSpan(1.2f), start, value.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//					valueSp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.invest_detail_text_color)), start, value.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//					mTvDate.setText(valueSp);
////					mTvDate.setText(Html.fromHtml(lableValue.getLabel()+"："+"<font color='#f7fd45'>"+lableValue.getValue()+"</font>"));
//				}else if("融资进度".equals(lableValue.getLabel())){
//					
//					mTvFinish.setProgress(Integer.valueOf(lableValue.getValue()));
//				}else if("已投金额".equals(lableValue.getLabel())){
//					alreadyMoney = lableValue.getValue();
//				}else if("融资金额".equals(lableValue.getLabel())){
//					investMoney =lableValue.getValue();
//				}
//			}
			String value = alreadyMoney+"/"+investMoney+"\n"+"已投金额/融资金额";
			int end = value.indexOf("/");
			Spannable valueSp  = new SpannableString(value);
			valueSp.setSpan(new StyleSpan(Typeface.BOLD), 0, value.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
			valueSp.setSpan(new RelativeSizeSpan(1.2f), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			valueSp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.invest_detail_text_color)), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			mTvFinish.setVisibility(View.VISIBLE);
			mTvMoney.setText(valueSp);
//			mTvMoney.append(Html.fromHtml("<font color='#f7fd45'>"+alreadyMoney+"</font>"+"/"+investMoney+sbStrMoney+"<br/>"+"已投金额/融资金额"));
			
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
