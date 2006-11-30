package org.pgist.system;

import java.util.Collection;

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
    
    
}//interface SystemDAO
