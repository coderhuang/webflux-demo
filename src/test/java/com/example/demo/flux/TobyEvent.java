package com.example.demo.flux;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

import com.alibaba.fastjson.annotation.JSONField;

import net.bytebuddy.utility.RandomString;

public class TobyEvent {
	
	private static final AtomicInteger ai = new AtomicInteger();
	
	private int eventNo = ai.incrementAndGet();

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime eventTime = LocalDateTime.now();

	private String eventName = RandomString.make();

	public int getEventNo() {
		return eventNo;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public LocalDateTime getEventTime() {
		return eventTime;
	}

	public String getEventName() {
		return eventName;
	}
}
