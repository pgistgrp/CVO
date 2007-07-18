package org.pgist.report;

import java.util.Collection;
import java.util.Map;

import org.pgist.packages.ClusteredPackage;
import org.pgist.packages.PackageSuite;
import org.pgist.system.BaseDAO;

public interface ReportDAO extends BaseDAO {

	void createConcernStatistics(Long workflowId, Long cctId, Long repoSuiteId) throws Exception;
	
	void createPkgStatistics(Long workflowId, Long repoSuiteId, Long packSuiteId) throws Exception;
	
	ClusteredPackage getPreferredClusteredPackage(Long pkgSuiteId) throws Exception;
	
	Collection getVoteSuiteStats(Long pkgSuiteId) throws Exception;
	
	ReportSuite getReportSuiteById(Long id) throws Exception;
	
	void editReportSummary(Long reportSummaryId, String executiveSummary, String participantsSummary, String concernSummary, String criteriaSummary, String projectSummary, String packageSummary, boolean finalized) throws Exception;
	
	ReportSuite createReportSuite() throws Exception;
	 
	void createReportVote(Long suiteId, boolean vote, Long userId) throws Exception;
	
	boolean getUserVoted(Long suiteId, Long userId) throws Exception;
	
	Map getVoteStats(Long suiteId) throws Exception;
	
}
