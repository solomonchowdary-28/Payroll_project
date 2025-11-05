package com.bsit.web.initializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bsit.web.dbutils.DbConnectionmanager;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
@WebListener

public class DbIntilizer implements ServletContextListener{
	private static final Logger logger=LoggerFactory.getLogger(DbIntilizer.class);
	
	 private DbConnectionmanager dbManager;

	    @Override
	    public void contextInitialized(ServletContextEvent sce) {
	        ServletContext ctx = sce.getServletContext();
	        dbManager = new DbConnectionmanager("db.properties");

	        // store it in context so Servlets can access it
	        ctx.setAttribute("DBManager", dbManager);
	        logger.info("dbmanager is setted to servletcontext");
	        logger.info("postgress is successfully intilized");
	    }

	    @Override
	    public void contextDestroyed(ServletContextEvent sce) {
	      
	        logger.info("Application stopped. Cleaning up resources...");
	    }

}
