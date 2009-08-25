package org.pgist.system;

import java.util.Collection;
import java.util.List;

import org.pgist.users.User;
import org.pgist.util.PageSetting;
import org.pgist.web.DelegatingHttpServletRequestWrapper;


/**
 * 
 * @author John
 *
 */
public interface SystemDAO extends BaseDAO {
    
    
    Object getObject(String entityName, Long id) throws Exception;
    
    
    Collection getFeedbacks(PageSetting setting) throws Exception;
    
    
    YesNoVoting getVoting(int targetType, Long targetId) throws Exception;

    
    boolean setVoting(int targetType, Long targetId, boolean agree) throws Exception;


    void logGetting(DelegatingHttpServletRequestWrapper request) throws Exception;
    
    
    void logPosting(DelegatingHttpServletRequestWrapper request) throws Exception;
    
    
    Collection getAllUsers() throws Exception;
    
    Collection getAllAssocs() throws Exception;

    Collection getUserAssocs() throws Exception;

    User getUserById(Long id) throws Exception;
    
    
    void disableUsers(String[] ids, boolean enable) throws Exception;
    
    
    Collection getEnabledUsers() throws Exception;
    
    
    Collection getDisabledUsers() throws Exception;
    
    
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


	void deleteVote(Long oid) throws Exception;


    User getAdmin() throws Exception;
    
    
}//interface SystemDAO
