package net.logvv.oss.commom.utils;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * 
 * 时间工具类，负责提供所有的时间方法，可根据需要自行添加方法<br>
 * 本身依赖于joda，无多线程并发影响<br>
 * 
 * @author: fanyaowu
 * @data: 2014年7月7日
 * @version 1.0.0
 *
 */
public final class DateUtils {
	
	
	/**
	 * 
	 * 私有构造方法，工具类不需要外部构造
	 *
	 * @Constructors 
	 * @Author: fanyaowu
	 * @data: 2014年7月7日
	 * @version
	 *
	 */
	private DateUtils() {
		super();
	}

	// 默认的时间格式为"yyyy/MM/dd HH:mm:ss"，若后续有其他需求，则可以另行添加
	private static final String DEFAULT_DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";
	
	private static final String DATA_FORMAT = "yyyy/MM/dd";
	
	private static final String MONTH_FORMAT = "yyyy/MM";
	
	// 默认的时间格式为"yyyy/MM/dd HH:mm:ss"，若后续有其他需求，则可以另行添加
	private static DateTimeFormatter defaultFormatter = DateTimeFormat.forPattern(DEFAULT_DATE_FORMAT);
	
	// 当前日期标签，很多类似日志文件需要打时间标签
	private static final String DATE_MARK = "yyyyMMdd";
	
	// 默认的时间格式
	//private static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
	
	private static final String HOUR_MINUTE = "HH:mm";
	
	/**
	 * 
	 * 获取当前时间，返回的格式为"yyyy/MM/dd HH:mm:ss"<br>
	 *
	 * @param: 
	 * @return: String
	 * @Author: fanyaowu
	 * @data: 2014年7月7日
	 * @exception 
	 * @version
	 *
	 */
	public static String getCurrentTimeStr()
	{
		return DateTime.now().toString(DEFAULT_DATE_FORMAT);
	}
	
	/**
	 * 
	 * 获取数据库的timestamp时间格式<br>
	 *
	 * @return
	 * Timestamp
	 * @Author fanyaowu
	 * @data 2014年7月31日
	 * @exception 
	 * @version
	 *
	 */
	public static Timestamp getCurrentTimestamp()
	{
		return new Timestamp(System.currentTimeMillis());
	}
	
	public static Timestamp getTimestamp(Date date)
	{
		return new Timestamp(date.getTime());
	}
	
	public static Timestamp convertTimestampFromStr(String dateStr)
	{
		if(StringUtils.isEmpty(dateStr))
		{
			return null;
		}
		
		DateTime dt = parseDate(dateStr);
		
		return new Timestamp(dt.getMillis());
		
	}
	
	/**
	 * 
	 * 获取当前的日期标签<br>
	 *
	 * @return
	 * String
	 * @Author fanyaowu
	 * @data 2014年7月16日
	 * @exception 
	 * @version
	 *
	 */
	public static String getCurrentDateMark()
	{
		return DateTime.now().toString(DATE_MARK);
	}
	
	/**
	 * 
	 * 获取当前时间，返回的类型为long，精确到毫秒<br>
	 *
	 * @return: long
	 * @Author: fanyaowu
	 * @data: 2014年7月7日
	 * @exception 
	 * @version
	 *
	 */
	public static long getCurrentTime()
	{
		return System.currentTimeMillis();
	}
	
	/**
	 * 
	 * 将时间字符串按照指定格式转为DateTime类型<br>
	 *
	 * @param source
	 * @param format
	 * @return
	 * DateTime
	 * @Author fanyaowu
	 * @data 2014年7月7日
	 * @exception 
	 * @version
	 *
	 */
	public static DateTime parseDate(String source,String format)
	{
		DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern(format);
		
		return DateTime.parse(source, dateTimeFormat);
	}
	
	public static DateTime parseOnlyDate(String source)
	{
		DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern(DATA_FORMAT);
		
		return DateTime.parse(source, dateTimeFormat);
	}
	
	public static DateTime parseOnlyMonth(String source)
	{
		DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern(MONTH_FORMAT);
		
		return DateTime.parse(source, dateTimeFormat);
	}
	
	
	/**
	 * 
	 * 若字符串时间格式为"yyyy/MM/dd HH:mm:ss"，则直接使用该方法转为DateTime类型<br>
	 *
	 * @param source
	 * @return
	 * DateTime
	 * @Author fanyaowu
	 * @data 2014年7月7日
	 * @exception 
	 * @version
	 *
	 */
	public static DateTime parseDate(String source)
	{
		return DateTime.parse(source, defaultFormatter);
	}
	
	/**
	 * 
	 * 将Date类型转为DateTime<br>
	 *
	 * @param date
	 * @return
	 * DateTime
	 * @Author fanyaowu
	 * @data 2014年7月7日
	 * @exception 
	 * @version
	 *
	 */
	public static DateTime parseDate(Date date)
	{
		return new DateTime(date);
	}
	
	/**
	 * 
	 * 将calendar类型转为datetime类型<br>
	 *
	 * @param calendar
	 * @return
	 * DateTime
	 * @Author fanyaowu
	 * @data 2014年7月7日
	 * @exception 
	 * @version
	 *
	 */
	public static DateTime parseDate(Calendar calendar)
	{
		return new DateTime(calendar);
	}
	
	/**
	 * 
	 * 将dateime以默认的时间格式"yyyy/MM/dd HH:mm:ss"转为字符串<br>
	 *
	 * @param dt
	 * @return
	 * String
	 * @Author fanyaowu
	 * @data 2014年7月7日
	 * @exception 
	 * @version
	 *
	 */
	public static String date2Str(DateTime dt)
	{
		return dt.toString(DEFAULT_DATE_FORMAT);
	}
	
	/**
	 * 
	 * 将date按照指定的格式转为字符串类型<br>
	 *
	 * @param dt
	 * @param format
	 * @return
	 * String
	 * @Author fanyaowu
	 * @data 2014年7月7日
	 * @exception 
	 * @version
	 *
	 */
	public static String date2Str(DateTime dt,String format)
	{
		return dt.toString(format);
	}
	
	/**
	 * 
	 * 将dateime以默认的时间格式"yyyy/MM/dd HH:mm:ss"转为字符串<br>
	 *
	 * @param dt
	 * @return
	 * String
	 * @Author fanyaowu
	 * @data 2014年7月7日
	 * @exception 
	 * @version
	 *
	 */
	public static String date2Str(Date date)
	{
		return new DateTime(date).toString(DEFAULT_DATE_FORMAT);
	}
	
	/**
	 * 
	 * 将date按照指定的格式转为字符串类型<br>
	 *
	 * @param dt
	 * @param format
	 * @return
	 * String
	 * @Author fanyaowu
	 * @data 2014年7月7日
	 * @exception 
	 * @version
	 *
	 */
	public static String date2Str(Date date,String format)
	{
		return new DateTime(date).toString(format);
	}
	
	/**
	 * 
	 * 输入时间是否晚于当前时间<br>
	 * 该方法仅比较小时和分钟
	 * @param timeStr 格式为"HH:mm:SS"
	 * @return
	 * boolean
	 * @Author fanyaowu
	 * @data 2014年7月17日
	 * @exception 
	 * @version
	 *
	 */
	public static boolean isAfterNow(String timeStr)
	{
		assert StringUtils.isNoneBlank(timeStr);
		
		String currentDate = getCurrentDateMark();
		
		DateTime dt = DateUtils.parseDate(currentDate+' '+timeStr,DATE_MARK+' '+HOUR_MINUTE);
		
		return dt.isAfterNow();
		
	}
	
	/**
	 * 
	 * 输入时间是否早于当前时间<br>
	 * 该方法仅比较小时和分钟
	 * @param timeStr 格式为"HH:mm:SS"
	 * @return
	 * boolean
	 * @Author fanyaowu
	 * @data 2014年7月17日
	 * @exception 
	 * @version
	 *
	 */
	public static boolean isBeforeNow(String timeStr)
	{
		assert StringUtils.isNoneBlank(timeStr);
		
		String currentDate = getCurrentDateMark();
		
		DateTime dt = DateUtils.parseDate(currentDate+' '+timeStr,DATE_MARK+' '+HOUR_MINUTE);
		
		return dt.isBeforeNow();
		
	}
	
	/**
	 * 
	 * 根据小时和分钟组装成今天的指定时刻<br>
	 *
	 * @param timeStr
	 * @return
	 * DateTime
	 * @Author fanyaowu
	 * @data 2014年7月17日
	 * @exception 
	 * @version
	 *
	 */
	public static DateTime parseHourAndMinute(String timeStr)
	{
		assert StringUtils.isNoneBlank(timeStr);
		String currentDate = getCurrentDateMark();
		DateTime dt = DateUtils.parseDate(currentDate+' '+timeStr,DATE_MARK+' '+HOUR_MINUTE);
		
		return dt;
	}
	
}
