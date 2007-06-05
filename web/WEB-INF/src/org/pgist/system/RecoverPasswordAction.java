package org.pgist.system;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.exceptions.UserExistException;
import org.pgist.users.User;


/**
 * RecoverPasswordAction
 * 
 * @author John
 *
 */
public class RecoverPasswordAction extends Action {

    
    private RegisterService registerService;

	public RecoverPasswordAction() {
    }
    
    public void setRegisterService(RegisterService registerService) {
		this.registerService = registerService;
	}
    
    /*
     * ------------------------------------------------------------------------
     */

    
    /**
     */
    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response
    ) throws java.lang.Exception {
    	
    	UserForm uform = (UserForm) form;
    	
    	if (uform.isSave()){
	    		String email = uform.getEmail();
	    		if(email==null || "".equals(email.trim())) {	
		    		request.setAttribute("sysmsg", "Email address cannot be empty.");
		    		request.setAttribute("PGIST_SERVICE_SUCCESSFUL", false);
		    		return mapping.findForward("recoverpassword"); 
	    		}
	    		boolean emailsent = registerService.createPasswordRecovery(email);
	    		if(emailsent) {
		    		request.setAttribute("sysmsg", "An email has been sent.");	
		    		request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
	    		} else {
	    			request.setAttribute("sysmsg", "Your email address was not found in the system.");	
		    		request.setAttribute("PGIST_SERVICE_SUCCESSFUL", false);
	    		}
	    		return mapping.findForward("recoverpassword"); 
    	}
    	
    	request.setAttribute("PGIST_SERVICE_SUCCESSFUL", false);
        return mapping.findForward("recoverpassword");
    }//execute()
    
    
}//class RecoverPasswordAction
