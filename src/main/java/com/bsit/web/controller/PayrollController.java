package com.bsit.web.controller;

import java.io.IOException;
import java.util.List;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import com.bsit.web.EmployeeDao.EmployeeDaoImpl;
import com.bsit.web.dbutils.DbConnectionmanager;
import com.bsit.web.model.Employee;
import com.bsit.web.model.Payroll;
import com.bsit.web.payrollmanagementservice.PayrollManagementService;
import com.bsit.web.payrollmanagementservice.PayrollManagementServiceImpl;
import com.bsit.web.ytdutils.PayrollSummary;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/payroll/*")
public class PayrollController extends HttpServlet{
	
	private TemplateEngine templateEngine;
    private JakartaServletWebApplication application;
    WebContext context;

	
	PayrollManagementService payrollService=new PayrollManagementServiceImpl();
	DbConnectionmanager manager =new DbConnectionmanager("db.properties");
	EmployeeDaoImpl employeeDao=new EmployeeDaoImpl(manager);
	 @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        String path = request.getServletPath();
	        int id=(int) request.getSession().getAttribute("id");
	        String email=(String) request.getSession().getAttribute("email");
	        List<Payroll> data = null;
	        System.out.println(request.getPathInfo());
	       
	       if(request.getPathInfo().equals("/currentmonth")) {
	    	   System.out.println(request.getPathInfo());
	    	   //to get payroll data 
	    	      data  = payrollService.getCurrentMonthData(id);
	    	      System.out.println(data.get(0).getEmployeeId()+"  employeeid for current month");
	    	      request.getSession().setAttribute("payrollData", data);
	    	      
	    	  
	    	   renderdashboard(request,response,data);
	       }
	       if(request.getPathInfo().equals("/previousmonths")) {
	    	   //all previous months
	    	   System.out.println("inside previousmonths");
	    	   List<Payroll> list= payrollService.getPreviousMonthData(id);
	    	   request.getSession().setAttribute("payrollData", list);
	    	  renderdashboard(request, response, list);
	    	   
	    	   
	       }
	       
	       if(request.getPathInfo().equals("/ytd")) {
	    	  PayrollSummary summary= payrollService.getYtdSummary(id);
	    	  templateEngine = (TemplateEngine) getServletContext().getAttribute("templateEngine");
	     	 
	     	 application=(JakartaServletWebApplication)getServletContext().getAttribute("application");
	         // Build the "exchange" object required by the new WebContext constructor
	         var exchange = application.buildExchange(request, response);

	         // ✅ New way to create the WebContext in Thymeleaf 3.1
	          context = new WebContext(exchange);
	       
	         context.setVariable("summary", summary);
	         
	        
	         response.setContentType("text/html;charset=UTF-8");
	         templateEngine.process("ytdsummary", context, response.getWriter());
	    	  
	    	  
	    	  
	       }
	       if(request.getPathInfo().equals("/Tax")) {
	    	   
	       }
	       
          if(request.getPathInfo().equals("/view")) {
	    	  int payrollId=Integer.parseInt(request.getParameter("id"));
	    	  //get payroll data 
	    	 List<Payroll> listPayroll= (List<Payroll>) request.getSession().getAttribute("payrollData");
	    	 Payroll PayrollIdData=payrollService.view(listPayroll,payrollId);
	    	 //get employee data
             templateEngine = (TemplateEngine) getServletContext().getAttribute("templateEngine");
	     	 
	     	 application=(JakartaServletWebApplication)getServletContext().getAttribute("application");
	         // Build the "exchange" object required by the new WebContext constructor
	         var exchange = application.buildExchange(request, response);
	         context = new WebContext(exchange);
	    	 Employee emp= (Employee) request.getSession().getAttribute("emp");
	    	 context.setVariable("payroll", PayrollIdData);
	    	 context.setVariable("employee", emp);
	         
		        
	         response.setContentType("text/html;charset=UTF-8");
	         templateEngine.process("view", context, response.getWriter());
	    	  
	    	
	    	
	       }
            
	        
	        
	    }
	 //dashboard rendering logic for current month and previousmonths
	private void renderdashboard(HttpServletRequest request,HttpServletResponse response,List<Payroll> data) throws IOException {
		// TODO Auto-generated method stub
		 templateEngine = (TemplateEngine) getServletContext().getAttribute("templateEngine");
    	 
    	 application=(JakartaServletWebApplication)getServletContext().getAttribute("application");
        // Build the "exchange" object required by the new WebContext constructor
        var exchange = application.buildExchange(request, response);

        // ✅ New way to create the WebContext in Thymeleaf 3.1
        WebContext context = new WebContext(exchange);
      
        context.setVariable("payrolls", data);
        
       
        response.setContentType("text/html;charset=UTF-8");
        templateEngine.process("dashboard_2", context, response.getWriter());
	}
	
	

}
