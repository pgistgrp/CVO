package org.pgist.reports;

import java.util.Collection;

import org.pgist.packages.ClusteredPackage;
import org.pgist.system.BaseDAO;

public interface ReportDAO extends BaseDAO {

	void createStatistics(Long workflowId, Long cctId, Long projSuiteId, Long fundSuiteId, Long critSuiteId, Long projISID, Long fundISID) throws Exception;
	
	ClusteredPackage getPreferredClusteredPackage(Long pkgSuiteId) throws Exception;
	
	Collection getVoteSuiteStats(Long pkgSuiteId) throws Exception;
	
}
