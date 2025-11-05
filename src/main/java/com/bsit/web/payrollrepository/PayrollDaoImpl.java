package com.bsit.web.payrollrepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bsit.web.dbutils.DbConnectionmanager;
import com.bsit.web.model.Payroll;

public class PayrollDaoImpl implements PayrollDao {
	private static final Logger logger=LoggerFactory.getLogger(PayrollDaoImpl.class);
	private static final  String FIND_PAYROLL_BY_EMPLOYEE_ID_AND_CURRENT_MONTH = "SELECT id, employee_id, payroll_month, basic_pay, hra, allowances, deductions, tax,"
			+ "       lop_days, overtime_hours, overtime_amount, bonus_amount, net_pay,"
			+ "       credited_account, credited_date, status, created_at"
			+ " FROM employee_payroll"
			+ " WHERE employee_id = ? AND payroll_month = ?;";
	
	private static final String FIND_PAYROLL_BY_EMPLOYEE_ID_AND_PREVIOUS_MONTH ="SELECT id, employee_id, payroll_month, basic_pay, hra, allowances, deductions, tax, lop_days, overtime_hours, overtime_amount, bonus_amount, net_pay, credited_account, credited_date, status, created_at FROM employee_payroll WHERE employee_id = ? AND to_date(payroll_month, 'YYYY-MM-DD') >= date_trunc('year', current_date)::date AND to_date(payroll_month, 'YYYY-MM-DD') < date_trunc('month', current_date)::date ORDER BY to_date(payroll_month, 'YYYY-MM-DD');";
		
	
	private static final String FIND_PAYROLL_BY_DATES="SELECT id, employee_id, payroll_month, basic_pay, hra, allowances, deductions, tax, lop_days, overtime_hours, overtime_amount, bonus_amount, net_pay, credited_account, credited_date, status, created_at FROM public.employee_payroll WHERE TO_DATE(payroll_month, 'YYYY-MM-DD') BETWEEN ? AND ? AND employee_id = ?;";
	 private DbConnectionmanager dbManager;
	 Payroll payroll;
	 

	    public PayrollDaoImpl(DbConnectionmanager dbManager) {
	        this.dbManager = dbManager;
	    }

	@Override
	public List<Payroll> findPayrollByEmployeeIdAndCurrentMonth(int id) throws SQLException {
		logger.info("PayrollDaoImpl.FIND_PAYROLL_BY_EMPLOYEE_ID_AND_CURRENT_MONTH()");
		logger.debug(FIND_PAYROLL_BY_EMPLOYEE_ID_AND_CURRENT_MONTH);
		List<Payroll> list=null;
		// Define formatter once (outside if reused often)
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
		String currentMonth = LocalDate.now().format(formatter);

		String query = FIND_PAYROLL_BY_EMPLOYEE_ID_AND_CURRENT_MONTH;

		try (
		    Connection connection = dbManager.getConnection();
		    PreparedStatement ps = connection.prepareStatement(query);
		) {
		    ps.setInt(1, id);
		    ps.setString(2, currentMonth);

		    logger.debug("Executing query for employeeId={} and month={}", id, currentMonth);

		    try (ResultSet rs = ps.executeQuery()) {
		        list = copyResultSetToPayrollList(rs);
		    }

		catch(Exception e) {
			logger.error("error1",e);
			
		}
		 return list;
		}
	}
	//tocopy ResultSet data to List 
	public List<Payroll> copyResultSetToPayrollList(ResultSet rs) throws SQLException{
		 List<Payroll> payrollList=new ArrayList<>();
		while(rs.next()) {
				
				payroll = new Payroll();
				logger.debug("inside while");
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
		  
		return payrollList;
	}

	@Override
	public List<Payroll> findPayrollByEmployeeIdAndPreviousMonth(int id) throws SQLException {
		logger.info("PayrollDaoImpl.findPayrollByEmployeeIdAndPreviousMonth()");
	
	
	logger.debug(FIND_PAYROLL_BY_EMPLOYEE_ID_AND_PREVIOUS_MONTH );
	 List<Payroll> payrollList=null;
	
	 try (
			    Connection connection = dbManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(
					 FIND_PAYROLL_BY_EMPLOYEE_ID_AND_PREVIOUS_MONTH ,
					    ResultSet.TYPE_SCROLL_INSENSITIVE,
					    ResultSet.CONCUR_READ_ONLY
					);
			) {
			    ps.setInt(1, id);

			    try (ResultSet rs = ps.executeQuery()) {
			        if (!rs.next()) {
			            logger.debug("No payroll data found for employeeId={}", id);
			            return payrollList; // or handle as you wish
			        }

			        // Move cursor back to first row since rs.next() moved it
			        rs.beforeFirst();

			        payrollList = copyResultSetToPayrollList(rs);
			    }
	catch(Exception e) {
		logger.error("error2" ,e);
		
		
	}
	
	
	}
	 return payrollList;
	}
	 
	@Override
	public void findPayrollByEmployee(int id) {
           
//later implementation
	}
	
	
	
	public List<Payroll> findPayrollByDates(Date d1,Date d2,int id) {
		logger.info("PayrollDaoImpl.findPayrollByDates()");
		
		logger.debug(FIND_PAYROLL_BY_DATES);
		 List<Payroll> payrollList=null;
			
			try (
			Connection	conn=dbManager.getConnection();
			 PreparedStatement ps = conn.prepareStatement(FIND_PAYROLL_BY_DATES);
					)
			{
			  ps.setDate(1, (java.sql.Date) d1);
	            ps.setDate(2, (java.sql.Date) d2);
	            ps.setInt(3, 1);
			 ResultSet rs=ps.executeQuery();
			 
			
			 payrollList=	copyResultSetToPayrollList(rs);

			}
			catch(Exception e) {
				logger.error("error11",e);
				
				
			}
			
		
		return payrollList;
	}

}
