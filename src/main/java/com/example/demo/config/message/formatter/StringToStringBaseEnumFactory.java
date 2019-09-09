package com.example.demo.config.message.formatter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import com.example.demo.type.StringBaseEnum;


/**
 * @author huangzhongjun
 *
 */
public class StringToStringBaseEnumFactory implements ConverterFactory<String, StringBaseEnum> {

	@Override
	public <T extends StringBaseEnum> Converter<String, T> getConverter(Class<T> targetType) {

		return new StringToStringBaseEnumConverter<>(targetType);
	}

	public static class StringToStringBaseEnumConverter<T extends StringBaseEnum> implements Converter<String, T> {

		private Class<T> type;
		
		public StringToStringBaseEnumConverter(Class<T> type) {
			
			this.type = type;
		}
		
		@Override
		public T convert(String source) {
			
			if (StringUtils.isBlank(source)) {
				
				return null;
			}
			
			return StringBaseEnum.valEnum(type, source);
		}
	}
}
