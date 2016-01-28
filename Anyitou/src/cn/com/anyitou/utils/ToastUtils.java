package cn.com.anyitou.utils;


import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.anyitou.R;

public class ToastUtils {
	
	
	
	private static Toast mToast;
    private static Handler mHandler = new Handler();
    private static Runnable r = new Runnable() {
        public void run() {
            mToast.cancel();
        }
    };

    public static void showToast(Context mContext, String text) {
       
        mHandler.removeCallbacks(r);
        if (mToast != null){
        	TextView tvMessage = (TextView)mToast.getView().findViewById(R.id.message);
        	tvMessage.setText(text);
        }
        else{
        	LayoutInflater inflater = LayoutInflater.from(mContext);
		    View view=inflater.inflate(R.layout.toast_tips_loading, null);
		    TextView mTvMessage = (TextView)view.findViewById(R.id.message);
		    mTvMessage.setText(text);
//			Toast t = Toast.makeText(context, text, duration);
		    mToast = new Toast(mContext);
		    mToast.setDuration(Toast.LENGTH_SHORT);
		    mToast.setGravity(Gravity.CENTER, 0, 0);
		    mToast.setView(view);
		    mToast.show();
        }
        mHandler.postDelayed(r, 1000);

        mToast.show();
    }

    public static void showToast(Context mContext, int resId, int duration) {
        showToast(mContext, mContext.getResources().getString(resId), 1000);
    }
	
	
	
	public static void showToast(Context mContext,String text,int duration,int gravity){
		mHandler.removeCallbacks(r);
        if (mToast != null){
        	TextView tvMessage = (TextView)mToast.getView().findViewById(R.id.message);
        	tvMessage.setText(text);
        }
        else{
        	LayoutInflater inflater = LayoutInflater.from(mContext);
		    View view=inflater.inflate(R.layout.toast_tips_loading, null);
		    TextView mTvMessage = (TextView)view.findViewById(R.id.message);
		    mTvMessage.setText(text);
//			Toast t = Toast.makeText(context, text, duration);
		    mToast = new Toast(mContext);
		    if(duration == Toast.LENGTH_LONG){
		    	mToast.setDuration(Toast.LENGTH_LONG);
		    }else{
		    	mToast.setDuration(Toast.LENGTH_SHORT);
		    }
		    mToast.setGravity(Gravity.CENTER, 0, 0);
		    mToast.setView(view);
		    mToast.show();
        }
        mHandler.postDelayed(r, 1000);

        mToast.show();
	}
	
	public static void showToast(Context context ,int text){
		showToast(context, context.getResources().getText(text).toString(), Toast.LENGTH_SHORT, -1);
	}
	
	public static void showToast(Context context ,String text, int duration){
		showToast(context, text, duration, -1);
	}
	
	public static Dialog mDialog;
	
	private static String oldMsg; 
    protected static Toast toast   = null; 
    private static long oneTime=0; 
    private static long twoTime=0; 

    public synchronized static void showToastSingle(Context mContext, String text){     
    	mHandler.removeCallbacks(r);
        if (mToast != null){
        	TextView tvMessage = (TextView)mToast.getView().findViewById(R.id.message);
        	tvMessage.setText(text);
        }
        else{
        	LayoutInflater inflater = LayoutInflater.from(mContext);
		    View view=inflater.inflate(R.layout.toast_tips_loading, null);
		    TextView mTvMessage = (TextView)view.findViewById(R.id.message);
		    mTvMessage.setText(text);
//			Toast t = Toast.makeText(context, text, duration);
		    mToast = new Toast(mContext);
		    mToast.setDuration(Toast.LENGTH_SHORT);
		    mToast.setGravity(Gravity.CENTER, 0, 0);
		    mToast.setView(view);
		    mToast.show();
        }
        mHandler.postDelayed(r, 1000);

        mToast.show();
    } 


    public static void showToastSingle(Context context, int resId){    
        showToast(context, context.getString(resId)); 
    } 

}