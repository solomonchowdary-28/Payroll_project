package com.bsit.web.payrollmanagementservice;

import java.util.ArrayList;
import java.util.List;

import com.bsit.web.dbutils.DbConnectionmanager;
import com.bsit.web.model.Payroll;
import com.bsit.web.payrollDao.PayrollDao;
import com.bsit.web.payrollDao.PayrollDaoImpl;
import com.bsit.web.ytdutils.PayrollSummary;
import com.bsit.web.ytdutils.YtdUtils;

public class PayrollManagementServiceImpl implements PayrollManagementService {
	PayrollDao dao;
	@Override
	public List<Payroll> getCurrentMonthData(int id) {
		
	dao=new PayrollDaoImpl(new DbConnectionmanager("db.properties"));
	List<Payroll> payrollData=dao.findPayrollByEmployeeIdAndCurrentMonth(id);
System.out.println(payrollData.get(0).getEmployeeId()+" empid from service");
	return payrollData;
		

	}

	@Override
	public List<Payroll> getPreviousMonthData(int id) {
		dao=new PayrollDaoImpl(new DbConnectionmanager("db.properties"));
		List<Payroll> list=dao.findPayrollByEmployeeIdAndPreviousMonth(id);
		return list;

	}

	@Override
	public PayrollSummary getYtdSummary(int id) {
		//currentmonth payroll call method
		List<Payroll> currentMonth=getCurrentMonthData(id);
		List<Payroll> previousMonth=getPreviousMonthData(id);
		List<Payroll> ytd=new ArrayList<Payroll>();
		ytd.addAll(currentMonth);
		ytd.addAll(previousMonth);
		
		YtdUtils utils=new YtdUtils();
		PayrollSummary summary =utils.calculateYtdTotals(ytd);
		
		return summary;
		
		

	}

	@Override
	public void getTaxBreakup(int id) {
		// TODO Auto-generated method stub

	}
	
	
	public Payroll view(List<Payroll> listPayroll,int payrollId) {
		
		Payroll payroll=null;
		for(int i=0;i<listPayroll.size();i++) {
			 payroll=listPayroll.get(i);
				System.out.println(payrollId+"  "+payroll.getId());
			if(payroll.getId()==payrollId) {
			
				return payroll;
			}
		}
		return payroll;
		
	}

}
