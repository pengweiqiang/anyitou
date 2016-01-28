package cn.com.anyitou.receiver;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.style.BulletSpan;
import cn.com.anyitou.MyApplication;
import cn.com.anyitou.api.ApiMessageUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.api.constant.Push2Page;
import cn.com.anyitou.commons.Constant;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.ui.HomeActivity;
import cn.com.anyitou.ui.SplashActivity;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.Log;
import cn.com.anyitou.utils.SharePreferenceManager;
import cn.com.anyitou.utils.StringUtils;
import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class JPushReceiver extends BroadcastReceiver {
	private static final String TAG = "JPush";
	

	@Override
	public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
		Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
		
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
        	String regId = "";
        	if(bundle!=null && bundle.containsKey(JPushInterface.EXTRA_REGISTRATION_ID)){
        		regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
        	}else{
        		regId = JPushInterface.getRegistrationID(context);
        	}
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...
            registerId(context, regId);
                        
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
        	Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
        
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
        	
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
            startPushActivity(bundle, context);
            
//        	//打开自定义的Activity
//        	Intent i = new Intent(context, TestActivity.class);
//        	i.putExtras(bundle);
//        	//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
//        	context.startActivity(i);
        	
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
            startHomeActivity(context);
        	
        } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
        	boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
        	Log.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
        } else {
        	Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
	}
	
	private void startPushActivity(Bundle bundle,Context context){
		if(bundle!=null){
			Intent i = new Intent(context, SplashActivity.class);
			i.putExtras(bundle);
	    	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
	    	context.startActivity(i);
		}else{
			 startHomeActivity(context);
		}
	}
	
	private void startHomeActivity(Context context){
	   	Intent i = new Intent(context, SplashActivity.class);
    	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
    	context.startActivity(i);
	}
	

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		
		StringBuilder sb = new StringBuilder();
		if(bundle!=null){
			for (String key : bundle.keySet()) {
				if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
					sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
				}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
					sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
				} else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
					if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
						Log.i(TAG, "This message has no Extra data");
						continue;
					}
	
					try {
						JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
						Iterator<String> it =  json.keys();
	
						while (it.hasNext()) {
							String myKey = it.next().toString();
							sb.append("\nkey:" + key + ", value: [" +
									myKey + " - " +json.optString(myKey) + "]");
						}
					} catch (JSONException e) {
						Log.e(TAG, "Get message extra JSON error!");
					}
	
				} else {
					sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
				}
			}
		}
		return sb.toString();
		
	}
	//获取regId传给服务器
	private void registerId(final Context context,final String regId){
		String regIdCache = (String)SharePreferenceManager.getSharePreferenceValue(context, Constant.FILE_NAME, "jpush_reg", "");
		if(!StringUtils.isEmpty(regIdCache)){//已经注册过
			return;
		}
		if(MyApplication.getInstance().getCurrentUser() ==null){//未登陆不能注册jpush
			return;
		}
		final String alias = MyApplication.getInstance().getCurrentUser().getUsername();
		ApiMessageUtils.pushBindingDevice(context, regId, alias, new RequestCallback() {
			@Override
			public void execute(ParseModel parseModel) {
				if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
					SharePreferenceManager.saveBatchSharedPreference(context, Constant.FILE_NAME, "jpush_reg", regId);
				}
			}
		});
	}
	
}