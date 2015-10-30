package cn.com.anyitou.utils;

import android.content.Context;
import cn.com.GlobalConfig;
import cn.com.anyitou.MyApplication;
import cn.com.anyitou.api.ApiUserUtils;
import cn.com.anyitou.api.constant.ReqUrls;
import cn.com.anyitou.commons.Constant;
import cn.com.anyitou.entity.ParseModel;
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

	public static void getClientToken(final Context mContext){
		ApiUserUtils.oauthAccessToken(mContext, ReqUrls.CLIENT_CREDENTIALS, "", "", "", new RequestCallback() {
			
			@Override
			public void execute(ParseModel parseModel) {
				String clientToken = parseModel.getAccess_token();
				if(!StringUtils.isEmpty(clientToken)){
					saveToken(mContext,clientToken,"","");
				}
			}
		});
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
		});
	}
	
	
	public static void refreshToken(final Context mContext,String refreshToken){
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
		});
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
		MyApplication.getInstance().setUser(null);
	}
	
	private static void saveToken(Context mContext,String clientToken,String accessToken,String refreshToken){
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
