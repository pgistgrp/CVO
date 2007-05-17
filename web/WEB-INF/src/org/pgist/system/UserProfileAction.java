package org.pgist.system;

import java.text.DateFormat;
import java.util.Date;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.exceptions.UserExistException;
import org.pgist.users.User;
import org.pgist.util.WebUtils;


/**
 * Register Action.
 * 
 * @author John
 *
 */
public class UserProfileAction extends Action {

    
    private SystemService systemService;
    
    private ProfileService profileService;
    
    public UserProfileAction() {
    }
    
    
    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }
    
    public void setProfileService(ProfileService profileService) {
        this.profileService = profileService;
    }
    
    /*
     * ------------------------------------------------------------------------
     */

    
    /**
     * When call this action, the following parameters are required:<br>
     * <ul>
     *   <li>save           - string, the only valid value is "true". It means to save the given information to a new User object. Any other value will turn the page to register.jsp again.</li>

     * </ul>
     */
    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response
    ) throws java.lang.Exception {

    	
    		String username = request.getParameter("user");
    		
    		try {
        		
    			User u = profileService.getUserInfo(username);
    			Date date = profileService.getLastLogin(username);
    			String strDate = "" + DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(date);
    			int post = profileService.getPostCount(username);
    			int visits = profileService.getTotalVisits(username);
    			
    			request.setAttribute("user", u);
    			request.setAttribute("lastlogin", strDate);
    			request.setAttribute("post", post);
        		request.setAttribute("visits", visits);
        		return mapping.findForward("publicprofile");
        		
            } catch (Exception e) {
            	User user = new User();
            	user.setLoginname("Unkown User");
            	request.setAttribute("username", user);
                return mapping.findForward("publicprofile");
            }
    		

    }//execute()
    
    
}//class UserProfileAction
