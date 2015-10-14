package cn.com.anyitou.ui;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import cn.com.anyitou.R;

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
import cn.com.anyitou.views.LoadingDialog;

public class RegisteredAccountActivity extends BaseActivity {
	
	private ActionBar mActionBar;
	private EditText mEtUserName,mEtPassword,mEtPassword2,mEtCode,mEtRecommended;
	private View mGetCode,mRegister,mHasLogin,mReaded;
	private LoadingDialog loadingDialog;
	private TextView mTvGetCode;
	
	private String sessionId = "";//用于验证短信验证码
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_registered_account);
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public void initView() {
		mActionBar = (ActionBar) findViewById(R.id.actionBar);
		onConfigureActionBar(mActionBar);
		mEtUserName = (EditText) findViewById(R.id.registered_un);
		mEtPassword = (EditText) findViewById(R.id.registered_pw);
		mEtPassword2 = (EditText) findViewById(R.id.registered_pwd2);
		mEtCode = (EditText) findViewById(R.id.registered_code);
		mEtRecommended = (EditText) findViewById(R.id.registered_recommend);
		mGetCode =  findViewById(R.id.get_code);
		mTvGetCode = (TextView)findViewById(R.id.get_code_tv);
		mRegister = findViewById(R.id.register); 
		mHasLogin = findViewById(R.id.has_login);
		mReaded = findViewById(R.id.readed);
	}
	protected void onConfigureActionBar(ActionBar actionBar) {
		actionBar.setTitle("注册帐号");
		actionBar.setLeftActionButton(R.drawable.btn_back, new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				AppManager.getAppManager().finishActivity();
			}
		});
//		actionBar.hideLeftActionButtonText();
		
	}

	@Override
	public void initListener() {
		//已有帐号登录
		mHasLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext,LoginActivity.class);
				startActivity(intent);
				AppManager.getAppManager().finishActivity();
			}
		});
		//同意协议
		mReaded.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(AgreementActivity.class);
			}
		});
		//获取验证码
		mGetCode.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String tel = mEtUserName.getText().toString().trim();
				if(StringUtils.isEmpty(tel)){
					ToastUtils.showToast(mContext, mContext.getResources().getString(R.string.input_phone));
					mEtUserName.requestFocus();
					return;
				}
				
				if(CheckInputUtil.checkPhone(tel, mContext)){
					if(mGetCode.isEnabled()){
						mGetCode.setEnabled(false);
						ApiUserUtils.registerCode(mContext, tel, new RequestCallback() {
							
							@Override
							public void execute(ParseModel parseModel) {
								if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){//注册成功
									regainCode();
									sessionId = parseModel.getSession_id();
								}else{
									mGetCode.setEnabled(true);
									ToastUtils.showToast(mContext, parseModel.getMsg());
								}
							}
						});
					}
				}
				
			}
		});
		
		//注册
		mRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final String userName = mEtUserName.getText().toString().trim();//手机号码
				if(StringUtils.isEmpty(userName)){
					ToastUtils.showToast(mContext, mContext.getResources().getString(R.string.input_phone));
					mEtUserName.requestFocus();
					return;
				}
				if(!CheckInputUtil.checkUser(userName, mContext)){
					return;
				}
				final String passWord = mEtPassword.getText().toString().trim();//密码
				if(StringUtils.isEmpty(passWord)){
					ToastUtils.showToast(mContext, mContext.getResources().getString(R.string.input_pwd));
					mEtPassword.requestFocus();
					return;
				}
				if(!CheckInputUtil.checkPassword(passWord, mContext)){
					return;
				}
				
				final String passWord2 = mEtPassword2.getText().toString().trim();//确认密码
				if(StringUtils.isEmpty(passWord2)){
					ToastUtils.showToast(mContext, mContext.getResources().getString(R.string.input_correct_pwd));
					mEtPassword2.requestFocus();
					return;
				}
				/*if(!CheckInputUtil.checkPassword(passWord2, mContext)){
					return;
				}*/
				if(!passWord.equals(passWord2)){
					ToastUtils.showToast(mContext, mContext.getResources().getString(R.string.error_pwd_again));
					mEtPassword2.requestFocus();
					return;
				}
				
				/*String tel = mEtPhone.getText().toString().trim();
				if(StringUtils.isEmpty(tel)){
					ToastUtils.showToast(mContext, mContext.getResources().getString(R.string.input_phone));
					mEtPhone.requestFocus();
					return;
				}
				if(!CheckInputUtil.checkPhone(tel, mContext)){
					return;
				}*/
				
				if(StringUtils.isEmpty(sessionId)){
					ToastUtils.showToast(mContext, "请先获取验证码");
					mEtCode.requestFocus();
					return;
				}
				String authCode = mEtCode.getText().toString().trim();
				if(StringUtils.isEmpty(authCode)){
					ToastUtils.showToast(mContext, mContext.getResources().getString(R.string.input_code));
					mEtCode.requestFocus();
					return;
				}
				/*String phone = mEtRecommended.getText().toString().trim();//推荐人手机号
				if(!StringUtils.isEmpty(phone) && (phone.length()<6 || phone.length()>20)){
					mEtRecommended.requestFocus();
					ToastUtils.showToast(mContext, mContext.getResources().getString(R.string.error_phone));
					return;
				}*/
				String choose = "1";//是否同意协议
				loadingDialog = new LoadingDialog(mContext,"注册中...");
				loadingDialog.show();
				ApiUserUtils.register(mContext, sessionId, authCode, userName, passWord, userName, "", choose, new RequestCallback() {
					
					@Override
					public void execute(ParseModel parseModel) {
						if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){//注册成功
							login(userName,passWord);//注册成功调用登录接口
						}else{
							ToastUtils.showToast(mContext, parseModel.getMsg());
						}
					}
				});
			}
		});
	}
	/**
	 * 登录
	 * @param userName
	 * @param passWord
	 */
	private void login(final String userName,final String passWord){
		ApiUserUtils.login(mContext, userName, passWord, new RequestCallback() {
			
			@Override
			public void execute(ParseModel parseModel) {
				loadingDialog.cancel();
				if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){//登录成功
					String token = parseModel.getToken();
					User user = new User();
					user.setUser_name(userName);
					user.setPass_word(passWord);
					logined(token,user);
					ToastUtils.showToast(mContext, "注册成功");
					Intent intent = new Intent(mContext,RegisteredActivity.class);
					startActivity(intent);
					AppManager.getAppManager().finishActivity(LoginActivity.class);
					AppManager.getAppManager().finishActivity();
				}else{
					ToastUtils.showToast(mContext, parseModel.getMsg());
				}
			}
		});
	}
	
	private Timer timer;// 计时器
	private int time = 120;//倒计时120秒

	private void regainCode() {
		time = 120;
		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				handler.sendEmptyMessage(time--);
			}
		}, 0, 1000);
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0) {
				mGetCode.setEnabled(true);
				mTvGetCode.setText("获取验证码");
				timer.cancel();
			} else {
				mTvGetCode.setText(msg.what + "秒重发");
			}
		};
	};

}
