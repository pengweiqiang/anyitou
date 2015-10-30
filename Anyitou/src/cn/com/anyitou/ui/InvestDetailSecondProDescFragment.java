package cn.com.anyitou.ui;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.com.anyitou.R;

import cn.com.anyitou.entity.LableValue;
import cn.com.anyitou.ui.base.BaseFragment;
import cn.com.anyitou.utils.StringUtils;
/**
 * 项目描述
 * @author will
 *
 */
public class InvestDetailSecondProDescFragment extends BaseFragment {

	View infoView;
	TextView mTvTitle;
	TextView mTvDetail;
	private List<LableValue> secondPage;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		infoView = inflater.inflate(R.layout.project_info, container, false);
		mTvTitle = (TextView)infoView.findViewById(R.id.first_textview);
		mTvDetail = (TextView)infoView.findViewById(R.id.project_detail);
		return infoView;
	}

	public void setSecondeList(List<LableValue> secondPage){
		this.secondPage = secondPage;
		if(secondPage!=null && !secondPage.isEmpty()){
			StringBuffer titleSb = new StringBuffer();
			for(LableValue lableValue:secondPage){
//				if("detail".equals(lableValue.getLabel()) ||"detial".equals(lableValue.getLabel())){
//					mTvDetail.setText("\t"+StringUtils.ToDBC(lableValue.getValue())+"\n");
//				}else{
//					titleSb.append(lableValue.getLabel()+":   "+lableValue.getValue()+"\n");
//				}
			}
			if(!StringUtils.isEmpty(titleSb.toString())){
				mTvTitle.setText(titleSb.substring(0, titleSb.lastIndexOf("\n")));
			}else{
				mTvTitle.setText(titleSb);
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
		setSecondeList(secondPage);
	}

	@Override
	public void onPause() {
		super.onPause();
	}
	
}
