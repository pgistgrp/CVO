package org.pgist.system;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.exceptions.UserExistException;
import org.pgist.users.User;
import org.pgist.util.WebUtils;


/**
 * Register Questionnaire Action.
 * 
 * @author kenny
 *
 */
public class Register2bAction extends Action {

    
    private SystemService systemService;
    
    
    public Register2bAction() {
    }
    
    
    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */

    
    /**
     * When call this action, the following parameters are required:<br>
     * <ul>
     * </ul>
     */
    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response
    ) throws java.lang.Exception {
    	
    	
    	try {
    		Long id = WebUtils.currentUserId();
        } catch (Exception e) {
            return mapping.findForward("register");
        }
        return mapping.findForward("register2b");
    }//execute()
    
    
}//class Register2bAction
