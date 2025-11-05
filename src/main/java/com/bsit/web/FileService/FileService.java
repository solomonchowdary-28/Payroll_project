package com.bsit.web.fileservice;

import java.util.Date;
import java.util.List;

import com.bsit.web.model.Payroll;

public interface  FileService {
	
	
	
	public void downloadExcel();
	public List<Payroll> downloadPdf(Date d1, Date d2, int id);

}
