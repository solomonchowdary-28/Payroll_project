package com.bsit.web.controller;

import java.io.IOException;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/otpverify")
public class OtpVerifier extends HttpServlet {
	private TemplateEngine templateEngine;
    private JakartaServletWebApplication application;
	
	  protected void doPost(HttpServletRequest request,
              HttpServletResponse response)
 throws ServletException, IOException {
		  
		String otp=  (Integer) request.getSession().getAttribute("otp")+"";
		  String enteredOtp=request.getParameter("otp");
		  System.out.println(otp+" session otp");
		  System.out.println(enteredOtp+" user otp");
		  
		if(otp.equals(enteredOtp)) {
			
	        
			  response.sendRedirect(request.getContextPath() + "/payroll/currentmonth");
			 
	        
		}
		else
		{
			templateEngine = (TemplateEngine) getServletContext().getAttribute("templateEngine");
	    	 
	    	 application=(JakartaServletWebApplication)getServletContext().getAttribute("application");
	        // Build the "exchange" object required by the new WebContext constructor
	        var exchange = application.buildExchange(request, response);

	        // ✅ New way to create the WebContext in Thymeleaf 3.1
	        WebContext context = new WebContext(exchange);
	        context.setVariable("error", "invalid otp");
	        response.setContentType("text/html;charset=UTF-8");
	        templateEngine.process("otp", context, response.getWriter());
		}
		}

	  }
	
	


