package cn.com.anyitou.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import cn.com.anyitou.R;

import cn.com.anyitou.api.ApiOrderUtils;
import cn.com.anyitou.api.ApiUserUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.entity.User;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.utils.CheckInputUtil;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.ClearEditText;
import cn.com.anyitou.views.LoadingDialog;

public class LoginActivity extends BaseActivity {
	
	private ActionBar mActionBar;
	private ClearEditText mEtUsername,mEtPassword;
	private View mLoginBtn,mRegisterBtn;
	private LoadingDialog loadingDialog;
	private TextView mTvForgetPwd;
	private String username = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_login);
		super.onCreate(savedInstanceState);
		username = this.getIntent().getStringExtra("username");
		mEtUsername.setText(username);
	}
	
	
	@Override
	public void initView() {
		mActionBar = (ActionBar) findViewById(R.id.actionBar);
		onConfigureActionBar(mActionBar);
		mEtUsername = (ClearEditText) findViewById(R.id.username);
		mEtPassword = (ClearEditText) findViewById(R.id.password);
		
		mLoginBtn = findViewById(R.id.login);
		mRegisterBtn = findViewById(R.id.register);
		mTvForgetPwd = (TextView) findViewById(R.id.forgetPwd); 
	}

	protected void onConfigureActionBar(ActionBar actionBar) {
		actionBar.setTitle("登录");
		actionBar.setLeftActionButton(R.drawable.nav_menu, new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				if(!StringUtils.isEmpty(username)){
					Intent intent = new Intent(mContext,MainActivity.class);
					intent.putExtra("showMenu", true);
					startActivity(intent);
					AppManager.getAppManager().finishActivity();
				}else{
					AppManager.getAppManager().finishActivity();
				}
			}
		});
//		actionBar.hideLeftActionButtonText();
	}
	
	@Override
	public void initListener() {
		//忘记密码
		mTvForgetPwd.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				startActivity(BackLoginPassWordActivity.class);
			}
			
		});
		//登录
		mLoginBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				
				final String userName = mEtUsername.getText().toString().trim();
				final String passWord = mEtPassword.getText().toString().trim();
				if(StringUtils.isEmpty(userName)){
					ToastUtils.showToast(mContext, "请输入用户名");
					mEtUsername.requestFocus();
					return;
				}
				if(!CheckInputUtil.checkUser(userName, mContext)){
					return;
				}
				if(StringUtils.isEmpty(passWord)){
					ToastUtils.showToast(mContext, mContext.getResources().getString(R.string.input_pwd));
					mEtPassword.requestFocus();
					return;
				}
				if(!CheckInputUtil.checkPassword(passWord, mContext)){
					return;
				}
				loadingDialog = new LoadingDialog(mContext, "登录中...");
				loadingDialog.show();
				ApiUserUtils.login(mContext, userName, passWord, new RequestCallback() {
					
					@Override
					public void execute(ParseModel parseModel) {
						//loadingDialog.cancel();
						if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){//登录成功
							String token = parseModel.getToken();
							User user = new User();
							user.setUser_name(userName);
							user.setPass_word(passWord);
							logined(token,user);
							getIsHfUser();
							
							/*startActivity(MainActivity.class);
							AppManager.getAppManager().finishActivity();*/
						}else{
							loadingDialog.cancel();
							ToastUtils.showToast(mContext, parseModel.getMsg());
						}
					}
				});
			}
		});
		//注册
		mRegisterBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(LoginActivity.this,RegisteredAccountActivity.class);
				startActivity(intent);
			}
		});
	}
	
	private void getIsHfUser(){
		ApiOrderUtils.getRegisterPayResult(mContext, new RequestCallback() {
			@Override
			public void execute(ParseModel parseModel) {
				loadingDialog.cancel();
				if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){//注册汇付成功
					ToastUtils.showToast(mContext, "登录成功");
					User user = application.getCurrentUser();
					user.setIshfuser("1");
					logined("", user);
					startActivity(MainActivity.class);
					AppManager.getAppManager().finishActivity();
				}else{
					//ToastUtils.showToast(mContext, parseModel.getMsg());
					AppManager.getAppManager().finishActivity();
				}
			}
		});
	}
	

}
