package com.bsit.web.payrollDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bsit.web.dbutils.DbConnectionmanager;
import com.bsit.web.model.Payroll;

public class PayrollDaoImpl implements PayrollDao {
	private final String query = "SELECT id, employee_id, payroll_month, basic_pay, hra, allowances, deductions, tax,"
			+ "       lop_days, overtime_hours, overtime_amount, bonus_amount, net_pay,"
			+ "       credited_account, credited_date, status, created_at"
			+ " FROM employee_payroll"
			+ " WHERE employee_id = ? AND payroll_month = ?;";
	 private DbConnectionmanager dbManager;
	 Payroll payroll;
	 Connection conn;

	    public PayrollDaoImpl(DbConnectionmanager dbManager) {
	        this.dbManager = dbManager;
	    }

	@Override
	public List<Payroll> findPayrollByEmployeeIdAndCurrentMonth(int id) {
		System.out.println("PayrollDaoImpl.findPayrollByEmployeeIdAndCurrentMonth()");
		System.out.println(query);
		List<Payroll> list=null;
		try {
			conn=dbManager.getConnection();
		 PreparedStatement ps = conn.prepareStatement(query); 
		 ps.setInt(1, id);
		// Create formatter for yyyy-MM
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
		 String currentMonth = LocalDate.now().format(formatter);
         System.out.println(id+" "+currentMonth);
		 ps.setString(2, currentMonth);
		 ResultSet rs=ps.executeQuery();
		 list=copyResultSetToPayrollList(rs);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		 return list;
	}
	//tocopy ResultSet data to List 
	public List<Payroll> copyResultSetToPayrollList(ResultSet rs) throws SQLException{
		 List<Payroll> payrollList=new ArrayList<>();
		while(rs.next()) {
			 System.out.println("inside rs");
			  payroll = new Payroll();
	          System.out.println("inside while");
				 payroll.setId(rs.getInt("id"));
				 payroll.setEmployeeId(rs.getInt("employee_id"));
				 payroll.setPayrollMonth(rs.getString("payroll_month"));
				 payroll.setBasicPay(rs.getBigDecimal("basic_pay"));
				 payroll.setHra(rs.getBigDecimal("hra"));
				 payroll.setAllowances(rs.getBigDecimal("allowances"));
				 payroll.setDeductions(rs.getBigDecimal("deductions"));
				 payroll.setTax(rs.getBigDecimal("tax"));
				 payroll.setLopDays(rs.getInt("lop_days"));
				 payroll.setOvertimeHours(rs.getBigDecimal("overtime_hours"));
				 payroll.setOvertimeAmount(rs.getBigDecimal("overtime_amount"));
				 payroll.setBonusAmount(rs.getBigDecimal("bonus_amount"));
				 payroll.setNetPay(rs.getBigDecimal("net_pay"));
				 payroll.setCreditedAccount(rs.getString("credited_account"));

				 // Convert SQL DATE → LocalDate safely
				 java.sql.Date creditedDate = rs.getDate("credited_date");
				 if (creditedDate != null) {
				     payroll.setCreditedDate(creditedDate.toLocalDate());
				 }

				 payroll.setStatus(rs.getString("status"));

				 // Convert SQL TIMESTAMP → LocalDateTime safely
				 java.sql.Timestamp createdAt = rs.getTimestamp("created_at");
				 if (createdAt != null) {
				     payroll.setCreatedAt(createdAt.toLocalDateTime());
				 }

			 payrollList.add(payroll);
		 

			
		 }
		   System.out.println(payrollList.get(0).getEmployeeId()+"  employeeid for current month");
		return payrollList;
	}

	@Override
	public List<Payroll> findPayrollByEmployeeIdAndPreviousMonth(int id) {
	String query2="SELECT * "
			+ "FROM employee_payroll\r\n"
			+ "WHERE employee_id =?"
			+ "  AND to_date(payroll_month, 'YYYY-MM-DD') >= date_trunc('year', current_date)::date\r\n"
			+ "  AND to_date(payroll_month, 'YYYY-MM-DD') < date_trunc('month', current_date)::date\r\n"
			+ "ORDER BY to_date(payroll_month, 'YYYY-MM-DD');\r\n";
	
	System.out.println(query2);
	 List<Payroll> payrollList=null;
	
	try {
		conn=dbManager.getConnection();
	 PreparedStatement ps = conn.prepareStatement(query2); 
	 ps.setInt(1, id);
	 ResultSet rs=ps.executeQuery();
	 if(rs.next()==false) {
		 System.out.println("no data");
		 
	 }
	
	 payrollList=	copyResultSetToPayrollList(rs);

	}
	catch(Exception e) {
		e.printStackTrace();
		
	}
	
	return payrollList;
	}
	@Override
	public void findPayrollByEmployee(int id) {
           

	}
	
	
	
	public List<Payroll> findPayrollByDates(Date d1,Date d2,int id) {
		String query="SELECT * FROM public.employee_payroll WHERE TO_DATE(payroll_month, 'YYYY-MM-DD') BETWEEN ? AND ? AND employee_id = ?;";
		
		System.out.println(query);
		 List<Payroll> payrollList=null;
			
			try {
				conn=dbManager.getConnection();
			 PreparedStatement ps = conn.prepareStatement(query); 
			  ps.setDate(1, (java.sql.Date) d1);
	            ps.setDate(2, (java.sql.Date) d2);
	            ps.setInt(3, 1);
			 ResultSet rs=ps.executeQuery();
			 if(rs.next()==false) {
				 System.out.println("no data");
				 
			 }
			
			 payrollList=	copyResultSetToPayrollList(rs);

			}
			catch(Exception e) {
				e.printStackTrace();
				
			}
		
		return payrollList;
	}

}
