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
    	boolean valid = registerService.validatePasswordRecoveryCode(code);
        
    	if(valid) {
    		
    		return mapping.findForward("recoverpassword");
    	}
    	
        return mapping.findForward("recoverpassword");
    }//execute()
    
    
}//class RecoverPasswordAction
