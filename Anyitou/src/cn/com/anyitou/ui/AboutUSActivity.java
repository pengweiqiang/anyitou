package cn.com.anyitou.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import cn.com.anyitou.R;

import cn.com.gson.reflect.TypeToken;
import cn.com.anyitou.api.ApiHomeUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.commons.Constant;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.entity.TitleSection;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.JsonUtils;
import cn.com.anyitou.utils.SharePreferenceManager;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.LoadingDialog;

public class AboutUSActivity extends BaseActivity {
	
	private ActionBar mActionBar;
	private TextView mTvAboutUs;
	private LoadingDialog loadingDialog;
	private List<TitleSection> titleSections = new ArrayList<TitleSection>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_about_us);
		super.onCreate(savedInstanceState);
		
		initData();
	}
	private void initData(){
		loadingDialog = new LoadingDialog(mContext);
		loadingDialog.show();
		ApiHomeUtils.getAboutUs(mContext, new RequestCallback() {
			
			@Override
			public void execute(ParseModel parseModel) {
				loadingDialog.cancel();
				if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getStatus())){
					titleSections = JsonUtils.fromJson(parseModel.getData().toString(), new TypeToken<List<TitleSection>>() {});
					if(titleSections!=null && !titleSections.isEmpty()){
						StringBuffer sbData = new StringBuffer();
						int index = 0;
						for (TitleSection titleSection : titleSections) {
							if("small_title".equals(titleSection.getType())){
								if(index != titleSections.size()-1){
									sbData.append(titleSection.getContent()+"<BR><BR>");
								}else{
									sbData.append(titleSection.getContent()+"<BR>");
								}
							}else if("section".equals(titleSection.getType())){
								if(index != titleSections.size()-1){
									sbData.append(titleSection.getContent()+"<BR><BR>");
								}else{
									sbData.append(titleSection.getContent()+"<BR>");
								}
							}
							index ++;
						}
						mTvAboutUs.setText(Html.fromHtml(sbData.toString()));
						SharePreferenceManager.saveBatchSharedPreference(mContext, Constant.FILE_NAME, "aboutus", sbData.toString());
					}
//					String data = parseModel.getData().toString();
					
				}else{
					ToastUtils.showToast(mContext, parseModel.getDesc());
					String aboutUs = (String)SharePreferenceManager.getSharePreferenceValue(mContext, Constant.FILE_NAME, "aboutus", "");
					if(!StringUtils.isEmpty(aboutUs)){
						mTvAboutUs.setText(Html.fromHtml(aboutUs));
					}
				}
			}
		});
		
	}
	
	@Override
	public void initView() {
		mTvAboutUs = (TextView)findViewById(R.id.about_us_content);
		mActionBar = (ActionBar)findViewById(R.id.actionBar);
		mActionBar.setTitle("关于我们");
		mActionBar.setLeftActionButton(R.drawable.btn_back, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AppManager.getAppManager().finishActivity();
			}
		});
	}

	@Override
	public void initListener() {
		
	}

}
