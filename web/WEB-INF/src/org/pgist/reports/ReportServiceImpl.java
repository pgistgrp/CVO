package org.pgist.reports;


public class ReportServiceImpl implements ReportService{
	
	private ReportDAO reportDAO;

	public void setReportDAO(ReportDAO reportDAO) {
		this.reportDAO = reportDAO;
	}
	
	
	public void createStatistics(Long workflowId, Long cctId, Long projSuiteId, Long fundSuiteId, Long critSuiteId, Long projISID, Long fundISID) throws Exception {
		reportDAO.createStatistics(workflowId, cctId, projSuiteId, fundSuiteId, critSuiteId, projISID, fundISID);
	}
	
}
