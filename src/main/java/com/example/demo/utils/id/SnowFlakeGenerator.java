package com.example.demo.utils.id;

/**
 * 基于雪花算法实现id生成 long =》 64位
 * 1位标志位 + 41位时间戳 + 10位机器标识 + 12位数字标识 每毫秒可生成4096个Id
 * 保证唯一性，趋势递增
 * @author toby
 *
 */
public final class SnowFlakeGenerator {
	
	private SnowFlakeGenerator() {}

	// 符号标志位
	private static final long FLAG_BIT = 0L;
	
	// 末尾数字所占位数
	private static final int NUM_BITS = 12;
	
	// 末尾数字的掩码
	private static final Long NUM_MASK = (1L << NUM_BITS) - 1;
	
	// 机器标识所占位数
	private static final int MACHINE_BITS = 10;
	
	// 时间戳偏移位数
	private static final int STAMP_LEFT_BITS = NUM_BITS + MACHINE_BITS;
	
	// 起始时间戳值
	private static final long START_STAMP = 1570542039201L;
	
	// 上一次请求的时间戳
	private static long lastStamp = -1L;
	
	// 只为测试代码，所以写一个固定值
	private static long machineId = 100 << NUM_BITS;
	
	private static long sequenceNo = -1L;
	
	public static synchronized long nextId() {
		
		long currentStamp = currentStamp();
		if (currentStamp > lastStamp) {
			// 更新时间
			lastStamp = currentStamp;
			sequenceNo = 0L;
		} else if (currentStamp < lastStamp) {
			
			if (currentStamp - lastStamp > 1000L) {
				
				throw new IllegalStateException("System time go back too much");
			}
			
			while (currentStamp < lastStamp) {
				// 等待时间戳超过缓存的时间戳
				currentStamp = nextMillis();
			}
			lastStamp = currentStamp;
		}
		
		sequenceNo = (++sequenceNo) & NUM_MASK;
		if (sequenceNo == 0L) {
			// 溢出数字位的最大值
			lastStamp = nextMillis();
			sequenceNo = (++sequenceNo) & NUM_MASK;
		}
		
		long stamp = lastStamp - START_STAMP;
		return FLAG_BIT | (stamp << STAMP_LEFT_BITS) | machineId | sequenceNo;
	}
	
	private static long currentStamp() {
		
		return System.currentTimeMillis();
	}
	
	private static long nextMillis() {
		
		long currentStamp = currentStamp();
		while (currentStamp <= lastStamp) {
			currentStamp = currentStamp();
		}
		
		return currentStamp;
	}
	
}
