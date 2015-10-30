package cn.com.anyitou.views;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import cn.com.anyitou.R;

public class MyPopupWindow{
	
	public static PopupWindow getPopupWindow(int layoutViewId,final Activity mActivity){
		LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View popupWindowView = inflater.inflate(layoutViewId, null);
		final PopupWindow popupWindow = new PopupWindow(popupWindowView,
				LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT,
				true);
		popupWindow.setWidth(LayoutParams.MATCH_PARENT);
		popupWindow.setHeight(LayoutParams.WRAP_CONTENT);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
//		ColorDrawable cd = new ColorDrawable(0xb0000000);
//		popupWindow.setBackgroundDrawable(cd);
		popupWindow.setTouchable(true);
		popupWindow.setFocusable(true);
		setBackgroundAlpha(mActivity, 0.4f);
		// 设置PopupWindow的弹出和消失效果
		popupWindow.setAnimationStyle(R.style.popupAnimation);
		
		popupWindow.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				setBackgroundAlpha(mActivity, 1f);
			}
		});
		return popupWindow;
	}
	public static void setBackgroundAlpha(Activity mActivity,float alpha){
		WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
		lp.alpha = alpha;
		mActivity.getWindow().setAttributes(lp);
	}
}
