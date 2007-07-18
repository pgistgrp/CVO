package org.pgist.lm;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.projects.ProjectAlternative;


/**
 * 
 * @author John
 *
 */
public class LmAltAction extends Action {
	
	private LmService lmService;
	
    
    public void setLmService(LmService lmService) {
		this.lmService = lmService;
	}
    
    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response
    ) throws java.lang.Exception {
        
    	String strAltId = request.getParameter("altId");
    	
        if (strAltId==null || "".equals(strAltId.trim())) {
            request.setAttribute("error", "altId cannot be empty");
            return mapping.findForward("main");
        }
        
        Long altId = Long.parseLong(strAltId);
        ProjectAlternative alternative = lmService.getAlt(altId);
        
        request.setAttribute("alt", alternative);
        return mapping.findForward("main");
    }//execute()
    
    
}//class LmAltAction
