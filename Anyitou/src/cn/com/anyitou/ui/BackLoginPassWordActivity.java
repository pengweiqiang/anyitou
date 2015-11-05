package cn.com.anyitou.ui;

import java.util.Timer;
import java.util.TimerTask;

import android.content.DialogInterface;
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
import cn.com.anyitou.api.constant.OperationType;
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
 * 找回密码
 * 
 * @author pengweiqiang
 * 
 */
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
		mActionBar.setRightActionButton("放弃", new OnClickListener() {

			@Override
			public void onClick(View v) {
				InfoDialog.Builder builder = new InfoDialog.Builder(mContext);
				builder.setMessage("放弃后您的密码不会改变");
				builder.setTitle("确定要放弃?");
				builder.setButton1("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				builder.setButton2("确定放弃",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
								AppManager.getAppManager().finishActivity();
							}
						});
				builder.create().show();

			}
		});
		mBtnNext = findViewById(R.id.btn_next);
		mBtnSendMsg = findViewById(R.id.get_code);
		mTvTime = (TextView) findViewById(R.id.time);
		// mBtnRegister = findViewById(R.id.to_register);
		mEtPhone = (EditText) findViewById(R.id.phone);
		mEtCode = (EditText) findViewById(R.id.input_code);
		mEtPassword = (EditText) findViewById(R.id.input_new_password);
	}

	@Override
	public void initListener() {

		mActionBar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AppManager.getAppManager().finishActivity();
			}
		});
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
				loadingDialog = new LoadingDialog(mContext, "发送中...");
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

		// 完成
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
				ApiUserUtils.getPwd(mContext, phone, captcha_key, code,
						password, new RequestCallback() {

							@Override
							public void execute(ParseModel parseModel) {
								loadingDialog.cancel();
								if (ApiConstants.RESULT_SUCCESS
										.equals(parseModel.getCode())) {
									showFindPwdSuccess();
								} else {
									ToastUtils.showToast(mContext,
											parseModel.getMsg());
								}
							}

						});

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
	
	private void showFindPwdSuccess() {
		InfoDialog.Builder builder = new InfoDialog.Builder(mContext);
		builder.setMessage("设置成功,请妥善保管密码");
		builder.setTitle("提示");
		builder.setButton1("我知道了", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				Intent intent = new Intent(mContext,
						LoginActivity.class);
				startActivity(intent);
				AppManager.getAppManager().finishActivity();
			}
		});
		builder.create().show();
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
