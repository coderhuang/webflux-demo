package com.example.demo.utils;

import static java.time.Month.JANUARY;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class LocalDateTimeUtils {

	private LocalDateTimeUtils() {
	}

	public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public static final String DATE_PATTERN = "yyyy-MM-dd";

	public static final String TIME_PATTERN = "HH:mm:ss";

	public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

	public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

	public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_PATTERN);

	/**
	 * 业务意义上的最小时间
	 */
	public static final LocalDateTime MIN = LocalDateTime.of(1970, JANUARY, 1, 0, 0, 0);
}
