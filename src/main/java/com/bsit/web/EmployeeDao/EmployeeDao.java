package com.bsit.web.EmployeeDao;

import com.bsit.web.model.Employee;

public interface EmployeeDao  {
	
	public Employee findEmployeByEmail(String email);

}
