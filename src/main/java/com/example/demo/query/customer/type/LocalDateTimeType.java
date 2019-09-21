package com.example.demo.query.customer.type;

import static com.example.demo.utils.CalendarUtils.ZONE_GMT_8_STRING;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.ZoneId;

import com.example.demo.utils.LocalDateTimeUtils;
import com.querydsl.sql.types.AbstractDateTimeType;

public class LocalDateTimeType extends AbstractDateTimeType<LocalDateTime> {
	
	public LocalDateTimeType() {

		super(Types.TIMESTAMP);
	}

	public LocalDateTimeType(int type) {

		super(type);
	}
	
	@Override
    public String getLiteral(LocalDateTime value) {
		
        return LocalDateTimeUtils.DATE_TIME_FORMATTER.format(value);
    }

	@Override
	public Class<LocalDateTime> getReturnedClass() {

		return LocalDateTime.class;
	}

	@Override
	public LocalDateTime getValue(ResultSet rs, int startIndex) throws SQLException {

		Timestamp ts = rs.getTimestamp(startIndex);
		return ts != null ? LocalDateTime.ofInstant(ts.toInstant(), ZoneId.of(ZONE_GMT_8_STRING)) : null;
	}

	@Override
	public void setValue(PreparedStatement st, int startIndex, LocalDateTime value) throws SQLException {

		st.setTimestamp(startIndex, Timestamp.valueOf(value));
	}

}
