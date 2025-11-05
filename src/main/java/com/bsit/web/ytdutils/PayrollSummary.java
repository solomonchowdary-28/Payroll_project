package com.bsit.web.ytdutils;

public class PayrollSummary {
    private double totalBasicPay;
    private double totalHra;
    private double totalAllowances;
    private double totalDeductions;
    private double totalTax;
    private double totalBonus;
    private double totalOvertimeAmount;
    private double totalNetPay;

    // Getters and setters
    public double getTotalBasicPay() { return totalBasicPay; }
    public void setTotalBasicPay(double totalBasicPay) { this.totalBasicPay = totalBasicPay; }

    public double getTotalHra() { return totalHra; }
    public void setTotalHra(double totalHra) { this.totalHra = totalHra; }

    public double getTotalAllowances() { return totalAllowances; }
    public void setTotalAllowances(double totalAllowances) { this.totalAllowances = totalAllowances; }

    public double getTotalDeductions() { return totalDeductions; }
    public void setTotalDeductions(double totalDeductions) { this.totalDeductions = totalDeductions; }

    public double getTotalTax() { return totalTax; }
    public void setTotalTax(double totalTax) { this.totalTax = totalTax; }

    public double getTotalBonus() { return totalBonus; }
    public void setTotalBonus(double totalBonus) { this.totalBonus = totalBonus; }

    public double getTotalOvertimeAmount() { return totalOvertimeAmount; }
    public void setTotalOvertimeAmount(double totalOvertimeAmount) { this.totalOvertimeAmount = totalOvertimeAmount; }

    public double getTotalNetPay() { return totalNetPay; }
    public void setTotalNetPay(double totalNetPay) { this.totalNetPay = totalNetPay; }
}
