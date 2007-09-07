package org.pgist.system;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

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
public class LoginAction extends Action {

    
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
        //Invalidate the Session
        HttpSession session = request.getSession(false);
        
        String loginname = request.getParameter("loginname");
        String password = request.getParameter("password");
        
        if((loginname==null || "".equals(loginname)) && !(password==null || "".equals(password))){
            request.setAttribute("reason", "Please Enter a User Name.");  
        	return mapping.findForward("loginPage");
        } else if(!(loginname==null || "".equals(loginname)) && (password==null || "".equals(password))){
            request.setAttribute("reason", "Please Enter a Password.");
        	return mapping.findForward("loginPage");
        }
        
        if (loginname==null || "".equals(loginname)) {
            return mapping.findForward("loginPage");
        }
        
        
        if (password==null || "".equals(password)) {
            return mapping.findForward("loginPage");
        }
        
        User user = systemService.getUserByName(loginname, true, false);
        if(user == null) {
        	//check to see if the account is disabled
        	User user2 = systemService.getUserByName(loginname, false, false);
        	if(user2 != null) {
        		request.setAttribute("reason", "You account has been disabled. Please contact us at <a href=\"mailto:moderator@letsimprovetransportation.org\">moderator@letsimprovetransportation.org</a> for assistance.");
        	} else {
        		request.setAttribute("reason", "Invalid User Name.");
        	}
        	return mapping.findForward("loginPage");
        }
        
        if (user.checkPassword(password)) {
            session = request.getSession(true);
            
            UserInfo userInfo = new UserInfo(user);
            request.setAttribute("baseuser", userInfo);
            session.setAttribute("user", userInfo);
            WebUtils.setCurrentUser(userInfo);
            
            request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
            
            /*
             * Now the user is authenticated and authorized
             */
            
            /*
             * Check if it's a intermediate login
             */
            for (Cookie cookie : request.getCookies()) {
                if ("PG_INIT_URL".equals(cookie.getName())) {
                    String initURL = cookie.getValue();
                    
                    /*
                     * Remove the cookie
                     */
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    
                    if (initURL!=null && initURL.length()>0) {
                        /*
                         * redirect to the initial URL
                         */
                        ActionForward af = new ActionForward(initURL, true);
                        return af;
                    }
                }
            }//for
            
            ActionForward af = new ActionForward(request.getAttribute("httpPrefix")+mapping.findForward("main").getPath(), true);
            return af;
        } else if(!user.checkPassword(password)){
            request.setAttribute("reason", "Your Password is Invalid. Please Try Again.");
        	return mapping.findForward("loginPage");
        }
        
        return mapping.findForward("loginPage");
    }//execute()
    
    
}//class LoginAction
