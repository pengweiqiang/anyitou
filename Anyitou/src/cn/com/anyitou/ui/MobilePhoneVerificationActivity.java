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
import cn.com.gson.JsonObject;
/**
 * 修改手机号
 * @author will
 *
 */
public class MobilePhoneVerificationActivity extends BaseActivity {

	private ActionBar actionBar;
	private EditText mEtPhone, mEtCode;
	private View mGetCode, mNowCheck;
	private String mobile = "",captcha_key = "";
	private TextView mTvCodeMsg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_mobile_phone_verification);
		super.onCreate(savedInstanceState);
		getUserInfo();
//		mEtPhone.setText(MyApplication.getInstance().getCurrentUser().getMobile());
	}

	@Override
	public void initView() {
		actionBar = (ActionBar) findViewById(R.id.actionBar);
		onConfigureActionBar(actionBar);

		mEtPhone = (EditText) findViewById(R.id.registered_phone);
		mEtCode = (EditText) findViewById(R.id.registered_code);
		mGetCode = findViewById(R.id.get_code);
		mNowCheck = findViewById(R.id.now_check);
		mTvCodeMsg = (TextView)findViewById(R.id.code_msg);
	}

	protected void onConfigureActionBar(ActionBar actionBar) {
		actionBar.setTitle("解除原手机绑定");
//		actionBar.hideLeftActionButtonText();
	}

	@Override
	public void initListener() {
		actionBar.setLeftActionButton(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AppManager.getAppManager().finishActivity();
			}
		});
		mGetCode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mobile = mEtPhone.getText().toString().trim();
				if(!CheckInputUtil.checkPhone(mobile, mContext)){
//					ToastUtils.showToast(mContext, mContext.getResources().getString(R.string.input_phone));
					mEtPhone.requestFocus();
					return;
				}
//				if(MyApplication.getInstance().getCurrentUser() == null/* && !MyApplication.getInstance().getCurrentUser().getMobile().equals(mobile)*/){
//					ToastUtils.showToast(mContext, mContext.getResources().getString(R.string.input_correct_phone));
//					return;
//				}
				if (mGetCode.isEnabled()) {
					mGetCode.setEnabled(false);
					loadingDialog = new LoadingDialog(mContext);
					loadingDialog.show();
					ApiUserUtils.sendUserCode(mContext, "app_check_current_mobile", new RequestCallback() {
						
						@Override
						public void execute(ParseModel parseModel) {
							if (ApiConstants.RESULT_SUCCESS
									.equals(parseModel.getCode())) {// 发送验证码成功
								regainCode();
								captcha_key = parseModel.getData()
										.getAsJsonObject()
										.get("captcha_key")
										.getAsString();
								mEtCode.requestFocus();
							} else {
								mGetCode.setEnabled(true);
								ToastUtils.showToast(mContext,
										parseModel.getMsg());
							}
							loadingDialog.cancel();
						}
					});
				}
			}
		});
		mNowCheck.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(StringUtils.isEmpty(mobile)){
					ToastUtils.showToast(mContext, mContext.getResources().getString(R.string.input_phone));
					mEtPhone.requestFocus();
					return;
				}
				if(!CheckInputUtil.checkPhone(mobile, mContext)){
					return;
				}
				if(StringUtils.isEmpty(captcha_key)){
					ToastUtils.showToast(mContext, mContext.getResources().getString(R.string.send_msg));
					return;
				}
				String code = mEtCode.getText().toString().trim();
				if(StringUtils.isEmpty(code)){
					ToastUtils.showToast(mContext, mContext.getResources().getString(R.string.input_code));
					mEtCode.requestFocus();
					return;
				}
				Intent intent = new Intent(mContext,MobilePhoneVerificationActivity2.class);
				intent.putExtra("code", code);
				intent.putExtra("captcha_key", captcha_key);
				intent.putExtra("mobile", mobile);
				startActivity(intent);
				AppManager.getAppManager().finishActivity();
			}
		});
	}
	
	LoadingDialog loadingDialog;
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
								mEtPhone.setText(mobile);
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
	private int time = 60;// 倒计时120秒

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
				mGetCode.setEnabled(true);
				mTvCodeMsg.setText("获取验证码");
				timer.cancel();
			} else {
				String str = "剩余 "+msg.what + " 秒";
				SpannableString span = TextViewUtils.getSpannableStringColor(str, 2, str.indexOf("秒"), getResources().getColor(R.color.app_bg_color));
				mTvCodeMsg.setText(span);
			}
		};
	};

}
