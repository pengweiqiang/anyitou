package cn.com.anyitou.ui;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import cn.com.anyitou.MyApplication;
import cn.com.anyitou.R;
import cn.com.anyitou.api.ApiUserUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.api.constant.OperationType;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.entity.User;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.TextViewUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.LoadingDialog;
import cn.com.gson.JsonObject;
/**
 * 
 * @author will
 * 修改密码
 *
 */
public class ModifyLoginPassWordActivity extends BaseActivity {
	ActionBar actionBar;
	private TextView mTvSendTip;
	private EditText mEtCode;
	private View mBtnConfirm;
	LoadingDialog loadingDialog;
	private TextView mTvSendCode;
	String captchaKey = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_modify_loginpassword);
		super.onCreate(savedInstanceState);
		getUserInfo();
		User user = MyApplication.getInstance().getCurrentUser();
		if(user !=null){
			mTvSendTip.setText("请输入"+StringUtils.getsubMobileString(user.getMobile())+"收到的短信验证码");
		}
	}
	
	private void initData(){
		if(mTvSendCode.isEnabled()){
			mTvSendCode.setEnabled(false);
			loadingDialog = new LoadingDialog(mContext, "发送中...");
			loadingDialog.show();
			ApiUserUtils.sendUserCode(mContext, OperationType.APP_CHECK_CURRENT_MOBILE.getName(), new RequestCallback() {
				
				@Override
				public void execute(ParseModel parseModel) {
					loadingDialog.cancel();
					if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
						regainCode();
						captchaKey = parseModel.getData().getAsJsonObject().get("captcha_key").getAsString();
					}else{
						mTvSendCode.setEnabled(true);
						ToastUtils.showToast(mContext, "发送验证码错误,稍后请重试");
					}
				}
			});
		}
		
	}
	
	@Override
	public void initView() {
		actionBar = (ActionBar)findViewById(R.id.actionBar);
		actionBar.setTitle("修改密码");
		
		mEtCode = (EditText)findViewById(R.id.et_code);
		mTvSendCode = (TextView)findViewById(R.id.send_code);
		mBtnConfirm = findViewById(R.id.btn_confirm);
		mTvSendTip = (TextView)findViewById(R.id.send_tip);
	}

	@Override
	public void initListener() {
		actionBar.setLeftActionButton(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AppManager.getAppManager().finishActivity();
			}
		});
		mTvSendCode.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				initData();
			}
		});
		
		mBtnConfirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(StringUtils.isEmpty(captchaKey)){
					ToastUtils.showToast(mContext, "请先发送验证码");
					return;
				}
				
				String code = mEtCode.getText().toString().trim();
				if(StringUtils.isEmpty(code)){
					ToastUtils.showToast(mContext, mContext.getResources().getString(R.string.input_code));
					mEtCode.requestFocus();
					return;
				}
				
				checkCode(code);
				
			}
		});
	}
	
	/**
	 * 验证验证码
	 * @param captcha
	 */
	private void checkCode(final String captcha){
		loadingDialog = new LoadingDialog(mContext);
		loadingDialog.show();
		ApiUserUtils.registerCode(mContext, captchaKey, captcha, new RequestCallback() {
			
			@Override
			public void execute(ParseModel parseModel) {
				if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
					Intent intent = new Intent(mContext,ModifyLoginPassWordActivity2.class);
					intent.putExtra("code", captcha);
					intent.putExtra("captchaKey", captchaKey);
					startActivity(intent);
					AppManager.getAppManager().finishActivity();
				}else{
					ToastUtils.showToastSingle(mContext, parseModel.getMsg());
				}
				loadingDialog.cancel();
			}
		});
	}
	
	private void getUserInfo(){
		loadingDialog = new LoadingDialog(mContext);
		loadingDialog.show();
		ApiUserUtils.getUserInfo(mContext,new RequestCallback() {
			
			@Override
			public void execute(ParseModel parseModel) {
				if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
					try{
						JsonObject data = parseModel.getData().getAsJsonObject();
						if(data!=null){
							String mobile = data.get("mobile").getAsString();
							if(!StringUtils.isEmpty(mobile)){
								mTvSendTip.setText("请输入"+StringUtils.getsubMobileString(mobile)+"收到的短信验证码");
							}
						}
					}catch(Exception e){
						
					}
					loadingDialog.cancel();
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
				mTvSendCode.setEnabled(true);
				mTvSendCode.setText("获取验证码");
				timer.cancel();
			} else {
				String str = msg.what + " 秒重发";
				SpannableString span = TextViewUtils.getSpannableStringColor(str, 0, str.indexOf("秒"), getResources().getColor(R.color.app_bg_color));
				mTvSendCode.setText(span);
			}
		};
	};

}
