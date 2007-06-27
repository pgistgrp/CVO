package org.pgist.report;

import java.util.Collection;
import java.util.Date;

import org.pgist.criteria.CriteriaRef;
import org.pgist.criteria.CriteriaSuite;
import org.pgist.cvo.CCT;
import org.pgist.discussion.InfoObject;
import org.pgist.discussion.InfoStructure;
import org.pgist.packages.ClusteredPackage;
import org.pgist.packages.PackageSuite;


public class ReportServiceImpl implements ReportService{
	
	
	private ReportDAO reportDAO;

	
	public void setReportDAO(ReportDAO reportDAO) {
		this.reportDAO = reportDAO;
	}
	
	
	public void createStatistics(Long workflowId, Long cctId, Long projSuiteId, Long fundSuiteId, Long critSuiteId, Long repoSuiteId, Long projISID, Long fundISID) throws Exception {
		reportDAO.createStatistics(workflowId, cctId, projSuiteId, fundSuiteId, critSuiteId, repoSuiteId, projISID, fundISID);
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
	
	
	public void editReportSummary(Long reportSummaryId, String executiveSummary, String participantsSummary, String concernSummary, String criteriaSummary, String projectSummary, String packageSummary) throws Exception {
		reportDAO.editReportSummary(reportSummaryId, executiveSummary, participantsSummary, concernSummary, criteriaSummary, projectSummary, packageSummary);
	}
	
	
	public ReportSuite createReportSuite() throws Exception {
		return reportDAO.createReportSuite();
	}
	
	
	 public InfoStructure publish(Long workflowId, Long cctId, Long suiteId, String title) throws Exception {
	        //create publish function if needed
	        return null;
	    }//publish()
	
}
