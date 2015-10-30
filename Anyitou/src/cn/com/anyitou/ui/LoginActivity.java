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
import cn.com.anyitou.utils.MD5Util;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.ClearEditText;
import cn.com.anyitou.views.LoadingDialog;
/**
 * 登陆
 * @author will
 *
 */
public class LoginActivity extends BaseActivity {
	
	private ActionBar mActionBar;
	private ClearEditText mEtUsername,mEtPassword,mEtCode;
	private View mLoginBtn,mRegisterBtn;
	private LoadingDialog loadingDialog;
	private TextView mTvForgetPwd,mTvCodeMsg;
	private String username = "";
	private boolean isFromMy = false;
	private int type = 0;//2 token失效。重新登陆授权
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_login);
		super.onCreate(savedInstanceState);
		username = this.getIntent().getStringExtra("username");
		isFromMy = this.getIntent().getBooleanExtra("isFromMy", false);
		type = this.getIntent().getIntExtra("type",0);
		if(type == 2){
			ToastUtils.showToast(mContext, "您的账号在其他设备登录，请重新授权登录");
		}
		mEtUsername.setText(username);
	}
	
	
	@Override
	public void initView() {
		mActionBar = (ActionBar) findViewById(R.id.actionBar);
		mActionBar.setTitle("登录");
		mEtUsername = (ClearEditText) findViewById(R.id.username);
		mEtPassword = (ClearEditText) findViewById(R.id.password);
		mEtCode = (ClearEditText) findViewById(R.id.code);
		
		
		mLoginBtn = findViewById(R.id.login);
		mRegisterBtn = findViewById(R.id.register);
		mTvForgetPwd = (TextView) findViewById(R.id.forgetPwd); 
		mTvCodeMsg = (TextView) findViewById(R.id.code_msg);
		
		mTvCodeMsg.setText(StringUtils.getCode());
		
	}
	
	@Override
	public void initListener() {
		mTvCodeMsg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mTvCodeMsg.setText(StringUtils.getCode());
			}
		});
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
				final String code = mEtCode.getText().toString().trim();
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
				if(StringUtils.isEmpty(code)){
					ToastUtils.showToast(mContext, mContext.getResources().getString(R.string.input_code));
					mEtCode.requestFocus();
					return;
				}
				String realCode = mTvCodeMsg.getText().toString().trim();
				if(!realCode.equals(code)){
					ToastUtils.showToast(mContext, mContext.getResources().getString(R.string.input_correct_code));
					mEtCode.requestFocus();
					return;
				}
				loadingDialog = new LoadingDialog(mContext, "登录中...");
				loadingDialog.show();
				ApiUserUtils.login(mContext, userName, passWord, new RequestCallback() {
					
					@Override
					public void execute(ParseModel parseModel) {
						mTvCodeMsg.setText(StringUtils.getCode());
						loadingDialog.cancel();
						String accessToken = parseModel.getAccess_token();
						if(!StringUtils.isEmpty(accessToken)){//登陆成功
							ToastUtils.showToast(mContext, "登录成功");
							String refreshToken = parseModel.getRefresh_token();
							User user = new User();
							user.setMobile(userName);
							user.setPassword(MD5Util.MD5(passWord));
							user.setUsername(userName);
							logined(accessToken, refreshToken, user);
							if(isFromMy){
								setResult(123);
							}
							startActivity(HomeActivity.class);
							AppManager.getAppManager().finishActivity();
						}else{
							if(!StringUtils.isEmpty(parseModel.getMsg())){
								ToastUtils.showToast(mContext, parseModel.getMsg());
							}else{
								ToastUtils.showToast(mContext, "登录失败");
							}
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
//					logined("", user);
					startActivity(HomeActivity.class);
					AppManager.getAppManager().finishActivity();
				}else{
					//ToastUtils.showToast(mContext, parseModel.getMsg());
					AppManager.getAppManager().finishActivity();
				}
			}
		});
	}
	

}
