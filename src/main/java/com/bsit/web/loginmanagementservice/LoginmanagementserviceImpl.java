package com.bsit.web.loginmanagementservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bsit.web.dbutils.DbConnectionmanager;
import com.bsit.web.employeerepository.EmployeeDao;
import com.bsit.web.employeerepository.EmployeeDaoImpl;
import com.bsit.web.model.Employee;

public class LoginmanagementserviceImpl implements Loginmanagementservice{
private static final Logger logger=LoggerFactory.getLogger(LoginmanagementserviceImpl.class);
	EmployeeDao dao;
	
	@Override
	public Employee login(String email, String pwd) {
		logger.info("LoginmanagementserviceImpl.login()");
		DbConnectionmanager manager=new DbConnectionmanager("db.properties");
		dao=new EmployeeDaoImpl(manager); 
		Employee emp=dao.findEmployeByEmail(email);

		return emp;

}
}
