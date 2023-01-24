package kh.study.soccer.config;

import java.util.Calendar;

public class appDateUtil {
	//오늘 날짜를 문자열로 리턴
	public static String getNowDateToString() {
		Calendar cal = Calendar.getInstance();
		
		int year = cal.get(Calendar.YEAR);
		
		int month = cal.get(Calendar.MONTH) + 1;
		String monthStr = month / 10 == 0 ? "0" + month : month + "";
		
		int date = cal.get(Calendar.DATE);
		String dateStr = date / 10 == 0 ? "0" + date : date + "";
		
		String nowDate = year + "" + monthStr + "" + dateStr;
		
		return nowDate;
	}
	
	//오늘 날짜를 문자열로 리턴 + 포맷지정  
	public static String getNowDateToString(String format) {
		Calendar cal = Calendar.getInstance();
		
		int year = cal.get(Calendar.YEAR);
		
		int month = cal.get(Calendar.MONTH) + 1;
		String monthStr = month / 10 == 0 ? "0" + month : month + "";
		
		int date = cal.get(Calendar.DATE);
		String dateStr = date / 10 == 0 ? "0" + date : date + "";
		
		String nowDate = year + format + monthStr + format + dateStr;
		
		return nowDate;
	}
	
	
	//한 달 전 날짜를 문자열로 리턴
	public static String getBeforeMonthDateToString() {
		Calendar cal = Calendar.getInstance();
		
		int year = cal.get(Calendar.YEAR);
		
		int month = cal.get(Calendar.MONTH);
		String monthStr = month / 10 == 0 ? "0" + month : month + "";
		
		int date = cal.get(Calendar.DATE);
		String dateStr = date / 10 == 0 ? "0" + date : date + "";
		
		String nowDate = year + "-" + monthStr + "-" + dateStr;
		
		return nowDate;
	}
	
	
}













