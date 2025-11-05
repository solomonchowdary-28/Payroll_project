package com.bsit.web.EmployeeDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.bsit.web.dbutils.DbConnectionmanager;
import com.bsit.web.model.Employee;

public class EmployeeDaoImpl implements EmployeeDao {
	private final String query = "SELECT id, emp_code, name, email, mobile, password_hash, role, bank_account_no, bank_ifsc, created_at, updated_at FROM employee\r\n"
			+ " WHERE email = ?";
	 private DbConnectionmanager dbManager;
	 Employee emp;
	 Connection conn;

	    public EmployeeDaoImpl(DbConnectionmanager dbManager) {
	        this.dbManager = dbManager;
	    }

	@Override
	public Employee findEmployeByEmail(String email) {
       
		System.out.println("EmployeeDaoImpl.findEmployeByEmail()");
		System.out.println(query);

	        
	        try  {
	        	conn = dbManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(query);
        		ps.setString(1, email);
                ResultSet rs = ps.executeQuery();
                System.out.println("after resultset getting");
	              
	        	
                if (rs.next()) {
                    Employee employee = new Employee();
                    employee.setId(rs.getInt("id"));
                    employee.setEmpCode(rs.getString("emp_code"));
                    employee.setName(rs.getString("name"));
                    employee.setEmail(rs.getString("email"));
                    employee.setMobile(rs.getString("mobile"));
                    employee.setPasswordHash(rs.getString("password_hash"));
                    employee.setRole(rs.getString("role"));
                    employee.setBankAccountNo(rs.getString("bank_account_no"));
                    employee.setBankIfsc(rs.getString("bank_ifsc"));
                    employee.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    employee.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                    return employee;
                }
	        	

	           } catch (Exception e) {
	            System.out.println(e);
	            e.printStackTrace();
	           }
	        
	        return emp;


	}

}
