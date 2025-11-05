package com.bsit.web.controller;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import com.bsit.web.FileService.FileService;
import com.bsit.web.FileService.FileServiceImpl;
import com.bsit.web.model.Payroll;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/downloads/*")
public class FileDownloads extends HttpServlet{
	private static final DateTimeFormatter MONTH_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final NumberFormat CURRENCY = NumberFormat.getCurrencyInstance(new Locale("en", "IN")); // change locale if needed

	
	FileService service=new FileServiceImpl();
	  protected void doGet(HttpServletRequest request,
              HttpServletResponse response)
 throws ServletException, IOException {
		  Integer id = (Integer) request.getSession().getAttribute("id");

		  String d1=request.getParameter("start");
		  String d2=request.getParameter("end");
		  //convert string to date
		  System.out.println(d1);
		  System.out.println(d2);
		  java.sql.Date startDate = java.sql.Date.valueOf(d1);
		  java.sql.Date endDate = java.sql.Date.valueOf(d2);
		 if(request.getPathInfo().equals("/excel")) {
			 //call service layer
			 System.out.println("excel download");
			 List<Payroll> payrolls=service.downloadPdf(startDate, endDate, id);
			 if (payrolls == null || payrolls.isEmpty()) {
		            response.setContentType("text/plain;charset=UTF-8");
		            response.getWriter().write("No payroll data available to generate PDF.");
		            return;
		        }
			 
			// PDF response headers
		        response.setContentType("application/pdf");
		        // Inline vs attachment: use attachment for direct download
		        response.setHeader("Content-Disposition", "attachment; filename=\"payrolls.pdf\"");

		        // Create PDF document
		        Document document = new Document(PageSize.A4.rotate(), 36, 36, 54, 36); // rotated 
		        
		        try (OutputStream out = response.getOutputStream()) {
		            PdfWriter.getInstance(document, out);
		            document.open();

		            // Title
		            Font titleFont = new Font(Font.HELVETICA, 16, Font.BOLD);
		            Paragraph title = new Paragraph("Payroll Report", titleFont);
		            title.setAlignment(Element.ALIGN_CENTER);
		            title.setSpacingAfter(12f);
		            document.add(title);

		            // Optional subtitle: employee or date range if you have it in session or params
		            Object empObj = request.getSession().getAttribute("employee"); // if available
		            if (empObj != null) {
		                // assume employee has getName()
		                try {
		                    String empName = empObj.getClass().getMethod("getName").invoke(empObj).toString();
		                    Paragraph sub = new Paragraph("Employee: " + empName);
		                    sub.setAlignment(Element.ALIGN_LEFT);
		                    sub.setSpacingAfter(8f);
		                    document.add(sub);
		                } catch (Exception ignore) { /* reflection fallback; not critical */ }
		            }

		            // Build table with columns - adjust columns to your Payroll fields
		            PdfPTable table = new PdfPTable(new float[] {1.2f, 2f, 1.5f, 1.5f, 1.2f, 1.2f, 1.2f, 1f, 1f, 1.2f, 1.2f});
		            table.setWidthPercentage(100);
		            addTableHeader(table);

		            for (Payroll p : payrolls) {
		                addPayrollRow(table, p);
		            }

		            document.add(table);

		            // Optional: totals row
		            // You can compute totals beforehand and then add a totals row

		            document.close();
		        } catch (DocumentException de) {
		            throw new IOException("Error generating PDF", de);
		        }
		 }//if
		  
		  
	  }//do
	  private void addTableHeader(PdfPTable table) {
	        Font head = new Font(Font.HELVETICA, 10, Font.BOLD);
	        addHeaderCell(table, "ID", head);
	        addHeaderCell(table, "Month", head);
	        addHeaderCell(table, "Basic", head);
	        addHeaderCell(table, "HRA", head);
	        addHeaderCell(table, "Allowances", head);
	        addHeaderCell(table, "Deductions", head);
	        addHeaderCell(table, "Tax", head);
	        addHeaderCell(table, "LOP", head);
	        addHeaderCell(table, "OT Hrs", head);
	        addHeaderCell(table, "OT Amt", head);
	        addHeaderCell(table, "Net Pay", head);
	    }
	  private void addHeaderCell(PdfPTable table, String text, Font font) {
	        PdfPCell cell = new PdfPCell(new Phrase(text, font));
	        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	        cell.setBackgroundColor(new Color(230, 230, 230));
	        cell.setPadding(6f);
	        table.addCell(cell);
	    }
	  
	  private void addPayrollRow(PdfPTable table, Payroll p) {
	        Font body = new Font(Font.HELVETICA, 9, Font.NORMAL);

	        // id
	        table.addCell(new PdfPCell(new Phrase(String.valueOf(p.getId()), body)));

	        // payrollMonth - may be String or LocalDate or Date - handle nulls safely
	        String monthVal = safeFormatDate(p.getPayrollMonth());
	        table.addCell(new PdfPCell(new Phrase(monthVal, body)));

	        // numeric values - format using currency or plain if needed
	        table.addCell(rightCell(formatMoney(p.getBasicPay()), body));
	        table.addCell(rightCell(formatMoney(p.getHra()), body));
	        table.addCell(rightCell(formatMoney(p.getAllowances()), body));
	        table.addCell(rightCell(formatMoney(p.getDeductions()), body));
	        table.addCell(rightCell(formatMoney(p.getTax()), body));

	        table.addCell(rightCell(String.valueOf(p.getLopDays()), body));
	        table.addCell(rightCell(formatDecimal(p.getOvertimeHours()), body));
	        table.addCell(rightCell(formatMoney(p.getOvertimeAmount()), body));
	        table.addCell(rightCell(formatMoney(p.getNetPay()), body));
	    }

	    private PdfPCell rightCell(String text, Font font) {
	        PdfPCell cell = new PdfPCell(new Phrase(text, font));
	        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	        cell.setPadding(6f);
	        return cell;
	    }

	    private String formatMoney(BigDecimal bd) {
	        if (bd == null) return "";
	        // Use NumberFormat to produce currency-like text; or bd.toPlainString()
	        return CURRENCY.format(bd.doubleValue());
	    }

	    private String formatDecimal(BigDecimal bd) {
	        if (bd == null) return "";
	        return bd.stripTrailingZeros().toPlainString();
	    }

	    private String safeFormatDate(Object payrollMonth) {
	        if (payrollMonth == null) return "";
	        // payrollMonth could be java.sql.Date, java.time.LocalDate, String, etc.
	        if (payrollMonth instanceof java.time.LocalDate) {
	            return ((java.time.LocalDate) payrollMonth).format(MONTH_FMT);
	        } else if (payrollMonth instanceof java.time.LocalDateTime) {
	            return ((java.time.LocalDateTime) payrollMonth).toLocalDate().format(MONTH_FMT);
	        } else if (payrollMonth instanceof java.sql.Date) {
	            return ((java.sql.Date) payrollMonth).toLocalDate().format(MONTH_FMT);
	        } else {
	            return payrollMonth.toString();
	        }
}
}
