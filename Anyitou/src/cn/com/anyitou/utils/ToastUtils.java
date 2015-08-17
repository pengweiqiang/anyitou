package cn.com.anyitou.utils;


import android.app.Dialog;
import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
	
	public static void showToast(Context context ,String text){
		Toast t = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		t.show();
	}
	
	public static void showToast(Context context ,int text){
		Toast t = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		t.show();
	}
	
	public static void showToast(Context context ,String text, int duration){
		Toast t = Toast.makeText(context, text, duration);
		t.show();
	}
	
	public static Dialog mDialog;

}