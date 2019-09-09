package com.example.demo.utils.json.jackson;

import java.io.IOException;

import org.springframework.beans.BeanUtils;

import com.example.demo.type.StringBaseEnum;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * 
 * @author huangzhongjun
 *
 * @param <T>
 */
public class StringBaseEnumDeserializer<T extends StringBaseEnum> extends JsonDeserializer<T> {

	@Override
	public T deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
		
		String currentName = jp.getCurrentName();
		Object currentValue = jp.getCurrentValue();
		@SuppressWarnings("unchecked")
		Class<T> findPropertyType = (Class<T>) BeanUtils.findPropertyType(currentName, currentValue.getClass());
        
        return StringBaseEnum.valEnum(findPropertyType, jp.getText());
	}

}
