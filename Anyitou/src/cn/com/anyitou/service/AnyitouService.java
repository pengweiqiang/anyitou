package cn.com.anyitou.service;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Looper;
import cn.com.GlobalConfig;
import cn.com.anyitou.MyApplication;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.TokenUtil;

public class AnyitouService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	private boolean isStarted = false;
	@Override
	public void onCreate() {
		super.onCreate();
		timer = new Timer();
		long perid = Math.round(1000*60*60*1.5);
		timer.schedule(task, perid, perid);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		timer.cancel();
	}
	Timer timer;
	TimerTask task = new TimerTask() {

		@Override
		public void run() {
			try{
				if(!isStarted){
					Looper.prepare();
					isStarted = true;
				}
				cn.com.anyitou.utils.Log.e("Token", "-------刷新token----------");
				TokenUtil.getClientToken(AnyitouService.this,true,null);
				if(MyApplication.getInstance().getCurrentUser() != null && !StringUtils.isEmpty(GlobalConfig.REFRESH_TOKEN)){
					TokenUtil.refreshToken(AnyitouService.this, GlobalConfig.REFRESH_TOKEN,true);
				}
//				if(isStarted){
//					Looper.loop();
//				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}

	};

}
