package com.bsit.web.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@WebServlet("/loginsubmit")
public class LoginHandling extends HttpServlet{
	
	private static final Logger logger=LoggerFactory.getLogger(LoginHandling.class);
	private TemplateEngine templateEngine;
    private JakartaServletWebApplication application;
    private Loginmanagementservice loginService;

    

    @Override
    protected void doPost(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
    	logger.info("LoginHandling.doPost()");
    	
    	
    	String email = request.getParameter("email");
        String password = request.getParameter("password");

        loginService = new LoginmanagementserviceImpl();
        Employee emp = loginService.login(email, password);

        templateEngine = (TemplateEngine) getServletContext().getAttribute("templateEngine");
        application = (JakartaServletWebApplication) getServletContext().getAttribute("application");
        var exchange = application.buildExchange(request, response);
        WebContext context = new WebContext(exchange);

        // --- Null-safe login handling ---
        if (emp == null) {
            context.setVariable("error", "Invalid username or password");
            response.setContentType("text/html;charset=UTF-8");
            templateEngine.process("login", context, response.getWriter());
            return; // explicit return — makes control flow clear
        }

        // emp is non-null now
        request.getSession().setAttribute("emp", emp);
        Integer id = emp.getId();
        request.getSession().setAttribute("id", id);
        request.getSession().setAttribute("email", email);

        // Generate OTP (don't use hard-coded in production)
        request.getSession().setAttribute("otp", 123456);

        response.setContentType("text/html;charset=UTF-8");
        templateEngine.process("otp", context, response.getWriter());
        logger.info("LoginHandling.doPost() completed");
        return;
       
      
    }


}
