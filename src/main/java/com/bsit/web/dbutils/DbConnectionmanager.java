package com.bsit.web.dbutils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class DbConnectionmanager {
	private static final Logger logger=LoggerFactory.getLogger(DbConnectionmanager.class);
	 private String url;
	    private String username;
	    private String password;

	    public DbConnectionmanager(String propertyFile) {
	    	logger.info("DbConnectionmanager.DbConnectionmanager()");
	        try (InputStream input = getClass().getClassLoader().getResourceAsStream(propertyFile)) {
	            Properties props = new Properties();
	            props.load(input);
	            url = props.getProperty("jdbc.url");
	            username = props.getProperty("jdbc.username");
	            password = props.getProperty("jdbc.password");

	            // Load the PostgreSQL driver
	            Class.forName("org.postgresql.Driver");

	        } catch (IOException | ClassNotFoundException e) {
	        	logger.error("failed to load db properties....");
	            throw new RuntimeException("Failed to load DB properties", e);
	        }
	    }

	    public Connection getConnection() throws SQLException {
            logger.info("DbConnectionmanager.getConnection()");
	        return DriverManager.getConnection(url, username, password);
	    }

}
