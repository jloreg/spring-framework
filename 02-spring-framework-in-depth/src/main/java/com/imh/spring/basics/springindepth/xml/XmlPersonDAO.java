package com.imh.spring.basics.springindepth.xml;

public class XmlPersonDAO {						//Data Access Object, that's your Data layer.

	XmlJdbcConnection jdbcConnection;

	public XmlJdbcConnection getJdbcConnection() {
		return jdbcConnection;
	}

	public void setJdbcConnection(XmlJdbcConnection jdbcConnection) {
		this.jdbcConnection = jdbcConnection;
	}
}