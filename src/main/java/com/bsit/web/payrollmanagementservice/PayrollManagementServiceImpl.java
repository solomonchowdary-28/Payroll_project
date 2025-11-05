package com.bsit.web.payrollmanagementservice;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bsit.web.dbutils.DbConnectionmanager;
import com.bsit.web.model.Payroll;
import com.bsit.web.payrollrepository.PayrollDao;
import com.bsit.web.payrollrepository.PayrollDaoImpl;
import com.bsit.web.ytdutils.PayrollSummary;
import com.bsit.web.ytdutils.YtdUtils;

public class PayrollManagementServiceImpl implements PayrollManagementService {
	PayrollDao dao;
	private static final Logger logger=LoggerFactory.getLogger(PayrollManagementServiceImpl.class);
	@Override
	public List<Payroll> getCurrentMonthData(int id) throws SQLException {
		logger.info("PayrollManagementServiceImpl.getCurrentMonthData() started...");
		dao=new PayrollDaoImpl(new DbConnectionmanager("db.properties"));
		List<Payroll> payrollData=dao.findPayrollByEmployeeIdAndCurrentMonth(id);
		logger.info("PayrollManagementServiceImpl.getCurrentMonthData() ended");
	return payrollData;
	}

	@Override
	public List<Payroll> getPreviousMonthData(int id) throws SQLException {
		logger.info("PayrollManagementServiceImpl.getPreviousMonthData() strated....");
		dao=new PayrollDaoImpl(new DbConnectionmanager("db.properties"));
		List<Payroll> list=dao.findPayrollByEmployeeIdAndPreviousMonth(id);
		logger.info("PayrollManagementServiceImpl.getPreviousMonthData( ended....)");
		return list;

	}

	@Override
	public PayrollSummary getYtdSummary(int id) throws SQLException {
		logger.info("PayrollManagementServiceImpl.getYtdSummary() strated....");
		List<Payroll> currentMonth=getCurrentMonthData(id);
		logger.debug("retrived current month data");
		List<Payroll> previousMonth=getPreviousMonthData(id);
		logger.debug("retrived previous month data");
		List<Payroll> ytd=new ArrayList<Payroll>();
		ytd.addAll(currentMonth);
		ytd.addAll(previousMonth);
		YtdUtils utils=new YtdUtils();
		PayrollSummary summary =utils.calculateYtdTotals(ytd);
		logger.info("PayrollManagementServiceImpl.getYtdSummary() ended....");
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
