package org.pgist.system;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.system.SystemService;
import org.pgist.users.User;

/**
 * 
 * @author John
 *
 */



public class UserHomeAction extends Action {

	private SystemService systemService;
	
    public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response
    ) throws java.lang.Exception {
        
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        User u = systemService.getCurrentUser();
        if(u.getWebQ() != null) {
        	request.setAttribute("webq", u.getWebQ().getValue());
        }
        return mapping.findForward("userhome");
    }//execute()
    
    
}//class UserHomeAction
