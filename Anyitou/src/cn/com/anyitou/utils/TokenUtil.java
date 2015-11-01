package cn.com.anyitou.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import cn.com.GlobalConfig;
import cn.com.anyitou.MyApplication;
import cn.com.anyitou.api.ApiUserUtils;
import cn.com.anyitou.api.ApiUtils;
import cn.com.anyitou.api.constant.ReqUrls;
import cn.com.anyitou.commons.Constant;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.ui.LoginActivity;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;

/**
 * token获取，分为三种token
 * 
 * //1.客户端授权默认(有效期2小时)
	public static String CLIENT_CREDENTIALS = "client_credentials";
	//2. 用户授权token(有效期2小时)
	public static String ACCESS_TOKEN = "access_token";
	//3.通过 refresh_token 获取新的token(有效期30天)
	public static String REFRESH_TOKEN = "refresh_token";
 * 
 * @author pengweiqiang
 *
 */
public class TokenUtil {
	/**
	 * 
	 * @param mContext
	 * @param isTimer 自动刷新
	 */
	public static void getClientToken(final Context mContext,boolean isTimer){
		ApiUserUtils.oauthAccessToken(mContext, ReqUrls.CLIENT_CREDENTIALS, "", "", "", new RequestCallback() {
			
			@Override
			public void execute(ParseModel parseModel) {
				String clientToken = parseModel.getAccess_token();
				if(!StringUtils.isEmpty(clientToken)){
					saveToken(mContext,clientToken,"","");
				}
			}
		},isTimer);
	}
	public static void getClientToken(final Context mContext){
		getClientToken(mContext, false);
	}
	
	public static void getUserToken(final Context mContext,String userName,String password){
		ApiUserUtils.oauthAccessToken(mContext, "password", userName, password, "", new RequestCallback() {
			
			@Override
			public void execute(ParseModel parseModel) {
				String accessToken = parseModel.getAccess_token();
				String refreshToken = parseModel.getRefresh_token();
				if(!StringUtils.isEmpty(accessToken)){
					saveToken(mContext,"",accessToken,refreshToken);
				}
			}
		},false);
	}
	
	public static void refreshToken(final Context mContext,String refreshToken,boolean isTimer){
		ApiUserUtils.oauthAccessToken(mContext, "refresh_token", "", "", refreshToken, new RequestCallback() {
			
			@Override
			public void execute(ParseModel parseModel) {
				String accessToken = parseModel.getAccess_token();
				if(!StringUtils.isEmpty(accessToken)){
					String refreshToken = parseModel.getRefresh_token();
					if(!StringUtils.isEmpty(accessToken)){
						saveToken(mContext,"",accessToken,refreshToken);
					}
				}else{//重新授权失败，重新登陆
					logOut(mContext);
				}
			}
		},isTimer);
	}
	
	public static void refreshToken(final Context mContext,String refreshToken){
		refreshToken(mContext, refreshToken, false);
	}
	
	public static void logOut(Context mContext) {
		SharePreferenceManager.saveBatchSharedPreference(mContext,
				Constant.FILE_NAME, "myassets", "");
		SharePreferenceManager.saveBatchSharedPreference(mContext,
				Constant.FILE_NAME, "user", "");
		SharePreferenceManager.saveBatchSharedPreference(mContext,
				Constant.FILE_NAME, ReqUrls.ACCESS_TOKEN, "");
		SharePreferenceManager.saveBatchSharedPreference(mContext,
				Constant.FILE_NAME, ReqUrls.REFRESH_TOKEN, "");
		GlobalConfig.ACCESS_TOKEN = "";
		GlobalConfig.REFRESH_TOKEN = "";
		Intent loginIntent = new Intent(mContext,LoginActivity.class);
		loginIntent.putExtra("userName", MyApplication.getInstance().getCurrentUser().getUsername());
		loginIntent.putExtra("type", 2);
		loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mContext.startActivity(loginIntent);
		MyApplication.getInstance().setUser(null);
	}
	public static void saveTokenIsTimer(Context mContext,String backStr,String type){
		if (!StringUtils.isEmpty(backStr)){
			ParseModel pm = ApiUtils.parse2ParseModel(backStr);
			if(pm != null){
				if(ReqUrls.CLIENT_CREDENTIALS.equals(type)){//client客户端授权刷新
					saveToken(mContext, pm.getAccess_token(), "", "");
				}else if(ReqUrls.REFRESH_TOKEN.equals(type)){//用户授权刷新
					saveToken(mContext, "", pm.getAccess_token(), pm.getRefresh_token());
				}
				if(!StringUtils.isEmpty(pm.getError()) && pm.getError().equals("invalid_grant")){//refresh_token失效，重新登陆
					Intent loginIntent = new Intent(mContext,LoginActivity.class);
					loginIntent.putExtra("userName", MyApplication.getInstance().getCurrentUser().getUsername());
					loginIntent.putExtra("type", 2);
					loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					mContext.startActivity(loginIntent);
				}
			}
		}
	}
	public static void saveToken(Context mContext,String clientToken,String accessToken,String refreshToken){
		if(!StringUtils.isEmpty(clientToken)){
			GlobalConfig.CLIENT_TOKEN = clientToken;
			
			long currentTime = System.currentTimeMillis();
			SharePreferenceManager.saveBatchSharedPreference(mContext,
					Constant.FILE_NAME, ReqUrls.CLIENT_CREDENTIALS, clientToken + "_"
							+ currentTime);
			
		}
		if (!StringUtils.isEmpty(accessToken)) {
			GlobalConfig.ACCESS_TOKEN = accessToken;
			
			long currentTime = System.currentTimeMillis();
			SharePreferenceManager.saveBatchSharedPreference(mContext,
					Constant.FILE_NAME, ReqUrls.ACCESS_TOKEN, accessToken + "_"
							+ currentTime);
		}
		if(!StringUtils.isEmpty(refreshToken)){
			GlobalConfig.REFRESH_TOKEN = refreshToken;
			
			long currentTime = System.currentTimeMillis();
			SharePreferenceManager.saveBatchSharedPreference(mContext,
					Constant.FILE_NAME, ReqUrls.REFRESH_TOKEN, refreshToken + "_"
							+ currentTime);
		}
	}
	
}
