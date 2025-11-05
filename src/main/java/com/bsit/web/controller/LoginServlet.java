package com.bsit.web.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/login")
public class LoginServlet extends HttpServlet{
	
	private  static final Logger logger=LoggerFactory.getLogger(LoginServlet.class);
	private TemplateEngine templateEngine;
    private JakartaServletWebApplication application;

    

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
	    		logger.info("LoginServlet.doGet()");
	    		templateEngine = (TemplateEngine) getServletContext().getAttribute("templateEngine");
	    	 
	    		application=(JakartaServletWebApplication)getServletContext().getAttribute("application");
	    		// Build the "exchange" object required by the new WebContext constructor
	    		var exchange = application.buildExchange(request, response);
	
	    		// ✅ New way to create the WebContext in Thymeleaf 3.1
	    		WebContext context = new WebContext(exchange);
	    		response.setContentType("text/html;charset=UTF-8");
	    		templateEngine.process("login", context, response.getWriter());
	    		logger.info("LoginServlet.doget() is completed....");
    }

}
