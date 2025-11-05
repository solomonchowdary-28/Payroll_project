package com.bsit.web.payrollrepository;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.bsit.web.model.Payroll;

public interface PayrollDao {
	
	public List<Payroll> findPayrollByEmployeeIdAndCurrentMonth(int id) throws SQLException;
	public List<Payroll> findPayrollByEmployeeIdAndPreviousMonth(int id) throws SQLException;
	public void findPayrollByEmployee(int id);
	public List<Payroll> findPayrollByDates(Date d1, Date d2, int id);

}
