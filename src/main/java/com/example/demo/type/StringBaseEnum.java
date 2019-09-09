package com.example.demo.type;

import java.io.Serializable;

public interface StringBaseEnum extends Serializable {

	/**
	 * 获取值
	 * @return
	 */
	String getCode();

	/**
	 * 获取标签信息
	 * @return
	 */
	String getLabel();

	public static <T extends StringBaseEnum> T valEnum(Class<T> type, String code) {

		if (code == null) {
			
			throw new IllegalArgumentException("StringBaseEnum value should not be null");
		}
		if (type.isAssignableFrom(Enum.class)) {
			
			throw new IllegalArgumentException("illegal StringBaseEnum type");
		}

		T[] enums = type.getEnumConstants();
		for (T t : enums) {

			if (t.getCode().equals(code)) {
				return t;
			}
		}

		throw new IllegalArgumentException("cannot parse String: " + code + " to " + type.getName());
	}
}
