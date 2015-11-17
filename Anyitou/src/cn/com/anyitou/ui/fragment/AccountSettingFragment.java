package cn.com.anyitou.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import cn.com.GlobalConfig;
import cn.com.anyitou.MyApplication;
import cn.com.anyitou.R;
import cn.com.anyitou.api.constant.ReqUrls;
import cn.com.anyitou.commons.Constant;
import cn.com.anyitou.entity.User;
import cn.com.anyitou.ui.LoginActivity;
import cn.com.anyitou.ui.MobilePhoneVerificationActivity;
import cn.com.anyitou.ui.ModifyGestureDialog;
import cn.com.anyitou.ui.ModifyLoginPassWordActivity;
import cn.com.anyitou.ui.base.BaseFragment;
import cn.com.anyitou.utils.SharePreferenceManager;
import cn.com.anyitou.utils.ShareUtil;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.MyPopupWindow;
import cn.com.anyitou.views.ToggleButton;
import cn.com.anyitou.views.ToggleButton.OnToggleChanged;

/**
 * 账户设置
 * 
 * @author will
 * 
 */
@SuppressLint("NewApi")
public class AccountSettingFragment extends BaseFragment {
	private View infoView;
	private ActionBar mActionBar;
	
	private TextView mTvLoginStatus;
	private View mBtnLogout;
	private View mBtnUpdatePwd,mBtnUpdatePhone,mBtnUpdateGesture,mBtnShare;
	private ToggleButton mTbSwicthPush;
	cn.com.anyitou.utils.ShareUtil shareUtil;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		infoView = inflater.inflate(R.layout.activity_set_up, container,
				false);
		
		initView();
		initListener();
		shareUtil = new ShareUtil(mActivity);
		Boolean isPush = (Boolean)SharePreferenceManager.getSharePreferenceValue(mActivity, Constant.FILE_NAME, Constant.PUSH, true);
		if (isPush)
			mTbSwicthPush.toggleOn();
		else
			mTbSwicthPush.toggleOff();
		
		return infoView;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		showLoginStatus();
	}

	/**
	 * 显示登陆状态
	 */
	private void showLoginStatus(){
		if(isLogin()){
			mTvLoginStatus.setText("退出登录");
		}else{
			mTvLoginStatus.setText("登录");
		}
	}
	private void initView(){
		mActionBar = (ActionBar) infoView.findViewById(R.id.actionBar);
		mActionBar.setTitle(getResources().getString(R.string.setting));
		mBtnUpdatePwd = infoView.findViewById(R.id.update_pwd);
		mBtnUpdatePhone = infoView.findViewById(R.id.btn_phone);
		mBtnUpdateGesture = infoView.findViewById(R.id.btn_gesture);
		mTbSwicthPush = (ToggleButton) infoView.findViewById(R.id.switch_push);
		mBtnShare = infoView.findViewById(R.id.share_friend);
		mBtnLogout = infoView.findViewById(R.id.logout);
		mTvLoginStatus = (TextView) infoView.findViewById(R.id.login_title);
	}
	
	private void initListener(){
		mActionBar.hideLeftActionButtonText();
		mBtnUpdatePwd.setOnClickListener(onClickListener);
		mBtnUpdatePhone.setOnClickListener(onClickListener);
		mBtnUpdateGesture.setOnClickListener(onClickListener);
	    //推送开关切换事件
		mTbSwicthPush.setOnToggleChanged(new OnToggleChanged(){
	            @Override
	            public void onToggle(boolean on) {
	            	SharePreferenceManager.saveBatchSharedPreference(mActivity, Constant.FILE_NAME, Constant.PUSH, on);
	            }
	    });
		mBtnShare.setOnClickListener(onClickListener);
		mBtnLogout.setOnClickListener(onClickListener);
	}
	
	OnClickListener onClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			switch (v.getId()) {
			case R.id.update_pwd://修改登陆密码
				if(isLogin()){
					intent.setClass(mActivity, ModifyLoginPassWordActivity.class);
				}else{
					ToastUtils.showToast(mActivity, "请先登录");
					intent.setClass(mActivity, LoginActivity.class);
				}
				break;
			case R.id.btn_phone://修改手机号码
				if(isLogin()){
					intent.setClass(mActivity, MobilePhoneVerificationActivity.class);
				}else{
					ToastUtils.showToast(mActivity, "请先登录");
					intent.setClass(mActivity, LoginActivity.class);
				}
				break;
			case R.id.btn_gesture://修改手势密码
				if(isLogin()){
					intent.setClass(mActivity, ModifyGestureDialog.class);
				}else{
					ToastUtils.showToast(mActivity, "请先登录");
					intent.setClass(mActivity, LoginActivity.class);
				}
				break;
//			case R.id.switch_push://接受推送通知
//				
//				return;
			case R.id.share_friend://分享给好友
				shareFriend(v);
				return;
			case R.id.logout://退出登录 or 登录
				changeLoginStatus();
				return;

			default:
				break;
			}
			startActivity(intent);
		}
	};

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// 判断Fragment中的ListView时候存在，判断该Fragment时候已经正在前台显示
		// 通过这两个判断，就可以知道什么时候去加载数据了
		if (isVisibleToUser && isVisible()) {
//			if (MyApplication.getInstance().getCurrentUser() == null) {
//				Intent intent = new Intent(mActivity, LoginActivity.class);
//				startActivity(intent);
//			}
		}
		super.setUserVisibleHint(isVisibleToUser);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		if (getUserVisibleHint()) {
			
		}
		super.onActivityCreated(savedInstanceState);
	}


	/**
	 * 切换登录状态
	 */
	private void changeLoginStatus(){
		if(isLogin()){
			ToastUtils.showToast(mActivity, "退出登录成功");
			logOut();
			showLoginStatus();
		}else{
			Intent intent = new Intent(mActivity,LoginActivity.class);
			startActivity(intent);
		}
	}
	private boolean isLogin(){
		boolean isLogin = false;
		User user = MyApplication.getInstance().getCurrentUser();
		if(user != null){
			isLogin = true;
		}
		return isLogin;
	}
	
	private PopupWindow popupWindow;
	private TextView cancleTextView;
	private View mShareWeixin,mShareWeixinLine,mShareWeibo,mShareQQ;
	
	@SuppressWarnings("deprecation")
	public void shareFriend(View view){
		
		popupWindow = MyPopupWindow.getPopupWindow(R.layout.invite_friends_popupwindow, mActivity);
		View popupWindowView = popupWindow.getContentView();
		cancleTextView = (TextView) popupWindowView.findViewById(R.id.friendsCancleTextView);
		mShareWeixin = popupWindowView.findViewById(R.id.share_wx_friend);
		mShareWeixinLine = popupWindowView.findViewById(R.id.share_wx_line);
		mShareWeibo = popupWindowView.findViewById(R.id.share_weibo_sina_friend);
		mShareQQ = popupWindowView.findViewById(R.id.share_qq_friend);
		
		mShareWeixin.setOnClickListener(shareClickListener);
		mShareWeixinLine.setOnClickListener(shareClickListener);
		mShareWeibo.setOnClickListener(shareClickListener);
		mShareQQ.setOnClickListener(shareClickListener);
		cancleTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				// 关闭popupWindow.dismiss();
				popupWindow.dismiss();
			}
		});
//		popupWindow.showAsDropDown(view);
		popupWindow.showAtLocation(cancleTextView, Gravity.BOTTOM, 0,0);
		
	}
	
	OnClickListener shareClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.share_wx_friend:
				shareUtil.share(0,"");
				break;
			case R.id.share_wx_line:
				shareUtil.share(1,"");
				break;
			case R.id.share_weibo_sina_friend:
				shareUtil.share(5,"");
				break;
			case R.id.share_qq_friend:
				shareUtil.share(3,"");
				break;

			default:
				break;
			}
			popupWindow.dismiss();
		}
	};
	
	/**
	 * 退出登录
	 */
	public void logOut() {
		SharePreferenceManager.saveBatchSharedPreference(mActivity,
				Constant.FILE_NAME, "myassets", "");
		SharePreferenceManager.saveBatchSharedPreference(mActivity,
				Constant.FILE_NAME, "user", "");
		SharePreferenceManager.saveBatchSharedPreference(mActivity,
				Constant.FILE_NAME, ReqUrls.ACCESS_TOKEN, "");
		SharePreferenceManager.saveBatchSharedPreference(mActivity,
				Constant.FILE_NAME, ReqUrls.REFRESH_TOKEN, "");
		GlobalConfig.ACCESS_TOKEN = "";
		GlobalConfig.REFRESH_TOKEN = "";
		MyApplication.getInstance().setUser(null);
	}



}
