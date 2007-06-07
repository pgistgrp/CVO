package org.pgist.reports;


public class ReportServiceImpl implements ReportService{
	
	private ReportDAO reportDAO;

	public void setReportDAO(ReportDAO reportDAO) {
		this.reportDAO = reportDAO;
	}
	
}
