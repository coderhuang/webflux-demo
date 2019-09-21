package com.example.demo.type.zt;

import com.example.demo.type.IntegerBaseEnum;

public enum BookCategory implements IntegerBaseEnum {
	
	TECH(1, "技术类");
	
	private int value;

	private String label;
	
	private BookCategory(int value, String label) {
		
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
