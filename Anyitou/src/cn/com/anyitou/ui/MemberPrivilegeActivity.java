package cn.com.anyitou.ui;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.api.ApiUserUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.Grades;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.JsonUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.LoadingDialog;

/**
 * 会员特权
 * 
 * @author will
 * 
 */
public class MemberPrivilegeActivity extends BaseActivity {

	ActionBar mActionBar;
	LoadingDialog loadingDialog;
	private Grades grades;
	private TextView mTvInfo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_member_privilege);
		super.onCreate(savedInstanceState);
		
		initData();
	}

	// 设置activity的导航条
	protected void onConfigureActionBar(ActionBar actionBar) {
		actionBar.setTitle("会员特权");
	}

	@Override
	public void initView() {
		mActionBar = (ActionBar)findViewById(R.id.actionBar);
		onConfigureActionBar(mActionBar);
		mTvInfo = (TextView) findViewById(R.id.info);
	}
	private void showData(){
		if(grades!=null){
			mTvInfo.setText(Html.fromHtml(grades.getInfo()));
		}
	}
	private void initData(){
		loadingDialog = new LoadingDialog(mContext);
		loadingDialog.show();
		ApiUserUtils.getGrades(mContext, new RequestCallback() {
			
			@Override
			public void execute(ParseModel parseModel) {
				loadingDialog.cancel();
				if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
					grades = JsonUtils.fromJson(parseModel.getData().toString(), Grades.class);
					showData();
				}else{
					ToastUtils.showToast(mContext, parseModel.getMsg());
				}
			}
		});
		
	}
	@Override
	public void initListener() {
		mActionBar.setLeftActionButton(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AppManager.getAppManager().finishActivity();
			}
		});
		
	}

}
