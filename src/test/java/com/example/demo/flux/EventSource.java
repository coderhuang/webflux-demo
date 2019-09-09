package com.example.demo.flux;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventSource {

	private static final Logger LOGGER = LoggerFactory.getLogger(EventSource.class);
	
	private static List<TobyEventListener> listeners;
	static {

		listeners = new ArrayList<>();
	}

	public static void registerListener(TobyEventListener listener) {

		listeners.add(listener);
	}

	private static void triggerListener(TobyEvent event) {

		for (TobyEventListener tobyEventListener : listeners) {

			tobyEventListener.onEvent(event);
		}
	}

	public static void inputEvent(TobyEvent event) {

//		LOGGER.info("事件发生[{}]", JSON.toJSONString(event));
		triggerListener(event);
	}
}
