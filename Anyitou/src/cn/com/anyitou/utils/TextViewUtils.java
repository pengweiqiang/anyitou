package cn.com.anyitou.utils;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;

public class TextViewUtils {
	/**
	 * 改变textview指定位置的字体颜色
	 * @param str
	 * @param start
	 * @param length
	 * @param colorId
	 * @return
	 */
	public static SpannableString getSpannableStringColor(String str,int start,int length,int colorId){
		SpannableString ss = new SpannableString(str);
		
		ss.setSpan(new ForegroundColorSpan(colorId), start, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
		
		return ss;
	}
	/**
	 * 改变textview指定位置的字体大小
	 * @param str
	 * @param start
	 * @param length
	 * @param textSize
	 * @return
	 */
	public static SpannableString getSpannableStringSize(String str,int start,int length,int textSize){
		SpannableString ss = new SpannableString(str);
		ss.setSpan(new AbsoluteSizeSpan(textSize,true), start, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); 
		return ss;
	}
	/**
	 * 改变textview指定位置的字体大小、颜色
	 * @param str
	 * @param start
	 * @param length
	 * @param textSize
	 * @param colorId
	 * @return
	 */
	public static SpannableString getSpannableStringSizeAndColor(String str,int start,int length,int textSize,int colorId){
		SpannableString ss = new SpannableString(str);
		ss.setSpan(new ForegroundColorSpan(colorId), start, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); 
		ss.setSpan(new AbsoluteSizeSpan(textSize,true), start, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); 
		return ss;
	}
}
