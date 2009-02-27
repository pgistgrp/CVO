package org.pgist.system;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.users.User;


/**
 * 
 * @author John
 *
 */
public class UsercpAction extends Action {
	
	public UsercpAction() {
    }
	
	
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
			
		UserForm uform = (UserForm) form;
		User userInfo = systemService.getCurrentUser();
		if(uform.getEmail()==null || uform.getEmail().equals("")){
			uform.setEmailNotify(userInfo.isEmailNotify());
			uform.setEmailNotifyDisc(userInfo.isEmailNotifyDisc());
		}
		request.setAttribute("user", userInfo);
    	request.setAttribute("transtypes", systemService.getTransTypes());
    	
		if (!uform.isSave()) return mapping.findForward("usercp");
	
		String email = uform.getEmail();
		boolean emailNotify = uform.isEmailNotify();
		boolean emailNotifyDisc = uform.isEmailNotifyDisc();
        String password1 = uform.getPassword1();
        String password2 = uform.getPassword2();
        String cpassword = uform.getCurrentpassword();
        
        System.out.println("UsercpAction: " + emailNotify + emailNotifyDisc);
        
        if (email==null || "".equals(email)) {
            uform.setReason("Email is Required.");
            return mapping.findForward("usercp");
        } else if (email.indexOf("@")== -1 || email.indexOf(".")==-1){
        	uform.setReason("Please Enter a valid Email Address.");
            return mapping.findForward("usercp");
        } 

        if ((cpassword==null || "".equals(cpassword)) && (password1==null || "".equals(password1)) && (password2==null || "".equals(password2))) {
        	cpassword="";
        	password1="";
        	password2="";
        } else {
        	if(password1.length() < 6){
        		uform.setReason("Password must be six characters long");
                return mapping.findForward("usercp");
        	}
            if (!password1.equals(password2)) {
                uform.setReason("Both Password Fields Must Match.");
                return mapping.findForward("usercp");
            }
        }
        
        try {
            boolean complete = systemService.editUserSettings(cpassword, password1, email, emailNotify, emailNotifyDisc);
            if(complete) {
            	uform.setReason("Your settings have been updated.");
            } else {
            	uform.setReason("Your Current password is incorrect.");
            }
            request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
            return mapping.findForward("usercp"); //Maybe redirect to different page           
        } catch (Exception e) {
            uform.setReason("Current Password is Incorrect.");
        }
	
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", false);
        
        return mapping.findForward("usercp");
    }//execute()
    
    
}//class UsercpAction
