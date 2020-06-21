package com.example.demo.id;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import com.example.demo.utils.id.SnowFlakeGenerator;

import edu.emory.mathcs.backport.java.util.concurrent.CountDownLatch;
import edu.emory.mathcs.backport.java.util.concurrent.atomic.AtomicInteger;

public class SnowFlakeTest {

	@Test
	public void generateIds() throws InterruptedException {
		
		int threadCount = 1000;
		int perThread = 10000;
		Long[] idArray = new Long[threadCount * perThread];
//		AtomicInteger ai = new AtomicInteger(0);
		CountDownLatch latch = new CountDownLatch(threadCount);
		ExecutorService es = Executors.newFixedThreadPool(threadCount);
		
		LocalDateTime startTime = LocalDateTime.now();
		for (int i = 0; i < threadCount; i++) {
			
			final int tempI = i;
			es.execute(() -> {
				
				int threadNo = tempI;
				int idxBase = threadNo * perThread;
				for (int j = 0; j < perThread; j++) {
					
					long id = SnowFlakeGenerator.nextId();
//					idArray[ai.getAndIncrement()] = id;
					// 划分区域,避免争抢
					idArray[idxBase + j] = id;
				}
				latch.countDown();
			});
		}
		latch.await();
		LocalDateTime endTime = LocalDateTime.now();
		
		System.err.println("耗时：" + Duration.between(startTime, endTime).toMillis()+ "毫秒");
		HashSet<Long> idSet = new HashSet<>(threadCount * perThread * 2);
		idSet.addAll(Arrays.asList(idArray));
		System.err.println("生成id个数："+ idArray.length);
		System.err.println("不重复的元素个数：" + idSet.size());
	}
}
