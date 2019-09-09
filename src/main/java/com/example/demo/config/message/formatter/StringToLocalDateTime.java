package com.example.demo.config.message.formatter;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import com.example.demo.utils.LocalDateTimeUtils;

/**
 * @author huangzhongjun
 *
 */
public class StringToLocalDateTime implements Converter<String, LocalDateTime> {

	@Override
	public LocalDateTime convert(String source) {
		
		if (StringUtils.isBlank(source)) {
			
			return null;
		}
		
		return LocalDateTime.parse(source, LocalDateTimeUtils.DATE_TIME_FORMATTER);
	}

	
}
