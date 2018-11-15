package com.cubicpark.mechanic.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public enum DateUtil {
	INSTANCE;
	
	/**
	 * 判断是否是周未
	 * 
	 * @param date
	 * @return
	 */
	public boolean isWeekend(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		switch (dayOfWeek) {
		case 7:
			return true;
		case 1:
			return true;
		default:
			return false;
		}
	}

	/**
	 * 取得前一天
	 * @param date
	 * @return
	 */
	public Date getBeforeDate(Date date) {
		Calendar gc = Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.DATE, -1);
		return gc.getTime();
	}
	
	/**
	 * 取得前X天
	 * @param date
	 * @return
	 */
	public Date getBeforeDate(Date date,int x) {
		Calendar gc = Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.DATE, -x);
		return gc.getTime();
	}
	
	/**
	 * 
	 * 功能描述: <br>
	 * 将日期格式的字符串格式化
	 *
	 * @param dateString
	 * @param format
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public String formatDateString(String dateString, String format) {
	    return date2String(covert2Date(dateString, format), format);
	}

	
	public String date2String(Date date,String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}
	
	public Date covert2Date(String dateStr,String format) {
		//"yyyy-MM-dd"
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		try {
			return dateFormat.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	//返回系统当前时间的年月日----20100406
	public String getSysDateByDayCounts(int dayCounts) {
		Calendar c = GregorianCalendar.getInstance();
		if(0 != dayCounts) {
			c.add(Calendar.DAY_OF_MONTH, dayCounts);
		}
		SimpleDateFormat sdfMonth = new SimpleDateFormat("yyyyMMdd");
		return sdfMonth.format(c.getTime());
	}
	public String getFormatDate(int dayCounts) {
		Calendar c = GregorianCalendar.getInstance();
		if(0 != dayCounts) {
			c.add(Calendar.DAY_OF_MONTH, dayCounts);
		}
		SimpleDateFormat sdfMonth = new SimpleDateFormat("yyyy-MM-dd");
		return sdfMonth.format(c.getTime());
	}
	
	public Date getNextDate(Date date) {
		Calendar gc = Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.DATE, 1);
		return gc.getTime();
	}
	
	public String getNextDateString(String dateStr,String format){
		if(dateStr==null){
			return null;
		}
		Date datetemp = covert2Date(dateStr,format);
		datetemp = getNextDate(datetemp);
		return date2String(datetemp,format);
	}
	
	public Date getNextDate(String dateStr,String format){
		if(dateStr==null){
			return null;
		}
		Date datetemp = covert2Date(dateStr,format);
		datetemp = getNextDate(datetemp);
		return datetemp;
	}
}
