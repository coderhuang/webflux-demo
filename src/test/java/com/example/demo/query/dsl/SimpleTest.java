package com.example.demo.query.dsl;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.BookOrder;
import com.example.demo.query.QBookOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.dml.DefaultMapper;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class SimpleTest {

	@Autowired
	private SQLQueryFactory sqlQueryFactory;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private ObjectMapper objectmapper;

	@Before
	public void testDataSource() throws JsonProcessingException {

		DataSource dataSource = applicationContext.getBean(DataSource.class);
		System.out.println(dataSource);

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Integer result = jdbcTemplate.queryForObject("SELECT 1 FROM DUAL", Integer.class);
		System.out.println("===>>>>>>>>>>>" + objectmapper.writeValueAsString(result));
	}

	@Test
	public void insertTest() {

		var bookOrder = newBookOrder();
		var effectCount = sqlQueryFactory.insert(QBookOrder.BOOK_ORDER)
				.populate(bookOrder, DefaultMapper.WITH_NULL_BINDINGS)
				.execute();
		System.err.println(effectCount);
	}

	private BookOrder newBookOrder() {

		var bookOrder = new BookOrder();
		bookOrder.setNo(1);
		bookOrder.setName("测试-1");
		bookOrder.setAuthors("david fetcher, michale link");
		bookOrder.setPrice(new BigDecimal("1.00001"));
		bookOrder.setPublishTime(LocalDateTime.now());

		return bookOrder;
	}
}
