package com.example.demo.utils.json.jackson;

import java.io.IOException;

import com.example.demo.type.IntegerBaseEnum;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 
 * @author huangzhongjun
 *
 * @param <T>
 */
public class IntegerBaseEnumSerializer<T extends IntegerBaseEnum> extends JsonSerializer<T> {

	@Override
	public void serialize(T value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		
		gen.writeNumber(value.getValue());
	}

}
