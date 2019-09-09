package com.example.demo.config.message.formatter;

import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

/**
 * @author huangzhongjun
 *
 */
public class StringToLocalDate implements Converter<String, LocalDate> {

	@Override
	public LocalDate convert(String source) {
		
		if (StringUtils.isBlank(source)) {
			
			return null;
		}
		
		return LocalDate.parse(source);
	}

}
