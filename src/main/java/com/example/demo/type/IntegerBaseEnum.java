package com.example.demo.type;

import java.io.Serializable;

public interface IntegerBaseEnum extends Serializable {

	/**
	 * 获取值
	 * @return
	 */
	int getValue();

	/**
	 * 获取标签信息
	 * @return
	 */
	String getLabel();
	
	public static <T extends IntegerBaseEnum> T valEnum(Class<T> type, Integer value) {

		if (value == null) {
			throw new IllegalArgumentException("IntegerBaseEnum value should not be null");
		}
		if (type.isAssignableFrom(Enum.class)) {
			throw new IllegalArgumentException("illegal IntegerBaseEnum type");
		}

		T[] enums = type.getEnumConstants();
		for (T t : enums) {

			if (t.getValue() == value.intValue()) {
				return t;
			}
		}
		throw new IllegalArgumentException("cannot parse integer: " + value + " to " + type.getName());
	}

    /**
      String DEFAULT_VALUE_NAME = "value";
     
    
    String DEFAULT_LABEL_NAME = "label";
 
    default Integer getValue() {
        Field field = ReflectionUtils.findField(this.getClass(), DEFAULT_VALUE_NAME);
        if (field == null)
            return null;
        try {
            field.setAccessible(true);
            return Integer.parseInt(field.get(this).toString());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
 
    default String getLabel() {
        Field field = ReflectionUtils.findField(this.getClass(), DEFAULT_LABEL_NAME);
        if (field == null)
            return null;
        try {
            field.setAccessible(true);
            return field.get(this).toString();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    */
}
