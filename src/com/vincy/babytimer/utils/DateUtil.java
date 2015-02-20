package com.vincy.babytimer.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateUtil {
	private static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
	private static final String FORMAT_DATE = "yyyy-MM-dd";
	private static final String FORMAT_TIME = "HH:mm:ss";

	public static String getCurrentDateTime() {
		SimpleDateFormat fmt = new SimpleDateFormat(FORMAT_DATETIME,
				Locale.CHINA);
		return fmt.format(System.currentTimeMillis());
	}

	public static String getCurrentDate() {
		SimpleDateFormat fmt = new SimpleDateFormat(FORMAT_DATE, Locale.CHINA);
		return fmt.format(System.currentTimeMillis());
	}

}
