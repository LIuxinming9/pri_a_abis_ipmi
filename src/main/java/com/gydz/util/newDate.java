package com.gydz.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class newDate {

	public static Date newDate() throws Exception {
		Date date = new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd kk:mm:ss:SSS ");
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai")); //格式差8小时
		String format = sdf.format(date);
		Date newDate = sdf.parse(format);
		return newDate;
	}
}
