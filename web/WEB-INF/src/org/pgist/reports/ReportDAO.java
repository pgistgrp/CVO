package org.pgist.reports;

import org.pgist.system.BaseDAO;

public interface ReportDAO extends BaseDAO {

	void createStatistics(Long workflowId, Long cctId, Long projSuiteId, Long fundSuiteId, Long critSuiteId, Long projISID, Long fundISID) throws Exception;
	
}
