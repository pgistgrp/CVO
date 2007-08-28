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
			System.out.println("***Start Report Summary Action****");
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
		 	String part1a = rForm.getPart1a();
		 	String part1b = rForm.getPart1b();
		 	String part2a = rForm.getPart2a();
		 	String part3a = rForm.getPart3a();
		 	String part4a = rForm.getPart4a();
		 	
		 		
		 	String finalVoteDate = rForm.getFinalVoteDate();
		 	String finalReportDate = rForm.getFinalReportDate();
		 	boolean finalized = rForm.isFinalized();
		 	
		 	reportService.editReportSummary(rSummary.getId(), executiveSummary, part1a, part1b, part2a, part3a, part4a, finalized, finalVoteDate, finalReportDate);
		 	//reportService.createReportSuite();
		 	rForm.setReason("Changes have been updated.");
	    	
	        return mapping.findForward("reportsummary");
	    }//execute()
}
