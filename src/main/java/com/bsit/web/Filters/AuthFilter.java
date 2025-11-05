package com.bsit.web.Filters;



import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

// Intercepts all requests
@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        HttpSession session = req.getSession(false); // don't create new session

        // Whitelist: paths that should NOT require login
        boolean isLoginPage = uri.endsWith("/login") || uri.endsWith("/loginsubmit");
        boolean isOtpPage = uri.endsWith("/otp") || uri.endsWith("/otpverify");
        boolean isStaticResource = uri.contains("/css/") || uri.contains("/js/") || uri.contains("/images/");

        // Skip filter for allowed pages
        if (isLoginPage || isOtpPage || isStaticResource) {
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
