package org.pgist.system;


import java.util.Collection;
import java.util.Date;
import java.util.Set;

import org.pgist.users.User;


public class ProfileServiceImpl implements ProfileService {

	private ProfileDAO profileDAO;
	
    private SystemService systemService;
    

    public void setProfileDAO(ProfileDAO profileDAO) {
        this.profileDAO = profileDAO;
    }
    
    
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}
	
	
    public User getUserInfo(Long userId) throws Exception {
    	User user = systemService.getUserById(userId);
    	return user;
    }
    
    
    public boolean setUserInfo(String username, String homecity, String homezipcode, String workcity, String workzipcode, String vocation, String primarytransport, String profiledesc) throws Exception {
    	User user = systemService.getCurrentUser();
    	if(username.equals(user.getLoginname())) {
    		return profileDAO.setUserInfo(user, homecity, homezipcode, workcity, workzipcode, vocation, primarytransport, profiledesc);
    	}
    	return false;
    }

    
    public Date getLastLogin(Long id) throws Exception {
    	return profileDAO.getLastLogin(id);
    }
    
    
    public int getTotalVisits(Long userId) throws Exception {
    	return profileDAO.getTotalVisits(userId);
    }
    
    
    public int getPostCount(Long id) throws Exception {
    	return profileDAO.getPostCount(id);
    }
    
    
    public Collection getUserConcerns(Long id) throws Exception {
    	return profileDAO.getUserConcerns(id);
    }
    
    
    public Collection getUserDiscussion(String username, int start, int end) throws Exception {
    	return profileDAO.getUserDiscussion(username, start, end);
    }
    
    
    public String[] getAllTags(Long userId) throws Exception {
    	return profileDAO.getAllTags(userId);
    }
    
    
    public Set getUserCriteria(String username) throws Exception {
    	return profileDAO.getUserCriteria(username);
    }
    
    
}
