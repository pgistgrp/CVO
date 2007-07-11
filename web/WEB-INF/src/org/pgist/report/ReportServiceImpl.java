package org.pgist.report;

import java.util.Collection;
import java.util.Date;

import org.pgist.criteria.CriteriaRef;
import org.pgist.criteria.CriteriaSuite;
import org.pgist.cvo.CCT;
import org.pgist.cvo.CCTDAO;
import org.pgist.discussion.DiscussionDAO;
import org.pgist.discussion.InfoObject;
import org.pgist.discussion.InfoStructure;
import org.pgist.packages.ClusteredPackage;
import org.pgist.packages.PackageSuite;


public class ReportServiceImpl implements ReportService{
	
	
	private ReportDAO reportDAO;
	
	
	private DiscussionDAO discussionDAO;
	
	
	public void setDiscussionDAO(DiscussionDAO discussionDAO) {
		this.discussionDAO = discussionDAO;
	}
	
	
	public void setReportDAO(ReportDAO reportDAO) {
		this.reportDAO = reportDAO;
	}
	
	
	public ReportStats createStatistics(Long workflowId, Long cctId, Long projSuiteId, Long fundSuiteId, Long critSuiteId, Long repoSuiteId, Long projISID, Long fundISID) throws Exception {
		return reportDAO.createStatistics(workflowId, cctId, projSuiteId, fundSuiteId, critSuiteId, repoSuiteId, projISID, fundISID);
	}
	
	
	public ClusteredPackage getPreferredClusteredPackage(Long pkgSuiteId) throws Exception {
		return reportDAO.getPreferredClusteredPackage(pkgSuiteId);
	}
	
	
	public Collection getVoteSuiteStats(Long pkgSuiteId) throws Exception {
		return reportDAO.getVoteSuiteStats(pkgSuiteId);
	}

	
	public ReportSuite getReportSuiteById(Long id) throws Exception {
		return reportDAO.getReportSuiteById(id);
	}
	
	
	public void editReportSummary(Long reportSummaryId, String executiveSummary, String participantsSummary, String concernSummary, String criteriaSummary, String projectSummary, String packageSummary, boolean finalized) throws Exception {
		reportDAO.editReportSummary(reportSummaryId, executiveSummary, participantsSummary, concernSummary, criteriaSummary, projectSummary, packageSummary, finalized);
	}
	
	
	public ReportSuite createReportSuite() throws Exception {
		return reportDAO.createReportSuite();
	}
	
	
	 public InfoStructure publish(Long workflowId, Long cctId, Long suiteId, String title) throws Exception {


	        CCT cct = (CCT)reportDAO.load(CCT.class, cctId);
	        
	        ReportSuite suite = reportDAO.getReportSuiteById(suiteId);
	        
	        Date date = new Date();
	        
	        /*
	        Long checkId = criteriaDAO.checkPublished(cctId);
	        InfoStructure structure = null;
	        
	        if(checkId == null) {         
		        structure = new InfoStructure();
	            structure.getDiscussion().setWorkflowId(workflowId);
	        } else {
	        	structure = (InfoStructure) criteriaDAO.load(InfoStructure.class, checkId);
	        	structure.deleteInfoObjects();
	        }
	        */
	        
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



	
}
