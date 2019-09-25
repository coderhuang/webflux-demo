package com.example.demo.type.enums;

import com.example.demo.type.IntegerBaseEnum;

public enum OrderStatus implements IntegerBaseEnum {
	
	INIT(1, "初始"),
	
	SAILING(2, "销售中"),
	
	PROMOTION(11, "促销");

	private int value;

	private String label;
	
	private OrderStatus(int value, String label) {
		
		this.value = value;
		this.label = label;
	}

	@Override
	public int getValue() {
		
		return this.value;
	}

	@Override
	public String getLabel() {
		
		return this.label;
	}
}
