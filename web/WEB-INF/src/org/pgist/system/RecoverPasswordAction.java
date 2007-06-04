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
     * When call this action, the following parameters are required:<br>
     * <ul>
     *   <li>rc - string, recovery code</li>

     * </ul>
     */
    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response
    ) throws java.lang.Exception {
    	String code = request.getParameter("rc");
    	String update = request.getParameter("update");
    	
    	boolean valid = registerService.validatePasswordRecoveryCode(code);
    	boolean bool_update = false;
    	
    	if(update.equals("true")){
    		bool_update = true;  		
    	}
    	
    	if(valid) {
    		
    		request.setAttribute("valid", true);
    		request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
    		return mapping.findForward("recoverpassword");
    	}
    	if(valid && bool_update) {
    		String password1 = request.getParameter("password1");
    		String password2 = request.getParameter("password2");
    		
    		if(password1.equals(password2)) {
    			registerService.changePassword(code, password1);
    			request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
    			return mapping.findForward("main");
    		}
    		request.setAttribute("error", "passwords do not match");
    		request.setAttribute("PGIST_SERVICE_SUCCESSFUL", false);
    		return mapping.findForward("recoverpassword"); 
    	}
    	
    	request.setAttribute("valid", false);
    	request.setAttribute("PGIST_SERVICE_SUCCESSFUL", false);
        return mapping.findForward("recoverpassword");
    }//execute()
    
    
}//class RecoverPasswordAction
