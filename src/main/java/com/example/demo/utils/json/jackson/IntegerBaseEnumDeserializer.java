package com.example.demo.utils.json.jackson;

import java.io.IOException;

import org.springframework.beans.BeanUtils;

import com.example.demo.type.IntegerBaseEnum;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * 
 * @param <T>
 */
public class IntegerBaseEnumDeserializer<T extends IntegerBaseEnum> extends JsonDeserializer<T> {

	@Override
	public T deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
		
        String currentName = jp.getCurrentName();
        Object currentValue = jp.getCurrentValue();
        @SuppressWarnings("unchecked")
		Class<T> findPropertyType = (Class<T>) BeanUtils.findPropertyType(currentName, currentValue.getClass());
        
        return IntegerBaseEnum.valEnum(findPropertyType, jp.getIntValue());
	}

}
