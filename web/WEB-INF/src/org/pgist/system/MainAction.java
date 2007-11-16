package org.pgist.system;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.users.User;
import org.pgist.users.UserInfo;
import org.pgist.util.WebUtils;


/**
 * 
 * @author kenny
 *
 */
public class MainAction extends Action {

    
    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response
    ) throws java.lang.Exception {
        
        return mapping.findForward("main");
    }//execute()
    
    
}//class LoginAction
