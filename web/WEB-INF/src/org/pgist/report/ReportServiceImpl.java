package org.pgist.report;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.pgist.cvo.CCT;
import org.pgist.discussion.DiscussionDAO;
import org.pgist.discussion.InfoObject;
import org.pgist.discussion.InfoStructure;
import org.pgist.packages.ClusteredPackage;
import org.pgist.util.WebUtils;

public class ReportServiceImpl implements ReportService{
	
	
	private ReportDAO reportDAO;
	
	
	private DiscussionDAO discussionDAO;
	
	
	public void setDiscussionDAO(DiscussionDAO discussionDAO) {
		this.discussionDAO = discussionDAO;
	}
	
	
	public void setReportDAO(ReportDAO reportDAO) {
		this.reportDAO = reportDAO;
	}
	
	
	public void createStatistics(Long workflowId, Long cctId, Long repoSuiteId, Long packSuiteId, Long critSuiteId, Long projSuiteId) throws Exception {
		reportDAO.createStatsPart1(workflowId, cctId, repoSuiteId);
		reportDAO.createStatsPart2(workflowId, cctId, repoSuiteId, critSuiteId);
		reportDAO.createStatsPart3(workflowId, cctId, repoSuiteId, projSuiteId, packSuiteId);
		reportDAO.createStatsPart4(workflowId, repoSuiteId, packSuiteId);
		reportDAO.createStatsES(workflowId, repoSuiteId, packSuiteId); // Must be ran last
	}
	
	
	public Collection getVoteSuiteStats(Long pkgSuiteId) throws Exception {
		return reportDAO.getVoteSuiteStats(pkgSuiteId);
	}

	
	public ReportSuite getReportSuiteById(Long id) throws Exception {
		return reportDAO.getReportSuiteById(id);
	}
	
	
	public void editReportSummary(Long reportSummaryId, String executiveSummary, String part1a, String part1b, String part2a, String part3a, String part4a, boolean finalized, String finalVoteDate, String finalReportDate) throws Exception {
		reportDAO.editReportSummary(reportSummaryId, executiveSummary, part1a, part1b, part2a, part3a, part4a, finalized, finalVoteDate, finalReportDate);
	}
	
	
	public ReportSuite createReportSuite() throws Exception {
		return reportDAO.createReportSuite();
	}
	
	
	 public InfoStructure publish(Long workflowId, Long cctId, Long suiteId, String title) throws Exception {


	        CCT cct = (CCT)reportDAO.load(CCT.class, cctId);
	        
	        ReportSuite suite = reportDAO.getReportSuiteById(suiteId);
	        
	        Date date = new Date();
	        
	        
	        InfoStructure structure = new InfoStructure();
            structure.getDiscussion().setWorkflowId(workflowId);
            
	        structure.setType("sdr");
	        structure.setTitle(title);
	        structure.setRespTime(date);
	        structure.setCctId(cct.getId());
	        discussionDAO.save(structure);
	        
   
            InfoObject obj = new InfoObject();
            obj.getDiscussion().setWorkflowId(workflowId);
            obj.setRespTime(date);
            discussionDAO.save(obj);
            
            structure.getInfoObjects().add(obj);

	        discussionDAO.save(structure);
	        
	        return structure;
	 }//publish()


	 public void createReportVote(Long suiteId, boolean vote) throws Exception {
		 Long userId = WebUtils.currentUserId();
		 reportDAO.createReportVote(suiteId, vote, userId);
	 }
	
	 
	 public boolean getUserVoted(Long suiteId) throws Exception {
		 Long userId = WebUtils.currentUserId();
		 return reportDAO.getUserVoted(suiteId, userId);
	 }
	 
	 public Map getVoteStats(Long suiteId) throws Exception {
		 return reportDAO.getVoteStats(suiteId);
	 }
	 
}
