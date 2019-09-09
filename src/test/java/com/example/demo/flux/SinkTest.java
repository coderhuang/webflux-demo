package com.example.demo.flux;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * 
 */
public class SinkTest {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	final AtomicInteger ai = new AtomicInteger(0);

	@Test
	public void createTest() throws InterruptedException {

		Flux.create(sink -> {

			EventSource.registerListener(new TobyEventListener() {

				private String listenerName = "listernter-" + String.valueOf(ai.incrementAndGet());

				@Override
				public void onEvent(TobyEvent event) {

					if (event.getEventNo() > 500) {
						
						sink.complete();
					}
					
					logger.info("事件发生    =>    [{}]", event.getEventNo());
					sink.next(event);
					logger.info("\n");
					logger.info("----------------" + listenerName);
//					logger.info(JSON.toJSONString(event));
					logger.info("\n");
				}

				@Override
				public void onError(Throwable e) {

					logger.error(e.getMessage());
				}

				@Override
				public void onComplete() {

					logger.info(listenerName + " => complete");
				}
			});
		})
		.publishOn(Schedulers.single(), 1)
		.subscribe(event -> {
			
//			logger.info(JSON.toJSONString(event));
			logger.info("事件消费    =>    eventNo => {}", ((TobyEvent)event).getEventNo());
			try {
				TimeUnit.MILLISECONDS.sleep(10);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		});
		
		
		Flux.interval(Duration.ofMillis(5L))
		.map(l -> {
			TobyEvent te = new TobyEvent();
			EventSource.inputEvent(te);
			
			return te;
		})
		.blockLast();
		TimeUnit.SECONDS.sleep(2L);
	}
	
	@Test
	public void generateTest() {
		
		final AtomicInteger ai = new AtomicInteger(0);
		Flux.generate(sink -> {
			
			sink.next(ai.incrementAndGet());
			if (ai.get() > 1_00) {
				sink.complete();
			}
		})
		.subscribe(i -> logger.info("num:{}", i));
	}

	@Test
	public void generateTest1() throws InterruptedException {

		EventSource.registerListener(new TobyEventListener() {

			private String listenerName = "listernter-" + String.valueOf(ai.incrementAndGet());

			@Override
			public void onEvent(TobyEvent event) {

				logger.info("----------------" + listenerName);
				logger.info(JSON.toJSONString(event));
				logger.info("\n");
			}

			@Override
			public void onError(Throwable e) {

				logger.error(e.getMessage());
			}

			@Override
			public void onComplete() {

			}
		});

		Flux.interval(Duration.ofSeconds(3)).map(l -> new TobyEvent())
				.doOnNext(EventSource::inputEvent).subscribe();
		TimeUnit.SECONDS.sleep(100L);
	}
}
