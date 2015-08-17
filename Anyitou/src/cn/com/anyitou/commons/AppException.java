package cn.com.anyitou.commons;

import java.io.IOException;
import java.io.Serializable;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.apache.http.HttpException;

import android.content.Context;
import android.widget.Toast;

import cn.com.anyitou.R;

/**
 * 应用程序异常类：用于捕获异常和提示错误信
 */
public class AppException extends Exception implements UncaughtExceptionHandler, Serializable {
	private static final long serialVersionUID = -6552346351298814195L;
	public static final String NO_ERROR = "600"; //
	public static final String ERR_DATA_EMPTY = "601";
	@SuppressWarnings("unused")
	private final static boolean Debug = false;// 是否保存错误日志
	
	/** 定义异常类型 */
	public final static byte TYPE_NETWORK = 0x01;
	public final static byte TYPE_SOCKET = 0x02;
	public final static byte TYPE_HTTP_CODE = 0x03;
	public final static byte TYPE_HTTP_ERROR = 0x04;
	public final static byte TYPE_JSON = 0x05;
	public final static byte TYPE_IO = 0x06;
	public final static byte TYPE_RUN = 0x07;
	
	public final static String ERROR_LOG_NAME = "errorlog.txt";
	public final static String TAG = "repoException";
	
	private byte type;
	private int code;
	
	/** 系统默认的UncaughtException处理�? */
	private AppException() {
		
	}
	
	private AppException(byte type, int code, Exception excp) {
		super(excp);
		this.type = type;
		this.code = code;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public int getType() {
		return this.type;
	}
	
	/**
	 * 提示友好的错误信息
	 * 
	 * @param ctx
	 */
	public void makeToast(Context ctx) {
		switch (this.getType()) {
		case TYPE_HTTP_CODE:
			String err = ctx.getString(R.string.NETWORK_ERROR);
			Toast.makeText(ctx, err, Toast.LENGTH_SHORT).show();
			break;
		case TYPE_HTTP_ERROR:
			Toast.makeText(ctx, R.string.NETWORK_ERROR, Toast.LENGTH_SHORT).show();
			break;
		case TYPE_SOCKET:
			Toast.makeText(ctx, R.string.NETWORK_ERROR, Toast.LENGTH_SHORT).show();
			break;
		case TYPE_NETWORK:
			Toast.makeText(ctx, R.string.NETWORK_ERROR, Toast.LENGTH_SHORT).show();
			break;
		case TYPE_JSON:
			Toast.makeText(ctx, R.string.NETWORK_ERROR, Toast.LENGTH_SHORT).show();
			break;
		case TYPE_IO:
			Toast.makeText(ctx, R.string.NETWORK_ERROR, Toast.LENGTH_SHORT).show();
			break;
		case TYPE_RUN:
			Toast.makeText(ctx, R.string.NETWORK_ERROR, Toast.LENGTH_SHORT).show();
			break;
		}
	}
	
	public static AppException http(int code) {
		return new AppException(TYPE_HTTP_CODE, code, null);
	}
	
	public static AppException http(Exception e) {
		return new AppException(TYPE_HTTP_ERROR, 0, e);
	}
	
	public static AppException socket(Exception e) {
		return new AppException(TYPE_SOCKET, 0, e);
	}
	
	public static AppException io(Exception e) {
		if (e instanceof UnknownHostException || e instanceof ConnectException) {
			return new AppException(TYPE_NETWORK, 0, e);
		} else if (e instanceof IOException) {
			return new AppException(TYPE_IO, 0, e);
		}
		return run(e);
	}
	
	public static AppException json(Exception e) {
		return new AppException(TYPE_JSON, 0, e);
	}
	
	public static AppException network(Exception e) {
		if (e instanceof UnknownHostException || e instanceof ConnectException) {
			return new AppException(TYPE_NETWORK, 0, e);
		} else if (e instanceof HttpException) {
			return http(e);
		} else if (e instanceof SocketException) {
			return socket(e);
		}
		return http(e);
	}
	
	public static AppException run(Exception e) {
		return new AppException(TYPE_RUN, 0, e);
	}
	
	/**
	 * 获取APP异常崩溃处理对象
	 * 
	 * @param context
	 * @return
	 */
	public static AppException getAppExceptionHandler() {
		return new AppException();
	}
	
	/*
	 * 接口UncaughtExceptionHandler 实现的方�?
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		ex.printStackTrace();
	}
	
	public static void handleException(Context con, Object paramObject) {
		if (paramObject != null) {
			if (paramObject instanceof AppException) {
				AppException appException = (AppException) paramObject;
				appException.makeToast(con);
			}
		}
	}
}
