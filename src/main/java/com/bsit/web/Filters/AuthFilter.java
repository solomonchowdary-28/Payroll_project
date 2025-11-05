package com.bsit.web.filters;



import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// Intercepts all requests
@WebFilter("/*")
public class AuthFilter implements Filter {
private static final Logger logger =LoggerFactory.getLogger(AuthFilter.class);
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
    	
    	logger.info("AuthFilter strated");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        HttpSession session = req.getSession(false); // don't create new session

        // Whitelist: paths that should NOT require login
        boolean isLoginPage = uri.endsWith("/login") || uri.endsWith("/loginsubmit");
        boolean isOtpPage = uri.endsWith("/otp") || uri.endsWith("/otpverify");
       

        // Skip filter for allowed pages
        if (isLoginPage || isOtpPage ) {
            chain.doFilter(request, response);
            return;
        }

        // Check for valid session
        boolean loggedIn = (session != null && session.getAttribute("emp") != null);

        if (loggedIn) {
            // ✅ User has valid session, continue
            chain.doFilter(request, response);
        } else {
            // ❌ No valid session, redirect to login
            resp.sendRedirect(req.getContextPath() + "/login");
        }
        
        logger.info("auth filter ended");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // optional initialization logic
    }

    @Override
    public void destroy() {
        // optional cleanup
    }
}
