package org.kd.server.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	public static final String dateRegex = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
	
	//给指定时间 增加或减少分钟数
	public static Date addMinutesToDate(Date date, int mins) {
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, mins);

		return c.getTime();
	}
	
	//指定日期的前后多少天
	public static Date aroundDay(Date date, int day, boolean clearTime) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		if (clearTime) {
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
		}
		c.add(Calendar.DATE, -day);

		return c.getTime();
	}

	public static Date parseDateByString(String date,String pattern) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.parse(date);
	}
	
	public static String formatDateByString(Date date,String pattern){
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}
	
	//查询指定日期当周第一天
	public static Date findFirstDayOfWork(Date date){
		Calendar currentDate = Calendar.getInstance();
		currentDate.setTime(date);
        currentDate.setFirstDayOfWeek(Calendar.MONDAY);
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return currentDate.getTime();
	}
	
	//查询指定日期当月第一天
	public static Date findFirstDayOfMonth(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
  
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}
	
	//查询指定日期相隔几月之前或之后的日期时间
	public static Date findMonthBeforeOrAfter(Date date,int monthCount){
		Calendar currentDate = Calendar.getInstance();
		currentDate.setTime(date);
		currentDate.add(Calendar.MONTH, monthCount);
		return currentDate.getTime();
	}
	
	//给指定日期添加年份
	public static Date addYearsToDate(Date date, int years) {
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, years);
		return c.getTime();
	}
}
