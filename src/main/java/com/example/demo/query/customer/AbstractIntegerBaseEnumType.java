package com.example.demo.query.customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.example.demo.type.IntegerBaseEnum;
import com.querydsl.sql.types.AbstractType;

public abstract class AbstractIntegerBaseEnumType<T extends IntegerBaseEnum> extends AbstractType<T> {

	private Class<T> clazz;

	public AbstractIntegerBaseEnumType(Class<T> clazz) {
		
		super(Types.TINYINT);
		this.clazz = clazz;
	}

	@Override
    public String getLiteral(T value) {
		
        return String.valueOf(value.getValue());
    }

	@Override
	public Class<T> getReturnedClass() {

		return clazz;
	}

	@Override
	public T getValue(ResultSet rs, int startIndex) throws SQLException {

		int value = rs.getInt(startIndex);
		return IntegerBaseEnum.valEnum(clazz, value);
	}

	@Override
	public void setValue(PreparedStatement st, int startIndex, T value) throws SQLException {

		st.setInt(startIndex, value.getValue());
	}

}
