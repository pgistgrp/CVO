package org.pgist.system;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * Register Action.
 * 
 * @author John
 *
 */
public class RegisterAction extends Action {

    
    private SystemService systemService;
    
    
    public RegisterAction() {
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
     *   <li>save - string, the only valid value is "true". It means to save the given information to a new User object. Any other value will turn the page to register.jsp again.</li>

     * </ul>
     */
    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response
    ) throws java.lang.Exception {
        return mapping.findForward("register");
    }//execute()
    
    
}//class RegisterAction
