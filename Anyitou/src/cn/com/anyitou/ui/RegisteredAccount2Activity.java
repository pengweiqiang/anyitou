package cn.com.anyitou.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import cn.com.anyitou.R;
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
import cn.com.anyitou.views.LoadingDialog;
/**
 * 注册 (确定密码)
 * @author will
 *
 */
public class RegisteredAccount2Activity extends BaseActivity {
	
	private ActionBar mActionBar;
	private EditText mEtPassword,mEtPassword2;
	private View mRegister;
	private LoadingDialog loadingDialog;
	private CheckBox cbShowPassword;
	
	private String captcha_key = "";//用于验证短信验证码
	private String mobile = "";//手机号
	private String code = "";//验证码
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_registered_account2);
		super.onCreate(savedInstanceState);
		captcha_key = this.getIntent().getStringExtra("captcha_key");
		mobile = this.getIntent().getStringExtra("mobile");
		code = this.getIntent().getStringExtra("code");
		
	}
	
	@Override
	public void initView() {
		mActionBar = (ActionBar) findViewById(R.id.actionBar);
		onConfigureActionBar(mActionBar);
		mEtPassword = (EditText) findViewById(R.id.registered_pw);
		mEtPassword2 = (EditText) findViewById(R.id.registered_pw2);
		mRegister = findViewById(R.id.register); 
		cbShowPassword = (CheckBox)findViewById(R.id.cb_show_password);
	}
	protected void onConfigureActionBar(ActionBar actionBar) {
		actionBar.setTitle("注册帐号");
		actionBar.setLeftActionButton(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				AppManager.getAppManager().finishActivity();
			}
		});
//		actionBar.hideLeftActionButtonText();
		
	}

	@Override
	public void initListener() {
		mActionBar.setLeftActionButton(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AppManager.getAppManager().finishActivity();
			}
		});
		cbShowPassword.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					mEtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
				}else{
					mEtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
				}
			}
		});
		
		//注册
		mRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(StringUtils.isEmpty(mobile)){
					ToastUtils.showToast(mContext, mContext.getResources().getString(R.string.input_phone));
					return;
				}
				if(!CheckInputUtil.checkUser(mobile, mContext)){
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
				
				if(StringUtils.isEmpty(captcha_key)){
					ToastUtils.showToast(mContext, "请先获取验证码");
					return;
				}
				if(StringUtils.isEmpty(code)){
					ToastUtils.showToast(mContext, mContext.getResources().getString(R.string.input_code));
					return;
				}
				/*String phone = mEtRecommended.getText().toString().trim();//推荐人手机号
				if(!StringUtils.isEmpty(phone) && (phone.length()<6 || phone.length()>20)){
					mEtRecommended.requestFocus();
					ToastUtils.showToast(mContext, mContext.getResources().getString(R.string.error_phone));
					return;
				}*/
				String recid = "";//邀请人ID
				String from = "";//渠道来源
				loadingDialog = new LoadingDialog(mContext,"注册中...");
				loadingDialog.show();
				ApiUserUtils.register(mContext, mobile, mobile, passWord, code, captcha_key, recid, from, new RequestCallback() {
					
					@Override
					public void execute(ParseModel parseModel) {
						if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){//注册成功
							login(mobile,passWord);//注册成功调用登录接口
							//ToastUtils.showToast(mContext, parseModel.getMsg());
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
				String accessToken = parseModel.getAccess_token();
				if(!StringUtils.isEmpty(accessToken)){//登陆成功
					ToastUtils.showToast(mContext, "登录成功");
					String refreshToken = parseModel.getRefresh_token();
					User user = new User();
					user.setMobile(userName);
					user.setPassword(MD5Util.MD5(passWord));
					user.setUsername(userName);
					logined(accessToken, refreshToken, user);
//					Intent intent = new Intent(mContext,HomeActivity.class);
//					intent.putExtra("from", "login");
//					startActivity(intent);
//					AppManager.getAppManager().finishActivity();
					Intent intent = new Intent(mContext,GestureLockActivity.class);
					intent.putExtra("type", 3);
					startActivity(intent);
					AppManager.getAppManager().finishActivity(LoginActivity.class);
					
				}else{
//					if(!StringUtils.isEmpty(parseModel.getMsg())){
//						ToastUtils.showToast(mContext, parseModel.getMsg());
//					}else{
//						ToastUtils.showToast(mContext, "登录失败");
//					}
					ToastUtils.showToast(mContext, "用户名或密码错误");
				}
			}
		});
	}
	

}
