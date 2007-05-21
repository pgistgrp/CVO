package org.pgist.system;


import java.util.Collection;
import java.util.Date;

import org.pgist.users.User;
import org.pgist.util.WebUtils;


public class ProfileServiceImpl implements ProfileService {

	private ProfileDAO profileDAO;
	
    private SystemService systemService;
    

    public void setProfileDAO(ProfileDAO profileDAO) {
        this.profileDAO = profileDAO;
    }
    
    
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}
	
	
    public User getUserInfo(String username) throws Exception {
    	User user = systemService.getUserByName(username, true, false);
    	return profileDAO.getUserInfo(user);
    }
    
    
    public boolean setUserInfo(String username, String homecity, String homezipcode, String workcity, String workzipcode, String vocation, String primarytransport, String profiledesc) throws Exception {
    	User user = systemService.getCurrentUser();
    	if(username.equals(user.getLoginname())) {
    		return profileDAO.setUserInfo(user, homecity, homezipcode, workcity, workzipcode, vocation, primarytransport, profiledesc);
    	}
    	return false;
    }

    
    public void getDiscussionPost() throws Exception {
    	User user = systemService.getCurrentUser();
    	profileDAO.getDiscussionPost(user);
    }

    
    public Date getLastLogin(String username) throws Exception {
    	return profileDAO.getLastLogin(username);
    }
    
    
    public int getTotalVisits(String username) throws Exception {
    	return profileDAO.getTotalVisits(username);
    }
    
    
    public int getPostCount(String username) throws Exception {
    	return profileDAO.getPostCount(username);
    }
    
    
    public Collection getUserConcerns(String username) throws Exception {
    	return profileDAO.getUserConcerns(username);
    }
    
    
}
