package cn.com.anyitou.ui;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.com.anyitou.R;

import cn.com.anyitou.entity.LableValue;
import cn.com.anyitou.ui.base.BaseFragment;
import cn.com.anyitou.utils.StringUtils;
/**
 * 风险控制
 * @author will
 *
 */
public class InvestDetailSecondRiskFragment extends BaseFragment {

	View infoView;
	private TextView mTvRisk;
	private List<LableValue> secondePage;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		infoView = inflater.inflate(R.layout.risk_control, container, false);
		mTvRisk = (TextView)infoView.findViewById(R.id.project_risk);
		
		
		return infoView;
	}
	public void setSecondeList(List<LableValue> secondPage){
		this.secondePage = secondPage;
		if(secondPage!=null && !secondPage.isEmpty()){
			StringBuffer titleSb = new StringBuffer();
			for (LableValue lableValue : secondPage) {
				titleSb.append("<font color='#646464'>"+lableValue.getLabel()+"</font>"+"<BR/><BR/>"+lableValue.getValue()+"<BR/><BR/>");
			}
			if(!StringUtils.isEmpty(titleSb.toString())){
				mTvRisk.setText(Html.fromHtml(titleSb.subSequence(0, titleSb.lastIndexOf("<BR/>")).toString()));
			}else{
				mTvRisk.setText(titleSb);
			}
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
		setSecondeList(secondePage);
	}

	@Override
	public void onPause() {
		super.onPause();
	}
	
}
