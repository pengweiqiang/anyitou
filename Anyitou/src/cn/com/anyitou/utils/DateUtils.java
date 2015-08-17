/**
 * 
 */
package cn.com.anyitou.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class DateUtils {

    /** 默认日期格式 */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd".intern();

    /** 默认时间格式 */
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss".intern();
    
    /** 时:分  */
    public static final String DEFAULT_HOUR_FORMAT = "HH:mm".intern();
    
    /** 月-日 时:分 */
    public static final String M_D_H_M = "MM-dd".intern() + " " +"HH:mm".intern();
    /** 年-月-日 时:分 */
    public static final String Y_M_D_H_M = "yyyy-MM-dd".intern() + " " +"HH:mm".intern();

    /** 默认日期时间格式(精确到秒) */
    public static final String DEFAULT_DATETIME_FORMAT = (DEFAULT_DATE_FORMAT + " " + DEFAULT_TIME_FORMAT)
            .intern();
    
    public static final String FULL_DATETIME = "yyyyMMddHHmmss";

    public static final int SECOND_TO_MILLI_SECOND = 1000;

    public static final int MINUTE_TO_SECOND = 60;

    public static final int HOUR_TO_MINUTE = 60;

    public static final int HALF_DAY = 1000 * 60 * 60 * 12;

    /**
     * 获取指定日期时间几天后的凌晨日期时间（时分秒都为0）
     * 
     * @param date
     * @param intervalDay 正数，几天之后 负数，几天之前
     * @return
     */
    public static Date getZeroDateTimeAfterSomeDay(Date date, int intervalDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, intervalDay);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }
    
    public static String getDateAfterSomeDay(String nowdateStr,int intervalDay){
    	Date nowdate;
    	String afterDateStr = "";
		try {
			nowdate = DateUtil.getDate(nowdateStr, DateUtil.CUSTOM_PATTERN_SCHEDULED);
			Calendar calendar = Calendar.getInstance();
	        calendar.setTimeInMillis(nowdate.getTime());
	        calendar.add(Calendar.DAY_OF_MONTH, intervalDay);
	        calendar.set(Calendar.HOUR_OF_DAY, 0);
	        calendar.set(Calendar.MINUTE, 0);
	        calendar.set(Calendar.SECOND, 0);
	        Date afterdate = calendar.getTime();
	        afterDateStr = DateUtil.getDateString(afterdate, DateUtil.CUSTOM_PATTERN_SCHEDULED);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        
        return afterDateStr;
    }
    
    /**
     * 获取指定日期时间几小时后的整点时间（分秒都为0）
     * 
     * @param date
     * @param intervalDay 正数，几小时之后 负数，几小时之前
     * @return
     */
    public static Date getHourStartTimeAfterSomeHour(Date date, int intervalHour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        calendar.add(Calendar.HOUR_OF_DAY, intervalHour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }
    
    /**
     * 获取指定日期时间几周后的凌晨日期时间（时分秒都为0）
     * 
     * @param date
     * @param intervalDay 正数，几周之后 负数，几周之前
     * @return
     */
    public static Date getZeroDateTimeAfterSomeWeek(Date date, int intervalWeek) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        calendar.add(Calendar.WEEK_OF_MONTH, intervalWeek);
        calendar.set(Calendar.DAY_OF_WEEK, 2);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }
    
    /**
     * 获取指定日期时间几月后的凌晨日期时间（时分秒都为0）
     * 
     * @param date
     * @param intervalDay 正数，几月之后 负数，几月之前
     * @return
     */
    public static Date getZeroDateTimeAfterSomeMonth(Date date, int intervalMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        calendar.add(Calendar.MONTH, intervalMonth);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 返回多少分钟以后的日期格式
     * 
     * @param maintainMinute
     * @return
     */
    public static Date calulateMaintainDateTime(int maintainMinute) {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(now.getTime());
        calendar.add(Calendar.MINUTE, maintainMinute);
        return calendar.getTime();
    }

    /**
     * 传入时间是否早于当前时间
     * 
     * @param when
     * @return
     */
    public static boolean beforeNow(Date when) {
        return getCurrentDate().after(when);
    }

    /**
     * 获取当前时间对象
     * 
     * @return
     */
    public static Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    public static Date getFutureDate(long offset) {
        return new Date(System.currentTimeMillis() + offset);
    }

    /**
     * 获取两个毫秒值之间相差多少毫秒
     * 
     * @param big
     * @param small
     * @return
     */
    public static long getUnsignedOffset(long big, long small) {
        return Math.max(big, small) - Math.min(big, small);
    }

    public static long getUnsignedOffset(Date big) {
        return getUnsignedOffset(big.getTime());
    }

    public static long getUnsignedOffset(long big) {
        return getUnsignedOffset(big, System.currentTimeMillis());
    }

    /**
     * 获取传入时间与当前时间的毫秒差值
     * 
     * @param big
     * @param small
     * @return
     */
    public static long getOffset(long big) {
        return System.currentTimeMillis() - big;
    }

    /**
     * 获取当天日期（除时分秒）的毫秒值 注意： 1、可以通过调用 set
     * 方法来设置日历字段值。在需要计算时间值（距历元所经过的毫秒）或日历字段值之前， 不会解释 Calendar 中的所有字段值设置。调用
     * get、getTimeInMillis、getTime、add 和 roll 涉及此类计算；
     * 2、在使用set方法之前，必须先clear一下，否则很多信息会继承自系统当前时间；
     * 
     * @return
     */
    public static long getTodayDateInMillis() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTimeInMillis();
    }
    /**
     * 
     * @param 要转换的毫秒数
     * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
     * @author fy.zhang
     */
    public static String formatDuring(long mss) {
        StringBuilder sb = new StringBuilder("[ ");
        long days = mss / (1000 * 60 * 60 * 24);
        if (days > 0) sb.append(days).append(" days ");
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        if (hours > 0) sb.append(hours).append(" hours ");
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        if (minutes > 0) sb.append(minutes).append(" minutes ");
        long seconds = (mss % (1000 * 60)) / 1000;
        if (seconds > 0) sb.append(seconds).append(" seconds ");
        sb.append(" ]");
        return sb.toString();
    }

    /**
     * 
     * @param begin 时间段的开始
     * @param end 时间段的结束
     * @return 输入的两个Date类型数据之间的时间间格用* days * hours * minutes * seconds的格式展示
     * @author fy.zhang
     */
    public static String formatDuring(Date begin, Date end) {
        return formatDuring(end.getTime() - begin.getTime());
    }

    /**
     * 字符串转日期
     * 
     * @param dateTime
     * @return
     */
    public static Date format(String dateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT, Locale.getDefault());
        try {
            Date date = sdf.parse(dateTime);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            //PartyLoggers.UN_RUNTIME_EXCEPTION_LOGGER.error(e);
            return null;
        }
    }
    public static String convert2String(long time, String format) {
    	  if (time > 0l) {
    	   if (StringUtils.isEmpty(format))
    	    format =DEFAULT_DATETIME_FORMAT;
    	   
    	   SimpleDateFormat sf = new SimpleDateFormat(format, Locale.getDefault());
    	   Date date = new Date(time);
    	   return sf.format(date);
    	  }
    	  return "";
   }
    /**
     * 字符串转日期
     * 
     * @param dateTime
     * @return
     */
    public static Date format(String dateTime, String fromat) {
        SimpleDateFormat sdf = new SimpleDateFormat(fromat, Locale.getDefault());
        try {
            Date date = sdf.parse(dateTime);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            //PartyLoggers.UN_RUNTIME_EXCEPTION_LOGGER.error(e);
            return null;
        }
    }
    /**
     * 解析excel中定义的日期字符串
     * 
     * @param excelDateTime
     *        在excel中定义的日期的字符串形式，非标准(20110401120000)，去掉了-、空格和冒号
     * @return
     */
    public static String parseExcelDateTimeStr(String excelDateTime) {
        String dateTime = "";
        try {
            dateTime = excelDateTime.substring(0, 4) + "-" + excelDateTime.substring(4, 6) + "-"
                    + excelDateTime.substring(6, 8) + " " + excelDateTime.substring(8, 10) + ":"
                    + excelDateTime.substring(10, 12) + ":" + excelDateTime.substring(12, 14);
        } catch (Exception e) {
            return "";
        }

        return dateTime;
    }

    /**
     * 获取指定的日期是一周中的第几天
     * 
     * @param date
     * @return
     */
    public static int getDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取今天是一周第几天
     * 
     * @return
     */
    public static int getTodayOfWeek() {
        Calendar c = Calendar.getInstance();
        c.setTime(getCurrentDate());
        return c.get(Calendar.DAY_OF_WEEK);
    }

   /* public static void main(String[] args) {
        //System.out.println(getZeroDateTimeAfterSomeDay(new java.util.Date(),1));
        //System.out.println(getNowRemainTomorrowSeconds());
        //System.out.println(getTodayDateInMillis());

        //System.out.println(getTodayOfWeek());
        System.out.println(isSameDay(DateUtil.getDate("2012-08-14"), getCurrentDate()));
        System.out.println(getNowRemainNextHourSeconds());
        System.out.println(getZeroDateTimeAfterSomeWeek(new Date(), 2));
        System.out.println(getZeroDateTimeAfterSomeMonth(new Date(), 1));
    }*/

    /**
     * 将秒数解析为HH:mm:ss格式
     * @param second
     * @return
     */
    public static String getCountdown(int second) {
        int hour = 0;
        int min = 0;
        int sec = 0;
        int temp = second % 3600;
        if (second > 3600) {
            hour = second / 3600;
            if (temp != 0) {
                if (temp > 60) {
                    min = temp / 60;
                    if (temp % 60 != 0) {
                        sec = temp % 60;
                    }
                } else {
                    sec = temp;
                }
            }
        } else {
            min = second / 60;
            if (second % 60 != 0) {
                sec = second % 60;
            }
        }
        return hour+":"+min+":"+sec;
    }
    public static String getCurrentDate(Date date){
    	return new SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.getDefault()).format(date);
    }

    
}
