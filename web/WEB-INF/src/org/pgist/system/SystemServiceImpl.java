package org.pgist.system;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.pgist.exceptions.UserExistException;
import org.pgist.users.BaseUser;
import org.pgist.users.Role;
import org.pgist.users.User;
import org.pgist.util.PageSetting;
import org.pgist.util.WebUtils;
import org.pgist.web.DelegatingHttpServletRequestWrapper;


/**
 * 
 * @author kenny
 *
 */
public class SystemServiceImpl implements SystemService {
    
    
    private SystemDAO systemDAO;
    
    private UserDAO userDAO;
    
    
    public void setSystemDAO(SystemDAO systemDAO) {
        this.systemDAO = systemDAO;
    }


    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    public User getUserByName(String loginname, boolean enabled, boolean deleted) throws Exception {
        return userDAO.getUserByName(loginname, enabled, deleted);
    }//getUserByName()
    
    
    public Collection getUsersByRole(String role) throws Exception {
        return userDAO.getUsersByRole(role);
    }//getUsersByRole()


    public void createUser(User user) throws Exception {
        // check if user already exists
        User other = userDAO.getUserByName(user.getLoginname(), false);
        if (other!=null) throw new UserExistException("The ID already exist. Please pick a differnt ID.");
        
        Role role = userDAO.getRoleByName("participant");
        user.addRole(role);
        
        userDAO.save(user);
    }//createUser()


    public Feedback createFeedback(String action, String s) throws Exception {
        Feedback feedback = new Feedback();
        
        feedback.setAction(action);
        feedback.setContent(s);
        feedback.setCreateTime(new Date());
        
        User user = userDAO.getUserById(WebUtils.currentUserId(), true, false);
        feedback.setUser(user);
        
        BaseUser baseuser = new BaseUser();
        String email = baseuser.getEmail();
        feedback.setEmail(email);
        
        systemDAO.save(feedback);
        
        return feedback;
    }//createFeedback()


    public Collection getFeedbacks(PageSetting setting) throws Exception {
        return systemDAO.getFeedbacks(setting);
    }//getFeedbacks()


	public User getCurrentUser() throws Exception {
		return userDAO.getUserById(WebUtils.currentUserId(), true, false);
	}
    
    
    public YesNoVoting getVoting(int targetType, Long targetId) throws Exception {
        return systemDAO.getVoting(targetType, targetId);
    }//getVoting()


    public void logRequest(DelegatingHttpServletRequestWrapper request) throws Exception {
        String method = request.getMethod();
        if ("GET".equals(method)) {
            systemDAO.logGetting(request);
        } else {
            systemDAO.logPosting(request);
        }
    }//logRequest()


    public void editCurrentUser(String address1, String address2, String state, String homeCity, String homeZipcode, String workCity, String workZipcode, String vocation, String primaryTransport, String profileDesc) throws Exception {
        User user = userDAO.getUserById(WebUtils.currentUserId(), true, false);
        
        user.setHomeAddr(address1);
        user.setState(state);
        user.setCity(homeCity);
        user.setZipcode(homeZipcode);

        if(!("".equals(address2.trim()))) {
        	user.setHomeAddr(address2);
        }
        if(!("".equals(workCity.trim()))) {
        	user.setWorkCity(workCity);
        }
        if(!("".equals(workZipcode.trim()))) {
        	user.setWorkZipcode(workZipcode);
        }
        if(!("".equals(vocation.trim()))) {
        	user.setVocation(vocation);
        }
        if(!("".equals(primaryTransport.trim()))) {
        	user.setPrimaryTransport(primaryTransport);
        }
        if(!("".equals(profileDesc.trim()))) {
        	user.setProfileDesc(profileDesc);
        }

        userDAO.updateProfile(user);
    }//editCurrentUser()

    
    public boolean editUserSettings(String cpassword, String password1, String email, boolean emailNotify, boolean emailNotifyDisc) throws Exception {
    	User user = userDAO.getUserById(WebUtils.currentUserId(), true, false);
    	
    	if((cpassword==null || "".equals(cpassword)) && (password1==null || "".equals(password1))) {
    		user.setEmail(email);
        	user.setEmailNotify(emailNotify);
        	user.setEmailNotifyDisc(emailNotifyDisc);
        	userDAO.save(user);
        	return true;
    	} else {
	    	if (!user.checkPassword(cpassword)) {
	    		return false;
	    	}
	    	
	    	user.setPassword(password1);
	    	user.encodePassword();
	    	user.setEmail(email);
        	user.setEmailNotify(emailNotify);
        	user.setEmailNotifyDisc(emailNotifyDisc);
        	
	    	userDAO.save(user);
	    	return true;
    	}
    }
    
    
    public Collection getAllUsers() throws Exception {    	
    	
    	return systemDAO.getAllUsers();
    } //getAllUsers();

    
    public User getUserById(Long id) throws Exception { 
    	
    	return systemDAO.getUserById(id);
    }
    
    
    public void disableUsers(String[] ids, boolean enable) throws Exception {
    	systemDAO.disableUsers(ids, enable);
    }
    
    
    public Collection getEnabledUsers() throws Exception {
    	return systemDAO.getEnabledUsers();
    }
    
    
    public Collection getDisabledUsers() throws Exception {
    	return systemDAO.getDisabledUsers();
    }
    
    
    public String getEmailList(boolean enabled, boolean disabled) throws Exception {
    	Collection userslist = getAllUsers();
    	String emaillist = "";
    	
    	Iterator ul = userslist.iterator();
    	
		if(enabled == disabled) {
			while(ul.hasNext()) {
	    		User user = (User)ul.next();
	    		emaillist += user.getEmail() + ", ";
			}
		} else if (enabled) {
			while(ul.hasNext()) {
	    		User user = (User)ul.next();
	    		if(user.isEnabled()) {
	    			emaillist += user.getEmail() + ", ";
	    		}
			}
		} else if (disabled) {
			while(ul.hasNext()) {
	    		User user = (User)ul.next();
	    		if(!user.isEnabled()) {
	    			emaillist += user.getEmail() + ", ";
	    		}
			}
		}

    	return emaillist;
    }
    
    
    public void resetPassword(String[] ids, String password) throws Exception {
    	systemDAO.resetPassword(ids, password);
    }
    
    
    public void setQuota(Long id, boolean quota) throws Exception {
    	systemDAO.setQuota(id, quota);
    }
    
    
    public void setQuotaLimit(Long countyId, int limit) throws Exception {
    	systemDAO.setQuotaLimit(countyId, limit);
    }
    
    
    public Long addCounty(String name) throws Exception {
    	return systemDAO.addCounty(name);
    }
    
    
    public void editCountyName(Long countyId, String name) throws Exception {
    	systemDAO.editCountyName(countyId, name);
    }
    
    
    public Collection createQuotaStats() throws Exception {
    	return systemDAO.createQuotaStats();
    }
    
    
    public void addZipCodes(Long countyId, String[] zipCodes) throws Exception {
    	systemDAO.addZipCodes(countyId, zipCodes);
    }
    
    
    public void deleteZipCodes(Long countyId, String[] zipCodes) throws Exception {
    	systemDAO.deleteZipCodes(countyId, zipCodes);
    }
    
    
    public Collection getAllCounties() throws Exception {
    	return systemDAO.getAllCounties();
    }
    
    
    public void deleteCounty(Long countyId) throws Exception {
    	systemDAO.deleteCounty(countyId);
    }
    
    
    public void addAnnouncement(Long workflowId, String message) throws Exception {
    	systemDAO.addAnnouncement(workflowId, message);
    }
    
    
    public void editAnnouncement(Long id, String message) throws Exception {
    	systemDAO.editAnnouncement(id, message);
    }
    
    
    public void deleteAnnouncement(Long id) throws Exception {
    	systemDAO.deleteAnnouncement(id);
    }
    
    
    public Collection getAnnouncements(Long workflowId) throws Exception {
    	return systemDAO.getAnnouncements(workflowId);
    }
    
    
}//class SystemServiceImpl
