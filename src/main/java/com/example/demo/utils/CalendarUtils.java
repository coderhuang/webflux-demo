package com.example.demo.utils;

import java.util.Calendar;
import java.util.TimeZone;

public final class CalendarUtils {
	
	private CalendarUtils() {}
	
	public static final String ZONE_GMT_8_STRING = "GMT+08:00";
	
    public static Calendar asianShanghai() {
    	// calendar是可变对象，为线程安全，每次调用都生成新的对象
    	Calendar asianShanghaiCalendar = Calendar.getInstance(TimeZone.getTimeZone(ZONE_GMT_8_STRING));
    	asianShanghaiCalendar.setTimeInMillis(0);
    	return asianShanghaiCalendar;
    }
}
