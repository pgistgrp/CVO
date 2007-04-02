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
public interface SystemDAO extends BaseDAO {
    
    
    Collection getFeedbacks(PageSetting setting) throws Exception;
    
    
    YesNoVoting getVoting(int targetType, Long targetId) throws Exception;

    
    boolean setVoting(int targetType, Long targetId, boolean agree) throws Exception;


    void logGetting(DelegatingHttpServletRequestWrapper request) throws Exception;
    
    
    void logPosting(DelegatingHttpServletRequestWrapper request) throws Exception;
    
    
    Collection getAllUsers() throws Exception;


    User getUserById(Long id) throws Exception;
    
    
    void disableUsers(String[] ids, boolean enable) throws Exception;
    
    
    Collection getEnabledUsers() throws Exception;
    
    
    Collection getDisabledUsers() throws Exception;
    
    
    void resetPassword(String[] ids) throws Exception;
    
    
    void setQuota(Long id, boolean quota) throws Exception;
    
    void setQuotaLimit(Long countyId, int limit) throws Exception;
    
    Long addCounty(String name) throws Exception;
    
    Collection createQuotaStats() throws Exception;
    
    
}//interface SystemDAO
