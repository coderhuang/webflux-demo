package com.example.demo.config;

import java.sql.Connection;

import javax.inject.Provider;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.example.demo.query.customer.type.BookCategoryType;
import com.example.demo.query.customer.type.LocalDateTimeType;
import com.querydsl.sql.MySQLTemplates;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.SQLTemplates;
import com.querydsl.sql.spring.SpringConnectionProvider;
import com.querydsl.sql.spring.SpringExceptionTranslator;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
public class QueryDslConfig {

	@Autowired
	private DataSource dataSource;

	@Bean
	public PlatformTransactionManager transactionManager() {

		return new DataSourceTransactionManager(dataSource);
	}

	@Bean
	public com.querydsl.sql.Configuration querydslConfiguration() {

		SQLTemplates templates = MySQLTemplates.builder().printSchema()
//        		.quote()
		// to replace new lines with single space in the output
        		.newLineToSingleSpace()
		// to set the escape char
//        	    .escape('`')
				.build();
		com.querydsl.sql.Configuration configuration = new com.querydsl.sql.Configuration(templates);
		configuration.setExceptionTranslator(new SpringExceptionTranslator());
		// 针对java.time.LocalDateTime的解析
		configuration.register(new LocalDateTimeType());
		configuration.register(new BookCategoryType());

		return configuration;
	}

	@Bean
	public SQLQueryFactory queryFactory() {

		Provider<Connection> provider = new SpringConnectionProvider(dataSource);
		return new SQLQueryFactory(querydslConfiguration(), provider);
	}
}
