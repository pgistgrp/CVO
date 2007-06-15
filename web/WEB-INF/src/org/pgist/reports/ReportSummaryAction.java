package org.pgist.reports;

import java.util.Collection;
import java.util.Iterator;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class ReportSummaryAction extends Action  {

	 public ActionForward execute(
	            ActionMapping mapping,
	            ActionForm form,
	            javax.servlet.http.HttpServletRequest request,
	            javax.servlet.http.HttpServletResponse response
	    ) throws java.lang.Exception {
	    	
		 	ReportForm rForm = (ReportForm) form;
		 	
		 	String pkgSuiteId = request.getParameter("pkgSuiteId");
		 	//load pkgsuite
		 	//get reportsummary class out og pkgsuite
		 	//set reportsummary request.setAttribute("rs", rs);
		 	
		 	if (!rForm.isSave()) return mapping.findForward("reportsummary");
		 	
		 	
		 	String executiveSummary = rForm.getExecutiveSummary();
		 	String participantsSummary = rForm.getParticipantsSummary();
		 	String concernSummary = rForm.getConcernSummary();
		 	String criteriaSummary = rForm.getCriteriaSummary();
		 	String projectSummary = rForm.getProjectSummary();
		 	String packageSummary = rForm.getPackageSummary();
		 	
		 	
		 	
	    	
	        return mapping.findForward("reportsummary");
	    }//execute()
}
