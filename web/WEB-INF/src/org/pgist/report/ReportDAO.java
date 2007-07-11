package org.pgist.report;

import java.util.Collection;

import org.pgist.packages.ClusteredPackage;
import org.pgist.packages.PackageSuite;
import org.pgist.system.BaseDAO;

public interface ReportDAO extends BaseDAO {

	ReportStats createStatistics(Long workflowId, Long cctId, Long projSuiteId, Long fundSuiteId, Long critSuiteId, Long repoSuiteId, Long projISID, Long fundISID) throws Exception;
	
	ClusteredPackage getPreferredClusteredPackage(Long pkgSuiteId) throws Exception;
	
	Collection getVoteSuiteStats(Long pkgSuiteId) throws Exception;
	
	ReportSuite getReportSuiteById(Long id) throws Exception;
	
	void editReportSummary(Long reportSummaryId, String executiveSummary, String participantsSummary, String concernSummary, String criteriaSummary, String projectSummary, String packageSummary, boolean finalized) throws Exception;
	
	ReportSuite createReportSuite() throws Exception;
	 
}
