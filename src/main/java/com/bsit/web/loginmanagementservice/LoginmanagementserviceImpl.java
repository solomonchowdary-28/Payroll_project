package com.bsit.web.loginmanagementservice;

import com.bsit.web.EmployeeDao.EmployeeDao;
import com.bsit.web.EmployeeDao.EmployeeDaoImpl;
import com.bsit.web.dbutils.DbConnectionmanager;
import com.bsit.web.model.Employee;

public class LoginmanagementserviceImpl implements Loginmanagementservice{

	EmployeeDao dao;
	
	@Override
	public Employee login(String email, String pwd) {
		System.out.println("LoginmanagementserviceImpl.login()");
		DbConnectionmanager manager=new DbConnectionmanager("db.properties");
		dao=new EmployeeDaoImpl(manager); 
		Employee emp=dao.findEmployeByEmail(email);
		System.out.println(emp.getPasswordHash()+"   "+emp.getId());
		if(emp.getPasswordHash().equals(pwd)) {
			System.out.println("equals");
		return emp;
		}
		else 
			return null;

}
}
