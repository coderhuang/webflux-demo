package com.example.demo.flux;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;
import reactor.core.scheduler.Schedulers;

public class ProcessorTest {

	@Test
	public void testGenerate() {

		var incrNum = new AtomicInteger();

		var f = Flux.generate(s -> {

			if (incrNum.getAcquire() > 10) {

				s.complete();
//				return;
			}
			s.next(incrNum.incrementAndGet());
		}).publishOn(Schedulers.newParallel("aha", 5));
		f.subscribe(System.out::println);
		System.err.println(f.blockLast());
		f.subscribe(System.out::println);
	}

	@Test
	public void testDeplayElement() throws InterruptedException {

		var i = Flux.range(0, 10).delayElements(Duration.ofMillis(2L)).log().blockLast();
		System.err.println(i);
	}

	@Test
	public void unicastProcessorOnNextTest() throws InterruptedException {

		var unicastProcessor = UnicastProcessor.create();
		var incrNum = new AtomicInteger();
		var connectableFlux = unicastProcessor
//				.replay()
				.publish()
				.autoConnect(2)
				.delayElements(Duration.ofSeconds(1L));

		connectableFlux.subscribe(System.out::println);
		while (incrNum.incrementAndGet() < 11) {

			unicastProcessor.onNext(LocalDateTime.now().toString());
			TimeUnit.MICROSECONDS.sleep(5);
		}
		connectableFlux.subscribe(System.out::println);
		unicastProcessor.sink().complete();
		
		System.err.println(connectableFlux.blockLast());
	}

}
