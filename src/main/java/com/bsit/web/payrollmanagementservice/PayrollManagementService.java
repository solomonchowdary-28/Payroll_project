package com.bsit.web.payrollmanagementservice;

import java.util.List;

import com.bsit.web.model.Payroll;
import com.bsit.web.ytdutils.PayrollSummary;

public interface PayrollManagementService {
	public List<Payroll> getCurrentMonthData(int id);
	public List<Payroll> getPreviousMonthData(int id);
	public PayrollSummary getYtdSummary(int id);
	public void getTaxBreakup(int id);
	public Payroll view(List<Payroll> listPayroll, int id);

}
