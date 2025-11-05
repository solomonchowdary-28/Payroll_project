package com.bsit.web.employeerepository;

import com.bsit.web.model.Employee;

public interface EmployeeDao  {
	
	public Employee findEmployeByEmail(String email);

}
