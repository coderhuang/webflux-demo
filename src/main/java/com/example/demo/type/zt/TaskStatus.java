package com.example.demo.type.zt;

import com.example.demo.type.StringBaseEnum;

public enum TaskStatus implements StringBaseEnum {

	/**
	 * 未开始
	 */
	WAIT("wait", "未开始"),
	/**
	 * 进行中
	 */
	DOING("doing", "进行中"),
	/**
	 * 已挂起
	 */
	SUSPENDED("suspend", "已挂起"),
	/**
	 * 已关闭
	 */
	CLOSED("closed", "已关闭");
	
	private String code;

	private String label;

	private TaskStatus(String code, String label) {

		this.code = code;
		this.label = label;
	}

	@Override
	public String getCode() {

		return code;
	}

	@Override
	public String getLabel() {

		return label;
	}
}
