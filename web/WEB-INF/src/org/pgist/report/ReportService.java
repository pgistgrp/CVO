package org.pgist.report;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.pgist.discussion.InfoStructure;
import org.pgist.packages.ClusteredPackage;
import org.pgist.users.User;

public interface ReportService {

	
	void createStatistics(Long workflowId, Long cctId, Long repoSuiteId, Long packSuiteId, Long critSuiteId, Long projSuiteId, Long fundSuiteId) throws Exception;
	
	Collection getVoteSuiteStats(Long pkgSuiteId) throws Exception;
	
	ReportSuite getReportSuiteById(Long id) throws Exception;
	
	void editReportSummary(Long reportSummaryId, String executiveSummary, String part1a, String part1b, String part2a, String part3a, String part4a, boolean finalized, String finalVoteDate, String finalReportDate) throws Exception;
	
	ReportSuite createReportSuite() throws Exception;
	
	InfoStructure publish(Long workflowId, Long cctId, Long suiteId, String title) throws Exception;
	
	void createReportVote(Long suiteId, boolean vote) throws Exception;
	
	boolean getUserVoted(Long suiteId) throws Exception;
	
	Map getVoteStats(Long suiteId) throws Exception;
	
	Set getFundRefbySuiteId(Long suiteId) throws Exception;
	
	Set getProjRefbySuiteId(Long suiteId) throws Exception;
	
	User getUserById(Long id) throws Exception;
	
}
 