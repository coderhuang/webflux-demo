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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.BookOrder;
import com.example.demo.query.QBookOrder;
import com.example.demo.type.enums.OrderStatus;
import com.example.demo.utils.TestUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.dml.DefaultMapper;
import com.querydsl.sql.dml.SQLInsertClause;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SimpleTest {

	@Autowired
	private SQLQueryFactory sqlQueryFactory;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private ObjectMapper objectmapper;

	QBookOrder qBookOrder = QBookOrder.BOOK_ORDER;

	@Before
	public void testDataSource() throws JsonProcessingException {

		DataSource dataSource = applicationContext.getBean(DataSource.class);
		System.out.println(dataSource);

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Integer result = jdbcTemplate.queryForObject("SELECT 1 FROM DUAL", Integer.class);
		System.out.println("===>>>>>>>>>>>" + objectmapper.writeValueAsString(result));
	}

	@Test
	@Transactional
	@Rollback(false)
	public void querySimpleTest() {

		var orderList = sqlQueryFactory.selectFrom(qBookOrder)
				.where(
						Expressions.booleanTemplate("{0} & {1} = {2}", qBookOrder.no.getMetadata().getName(), 1, 1)
						.and(qBookOrder.status.eq(OrderStatus.INIT)))
				.where(qBookOrder.id.gt(1L))
//				.query()
//				.select(qBookOrder.all())
//				.from(qBookOrder)
				.fetch();
		TestUtils.printProperty(BookOrder.class, orderList);
	}

	@Test
	@Transactional
	@Rollback(false)
	public void batchTest() {

		SQLInsertClause insert = sqlQueryFactory.insert(qBookOrder);

		insert.set(qBookOrder.name, "你猜一猜").set(qBookOrder.price, new BigDecimal("123456789.123456"))
				.set(qBookOrder.publishTime, LocalDateTime.now()).set(qBookOrder.no, 123)
				.set(qBookOrder.status, OrderStatus.SAILING).addBatch();

		insert.columns(qBookOrder.name, qBookOrder.price, qBookOrder.no, qBookOrder.publishTime)
				.values("阿拉同你港", new BigDecimal("123.123"), 123, LocalDateTime.now()).addBatch();

		var bookOrder = newBookOrder();
		bookOrder.setName("你黑边个");
		bookOrder.setStatus(OrderStatus.PROMOTION);
		insert.populate(bookOrder).addBatch();

		insert.getSQL().forEach(binding -> System.err.println(binding.getSQL()));
		long ac = insert.execute();
		System.err.println(ac);
	}

	@Test
	@Transactional
	@Rollback(false)
	public void deleteTest() {

		long ac = sqlQueryFactory.delete(qBookOrder).where(qBookOrder.name.eq("测试-1sdf")).execute();
		System.err.println(ac);
	}

	@Test
	@Transactional
	@Rollback(false)
	public void updateTest() {

		long ac = sqlQueryFactory.update(qBookOrder).where(qBookOrder.price.eq(new BigDecimal("1.001")))
				.set(qBookOrder.publishTime, LocalDateTime.now()).set(qBookOrder.price, new BigDecimal("1.110"))
				.execute();
		System.err.println(ac);

		var bookOrder = newBookOrder();
		bookOrder.setPrice(new BigDecimal("1.020"));
		ac = sqlQueryFactory.update(qBookOrder).where(qBookOrder.price.eq(new BigDecimal("1.000"))).populate(bookOrder)
				.execute();
		System.err.println(ac);
	}

	@Test
	@Transactional
	@Rollback(false)
	public void insertTest() {

		long ac = sqlQueryFactory.insert(qBookOrder).set(qBookOrder.no, 1).set(qBookOrder.name, "test name")
				.set(qBookOrder.publishTime, LocalDateTime.now()).set(qBookOrder.status, OrderStatus.INIT).execute();
		System.err.println(ac);

		ac = sqlQueryFactory.insert(qBookOrder)
				.columns(qBookOrder.no, qBookOrder.name, qBookOrder.publishTime, qBookOrder.price, qBookOrder.status)
				.values(1, "test name1", LocalDateTime.now(), new BigDecimal("123456456453.123456"), OrderStatus.INIT)
				.execute();
		System.err.println(ac);

		var bookOrder = newBookOrder();
		var effectCount = sqlQueryFactory.insert(qBookOrder).populate(bookOrder, DefaultMapper.WITH_NULL_BINDINGS)
				.execute();
		System.err.println(effectCount);
	}

	private BookOrder newBookOrder() {

		var bookOrder = new BookOrder();
		bookOrder.setNo(1);
		bookOrder.setName("测试-1");
		bookOrder.setAuthors("david fetcher, michale link");
		bookOrder.setPrice(new BigDecimal("1.00071"));
		bookOrder.setStatus(OrderStatus.INIT);
		bookOrder.setPublishTime(LocalDateTime.now());

		return bookOrder;
	}
}
