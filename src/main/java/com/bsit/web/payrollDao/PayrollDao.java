package com.bsit.web.payrollDao;

import java.util.Date;
import java.util.List;

import com.bsit.web.model.Payroll;

public interface PayrollDao {
	
	public List<Payroll> findPayrollByEmployeeIdAndCurrentMonth(int id);
	public List<Payroll> findPayrollByEmployeeIdAndPreviousMonth(int id);
	public void findPayrollByEmployee(int id);
	public List<Payroll> findPayrollByDates(Date d1, Date d2, int id);

}
