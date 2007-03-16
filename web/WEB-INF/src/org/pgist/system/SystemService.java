package org.pgist.system;

import java.util.Collection;

import org.pgist.users.User;
import org.pgist.util.PageSetting;
import org.pgist.web.DelegatingHttpServletRequestWrapper;


/**
 * 
 * @author kenny
 *
 */
public interface SystemService {
    
    
    User getUserByName(String loginname, boolean enabled, boolean deleted) throws Exception;
    
    
    Collection getUsersByRole(String role) throws Exception;
    
    
    void createUser(User user) throws Exception;
    
    
    Feedback createFeedback(String action, String s) throws Exception;
    
    
    Collection getFeedbacks(PageSetting setting) throws Exception;
    
    
	User getCurrentUser() throws Exception;
    
    
    YesNoVoting getVoting(int targetType, Long targetId) throws Exception;


    void logRequest(DelegatingHttpServletRequestWrapper request) throws Exception;


    void editCurrentUser(String cpassword, String password, String email) throws Exception;


    Collection getAllUsers() throws Exception;
    
    
    User getUserById(Long id) throws Exception;
    
    
    void disableUsers(String[] ids, boolean enable) throws Exception;
    
    
    Collection getEnabledUsers() throws Exception;
    
    
    Collection getDisabledUsers() throws Exception;
    
    
    String getEmailList(boolean enabled, boolean disabled) throws Exception;
    
    
    void resetPassword(String[] ids) throws Exception;
    
    
    void setQuota(Long id, boolean quota) throws Exception;
    
    
}//interface SystemService
