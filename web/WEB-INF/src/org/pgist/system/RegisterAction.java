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
    
    
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */

    
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
            uform.setReason("Preferred LIT ID is required.");
            return mapping.findForward("register");
        }
        
        String password = user.getPassword();
        if (password==null || "".equals(password)) {
            uform.setReason("Password is required.");
            return mapping.findForward("register");
        }
        
        String password1 = uform.getPassword1();
        if (password1==null || "".equals(password1)) {
            uform.setReason("Re-type Password is required.");
            return mapping.findForward("register");
        }
        
        if (!password.equals(password1)) {
            uform.setReason("Re-type Password must conform to Password.");
            return mapping.findForward("register");
        }
        
        String firstname = user.getFirstname();
        if (firstname==null || "".equals(firstname)) {
            uform.setReason("First Name is required.");
            return mapping.findForward("register");
        }
        
        String lastname = user.getLastname();
        if (lastname==null || "".equals(lastname)) {
            uform.setReason("Last Name is required.");
            return mapping.findForward("register");
        }
        
        String email = user.getEmail();
        if (email==null || "".equals(email)) {
            uform.setReason("Email is required.");
            return mapping.findForward("register");
        }
        
        String homeAddr = user.getHomeAddr();
        if (homeAddr==null || "".equals(homeAddr)) {
            uform.setReason("Home Address is required.");
            return mapping.findForward("register");
        }
        
        String city = user.getCity();
        if (city==null || "".equals(city)) {
            uform.setReason("City is required.");
            return mapping.findForward("register");
        }
        
        String state = user.getState();
        if (state==null || "".equals(state)) {
            uform.setReason("State is required.");
            return mapping.findForward("register");
        }
        
        String zipcode = user.getZipcode();
        if (zipcode==null || "".equals(zipcode)) {
            uform.setReason("ZIP/Postal Code is required.");
            return mapping.findForward("register");
        }
        
        String ethnicity = user.getEthnicity();
        if (ethnicity==null || "".equals(ethnicity) || "-1".equals(ethnicity)) {
            uform.setReason("Ethnicity is required.");
            return mapping.findForward("register");
        } else if ("other".equals(ethnicity)) {
            String eth = uform.getEthnicity1();
            if (eth==null || "".equals(eth.trim())) {
                uform.setReason("Ethnicity (other) is required.");
                return mapping.findForward("register");
            }
            user.setEthnicity(eth);
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
