package com.example.demo.config.message.formatter;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import com.example.demo.type.IntegerBaseEnum;


public class StringToIntegerBaseEnumFactory implements ConverterFactory<String, IntegerBaseEnum> {

	@Override
	public <T extends IntegerBaseEnum> Converter<String, T> getConverter(Class<T> targetType) {
		
		return new StringToIntegerBaseEnumConverter<>(targetType);
	}
	
	public static class StringToIntegerBaseEnumConverter<T extends IntegerBaseEnum> implements Converter<String, T>{

		private Class<T> type;
		
		public StringToIntegerBaseEnumConverter(Class<T> type) {
			
			this.type = type;
		}
		
		@Override
		public T convert(String source) {
			
			if (!NumberUtils.isCreatable(source)) {
				
				return null;
			}
			
			int value = NumberUtils.toInt(source);
			return IntegerBaseEnum.valEnum(type, value);
		}
	}

}
