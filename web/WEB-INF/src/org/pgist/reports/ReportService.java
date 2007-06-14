package org.pgist.reports;

import org.pgist.packages.ClusteredPackage;

public interface ReportService {

	
	void createStatistics(Long workflowId, Long cctId, Long projSuiteId, Long fundSuiteId, Long critSuiteId, Long projISID, Long fundISID) throws Exception;
	
	ClusteredPackage getPreferredClusteredPackage(Long pkgSuiteId) throws Exception;
	
}
