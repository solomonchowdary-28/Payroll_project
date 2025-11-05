package com.bsit.web.employeerepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bsit.web.dbutils.DbConnectionmanager;
import com.bsit.web.model.Employee;

public class EmployeeDaoImpl implements EmployeeDao {
	private static final Logger logger=LoggerFactory.getLogger(EmployeeDaoImpl.class);
	private static final String FIND_EMPLOYEE_BY_EMAIL = "SELECT id, emp_code, name, email, mobile, password_hash, role, bank_account_no, bank_ifsc, created_at, updated_at FROM employee\r\n"
			+ " WHERE email = ?";
	 private DbConnectionmanager dbManager;
	 
	
	
	

	    public EmployeeDaoImpl(DbConnectionmanager dbManager) {
	        this.dbManager = dbManager;
	    }

	@Override
	public Employee findEmployeByEmail(String email) {
		  logger.info("EmployeeDaoImpl.findEmployeByEmail()");
		    logger.debug("Query: {}", FIND_EMPLOYEE_BY_EMAIL);

		    Employee emp = null;

		    try (Connection conn = dbManager.getConnection();
		         PreparedStatement ps = conn.prepareStatement(FIND_EMPLOYEE_BY_EMAIL)) {

		        ps.setString(1, email);

		        try (ResultSet rs = ps.executeQuery()) {
		            logger.debug("Result fetched from database for email={}", email);

		            if (rs.next()) {
		                logger.debug("Reading resultset row for email={}", email);
		                emp = new Employee();
		                emp.setId(rs.getInt("id"));
		                emp.setEmpCode(rs.getString("emp_code"));
		                emp.setName(rs.getString("name"));
		                emp.setEmail(rs.getString("email"));
		                emp.setMobile(rs.getString("mobile"));
		                emp.setPasswordHash(rs.getString("password_hash"));
		                emp.setRole(rs.getString("role"));
		                emp.setBankAccountNo(rs.getString("bank_account_no"));
		                emp.setBankIfsc(rs.getString("bank_ifsc"));

		                // Null-safe conversion of timestamps
		                java.sql.Timestamp createdTs = rs.getTimestamp("created_at");
		                if (createdTs != null) {
		                    emp.setCreatedAt(createdTs.toLocalDateTime());
		                }
		                java.sql.Timestamp updatedTs = rs.getTimestamp("updated_at");
		                if (updatedTs != null) {
		                    emp.setUpdatedAt(updatedTs.toLocalDateTime());
		                }
		            } else {
		                logger.debug("No employee found for email={}", email);
		            }
		        }

		    } catch (SQLException e) {
		        logger.error("Error in findEmployeByEmail for email={}: {}", email, e.getMessage(), e);
		    }

		    logger.info("EmployeeDaoImpl.findEmployeByEmail() completed.");
		    return emp;
	}

}
