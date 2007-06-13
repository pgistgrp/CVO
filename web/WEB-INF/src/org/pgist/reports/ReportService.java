package org.pgist.reports;

public interface ReportService {

	
	void createStatistics(Long workflowId, Long cctId, Long projSuiteId, Long fundSuiteId, Long critSuiteId, Long projISID, Long fundISID) throws Exception;
	
}
