package com.example.demo.config.message.formatter;

import java.time.LocalTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

public class StringToLocalTime implements Converter<String, LocalTime> {

	@Override
	public LocalTime convert(String source) {
		
		if (StringUtils.isBlank(source)) {
			
			return null;
		}
		
		return LocalTime.parse(source);
	}

}
