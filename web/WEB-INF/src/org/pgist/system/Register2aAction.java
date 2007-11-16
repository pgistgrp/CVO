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
public class Register2aAction extends Action {

    
    private SystemService systemService;
    
    private RegisterService registerService;
    
    public Register2aAction() {
    }
    
    
    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
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
    		User user = systemService.getUserById(id);
    		boolean qualify = registerService.createQuotaQualify(id);
    		
    		if(user.getRegComplete()){
    			return mapping.findForward("main");
    		}
    		
    		if(qualify) {
    			return mapping.findForward("register2a");
    		}
    		
        } catch (Exception e) {
            return mapping.findForward("register");
        }
        
        return mapping.findForward("register");
        
    }//execute()
    
    
}//class Register2aAction
