package org.pgist.report;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class ReportSummaryAction extends Action  {
	
	public ReportSummaryAction() {			
	}
	
	 private ReportService reportService;
	
	 public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	public ActionForward execute(
	            ActionMapping mapping,
	            ActionForm form,
	            javax.servlet.http.HttpServletRequest request,
	            javax.servlet.http.HttpServletResponse response
	    ) throws java.lang.Exception {
	    	
		 	ReportForm rForm = (ReportForm) form;
		 	
		 	String strReportSuiteId = request.getParameter("suite_id");
		 	
		 	
		 	
		 	if (strReportSuiteId==null || "".equals(strReportSuiteId)) { 
		 		rForm.setReason("suite_id is Required.");
	            return mapping.findForward("reportsummary");
	        }
		 	
		 	Long reportSuiteId = Long.parseLong(strReportSuiteId);
		 
		 	ReportSuite rs = reportService.getReportSuiteById(reportSuiteId);
		 	ReportSummary rSummary = rs.getReportSummary();
		 	
		 	request.setAttribute("rSummary", rSummary);
		 	
		 	request.setAttribute("suite_id", reportSuiteId);
		 	
		 	if (!rForm.isSave()) return mapping.findForward("reportsummary");
		 	
		 	//get variables
		 	String executiveSummary = rForm.getExecutiveSummary();
		 	String participantsSummary = rForm.getParticipantsSummary();
		 	String concernSummary = rForm.getConcernSummary();
		 	String criteriaSummary = rForm.getCriteriaSummary();
		 	String projectSummary = rForm.getProjectSummary();
		 	String packageSummary = rForm.getPackageSummary();
		 	boolean finalized = rForm.isFinalized();
		 	
		 	reportService.editReportSummary(rSummary.getId(), executiveSummary, participantsSummary, concernSummary, criteriaSummary, projectSummary, packageSummary, finalized);
		 	//reportService.createReportSuite();
		 	rForm.setReason("Changes have been updated.");
	    	
	        return mapping.findForward("reportsummary");
	    }//execute()
}
