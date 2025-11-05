package com.bsit.web.payrollmanagementservice;

import java.sql.SQLException;
import java.util.List;

import com.bsit.web.model.Payroll;
import com.bsit.web.ytdutils.PayrollSummary;

public interface PayrollManagementService {
	public List<Payroll> getCurrentMonthData(int id) throws SQLException;
	public List<Payroll> getPreviousMonthData(int id) throws SQLException;
	public PayrollSummary getYtdSummary(int id) throws SQLException;
	public void getTaxBreakup(int id);
	public Payroll view(List<Payroll> listPayroll, int id);

}
