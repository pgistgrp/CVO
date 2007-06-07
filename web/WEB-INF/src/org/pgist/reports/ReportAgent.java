package org.pgist.reports;

/**
 * DWR AJAX Agent class.<br>
 * Provide AJAX services to client programs.<br>
 * In this document, all the NON-AJAX methods are marked out. So all methods
 * <span style="color:red;">without</span> such a description
 * <span style="color:red;">ARE</span> AJAX service methods.<br>
 *
 * @author John
 *
 */

public class ReportAgent {

	private ReportService reportService;

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}
	
} //ReportAgent
