package org.pgist.system;

import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.users.User;
import org.pgist.users.UserInfo;


/**
 * Public profile
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
     *   <li>user - string, username.</li>

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
    	        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("user");
    	        request.setAttribute("baseuser", userInfo);
        		
    			User u = profileService.getUserInfo(username);
    			Date date = profileService.getLastLogin(username);
    			Collection concerns = profileService.getUserConcerns(username);
    			String strDate = "";
    			if(date != null) {
    				strDate = "" + DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(date);
    			}
    			int post = profileService.getPostCount(username);
    			int visits = profileService.getTotalVisits(username);
    			//Collection discussions = profileService.getUserDiscussion(username);
    			String[] tags = profileService.getAllTags(username);
    			Set criterias = profileService.getUserCriteria(username);
    			
    			request.setAttribute("user", u);
    			request.setAttribute("tags", tags);
    			request.setAttribute("concerns", concerns);
    			request.setAttribute("criterias", criterias);
    			//request.setAttribute("discussions", discussions);
    			request.setAttribute("lastlogin", strDate);
    			request.setAttribute("post", post);
        		request.setAttribute("visits", visits);
        		return mapping.findForward("publicprofile");
        		
            } catch (Exception e) {
                e.printStackTrace();
            	User user = new User();
            	user.setLoginname("Unkown User");
            	request.setAttribute("username", user);
                return mapping.findForward("publicprofile");
            }
    		

    }//execute()
    
    
}//class UserProfileAction
