package com.quantumdataengines.app.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConnectionUtil{
	
	private static final Logger log = LoggerFactory.getLogger(ConnectionUtil.class);
	@Autowired
	private DataSource dataSource;
	private Connection connection;
	public Connection getConnection(){
		try{
			StackTraceElement[] arrStackTraceElement = new Exception().getStackTrace();
			for(StackTraceElement stackTraceElement : arrStackTraceElement){
				if(stackTraceElement.getClassName().contains("dao"))
					log.debug("Database connection request from "+stackTraceElement.getMethodName()
						+" in "+stackTraceElement.getClassName());
			}
			connection = dataSource.getConnection();
		}catch(Exception e){
			log.error("Error while getting db connection from DataSource["+dataSource+"] : "+e.getMessage());
			e.printStackTrace();
		}
		return connection;
	}
	
	public void closeResources(Connection connection, Statement statement, ResultSet resultSet){
		try{
			if(connection != null)
				connection.close();
			if(statement != null)
				statement.close();
			if(resultSet != null)
				resultSet.close();
			
		}catch(Exception e){
			log.error("Error while closing db resource : "+e.getMessage());
			e.printStackTrace();
		}
	}
}