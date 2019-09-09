package com.example.demo.config.message.formatter;

import java.util.Objects;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import com.example.demo.type.IntegerBaseEnum;

public class IntegerToIntegerBaseEnumFactory implements ConverterFactory<Integer, IntegerBaseEnum> {

	@Override
	public <T extends IntegerBaseEnum> Converter<Integer, T> getConverter(Class<T> targetType) {
		
		return new IntegerToIntegerBaseEnumConverter<>(targetType);
	}
	
	public static class IntegerToIntegerBaseEnumConverter<T extends IntegerBaseEnum> implements Converter<Integer, T>{

		private Class<T> type;
		
		public IntegerToIntegerBaseEnumConverter(Class<T> type) {
			
			this.type = type;
		}
		
		@Override
		public T convert(Integer source) {
			
			if (Objects.isNull(source)) {
				
				return null;
			}
			
			int value = source;
			return IntegerBaseEnum.valEnum(type, value);
		}
	}
}
