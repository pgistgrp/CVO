package org.pgist.system;

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

    
    private UserDAO userDAO;
    
    
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    
    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response
    ) throws java.lang.Exception {
        //Invalidate the Session
        HttpSession session = request.getSession(false);
        if (session!=null) session.invalidate();
        
        UserForm uform = (UserForm) form;
        
        String loginname = uform.getUser().getLoginname();
        String password = uform.getUser().getPassword();
        
        if((loginname==null || "".equals(loginname)) && !(password==null || "".equals(password))){
        	uform.setReason("Please Enter a User Name.");  
        	return mapping.findForward("login");
        } else if(!(loginname==null || "".equals(loginname)) && (password==null || "".equals(password))){
        	uform.setReason("Please Enter a Password.");
        	return mapping.findForward("login");
        }
        
        if (loginname==null || "".equals(loginname)) {
            return mapping.findForward("login");
        }
        
        
        if (password==null || "".equals(password)) {
            return mapping.findForward("login");
        }
        
        User user = userDAO.getUserByName(loginname, true, false);
        if(user == null) {
        	uform.setReason("Invalid User Name.");
        	return mapping.findForward("login");
        }
        
        if (user!=null && user.checkPassword(password)) {
            session = request.getSession(true);
            
            UserInfo userInfo = new UserInfo(user);
            request.setAttribute("baseuser", userInfo);
            session.setAttribute("user", userInfo);
            WebUtils.setCurrentUser(userInfo);
            
            return mapping.findForward("main");
        } else if(!user.checkPassword(password)){
        	uform.setReason("Your Password is Invalid. Please Try Again.");
        	return mapping.findForward("login");
        } 
        
        uform.setReason("An unknown error occured. Please email pgistfeedback@gmail.com");
        return mapping.findForward("login");
    }//execute()
    
    
}//class LoginAction
