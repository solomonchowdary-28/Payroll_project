package com.bsit.web.fileservice;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bsit.web.dbutils.DbConnectionmanager;
import com.bsit.web.model.Payroll;
import com.bsit.web.payrollrepository.PayrollDao;
import com.bsit.web.payrollrepository.PayrollDaoImpl;

public class FileServiceImpl implements FileService {
PayrollDao dao;
DbConnectionmanager manager;
private static final Logger logger=LoggerFactory.getLogger(FileServiceImpl.class);
	@Override
	public List<Payroll> downloadPdf(Date d1,Date d2,int id) {
		logger.info("FileServiceImpl.downloadPdf()");
		manager=new	DbConnectionmanager("db.properties"); 
		dao=new PayrollDaoImpl(manager);
		List<Payroll> payroll=dao.findPayrollByDates(d1,d2,id);
		logger.info("FileServiceImpl.downloadPdf() completed");
		
        return payroll;
	}

	@Override
	public void downloadExcel() {
		//implted later 

	}

}
