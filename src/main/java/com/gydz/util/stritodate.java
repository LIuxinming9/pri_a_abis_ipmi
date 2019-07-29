package com.gydz.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class stritodate {

	public static String strtodatetostr(String time) {
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String format = "";
		 try {
			Date date = sdf.parse(time);
			SimpleDateFormat dts = new SimpleDateFormat("yyyyMMdd");
			format = dts.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return format;
	}
}
