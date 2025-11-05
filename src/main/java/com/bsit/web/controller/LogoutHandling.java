package com.bsit.web.controller;

import java.io.IOException;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import com.bsit.web.loginmanagementservice.Loginmanagementservice;
import com.bsit.web.loginmanagementservice.LoginmanagementserviceImpl;
import com.bsit.web.model.Employee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/logout")
public class LogoutHandling extends HttpServlet{
	
	
	private TemplateEngine templateEngine;
    private JakartaServletWebApplication application;
    

    

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
    	
    	

        request.getSession().invalidate();
        response.sendRedirect("/Payroll_Statement_Inquiry/login");
      
    }


}
