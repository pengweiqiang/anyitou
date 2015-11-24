package cn.com.anyitou.ui;

import java.util.Timer;
import java.util.TimerTask;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import cn.com.anyitou.R;

import cn.com.anyitou.api.ApiUserUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.utils.CheckInputUtil;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.TextViewUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.InfoDialog;
import cn.com.anyitou.views.LoadingDialog;
/**
 * 修改手机号2-->输入新手机号
 * @author will
 *
 */
public class MobilePhoneVerificationActivity2 extends BaseActivity {

	private ActionBar actionBar;
	private EditText mEtPhone, mEtCode;
	private View mGetCode, mNowCheck;
	private TextView mTvCodeMsg;
	
	private String mobile,captchaKey;

	private LoadingDialog loadingDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_mobile_phone_verification2);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void initView() {
		actionBar = (ActionBar) findViewById(R.id.actionBar);
		onConfigureActionBar(actionBar);

		mEtPhone = (EditText) findViewById(R.id.registered_phone);
		mEtCode = (EditText) findViewById(R.id.registered_code);
		mGetCode = findViewById(R.id.get_code);
		mNowCheck = findViewById(R.id.now_check);
		mTvCodeMsg = (TextView) findViewById(R.id.code_msg);
	}

	protected void onConfigureActionBar(ActionBar actionBar) {
		actionBar.setTitle("验证新手机号");
		actionBar.setLeftActionButton(
				new OnClickListener() {

					@Override
					public void onClick(View view) {
						AppManager.getAppManager().finishActivity();
					}
				});
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
				if(StringUtils.isEmpty(mobile)){
					ToastUtils.showToast(mContext, mContext.getResources().getString(R.string.input_phone));
					mEtPhone.requestFocus();
					return;
				}
				if(!CheckInputUtil.checkPhone(mobile, mContext)){
					return;
				}
				if (mGetCode.isEnabled()) {
					mGetCode.setEnabled(false);
					loadingDialog = new LoadingDialog(mContext);
					loadingDialog.show();
					ApiUserUtils.sendMobileCode(mContext, mobile, "app_change_mobile", new RequestCallback() {
						
						@Override
						public void execute(ParseModel parseModel) {
							if (ApiConstants.RESULT_SUCCESS
									.equals(parseModel.getCode())) {// 发送验证码成功
								regainCode();
								mEtCode.requestFocus();
								captchaKey = parseModel.getData()
										.getAsJsonObject()
										.get("captcha_key")
										.getAsString();
							} else {
								mGetCode.setEnabled(true);
								if(StringUtils.isEmpty(parseModel.getMsg())){
									ToastUtils.showToast(mContext,"发送验证码失败，稍后请重试");
								}else{
									ToastUtils.showToast(mContext,
										parseModel.getMsg());
								}
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
				if(StringUtils.isEmpty(captchaKey)){
					ToastUtils.showToast(mContext, mContext.getResources().getString(R.string.send_msg));
					return;
				}
				String code = mEtCode.getText().toString().trim();
				if(StringUtils.isEmpty(code)){
					ToastUtils.showToast(mContext, mContext.getResources().getString(R.string.input_code));
					mEtCode.requestFocus();
					return;
				}
				loadingDialog = new LoadingDialog(mContext);
				loadingDialog.show();
				ApiUserUtils.modifyMobile(mContext, mobile, captchaKey, code, new RequestCallback() {
					
					@Override
					public void execute(ParseModel parseModel) {
						loadingDialog.cancel();
						if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
							ToastUtils.showToast(mContext, "修改手机号成功");
							AppManager.getAppManager().finishActivity();
						}else{
							ToastUtils.showToast(mContext, parseModel.getMsg());
						}
					}
				});
				
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
