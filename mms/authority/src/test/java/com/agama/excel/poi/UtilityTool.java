package com.agama.excel.poi;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UtilityTool {
	public UtilityTool() {
		super();
	}

	/**
		 * 
		 * @方法名 ：getServerPath<br>
		 * @方法描述 ：获取服务器路径<br>
		 * @return 返回类型 ：String
		 */
		public static String getServerPath(Object object) {
			String serverPath = object.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
			serverPath = serverPath.substring(1, serverPath.indexOf("WEB-INF"));
			return serverPath;
		}
		
		public static String getCurrDate(String format){
			Date date = new Date();
//			String format = "yyyyMMddHHmmss";
			DateFormat df=new SimpleDateFormat(format);
			return df.format(date);
		}
		
		public static Date getDateFromStartDateAddSomeDays(int startYear,int startMonth,int startDay,int someDays){
			Calendar cal = Calendar.getInstance();
			cal.set(startYear, startMonth, startDay);
			cal.add(Calendar.DAY_OF_MONTH, someDays);
//			System.out.print(cal.get(Calendar.YEAR) + "年");
//			System.out.print(cal.get(Calendar.MONTH) + 1 + "月");
//			System.out.println(cal.get(Calendar.DAY_OF_MONTH) + "日");
			return cal.getTime();
		}
		
		public static Date getFormatDate(String date,String format) {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			Date targetDate = null;
			try {
				targetDate = formatter.parse(date);
				cal.setTime(targetDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return cal.getTime();
		}
		
		public static void main(String[] args) {
			Date date = getDateFromStartDateAddSomeDays(1899, 11, 30, 41275);
			System.out.println(date);

		}
}

