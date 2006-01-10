package org.pgist.action;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.dao.UserDAO;
import org.pgist.form.UserForm;
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
        
        User user = new User();
        
        String loginname = uform.getLoginname();
        if (loginname==null || "".equals(loginname)) {
            return mapping.findForward("register");
        }
        user.setLoginname(loginname);
        
        String password = uform.getPassword();
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
        
        user.setPassword(password);
        
        String firstname = uform.getFirstname();
        if (firstname==null || "".equals(firstname)) {
            return mapping.findForward("register");
        }
        
        user.setFirstname(firstname);
        
        String lastname = uform.getLastname();
        if (lastname==null || "".equals(lastname)) {
            return mapping.findForward("register");
        }
        
        user.setLastname(lastname);
        
        String email = uform.getEmail();
        if (email==null || "".equals(email)) {
            return mapping.findForward("register");
        }
        
        user.setEmail(email);
        
        user.setDeleted(false);
        user.setEnabled(true);
        user.setInternal(false);
        
        Role role = userDAO.getRoleByName("member");
        user.getRoles().add(role);
        
        userDAO.addUser(user);
        
        return mapping.findForward("login");
    }//execute()
    
    
}
