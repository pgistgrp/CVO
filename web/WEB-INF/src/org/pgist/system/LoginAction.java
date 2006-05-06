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
        if (loginname==null || "".equals(loginname)) {
            return mapping.findForward("login");
        }
        
        String password = uform.getUser().getPassword();
        if (password==null || "".equals(password)) {
            return mapping.findForward("login");
        }
        
        User user = userDAO.getUserByName(loginname, true, false);
        if (user!=null && user.checkPassword(password)) {
            session = request.getSession(true);
            
            UserInfo userInfo = new UserInfo(user);
            session.setAttribute("user", userInfo);
            WebUtils.setCurrentUser(userInfo);
            
            return mapping.findForward("main");
        }
        
        return mapping.findForward("login");
    }//execute()
    
    
}//class LoginAction
