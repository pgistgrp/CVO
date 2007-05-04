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
public class ProfileAction extends Action {
	
	public ProfileAction() {
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
    	request.setAttribute("user", userInfo);
    	
		if (!uform.isSave()) return mapping.findForward("usercp");
		
		String address1 = uform.getAddress1();
		String address2 = uform.getAddress2();
		String state = uform.getState();
		String homeCity = uform.getCity();
		String homeZipcode = uform.getZipcode();
		String workCity = uform.getWorkCity();
		String workZipcode = uform.getWorkZipcode();
		String vocation = uform.getVocation();
		String primaryTransport = uform.getPrimaryTransport();
		String profileDesc = uform.getProfileDesc();

        if (address1==null || "".equals(address1)) {
            uform.setReason("Address is Required");
            return mapping.findForward("usercp");
        }

        if (address2==null || "".equals(address2)) {
        	address2 = "";
        }
        
        if (state==null || "".equals(state)) {
            uform.setReason("state is Required");
            return mapping.findForward("usercp");
        }
        
        if (state.length()>2) {
            uform.setReason("Please use a two digit state code.");
            return mapping.findForward("usercp");
        }
        
        if (homeCity==null || "".equals(homeCity)) {
            uform.setReason("Home Location City is Required");
            return mapping.findForward("usercp");
        }
        
        if (homeZipcode==null || "".equals(homeZipcode)) {
            uform.setReason("Home Location Zipcode is Required");
            return mapping.findForward("usercp");
        }
        
        if (workCity==null || "".equals(workCity)) {
        	workCity = "";
        }
        
        if (workZipcode==null || "".equals(workZipcode)) {
        	workZipcode = "";
        }
        
		if (vocation==null || "".equals(vocation)) {
			vocation = "";
        }
		
		if (primaryTransport==null || "".equals(primaryTransport)) {
			primaryTransport = "";
        }
		
		if (profileDesc==null || "".equals(profileDesc)) {
			profileDesc = "";
        }
        
        
        try {
            systemService.editCurrentUser(address1, address2, state, homeCity, homeZipcode, workCity, workZipcode, vocation, primaryTransport, profileDesc);	            
            uform.setReason("Your Profile Information has been updated.");
            request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
            return mapping.findForward("usercp"); //Maybe redirect to different page  
        } catch (Exception e) {
            uform.setReason("A system error occurred while editing the current user");
        }
		
		
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", false);
        
        return mapping.findForward("usercp");
    }//execute()
    
    
}//class UsercpAction
