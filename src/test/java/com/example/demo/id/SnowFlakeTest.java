package com.example.demo.id;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Test;

import com.example.demo.utils.id.SnowFlakeGenerator;

public class SnowFlakeTest {

	@Test
	public void generateIds() {
		
		ArrayList<Long> idList = new ArrayList<>();
		for (int i = 0; i < 100_0000; i++) {
			
			long id = SnowFlakeGenerator.nextId();
			System.err.println(id);
			idList.add(id);
		}
		
		HashSet<Long> idSet = new HashSet<>();
		idSet.addAll(idList);
		System.err.println("不重复的元素个数：" + idSet.size());
	}
}
