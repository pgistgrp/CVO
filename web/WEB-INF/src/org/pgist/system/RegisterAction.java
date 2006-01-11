package org.pgist.system;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.users.Role;
import org.pgist.users.User;


public class RegisterAction extends Action {

    
    private UserDAO userDAO;
    
    
    public RegisterAction() {
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
        UserForm uform = (UserForm) form;
        
        if (!uform.isSave()) return mapping.findForward("register");
        
        User user = uform.getUser();
        
        String loginname = user.getLoginname();
        if (loginname==null || "".equals(loginname)) {
            return mapping.findForward("register");
        }
        
        String password = user.getPassword();
        if (password==null || "".equals(password)) {
            return mapping.findForward("register");
        }
        
        String password1 = uform.getPassword1();
        if (password1==null || "".equals(password1)) {
            return mapping.findForward("register");
        }
        
        if (!password.equals(password1)) {
            return mapping.findForward("register");
        }
        
        String firstname = user.getFirstname();
        if (firstname==null || "".equals(firstname)) {
            return mapping.findForward("register");
        }
        
        String lastname = user.getLastname();
        if (lastname==null || "".equals(lastname)) {
            return mapping.findForward("register");
        }
        
        String email = user.getEmail();
        if (email==null || "".equals(email)) {
            return mapping.findForward("register");
        }
        
        user.setDeleted(false);
        user.setEnabled(true);
        user.setInternal(false);
        
        //TODO: Check if user already exists
        
        Role role = userDAO.getRoleByName("member");
        user.addRole(role);
        
        user.encodePassword();
        
        userDAO.saveUser(user);
        
        return mapping.findForward("login");
    }//execute()
    
    
}
