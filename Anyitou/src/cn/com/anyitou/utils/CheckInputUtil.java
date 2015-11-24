package cn.com.anyitou.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.com.anyitou.R;

import android.content.Context;

/**
 * 检索输入条件
 * @author will
 *
 */
public class CheckInputUtil {

	/**
	 * 用户名必须是6-25位的英文、数字、特殊字符@._-组合
	 * @param username
	 */
	static String regEx = "[\u4e00-\u9fa5]";
	static Pattern pat = Pattern.compile(regEx);
	public static boolean checkUser(String username,Context context){
		boolean flag = true;
		String returnStr = "";
		Matcher matcher = pat.matcher(username);
		if(StringUtils.isEmpty(username)){
			returnStr = "请输入手机号";
		}else if(username.length()<6 || username.length()>25){
			returnStr = "手机号码格式错误";
		}else if(matcher.find()){
			returnStr = "手机号码格式错误";
		}
		if(!"".equals(returnStr)){
			flag = false;
			ToastUtils.showToast(context, returnStr);
		}
		
		return flag;
	}
	/**
	 * 密码必须是6-20个字符长度
	 * @param password
	 */
	public static boolean checkPassword(String password,Context context){
		boolean flag = true;
		String returnStr = "";
		if(StringUtils.isEmpty(password)){
			returnStr = context.getResources().getString(R.string.input_pwd);
		}else if(password.length()<6 || password.length()>20){
			returnStr = context.getResources().getString(R.string.error_pwd);
		}
		
		if(!"".equals(returnStr)){
			flag = false;
			ToastUtils.showToast(context, returnStr);
		}
		
		return flag;
	}
	/**
	 * 检测手机号
	 * @param phone
	 * @return
	 */
	public static boolean checkPhone(String phone,Context context){
		String returnStr = "";
		boolean flag = true;
		if(StringUtils.isEmpty(phone)){
			returnStr = context.getResources().getString(R.string.input_phone);
		}else if(phone.length()!=11){
			returnStr = context.getResources().getString(R.string.error_phone);
		}
		if(!"".equals(returnStr)){
			flag = false;
			ToastUtils.showToast(context, returnStr);
		}
		
		return flag;
	}
}
