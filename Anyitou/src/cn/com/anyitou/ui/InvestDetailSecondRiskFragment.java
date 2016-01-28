package cn.com.anyitou.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.ui.base.BaseFragment;
import cn.com.anyitou.utils.StringUtils;
/**
 * 风险控制
 * @author will
 *
 */
public class InvestDetailSecondRiskFragment extends BaseFragment {

	View infoView;
	private TextView mTvGuaranteeOp,mTvRisk,mTvIdeaRisk;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		infoView = inflater.inflate(R.layout.risk_control, container, false);
		mTvGuaranteeOp = (TextView)infoView.findViewById(R.id.guarantee_opinion_content);
		mTvRisk = (TextView)infoView.findViewById(R.id.risk_content);
		mTvIdeaRisk = (TextView)infoView.findViewById(R.id.idea_risk_content);
		
		return infoView;
	}
	public void setRiskContent(String guaranteeOp,String riskContent,String ideaRisk){
		//担保机构
		if(StringUtils.isEmpty(guaranteeOp)){
			infoView.findViewById(R.id.rl_guarantee_opinion).setVisibility(View.GONE);
		}else{
			mTvGuaranteeOp.setText("\t"+Html.fromHtml(guaranteeOp));
		}
		//风险控制
		if(StringUtils.isEmpty(riskContent)){
			infoView.findViewById(R.id.rl_risk).setVisibility(View.GONE);
		}else{
			mTvRisk.setText("\t"+Html.fromHtml(riskContent));
		}
		if(StringUtils.isEmpty(ideaRisk)){
			infoView.findViewById(R.id.rl_idea_risk).setVisibility(View.GONE);
		}else{
			mTvIdeaRisk.setText("\t"+Html.fromHtml(ideaRisk));
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
