package cn.com.anyitou.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.util.Log;
import cn.bidaround.ytcore.YtCore;
import cn.bidaround.ytcore.YtShareListener;
import cn.bidaround.ytcore.data.ShareData;
import cn.bidaround.ytcore.data.YtPlatform;
import cn.bidaround.ytcore.util.HttpUtils;
import cn.com.anyitou.R;
import cn.com.anyitou.ui.HomeActivity;

/**
 * 分享工具类
 * @author pengweiqiang
 *
 */
public class ShareUtil {
	
	private Context context;
	boolean isExist = false;
	public ShareUtil(Context context){
		this.context = context;
		try{
			if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
			{
				isExist = true;
				// 友推分享组件初始化
				YtCore.init((HomeActivity)context);
				initShareData();
				initPlatform();
			}
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/** 分享内容的封装数据 */
	private ShareData shareData;

	/** 每一个分享平台的名字 */
	private List<String> platformNames = new ArrayList<String>();
	
	
	public void share(int position,String text){
		share(position, text,"https://www.anyitou.com/wap/reg/register");
	}
	
	public void share(int position,String text,String inviteUrl){
		if(isExist){
			if(!StringUtils.isEmpty(text)){
				shareData.setText(text);
				shareData.setDescription(text);
				if(position == 1){
					shareData.setTitle(text);
				}else if(position == 5){
					shareData = new ShareData();
					shareData.setAppShare(false); // 是否为应用分享，如果为true，分享的数据需在友推后台设置
					shareData.setDescription("1000元＋58元现金券，注册就送，一般人我不告诉他...");// 待分享内容的描述
					shareData.setTitle("您的土豪朋友为您准备了58元现金礼包，速来领取！"); // 待分享的标题
					shareData.setText("1000元＋58元现金券，注册就送，一般人我不告诉他...");// 待分享的文字
					shareData.setPublishTime(DateUtil.getDateString(new Date(), DateUtil.DAY_PATTERN));
					shareData.setTargetId(String.valueOf(100));
					shareData.setTargetUrl(inviteUrl);// 待分享内容的跳转链接
				}
			}
			if(StringUtils.isEmpty(inviteUrl)){
				inviteUrl = "https://www.anyitou.com/wap/reg/register";
			}
			shareData.setTargetUrl(inviteUrl);
			
			for (YtPlatform platform : YtPlatform.values())
				if (platform.getTitleName(context).equals(platformNames.get(position)))
					// 指定平台进行分享
					YtCore.getInstance().share((HomeActivity)context, platform, listener, shareData);
		}else{
			ToastUtils.showToast(context, "SD卡不存在");
		}
	}
	/**
	 * 初始化数据配置 说明：
	 * 1、如果有设置链接TargetUrl，就必须设置参数：Title、Description、PublishTime、TargetId
	 * 、ImageUrl，否则无法分享；目的是在友推后台可以统计分享链接子页面 1）Title分享内容的标题 2）Description
	 * 分享内容的简单描述信息 3）PublishTime 分享信息的发布时间, 会显示在子页面统计的发布时间栏上，格式为2014-02-17
	 * 4）TargetId 分享内容的在用户系统的中主键id，用来区分子页面，如果的TargetId会显示多栏，如果相同，会增加该子页面的分享量
	 * 5）ImageUrl 分享图片的地址，可以通过setImage(ShareData.IMAGETYPE_INTERNET,
	 * IMAGEURL)方式设置
	 */
	public void initShareData() {
		shareData = new ShareData();
		shareData.setAppShare(false); // 是否为应用分享，如果为true，分享的数据需在友推后台设置
		shareData.setDescription("1000元＋58元现金券，注册就送，一般人我不告诉他...");// 待分享内容的描述
		shareData.setTitle("您的土豪朋友为您准备了58元现金礼包，速来领取！"); // 待分享的标题
		shareData.setText("1000元＋58元现金券，注册就送，一般人我不告诉他...");// 待分享的文字
		shareData.setImage(ShareData.IMAGETYPE_APPRESOURE, String.valueOf(R.drawable.share_logo));// 设置网络分享地址
		shareData.setPublishTime(DateUtil.getDateString(new Date(), DateUtil.DAY_PATTERN));
		shareData.setTargetId(String.valueOf(100));
		shareData.setTargetUrl("https://www.anyitou.com/wap/reg/register");// 待分享内容的跳转链接
	}

	/**
	 * 初始化分享平台的名字
	 */
	private void initPlatform() {
		for (YtPlatform platform : YtPlatform.values())
			// 截屏分享在自定义界面中不适用
			if (platform != YtPlatform.PLATFORM_SCREENCAP)
				platformNames.add(platform.getTitleName(context));
	}
	
	/**
	 * 设置分享监听器
	 */

	YtShareListener listener = new YtShareListener() {

		/** 分享成功后回调方法 */
		@Override
		public void onSuccess(YtPlatform platform, String result) {
//			YtToast.showS(context, "分享成功");
			ToastUtils.showToastSingle(context, "分享成功");

			Log.w("YouTui", platform.getName());

			/** 清理缓存 */
			HttpUtils.deleteImage(shareData.getImagePath());
		}

		/** 分享之前调用方法 */
		@Override
		public void onPreShare(YtPlatform platform) {
//			YtToast.showS(context, "分享中...");
			ToastUtils.showToastSingle(context, "分享中...");

			Log.w("YouTui", platform.getName());
		}

		/** 分享失败回调方法 */
		@Override
		public void onError(YtPlatform platform, String error) {
//			YtToast.showS(context, "分享错误");
			ToastUtils.showToastSingle(context, "分享错误");

			Log.w("YouTui", platform.getName());
			Log.w("YouTui", error);
			/** 清理缓存 */
			HttpUtils.deleteImage(shareData.getImagePath());
		}

		/** 分享取消回调方法 */
		@Override
		public void onCancel(YtPlatform platform) {
//			YtToast.showS(context, "取消分享");
			ToastUtils.showToastSingle(context, "取消分享");

			Log.w("YouTui", platform.getName());
			/** 清理缓存 */
			HttpUtils.deleteImage(shareData.getImagePath());
		}
	};
	
}
