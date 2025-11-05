package com.bsit.web.FileService;

import java.util.Date;
import java.util.List;

import com.bsit.web.dbutils.DbConnectionmanager;
import com.bsit.web.model.Payroll;
import com.bsit.web.payrollDao.PayrollDao;
import com.bsit.web.payrollDao.PayrollDaoImpl;

public class FileServiceImpl implements FileService {
PayrollDao dao;
DbConnectionmanager manager;
	@Override
	public List<Payroll> downloadPdf(Date d1,Date d2,int id) {
	manager=new	DbConnectionmanager("db.properties"); 
		dao=new PayrollDaoImpl(manager);
		
		List<Payroll> payroll=dao.findPayrollByDates(d1,d2,id);
		
return payroll;
	}

	@Override
	public void downloadExcel() {
		// TODO Auto-generated method stub

	}

}
