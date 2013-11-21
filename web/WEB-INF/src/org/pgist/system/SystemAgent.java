package org.pgist.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.WebContext;
import org.json.JSONException;
import org.json.JSONObject;
import org.pgist.users.User;
import org.pgist.users.UserInfo;
import org.pgist.util.PageSetting;
import org.pgist.util.WebUtils;

/**
 * DWR AJAX Agent class.<br>
 * Provide AJAX services to client programs.<br>
 * In this document, all the NON-AJAX methods are marked out. So all methods
 * <span style="color:red;">without</span> such a description
 * <span style="color:red;">ARE</span> AJAX service methods.<br>
 *
 * @author kenny
 *
 */
public class SystemAgent {
    
    
    private SystemService systemService;
    
    private EmailSender emailSender;
    
    private RegisterService registerService;
    
    private Properties props = new Properties();
    
    
    /**
     * This is not an AJAX service method.
     *
     * @param systemService
     */
    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }
    
    
    public void setEmailSender(EmailSender emailSender) {
        this.emailSender = emailSender;
    }
    
    public void setRegisterService(RegisterService registerService) {
        this.registerService = registerService;
    }

    public void setProps(Properties props) {
        this.props = props;
    }

    /*  
     * ------------------------------------------------------------------------
     */
    
    
    /**
     * Create a new Feedback for the current user.
     * 
     * @param a map contains:
     *   <ul>
     *     <li>feedback - string, the feedback from the user</li>
     *     <li>action - string, the action where the feedback is created</li>
     *   </ul>
     * 
     * @return a map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map createFeedback(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            String s = (String) params.get("feedback");
            String action = (String) params.get("action");
            
            if (s==null || "".equals(s.trim())) {
                map.put("reason", "The content of feedback can't be empty!");
                return map;
            }
            
            if (action==null || "".equals(action.trim())) {
                map.put("reason", "Unknown Action!");
                return map;
            }
            
            Feedback feedback = systemService.createFeedback(action, s);
            
            try {
                Map values = new HashMap();
                values.put("feedback", feedback);
                
                Collection moderators = systemService.getUsersByRole("moderator");
                
                for (User moderator : (Collection<User>) moderators) {
                    values.put("moderator", moderator);
                    emailSender.send(moderator, "feedback", values);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//createFeedback();
    
    
    /**
     * Get a feedback list
     * 
     * @param a map contains:
     *   <ul>
     *     <li>count - int, the number of feedbacks shown on each page</li>
     *     <li>page - int, current page</li>
     *   </ul>
     * 
     * @return a map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>html - a HTML source segment generated by file "/WEB-INF/jsp/system/feedbacks.jsp", the following variables available:
     *       <ul>
     *         <li>feedbacks - a list of Feedback objects</li>
     *         <li>setting - a PageSetting object</li>
     *       </ul>
     *     </li>
     *   </ul>
     */
    public Map getFeedbacks(Map params, HttpServletRequest request) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            PageSetting setting = new PageSetting();
            setting.setRowOfPage((String) params.get("count"));
            setting.setPage((String) params.get("page"));
            
            Collection feedbacks = systemService.getFeedbacks(setting);
            
            request.setAttribute("feedbacks", feedbacks);
            request.setAttribute("setting", setting);
            
            map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/system/feedbacks.jsp"));
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//getFeedbacks()
    
    /**
     * Get user by name or create one on the fly
     * 
     * @return User
     */
    
    public Map loadUserByName(String loginname, String token, String domain){
    	Map map = new HashMap();
    	try{
	    	User user = systemService.getUserByName(loginname, true, false);
	    	
	    	//For CyberGIS, instantiate new user on the fly
	    	if (user == null & domain.equals("cybergis")){
	    		String [] assocs = {"CyberGIS"};
	    		Long newUserId = registerService.addSarpUser(loginname, loginname, loginname+"@cybergis.org", "0", "M", "0", "0", "", loginname, "cybergis", "", new HashSet(Arrays.asList(assocs)));
	    		user = systemService.getUserById(newUserId);
	    	}else if(user == null & domain.equals("csf")){
	    		String [] assocs = {"CSF"};
	    		Long newUserId = registerService.addSarpUser(loginname, loginname, loginname+"@asu.edu", "0", "M", "0", "0", "", loginname, "csf", "", new HashSet(Arrays.asList(assocs)));
	    		user = systemService.getUserById(newUserId);
	    	}
	    	
	    	/*If a security token is provided verify it, this needs to be made more robust for multiple integration scenarios.
	    	 *Currently specific to CyberGIS.
	    	 */
	    	if (!token.equals("null")){
	    		String result = verifyToken (loginname, token);
	    		JSONObject jsonObject = new JSONObject(result);
	    		String status = jsonObject.getString("status");
	    		
	    		if(status.equals("error")){
	    			map.put("successful", new Boolean(false));
	    			String errorMsg = "CyberGIS SSO failed. " + jsonObject.getJSONObject("result").getString("message");
	        		map.put("error", errorMsg);
	        		return map;
	    		}
	    	}
	    	
	    	//added for ExtJS Implementation because much of the code uses WebUtils to access user
	    	UserInfo userInfo = new UserInfo(user);
	    	WebUtils.setCurrentUser(userInfo);
	    	map.put("successful", new Boolean(true));
    	}
    	catch(Exception e){
    		map.put("successful", new Boolean(false));
    		map.put("error", e.getMessage());
	    }
    	
    	return map;
    }
    
    /**
     * Get a list of all the users
     * 
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>users - array, a list of user objects</li>
     *     <li>html - system_users.jsp</li>
     *   </ul>
     */
	public Map getAllUsers(Map params, HttpServletRequest request) {		
		Map map = new HashMap();
        map.put("successful", false);
		
        try {
        	Collection users = systemService.getAllUsers();
        	/*sort */
 
        	request.setAttribute("users", users);
        	map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/system/system_allusers.jsp"));
        	map.put("users", users);
        	map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
	} //getAllUsers()
	
    
    
    /**
     * Get a list of all affiliations
     * 
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>assocs - array, a list of assoc objects</li>
     *     <li>html - system_users.jsp</li>
     *   </ul>
     */
    public Map getAllAssocs(Map params, HttpServletRequest request) {        
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            Collection users = systemService.getAllUsers();
            /*sort */
 
            request.setAttribute("users", users);
            map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/system/system_allusers.jsp"));
            map.put("users", users);
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    } //getAllUsers()
    
    
	
	 /**
     * Get a user object by giving user Id
     * 
     * @param params a Map contains:
     *   <ul>
     *     <li>id - string, id of the user</li>
     *   </ul>
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>user - object, the object of the user</li>
     *   </ul>
     */
	public Map getUserById(Map params) {
		Map map = new HashMap();
        map.put("successful", false);
		
        String strId = (String)params.get("id");
        
        if(strId==null || "".equals(strId.trim())){
        	map.put("reason", "User id cannot be null.");
    		return map;	
        }
        
        Long id = Long.parseLong(strId);
        
        try {
        	User user = systemService.getUserById(id);
        	
        	map.put("user", user);
        	map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
	} //getUserById()
	
	
	 /**
     * Edit a User
     * 
     * @param params a Map contains:
     *   <ul>
     *     <li>id - string, id of the user</li>
     *     <li>password - string, password for the user</li> 
     *   </ul>
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>user - object, the object of the user</li>
     *   </ul>
     */
	public Map editUser(Map params) {
		Map map = new HashMap();
        map.put("successful", false);
		
        try {

        	map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        return map;	
	} //editUser()

	
	 /**
     * Disables a user account
     * 
     * @param params a Map contains:
     *   <ul>
     *     <li>ids - string list of id separated by a comma. user1, user2, etc</li>
     *   </ul>
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
	public Map disableUsers(Map params) {
		Map map = new HashMap();
        map.put("successful", false);
		
        String strIds = (String)params.get("ids");
        
        if(strIds==null || "".equals(strIds.trim())){
        	map.put("reason", "User id cannot be null.");
    		return map;	
        }
        
        
        try {
        	String[] idList = strIds.split(",");
        	
        	systemService.disableUsers(idList, false);
        	map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        return map;	
	} //disableUser()
	
	
	 /**
     * Delete a user account
     * 
     * @param params a Map contains:
     *   <ul>
     *     <li>id - user id</li>
     *   </ul>
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
	public Map deleteUser(Map params) {
		Map map = new HashMap();
        map.put("successful", false);
		
        String strId = (String)params.get("id");
        
        if(strId==null || "".equals(strId.trim())){
        	map.put("reason", "User id cannot be null.");
    		return map;	
        }
        
        
        try {
        	Long id = Long.parseLong(strId);
        	
        	systemService.deleteUser(id);
        	map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        return map;	
	} //disableUser()
	
	
	 /**
     * enables a user account
     * 
     * @param params a Map contains:
     *   <ul>
     *     <li>id - string, id of the user</li>
     *   </ul>
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
	public Map enableUsers(Map params) {
		Map map = new HashMap();
        map.put("successful", false);
		
        String strIds = (String)params.get("ids");
        strIds = strIds.replaceAll(" ", "");
        if(strIds==null || "".equals(strIds.trim())){
        	map.put("reason", "User id cannot be null.");
    		return map;	
        }
        
        try {
        	String[] idList = strIds.split(",");
        	
        	systemService.disableUsers(idList, true);
        	map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        return map;
	} //enableUser()
	
	
	 /**
     * Gets a list of user accounts that are disabled
     * 
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>users - collection of User objects, the object of the user</li>
     *     <li>html - html file system_user.jsp</li>
     *   </ul>
     */
	public Map getEnabledUsers(Map params, HttpServletRequest request) {
		Map map = new HashMap();
        map.put("successful", false);
		
        try {
        	Collection users = systemService.getEnabledUsers();
        	
        	request.setAttribute("users", users);
        	map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/system/system_users.jsp"));
        	map.put("users", users);
        	map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        return map;	
	} //getEnabledUser()
	
	
	 /**
     * Gets a list of user accounts that are disabled
     * 
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>dusers - collection of User objects, the object of the user</li>
     *     <li>html - html file system_lockeduser.jsp</li>
     *   </ul>
     */
	public Map getDisabledUsers(Map params, HttpServletRequest request) {
		Map map = new HashMap();
        map.put("successful", false);
		
        try {
        	Collection users = systemService.getDisabledUsers();
        	
        	request.setAttribute("dusers", users);
        	map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/system/system_lockedusers.jsp"));
        	map.put("dusers", users);
        	map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        return map;	
	} //getDisabledUser()
	
	
	/**
     * Gets an email list of user emails. Either All users, don't set variables.
     *   <ul>
     *     <li>quota - (optional) boolean, string</li>
     *     <li>nonquota - (optional)boolean, string</li>
     *   </ul>
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>emaillist - string, An email list</li>
     *   </ul>
     */
	public Map getEmailList(Map params) {
		Map map = new HashMap();
        map.put("successful", false);
        boolean bool_quota = false;
        boolean bool_nonquota = false;
        
        String quota = (String)params.get("quota");
        String nonquota = (String)params.get("nonquota");
        
        if(quota.toLowerCase().equals("true")) {
        	bool_quota = true;
        }
        if(nonquota.toLowerCase().equals("true")) {
        	bool_nonquota = true;
        }
        
        try {
        	String emaillist = systemService.getEmailList(bool_quota, bool_nonquota);
        	map.put("emaillist", emaillist);
        	map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        return map;	
	} //getEMailList()
	

	/**
     * Resets a users password
     *   <ul>
     *     <li>ids- string, list of user id's comma separated</li>
     *   </ul>
     * @return a Map contains:
     *   <ul>
     *     <li>password - users new password</li>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
	public Map resetPassword(Map params) {
		Map map = new HashMap();
        map.put("successful", false);
        
        String strIds = (String)params.get("ids");
        strIds = strIds.replaceAll(" ", "");
        
        if(strIds==null || "".equals(strIds.trim())){
        	map.put("reason", "User id cannot be null.");
    		return map;	
        }
        
        try {
        	String[] idList = strIds.split(",");
        	String password = "ppgisLIT";
        	systemService.resetPassword(idList, password);       	
        	
        	map.put("password", password);
        	map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        return map;	
	} //resetPassword();
	
	
	/**
     * Sets the user quota, quota boolean sets it true or false.
     *   <ul>
     *     <li>id - int value, string type</li>
     *     <li>quota - boolean, string type</li>
     *   </ul>
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
	public Map setQuota(Map params) {
		Map map = new HashMap();
        map.put("successful", false);
        boolean quota = false;
        
        String strId = (String)params.get("id");
        String strQuota = (String)params.get("quota");
        
        if(strId==null || "".equals(strId.trim())){
        	map.put("reason", "User id cannot be null.");
    		return map;	
        }
        
        if(strQuota.toLowerCase().equals("true")) {       	
        	quota = true;
        }
        
        try {   
        	Long id = Long.parseLong(strId);
        	systemService.setQuota(id, quota);       	
        	map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        return map;	
	} //setQuota();
	
	
	/**
     * Sets the county quota limit
     *   <ul>
     *     <li>countyId - Long value, string type</li>
     *     <li>limit - int value,</li> 
     *   </ul>
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
	public Map setQuotaLimit(Map params) {
		Map map = new HashMap();
		map.put("successful", false);
		
		String strCountyId = (String)params.get("countyId");
        String strLimit = (String)params.get("limit");
        
        if(strCountyId==null || "".equals(strCountyId.trim())){
        	map.put("reason", "countyId cannot be null.");
    		return map;	
        }
        if(strLimit==null || "".equals(strLimit.trim())){
        	map.put("reason", "limit cannot be null.");
    		return map;	
        }
		
        try {   
        	Long countyId = Long.parseLong(strCountyId);
        	int limit = Integer.parseInt(strLimit);
        	systemService.setQuotaLimit(countyId, limit);
        	map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
		return map;
	} //setQuotaLimit()
	
	
	/**
     * Gets the data necessary for quota statistics like tempQuotaNumber and quotaLimit
     * @return a Map contains:
     *   <ul>
     *     <li>counties - a collection of counties, you can get the name, quotaLimit, and tempQuotaNumber</li>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
	public Map createQuotaStats(Map params, HttpServletRequest request) {
		Map map = new HashMap();
		map.put("successful", false);
		
        try {   
        	Collection county = systemService.createQuotaStats();
        	
        	request.setAttribute("counties", county);
        	map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/system/system_countystats.jsp"));
        	
        	map.put("counties", county);
        	map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
		return map;
	}
	
	
	/**
     * Adds a new county to the system
     * @param params a Map contains:
     *   <ul>
     *     <li>name - string, name of the county</li>
     *   </ul>
     * @return a Map contains:
     *   <ul>
     *    <li>id - id of county</li>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
	public Map addCounty(Map params) {
		Map map = new HashMap();
		map.put("successful", false);
		
        String name = (String)params.get("name");
        
        if(name==null || "".equals(name.trim())){
        	map.put("reason", "name cannot be null.");
    		return map;	
        }
        
        try {   
        	Long id = systemService.addCounty(name);
        	map.put("id", id);
        	map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
		return map;
	}

	
	/**
     * Adds zip codes to a county
     * @param params a Map contains:
     *   <ul>
     *     <li>countyId - countyId, id of the county</li>
     *     <li>zips - zip codes separated by a comma.</li>
     *   </ul>
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
	public Map addZipCodes(Map params) throws Exception {
		Map map = new HashMap();
		map.put("successful", false);
		
		String strCountyId = (String)params.get("countyId");
		String strZips = (String)params.get("zips");
		
        if(strCountyId==null || "".equals(strCountyId.trim())){
        	map.put("reason", "countydd cannot be null.");
    		return map;	
        }
        if(strZips==null || "".equals(strZips.trim())){
        	map.put("reason", "zips cannot be null.");
    		return map;	
        }
        
        try {   
        	Long countyId = (Long) Long.parseLong(strCountyId);
        	String[] zipCodes = strZips.split(",");
        	
        	systemService.addZipCodes(countyId, zipCodes);
        	
        	map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
		return map;
	}
	
	
	/**
     * Edit county name
     * @param params a Map contains:
     *   <ul>
     *     <li>countyId - countyId, id of the county</li>
     *     <li>name - new county name</li>
     *   </ul>
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
	public Map editCountyName(Map params) throws Exception {
		Map map = new HashMap();
		map.put("successful", false);
		
		String strCountyId = (String)params.get("countyId");
		String name = (String)params.get("name");
		
        if(strCountyId==null || "".equals(strCountyId.trim())){
        	map.put("reason", "countyId cannot be null.");
    		return map;	
        }
        if(name==null || "".equals(name.trim())){
        	map.put("reason", "name cannot be null.");
    		return map;	
        }
        
        try {   
        	Long countyId = (Long) Long.parseLong(strCountyId);
        	
        	
        	systemService.editCountyName(countyId, name);
        	
        	map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
		return map;
	}
	
	
	/**
     * Remove Zip codes from a county
     * @param params a Map contains:
     *   <ul>
     *     <li>countyid - countyId, id of the county</li>
     *     <li>zips - zip codes separated by a comma.</li>
     *   </ul>
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
	public Map deleteZipCodes(Map params) throws Exception {
		Map map = new HashMap();
		map.put("successful", false);
		
		String strCountyId = (String)params.get("countyid");
		String strZips = (String)params.get("zips");
		
        if(strCountyId==null || "".equals(strCountyId.trim())){
        	map.put("reason", "countyid cannot be null.");
    		return map;	
        }
        if(strZips==null || "".equals(strZips.trim())){
        	map.put("reason", "zips cannot be null.");
    		return map;	
        }
        
        try {   
        	Long countyId = (Long) Long.parseLong(strCountyId);
        	String[] zipCodes = strZips.split(",");
        	
        	systemService.deleteZipCodes(countyId, zipCodes);
        	
        	map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
		return map;
	}
	
	
	/**
     * Get All Counties
     * @return a Map contains:
     *   <ul>
     *     <li>counties - a collection of counties</li>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
	public Map getAllCounties(Map params, HttpServletRequest request) throws Exception {
		Map map = new HashMap();
		map.put("successful", false);
        
        try {   
        	
        	Collection counties = systemService.getAllCounties();
        	
        	request.setAttribute("counties", counties);
        	map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/system/system_counties.jsp"));
        	
        	map.put("counties", counties);
        	map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
		return map;
	}
	
	
	/**
     * Delete a county
     * @param params a Map contains:
     *   <ul>
     *     <li>countyid - countyId, id of the county</li>
     *   </ul>
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
	public Map deleteCounty(Map params) throws Exception {
		Map map = new HashMap();
		map.put("successful", false);
		
		String strCountyId = (String)params.get("countyid");
		
        if(strCountyId==null || "".equals(strCountyId.trim())){
        	map.put("reason", "countyid cannot be null.");
    		return map;	
        }
        
        try {   
        	Long countyId = (Long) Long.parseLong(strCountyId);
        	
        	systemService.deleteCounty(countyId);
        	
        	map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
		return map;
	}
	
	
	/**
     * Add Announcement
     * @param params a Map contains:
     *   <ul>
     *     <li>workflowId - Id of the workflow instance</li>
     *     <li>message - message to the users</li>
     *     <li>email - boolean, true or false to email all participants - optional</li>
     *   </ul>
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
	public Map addAnnouncement(Map params) throws Exception {
		Map map = new HashMap();
		map.put("successful", false);
		boolean bool_email = false;
		
		String strWorkflowId = (String)params.get("workflowId");
		String message = (String)params.get("message");
		String email = (String)params.get("email");
		
		
        if(strWorkflowId==null || "".equals(strWorkflowId.trim())){
        	map.put("reason", "workflowId cannot be null.");
    		return map;	
        }
        
        if(message==null || "".equals(message.trim())){
        	map.put("reason", "message cannot be null.");
    		return map;	
        }
        
        if(email.equals("true")) {
        	bool_email = true;
        }
        
        try {   
        	Long workflowId = Long.parseLong(strWorkflowId);
        	systemService.addAnnouncement(workflowId, message);
        	
        	Map values = new HashMap();
        	if(bool_email) {
	        	Collection participants = systemService.getUsersByRole("participant");
	            values.put("announcement", message);
	            for (User participant : (Collection<User>) participants) {
	            	if(participant.isEmailNotify()) {
		                values.put("participant", participant);
		                emailSender.send(participant, "announcement", values);
	            	}
	            }
        	}
        	map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
		return map;
	}
	
	
	/**
     * Edit Announcement
     * @param params a Map contains:
     *   <ul>
     *     <li>id - id of the announcement</li>
     *     <li>message - message to the users</li>
     *   </ul>
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
	public Map editAnnouncement(Map params) throws Exception {
		Map map = new HashMap();
		map.put("successful", false);
		
		String strId = (String)params.get("id");
		String message = (String)params.get("message");

        if(strId==null || "".equals(strId.trim())){
        	map.put("reason", "id cannot be null.");
    		return map;	
        }
        
        if(message==null || "".equals(message.trim())){
        	map.put("reason", "message cannot be null.");
    		return map;	
        }
        
        try {   
        	Long id = Long.parseLong(strId);
        	
        	systemService.editAnnouncement(id, message);
        	
        	map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
		return map;
	}
	
	
	/**
     * delete Announcement
     * @param params a Map contains:
     *   <ul>
     *     <li>id - id of the announcement</li>
     *   </ul>
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
	public Map deleteAnnouncement(Map params) throws Exception {
		Map map = new HashMap();
		map.put("successful", false);
		
		String strId = (String)params.get("id");

        if(strId==null || "".equals(strId.trim())){
        	map.put("reason", "id cannot be null.");
    		return map;	
        }
        
        try {   
        	Long id = Long.parseLong(strId);
        	
        	systemService.deleteAnnouncement(id);
        	
        	map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
		return map;
	}
	
	
	/**
     * Get Announcements
     * @param params a Map contains:
     *   <ul>
     *     <li>workflowId - workflowId of the current instance</li>
     *   </ul>
     * @return a Map contains:
     *   <ul>
     *     <li>announcements - collection of announcement objects</li>
     *     <li>html - html file system_announcements.jsp</li>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
	public Map getAnnouncements(Map params, HttpServletRequest request) throws Exception {
		Map map = new HashMap();
		map.put("successful", false);
		
		
		String strWorkflowId = (String)params.get("workflowId");
		
        if(strWorkflowId==null || "".equals(strWorkflowId.trim())){
        	map.put("reason", "workflowId cannot be null.");
    		return map;	
        }
        
		
        try {   
        	Long workflowId = Long.parseLong(strWorkflowId);
        	
        	Collection announcements = systemService.getAnnouncements(workflowId);
        	request.setAttribute("announcements", announcements);
        	map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/system/system_announcements.jsp"));
        	map.put("announcements", announcements);
        	
        	map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
		return map;
	}
	
	
    /**
     * 
     * This method has no parameter.
     * 
     * @return a map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map setUnloading() {
        Map map = new HashMap();
        map.put("successful", true);
        
        /*
         * Do nothing, just return successful=true,
         * the PgistFilter will log it.
         */
        
        return map;
    }//setUnloading();
    
    
	/**
	 * =========================================
	 * Ajax function
	 *
     */
	public Map logMapEvent(String eventinfo){
		Map result = new HashMap();
		result.put("successful", true);
		
		return result;
	} //logMapEvent();

	
	public Map addNewAffiliation(String affiliation) {
        Map result = new HashMap();
        result.put("successful", false);
	    
        try {
            if (affiliation==null) {
                throw new Exception("Affiliation can't be empty.");
            }
            
            affiliation = affiliation.trim();
            if (affiliation.length()==0) {
                result.put("reason", "Affiliation can't be empty.");
                return result;
            } else if (affiliation.length()>64) {
                affiliation = affiliation.substring(0, 65);
            }
            
            Long id = systemService.addNewAffiliation(affiliation);
            Collection userAssocs = systemService.getUserAssocs(WebUtils.currentUserId());
            result.put("enough", userAssocs.size()>=5);
            result.put("id", id);
            result.put("successful", true);
        } catch (Exception e) {
            result.put("successful", false);
            result.put("reason", e.getMessage());
            e.printStackTrace();
        }
        
        return result;
	}//addNewAffiliation()
	
	//The following methods are for CyberGIS Token Service
	private String verifyToken(String loginname, String token)
	throws KeyManagementException, HttpException,
	NoSuchAlgorithmException, IOException, JSONException {
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest req = ctx.getHttpServletRequest();
		
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("token", token));
		formparams.add(new BasicNameValuePair("consumer", props.getProperty("CONSUMER_ID")));
		formparams.add(new BasicNameValuePair("auth_request", "default"));
		formparams.add(new BasicNameValuePair("username", loginname));
		formparams.add(new BasicNameValuePair("remote_addr", req.getRemoteAddr()));
		//formparams.add(new BasicNameValuePair("remote_addr", "71.85.121.220"));
		String url = props.getProperty("APIURL")+"token";
		String resultString = executePut(url, formparams);
		
		return resultString;
}
    

	private String executePut(String url, List<NameValuePair> formparams)
	throws HttpException, IOException, KeyManagementException,
	NoSuchAlgorithmException {
		HttpPut method = new HttpPut(url);
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
		method.setEntity(entity);
		return executeMethod(method);
	}

	
	private String getQueryString(List<NameValuePair> formparams) {
		StringBuffer buffer = new StringBuffer();
		for (NameValuePair nv : formparams) {
			buffer.append("&").append(URLEncoder.encode(nv.getName())).append("=")
					.append(URLEncoder.encode(nv.getValue()));
		}
		if (buffer.length() > 0) {
			return buffer.substring(1);
		} else {
			return null;
		}
	}

	private String executeMethod(HttpRequestBase method) throws HttpException,
			IOException, KeyManagementException, NoSuchAlgorithmException {
		SSLContext ctx = SSLContext.getInstance("TLS");
		ctx.init(new KeyManager[0],
				new TrustManager[] { new DefaultTrustManager() },
				new SecureRandom());
		SSLContext.setDefault(ctx);
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(method);
		HttpEntity resEntity = response.getEntity();
		String responseString = "";
		if(resEntity!=null){
			BufferedReader br = new BufferedReader(new InputStreamReader(resEntity.getContent()));
			String line = "";
			while((line=br.readLine())!=null){
				responseString += line;
			}
		}
		return responseString;
	}

	private class DefaultTrustManager implements X509TrustManager {

		public void checkClientTrusted(X509Certificate[] arg0, String arg1)
				throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] arg0, String arg1)
				throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

	}
	
	
}//class SystemAgent
