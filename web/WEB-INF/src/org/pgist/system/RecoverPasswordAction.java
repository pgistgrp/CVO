package org.pgist.system;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


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
		    		return mapping.findForward("forgotpassword"); 
	    		}
	    		boolean emailsent = registerService.createPasswordRecovery(email);
	    		if(emailsent) {
		    		request.setAttribute("sysmsg", "<span style=\"color: green;\">A password recovery email has been sent to " + email + "</span>");	
		    		request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
	    		} else {
	    			request.setAttribute("sysmsg", "<span style=\"color: red;\">The email address you entered was not found in the system.</span>");	
		    		request.setAttribute("PGIST_SERVICE_SUCCESSFUL", false);
	    		}
	    		return mapping.findForward("forgotpassword"); 
    	}
    	
    	request.setAttribute("PGIST_SERVICE_SUCCESSFUL", false);
        return mapping.findForward("forgotpassword");
    }//execute()
    
    
}//class RecoverPasswordAction
