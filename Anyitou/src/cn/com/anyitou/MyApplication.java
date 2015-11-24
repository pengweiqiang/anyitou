package cn.com.anyitou;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import cn.com.GlobalConfig;
import cn.com.anyitou.api.constant.ReqUrls;
import cn.com.anyitou.commons.Constant;
import cn.com.anyitou.entity.MyCapital;
import cn.com.anyitou.entity.User;
import cn.com.anyitou.utils.JsonUtils;
import cn.com.anyitou.utils.SharePreferenceManager;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.TokenUtil;
import cn.com.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import cn.com.universalimageloader.cache.memory.MemoryCacheAware;
import cn.com.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import cn.com.universalimageloader.core.DisplayImageOptions;
import cn.com.universalimageloader.core.ImageLoader;
import cn.com.universalimageloader.core.ImageLoaderConfiguration;
import cn.com.universalimageloader.core.assist.ImageScaleType;
import cn.com.universalimageloader.core.assist.QueueProcessingType;

import com.umeng.analytics.MobclickAgent;

public class MyApplication extends Application {

	public static String TAG;
	
	public int refresh = 0;// 0 代表不刷新我的资产 1代表充值成功刷新  2 代表提现成功刷新

	private static MyApplication myApplication = null;
	public String gesturePwd = "";//手势密码
	public boolean isLock = false;//是否解锁
	public Class classLast =null;
	private User user;
	private MyCapital myCapital;
	
	public static MyApplication getInstance() {
		return myApplication;
	}

	public MyCapital getMyCapital() {
		return myCapital;
	}

	public void setMyCapital(MyCapital myCapital) {
		this.myCapital = myCapital;
	}


	public void setUser(User user) {
		this.user = user;
	}

	public User getCurrentUser() {
		return user;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		TAG = this.getClass().getSimpleName();
		// 由于Application类本身已经单例，所以直接按以下处理即可。
		myApplication = this;
		try{
			String userJson = (String)SharePreferenceManager.getSharePreferenceValue(myApplication, Constant.FILE_NAME, "user", "");
			String clientTokenStr = (String)SharePreferenceManager.getSharePreferenceValue(myApplication, Constant.FILE_NAME, ReqUrls.CLIENT_CREDENTIALS, "");
			String tokenStr = (String)SharePreferenceManager.getSharePreferenceValue(myApplication, Constant.FILE_NAME, ReqUrls.ACCESS_TOKEN, "");
			
			
			if(!StringUtils.isEmpty(clientTokenStr)){
				String clientTokens[] = clientTokenStr.split("_");
				long currentTime = System.currentTimeMillis();
				long saveCurrentTime = Long.parseLong(clientTokens[1]);
				if(Math.abs(currentTime - saveCurrentTime) < 1000*60*60*1.5){
					GlobalConfig.CLIENT_TOKEN = clientTokens[0];
				}
				TokenUtil.getClientToken(myApplication);
				
			}else{
				TokenUtil.getClientToken(myApplication);
			}
			
			
			if(!StringUtils.isEmpty(userJson) && !StringUtils.isEmpty(tokenStr)){
				String refreshTokenStr = (String)SharePreferenceManager.getSharePreferenceValue(myApplication, Constant.FILE_NAME, ReqUrls.REFRESH_TOKEN, "");
				String tokens[] = tokenStr.split("_");
				String refreshTokens[] = refreshTokenStr.split("_");
				
				GlobalConfig.REFRESH_TOKEN = refreshTokens[0];
				long currentTime = System.currentTimeMillis();
				long saveCurrentTime = Long.parseLong(tokens[1]);
				if(Math.abs(currentTime - saveCurrentTime) < 1000*60*60*1.5){
					//判断token的有效期(设置了2个小时,这里设置1.5小时)
					user = JsonUtils.fromJson(userJson, User.class);
					GlobalConfig.ACCESS_TOKEN = tokens[0];
					
				}else if(!StringUtils.isEmpty(userJson)){//重新刷新token
					if(!StringUtils.isEmpty(GlobalConfig.REFRESH_TOKEN)){
						long saveRefreshTokenTime = Long.parseLong(tokens[1]);
						if(Math.abs(currentTime - saveRefreshTokenTime) < 1000*60*60*24*20){//未超过登陆20天
							user = JsonUtils.fromJson(userJson, User.class);
							TokenUtil.refreshToken(myApplication, GlobalConfig.REFRESH_TOKEN);
						}
					}
				}
				String gesturePwds = (String)SharePreferenceManager.getSharePreferenceValue(myApplication, Constant.FILE_NAME, user.getUsername()+Constant.GESTURE_PWD, "");
				if(!StringUtils.isEmpty(gesturePwds)){
					String gpwds [] = gesturePwds.split(",");
					long distance = System.currentTimeMillis()-Long.valueOf(gpwds[0]);
					gesturePwd = gpwds[1];
					if(distance<1*60*60*1000){
						isLock = true;
					}else{
						isLock = false;
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		initImageLoader(this);
		MobclickAgent.openActivityDurationTrack(false);
		
	}

	/**
	 * 初始化imageLoader
	 */
	public void initImageLoader(Context context) {
		int memoryCacheSize = (int) (Runtime.getRuntime().maxMemory() / 8);

		MemoryCacheAware<String, Bitmap> memoryCache;

		memoryCache = new LRULimitedMemoryCache(memoryCacheSize);

		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.memoryCache(memoryCache).denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}

	public DisplayImageOptions getOptions(int drawableId) {
		return new DisplayImageOptions.Builder().showImageOnLoading(drawableId)
				.showImageForEmptyUri(drawableId).showImageOnFail(drawableId)
				.resetViewBeforeLoading(true).cacheInMemory(true)
				.cacheOnDisc(true).imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
	}
	public DisplayImageOptions getOptions2(int drawableId) {
		return new DisplayImageOptions.Builder().showImageOnLoading(drawableId)
				.showImageForEmptyUri(drawableId).showImageOnFail(drawableId)
				.resetViewBeforeLoading(true).cacheInMemory(true)
				.cacheOnDisc(true).imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.ARGB_8888).build();
	}
//	/**
//	 * 获取客户端授权access_token
//	 */
//	public void oauth(){
//		
//		ApiUserUtils.oauthAccessToken(myApplication, ReqUrls.CLIENT_CREDENTIALS, "", "", "", new RequestCallback() {
//			
//			@Override
//			public void execute(ParseModel parseModel) {
//				String accessToken = parseModel.getAccess_token();
//				if(!StringUtils.isEmpty(accessToken)){
//					GlobalConfig.CLIENT_TOKEN = accessToken;
//				}
//			}
//		});
//		
//	}

	

}
