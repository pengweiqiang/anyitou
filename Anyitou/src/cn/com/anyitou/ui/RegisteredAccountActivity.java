package cn.com.anyitou.ui;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.api.ApiUserUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.api.constant.OperationType;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.utils.CheckInputUtil;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.TextViewUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.LoadingDialog;
/**
 * 注册
 * @author will
 *
 */
public class RegisteredAccountActivity extends BaseActivity {
	
	private ActionBar mActionBar;
	private EditText mEtUserName,mEtCode,mEtRecommended;
	private View mRegister/*,mHasLogin*/,mReaded;
	private LoadingDialog loadingDialog;
	private TextView mTvGetCode/*,mTvTime*/;
	
	private String captcha_key = "";//验证码key
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
		mEtCode = (EditText) findViewById(R.id.registered_code);
		mEtRecommended = (EditText) findViewById(R.id.registered_recommend);
		mTvGetCode =  (TextView)findViewById(R.id.get_code);
//		mTvTime = (TextView)findViewById(R.id.time);
		mRegister = findViewById(R.id.register); 
//		mHasLogin = findViewById(R.id.has_login);
		mReaded = findViewById(R.id.readed);
	}
	protected void onConfigureActionBar(ActionBar actionBar) {
		actionBar.setTitle("注册帐号");
		actionBar.setLeftActionButton( new OnClickListener() {
			
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
		//同意协议
		mReaded.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(AgreementActivity.class);
			}
		});
		//获取验证码
		mTvGetCode.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String tel = mEtUserName.getText().toString().trim();
				if(StringUtils.isEmpty(tel)){
					ToastUtils.showToast(mContext, mContext.getResources().getString(R.string.input_phone));
					mEtUserName.requestFocus();
					return;
				}
				if(CheckInputUtil.checkPhone(tel, mContext)){
					if(mTvGetCode.isEnabled()){
						mTvGetCode.setEnabled(false);
						loadingDialog = new LoadingDialog(mContext);
						loadingDialog.show();
						ApiUserUtils.sendMobileCode(mContext, tel,OperationType.APP_REGISTER.getName(), new RequestCallback() {
							
							@Override
							public void execute(ParseModel parseModel) {
								if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){//发送验证码成功
									regainCode();
									mEtCode.requestFocus();
									captcha_key = parseModel.getData().getAsJsonObject().get("captcha_key").getAsString();
								}else{
									mTvGetCode.setEnabled(true);
									ToastUtils.showToast(mContext, parseModel.getMsg());
								}
								loadingDialog.cancel();
							}
						});
					}
				}
				
			}
		});
		
		//下一步
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
				
				if(StringUtils.isEmpty(captcha_key)){
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
				Intent intent = new Intent(mContext,RegisteredAccount2Activity.class);
				intent.putExtra("mobile", userName);
				intent.putExtra("captcha_key", captcha_key);
				intent.putExtra("code", authCode);
				startActivity(intent);
//				loadingDialog = new LoadingDialog(mContext,"注册中...");
//				loadingDialog.show();
//				ApiUserUtils.register(mContext, sessionId, authCode, userName, passWord, userName, "", choose, new RequestCallback() {
//					
//					@Override
//					public void execute(ParseModel parseModel) {
//						if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){//注册成功
//							login(userName,passWord);//注册成功调用登录接口
//						}else{
//							ToastUtils.showToast(mContext, parseModel.getMsg());
//						}
//					}
//				});
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
//					String token = parseModel.getToken();
//					User user = new User();
//					user.setUser_name(userName);
//					user.setPass_word(passWord);
//					logined(token,user);
//					ToastUtils.showToast(mContext, "注册成功");
//					Intent intent = new Intent(mContext,RegisteredActivity.class);
//					startActivity(intent);
//					AppManager.getAppManager().finishActivity(LoginActivity.class);
//					AppManager.getAppManager().finishActivity();
				}else{
					ToastUtils.showToast(mContext, parseModel.getMsg());
				}
			}
		});
	}
	
	private Timer timer;// 计时器
	private int time = 60;//倒计时120秒

	private void regainCode() {
		time = 60;
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
				mTvGetCode.setEnabled(true);
				mTvGetCode.setText("获取验证码");
				timer.cancel();
			} else {
				String str = "剩余 "+msg.what + " 秒";
				SpannableString span = TextViewUtils.getSpannableStringColor(str, 2, str.indexOf("秒"), getResources().getColor(R.color.app_bg_color));
				mTvGetCode.setText(span);
			}
		};
	};

}
