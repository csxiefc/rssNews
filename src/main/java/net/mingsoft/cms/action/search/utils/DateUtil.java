package net.mingsoft.cms.action.search.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DateUtil  {

 	/**
 	 * 日志文件定义
 	 */
	private static Logger log = LoggerFactory.getLogger(DateUtil.class);

	/**
	 * 转换格式的定义
	 */
	private static SimpleDateFormat dateformat = new SimpleDateFormat();
	
	public static final String MONTH_FLAG = "month";
	public static final String WEEK_FLAG = "week";
	public static final String YEAR_FLAG = "year";
	
	/**
	 * 构造器
	 *
	 */
	public DateUtil() {
	}

	/**
	 * 将字符串形式的日期/时间转换成java.util.Date类型
	 * @param strDate String 字符串形式的日期/时间
	 * @param pattern String 需要转换的格式(如：yyyy-MM-dd)
	 * @return 返回Date类型的日期
	 */
	public static Date parseDateFormat(String strDate, String pattern) {
		Date date = null;
		dateformat.applyPattern(pattern);
		try {
			date = dateformat.parse(strDate);
		}
		catch (Exception e) {
			log.error(e.getMessage());
		}
		return date;
	}
	public static String getTimestampToString(String formatPattern, Timestamp obj) {
        SimpleDateFormat df = new SimpleDateFormat(formatPattern);
        String str = df.format(obj);
        return str;
    }
	/**
	 * 将日期/时间参数转换成<yyyy-MM-dd HH:mm:ss,SSS>格式的字符串
	 * @param date java.util.Date 时间参数
	 * @return 转换后的时间字符串
	 */
	public String getDateMilliFormat(Date date) {
		return getDateFormat(date, "yyyy-MM-dd HH:mm:ss,SSS");
	}

	/**
	 * 将日期/时间参数转换成<yyyy-MM-dd HH:mm:ss>格式的字符串
	 * @param date java.util.Date 时间参数
	 * @return 转换后的时间字符串
	 */
	public String getDateSecondFormat(Date date) {
		return getDateFormat(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 将日期/时间参数转换成<yyyy-MM-dd HH:mm>格式的字符串
	 * @param date java.util.Date 时间参数
	 * @return 转换后的时间字符串
	 */
	public String getDateMinuteFormat(Date date) {
		return getDateFormat(date, "yyyy-MM-dd HH:mm");
	}

	/**
	 * 将日期/时间参数转换成<yyyy-MM-dd>格式的字符串
	 * @param date java.util.Date 时间参数
	 * @return 转换后的时间字符串
	 */
	public static String getShortDateFormat(Date date) {
		return getDateFormat(date, "yyyy-MM-dd");
	}

	/**
	 * 将日期/时间参数转换成<yyyyMMdd>格式的字符串
	 * @param date java.util.Date 时间参数
	 * @return 转换后的时间字符串
	 */
	public String getSuperShortDateFormat(Date date) {
		return getDateFormat(date, "yyyyMMdd");
	}

	/**
	 * 获取指定格式的日期/时间字符串
	 * @param date java.util.Date 日期/时间参数
	 * @param pattern String 需要转换的格式
	 * @return 转换后的日期/时间字符串
	 */
	public static String getDateFormat(Date date, String pattern) {
		dateformat.applyPattern(pattern);
		return dateformat.format(date);
	}
	
	/**
	 * 获取指定格式的日期/时间字符串
	 * @param date java.util.Date 日期/时间参数
	 * @param pattern String 需要转换的格式
	 * @return 转换后的日期/时间字符串
	 */
	public static String getDateFormat(String date, String pattern) {
		return getDateFormat(parseDateFormat(date, pattern), pattern);
	}
	
	/**
	 * 将时间参数转换成<b>长</b>时间格式的字符串
	 * 时间格式：2010年05月19日 08时35分18秒
	 * @param date java.util.Date 时间参数
	 * @return 转换后的时间字符串
	 */
	public String toLongDateString(Date date){
		dateformat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E ");        
		return dateformat.format(date);
	}
	
	/**
	 * 将时间参数转换成<b>年份或月份</b>时间格式的字符串
	 * 时间格式：2011或01
	 * @param date java.util.Date 时间参数
	 * @param flag String 年分/月份标记
	 * @return String 转换后的年份或月份
	 */
	public String getDateYearFormat(Date date,String flag) {
		if(flag.equals("year"))
			return getDateFormat(date, "yyyy");
		else
			return getDateFormat(date, "MM");
	}

	/**
	 * 将时间参数转换成<b>短</b>时间格式的字符串
	 * 时间格式：2010年05月19日
	 * @param date java.util.Date 时间参数
	 * @return 转换后的时间字符串
	 */
	public String toShortDateString(Date date){
		dateformat = new SimpleDateFormat("yyyy年MM月dd日");        
		return dateformat.format(date);
	}    

	/**
	 * 判断当前时间是否在时间date2之前
	 * 时间格式 2005-4-21 16:16:34
	 * @param date2 String：被比较时间
	 * @return 如果当前时间在date2之前，返回真；否则，返回假
	 */
	public boolean isDateBefore(String date2)
	{
		try{
			Date date1 = new Date();
			DateFormat df = DateFormat.getDateTimeInstance();
			return date1.before(df.parse(date2)); 
		}catch(ParseException e){
			log.error("判断当前时间是否在时间"+date2+"之前",e);
			return false;
		}
	}
	
	/**
	 * 判断时间date1是否在时间date2之前
	 * 时间格式 2005-4-21 16:16:34
	 * @param date1 String：比较时间
	 * @param date2 String：被比较时间
	 * @return 如果date1在date2之前，返回真；否则，返回假
	 */
	public boolean isDateBefore(String date1,String date2)
	{
		try{
			DateFormat df = DateFormat.getDateTimeInstance();
			return df.parse(date1).before(df.parse(date2)); 
		}catch(ParseException e){
			log.error("判断时间"+date1+"是否在时间"+date2+"之前",e);
			return false;
		}
	}

	/**
	* 计算两个日期之间相差的天数
	* @param enddate String 后一个日期
	* @param startdate String 前一个日期
	* @return int 返回两个日期之间相差的天数（正整数）
	*/
	public int getHowLongDays(String enddate,String startdate){
		try{
			Calendar start = Calendar.getInstance();
			Calendar end = Calendar.getInstance();
			
			start.set(Integer.parseInt(startdate.substring(0,4)),
					Integer.parseInt(startdate.substring(4,6))-1,
					Integer.parseInt(startdate.substring(6,8)));
			
			end.set(Integer.parseInt(enddate.substring(0,4)),
					Integer.parseInt(enddate.substring(4,6))-1,
					Integer.parseInt(enddate.substring(6,8)));
			
			long starttime=start.getTimeInMillis();
			long endtime=end.getTimeInMillis();
			int howlongdays = new Long((endtime - starttime)/(1000*60*60*24)).intValue();
			return howlongdays;
		}catch(Exception e){
			return -1;
		}
	}	
	
	/**
	 * 对于日期以.0结尾的日期字符串去掉后面的.0
	 * @param dataStr
	 * @return
	 */
	public static String getDateStr(String dataStr) {
		if(!"".equals(dataStr)) {
			if(dataStr.endsWith(".0"))
				return dataStr.substring(0, dataStr.lastIndexOf(".0"));
			else 
				return dataStr;
		} else {
			return "";
		}
	}
	
	/**
	 * 获取当前期日是星期几
	 * @param dt
	 * @return
	 */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;

        return weekDays[w];
    }
    
    public static String getAMPM(Date dt){
    	Calendar c = Calendar.getInstance(); 
    	return Calendar.AM == c.get(Calendar.AM_PM)?"上午":"下午";
    }
    
    /**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
    public static int daysBetween(Date smdate,Date bdate) throws ParseException    
    {    
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
    
   /**
   * @方法名: compare_date
   * @描述:比较两个时间的大小
   * @作者:程尚球
   * @时间:2014-8-27 下午11:11:26
   * @参数:@param DATE1
   * @参数:@param DATE2
   * @参数:@return 返回值为-1：DATE1在DATE2后(即为已经超时)  返回值为1:DATE1在DATE2之前（即为未超时）
   * @返回值：int
    */
   public static int compare_date(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
//                System.out.println("dt1在dt2前");
                return -1;
            } else if (dt1.getTime() < dt2.getTime()) {
//                System.out.println("dt1在dt2后");
                return 1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }
   
   /**
    * @方法名: compare_date
    * @描述:比较两个时间的大小
    * @作者:程尚球
    * @时间:2014-8-27 下午11:11:26
    * @参数:@param DATE1
    * @参数:@param DATE2
    * @参数:@param pattern 日期格式
    * @参数:@return 返回值为-1：DATE1在DATE2后(即为已经超时)  返回值为1:DATE1在DATE2之前（即为未超时）
    * @返回值：int
    */
   public static int compare_date(String DATE1, String DATE2,String pattern) {
	   DateFormat df = new SimpleDateFormat(pattern);
	   try {
		   Date dt1 = df.parse(DATE1);
		   Date dt2 = df.parse(DATE2);
		   if (dt1.getTime() > dt2.getTime()) {
//                System.out.println("dt1在dt2前");
			   return -1;
		   } else if (dt1.getTime() < dt2.getTime()) {
//                System.out.println("dt1在dt2后");
			   return 1;
		   } else {
			   return 0;
		   }
	   } catch (Exception exception) {
		   exception.printStackTrace();
	   }
	   return 0;
   }
   
   public static String getYear(){
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
	   return formatter.format(new Date());
   }
   public static int getSpecifyYearOfDate(int count){
	   	Calendar c=Calendar.getInstance();
	   	 c.setTime(new Date());
	   	 c.roll(Calendar.YEAR, count);
	   	 return c.get(Calendar.YEAR);
	   }
   /**
    * 取到指定离当前 count 个的月份 
    * count为正是下面的月份，负是上面的月份
    * @param date
    * @param count
    * @return
    */
   public static int getSpecifyMonthOfDate(Date date ,int count){
   	Calendar c=Calendar.getInstance();
   	 c.setTime(new Date());
   	 c.roll(Calendar.MONTH, count+1);
   	 return c.get(Calendar.MONTH);
   }
   
   
   /**
    * 获取当前月份
    * @param date
    * @return
    */
   public static int getMonth(){
	   	Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
        return cal.get(Calendar.MONTH)+1;
   }
   
	public static Boolean isBetweenTwoDay(Date firstDay,Date lastDay){
		Date now  = new Date();
		return  (lastDay.after(now) && firstDay.before(now));
	}
	
	/**
	 * 将标准英文格式的时间字符串转化为Date对象
	 * 
	 * @param dateStr
	 *            格式固定为：Thu Jul 25 20:49:00 CST 2013
	 * @return
	 */
	public static Date getDateFromForeginStr(String dateStr) {
		if (dateStr != null && dateStr.length() == 28
				&& dateStr.indexOf("CST") == 20) {
			String year = dateStr.substring(24, 28);
			String date = dateStr.substring(8, 10);
			String time = dateStr.substring(11, 19);
			String month = dateStr.substring(4, 7);
			if (month.equals("Jan")) {
				dateStr = year + "-" + "01" + "-" + date + " " + time;
			}
			if (month.equals("Feb")) {
				dateStr = year + "-" + "02" + "-" + date + " " + time;
			}
			if(month.equals("Mar")){
				dateStr = year + "-" + "03" + "-" + date+" "+time;
			}
			if(month.equals("Apr")){
				dateStr = year + "-" + "04" + "-" + date+" "+time;
			}
			if(month.equals("May")){
				dateStr = year + "-" + "05" + "-" + date+" "+time;
			}
			if(month.equals("Jun")){
				dateStr = year + "-" + "06" + "-" + date+" "+time;
			}
			if(month.equals("Jul")){
				dateStr = year + "-" + "07" + "-" + date+" "+time;
			}
			if(month.equals("Aug")){
				dateStr = year + "-" + "08" + "-" + date+" "+time;
			}
			if(month.equals("Sep")){
				dateStr = year + "-" + "09" + "-" + date+" "+time;
			}
			if(month.equals("Oct")){
				dateStr = year + "-" + "10" + "-" + date+" "+time;
			}
			if(month.equals("Nov")){
				dateStr = year + "-" + "11" + "-" + date+" "+time;
			}
			if(month.equals("Dec")){
				dateStr = year + "-" + "12" + "-" + date+" "+time;
			}
		}
		return DateUtil.parseDateFormat(dateStr,"yyyy-MM-dd HH:mm:ss");
	}	
	
	/** 
     * 时间戳转换成日期格式字符串 
     * @param seconds 精确到秒的字符串 
     * @param formatStr 
     * @return 
     */  
    public static String timeStamp2Date(String seconds,String format) {  
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){  
            return "";  
        }  
        if(format == null || format.isEmpty()) format = "yyyy-MM-dd HH:mm:ss";  
        SimpleDateFormat sdf = new SimpleDateFormat(format);  
        return sdf.format(new Date(Long.valueOf(seconds)));  
    }  
    
    
    public static void main(String []args){
    	getCurDateBeforeDate(new Date(),"year");
    }
    
    /**
	 * 获取当年的第一天
	 * @param year
	 * @return
	 */
	public static Date getCurrYearFirst(){
		Calendar currCal=Calendar.getInstance();  
		int currentYear = currCal.get(Calendar.YEAR);
		return getYearFirst(currentYear);
	}
	
	/**
	 * 获取当年的最后一天
	 * @param year
	 * @return
	 */
	public static Date getCurrYearLast(){
		Calendar currCal=Calendar.getInstance();  
		int currentYear = currCal.get(Calendar.YEAR);
		return getYearLast(currentYear);
	}
	
	/**
	 * 获取某年第一天日期
	 * @param year 年份
	 * @return Date
	 */
	public static Date getYearFirst(int year){
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		return currYearFirst;
	}
	
	/**
	 * 获取某年最后一天日期
	 * @param year 年份
	 * @return Date
	 */
	public static Date getYearLast(int year){
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = calendar.getTime();
		
		return currYearLast;
	}
	
	/**
	 * 获取当前月份的第一天
	 * @return
	 */
	public static Date getCurrMonthFirst(){
       Calendar cal=Calendar.getInstance();//获取当前日期 
       cal.add(Calendar.MONTH, 0);
       cal.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
	   return cal.getTime();
	}
	
	/**
	 * 获取当前月份的最后一天
	 * @return
	 */
	public static Date getCurrMonthLast(){
		Calendar cal=Calendar.getInstance();//获取当前日期
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));  
		return cal.getTime();
	}
	/**
	 * 获取当前月份的第一天
	 * @return
	 */
	public static Date getMonthFirst(int year,int month){
		Calendar cal=Calendar.getInstance();//获取当前日期 
		//设置年份
		cal.set(Calendar.YEAR,year);
		//设置月份
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		return cal.getTime();
	}
	
	/**
	 * 获取当前月份的最后一天
	 * @return
	 */
	public static Date getMonthLast(int year,int month){
		Calendar cal=Calendar.getInstance();//获取当前日期
		//设置年份
		cal.set(Calendar.YEAR,year);
		//设置月份
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));  
		return cal.getTime();
	}
	
	/**
	 * 获取传入时间的前一周、前一月、前一年的时间
	 * @param date 需要计算的时间
	 * @param type week:获取传入时间前一周的时间节点 month:获取传入时间前一个月的时间节点  year:获取传入时间前一年的时间节点
	 * @return
	 */
	public static Date getCurDateBeforeDate(Date date,String type){
		Date result = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		if(WEEK_FLAG.equals(type)){//过去七天
	        c.setTime(date);
	        c.add(Calendar.DATE, - 7);
	        result = c.getTime();
	        String day = format.format(result);
//	        System.out.println("过去七天："+day);
		}else if(MONTH_FLAG.equals(type)){
			 //过去一月
	        c.setTime(new Date());
	        c.add(Calendar.MONTH, -1);
	        result = c.getTime();
	        String mon = format.format(result);
//	        System.out.println("过去一个月："+mon);
		}else if(YEAR_FLAG.equals(type)){
			 //过去一年
	        c.setTime(new Date());
	        c.add(Calendar.YEAR, -1);
	        result = c.getTime();
	        String year = format.format(result);
//	        System.out.println("过去一年："+year);
		}
//        //过去三个月
//        c.setTime(new Date());
//        c.add(Calendar.MONTH, -3);
//        Date m3 = c.getTime();
//        String mon3 = format.format(m3);
//        System.out.println("过去三个月："+mon3);
        return result;
	}
	public static String getBeforeOneWeekDate(Date date){
		String result=null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		//过去七天
        c.add(Calendar.DATE, - 7);
        Date d = c.getTime();
        result = format.format(d);
		return result;
	}
}
