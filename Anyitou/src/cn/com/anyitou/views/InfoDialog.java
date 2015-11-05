package cn.com.anyitou.views;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;
import cn.com.anyitou.R;


/**
 * 提示信息Dialog
 *
 * @author will
 *
 */
public class InfoDialog extends Dialog {

	
	
	private static final String TAG = "InfoDialog";

    public InfoDialog(Context context) {
        super(context);
    }


    public InfoDialog(Context context, int theme) {
        super(context, theme);
        Log.e(TAG, "InfoDialog");
        
    }
    
    public static class Builder {
    	private Context context;
    	private Button btn1;
    	private Button btn2;
    	
    	private String btnText1,btnText2;
    	private int backgroundDrawable = R.drawable.btn_left_concer_dialog_selector;
    	
    	private String message;
    	private String title;
    	
    	private TextView mTvTitle,mTvMessage;
    	
    	private DialogInterface.OnClickListener btn1ClickListener,btn2ClickListener;
    	
    	
    	public Builder(Context context) {
			this.context = context;
		}

		public Builder setTitle(String title){
    		this.title = title;
    		return this;
    	}
    	
    	public Builder setMessage(String message){
    		this.message = message;
    		return this;
    	}
    	
    	
    	public Builder setButton1(String btnText1,DialogInterface.OnClickListener listener){
    		return setButton1(btnText1, listener,R.drawable.btn_left_concer_dialog_selector);
    	}
    	public Builder setButton1(String btnText1,DialogInterface.OnClickListener listener,int backgroundDrawable){
    		this.btnText1 = btnText1;
    		this.btn1ClickListener = listener;
    		this.backgroundDrawable = backgroundDrawable;
    		return this;
    	}
    	
    	public Builder setButton2(String btnText2,DialogInterface.OnClickListener listener){
    		this.btnText2 = btnText2;
    		this.btn2ClickListener = listener;
    		return this;
    	}
    	
    	public InfoDialog create(){
    		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    		final InfoDialog dialog = new InfoDialog(context,R.style.Dialog);
    		View layout = inflater.inflate(R.layout.info_dialog, null);
    		dialog.setContentView(layout, new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
    		
    		((TextView) layout.findViewById(R.id.title)).setText(title); 
    		
    		// set the confirm button  
            if (btnText1 != null) {  
                ((Button) layout.findViewById(R.id.btn1))  
                        .setText(btnText1);  
                ((Button) layout.findViewById(R.id.btn1)).setBackgroundDrawable(context.getResources().getDrawable(backgroundDrawable));
                if (btn1ClickListener != null) {  
                    ((Button) layout.findViewById(R.id.btn1))  
                            .setOnClickListener(new View.OnClickListener() {  
                                public void onClick(View v) {  
                                	btn1ClickListener.onClick(dialog,  
                                            DialogInterface.BUTTON_POSITIVE);  
                                }  
                            });  
                }  
            } else {  
                // if no confirm button just set the visibility to GONE  
                layout.findViewById(R.id.btn1).setVisibility(  
                        View.GONE);  
            }  
            
         // set the cancel button  
            if (btnText2 != null) {  
                ((Button) layout.findViewById(R.id.btn2))  
                        .setText(btnText2);  
                if (btn2ClickListener != null) {  
                    ((Button) layout.findViewById(R.id.btn2))  
                            .setOnClickListener(new View.OnClickListener() {  
                                public void onClick(View v) {  
                                	btn2ClickListener.onClick(dialog,  
                                            DialogInterface.BUTTON_NEGATIVE);  
                                }  
                            });  
                }  
            } else {  
                // if no confirm button just set the visibility to GONE  
                layout.findViewById(R.id.btn2).setVisibility(  
                        View.GONE);  
            }  
            // set the content message  
            if (message != null) {  
                ((TextView) layout.findViewById(R.id.message)).setText(message);  
            }
            dialog.setContentView(layout);  
            dialog.setCanceledOnTouchOutside(false);
            
            return dialog;
    		
    	}
    	
    	
    	
    }
    
}