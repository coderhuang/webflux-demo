package com.example.demo.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.demo.processor.TimeProcessor;

/**
 * 
 * @author huangzhongjun
 *
 */
@Configuration
public class TimeRouterHandler {

	@Resource
	private TimeProcessor tp;

	/**
	 * @return
	 */
	@Bean
	public RouterFunction<ServerResponse> timeRouter() {

		// 另一种写法
//		RouterFunctions.route()
//				.path("/prefix",
//						builder -> builder.GET("/dasdsd", tp::timeInterval)
//								.nest(RequestPredicates.accept(MediaType.APPLICATION_JSON),
//										b -> b.POST("/asdfs", tp::taskDecode))
//								.POST("/asdfs", tp::taskDecode))
//				.GET("/timeinterval", tp::timeInterval).build();

		return RouterFunctions.route(GET("/timeinterval"), tp::timeInterval)
				.andRoute(POST("/task-decode"), tp::taskDecode).andRoute(POST("/formdata"), tp::formData)
				.filter((req, next) -> next.handle(req).onErrorResume(Exception.class,
						e -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).syncBody("系统压力山大")));
	}
}
