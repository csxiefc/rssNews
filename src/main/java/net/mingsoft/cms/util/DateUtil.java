package net.mingsoft.cms.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 时间日期工具类
 * @ClassName: DateUtil
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author lk
 * 2019年9月3日 下午3:36:12
 */
public class DateUtil {
	
	// 字符串 转 Date
	public static Date stringToDate(String strDate, String pattern) {
		SimpleDateFormat dateformat = new SimpleDateFormat();
		dateformat.applyPattern(pattern);
		Date date = null;
		try {
			date = dateformat.parse(strDate);
		} catch (Exception e) {
			return null;
		}
		return date;
	}
	/**
	 * 将字符串形式的日期/时间转换成java.util.Date类型
	 * @param strDate String 字符串形式的日期/时间
	 * @param pattern String 需要转换的格式(如：yyyy-MM-dd)
	 * @return 返回Date类型的日期
     * add by lk
	 */
	public static Date parseDateFormat(String strDate, String pattern) {
		SimpleDateFormat dateformat = new SimpleDateFormat();
		dateformat.applyPattern(pattern);
		Date date = null;
		try {
			date = dateformat.parse(strDate);
		} catch (Exception e) {
			return null;
		}
		return date;
	}
	
	// 字符串 转 LocalDate
	public static LocalDate stringToLocalDate (String strDate, String pattern) {
		try {
			return LocalDate.parse(strDate, DateTimeFormatter.ofPattern(pattern));
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	public static LocalDate stringToLocalDate (String strDate) {
		try {
			return LocalDate.parse(strDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	// 字符串 转 LocalDateTime
	public static LocalDateTime stringToLocalDateTime(String strDate, String pattern) {
		try {
			return LocalDateTime.parse(strDate, DateTimeFormatter.ofPattern(pattern));
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	public static LocalDateTime stringToLocalDateTime(String strDate) {
		return stringToLocalDateTime(strDate, "yyyy-MM-dd HH:mm:ss");
	}
	
	// Date 转  字符串
	public static String dateToString (Date date, String pattern) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(pattern);
			return formatter.format(date);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	// Date 转  LocalDate
	public static LocalDate dateToLocalDate(Date date) {
		try {
			Instant instant = date.toInstant();
			ZoneId zone = ZoneId.systemDefault();
			LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
			return localDateTime.toLocalDate();
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	// Date 转  LocalDateTime
	public static LocalDateTime dateToLocalDateTime(Date date) {
		return dateToLocalDateTime(date, "yyyy-MM-dd HH:mm:ss");
	}
	public static LocalDateTime dateToLocalDateTime(Date date, String pattern) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			String d = sdf.format(date);
			return stringToLocalDateTime(d);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	
	// LocalDate 转  字符串
	public static String localDateToString (LocalDate date, String pattern) {
		try {
		   DateTimeFormatter fmt = DateTimeFormatter.ofPattern(pattern);
	       return date.format(fmt);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	public static String localDateToString (LocalDate date) {
		return localDateToString(date, "yyyy-MM-dd");
	}
	
	// LocalDate 转  Date
	public static Date localDateToDate(LocalDate localDate) {
		try {
			ZoneId zone = ZoneId.systemDefault();
			Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
			return Date.from(instant);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	// LocalDate 转  LocalDateTime
	public static LocalDateTime localDateToLocalDateTime (LocalDate date, String pattern) {
		String d = localDateToString(date, pattern);
		return stringToLocalDateTime(d + " 00:00:00", pattern);
	}
	
	public static LocalDateTime localDateToLocalDateTime (LocalDate date) {
		String d =  localDateToString(date, "yyyy-MM-dd");
		return stringToLocalDateTime(d + " 00:00:00");
	}
	
	// LocalDateTime 转 字符串
	public static String localDateTimeToString(LocalDateTime localDateTime, String pattern) {
		try {
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
			return dateTimeFormatter.format(localDateTime);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	// LocalDateTime 转 Date
	public static Date localDateTimeToDate(LocalDateTime localDateTime) {
		try {
			ZoneId zone = ZoneId.systemDefault();
			Instant instant = localDateTime.atZone(zone).toInstant();
			return Date.from(instant);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	public static Date localDateTimeToDate2 (LocalDateTime localDateTime) {
		try {
			return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	// LocalDateTime 转 LocalDate
	public static LocalDate localDateTimeToLocalDate(LocalDateTime date, String pattern) {
		String d = localDateTimeToString(date, pattern);
		return stringToLocalDate(d, pattern);
	}
	
	// LocalDateTime 转 LocalDateTime
	public static LocalDateTime parseLocalDateTimeFormat(LocalDateTime date) {
		return parseLocalDateTimeFormat(date, "yyyy-MM-dd hh:mm:ss");
	}
	public static LocalDateTime parseLocalDateTimeFormat(LocalDateTime date, String pattern) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
		String d = dateTimeFormatter.format(date);
		return stringToLocalDateTime(d);
	}

	
	public static void main(String[] args) {
//		System.out.println("stringToDate==============" + stringToDate("2020-07-04", "yyyy-MM-dd"));
//		System.out.println("stringToLocalDate==============" + stringToLocalDate("2020-07-04", "yyyy-MM-dd"));
//		System.out.println("stringToLocalDateTime==============" + stringToLocalDateTime("2020-07-04 12:11:52"));
//		System.out.println("dateToString==============" + dateToString(new Date(), "2020-07-04"));
//		System.out.println("dateToLocalDate==============" + dateToLocalDate(new Date()));
//		System.out.println("dateToLocalDateTime==============" + dateToLocalDateTime(new Date(), "yyyy-MM-dd hh:mm:ss"));
//		System.out.println("localDateToDate==============" + localDateToDate(LocalDate.now()));
//		System.out.println("localDateTimeToString==============" + localDateTimeToString(LocalDateTime.now(), "yyyy-MM-dd hh:mm:ss"));
//		System.out.println("localDateTimeToDate==============" + localDateTimeToDate(LocalDateTime.now()));
//		System.out.println("localDateTimeToDate2==============" + localDateTimeToDate2(LocalDateTime.now()));
//		System.out.println("localDateTimeToLocalDate==============" + localDateTimeToLocalDate(LocalDateTime.now(), "yyyy-MM-dd"));
//		System.out.println("parseLocalDateTimeFormat==============" + parseLocalDateTimeFormat(LocalDateTime.now(), "yyyy-MM-dd hh:mm:ss"));
//		System.out.println("stringToLocalDateTime==============" + stringToLocalDateTime("2020-07-04 12:11:52", "yyyy-MM-dd HH:mm:ss"));
//		System.out.println("localDateToString==============" + localDateToString(LocalDate.now(), "yyyy-MM-dd"));
		
//		System.out.println(LocalDate.now());
//		System.out.println("localDateToLocalDateTime==============" + localDateToLocalDateTime(LocalDate.now()));

//		LocalDateTime a = LocalDateTime.now();
//		System.out.println(a.getDayOfMonth());
//		System.out.println(a.getDayOfYear());
//		System.out.println(a.plusDays(-2));v
//		
//		
//		System.out.println(dateToString(getLastNDate(3), "yyyy-MM-dd"));
//		System.out.println(DateAUtil.dateToString(DateAUtil.getLastNDate(5), "yyyy-MM-dd 00:00:00"));
//		System.out.println(DateAUtil.dateToString(DateAUtil.getLastNDate(2), "yyyy-MM-dd 23:59:59"));

//		LocalDateTime smdates = DateAUtil.stringToLocalDateTime("2020-11-06 14:47:44");;
//		LocalDateTime bdates = DateAUtil.stringToLocalDateTime("2020-11-06 14:48:44");;
//		String type = "sec";
//		try {
//			System.out.println(daysBetween(smdates, bdates, type));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Date smdates2 = DateAUtil.stringToDate("2020-11-06 14:47:24", "yyyy-MM-dd HH:mm:ss");;
//		Date bdates2 = DateAUtil.stringToDate("2020-11-06 14:48:44", "yyyy-MM-dd HH:mm:ss");;
//		try {
//			System.out.println(timeBetween_long(smdates2, bdates2, type));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(getStringDate(DateUtil.getLastNDate(3)) + " 00:00:00");
		
	}
	
	
	 /**
     * 当月第一天
     * @return
     */
	public static String getFirstDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date theDate = calendar.getTime();
        
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
//        StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
        return day_first;
 
    }
    
    /**
     * 当月最后一天
     * @return
     */
    public static String getLastDay() {
       SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
       Calendar calendar = Calendar.getInstance();
       calendar.add(Calendar.MONTH, 1);    //加一个月
       calendar.set(Calendar.DATE, 1);        //设置为该月第一天
       calendar.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天
       String day_last = df.format(calendar.getTime());
       StringBuffer endStr = new StringBuffer().append(day_last);
       day_last = endStr.toString();
       StringBuffer str = new StringBuffer().append(day_last);
       return str.toString();
    }
    
    /**
     * 判断当天是否是本月最后一天
     * @Title: isLastDay
     * @Description: TODO(描述这个方法的作用)
     * @author: lk
     * @time: 2021年2月5日 下午3:34:55
     */
    public static boolean isLastDay() {
    	// 测试用
//        return getLastDay().equals("2021-02-28");
        return getLastDay().equals(dateToString(new Date(), "yyyy-MM-dd"));
     }
	
	/**
	 * 获取前1个月的月份
	 * @Title: getLastMonth
	 * @Description: TODO(描述这个方法的作用)
	 * @author: lk
	 * @time: 2020年1月6日 下午4:34:09
	 */
	public static String getLastMonth() {
		LocalDate today = LocalDate.now();
		today = today.minusMonths(1);
		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy-MM");
		return formatters.format(today);
	}
	
	/**
	 * 获取前N个月的月份
	 * @Title: getLastNMonths
	 * @Description: TODO(描述这个方法的作用)
	 * @author: lk
	 * @time: 2020年1月6日 下午4:34:09
	 */
	public static String getLastNMonths(int i) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MONTH, -i);
		Date m = c.getTime();
		return sdf.format(m);
	}
	
	
	public static String getLastMonthFirthDay(){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return format.format(calendar.getTime());

	}

	//获取上个月的最后一天
	public static String getLastMonthLastDay(){
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar=Calendar.getInstance();
		int month=calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, month-1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return sf.format(calendar.getTime());
	}
	
	 //JAVA获取某段时间内的所有日期
    public static List<Date> findDates(Date dStart, Date dEnd) {
        Calendar cStart = Calendar.getInstance();
        cStart.setTime(dStart);

        List<Date> dateList = new ArrayList<Date>();
        //别忘了，把起始日期加上
        dateList.add(dStart);
        // 此日期是否在指定日期之后
        while (dEnd.after(cStart.getTime())) {
        // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cStart.add(Calendar.DAY_OF_MONTH, 1);
            dateList.add(cStart.getTime());
        }
        return dateList;
    }
    
    public static List<String> findStrDates(Date dStart, Date dEnd) {
        Calendar cStart = Calendar.getInstance();
        cStart.setTime(dStart);

        List<String> dateList = new ArrayList<String>();
        //别忘了，把起始日期加上
        dateList.add(getStringDate(dStart));
        // 此日期是否在指定日期之后
        while (dEnd.after(cStart.getTime())) {
        // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cStart.add(Calendar.DAY_OF_MONTH, 1);
            dateList.add(getStringDate(cStart.getTime()));
        }
        return dateList;
    }
	
	


	/**
	* 计算两个日期之间相差的天数
	* @param enddate String 后一个日期
	* @param startdate String 前一个日期
	* @return int 返回两个日期之间相差的天数（正整数）
     * add by lk
	*/
//	public static int getHowLongDays(String enddate,String startdate){
//		try{
//			Calendar start = Calendar.getInstance();
//			Calendar end = Calendar.getInstance();
//			
//			start.set(Integer.parseInt(startdate.substring(0,4)),
//					Integer.parseInt(startdate.substring(4,6))-1,
//					Integer.parseInt(startdate.substring(6,8)));
//			
//			end.set(Integer.parseInt(enddate.substring(0,4)),
//					Integer.parseInt(enddate.substring(4,6))-1,
//					Integer.parseInt(enddate.substring(6,8)));
//			
//			long starttime=start.getTimeInMillis();
//			long endtime=end.getTimeInMillis();
//			int howlongdays = new Long((endtime - starttime)/(1000*60*60*24)).intValue();
//			return howlongdays;
//		}catch(Exception e){
//			return -1;
//		}
//	}
	
	
	
	  /**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     * add by lk
     */    
//    public static int daysBetween(Date smdate,Date bdate) throws ParseException    
//    {    
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
//        smdate=sdf.parse(sdf.format(smdate));  
//        bdate=sdf.parse(sdf.format(bdate));  
//        Calendar cal = Calendar.getInstance();    
//        cal.setTime(smdate);    
//        long time1 = cal.getTimeInMillis();                 
//        cal.setTime(bdate);    
//        long time2 = cal.getTimeInMillis();         
//        long between_days=(time2-time1)/(1000*3600*24);  
//            
//       return Integer.parseInt(String.valueOf(between_days));           
//    }
    
   /**
    * 获取现在日期字符串
    * @Title: getStringDate   
    * @Description: TODO(描述这个方法的作用)   
    * @param: @return      
    * @return: String      
    * @throws
    */
    public static String getStringDate() {
	     Date currentTime = new Date();
	     SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	     String dateString = formatter.format(currentTime);
	     return dateString;
	}
    
    public static LocalDate getLocalDateByString(String dateStr) {
    	return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    
    public static LocalDate getLocalDateByDate(Date date) {
    	return getLocalDateByString(getStringDate(date));
    }
    
    /**
     * 获取当前时间"yyyy-MM-dd HH:mm:ss格式的字符串
     * @Title: getStringNow
     * @Description: TODO(描述这个方法的作用)
     * @author: lk
     * @time: 2020年1月2日 下午6:22:56
     */
    public static String getStringNow() {
	     return getStringNow("yyyy-MM-dd HH:mm:ss");
	}
    
    public static String getStringNow (String pattern) {
	   SimpleDateFormat formatter = new SimpleDateFormat(pattern);
	   return formatter.format(new Date());
    }
    
    public static String getStringDate(Date date) {
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    	String dateString = formatter.format(date);
    	return dateString;
    }
    
    public static String getStringDate(Date date, String pattern) {
    	SimpleDateFormat formatter = new SimpleDateFormat(pattern);
    	String dateString = formatter.format(date);
    	return dateString;
    }
    
//    public static String getStringDate(Date date, String pattern) {
//    	SimpleDateFormat formatter = new SimpleDateFormat(pattern);
//    	String dateString = formatter.format(date);
//    	return dateString;
//    }
    
    /**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     * add by lk
     */ 
    public static int daysBetween(Date smdate, LocalDateTime bdate) throws ParseException    
    {    
    	Date bdates = localDateTimeToDate(bdate);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdates=sdf.parse(sdf.format(bdates));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdates);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(Math.abs(time2-time1))/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));           
    }
    
    public static int daysBetween(LocalDateTime smdates, LocalDateTime bdates) throws ParseException    
    {    
    	Date bdate = localDateTimeToDate(bdates);
    	Date smdate = localDateTimeToDate(smdates);
    	
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));           
    }
    
    public static long daysBetween(LocalDateTime smdates, LocalDateTime bdates, String type) throws ParseException    
    {    
        Long time1 = smdates.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        Long time2 = bdates.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        
        /*long nd = 1000*24*60*60;//一天的毫秒数
        long nh = 1000*60*60;//一小时的毫秒数
    	long nm = 1000*60;//一分钟的毫秒数
    	long ns = 1000;//一秒钟的毫秒数long diff;*/      
        
        long nd = 86400000;//一天的毫秒数
    	long nh = 3600000;//一小时的毫秒数
    	long nm = 60000;//一分钟的毫秒数
    	long ns = 1000;//一秒钟的毫秒数long diff;
//        long betweens = 0;
//        long between_days=(time2-time1)/(1000*3600*24);  
		long diff = time2 - time1;
    	if("day".equalsIgnoreCase(type)){//返回相差的天数
    		long day = diff/nd;//计算差多少天
    		return day;
    	}else if("hour".equalsIgnoreCase(type)){//返回相差的小时数
    		long hour = diff/nh;//计算差多少小时
    		return hour;
    	}else if("min".equalsIgnoreCase(type)){//返回相差的分钟数
    		long min = diff/nm;//计算差多少分钟
    		return min;
    	}else{//返回相差的秒数
    		long sec = diff/ns;//计算差多少秒
    		return sec;
    	}
            
    }
    

    /**
     * 计算两个日期之间相差的时间(返回整数)
     * @param startTime
     * @param endTime
     * @param type day:返回相差的天数  hour:返回相差的小时数 min:返回相差的分钟数  sec:返回相差的秒数
     * @return
     * add by lk
     */
    public static Long timeBetween_long(Date startTime,Date endTime,String type) throws Exception{    
    	//按照传入的格式生成一个simpledateformate对象
    	long nd = 86400000;//一天的毫秒数
     	long nh = 3600000;//一小时的毫秒数
     	long nm = 60000;//一分钟的毫秒数
     	long ns = 1000;//一秒钟的毫秒数long diff;
    	//获得两个时间的毫秒时间差异
    	long diff = endTime.getTime() - startTime.getTime();
    	if("day".equalsIgnoreCase(type)){//返回相差的天数
    		long day = diff/nd;//计算差多少天
    		return day;
    	}else if("hour".equalsIgnoreCase(type)){//返回相差的小时数
//    		long hour = diff%nd/nh;//计算差多少小时
    		long hour = diff/nh;//计算差多少小时
    		return hour;
    	}else if("min".equalsIgnoreCase(type)){//返回相差的分钟数
//    		long min = diff%nd%nh/nm;//计算差多少分钟
    		long min = diff/nm;//计算差多少分钟
    		return min;
    	}else{//返回相差的秒数
//    		long sec = diff%nd%nh%nm/ns;//计算差多少秒
    		long sec = diff/ns;//计算差多少秒
    		return sec;
    	}
    }
    /**
     * 计算两个日期之间相差的时间（返回小数）
     * @param startTime
     * @param endTime
     * @param type day:返回相差的天数  hour:返回相差的小时数 min:返回相差的分钟数  sec:返回相差的秒数
     * @return
     * add by lk
     */
    public static Double timeBetween_double(Date startTime,Date endTime,String type) throws Exception{   
    	DecimalFormat df=new DecimalFormat("0.0");  
    	//按照传入的格式生成一个simpledateformate对象
    	double nd = 86400000;//一天的毫秒数
    	double nh = 3600000;//一小时的毫秒数
    	double nm = 60000;//一分钟的毫秒数
     	double ns = 1000;//一秒钟的毫秒数long diff;
    	
    	if(startTime == null || endTime == null){
    		return null;
    	}
		//获得两个时间的毫秒时间差异
		long diff = endTime.getTime() - startTime.getTime();
		if("day".equalsIgnoreCase(type)){//返回相差的天数
			double day = diff/nd;//计算差多少天
			return Double.parseDouble(df.format(day));
		}else if("hour".equalsIgnoreCase(type)){//返回相差的小时数
			double hour = diff/nh;//计算差多少小时
			return Double.parseDouble(df.format(hour));
		}else if("min".equalsIgnoreCase(type)){//返回相差的分钟数
			double min = diff/nm;//计算差多少分钟
			return Double.parseDouble(df.format(min));
		}else{//返回相差的秒数
			double sec = diff/ns;//计算差多少秒
			return Double.parseDouble(df.format(sec));
		}
    }

    /**
     * 获取明天
     * @Title: getNextDate
     * @Description: TODO(描述这个方法的作用)
     * @author: lk
     * @time: 2020年11月26日 上午10:32:35
     */
    public static Date getNextDate(Date d) {
    	Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.setTime(d); //设置时间为当前时间
        ca.add(Calendar.DATE, 1);
        return ca.getTime();
    }
    
    /**
     * 获取当前时间前一天的时间
     * @Title: getLastDate   
     * @Description: TODO(描述这个方法的作用)   
     * @param: @return      
     * @return: Date      
     * @throws
     */
    public static Date getLastDate() {
    	Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.setTime(new Date()); //设置时间为当前时间
        ca.add(Calendar.DATE, -1);
        return ca.getTime();
    }
    
    /**
     * 获取当前时间前n天的时间
     * @Title: getLastDate   
     * @Description: TODO(描述这个方法的作用)   
     * @param: @param n
     * @param: @return      
     * @return: Date      
     * @throws
     */
    public static Date getLastNDate(int n) {
    	Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.setTime(new Date()); //设置时间为当前时间
        ca.add(Calendar.DATE, -n);
        return ca.getTime();
    }
    
    
    /**
     * 获取当前时间前n天的日期
     * @Title: getLastNLocalDateTime
     * @Description: TODO(描述这个方法的作用)
     * @author: lk
     * @time: 2020年8月6日 下午5:07:06
     */
    public static LocalDateTime getLastNLocalDateTime(int n) {
    	Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.setTime(new Date()); //设置时间为当前时间
        ca.add(Calendar.DATE, -n);
        String d = dateToString(ca.getTime(), "yyyy-MM-dd") + " 00:00:00";
        return stringToLocalDateTime(d);
    }
    
    
    /**
     * 比较两个日期的大小，true:time1<time2; false:time1>time2
     * @Title: compare   
     * @Description: TODO(描述这个方法的作用)   
     * @param: @param time1
     * @param: @param time2
     * @param: @return
     * @param: @throws ParseException      
     * @return: boolean      
     * @throws
     */
//    public static boolean compare(String time1,String time2) throws ParseException{
//		//如果想比较日期则写成"yyyy-MM-dd"就可以了
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		//将字符串形式的时间转化为Date类型的时间
//		Date a=sdf.parse(time1);
//		Date b=sdf.parse(time2);
//		//Date类的一个方法，如果a早于b返回true，否则返回false
//		if(a.before(b))
//			return true;
//		else
//			return false;
//		/*
//		 * 如果你不喜欢用上面这个太流氓的方法，也可以根据将Date转换成毫秒
//		if(a.getTime()-b.getTime()<0)
//			return true;
//		else
//			return false;
//		*/
//	}
    
    /**
     * 比较两个日期的大小，true:time1<time2; false:time1>time2
     * @Title: compare   
     * @Description: TODO(描述这个方法的作用)   
     * @param: @param time1
     * @param: @param time2
     * @param: @return
     * @param: @throws ParseException      
     * @return: boolean      
     * @throws
     */
    public static boolean compare(Date time1,String time2) throws ParseException{
		//如果想比较日期则写成"yyyy-MM-dd"就可以了
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//将字符串形式的时间转化为Date类型的时间
//		Date a=sdf.parse(time1);
		Date b=sdf.parse(time2);
		//Date类的一个方法，如果a早于b返回true，否则返回false
		if(time1.before(b)){
			return true;
		}else{
			return false;
		}
			
		/*
		 * 如果你不喜欢用上面这个太流氓的方法，也可以根据将Date转换成毫秒
		if(a.getTime()-b.getTime()<0)
			return true;
		else
			return false;
		*/
	}
    
    
    /**
     * 比较两个日期的大小，true:time1<time2; false:time1>time2
     * @Title: compare   
     * @Description: TODO(描述这个方法的作用)   
     * @param: @param time1
     * @param: @param time2
     * @param: @return
     * @param: @throws ParseException      
     * @return: boolean      
     * @throws
     */
//    public static boolean compare(Date time1,LocalDateTime time2) throws ParseException{
//		//如果想比较日期则写成"yyyy-MM-dd"就可以了
////		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		//将字符串形式的时间转化为Date类型的时间
////		Date a=sdf.parse(time1);
////		Date b=sdf.parse(time2);
//		Date b = localDateTimeToDate(time2);
//		//Date类的一个方法，如果a早于b返回true，否则返回false
//		if(time1.before(b))
//			return true;
//		else
//			return false;
//		/*
//		 * 如果你不喜欢用上面这个太流氓的方法，也可以根据将Date转换成毫秒
//		if(a.getTime()-b.getTime()<0)
//			return true;
//		else
//			return false;
//		*/
//	}
    
//    public static boolean compare(Date time1,Date time2) throws ParseException{
//		// 如果你不喜欢用上面这个太流氓的方法，也可以根据将Date转换成毫秒
//		if(time1.getTime()-time2.getTime()<=0)
//			return true;
//		else
//			return false;
//		
//	}
    
    public static List<Map<String, String>> getColumnList(String min,String max) {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		Date curr = DateUtil.stringToDate(min, "yyyy-MM");
		Date maxs = DateUtil.stringToDate(max, "yyyy-MM");
		if(curr!=null&&maxs!=null){
			while (maxs.getTime() - curr.getTime() >= 0) { // 比较累加月份和结束月份的时间戳大小
		         // 如果上述比较为大于等于时，继续累加月份
		         String currM = DateUtil.getStringDate(curr, "yyyy-MM");
		         Map<String, String> map = new HashMap<String, String>();
		         map.put("name", currM);
		         map.put("code", currM);
		         result.add(map);
		         Calendar c = Calendar.getInstance();
		         c.setTime(curr);
		         c.add(Calendar.MONTH, +1);
		         curr = c.getTime();
		    }
		}
		return result;
	}
    
    /**
	 * 获取两个时间之间的所有月份
	 * @param minDate
	 * @param maxDate
	 * @return
	 * @throws Exception
	 */
    public static List<String> getMonthBetween(String minDate, String maxDate) {
		ArrayList<String> result = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");// 格式化为年月

		Calendar min = Calendar.getInstance();
		Calendar max = Calendar.getInstance();
		try {
			min.setTime(sdf.parse(minDate));
			min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
	
			max.setTime(sdf.parse(maxDate));
			max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
	
			Calendar curr = min;
			while (curr.before(max)) {
				result.add(sdf.format(curr.getTime()));
				curr.add(Calendar.MONTH, 1);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
    
    public static String getLastDayOfMonth(int year,int month) {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfMonth = sdf.format(cal.getTime());
        return lastDayOfMonth;
    }
    
    public static boolean isFirstDayofMonth(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
	    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
	    return dayOfMonth == 1;
	}
}
