package com.bsit.web.Initializer;

import com.bsit.web.dbutils.DbConnectionmanager;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
@WebListener

public class DbIntilizer implements ServletContextListener{
	
	
	 private DbConnectionmanager dbManager;

	    @Override
	    public void contextInitialized(ServletContextEvent sce) {
	        ServletContext ctx = sce.getServletContext();
	        dbManager = new DbConnectionmanager("db.properties");

	        // store it in context so Servlets can access it
	        ctx.setAttribute("DBManager", dbManager);
	        System.out.println("✅ PostgreSQL JDBC initialized successfully.");
	    }

	    @Override
	    public void contextDestroyed(ServletContextEvent sce) {
	        System.out.println("🛑 Application stopped. Cleaning up resources...");
	    }

}
