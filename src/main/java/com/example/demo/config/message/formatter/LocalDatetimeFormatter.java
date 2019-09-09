package com.example.demo.config.message.formatter;

import static com.example.demo.utils.LocalDateTimeUtils.DATE_TIME_FORMATTER;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Locale;

import org.springframework.format.Formatter;


public class LocalDatetimeFormatter implements Formatter<LocalDateTime> {

	@Override
	public String print(LocalDateTime object, Locale locale) {
		
		return object.format(DATE_TIME_FORMATTER);
	}

	@Override
	public LocalDateTime parse(String text, Locale locale) throws ParseException {
		
		return LocalDateTime.parse(text, DATE_TIME_FORMATTER);
	}

}
