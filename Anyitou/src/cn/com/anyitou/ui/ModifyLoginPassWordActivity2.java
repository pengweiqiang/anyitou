package cn.com.anyitou.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import cn.com.anyitou.R;
import cn.com.anyitou.api.ApiUserUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.utils.CheckInputUtil;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.InfoDialog;
import cn.com.anyitou.views.LoadingDialog;
/**
 * 
 * @author will
 * 修改密码--》第二步
 *
 */
public class ModifyLoginPassWordActivity2 extends BaseActivity {
	ActionBar actionBar;
	private EditText mEtOldPwd,mEtNewPwd,mEtNewPwd2;
	private View mBtnConfirm;
	LoadingDialog loadingDialog;
	String captchaKey = "";
	String code = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_modify_loginpassword2);
		super.onCreate(savedInstanceState);
		
		captchaKey = this.getIntent().getStringExtra("captchaKey");
		code = this.getIntent().getStringExtra("code");
	}
	
	@Override
	public void initView() {
		actionBar = (ActionBar)findViewById(R.id.actionBar);
		actionBar.setTitle("修改密码");
		
		mEtOldPwd = (EditText)findViewById(R.id.old_pwd);
		mEtNewPwd = (EditText)findViewById(R.id.new_pwd);
		mEtNewPwd2 = (EditText)findViewById(R.id.new_pwd2);
		mBtnConfirm = findViewById(R.id.btn_confirm);
	}

	@Override
	public void initListener() {
		actionBar.setLeftActionButton(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AppManager.getAppManager().finishActivity();
			}
		});
		
		mBtnConfirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String oldPwd = mEtOldPwd.getText().toString().trim();
				
				if(StringUtils.isEmpty(oldPwd)){
					ToastUtils.showToast(mContext, mContext.getResources().getString(R.string.input_old_pwd));
					mEtOldPwd.requestFocus();
					return;
				}
				if(!CheckInputUtil.checkPassword(oldPwd, mContext)){
					return;
				}
				
				String newPwd = mEtNewPwd.getText().toString().trim();
				
				if(StringUtils.isEmpty(newPwd)){
					ToastUtils.showToast(mContext, mContext.getResources().getString(R.string.input_new_pwd));
					mEtNewPwd.requestFocus();
					return;
				}
				if(!CheckInputUtil.checkPassword(newPwd, mContext)){
					return;
				}
				
				String newPwd2 = mEtNewPwd2.getText().toString().trim();
				if(StringUtils.isEmpty(newPwd2)){
					ToastUtils.showToast(mContext, mContext.getResources().getString(R.string.input_correct_pwd));
					mEtNewPwd2.requestFocus();
					return;
				}
				if(!CheckInputUtil.checkPassword(newPwd2, mContext)){
					return;
				}
				if(!newPwd.equals(newPwd2)){
					ToastUtils.showToast(mContext, mContext.getResources().getString(R.string.error_pwd_again));
					mEtNewPwd2.requestFocus();
					return;
				}
				
				
				loadingDialog = new LoadingDialog(mContext,"修改密码中...");
				loadingDialog.show();
				ApiUserUtils.updatePwd(mContext, oldPwd, newPwd2,captchaKey,code, new RequestCallback() {
					
					@Override
					public void execute(ParseModel parseModel) {
						loadingDialog.cancel();
						if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
							showUpdateSuccess();
						}else{
							ToastUtils.showToast(mContext, StringUtils.isEmpty(parseModel.getMsg())?"修改失败,稍后请重试":parseModel.getMsg());
						}
					}
				});
				
				
			}
		});
	}
	
	private void showUpdateSuccess(){
		InfoDialog.Builder builder = new InfoDialog.Builder(mContext,R.layout.info_dialog);
		builder.setMessage("密码修改成功,请记住您的新密码");
		builder.setTitle("修改成功");
		builder.setButton1("我知道了", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				logOut();
				startActivity(LoginActivity.class);
				AppManager.getAppManager().finishActivity();
			}
		},R.drawable.btn_concer_dialog_selector);
		builder.setButton2(null, null);
		builder.create().show();
	}

}
