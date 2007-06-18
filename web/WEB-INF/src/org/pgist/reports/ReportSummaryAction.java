package org.pgist.reports;

import java.util.Collection;
import java.util.Iterator;

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
		 	
		 	String strReportSuiteId = request.getParameter("reportSuiteId");
		 	
		 	if (strReportSuiteId==null || "".equals(strReportSuiteId)) {
		 		rForm.setReason("reportSuiteId is Required.");
	            return mapping.findForward("reportsummary");
	        }
		 	
		 	Long reportSuiteId = Long.parseLong(strReportSuiteId);
		 
		 	ReportSuite rs = reportService.getReportSuiteById(reportSuiteId);
		 	ReportSummary rSummary = rs.getReportSummary();
		 	
		 	request.setAttribute("rSummary", rSummary);
		 	
		 	if (!rForm.isSave()) return mapping.findForward("reportsummary");
		 	
		 	//get variables
		 	String executiveSummary = rForm.getExecutiveSummary();
		 	String participantsSummary = rForm.getParticipantsSummary();
		 	String concernSummary = rForm.getConcernSummary();
		 	String criteriaSummary = rForm.getCriteriaSummary();
		 	String projectSummary = rForm.getProjectSummary();
		 	String packageSummary = rForm.getPackageSummary();
		 	
		 	//save 
		 	reportService.editReportSummary(rSummary.getId(), executiveSummary, participantsSummary, concernSummary, criteriaSummary, projectSummary, packageSummary);
		 	
	    	
	        return mapping.findForward("reportsummary");
	    }//execute()
}
