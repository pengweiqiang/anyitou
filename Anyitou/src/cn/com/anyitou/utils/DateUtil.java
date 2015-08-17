package cn.com.anyitou.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
	public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String CUSTOM_PATTERN = "yyyy-MM-dd HH-mm-ss";
	public static final String CUSTOM_PATTERN2 = "yyyy年MM月dd日 HH:mm";
	public static final String CUSTOM_PATTERN3 = "yyyyMMdd HH:mm";
	public static final String FULL_PATTERN = "yyyy-MM-dd HH:mm:ss SSS";
	public static final String TRIM_PATTERN = "yyyyMMddHHmmss";
	public static final String SIMPLY_PATTERN = "HH:mm";
	public static final String SIMPLY_DD_PATTERN = "MM-dd HH:mm";
	public static final String DAY_PATTERN = "yyyy-MM-dd";
	public static final String SIMPLY_HH_PATTERN = "HH";
	public static final String CRITICISM_PATTERN = "yyyy-MM-dd HH:mm";
	public static final String CHINESE_PATTERN = "MM月dd日  HH:mm";
	public static final String CUSTOM_PATTERN_SCHEDULED = "yyyyMMdd";
	public static final String SIMPLY_DD_PATTERN2 = "MM-dd";
	public static final String SIMPLY_DD_PATTERN3 = "MM月dd日";
	public static final String CUSTOM_PATTERN4="yyyy年MM月dd日";

	/**
	 * 获得日期对象
	 * 
	 * @param dateStr
	 *            日期字符串，例如：2012-12-22 00:00:00
	 * @param pattern
	 *            日期格式
	 * @return dateStr所代表的Date对象
	 * @throws ParseException
	 * @throws Exception
	 */
	public static Date getDate(String dateStr, String pattern)
			throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat(pattern, Locale.getDefault());
		return df.parse(dateStr);
	}

	/**
	 * 返回指定样式的时间
	 * 
	 * @param timeMillis
	 *            毫秒值
	 * @param pattern
	 *            时间样式
	 * @return
	 */
	public static String getDateStringByMill(long timeMillis, String pattern) {
		Date date = new Date(timeMillis);
		return getDateString(date, pattern);
	}

	/**
	 * 获得日期的格式化字符串
	 * 
	 * @param date
	 *            日期对象
	 * @param pattern
	 *            日期格式
	 * @return 日期字符串
	 * @throws Exception
	 */
	public static String getDateString(Date date, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern, Locale.getDefault());
		return df.format(date);
	}

	/**
	 * 判断两个时间是否是同一天
	 * 
	 * @param date
	 *            日期对象
	 * @return 是否是同一天
	 */
	public static boolean isSameDay(Date date1, Date date2) {
		if (null == date1 || null == date2)
			return false;

		SimpleDateFormat df = new SimpleDateFormat(DAY_PATTERN,
				Locale.getDefault());
		String dateString1 = df.format(date1);
		String dateString2 = df.format(date2);
		return dateString1.equalsIgnoreCase(dateString2);
	}

	/**
	 * 格式化时间字符串
	 * 
	 * @param date
	 *            日期对象
	 * @param pattern
	 *            日期格式
	 * @return 日期字符串
	 * @throws Exception
	 */
	public static String getDateString(String dateStr, String pattern) {
		Date tempDate;
		try {
			tempDate = getDate(dateStr, FULL_PATTERN);
			return getDateString(tempDate, pattern);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	public static String getDateMMDDString(String dateStr, String pattern) {
		Date tempDate;
		try {
			tempDate = getDate(dateStr, SIMPLY_DD_PATTERN3);
			return getDateString(tempDate, pattern);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 格式化时间字符串
	 * 
	 * @param date
	 *            日期对象
	 * @param pattern
	 *            日期格式
	 * @return 日期字符串
	 * @throws Exception
	 */
	public static String getDateString(String dateStr, String oldPattern,
			String pattern) {
		if (null == dateStr || "".equals(dateStr))
			return "";
		Date tempDate;
		try {
			tempDate = getDate(dateStr, oldPattern);
			return getDateString(tempDate, pattern);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public static String getDateString(Date date, String pattern, int index) {
		// 通过日历获取下一天日期
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, +index);
		return getDateString(cal.getTime(), pattern);
	}

	public static String getScheduledTitle(Date date, String pattern, int index) {
		String title = "";
		// 通过日历获取下一天日期
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, +index);
		int week = cal.get(Calendar.DAY_OF_WEEK);
		switch (week) {
		case 1:// 周日
			title = "周日";
			break;
		case 2:// 周一
			title = "周一";
			break;
		case 3:// 周二
			title = "周二";
			break;
		case 4:// 周三
			title = "周三";
			break;
		case 5:// 周四
			title = "周四";
			break;
		case 6:// 周五
			title = "周五";
			break;
		case 7:// 周六
			title = "周六";
			break;

		default:
			break;
		}
		
//		title = title + "(" +getDateString(cal.getTime(), pattern)+")";
		return title;
	}
}
