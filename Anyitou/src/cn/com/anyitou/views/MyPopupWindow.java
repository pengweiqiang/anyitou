package cn.com.anyitou.views;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import cn.com.anyitou.R;

public class MyPopupWindow{
	
	public static PopupWindow getPopupWindow(int layoutViewId,final Activity mActivity){
		return getPopupWindow(layoutViewId, mActivity, R.style.popupAnimation);
	}
	public static Activity mActivity2;
	public static PopupWindow getPopupWindow(int layoutViewId,final Activity mActivity,int animation){
		LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		mActivity2 = mActivity;
		View popupWindowView = inflater.inflate(layoutViewId, null);
		final PopupWindow popupWindow = new PopupWindow(popupWindowView,
				LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT,
				true);
		popupWindow.setWidth(LayoutParams.MATCH_PARENT);
		popupWindow.setHeight(LayoutParams.WRAP_CONTENT);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
//		ColorDrawable cd = new ColorDrawable(0xb0000000);
//		popupWindow.setBackgroundDrawable(cd);
		popupWindow.setTouchable(true);
		popupWindow.setFocusable(true);
		setBackgroundAlpha(mActivity, 0.5f);
		// 设置PopupWindow的弹出和消失效果
		if(animation != 0){
			popupWindow.setAnimationStyle(R.style.popupAnimation);
		}
		
		popupWindow.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				setBackgroundAlpha(mActivity, 1f);
			}
		});
		return popupWindow;
	}
	public static void setBackgroundAlpha(final Activity mActivity,final float alpha){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				if(alpha<1f){
					for(float i = 0.95f;i>=alpha;i=i-0.01f){
						Message msg = new Message();
						msg.obj = i;
						handler.sendMessage(msg);
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}else {
					for(float i = 0.5f;i<=alpha;i=i+0.01f){
						Message msg = new Message();
						msg.obj = i;
						handler.sendMessage(msg);
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}).start();
		
	}
	private static  Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			float i = (Float)msg.obj;
			WindowManager.LayoutParams lp = mActivity2.getWindow().getAttributes();
			lp.alpha = i;
			mActivity2.getWindow().setAttributes(lp);
		}
		
	};
}
