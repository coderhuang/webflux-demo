package com.example.demo.id;

import org.junit.Test;

import com.example.demo.utils.id.SnowFlakeGenerator;

public class SnowFlakeTest {

	@Test
	public void generateIds() {
		
		for (int i = 0; i < 1000; i++) {
			
			long id = SnowFlakeGenerator.nextId();
			System.err.println(id);
		}
	}
}
