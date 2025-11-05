package com.bsit.web.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/logout")
public class LogoutHandling extends HttpServlet{
	private static final Logger logger=LoggerFactory.getLogger(LogoutHandling.class);
	
	private TemplateEngine templateEngine;
    private JakartaServletWebApplication application;
    

    

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
    	
    	logger.info("LogoutHandling doget()");

        request.getSession().invalidate();
        response.sendRedirect("/Payroll_Statement_Inquiry/login");
      logger.info("user logged out");
    }


}
