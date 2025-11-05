package com.bsit.web.Initializer;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.IWebApplication;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
@WebListener
public class ThymeleafInitializer implements ServletContextListener{
	
	@Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        // Wrap ServletContext
        IWebApplication application = JakartaServletWebApplication.buildApplication(context);

        // Configure template resolver
        WebApplicationTemplateResolver resolver = new WebApplicationTemplateResolver(application);
        resolver.setPrefix("/WEB-INF/templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML");
        resolver.setCharacterEncoding("UTF-8");

        // Create TemplateEngine
        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);

        // Store in ServletContext and application for all servlets to use
        context.setAttribute("templateEngine", engine);
        context.setAttribute("application", application);

        System.out.println("✅ Thymeleaf TemplateEngine initialized once globally");
    }


}
