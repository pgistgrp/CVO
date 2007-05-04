package org.pgist.system;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.directwebremoting.WebContextFactory;
import org.pgist.criteria.Criteria;
import org.pgist.criteria.CriteriaService;
import org.pgist.cvo.CCTService;
import org.pgist.funding.FundingService;
import org.pgist.funding.UserTaxInfoDTO;
import org.pgist.users.User;
import org.pgist.users.Vehicle;
import org.pgist.util.PageSetting;
import org.pgist.util.WebUtils;


/**
 * DWR AJAX Agent class.<br>
 * Provide AJAX services to client programs.<br>
 * In this document, all the NON-AJAX methods are marked out. So all methods
 * <span style="color:red;">without</span> such a description
 * <span style="color:red;">ARE</span> AJAX service methods.<br>
 *
 * @author John
 *
 */
public class ProfileAgent {

	private ProfileService profileService;
	
	public void setProfileService(ProfileService profileService) {
        this.profileService = profileService;
    }
	
	/**
     * Get Public User Information
     * 
     * @param params a Map contains:
     *   <ul>
     *     <li>username - string, user's login name</li>
     *   </ul>
     * @return a Map contains:
     *   <ul>
     *     <li>user - user object containing only public information</li>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map getUserInfo(HttpServletRequest request, Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        String username = (String) params.get("username");
    	
    	if(username==null || "".equals(username.trim())){
    		map.put("reason", "username cannot be blank.");
    		return map;
    	}
    	
        try {
        	
        	User user = profileService.getUserInfo(username);
            request.setAttribute("user", user);
            map.put("user", user);
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//getUserInfo()
    
    
	/**
     * Verify the current user
     * 
     * @param params a Map contains:
     *   <ul>
     *     <li>username - string, user's login name</li>
     *   </ul>
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map getUserVerify(HttpServletRequest request, Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        String username = (String) params.get("username");
    	
    	if(username==null || "".equals(username.trim())){
    		map.put("reason", "income cannot be blank.");
    		return map;
    	}
    	
        try {
        	//complete
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }
    
    
	/**
     * set or save the user info
     * 
     * @param params a Map contains:
     *   <ul>
     *     <li>username - string, user's login name</li>
     *     <li>homecity - name of the city they live in</li>
     *     <li>homezipcode - zipcode of the city they live in</li>
     *     <li>workcity - name of the city they work in</li>
     *     <li>workzipcode - zipcode of the city they work in</li>
     *     <li>vocation - name of their job/vocation</li>
     *     <li>primarytransport - User's primary method of transportation</li>
     *     <li>profiledesc - description of user profile</li>
     *   </ul>
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map setUserInfo(HttpServletRequest request, Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        String username = (String) params.get("username");
        String homecity = (String) params.get("homecity");
        String homezipcode = (String) params.get("homezipcode");
        String workcity = (String) params.get("workcity");
        String workzipcode = (String) params.get("workzipcode");
        String vocation = (String) params.get("vocation");
        String primarytransport = (String) params.get("primarytransport");
        String profiledesc = (String) params.get("profiledesc");
        
    	if(username==null || "".equals(username.trim())){
    		map.put("reason", "income cannot be blank.");
    		return map;
    	}
    	if(homecity==null || "".equals(homecity.trim())){
    		map.put("reason", "homecity cannot be blank.");
    		return map;
    	}
    	if(homezipcode==null || "".equals(homezipcode.trim())){
    		map.put("reason", "homezipcode cannot be blank.");
    		return map;
    	}
    	if(workcity==null || "".equals(workcity.trim())){
    		workcity = "";
    	}
    	if(workzipcode==null || "".equals(workzipcode.trim())){
    		workzipcode = "";
    	}
    	if(vocation==null || "".equals(vocation.trim())){
    		vocation = "";
    	}
    	if(primarytransport==null || "".equals(primarytransport.trim())){
    		primarytransport = "";
    	}
    	if(profiledesc==null || "".equals(profiledesc.trim())){
    		profiledesc = "";
    	}
    	
        try {
        	boolean bool_success = profileService.setUserInfo(username, homecity, homezipcode, workcity, workzipcode, vocation, primarytransport, profiledesc);
        	if(!bool_success) {
        		map.put("reason", "username does not match.");
        	}
        	map.put("successful", bool_success);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }
    
    
	/**
     * Get discussion post
     * 
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map getDiscussionPost(HttpServletRequest request, Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
       
        try {
        	profileService.getDiscussionPost();
        	
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }
} //ProfileAgent()
