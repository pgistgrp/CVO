package org.pgist.system;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.users.User;
import org.pgist.util.WebUtils;


/**
 * 
 * @author kenny
 *
 */
public class UsercpAction extends Action {
	
	public UsercpAction() {
    }
	
	
	private SystemService systemService;
	private UserDAO userDAO;
	
	
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
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
		User userInfo = systemService.getCurrentUser();
    	request.setAttribute("user", userInfo);
    	
		if (!uform.isSave()) return mapping.findForward("usercp");
		User user = uform.getUser();
		
	    //Check form input
        String cpassword = uform.getCurrentpassword();
        if (cpassword==null || "".equals(cpassword)) {
            uform.setReason("Current Password is Required" + cpassword);
            return mapping.findForward("usercp");
        }
        
        String email = user.getEmail();
        if (email==null || "".equals(email)) {
            uform.setReason("Email is Required.");
            return mapping.findForward("usercp");
        } else if (email.indexOf("@")== -1 || email.indexOf(".")==-1){
        	uform.setReason("Please Enter a valid Email Address.");
            return mapping.findForward("usercp");
        }  
        
        String password = user.getPassword();
        String password1 = uform.getPassword1();
        /*
		
        if (password==null || "".equals(password)) {
            uform.setReason("Password is Required.");
            return mapping.findForward("usercp");
        }
        
        if (password1==null || "".equals(password1)) {
            uform.setReason("Re-type Password is Required.");
            return mapping.findForward("usercp");
        }
        */
        
        if (!password.equals(password1)) {
            uform.setReason("Both Password Fields Must Match.");
            return mapping.findForward("usercp");
        }

        //check if current password is correct before save
        User currentUser = userDAO.getUserById(WebUtils.currentUserId(), true, false);
        
		if(currentUser!=null && currentUser.checkPassword(cpassword)) {
			currentUser.setEmail(email);
			//if password field left blank then don't update password
			if(password!=null || !("".equals(password))) {
				currentUser.setPassword(password);
			}
			userDAO.editUser(currentUser); //use this to update user
            
            request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
            
			return mapping.findForward("main");
		} else {
			uform.setReason("Current Password is Incorrect." + cpassword);
		}
    	
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        
        return mapping.findForward("usercp");
    }//execute()
    
    
}//class UsercpAction
