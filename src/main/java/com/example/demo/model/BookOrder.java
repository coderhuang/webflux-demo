package com.example.demo.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.annotation.Generated;

@Generated("com.querydsl.codegen.BeanSerializer")
public class BookOrder {

	private Long id;

	private Integer no;

	private String name;

	private BigDecimal price;

	private String authors;

	private LocalDateTime publishTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public LocalDateTime getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(LocalDateTime publishTime) {
		this.publishTime = publishTime;
	}
}
