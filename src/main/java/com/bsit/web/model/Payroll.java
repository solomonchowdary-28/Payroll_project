package com.bsit.web.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Payroll {
	    private int id;
	    private int employeeId;
	    private String payrollMonth;
	    private BigDecimal basicPay;
	    private BigDecimal hra;
	    private BigDecimal allowances;
	    private BigDecimal deductions;
	    private BigDecimal tax;
	    private int lopDays;
	    private BigDecimal overtimeHours;
	    private BigDecimal overtimeAmount;
	    private BigDecimal bonusAmount;
	    private BigDecimal netPay;
	    private String creditedAccount;
	    private LocalDate creditedDate;
	    private String status;
	    private LocalDateTime createdAt;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getEmployeeId() {
			return employeeId;
		}
		public void setEmployeeId(int employeeId) {
			this.employeeId = employeeId;
		}
		public String getPayrollMonth() {
			return payrollMonth;
		}
		public void setPayrollMonth(String payrollMonth) {
			this.payrollMonth = payrollMonth;
		}
		public BigDecimal getBasicPay() {
			return basicPay;
		}
		public void setBasicPay(BigDecimal basicPay) {
			this.basicPay = basicPay;
		}
		public BigDecimal getHra() {
			return hra;
		}
		public void setHra(BigDecimal hra) {
			this.hra = hra;
		}
		public BigDecimal getAllowances() {
			return allowances;
		}
		public void setAllowances(BigDecimal allowances) {
			this.allowances = allowances;
		}
		public BigDecimal getDeductions() {
			return deductions;
		}
		public void setDeductions(BigDecimal deductions) {
			this.deductions = deductions;
		}
		public BigDecimal getTax() {
			return tax;
		}
		public void setTax(BigDecimal tax) {
			this.tax = tax;
		}
		public int getLopDays() {
			return lopDays;
		}
		public void setLopDays(int lopDays) {
			this.lopDays = lopDays;
		}
		public BigDecimal getOvertimeHours() {
			return overtimeHours;
		}
		public void setOvertimeHours(BigDecimal overtimeHours) {
			this.overtimeHours = overtimeHours;
		}
		public BigDecimal getOvertimeAmount() {
			return overtimeAmount;
		}
		public void setOvertimeAmount(BigDecimal overtimeAmount) {
			this.overtimeAmount = overtimeAmount;
		}
		public BigDecimal getBonusAmount() {
			return bonusAmount;
		}
		public void setBonusAmount(BigDecimal bonusAmount) {
			this.bonusAmount = bonusAmount;
		}
		public BigDecimal getNetPay() {
			return netPay;
		}
		public void setNetPay(BigDecimal netPay) {
			this.netPay = netPay;
		}
		public String getCreditedAccount() {
			return creditedAccount;
		}
		public void setCreditedAccount(String creditedAccount) {
			this.creditedAccount = creditedAccount;
		}
		public LocalDate getCreditedDate() {
			return creditedDate;
		}
		public void setCreditedDate(LocalDate creditedDate) {
			this.creditedDate = creditedDate;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public LocalDateTime getCreatedAt() {
			return createdAt;
		}
		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}

}
