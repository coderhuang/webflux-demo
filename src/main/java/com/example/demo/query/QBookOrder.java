package com.example.demo.query;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import java.math.BigDecimal;
import java.sql.Types;
import java.time.LocalDateTime;

import javax.annotation.Generated;

import com.example.demo.model.BookOrder;
import com.example.demo.type.enums.OrderStatus;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.SimplePath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.ColumnMetadata;
import com.querydsl.sql.RelationalPathBase;

@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QBookOrder extends RelationalPathBase<BookOrder> {

	private static final long serialVersionUID = -5144198673954457252L;

	public static final QBookOrder BOOK_ORDER = new QBookOrder("book_order");

	public final NumberPath<Long> id = createNumber("id", Long.class);

	public final com.querydsl.sql.PrimaryKey<BookOrder> primary = createPrimaryKey(id);

	public final NumberPath<Integer> no = createNumber("no", Integer.class);

	public final StringPath name = createString("name");
	
	public final NumberPath<BigDecimal> price = createNumber("price", BigDecimal.class);
	
	public final SimplePath<OrderStatus> status = createSimple("status", OrderStatus.class);

	public final DateTimePath<LocalDateTime> publishTime = createDateTime("publishTime", LocalDateTime.class);

//	public final ArrayPath<String, String> authors = createArray("authors", String.class);
	
	public void initMetaData() {

		addMetadata(id, ColumnMetadata.named("id").ofType(Types.BIGINT).withSize(20));
		addMetadata(name, ColumnMetadata.named("name").ofType(Types.VARCHAR).withSize(45));
		addMetadata(no, ColumnMetadata.named("no").ofType(Types.INTEGER).withSize(11));
		addMetadata(price, ColumnMetadata.named("price").ofType(Types.DECIMAL).withDigits(0));
		addMetadata(status, ColumnMetadata.named("status").ofType(Types.TINYINT).withSize(4));
//		addMetadata(authors, ColumnMetadata.named("authors").ofType(Types.VARCHAR).withSize(128));
		addMetadata(publishTime, ColumnMetadata.named("publish_time").ofType(Types.TIMESTAMP).withSize(19));
	}

	public QBookOrder(String variable) {

		super(BookOrder.class, forVariable(variable), "toby_db", "book_order");
		initMetaData();
	}

	public QBookOrder(Class<? extends BookOrder> type, String variable, String schema, String table) {

		super(type, variable, schema, table);
		initMetaData();
	}

	public QBookOrder(String variable, String schema, String table) {

		super(BookOrder.class, forVariable(variable), schema, table);
		initMetaData();
	}

	public QBookOrder(String variable, String schema) {

		super(BookOrder.class, forVariable(variable), schema, "book_order");
		initMetaData();
	}

	public QBookOrder(Path<? extends BookOrder> path) {

		super(path.getType(), path.getMetadata(), "null", "book_order");
		initMetaData();
	}

	public QBookOrder(PathMetadata metadata) {

		super(BookOrder.class, metadata, "null", "book_order");
		initMetaData();
	}
}
