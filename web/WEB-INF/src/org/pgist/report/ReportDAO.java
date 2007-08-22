package org.pgist.report;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.pgist.packages.ClusteredPackage;
import org.pgist.system.BaseDAO;
import org.pgist.users.User;

public interface ReportDAO extends BaseDAO {

	void createStatsPart1(Long workflowId, Long cctId, Long repoSuiteId) throws Exception;
	
	void createStatsPart2(Long workflowId, Long cctId, Long repoSuiteId, Long critSuiteId) throws Exception;
	
	void createStatsPart3(Long workflowId, Long cctId, Long repoSuiteId, Long projSuiteId, Long packSuiteId, Long fundSuiteId) throws Exception;
	
	void createStatsPart4(Long workflowId, Long repoSuiteId, Long packSuiteId) throws Exception;
	
	void createStatsES(Long workflowId, Long cctId, Long repoSuiteId) throws Exception;
	
	Collection getVoteSuiteStats(Long pkgSuiteId) throws Exception;
	
	ReportSuite getReportSuiteById(Long id) throws Exception;
	
	void editReportSummary(Long reportSummaryId, String executiveSummary, String part1a, String part1b, String part2a, String part3a, String part4a, boolean finalized, String finalVoteDate, String finalReportDate) throws Exception;
	
	ReportSuite createReportSuite() throws Exception;
	 
	void createReportVote(Long suiteId, boolean vote, Long userId) throws Exception;
	
	boolean getUserVoted(Long suiteId, Long userId) throws Exception;
	
	Map getVoteStats(Long suiteId) throws Exception;
	
	Set getFundRefbySuiteId(Long suiteId) throws Exception;
	
	Set getProjRefbySuiteId(Long suiteId) throws Exception;
	
	User getUserById(Long id) throws Exception;
	
}
