package cn.com.anyitou.ui;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.commons.Constant;
import cn.com.anyitou.entity.User;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.utils.SharePreferenceManager;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.GestureLockView;
import cn.com.anyitou.views.GestureLockView.OnGestureFinishListener;
import cn.com.anyitou.views.InfoDialog;
/**
 * 手势密码
 * @author will
 *
 */
public class GestureLockActivity extends BaseActivity {
	GestureLockView mGestureLockView;
	TextView mTipTextView;
	TranslateAnimation mAnimation;
	private TextView tvLoginOther,tvGesturePwd;
	private TextView tvUserName;
	private int type; //1代表解锁 ， 2代表绘制密码(如果有手势密码，需要先解锁才能修改) 3从注册界面进入 4从登陆界面进入
	private boolean isLockCurrent = false;
	private String gestureLock = "";
	private int errorCount = 5;
	private TextView mTvRestartPwd;
	
	private ActionBar mActionBar;
	private String title = "修改手势密码";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.gesture_lock);
		super.onCreate(savedInstanceState);
//		tintManager.setTintColor(getResources().getColor(R.color.bg_gesture));
		type = this.getIntent().getIntExtra("type", 1);
		initData();
	}
	private void initData(){
		User user = application.getCurrentUser();
		if(user ==null){
			Intent intent = new Intent(mContext,LoginActivity.class);
			startActivity(intent);
			return;
		}
		
		
		mTipTextView.setTextColor(getResources().getColor(R.color.gesture_tip2));
		mTipTextView.setVisibility(View.VISIBLE);
		String tipStr = "";
		if(type == 1 && !StringUtils.isEmpty(application.gesturePwd)){
			mGestureLockView.setKey(application.gesturePwd);
			tipStr = "请输入解锁密码";
			title = "欢迎您,"+StringUtils.getsubMobileString(user.getUsername());
		}else if(type == 2 || type == 3 || type == 4){//绘制密码
			title = "设置手势密码";
			if(!StringUtils.isEmpty(application.gesturePwd)){
				mGestureLockView.setKey(application.gesturePwd);
				tipStr = "请输入当前解锁密码";
			}else{
				tvGesturePwd.setVisibility(View.GONE);
				tipStr = "请绘制手势密码";
				
			}
			mActionBar.setRightActionButton("取消", new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					AppManager.getAppManager().finishActivity(GestureLockActivity.this);
				}
			});
		}
		mTipTextView.setText(tipStr);
		mActionBar.setTitle(title);
	}
	@Override
	public void initView() {
		tvGesturePwd = (TextView)findViewById(R.id.gesturelock_pwq);
		tvLoginOther = (TextView)findViewById(R.id.login_other);
		tvUserName = (TextView)findViewById(R.id.userName);
		mTvRestartPwd = (TextView)findViewById(R.id.restart_pwd);
		mActionBar = (ActionBar)findViewById(R.id.actionBar);
		
		
		mGestureLockView = (GestureLockView) findViewById(R.id.gestureLockView);
		mTipTextView = (TextView) findViewById(R.id.textview);
		mAnimation = new TranslateAnimation(-20, 20, 0, 0);
		mAnimation.setDuration(50);
		mAnimation.setRepeatCount(2);
		mAnimation.setRepeatMode(Animation.REVERSE);
		
	}
	/**
	 * 五次输入密码错误
	 */
	private void errorGesturePwd(){
		//ToastUtils.showToast(mContext, "密码输入错误，请重新登录");
		SharePreferenceManager.saveBatchSharedPreference(mContext, Constant.FILE_NAME, application.getCurrentUser().getUsername()+Constant.GESTURE_PWD, "");
		application.gesturePwd = "";
		application.isLock = false;
		logOut();
		
		InfoDialog.Builder builder = new InfoDialog.Builder(mContext,R.layout.info_dialog);
		builder.setMessage("由于手势密码错误超过5次,您将退出登录\n请重新设置新的手势密码");
		builder.setTitle("手势密码错误超过5次");
		builder.setButton1("我知道了", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				startActivity(LoginActivity.class);
				AppManager.getAppManager().finishActivity();
			}
		},R.drawable.btn_concer_dialog_selector);
		builder.setButton2(null, null);
		builder.create().show();
		
		
	}

	private void gestureListener(){
		// 手势完成后回调
		mGestureLockView
				.setOnGestureFinishListener(new OnGestureFinishListener() {
					@Override
					public void OnGestureFinish(boolean success, String key) {
						if(type == 1){//解锁
							
							if (success) {
								mTipTextView.setTextColor(Color.parseColor("#FFFFFF"));
								mTipTextView.setVisibility(View.VISIBLE);
								mTipTextView.setText("密码正确！");
								application.isLock = true;
								//mTipTextView.startAnimation(mAnimation);
								if(AppManager.getAppManager().currentActivity().getClass() != GestureLockActivity.class){
									startActivity(AppManager.getAppManager().currentActivity().getClass());
								}else{
									if(application.classLast!=null)
									{
										startActivity(application.classLast);
									}else{
										startActivity(HomeActivity.class);
									}
								}
								SharePreferenceManager.saveBatchSharedPreference(mContext, Constant.FILE_NAME, application.getCurrentUser().getUsername()+Constant.GESTURE_PWD, System.currentTimeMillis()+","+key);
								
								AppManager.getAppManager().finishActivity();
							} else {
								if(errorCount > 1){
									errorCount --;
//											mTipTextView.setTextColor(Color.parseColor("#FF2525"));
									mTipTextView.setVisibility(View.VISIBLE);
									mTipTextView.setText("密码输入错误,还可以尝试"+errorCount+"次");
									mTipTextView.startAnimation(mAnimation);
								}else{
									errorGesturePwd();
								}
							}
							
						}else if(type == 2 || type == 3 || type == 4){//绘制密码
							if(!StringUtils.isEmpty(key) && key.length()>3){
								if(!StringUtils.isEmpty(application.gesturePwd) && !isLockCurrent){//如果有手势密码
									if(success){
										mGestureLockView.setKey("");
										isLockCurrent = true;
//												mTipTextView.setTextColor(Color.parseColor("#FFFFFF"));
										mTipTextView.setVisibility(View.VISIBLE);
										mTipTextView.setText("重新绘制");
									}else{
										if(errorCount > 1){
											errorCount --;
//													mTipTextView.setTextColor(Color.parseColor("#FF2525"));
											mTipTextView.setVisibility(View.VISIBLE);
											mTipTextView.setText("密码输入错误,还可以尝试"+errorCount+"次");
											mTipTextView.startAnimation(mAnimation);
										}else{
											errorGesturePwd();
										}
									}
								}else{
									mTvRestartPwd.setVisibility(View.VISIBLE);
									if(StringUtils.isEmpty(gestureLock)){//第一次绘制成功
										gestureLock = key;
										mTipTextView.setTextColor(getResources().getColor(R.color.gesture_tip2));
										mTipTextView.setText("请再次绘制手势密码");
									}else{//第二次绘制
										if(!key.equals(gestureLock)){//第二次密码不一致
//													mTipTextView.setTextColor(Color.parseColor("#FF2525"));
											mTipTextView.setVisibility(View.VISIBLE);
											mTipTextView.setText("与上一次绘制不一致，请再次绘制");
											mTipTextView.startAnimation(mAnimation);
										}else{//两次密码一致，保存密码
											application.gesturePwd = gestureLock;
											application.isLock = true;
											ToastUtils.showToast(mContext, "手势密码绘制成功");
											SharePreferenceManager.saveBatchSharedPreference(mContext, Constant.FILE_NAME, application.getCurrentUser().getUsername()+Constant.GESTURE_PWD, System.currentTimeMillis()+","+key);
											
											if(type ==3 || type == 4){
												Intent intent = new Intent(mContext,HomeActivity.class);
												intent.putExtra("type", "login");
												startActivity(intent);
											}else{
												startActivity(HomeActivity.class);
											}
											AppManager.getAppManager().finishActivity();
										}
									}
								}
							}else{
//										mTipTextView.setTextColor(Color.parseColor("#FF2525"));
								mTipTextView.setText("手势密码须大于4位");
								mTipTextView.startAnimation(mAnimation);
							}
						}
						
					}
				});
	}
	@Override
	public void initListener() {
		
		gestureListener();
		mActionBar.hideLeftActionButtonText();
		//重新绘制
		mTvRestartPwd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {	
				gestureLock = "";
				mTipTextView.setTextColor(getResources().getColor(R.color.gesture_tip2));
				mTipTextView.setText("请绘制手势密码");
				
				mTvRestartPwd.setVisibility(View.GONE);
			}
		});
		//忘记手势
		tvGesturePwd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				showForgetPwd();
			}
		});
		//登陆其他账号
		tvLoginOther.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				application.gesturePwd = "";
				Intent intent = new Intent(GestureLockActivity.this,LoginActivity.class);
				startActivity(intent);
			}
		});
	}
	
	public void showForgetPwd(){
        Builder builder = new AlertDialog.Builder(mContext)
        .setMessage("重新登录，清空手势密码？")
        .setPositiveButton("确定", new OnClickListener() {
            
            @Override
            public void onClick(DialogInterface dialog, int which) {
            	SharePreferenceManager.saveBatchSharedPreference(mContext,
        				Constant.FILE_NAME, Constant.GESTURE_PWD, "");
            	String username = application.getCurrentUser().getUsername();
            	Intent intent = new Intent(mContext,LoginActivity.class);
            	intent.putExtra("username", username);
            	application.isLock = false;
            	application.gesturePwd = "";
                logOut();
                startActivity(intent);
                AppManager.getAppManager().finishActivity();
            	dialog.dismiss();
                
            }
        })
        .setNegativeButton("取消", new OnClickListener() {
            
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

}
