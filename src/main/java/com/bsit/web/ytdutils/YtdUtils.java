package com.bsit.web.ytdutils;


import java.util.List;

import com.bsit.web.model.Payroll;

public class YtdUtils {
	
	
	public PayrollSummary calculateYtdTotals(List<Payroll> totalPayrolls) {
	    PayrollSummary summary = new PayrollSummary();

	    double totalBasic = 0.0;
	    double totalHra = 0.0;
	    double totalAllowances = 0.0;
	    double totalDeductions = 0.0;
	    double totalTax = 0.0;
	    double totalBonus = 0.0;
	    double totalOvertime = 0.0;
	    double totalNet = 0.0;

	    for (Payroll p : totalPayrolls) {
	        if (p.getBasicPay() != null) totalBasic += p.getBasicPay().doubleValue();
	        if (p.getHra() != null) totalHra += p.getHra().doubleValue();
	        if (p.getAllowances() != null) totalAllowances += p.getAllowances().doubleValue();
	        if (p.getDeductions() != null) totalDeductions += p.getDeductions().doubleValue();
	        if (p.getTax() != null) totalTax += p.getTax().doubleValue();
	        if (p.getBonusAmount() != null) totalBonus += p.getBonusAmount().doubleValue();
	        if (p.getOvertimeAmount() != null) totalOvertime += p.getOvertimeAmount().doubleValue();
	        if (p.getNetPay() != null) totalNet += p.getNetPay().doubleValue();
	    }

	    summary.setTotalBasicPay(totalBasic);
	    summary.setTotalHra(totalHra);
	    summary.setTotalAllowances(totalAllowances);
	    summary.setTotalDeductions(totalDeductions);
	    summary.setTotalTax(totalTax);
	    summary.setTotalBonus(totalBonus);
	    summary.setTotalOvertimeAmount(totalOvertime);
	    summary.setTotalNetPay(totalNet);

	    return summary;
	}
	
	 
}
	    



