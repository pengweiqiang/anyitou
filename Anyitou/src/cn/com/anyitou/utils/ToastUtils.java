package cn.com.anyitou.utils;


import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.anyitou.R;

public class ToastUtils {
	
	public static void showToast(Context context ,String text){
		showToast(context, text,Toast.LENGTH_SHORT,-1);
	}
	public static void showToast(Context context,String text,int duration,int gravity){
		if(!StringUtils.isEmpty(text)){
			LayoutInflater inflater = LayoutInflater.from(context);
		    View view=inflater.inflate(R.layout.toast_tips_loading, null);
		    TextView mTvMessage = (TextView)view.findViewById(R.id.message);
		    mTvMessage.setText(text);
//			Toast t = Toast.makeText(context, text, duration);
		    Toast t = new Toast(context);
		    t.setDuration(duration);
		    t.setView(view);
			if(gravity == -1){
				t.setGravity(Gravity.CENTER, 0, 0);
			}
			t.show();
		}
	}
	
	public static void showToast(Context context ,int text){
//		Toast t = Toast.makeText(context, text, Toast.LENGTH_SHORT);
//		t.setGravity(Gravity.CENTER, 0, 0);
//		t.show();
		LayoutInflater inflater = LayoutInflater.from(context);
	    View view=inflater.inflate(R.layout.toast_tips_loading, null);
	    TextView mTvMessage = (TextView)view.findViewById(R.id.message);
	    mTvMessage.setText(text);
//		Toast t = Toast.makeText(context, text, duration);
	    Toast t = new Toast(context);
	    t.setDuration(Toast.LENGTH_SHORT);
	    t.setGravity(Gravity.CENTER, 0, 0);
	    t.setView(view);
	    t.show();
	}
	
	public static void showToast(Context context ,String text, int duration){
		showToast(context, text, duration, -1);
	}
	
	public static Dialog mDialog;
	
	private static String oldMsg; 
    protected static Toast toast   = null; 
    private static long oneTime=0; 
    private static long twoTime=0; 

    public static void showToastSingle(Context context, String s){     
        if(toast==null){  
//            toast =Toast.makeText(context, s, Toast.LENGTH_SHORT); 
//            toast.setGravity(Gravity.CENTER, 0, 0);
//            toast.show(); 
        	LayoutInflater inflater = LayoutInflater.from(context);
		    View view=inflater.inflate(R.layout.toast_tips_loading, null);
		    TextView mTvMessage = (TextView)view.findViewById(R.id.message);
		    mTvMessage.setText(s);
//			Toast t = Toast.makeText(context, text, duration);
		    Toast toast = new Toast(context);
		    toast.setDuration(Toast.LENGTH_SHORT);
		    toast.setGravity(Gravity.CENTER, 0, 0);
		    toast.setView(view);
		    toast.show();
            oneTime=System.currentTimeMillis(); 
        }else{ 
            twoTime=System.currentTimeMillis(); 
            if(s.equals(oldMsg)){ 
                if(twoTime-oneTime>Toast.LENGTH_SHORT){ 
                	toast.setGravity(Gravity.CENTER, 0, 0);
                	toast.setDuration(Toast.LENGTH_SHORT);
                    toast.show(); 
                } 
            }else{ 
                oldMsg = s; 
                toast.setText(s); 
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show(); 
            }        
        } 
        oneTime=twoTime; 
    } 


    public static void showToastSingle(Context context, int resId){    
        showToast(context, context.getString(resId)); 
    } 

}