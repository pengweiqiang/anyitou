//package cn.com.anyitou.ui;
//
//import java.util.Timer;
//import java.util.TimerTask;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import cn.com.anyitou.R;
//
//import cn.com.anyitou.api.ApiUserUtils;
//import cn.com.anyitou.api.constant.ApiConstants;
//import cn.com.anyitou.commons.AppManager;
//import cn.com.anyitou.entity.ParseModel;
//import cn.com.anyitou.ui.base.BaseActivity;
//import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
//import cn.com.anyitou.utils.StringUtils;
//import cn.com.anyitou.utils.ToastUtils;
//import cn.com.anyitou.views.ActionBar;
//import cn.com.anyitou.views.LoadingDialog;
//
//public class BackLoginPWVCodeActivity extends BaseActivity {
//	
//	ActionBar mActionBar;
//	private View mBtnNext;
//	String sessionId = "";
//	String phone = "";
//	private EditText mEtMsgCode;
//	private TextView mTvGetCode,mTvTitleTip;
//	LoadingDialog loadingDialog;
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		setContentView(R.layout.activity_back_loginpw_vcode);
//		super.onCreate(savedInstanceState);
//	
//		
//		sessionId = this.getIntent().getStringExtra("sessionId");
//		phone = this.getIntent().getStringExtra("phone");
//		mTvGetCode.setEnabled(false);
//		regainCode();
//		mTvTitleTip.setText("我们刚刚发送一条验证码到"+phone);
//	}
//	
//	@Override
//	public void initView() {
//		mBtnNext = findViewById(R.id.btn_next);
//		mActionBar = (ActionBar)findViewById(R.id.actionBar);
//		mActionBar.setTitle("找回密码");
//		mActionBar.setLeftActionButton(R.drawable.btn_back, new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				AppManager.getAppManager().finishActivity();
//			}
//		});
//		mEtMsgCode = (EditText)findViewById(R.id.msg_code);
//		mTvGetCode = (TextView)findViewById(R.id.get_code);
//		mTvTitleTip = (TextView)findViewById(R.id.title_tip);
//	}
//
//	@Override
//	public void initListener() {
//		
//		mTvGetCode.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				if(mTvGetCode.isEnabled()){
//					loadingDialog = new LoadingDialog(mContext);
//					loadingDialog.show();
//					mTvGetCode.setEnabled(false);
//					ApiUserUtils.getPwdSendCode(mContext, phone, new RequestCallback() {
//						
//						@Override
//						public void execute(ParseModel parseModel) {
//							loadingDialog.cancel();
//							if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
//								regainCode();
//								sessionId = parseModel.getSession_id();
//							}else{
//								ToastUtils.showToast(mContext, parseModel.getMsg());
//							}
//						}
//					});
//				}
//			}
//		});
//		
//		mBtnNext.setOnClickListener(new OnClickListener(){
//
//			@Override
//			public void onClick(View v) {
//				
//				String msgCode = mEtMsgCode.getText().toString().trim();
//				if(StringUtils.isEmpty(msgCode)){
//					mEtMsgCode.requestFocus();
//					ToastUtils.showToast(mContext, mContext.getResources().getString(R.string.input_code));
//					return;
//				}
//				loadingDialog = new LoadingDialog(mContext);
//				loadingDialog.show();
////				if(mTvGetCode.isEnabled()){
//					ApiUserUtils.getPwdCheckCode(mContext, sessionId, msgCode, new RequestCallback() {
//						
//						@Override
//						public void execute(ParseModel parseModel) {
//							loadingDialog.cancel();
//							if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
//								regainCode();
//								if(!StringUtils.isEmpty(parseModel.getSession_id())){
//									sessionId = parseModel.getSession_id();
//								}
//								Intent intent = new Intent(mContext,InputNewPasswordActivity.class);
//								intent.putExtra("sessionId", sessionId);
//								startActivity(intent);
//								AppManager.getAppManager().finishActivity();
//							}else{
//								ToastUtils.showToast(mContext, parseModel.getMsg());
//							}
//						}
//					});
//				}
//				
////			}
//		});
//	}
//	
//	private Timer timer;// 计时器
//	private int time = 60;//倒计时120秒
//
//	private void regainCode() {
//		time = 60;
//		timer = new Timer();
//		timer.schedule(new TimerTask() {
//
//			@Override
//			public void run() {
//				handler.sendEmptyMessage(time--);
//			}
//		}, 0, 1000);
//	}
//
//	private Handler handler = new Handler() {
//		public void handleMessage(android.os.Message msg) {
//			if (msg.what == 0) {
//				mTvGetCode.setEnabled(true);
//				mTvGetCode.setText("获取验证码");
//				timer.cancel();
//			} else {
//				mTvGetCode.setText("有效时长:"+msg.what+"秒");
//			}
//		};
//	};
//
//}
