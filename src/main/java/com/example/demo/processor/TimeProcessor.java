package com.example.demo.processor;

import static com.example.demo.utils.LocalDateTimeUtils.DATE_TIME_FORMATTER;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.StringJoiner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.demo.model.Task;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class TimeProcessor {

	private Logger logger = LoggerFactory.getLogger(getClass());

	public Mono<ServerResponse> timeInterval(ServerRequest req) {

		return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(
				Flux.interval(Duration.ofSeconds(2)).map(l -> LocalDateTime.now().format(DATE_TIME_FORMATTER)),
				String.class);
	}

	public Mono<ServerResponse> taskDecode(ServerRequest req) {

//		var flux = req.bodyToFlux(Task.class).doOnNext(t -> System.err.println(t.getStatus().getCode()))
//				.doOnNext(t -> t.setCreateTime(LocalDateTime.now()));
//		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(flux, Task.class);
		
		var mono = req.bodyToMono(Task.class).doOnNext(t -> System.err.println(t.getStatus().getCode()))
				.doOnNext(t -> t.setCreateTime(LocalDateTime.now()));
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(mono, Task.class);
	}

	public Mono<ServerResponse> formData(ServerRequest req) {

		StringJoiner strJ = new StringJoiner(";");
		var mono = req.body(BodyExtractors.toFormData()).map(map -> {

			var entrySet = map.entrySet();
			for (var entry : entrySet) {

				var keyVal = entry.getKey() + "=>" + entry.getValue();
				logger.info(keyVal);
				strJ.add(keyVal);
			}
			return strJ.toString();
		});
		
		return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).body(mono, String.class);
	}
	
}
