package cn.com.anyitou.ui;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
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
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.LoadingDialog;

public class BackLoginPassWordActivity extends BaseActivity {

	ActionBar mActionBar;
	private View mBtnNext, mBtnSendMsg;
	private LoadingDialog loadingDialog;
	private EditText mEtPhone, mEtPassword, mEtCode;
	private TextView mTvTime;

	private String captcha_key = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_back_loginpassword);
		super.onCreate(savedInstanceState);

	}

	@Override
	public void initView() {
		mActionBar = (ActionBar) findViewById(R.id.actionBar);
		mActionBar.setTitle("找回密码");
		mActionBar.setRightActionButton("放弃",
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						AppManager.getAppManager().finishActivity();
					}
				});
		mBtnNext = findViewById(R.id.btn_next);
		mBtnSendMsg = findViewById(R.id.get_code);
		mTvTime = (TextView)findViewById(R.id.time);
		// mBtnRegister = findViewById(R.id.to_register);
		mEtPhone = (EditText) findViewById(R.id.phone);
		mEtCode = (EditText) findViewById(R.id.input_code);
		mEtPassword = (EditText) findViewById(R.id.input_new_password);
	}

	@Override
	public void initListener() {

		// 获取验证码
		mBtnSendMsg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String tel = mEtPhone.getText().toString().trim();
				if (StringUtils.isEmpty(tel)) {
					ToastUtils.showToast(mContext, mContext.getResources()
							.getString(R.string.input_phone));
					mEtPhone.requestFocus();
					return;
				}
				loadingDialog = new LoadingDialog(mContext,"发送中...");
				loadingDialog.show();
				if (CheckInputUtil.checkPhone(tel, mContext)) {
					if (mBtnSendMsg.isEnabled()) {
						mBtnSendMsg.setEnabled(false);
						ApiUserUtils.sendMobileCode(mContext, tel,
								OperationType.APP_FIND_PASSWORD.getName(),
								new RequestCallback() {

									@Override
									public void execute(ParseModel parseModel) {
										loadingDialog.cancel();
										if (ApiConstants.RESULT_SUCCESS
												.equals(parseModel.getCode())) {// 发送验证码成功
											regainCode();
											captcha_key = parseModel.getData()
													.getAsJsonObject()
													.get("captcha_key")
													.getAsString();
										} else {
											mBtnSendMsg.setEnabled(true);
											ToastUtils.showToast(mContext,
													parseModel.getMsg());
										}
									}
								});
					}
				}

			}
		});

		// 下一步
		mBtnNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final String phone = mEtPhone.getText().toString().trim();

				if (StringUtils.isEmpty(phone)) {
					ToastUtils.showToast(mContext, mContext.getResources()
							.getString(R.string.input_phone));
					mEtPhone.requestFocus();
					return;
				}
				if (!CheckInputUtil.checkPhone(phone, mContext)) {
					return;
				}
				final String code = mEtCode.getText().toString().trim();

				if (StringUtils.isEmpty(code)) {
					ToastUtils.showToast(mContext, mContext.getResources()
							.getString(R.string.input_code));
					mEtCode.requestFocus();
					return;
				}
				final String password = mEtPassword.getText().toString().trim();

				if (StringUtils.isEmpty(password)) {
					ToastUtils.showToast(mContext, mContext.getResources()
							.getString(R.string.input_pwd));
					mEtPassword.requestFocus();
					return;
				}
				if (!CheckInputUtil.checkPassword(password, mContext)) {
					return;
				}
				loadingDialog = new LoadingDialog(mContext);
				loadingDialog.show();
				// ApiUserUtils.getPwdSendCode(mContext, phone, new
				// RequestCallback() {
				//
				// @Override
				// public void execute(ParseModel parseModel) {
				// loadingDialog.cancel();
				// if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
				// Intent intent = new
				// Intent(mContext,BackLoginPWVCodeActivity.class);
				// intent.putExtra("sessionId", parseModel.getSession_id());
				// intent.putExtra("phone", phone);
				// startActivity(intent);
				// AppManager.getAppManager().finishActivity();
				// }else{
				// ToastUtils.showToast(mContext, parseModel.getMsg());
				// }
				// }
				// });

			}
		});
		// mBtnRegister.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// startActivity(RegisteredAccountActivity.class);
		// }
		// });
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
				mBtnSendMsg.setEnabled(true);
				// mTvGetCode.setText("获取验证码");
				timer.cancel();
			} else {
				mTvTime.setText("剩余 " + msg.what + " 秒");
			}
		};
	};

}
