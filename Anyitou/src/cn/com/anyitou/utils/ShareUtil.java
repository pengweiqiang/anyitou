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
import cn.bidaround.ytcore.util.YtToast;
import cn.com.anyitou.R;
import cn.com.anyitou.ui.HomeActivity;

/**
 * 分享工具类
 * @author will
 *
 */
public class ShareUtil {
	
	private Context context;
	public ShareUtil(Context context){
		this.context = context;
		try{
		// 友推分享组件初始化
		YtCore.init((HomeActivity)context);
		initShareData();
		initPlatform();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/** 分享内容的封装数据 */
	private ShareData shareData;

	/** 每一个分享平台的名字 */
	private List<String> platformNames = new ArrayList<String>();
	
	
	public void share(int position,String text){
		if(!StringUtils.isEmpty(text)){
			shareData.setText(text);
			shareData.setDescription(text);
			if(position == 1){
				shareData.setTitle(text);
			}
		}
		
		for (YtPlatform platform : YtPlatform.values())
			if (platform.getTitleName(context).equals(platformNames.get(position)))
				// 指定平台进行分享
				YtCore.getInstance().share((HomeActivity)context, platform, listener, shareData);
			
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
		shareData.setDescription("安宜投");// 待分享内容的描述
		shareData.setTitle("安宜投"); // 待分享的标题
		shareData.setText("不开玩笑，投资5万元，30天净收550元利息，在安宜投理财平台，这是个真事儿！");// 待分享的文字
		shareData.setImage(ShareData.IMAGETYPE_APPRESOURE, String.valueOf(R.drawable.ic_launcher));// 设置网络分享地址
		shareData.setPublishTime(DateUtil.getDateString(new Date(), DateUtil.DAY_PATTERN));
		shareData.setTargetId(String.valueOf(100));
		shareData.setTargetUrl("http://www.anyitou.com");// 待分享内容的跳转链接
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
			YtToast.showS(context, "分享成功");

			Log.w("YouTui", platform.getName());

			/** 清理缓存 */
			HttpUtils.deleteImage(shareData.getImagePath());
		}

		/** 分享之前调用方法 */
		@Override
		public void onPreShare(YtPlatform platform) {
			YtToast.showS(context, "分享中...");

			Log.w("YouTui", platform.getName());
		}

		/** 分享失败回调方法 */
		@Override
		public void onError(YtPlatform platform, String error) {
			YtToast.showS(context, "分享错误");

			Log.w("YouTui", platform.getName());
			Log.w("YouTui", error);
			/** 清理缓存 */
			HttpUtils.deleteImage(shareData.getImagePath());
		}

		/** 分享取消回调方法 */
		@Override
		public void onCancel(YtPlatform platform) {
			YtToast.showS(context, "取消分享");

			Log.w("YouTui", platform.getName());
			/** 清理缓存 */
			HttpUtils.deleteImage(shareData.getImagePath());
		}
	};
	
}
