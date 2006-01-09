package org.pgist.action;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.dao.UserDAO;
import org.pgist.users.User;


public class LoginAction extends Action {

    
    private UserDAO userDAO;
    
    
    public LoginAction() {
    }
    
    
    public UserDAO getUserDAO() {
        return userDAO;
    }


    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    
    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response
    ) throws java.lang.Exception {
        String loginname = request.getParameter("loginname");
        if (loginname==null || "".equals(loginname)) {
            return mapping.findForward("login");
        }
        
        String password = request.getParameter("password");
        if (password==null || "".equals(password)) {
            return mapping.findForward("login");
        }
        
        User user = userDAO.getUserByName(loginname, true, false);
        if (user!=null && user.checkPassword(password)) {
            return mapping.findForward("main");
        }
        
        return mapping.findForward("login");
    }//execute()
    
    
}
