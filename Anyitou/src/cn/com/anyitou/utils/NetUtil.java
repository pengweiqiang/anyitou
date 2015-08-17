package cn.com.anyitou.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import cn.com.anyitou.R;

import cn.com.GlobalConfig;
import cn.com.anyitou.api.constant.ReqUrls;

/**
 * 网络连接
 * 
 */
public class NetUtil {
	public static final int CONNECT_TIMEOUT = 5000;
	public static final int READ_TIMEOUT = 5000;
	private static final int MAX_RECONNECT_TIMES = 3;
	public static final int HTTP_PORT = 80;
	
	/** 服务端返回状态 **/
	public static final int SUCCESS_CODE = 1;
	public static final int FAIL_CODE = 500;
	public static final int FAIL_CODE_400 = 400;
	
	public static final int NET_QUERY_SUCC = 201;
	public static final int NET_ERR = 777;
	public static final String NET_ERR_MSG = "网络不给力呀，稍后试试吧！";
	public static final String SERVICE_ERR_MSG = "服务器异常，稍后试试吧！";
	public static final String SERVICE_SUCCESS_MSG = "提交成功";
	public static final int NET_EXC_ERR = 888;
	public static final int NET_REP_HOME = 889;
	public static final int NET_REQUEST_TIME_OUT = 10000; // 连接超时
	public static final int NET_CONN_EXCEPTION = 9001;
	private static final int UNUSE_HANDLER_CODE = -9999;
	private static HttpParams sHttpParamters;
	
	private static Context mContext;
	private static String sCharAnd = "&";
	private static String sCharEqual = "=";
	private static String sCharQuestion = "?";
	
	public static String HOST_IP = "";
	
	private static String TAG = "NetUtil";
	
	static {
		sHttpParamters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(sHttpParamters, CONNECT_TIMEOUT);
		HttpConnectionParams.setSoTimeout(sHttpParamters, READ_TIMEOUT);
	}
	
	public static void setContext(Context context) {
		mContext = context;
	}
	
	/**
	 * get请求
	 * 
	 * @param url
	 * @param mHandler
	 */
	public static void getInfoByGet(final String url, final Handler mHandler) {
		ThreadUtil.getTheadPool(true).submit(new Runnable() {
			@Override
			public void run() {
				doHttpGet(url, mHandler, UNUSE_HANDLER_CODE);
			}
		});
	}
	
	public static synchronized void getInfoByGet(final String url, final Handler mHandler, final int what) {
		ThreadUtil.getTheadPool(true).submit(new Runnable() {
			@Override
			public void run() {
				doHttpGet(url, mHandler, what);
			}
		});
	}
	
	/**
	 * get请求
	 * 
	 * @param url
	 * @param mHandler
	 */
	private static void doHttpGet(final String url, final Handler mHandler, final int what) {
		try {
			HttpGet method = new HttpGet(url);
			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, NET_REQUEST_TIME_OUT);
			HttpConnectionParams.setSoTimeout(httpParams, NET_REQUEST_TIME_OUT);
			DefaultHttpClient client = new DefaultHttpClient(httpParams);
			method.setHeader("Connection", "Keep-Alive");
			HttpResponse response = client.execute(method);
			int status = response.getStatusLine().getStatusCode();
			String netInfo = "";
			if (status == HttpStatus.SC_OK || status == NetUtil.NET_QUERY_SUCC) {
				netInfo = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
			}
			if (UNUSE_HANDLER_CODE == what) {
				Message message = mHandler.obtainMessage(status, netInfo);
				mHandler.sendMessage(message);
			} else {
				Message message = mHandler.obtainMessage(what, netInfo);
				mHandler.sendMessage(message);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			mHandler.sendMessage(mHandler.obtainMessage(NET_CONN_EXCEPTION));
		}
	}
	
	/**
	 * HTTP.POST请求,无参数
	 */
	public static String getNetInfoByPost(final String url, final Handler handler) {
		if (handler != null) { // 异步
			getNetInfoByPost(mContext, url, null, handler, null);
		} else { // 同步
			return getNetInfoByPostSync(mContext, url, null);
		}
		return null;
	}
	
	/**
	 * HTTP.POST请求
	 */
	public static String getNetInfoByPost(final String url, final Map<String, String> postInfo, final Handler handler) {
		if (handler != null) { // 异步
			getNetInfoByPost(mContext, url, postInfo, handler, null);
		} else { // 同步
			return getNetInfoByPostSync(mContext, url, postInfo);
		}
		return null;
	}
	
//	public static String getNetInfoByPost2(final String url, final Map<String, HttpBodyData> postInfo,
//			final Handler handler) {
//		return getNetInfoByPostSync2(mContext, url, postInfo);
//	}
	
	private final static HttpPost makeHttpPost(String srcUrl, HttpParams mHttpParameters) {
		mHttpParameters = new BasicHttpParams();
		HttpPost mHttpPost = new HttpPost(srcUrl);
		// 设置超时时间
		HttpConnectionParams.setConnectionTimeout(mHttpParameters, NetUtil.CONNECT_TIMEOUT);
		HttpConnectionParams.setSoTimeout(mHttpParameters, NetUtil.CONNECT_TIMEOUT);
		mHttpPost.setParams(mHttpParameters);
		return mHttpPost;
	}
	
	private static DefaultHttpClient makeHttpClient(HttpParams mHttpParameters) {
		DefaultHttpClient mHttpClient = new DefaultHttpClient(mHttpParameters);
		return mHttpClient;
	}
	
//	private static String getNetInfoByPostSync2(final Context context, final String srcUrl,
//			final Map<String, HttpBodyData> postInfo) {
//		HttpPost mHttpPost = null;
//		HttpParams mHttpParameters = null;
//		DefaultHttpClient mHttpClient = null;
//		mHttpPost = makeHttpPost(srcUrl, mHttpParameters);
//		mHttpClient = makeHttpClient(mHttpParameters);
//		HttpResponse response = null;
//		Log.d(TAG, "-------发出的url请求-----------" + srcUrl);
//		MultipartEntity entity = getMultipartEntity(postInfo);
//		mHttpPost.setEntity(entity);
//		String backStr = "";
//		try {
//			response = mHttpClient.execute(mHttpPost);
//			int statusCode = response.getStatusLine().getStatusCode();
//			backStr = String.valueOf(statusCode);
//			if (statusCode == HttpStatus.SC_OK || statusCode == NetUtil.NET_QUERY_SUCC) {
//				backStr = EntityUtils.toString(response.getEntity());
//				return backStr;
//			}
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (ParseException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return backStr;
//	}
	
	private static String getNetInfoByPostSync(final Context context, final String srcUrl,
			final Map<String, String> postInfo) {
		HttpPost mHttpPost = null;
		HttpParams mHttpParameters = null;
		DefaultHttpClient mHttpClient = null;
		mHttpPost = makeHttpPost(srcUrl, mHttpParameters);
		mHttpClient = makeHttpClient(mHttpParameters);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		HttpResponse response = null;
		Log.d(TAG, "-------发出的url请求-----------" + srcUrl);
		if (postInfo != null) {
			Set<String> keys = postInfo.keySet();
			for (String key : keys) {
				Log.d(TAG, "========" + key + "=" + postInfo.get(key));
				nvps.add(new BasicNameValuePair(key, postInfo.get(key)));
			}
		}
		String backStr = "";
		try {
			UrlEncodedFormEntity urlEntity = new UrlEncodedFormEntity(nvps, HTTP.UTF_8);
			mHttpPost.setEntity(urlEntity);
			response = mHttpClient.execute(mHttpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			backStr = String.valueOf(statusCode);
			if (statusCode == HttpStatus.SC_OK || statusCode == NetUtil.NET_QUERY_SUCC) {
				backStr = EntityUtils.toString(response.getEntity());
				return backStr;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return backStr;
	}
	
	private static void getNetInfoByPost(final Context context, final String srcUrl,
			final Map<String, String> postInfo, final Handler handler, final String jsonInfo) {
		ThreadUtil.getTheadPool(true).submit(new Runnable() {
			private HttpPost mHttpPost;
			private HttpParams mHttpParameters;
			private DefaultHttpClient mHttpClient;
			
			@Override
			public void run() {
				try {
					mHttpPost = makeHttpPost(srcUrl, mHttpParameters);
					mHttpClient = makeHttpClient(mHttpParameters);
					List<NameValuePair> nvps = new ArrayList<NameValuePair>();
					HttpResponse response = null;
					if (postInfo != null && jsonInfo == null) {
						Set<String> keys = postInfo.keySet();
						for (String key : keys) {
							nvps.add(new BasicNameValuePair(key, postInfo.get(key)));
						}
						UrlEncodedFormEntity urlEntity = new UrlEncodedFormEntity(nvps, HTTP.UTF_8);
						mHttpPost.setEntity(urlEntity);
					} else if (jsonInfo != null) {
						StringEntity entity = new StringEntity(jsonInfo, HTTP.UTF_8);
						mHttpPost.setEntity(entity);
					}
					response = mHttpClient.execute(mHttpPost);
					int statusCode = response.getStatusLine().getStatusCode();
					System.out.println("statusCode : " + statusCode);
					if (statusCode == HttpStatus.SC_OK || statusCode == NetUtil.NET_QUERY_SUCC) {
						String backStr = EntityUtils.toString(response.getEntity());
						doHandler(handler, statusCode, backStr, context);
					}
				} catch (ParseException e) {
					e.printStackTrace();
					handler.sendEmptyMessage(NetUtil.NET_ERR);
				} catch (IOException e) {
					e.printStackTrace();
					handler.sendEmptyMessage(NetUtil.NET_REQUEST_TIME_OUT);
				}
			}
		});
	}
	
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static void doHandler(Handler handler, int statusCode, String backStr, Context context) {
		Message message = handler.obtainMessage();
		message.obj = backStr;
		switch (statusCode) {
		case HttpStatus.SC_NOT_FOUND:
			message.obj = HttpStatus.SC_NOT_FOUND;
			break;
		case HttpStatus.SC_INTERNAL_SERVER_ERROR:
			message.obj = HttpStatus.SC_INTERNAL_SERVER_ERROR;
			break;
		case NetUtil.NET_CONN_EXCEPTION:
			message.obj = context.getString(R.string.NETWORK_ERROR);
			break;
		default:
			message.obj = context.getString(R.string.NETWORK_ERROR);
			break;
		}
		message.what = statusCode;
		handler.sendMessage(message);
	}
	
	/**
	 * 获得网络连接是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean hasNetwork(Context context) {
		ConnectivityManager con = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo workinfo = con.getActiveNetworkInfo();
		if (workinfo == null || !workinfo.isAvailable()) {
			return false;
		}
		return true;
	}
	
	/**
	 * 是否gps
	 * 
	 * @param inContext
	 * @return
	 */
	public static boolean isGPSAction(Context inContext) {
		LocationManager mLocationManager = (LocationManager) inContext.getSystemService(Context.LOCATION_SERVICE);
		return mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}
	
	/**
	 * 
	 * @param aUrl
	 * @return string, could be empty
	 * @throws Exception
	 */
	public static String downloadString(final String aUrl) {
		DefaultHttpClient httpclient = new DefaultHttpClient(sHttpParamters);
		HttpGet httpget = new HttpGet(aUrl);
		ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
			
			@Override
			public String handleResponse(HttpResponse response) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					try {
						return EntityUtils.toString(entity, HTTP.UTF_8);
					} catch (ParseException e) {
						return null;
					} catch (IOException e) {
						return null;
					}
				}
				return null;
			}
			
		};
		String outputString = "";
		for (int i = 0; i < MAX_RECONNECT_TIMES; ++i) {
			try {
				outputString = httpclient.execute(httpget, responseHandler);
				break;
			} catch (Exception e) {
				outputString = null;
			}
		}
		httpclient.getConnectionManager().shutdown();
		
		return outputString;
	}
	
	public static void downloadString(String aUrl, List<BasicNameValuePair> aParameters, Handler mHandler) {
		String retString = null;
		
		if (aParameters != null && aParameters.size() > 0) {
			StringBuilder sb = new StringBuilder();
			sb.append(sCharQuestion);
			for (BasicNameValuePair paramter : aParameters) {
				try {
					String value = paramter.getValue();
					if (value == null) {
						value = "";
					}
					sb.append(paramter.getName()).append(sCharEqual).append(URLEncoder.encode(value, HTTP.UTF_8))
							.append(sCharAnd);
				} catch (UnsupportedEncodingException e) {
					mHandler.sendEmptyMessage(2046);
				}
			}
			retString = sb.toString();
			retString = sb.substring(0, retString.length() - 1);
		}
		if (retString != null) {
			aUrl += retString;
		}
		getInfoByGet(aUrl, mHandler);
	}
	
	public static void downloadStringAsync(final String url, final Handler handler) {
		new Thread() {
			public void run() {
				String outputString = "";
				try {
					outputString = downloadString(url);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				Message msg = handler.obtainMessage();
				msg.obj = outputString;
				handler.sendMessage(msg);
			}
			
		}.start();
	}
	
	/**
	 * 从给定的URL获取流
	 * 
	 * @param aUrl
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static InputStream getStreamFromUrl(String aUrl) throws IOException {
		HttpURLConnection conn = getHttpURLConnection(aUrl);
		if (conn.getResponseCode() != 200) {
			throw new IOException("Server Code : " + conn.getResponseCode());
		}
		return conn.getInputStream();
	}
	
	/**
	 * 从给定的URL获取流
	 * 
	 * @param aUrl
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static InputStream getStreamFromUrl(String aUrl, HashMap<String, String> postinfo)
			throws MalformedURLException, IOException {
		HttpURLConnection conn = getHttpURLConnection(aUrl);
		return conn.getInputStream();
	}
	
	public static HttpResponse getResponseFromUrl(String aUrl) throws ClientProtocolException, IOException {
		HttpResponse response = null;
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(aUrl);
		response = client.execute(get);
		return response;
	}
	
	/**
	 * 获取HttpURLConnection
	 * 
	 * @param aUrl
	 * @return
	 */
	private static HttpURLConnection getHttpURLConnection(String aUrl) throws MalformedURLException, IOException {
		URL url = new URL(aUrl);
		HttpURLConnection conn;
		conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(CONNECT_TIMEOUT);
		conn.setReadTimeout(READ_TIMEOUT);
		return conn;
	}
	
	/**
	 * 判断网络环境
	 * 
	 * @param context
	 * @return
	 */
	public static String whichNetWorkConnexted(Context context) {
		String network = "";
		ConnectivityManager cwjManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netWorkInfo = cwjManager.getActiveNetworkInfo();
		if (netWorkInfo != null) {
			if (netWorkInfo.getTypeName().equals("WIFI")) {
				network = "WIFI"; // WIFI
			}
			if (netWorkInfo.getTypeName().equals("EDGE")) {
				network = "GSM";// GSM
			}
			if (netWorkInfo.getTypeName().equals("MOBILE")) {
				network = "MOBILE";// 3G
			}
		}
		return network;
		
	}
	
	/**
	 * 手动过滤URL中的非法字符
	 * 
	 * @param aPara
	 * @return
	 */
	public static String filterIllegalCharInUrl(final String aPara) {
		return aPara.replace(" ", "%20").replace("\"", "%22").replace("#", "%23").replace("%", "%25")
				.replace("&", "%26").replace("(", "%28").replace(")", "%29").replace("+", "%2B").replace(",", "%2C")
				.replace("/", "%2F").replace(":", "%3A").replace(";", "%3B").replace("<", "%3C").replace("=", "%3D")
				.replace(">", "%3E").replace("?", "%3F").replace("@", "%40").replace("\\", "%5C").replace("|", "%7C");
	}
	
	/**
	 * 测试网络ping通
	 * 
	 * @param aDomainName
	 * @return
	 */
	public static boolean isDomainReachable(String aDomainName) {
		Socket socket = null;
		boolean reachable = false;
		try {
			socket = new Socket(aDomainName, HTTP_PORT);
			reachable = true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null)
				try {
					socket.close();
				} catch (IOException e) {
				}
		}
		return reachable;
	}
	
	public static String getConectionUrlWithoutPort(String path, boolean isConfig) {
		String url = "";
		if (isConfig) {
			url += ReqUrls.CONFIG_HOST_IP;
		} else {
			if (StringUtils.isEmpty(HOST_IP)) { // TODO 上线后默认值该取域名
				HOST_IP = ReqUrls.DEFAULT_REQ_HOST_IP;
			}
			url += HOST_IP;
		}
		url += ReqUrls.PROJECT_NAME + path;
		System.out.println("-------------------请求的URL:" + url);
		return url;
	}
	
//	private static MultipartEntity getMultipartEntity(Map<String, HttpBodyData> aHashMap) {
//		MultipartEntity mpEntity = new MultipartEntity();
//		Set<String> keys = aHashMap.keySet();
//		for (String key : keys) {
//			HttpBodyData bean = aHashMap.get(key);
//			ContentBody contentBody = null;
//			switch (bean.getType()) {
//			case HttpBodyData.TYPE_STRING:
//				try {
//					contentBody = new StringBody(bean.getStringValue(), Charset.forName(HTTP.UTF_8));
//				} catch (UnsupportedEncodingException e) {
//					e.printStackTrace();
//				}
//				break;
//			case HttpBodyData.TYPE_IMAGE:
//				contentBody = new FileBody(bean.getImageFileValue(), "image/jpg");
//				break;
//			case HttpBodyData.TYPE_BYTE:
//				contentBody = new ByteArrayBody(bean.getByteValue(), "image/jpeg", bean.getByteFileName());
//			default:
//				break;
//			}
//			Log.d(TAG, "========" + key + "=" + contentBody);
//			if (contentBody != null) {
//				mpEntity.addPart(key, contentBody);// 上传文件
//			}
//			
//		}
//		return mpEntity;
//	}
//	
	/**
	 * 
	 * @param mContext
	 * @param url
	 */
	@SuppressWarnings("deprecation")
	public static void downloadImage(Context mContext, String url, String filename) {
		HttpURLConnection conn = null;
		InputStream is = null;
		FileOutputStream fos = null;
		try {
			if (url == null) {
				throw new IOException("ImageURL is NULL!");
			}
			URL console = new URL(url);
			if (GlobalConfig.CURRENT_NETWORK_STATE_TYPE == GlobalConfig.NETWORK_STATE_CMWAP
					|| GlobalConfig.CURRENT_NETWORK_STATE_TYPE == GlobalConfig.NETWORK_STATE_CTWAP) {
				// 设置wap代理
				String proxyHost = null;
				int proxyPort = 80;
				if (Build.VERSION.SDK_INT >= 13) {
					proxyHost = System.getProperties().getProperty("http.proxyHost");
					proxyPort = Integer.parseInt(System.getProperties().getProperty("http.proxyPort"));
				} else {
					proxyHost = Proxy.getHost(mContext);
					proxyPort = Proxy.getPort(mContext);
				}
				if (proxyHost != null) {
					SocketAddress sa = InetSocketAddress.createUnresolved(proxyHost, proxyPort);
					java.net.Proxy proxy = new java.net.Proxy(java.net.Proxy.Type.HTTP, sa);
					conn = (HttpURLConnection) console.openConnection(proxy);
				} else {
					conn = (HttpURLConnection) console.openConnection();
				}
			} else {
				conn = (HttpURLConnection) console.openConnection();
			}
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "*/*");
			conn.setRequestProperty("Accept-Encoding", "identity");
			conn.setRequestProperty("Accept-Language", "zh-CN");
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestProperty("User-Agent", "RenrenGames");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setDoInput(true);
			conn.setDoOutput(false);
			conn.setUseCaches(true); // 不使用缓存
			conn.setInstanceFollowRedirects(true); // 是否自动重定向
			
			conn.setConnectTimeout(GlobalConfig.HTTP_CONNECTION_TIMEOUT); // 链接超时时间
			conn.setReadTimeout(GlobalConfig.HTTP_SO_TIMEOUT); // 读取服务器返回数据超时时间
			conn.connect();
			if (conn.getResponseCode() != 200) {
				throw new IOException("HttpCode: " + conn.getResponseCode());
			}
			// 流处理
			byte[] buffer = new byte[16 * 1024];
			is = conn.getInputStream();
			int readsize = -1;
			File file = new File(filename);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			fos = new FileOutputStream(file);
			while ((readsize = is.read(buffer)) != -1) {
				fos.write(buffer, 0, readsize);
			}
			fos.flush();
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public static int checkNetworkStatus(Context context) {
		ConnectivityManager mConnectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);// 上下文连接服务
		NetworkInfo info = mConnectivity.getActiveNetworkInfo();// 给定网络接口的状态类型
		if (info == null || !info.isAvailable()) {// 无网络判断
			return (GlobalConfig.CURRENT_NETWORK_STATE_TYPE = GlobalConfig.NETWORK_STATE_IDLE);
		}
		String typeName = info.getTypeName();
		if (typeName.equals("WIFI")) {
			return (GlobalConfig.CURRENT_NETWORK_STATE_TYPE = GlobalConfig.NETWORK_STATE_WIFI);
		}
		String extraName = info.getExtraInfo();
		if (extraName == null || extraName.trim().length() == 0) {
			String proxyHost = null;
			if (Build.VERSION.SDK_INT >= 13) {
				proxyHost = System.getProperties().getProperty("http.proxyHost");
			} else {
				proxyHost = Proxy.getHost(context);
			}
			return (GlobalConfig.CURRENT_NETWORK_STATE_TYPE = (proxyHost == null ? GlobalConfig.NETWORK_STATE_CMNET
					: GlobalConfig.NETWORK_STATE_CMWAP));
		}
		
		if (extraName.equals("cmnet") || extraName.equals("3gnet") || extraName.equals("uninet")
				|| extraName.equals("ctnet") || extraName.equals("ctnet:CDMA") || extraName.equals("CTC")) {
			return (GlobalConfig.CURRENT_NETWORK_STATE_TYPE = GlobalConfig.NETWORK_STATE_CMNET);
		} else if (extraName.equals("cmwap") || extraName.equals("3gwap") || extraName.equals("uniwap")) {
			return (GlobalConfig.CURRENT_NETWORK_STATE_TYPE = GlobalConfig.NETWORK_STATE_CMWAP);
		} else if ("ctwap:CDMA".equals(extraName) || extraName.equals("ctwap") || extraName.equals("#777")) {
			return (GlobalConfig.CURRENT_NETWORK_STATE_TYPE = GlobalConfig.NETWORK_STATE_CTWAP);
		}
		
		String proxyHost = null;
		if (Build.VERSION.SDK_INT >= 13) {
			proxyHost = System.getProperties().getProperty("http.proxyHost");
		} else {
			proxyHost = Proxy.getHost(context);
		}
		return (GlobalConfig.CURRENT_NETWORK_STATE_TYPE = (proxyHost == null ? GlobalConfig.NETWORK_STATE_CMNET
				: GlobalConfig.NETWORK_STATE_CMWAP));
		
	}
	
	/**
	 * 获取当前网络状态
	 * 
	 * @param context
	 * @return 当前网络状态
	 */
	public static int getNetworkState(Context context) {
		Log.i(TAG, "getNetworkState:" + GlobalConfig.CURRENT_NETWORK_STATE_TYPE);
		if (GlobalConfig.CURRENT_NETWORK_STATE_TYPE != 0) {
			return GlobalConfig.CURRENT_NETWORK_STATE_TYPE;
		} else {
			int type = checkNetworkStatus(context);
			Log.i(TAG, "getNetworkState2:" + type);
			return type;
		}
	}
	
	public static NetworkInfo getActiveNetworkInfo() {
		ConnectivityManager connectivity = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return null;
		}
		
		final NetworkInfo activeInfo = connectivity.getActiveNetworkInfo();
		return activeInfo;
	}
}