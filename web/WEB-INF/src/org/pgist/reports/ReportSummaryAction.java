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
	    	

	    	
	        return mapping.findForward("reportsummary");
	    }//execute()
}
