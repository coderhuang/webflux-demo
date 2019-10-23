package com.example.demo.config;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

	@Bean
	public RestTemplate restTemplate(ClientHttpRequestFactory clientHttpRequestFactory,
			@Autowired @Qualifier("devopsCenterMappingJackson2HttpMessageConverter") MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter) {

		RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);

		List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
		for (int i = 0; i < converterList.size(); i++) {

			HttpMessageConverter<?> converter = converterList.get(i);
			if (converter instanceof MappingJackson2HttpMessageConverter) {
				// 加上对java.time几个常用类的支持
				converterList.remove(i);
				converterList.add(i, mappingJackson2HttpMessageConverter);
			} else if (converter instanceof StringHttpMessageConverter) {
				// 加上中文编码的支持
				((StringHttpMessageConverter) converter).setDefaultCharset(StandardCharsets.UTF_8);
			}

		}

		return restTemplate;
	}

	@Bean
	public ClientHttpRequestFactory clientHttpRequestFactory() {
//        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
//        factory.setReadTimeout(30 * 1000);//单位为ms
//        factory.setConnectTimeout(30 * 1000);//单位为ms

		HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		httpRequestFactory.setConnectionRequestTimeout(30 * 1000);
		httpRequestFactory.setConnectTimeout(30 * 3000);
		httpRequestFactory.setReadTimeout(30 * 3000);

		return httpRequestFactory;
	}
}
