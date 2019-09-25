package com.example.demo.query.customer.type;

import com.example.demo.query.customer.AbstractIntegerBaseEnumType;
import com.example.demo.type.enums.OrderStatus;

public class OrderStatusType extends AbstractIntegerBaseEnumType<OrderStatus> {

	public OrderStatusType() {
		
		super(OrderStatus.class);
	}

}
