package org.pgist.system;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class LogoutAction extends Action {

    
    public LogoutAction() {
    }
    
    
    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response
    ) throws java.lang.Exception {
        
        //Invalidate the Session
        HttpSession session = request.getSession(false);
        
        if (session!=null) {
            session.setAttribute("user", null);
            session.invalidate();
        }
        
        Cookie[] cookies = request.getCookies();
        
        if (cookies!=null) {
            for (Cookie cookie : cookies) {
                if ("PG_INIT_URL".equals(cookie.getName())) {
                    /*
                     * Remove the cookie
                     */
                    cookie.setValue("");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }//for
        }
        
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        
        return mapping.findForward("loginAction");
        
    }//execute()
    
    
}//class LogoutAction
