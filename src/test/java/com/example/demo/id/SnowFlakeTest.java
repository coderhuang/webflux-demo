package com.example.demo.id;

import org.junit.Test;

import com.example.demo.utils.id.SnowFlakeGenerator;

public class SnowFlakeTest {

	@Test
	public void generateIds() {
		
		for (int i = 0; i < 10; i++) {
			
			System.err.println(SnowFlakeGenerator.nextId());
		}
	}
}
