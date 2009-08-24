package org.pgist.system;

import java.util.Collection;

import org.pgist.sarp.vtt.VTT;
import org.pgist.users.User;
import org.pgist.util.PageSetting;
import org.pgist.web.DelegatingHttpServletRequestWrapper;


/**
 * 
 * @author kenny
 *
 */
public interface SystemService {
    
    
    Object getObject(String entityName, Long id) throws Exception;
    
    
    User getUserByName(String loginname, boolean deleted) throws Exception;
    
    
    User getUserByName(String loginname, boolean enabled, boolean deleted) throws Exception;
    
  
    Collection getUsersByRole(String role) throws Exception;
    
    Collection getUsersByAssoc(String assoc) throws Exception;
    
    void createUser(User user) throws Exception;
    
    
    Feedback createFeedback(String action, String s) throws Exception;
    
    
    Collection getFeedbacks(PageSetting setting) throws Exception;
    
    
	User getCurrentUser() throws Exception;
    
    
    YesNoVoting getVoting(int targetType, Long targetId) throws Exception;


    void logRequest(DelegatingHttpServletRequestWrapper request) throws Exception;


    void editCurrentUser(String address1, String address2, String state, String homeCity, String homeZipcode, String workCity, String workZipcode, String vocation, String primaryTransport, String profileDesc) throws Exception;

    
    boolean editUserSettings(String cpassword, String password1, String email, boolean emailNotify, boolean emailNotifyDisc) throws Exception;
    
    
    Collection getAllUsers() throws Exception;
    
    
    User getUserById(Long id) throws Exception;
    
    
    void disableUsers(String[] ids, boolean enable) throws Exception;
    
    
    Collection getEnabledUsers() throws Exception;
    
    
    Collection getDisabledUsers() throws Exception;
    
    
    String getEmailList(boolean quota, boolean nonquota) throws Exception;
    
    
    void resetPassword(String[] ids, String password) throws Exception;
    
    
    void setQuota(Long id, boolean quota) throws Exception;
    
    
    void setQuotaLimit(Long countyId, int limit) throws Exception;
    
    
    Long addCounty(String name) throws Exception;
    
    
    void editCountyName(Long countyId, String name) throws Exception;
    
    
    Collection createQuotaStats() throws Exception;
    
    
    void addZipCodes(Long countyId, String[] zipCodes) throws Exception;
    
    
    void deleteZipCodes(Long countyId, String[] zipCodes) throws Exception;
    
    
    Collection getAllCounties() throws Exception;
    
    
    void deleteCounty(Long countyId) throws Exception;
    
    
    void addAnnouncement(Long workflowId, String message) throws Exception; 
    
    
    void editAnnouncement(Long id, String message) throws Exception;
    
    
    void deleteAnnouncement(Long id) throws Exception;
    
    Collection getAnnouncements(Long workflowId) throws Exception;
    
    Collection getTransTypes() throws Exception;
    
    void deleteUser(Long id) throws Exception;

    
}//interface SystemService
