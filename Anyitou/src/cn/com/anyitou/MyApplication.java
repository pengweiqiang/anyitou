package cn.com.anyitou;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import cn.bidaround.ytcore.util.YtToast;

import cn.com.GlobalConfig;
import cn.com.anyitou.api.constant.ReqUrls;
import cn.com.anyitou.commons.Constant;
import cn.com.anyitou.entity.User;
import cn.com.anyitou.utils.JsonUtils;
import cn.com.anyitou.utils.SharePreferenceManager;
import cn.com.anyitou.utils.StringUtils;
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
	
	public static MyApplication getInstance() {
		return myApplication;
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
			String tokenStr = (String)SharePreferenceManager.getSharePreferenceValue(myApplication, Constant.FILE_NAME, ReqUrls.TOKEN, "");
			if(!StringUtils.isEmpty(userJson) && !StringUtils.isEmpty(tokenStr)){
				String tokens[] = tokenStr.split("_");
				long currentTime = System.currentTimeMillis();
				long saveCurrentTime = Long.parseLong(tokens[1]);
				if(currentTime - saveCurrentTime < 1000*60*60*24*5){//未超过5天
					//判断token的有效期(设置了一个礼拜,这里设置5天)
					user = JsonUtils.fromJson(userJson, User.class);
					GlobalConfig.TOKEN = tokens[0];
					
					gesturePwd = (String)SharePreferenceManager.getSharePreferenceValue(myApplication, Constant.FILE_NAME, user.getUser_name()+Constant.GESTURE_PWD, "");
					
				}
				
			}
		}catch(Exception e){
			
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
	/**
	 * 获取导航页
	 */
	/*public void getBoot(final Handler handler){
		
		ApiHomeUtils.getBoot(myApplication, new RequestCallback() {
			
			@Override
			public void execute(ParseModel parseModel) {
				if (!ApiConstants.RESULT_SUCCESS.equals(parseModel
						.getStatus())) {
					String dataStr = (String)SharePreferenceManager.getSharePreferenceValue(myApplication, Constant.FILE_NAME, "boot", "");
					if(!StringUtils.isEmpty(dataStr)){
						mImageView = JsonUtils.fromJson(dataStr, List.class);
					}
				} else {
					mImageView = JsonUtils.fromJson(parseModel.getData().toString(), List.class);
					SharePreferenceManager.saveBatchSharedPreference(myApplication, Constant.FILE_NAME, "boot", parseModel.getData().toString());
				}
				handler.sendEmptyMessage(1);
			}
		});
		
	}
*/
	

}
