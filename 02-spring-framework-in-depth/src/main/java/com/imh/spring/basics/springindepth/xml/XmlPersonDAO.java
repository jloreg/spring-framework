package com.imh.spring.basics.springindepth.xml;

public class XmlPersonDAO {						//Data Access Object, that's your Data layer.

	XmlJdbcConnection jdbcConnection;

	public XmlJdbcConnection getXmlJdbcConnection() {
		return jdbcConnection;
	}

	public void setXmlJdbcConnection(XmlJdbcConnection jdbcConnection) {
		this.jdbcConnection = jdbcConnection;
	}
}