package com.example.demo.config;

import static com.example.demo.utils.LocalDateTimeUtils.DATE_FORMATTER;
import static com.example.demo.utils.LocalDateTimeUtils.DATE_TIME_FORMATTER;
import static com.example.demo.utils.LocalDateTimeUtils.TIME_FORMATTER;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import com.example.demo.config.message.formatter.IntegerToIntegerBaseEnumFactory;
import com.example.demo.config.message.formatter.StringToIntegerBaseEnumFactory;
import com.example.demo.config.message.formatter.StringToLocalDate;
import com.example.demo.config.message.formatter.StringToLocalDateTime;
import com.example.demo.config.message.formatter.StringToLocalTime;
import com.example.demo.config.message.formatter.StringToStringBaseEnumFactory;
import com.example.demo.type.IntegerBaseEnum;
import com.example.demo.type.StringBaseEnum;
import com.example.demo.type.zt.TaskStatus;
import com.example.demo.utils.json.jackson.IntegerBaseEnumDeserializer;
import com.example.demo.utils.json.jackson.IntegerBaseEnumSerializer;
import com.example.demo.utils.json.jackson.StringBaseEnumDeserializer;
import com.example.demo.utils.json.jackson.StringBaseEnumSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

@Configuration
public class WebConfig implements WebFluxConfigurer {
	
	@Override
	public void addFormatters(FormatterRegistry registry) {
		
		registry.addConverter(new StringToLocalDateTime());
		registry.addConverter(new StringToLocalDate());
		registry.addConverter(new StringToLocalTime());
		
		registry.addConverterFactory(new IntegerToIntegerBaseEnumFactory());
		registry.addConverterFactory(new StringToIntegerBaseEnumFactory());
		registry.addConverterFactory(new StringToStringBaseEnumFactory());
		
//		registry.addFormatter(new LocalDatetimeFormatter());
		
	}
	
	@Override
	public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
		
		var objectMapper = initObjectMapper();
		configurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper));
		configurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper));
	}
	
	private ObjectMapper initObjectMapper() {
		
//		var objectMapper = new ObjectMapper();
		var objectMapper = Jackson2ObjectMapperBuilder.json().build();
		// 忽略空属性
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		// objectMapper.enable(SerializationConfig.Feature.INDENT_OUTPUT);//格式化
		objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
		
		JavaTimeModule timeModule = new JavaTimeModule();
		timeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DATE_TIME_FORMATTER));
		timeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DATE_FORMATTER));
		timeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(TIME_FORMATTER));
		
		timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DATE_TIME_FORMATTER));
		timeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DATE_FORMATTER));
		timeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(TIME_FORMATTER));
		objectMapper.registerModule(timeModule);
		
		addStringBaseEnumSerializers(TaskStatus.class);
		addIntegerBaseEnumSerializers();
		
		objectMapper.registerModule(simpleModule);
		return objectMapper;
	}
	
	SimpleModule simpleModule = new SimpleModule();
	@SafeVarargs
	private <T extends StringBaseEnum> void addStringBaseEnumSerializers(Class<T>... clazzes) {
		
		for (Class<T> s : clazzes) {
			
			simpleModule.addSerializer(s, new StringBaseEnumSerializer<>());
			simpleModule.addDeserializer(s, new StringBaseEnumDeserializer<>());
		}
	}
	
	@SafeVarargs
	private <T extends IntegerBaseEnum> void addIntegerBaseEnumSerializers(Class<T>... clazzes) {
		
		for (Class<T> i : clazzes) {
			
			simpleModule.addSerializer(i, new IntegerBaseEnumSerializer<>());
			simpleModule.addDeserializer(i, new IntegerBaseEnumDeserializer<>());
		}
	}
	
}
